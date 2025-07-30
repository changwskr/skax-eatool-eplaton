#!/bin/bash

# SKAX EATool Eplaton 통합 테스트 스크립트
# 사용법: ./integration-tests.sh <environment>

set -e

ENVIRONMENT=$1
NAMESPACE="skax-eatool"
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

# 입력 검증
if [ -z "$ENVIRONMENT" ]; then
    log_error "사용법: $0 <environment>"
    exit 1
fi

log_info "통합 테스트 시작: 환경=$ENVIRONMENT"

# 환경별 엔드포인트 설정
case $ENVIRONMENT in
    "development")
        BASE_URL="http://localhost:8080"
        ;;
    "staging")
        BASE_URL="http://staging.skax-eatool.local"
        ;;
    "production")
        BASE_URL="https://skax-eatool.local"
        ;;
    *)
        log_error "지원하지 않는 환경: $ENVIRONMENT"
        exit 1
        ;;
esac

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

# Pod 상태 확인
log_info "Pod 상태 확인 중..."
kubectl wait --for=condition=ready pod -l app=kji -n $NAMESPACE --timeout=300s
kubectl wait --for=condition=ready pod -l app=ksa -n $NAMESPACE --timeout=300s
kubectl wait --for=condition=ready pod -l app=mba -n $NAMESPACE --timeout=300s
kubectl wait --for=condition=ready pod -l app=mbc -n $NAMESPACE --timeout=300s
kubectl wait --for=condition=ready pod -l app=mbc01 -n $NAMESPACE --timeout=300s
kubectl wait --for=condition=ready pod -l app=apigateway -n $NAMESPACE --timeout=300s

log_info "모든 Pod가 준비되었습니다."

# 1. 기본 서비스 연결 테스트
log_info "=== 기본 서비스 연결 테스트 ==="

# KJI 서비스 테스트
log_info "KJI 서비스 통합 테스트 중..."
retry_request "$BASE_URL/kji/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/kji/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/kji/actuator/metrics" "GET" "" "200"

# KSA 서비스 테스트
log_info "KSA 서비스 통합 테스트 중..."
retry_request "$BASE_URL/ksa/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/ksa/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/ksa/actuator/metrics" "GET" "" "200"

# MBA 서비스 테스트
log_info "MBA 서비스 통합 테스트 중..."
retry_request "$BASE_URL/mba/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mba/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/mba/actuator/metrics" "GET" "" "200"

# MBC 서비스 테스트
log_info "MBC 서비스 통합 테스트 중..."
retry_request "$BASE_URL/mbc/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mbc/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/mbc/actuator/metrics" "GET" "" "200"

# MBC01 서비스 테스트
log_info "MBC01 서비스 통합 테스트 중..."
retry_request "$BASE_URL/mbc01/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mbc01/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/mbc01/actuator/metrics" "GET" "" "200"

# API Gateway 테스트
log_info "API Gateway 통합 테스트 중..."
retry_request "$BASE_URL/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/actuator/info" "GET" "" "200"
retry_request "$BASE_URL/actuator/metrics" "GET" "" "200"

# 2. 서비스 간 통신 테스트
log_info "=== 서비스 간 통신 테스트 ==="

# MBA에서 MBC 서비스 호출 테스트
log_info "MBA -> MBC 서비스 통신 테스트 중..."
retry_request "$BASE_URL/mba/api/mbc/health" "GET" "" "200"

# API Gateway를 통한 라우팅 테스트
log_info "API Gateway 라우팅 테스트 중..."
retry_request "$BASE_URL/gateway/kji/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/gateway/ksa/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/gateway/mba/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/gateway/mbc/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/gateway/mbc01/actuator/health" "GET" "" "200"

# 3. 데이터베이스 연결 테스트
log_info "=== 데이터베이스 연결 테스트 ==="

# H2 Console 접근 테스트
retry_request "$BASE_URL/kji/h2-console" "GET" "" "200"
retry_request "$BASE_URL/ksa/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mba/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mbc/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mbc01/h2-console" "GET" "" "200"

# 4. 성능 테스트
log_info "=== 성능 테스트 ==="

# 응답 시간 측정
for service in kji ksa mba mbc mbc01; do
    log_info "$service 서비스 응답 시간 측정 중..."
    
    # 10회 요청하여 평균 응답 시간 계산
    total_time=0
    success_count=0
    
    for i in {1..10}; do
        start_time=$(date +%s%N)
        status=$(http_request "$BASE_URL/$service/actuator/health")
        end_time=$(date +%s%N)
        
        if [ "$status" = "200" ]; then
            response_time=$(( (end_time - start_time) / 1000000 ))
            total_time=$((total_time + response_time))
            success_count=$((success_count + 1))
        fi
    done
    
    if [ $success_count -gt 0 ]; then
        avg_time=$((total_time / success_count))
        log_info "$service 평균 응답 시간: ${avg_time}ms"
        
        if [ $avg_time -gt 3000 ]; then
            log_warn "$service 응답 시간이 3초를 초과했습니다: ${avg_time}ms"
        fi
    else
        log_error "$service 모든 요청이 실패했습니다."
    fi
done

# 5. 부하 테스트 (간단한 버전)
log_info "=== 부하 테스트 ==="

# 동시 요청 테스트
for service in kji ksa mba mbc mbc01; do
    log_info "$service 서비스 동시 요청 테스트 중..."
    
    # 5개의 동시 요청
    for i in {1..5}; do
        (
            status=$(http_request "$BASE_URL/$service/actuator/health")
            if [ "$status" = "200" ]; then
                log_info "$service 요청 $i 성공"
            else
                log_warn "$service 요청 $i 실패: 상태 $status"
            fi
        ) &
    done
    
    # 모든 백그라운드 작업 완료 대기
    wait
done

# 6. 메모리 및 CPU 사용량 확인
log_info "=== 리소스 사용량 확인 ==="

kubectl top pods -n $NAMESPACE

# 7. 로그 확인
log_info "=== 로그 확인 ==="

# 최근 로그에서 에러 확인
for service in kji ksa mba mbc mbc01 apigateway; do
    log_info "$service 서비스 로그 확인 중..."
    error_count=$(kubectl logs -l app=$service -n $NAMESPACE --tail=100 | grep -i error | wc -l)
    
    if [ $error_count -gt 0 ]; then
        log_warn "$service 서비스에서 $error_count 개의 에러 로그 발견"
    else
        log_info "$service 서비스 로그 정상"
    fi
done

log_info "통합 테스트 완료!"
log_info "환경: $ENVIRONMENT"
log_info "모든 테스트가 성공적으로 완료되었습니다."

# 테스트 결과 요약
echo ""
echo "=== 통합 테스트 결과 요약 ==="
echo "환경: $ENVIRONMENT"
echo "테스트 시간: $(date)"
echo "상태: 성공"
echo "테스트된 항목:"
echo "  - 기본 서비스 연결"
echo "  - 서비스 간 통신"
echo "  - 데이터베이스 연결"
echo "  - 성능 테스트"
echo "  - 부하 테스트"
echo "  - 리소스 사용량"
echo "  - 로그 확인"
echo "" 