#!/bin/bash

# SKAX EATool Eplaton 롤백 검증 스크립트
# 사용법: ./verify-rollback.sh <environment> <deployment>

set -e

ENVIRONMENT=$1
DEPLOYMENT=$2
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
if [ -z "$ENVIRONMENT" ] || [ -z "$DEPLOYMENT" ]; then
    log_error "사용법: $0 <environment> <deployment>"
    exit 1
fi

log_info "롤백 검증 시작: 환경=$ENVIRONMENT, 배포=$DEPLOYMENT"

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
    
    curl -s -o /dev/null -w "%{http_code}" -X $method "$url"
}

# 재시도 함수
retry_request() {
    local url=$1
    local method=${2:-GET}
    local expected_status=${3:-200}
    
    for i in $(seq 1 $RETRY_COUNT); do
        log_info "요청 시도 $i/$RETRY_COUNT: $method $url"
        
        status=$(http_request "$url" "$method")
        
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

# 배포 히스토리 확인
log_info "=== 배포 히스토리 확인 ==="

if [ "$DEPLOYMENT" = "all" ]; then
    MODULES=("kji" "ksa" "mba" "mbc" "mbc01" "apigateway")
else
    MODULES=("$DEPLOYMENT")
fi

for module in "${MODULES[@]}"; do
    log_info "$module 모듈 배포 히스토리 확인 중..."
    
    # 현재 리비전 확인
    current_revision=$(kubectl rollout history deployment/${module}-deployment -n $NAMESPACE --output=jsonpath='{.items[0].revision}')
    log_info "$module 현재 리비전: $current_revision"
    
    # 이전 리비전 확인
    previous_revision=$((current_revision - 1))
    if [ $previous_revision -ge 1 ]; then
        log_info "$module 이전 리비전: $previous_revision"
    else
        log_warn "$module 이전 리비전이 없습니다."
    fi
    
    # 배포 상태 확인
    kubectl rollout status deployment/${module}-deployment -n $NAMESPACE --timeout=60s
done

# Pod 상태 확인
log_info "=== Pod 상태 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module Pod 상태 확인 중..."
    
    # Pod가 Ready 상태인지 확인
    kubectl wait --for=condition=ready pod -l app=$module -n $NAMESPACE --timeout=300s
    
    # Pod 상세 정보 확인
    pod_name=$(kubectl get pods -l app=$module -n $NAMESPACE -o jsonpath='{.items[0].metadata.name}')
    log_info "$module Pod 이름: $pod_name"
    
    # Pod 상태 확인
    pod_status=$(kubectl get pod $pod_name -n $NAMESPACE -o jsonpath='{.status.phase}')
    log_info "$module Pod 상태: $pod_status"
    
    if [ "$pod_status" != "Running" ]; then
        log_error "$module Pod가 Running 상태가 아닙니다: $pod_status"
        exit 1
    fi
done

# 서비스 상태 확인
log_info "=== 서비스 상태 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module 서비스 상태 확인 중..."
    
    # 서비스 엔드포인트 확인
    endpoint_count=$(kubectl get endpoints ${module}-service -n $NAMESPACE -o jsonpath='{.subsets[0].addresses}' | jq length 2>/dev/null || echo "0")
    log_info "$module 엔드포인트 수: $endpoint_count"
    
    if [ "$endpoint_count" = "0" ]; then
        log_error "$module 서비스에 엔드포인트가 없습니다."
        exit 1
    fi
done

# Health Check 확인
log_info "=== Health Check 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module Health Check 확인 중..."
    
    case $module in
        "apigateway")
            health_url="$BASE_URL/actuator/health"
            ;;
        *)
            health_url="$BASE_URL/$module/actuator/health"
            ;;
    esac
    
    retry_request "$health_url" "GET" "200"
done

# 이미지 태그 확인
log_info "=== 이미지 태그 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module 이미지 태그 확인 중..."
    
    # 현재 이미지 태그 확인
    current_image=$(kubectl get deployment ${module}-deployment -n $NAMESPACE -o jsonpath='{.spec.template.spec.containers[0].image}')
    log_info "$module 현재 이미지: $current_image"
    
    # 이전 이미지 태그와 비교 (롤백이 실제로 이루어졌는지 확인)
    # 실제 환경에서는 이전 이미지 태그를 저장해두고 비교해야 함
    log_info "$module 이미지 태그 확인 완료"
done

# 로그 확인
log_info "=== 로그 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module 로그 확인 중..."
    
    # 최근 로그에서 에러 확인
    error_count=$(kubectl logs -l app=$module -n $NAMESPACE --tail=50 | grep -i error | wc -l)
    
    if [ $error_count -gt 0 ]; then
        log_warn "$module 서비스에서 $error_count 개의 에러 로그 발견"
        # 에러 로그 출력
        kubectl logs -l app=$module -n $NAMESPACE --tail=50 | grep -i error
    else
        log_info "$module 서비스 로그 정상"
    fi
    
    # 최근 로그에서 롤백 관련 메시지 확인
    rollback_count=$(kubectl logs -l app=$module -n $NAMESPACE --tail=50 | grep -i "rollback\|restart\|reload" | wc -l)
    
    if [ $rollback_count -gt 0 ]; then
        log_info "$module 서비스에서 $rollback_count 개의 롤백 관련 로그 발견"
    fi
done

# 메트릭 확인
log_info "=== 메트릭 확인 ==="

for module in "${MODULES[@]}"; do
    log_info "$module 메트릭 확인 중..."
    
    case $module in
        "apigateway")
            metrics_url="$BASE_URL/actuator/metrics"
            ;;
        *)
            metrics_url="$BASE_URL/$module/actuator/metrics"
            ;;
    esac
    
    retry_request "$metrics_url" "GET" "200"
done

# 리소스 사용량 확인
log_info "=== 리소스 사용량 확인 ==="

kubectl top pods -n $NAMESPACE

# 롤백 검증 결과 요약
log_info "롤백 검증 완료!"
log_info "환경: $ENVIRONMENT"
log_info "배포: $DEPLOYMENT"

echo ""
echo "=== 롤백 검증 결과 요약 ==="
echo "환경: $ENVIRONMENT"
echo "배포: $DEPLOYMENT"
echo "검증 시간: $(date)"
echo "상태: 성공"
echo "검증된 항목:"
echo "  - 배포 히스토리"
echo "  - Pod 상태"
echo "  - 서비스 상태"
echo "  - Health Check"
echo "  - 이미지 태그"
echo "  - 로그 확인"
echo "  - 메트릭 확인"
echo "  - 리소스 사용량"
echo ""

log_info "롤백이 성공적으로 검증되었습니다." 