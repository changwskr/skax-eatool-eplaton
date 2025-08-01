#!/bin/bash

# SKAX EATool Eplaton 프로덕션 테스트 스크립트
# 사용법: ./production-tests.sh

set -e

NAMESPACE="skax-eatool"
BASE_URL="https://skax-eatool.local"
TIMEOUT=60
RETRY_COUNT=5

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 로그 함수
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_info "프로덕션 테스트 시작"

# HTTP 요청 함수
http_request() {
    local url=$1
    local method=${2:-GET}
    local data=${3:-}
    local headers=${4:-}
    
    if [ -n "$data" ]; then
        curl -s -o /dev/null -w "%{http_code}" -X $method -H "Content-Type: application/json" -H "$headers" -d "$data" "$url"
    else
        curl -s -o /dev/null -w "%{http_code}" -X $method -H "$headers" "$url"
    fi
}

# 재시도 함수
retry_request() {
    local url=$1
    local method=${2:-GET}
    local data=${3:-}
    local expected_status=${4:-200}
    local headers=${5:-}
    
    for i in $(seq 1 $RETRY_COUNT); do
        log_info "요청 시도 $i/$RETRY_COUNT: $method $url"
        
        status=$(http_request "$url" "$method" "$data" "$headers")
        
        if [ "$status" = "$expected_status" ]; then
            log_info "성공: $method $url (상태: $status)"
            return 0
        else
            log_warn "실패: $method $url (상태: $status, 예상: $expected_status)"
            if [ $i -lt $RETRY_COUNT ]; then
                sleep 10
            fi
        fi
    done
    
    log_error "최종 실패: $method $url"
    return 1
}

# 1. 프로덕션 환경 상태 확인
log_info "=== 프로덕션 환경 상태 확인 ==="

# Pod 상태 확인
log_info "Pod 상태 확인 중..."
kubectl get pods -n $NAMESPACE

# 서비스 상태 확인
log_info "서비스 상태 확인 중..."
kubectl get services -n $NAMESPACE

# Ingress 상태 확인
log_info "Ingress 상태 확인 중..."
kubectl get ingress -n $NAMESPACE

# 2. 보안 테스트
log_info "=== 보안 테스트 ==="

# HTTPS 리다이렉트 테스트
log_info "HTTPS 리다이렉트 테스트 중..."
http_status=$(curl -s -o /dev/null -w "%{http_code}" -I "http://skax-eatool.local")
if [ "$http_status" = "301" ] || [ "$http_status" = "302" ]; then
    log_info "HTTPS 리다이렉트 정상 작동"
else
    log_warn "HTTPS 리다이렉트가 예상대로 작동하지 않음: $http_status"
fi

# SSL 인증서 확인
log_info "SSL 인증서 확인 중..."
ssl_check=$(echo | openssl s_client -connect skax-eatool.local:443 -servername skax-eatool.local 2>/dev/null | openssl x509 -noout -dates)
if [ $? -eq 0 ]; then
    log_info "SSL 인증서 정상"
    echo "$ssl_check"
else
    log_error "SSL 인증서 확인 실패"
fi

# 3. 성능 테스트
log_info "=== 성능 테스트 ==="

# 응답 시간 측정 (프로덕션 기준)
for service in kji ksa mba mbc mbc01; do
    log_info "$service 서비스 성능 테스트 중..."
    
    # 20회 요청하여 평균 응답 시간 계산
    total_time=0
    success_count=0
    min_time=999999
    max_time=0
    
    for i in {1..20}; do
        start_time=$(date +%s%N)
        status=$(http_request "$BASE_URL/$service/actuator/health")
        end_time=$(date +%s%N)
        
        if [ "$status" = "200" ]; then
            response_time=$(( (end_time - start_time) / 1000000 ))
            total_time=$((total_time + response_time))
            success_count=$((success_count + 1))
            
            # 최소/최대 시간 업데이트
            if [ $response_time -lt $min_time ]; then
                min_time=$response_time
            fi
            if [ $response_time -gt $max_time ]; then
                max_time=$response_time
            fi
        fi
    done
    
    if [ $success_count -gt 0 ]; then
        avg_time=$((total_time / success_count))
        log_info "$service 성능 결과:"
        log_info "  - 평균 응답 시간: ${avg_time}ms"
        log_info "  - 최소 응답 시간: ${min_time}ms"
        log_info "  - 최대 응답 시간: ${max_time}ms"
        log_info "  - 성공률: $((success_count * 100 / 20))%"
        
        # 프로덕션 기준 검증
        if [ $avg_time -gt 2000 ]; then
            log_warn "$service 평균 응답 시간이 2초를 초과했습니다: ${avg_time}ms"
        fi
        
        if [ $success_count -lt 18 ]; then
            log_warn "$service 성공률이 90% 미만입니다: $((success_count * 100 / 20))%"
        fi
    else
        log_error "$service 모든 요청이 실패했습니다."
    fi
done

# 4. 부하 테스트
log_info "=== 부하 테스트 ==="

# 동시 요청 테스트 (프로덕션 기준)
for service in kji ksa mba mbc mbc01; do
    log_info "$service 서비스 부하 테스트 중..."
    
    # 10개의 동시 요청
    success_count=0
    total_count=10
    
    for i in {1..10}; do
        (
            status=$(http_request "$BASE_URL/$service/actuator/health")
            if [ "$status" = "200" ]; then
                echo "success" >> /tmp/load_test_$service
            else
                echo "fail" >> /tmp/load_test_$service
            fi
        ) &
    done
    
    # 모든 백그라운드 작업 완료 대기
    wait
    
    # 결과 집계
    if [ -f "/tmp/load_test_$service" ]; then
        success_count=$(grep -c "success" /tmp/load_test_$service)
        rm /tmp/load_test_$service
    fi
    
    success_rate=$((success_count * 100 / total_count))
    log_info "$service 부하 테스트 결과: $success_count/$total_count 성공 ($success_rate%)"
    
    if [ $success_rate -lt 80 ]; then
        log_warn "$service 부하 테스트 성공률이 80% 미만입니다: $success_rate%"
    fi
done

# 5. 가용성 테스트
log_info "=== 가용성 테스트 ==="

# 연속 요청 테스트 (1분간)
for service in kji ksa mba mbc mbc01; do
    log_info "$service 서비스 가용성 테스트 중 (1분간)..."
    
    start_time=$(date +%s)
    success_count=0
    total_count=0
    
    while [ $(($(date +%s) - start_time)) -lt 60 ]; do
        status=$(http_request "$BASE_URL/$service/actuator/health")
        total_count=$((total_count + 1))
        
        if [ "$status" = "200" ]; then
            success_count=$((success_count + 1))
        fi
        
        sleep 2
    done
    
    availability=$((success_count * 100 / total_count))
    log_info "$service 가용성: $success_count/$total_count ($availability%)"
    
    if [ $availability -lt 99 ]; then
        log_warn "$service 가용성이 99% 미만입니다: $availability%"
    fi
done

# 6. 리소스 모니터링
log_info "=== 리소스 모니터링 ==="

# CPU 및 메모리 사용량 확인
kubectl top pods -n $NAMESPACE

# 리소스 사용량 분석
for service in kji ksa mba mbc mbc01 apigateway; do
    log_info "$service 리소스 사용량 분석 중..."
    
    # CPU 사용량 확인
    cpu_usage=$(kubectl top pods -l app=$service -n $NAMESPACE --no-headers | awk '{print $2}' | sed 's/m//')
    memory_usage=$(kubectl top pods -l app=$service -n $NAMESPACE --no-headers | awk '{print $3}' | sed 's/Mi//')
    
    log_info "$service CPU 사용량: ${cpu_usage}m"
    log_info "$service 메모리 사용량: ${memory_usage}Mi"
    
    # 임계값 검증
    if [ "$cpu_usage" -gt 800 ]; then
        log_warn "$service CPU 사용량이 높습니다: ${cpu_usage}m"
    fi
    
    if [ "$memory_usage" -gt 1500 ]; then
        log_warn "$service 메모리 사용량이 높습니다: ${memory_usage}Mi"
    fi
done

# 7. 로그 분석
log_info "=== 로그 분석 ==="

# 에러 로그 확인
for service in kji ksa mba mbc mbc01 apigateway; do
    log_info "$service 로그 분석 중..."
    
    # 최근 1시간간 에러 로그 확인
    error_count=$(kubectl logs -l app=$service -n $NAMESPACE --since=1h | grep -i error | wc -l)
    warning_count=$(kubectl logs -l app=$service -n $NAMESPACE --since=1h | grep -i warning | wc -l)
    
    log_info "$service 최근 1시간 로그:"
    log_info "  - 에러: $error_count 개"
    log_info "  - 경고: $warning_count 개"
    
    if [ $error_count -gt 10 ]; then
        log_warn "$service 에러 로그가 많습니다: $error_count 개"
    fi
done

# 8. 데이터베이스 연결 테스트
log_info "=== 데이터베이스 연결 테스트 ==="

# H2 Console 접근 테스트 (프로덕션에서는 제한적)
for service in kji ksa mba mbc mbc01; do
    log_info "$service 데이터베이스 연결 테스트 중..."
    
    # Health Check를 통한 간접적인 DB 연결 확인
    health_response=$(curl -s "$BASE_URL/$service/actuator/health")
    
    if echo "$health_response" | grep -q "UP"; then
        log_info "$service 데이터베이스 연결 정상"
    else
        log_warn "$service 데이터베이스 연결 문제 가능성"
    fi
done

# 9. 외부 서비스 연결 테스트
log_info "=== 외부 서비스 연결 테스트 ==="

# API Gateway를 통한 외부 연결 테스트
log_info "API Gateway 외부 연결 테스트 중..."
retry_request "$BASE_URL/actuator/health" "GET" "" "200"

# 10. 백업 및 복구 테스트 (시뮬레이션)
log_info "=== 백업 및 복구 테스트 ==="

# 현재 상태 백업
log_info "현재 배포 상태 백업 중..."
kubectl get all -n $NAMESPACE -o yaml > /tmp/production_backup_$(date +%Y%m%d_%H%M%S).yaml
log_info "백업 완료: /tmp/production_backup_$(date +%Y%m%d_%H%M%S).yaml"

log_info "프로덕션 테스트 완료!"

# 테스트 결과 요약
echo ""
echo "=== 프로덕션 테스트 결과 요약 ==="
echo "테스트 시간: $(date)"
echo "환경: Production"
echo "상태: 완료"
echo "테스트된 항목:"
echo "  - 환경 상태 확인"
echo "  - 보안 테스트"
echo "  - 성능 테스트"
echo "  - 부하 테스트"
echo "  - 가용성 테스트"
echo "  - 리소스 모니터링"
echo "  - 로그 분석"
echo "  - 데이터베이스 연결"
echo "  - 외부 서비스 연결"
echo "  - 백업 및 복구"
echo ""

log_info "모든 프로덕션 테스트가 완료되었습니다." 