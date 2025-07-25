#!/bin/bash

# SKAX EATool Eplaton 배포 스크립트
# 사용법: ./deploy.sh <environment> <commit-sha>

set -e

ENVIRONMENT=$1
COMMIT_SHA=$2
REGISTRY="ghcr.io"
IMAGE_NAME="skax-eatool-eplaton"
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
if [ -z "$ENVIRONMENT" ] || [ -z "$COMMIT_SHA" ]; then
    log_error "사용법: $0 <environment> <commit-sha>"
    exit 1
fi

# 환경별 설정
case $ENVIRONMENT in
    "development")
        REPLICAS=1
        RESOURCES="requests.memory=256Mi,requests.cpu=100m,limits.memory=512Mi,limits.cpu=200m"
        ;;
    "staging")
        REPLICAS=2
        RESOURCES="requests.memory=512Mi,requests.cpu=200m,limits.memory=1Gi,limits.cpu=500m"
        ;;
    "production")
        REPLICAS=3
        RESOURCES="requests.memory=1Gi,requests.cpu=500m,limits.memory=2Gi,limits.cpu=1000m"
        ;;
    *)
        log_error "지원하지 않는 환경: $ENVIRONMENT"
        exit 1
        ;;
esac

log_info "배포 시작: 환경=$ENVIRONMENT, 커밋=$COMMIT_SHA"

# 네임스페이스 확인 및 생성
if ! kubectl get namespace $NAMESPACE >/dev/null 2>&1; then
    log_info "네임스페이스 $NAMESPACE 생성 중..."
    kubectl create namespace $NAMESPACE
fi

# 모듈별 배포
MODULES=("kji" "ksa" "mba" "mbc" "mbc01" "apigateway")

for module in "${MODULES[@]}"; do
    log_info "$module 모듈 배포 중..."
    
    # ConfigMap 업데이트
    kubectl create configmap ${module}-config \
        --from-literal=SPRING_PROFILES_ACTIVE=$ENVIRONMENT \
        --from-literal=IMAGE_TAG=$COMMIT_SHA \
        -n $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -
    
    # Secret 업데이트 (필요한 경우)
    if [ -f "configs/${module}-secret.yaml" ]; then
        kubectl apply -f configs/${module}-secret.yaml -n $NAMESPACE
    fi
    
    # Deployment 업데이트
    kubectl set image deployment/${module}-deployment \
        ${module}-app=${REGISTRY}/${IMAGE_NAME}/${module}:${COMMIT_SHA} \
        -n $NAMESPACE
    
    # 리소스 제한 업데이트
    kubectl patch deployment ${module}-deployment \
        -p "{\"spec\":{\"replicas\":$REPLICAS,\"template\":{\"spec\":{\"containers\":[{\"name\":\"${module}-app\",\"resources\":{\"requests\":{\"memory\":\"$(echo $RESOURCES | cut -d',' -f1 | cut -d'=' -f2)\",\"cpu\":\"$(echo $RESOURCES | cut -d',' -f2 | cut -d'=' -f2)\"},\"limits\":{\"memory\":\"$(echo $RESOURCES | cut -d',' -f3 | cut -d'=' -f2)\",\"cpu\":\"$(echo $RESOURCES | cut -d',' -f4 | cut -d'=' -f2)\"}}}}]}}}}" \
        -n $NAMESPACE
    
    # 배포 상태 확인
    log_info "$module 배포 상태 확인 중..."
    kubectl rollout status deployment/${module}-deployment -n $NAMESPACE --timeout=300s
    
    if [ $? -eq 0 ]; then
        log_info "$module 배포 완료"
    else
        log_error "$module 배포 실패"
        exit 1
    fi
done

# Ingress 업데이트
log_info "Ingress 업데이트 중..."
kubectl apply -f templates/ingress.yaml -n $NAMESPACE

# 서비스 상태 확인
log_info "모든 서비스 상태 확인 중..."
for module in "${MODULES[@]}"; do
    kubectl wait --for=condition=ready pod -l app=$module -n $NAMESPACE --timeout=300s
done

log_info "배포 완료!"
log_info "환경: $ENVIRONMENT"
log_info "커밋: $COMMIT_SHA"
log_info "네임스페이스: $NAMESPACE"

# 배포 정보 출력
echo ""
echo "=== 배포 정보 ==="
echo "환경: $ENVIRONMENT"
echo "커밋: $COMMIT_SHA"
echo "네임스페이스: $NAMESPACE"
echo "레플리카 수: $REPLICAS"
echo ""

# 서비스 엔드포인트 출력
echo "=== 서비스 엔드포인트 ==="
kubectl get services -n $NAMESPACE
echo ""

# Pod 상태 출력
echo "=== Pod 상태 ==="
kubectl get pods -n $NAMESPACE
echo "" 