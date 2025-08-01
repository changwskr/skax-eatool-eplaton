#!/bin/bash

# SKAX EATool Eplaton 스모크 테스트 스크립트
# 사용법: ./smoke-tests.sh <environment>

set -e

ENVIRONMENT=$1
NAMESPACE="skax-eatool"
TIMEOUT=30
RETRY_COUNT=3

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

log_info "스모크 테스트 시작: 환경=$ENVIRONMENT"

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
    
    if [ -n "$data" ]; then
        curl -s -o /dev/null -w "%{http_code}" -X $method -H "Content-Type: application/json" -d "$data" "$url"
    else
        curl -s -o /dev/null -w "%{http_code}" -X $method "$url"
    fi
}

# 재시도 함수
retry_request() {
    local url=$1
    local method=${2:-GET}
    local data=${3:-}
    local expected_status=${4:-200}
    
    for i in $(seq 1 $RETRY_COUNT); do
        log_info "요청 시도 $i/$RETRY_COUNT: $method $url"
        
        status=$(http_request "$url" "$method" "$data")
        
        if [ "$status" = "$expected_status" ]; then
            log_info "성공: $method $url (상태: $status)"
            return 0
        else
            log_warn "실패: $method $url (상태: $status, 예상: $expected_status)"
            if [ $i -lt $RETRY_COUNT ]; then
                sleep 5
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

# 서비스별 스모크 테스트
log_info "서비스별 스모크 테스트 시작..."

# 1. KJI 서비스 테스트
log_info "KJI 서비스 테스트 중..."
retry_request "$BASE_URL/kji/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/kji/actuator/info" "GET" "" "200"

# 2. KSA 서비스 테스트
log_info "KSA 서비스 테스트 중..."
retry_request "$BASE_URL/ksa/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/ksa/actuator/info" "GET" "" "200"

# 3. MBA 서비스 테스트
log_info "MBA 서비스 테스트 중..."
retry_request "$BASE_URL/mba/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mba/actuator/info" "GET" "" "200"

# 4. MBC 서비스 테스트
log_info "MBC 서비스 테스트 중..."
retry_request "$BASE_URL/mbc/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mbc/actuator/info" "GET" "" "200"

# 5. MBC01 서비스 테스트
log_info "MBC01 서비스 테스트 중..."
retry_request "$BASE_URL/mbc01/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/mbc01/actuator/info" "GET" "" "200"

# 6. API Gateway 테스트
log_info "API Gateway 테스트 중..."
retry_request "$BASE_URL/actuator/health" "GET" "" "200"
retry_request "$BASE_URL/actuator/info" "GET" "" "200"

# 데이터베이스 연결 테스트 (H2 Console)
log_info "데이터베이스 연결 테스트 중..."
retry_request "$BASE_URL/kji/h2-console" "GET" "" "200"
retry_request "$BASE_URL/ksa/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mba/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mbc/h2-console" "GET" "" "200"
retry_request "$BASE_URL/mbc01/h2-console" "GET" "" "200"

# 메트릭 엔드포인트 테스트
log_info "메트릭 엔드포인트 테스트 중..."
retry_request "$BASE_URL/kji/actuator/metrics" "GET" "" "200"
retry_request "$BASE_URL/ksa/actuator/metrics" "GET" "" "200"
retry_request "$BASE_URL/mba/actuator/metrics" "GET" "" "200"
retry_request "$BASE_URL/mbc/actuator/metrics" "GET" "" "200"
retry_request "$BASE_URL/mbc01/actuator/metrics" "GET" "" "200"
retry_request "$BASE_URL/actuator/metrics" "GET" "" "200"

# 응답 시간 테스트
log_info "응답 시간 테스트 중..."
for service in kji ksa mba mbc mbc01; do
    start_time=$(date +%s%N)
    status=$(http_request "$BASE_URL/$service/actuator/health")
    end_time=$(date +%s%N)
    response_time=$(( (end_time - start_time) / 1000000 ))
    
    if [ "$status" = "200" ]; then
        log_info "$service 응답 시간: ${response_time}ms"
        if [ $response_time -gt 5000 ]; then
            log_warn "$service 응답 시간이 5초를 초과했습니다: ${response_time}ms"
        fi
    else
        log_error "$service 응답 실패: 상태 $status"
    fi
done

log_info "스모크 테스트 완료!"
log_info "환경: $ENVIRONMENT"
log_info "모든 테스트가 성공적으로 완료되었습니다."

# 테스트 결과 요약
echo ""
echo "=== 스모크 테스트 결과 요약 ==="
echo "환경: $ENVIRONMENT"
echo "테스트 시간: $(date)"
echo "상태: 성공"
echo "테스트된 서비스: KJI, KSA, MBA, MBC, MBC01, API Gateway"
echo "테스트된 엔드포인트: Health, Info, Metrics, H2 Console"
echo "" 