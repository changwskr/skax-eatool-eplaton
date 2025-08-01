#!/bin/bash

# SKAX EATool Eplaton 롤백 스크립트
# 사용법: ./rollback.sh <environment> <deployment>

set -e

ENVIRONMENT=$1
DEPLOYMENT=$2
NAMESPACE="skax-eatool"

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

log_info "롤백 시작: 환경=$ENVIRONMENT, 배포=$DEPLOYMENT"

# 배포 히스토리 확인
if [ "$DEPLOYMENT" = "all" ]; then
    MODULES=("kji" "ksa" "mba" "mbc" "mbc01" "apigateway")
else
    MODULES=("$DEPLOYMENT")
fi

for module in "${MODULES[@]}"; do
    log_info "$module 모듈 롤백 중..."
    
    # 현재 배포 상태 확인
    CURRENT_REVISION=$(kubectl rollout history deployment/${module}-deployment -n $NAMESPACE --output=jsonpath='{.items[0].revision}')
    PREVIOUS_REVISION=$((CURRENT_REVISION - 1))
    
    if [ $PREVIOUS_REVISION -lt 1 ]; then
        log_warn "$module 모듈의 이전 버전이 없습니다. 롤백을 건너뜁니다."
        continue
    fi
    
    # 롤백 실행
    log_info "$module 모듈을 이전 버전($PREVIOUS_REVISION)으로 롤백 중..."
    kubectl rollout undo deployment/${module}-deployment -n $NAMESPACE --to-revision=$PREVIOUS_REVISION
    
    # 롤백 상태 확인
    log_info "$module 롤백 상태 확인 중..."
    kubectl rollout status deployment/${module}-deployment -n $NAMESPACE --timeout=300s
    
    if [ $? -eq 0 ]; then
        log_info "$module 롤백 완료"
    else
        log_error "$module 롤백 실패"
        exit 1
    fi
done

log_info "롤백 완료!"
log_info "환경: $ENVIRONMENT"
log_info "배포: $DEPLOYMENT"

# 롤백 후 상태 출력
echo ""
echo "=== 롤백 후 상태 ==="
kubectl get pods -n $NAMESPACE
echo ""

# 배포 히스토리 출력
echo "=== 배포 히스토리 ==="
for module in "${MODULES[@]}"; do
    echo "$module:"
    kubectl rollout history deployment/${module}-deployment -n $NAMESPACE
    echo ""
done 