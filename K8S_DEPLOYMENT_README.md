# SKAX EATool Eplaton Kubernetes 배포 가이드

## 개요

이 문서는 SKAX EATool Eplaton 애플리케이션을 Kubernetes 환경에 배포하는 방법을 설명합니다.

## 모듈 구성

### 1. KJI (Kesa Java Interface)
- **포트**: 8080
- **컨텍스트 경로**: `/kji`
- **기능**: Kesa Java 인터페이스 서비스

### 2. KSA (Kesa Service Architecture)
- **포트**: 8080
- **컨텍스트 경로**: `/ksa`
- **기능**: Kesa 서비스 아키텍처

### 3. MBA (Master Business Application)
- **포트**: 8084
- **컨텍스트 경로**: `/mba`
- **기능**: 마스터 비즈니스 애플리케이션

### 4. MBC (Master Business Component)
- **포트**: 8085
- **컨텍스트 경로**: `/mbc`
- **기능**: 마스터 비즈니스 컴포넌트

### 5. MBC01 (Master Business Component 01)
- **포트**: 8080
- **컨텍스트 경로**: `/mbc01`
- **기능**: 마스터 비즈니스 컴포넌트 01

### 6. API Gateway
- **포트**: 8000 (내부), 80 (외부)
- **기능**: Spring Cloud Gateway 기반 API 게이트웨이

## 파일 구조

```
skax-eatool-eplaton-master/
├── k8s-namespace.yaml              # 공통 Namespace
├── k8s-ingress.yaml                # 공통 Ingress
├── k8s-deploy-all.bat              # 전체 배포 스크립트
├── k8s-delete-all.bat              # 전체 삭제 스크립트
├── k8s-status.bat                  # 상태 확인 스크립트
├── kji-java/k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   └── service.yaml
├── ksa-java/k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   └── service.yaml
├── mba-java/k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   └── service.yaml
├── mbc-java/k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   └── service.yaml
├── mbc01-java/k8s/
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── deployment.yaml
│   └── service.yaml
└── spring-cloud-apigateway-service/k8s/
    ├── configmap.yaml
    ├── secret.yaml
    ├── deployment.yaml
    └── service.yaml
```

## 사전 요구사항

1. **Kubernetes 클러스터**
   - Minikube, Docker Desktop, 또는 클라우드 K8s 서비스
   - kubectl CLI 도구 설치

2. **Docker 이미지**
   - 각 모듈별 Docker 이미지가 빌드되어 있어야 함
   - 이미지 태그: `skax-eatool/{module}:latest`

3. **Ingress Controller**
   - nginx-ingress-controller 설치 필요

## 배포 방법

### 1. 전체 배포
```bash
# Windows
k8s-deploy-all.bat

# Linux/Mac
./k8s-deploy-all.sh
```

### 2. 개별 모듈 배포
```bash
# KJI 모듈 배포
kubectl apply -f kji-java/k8s/

# KSA 모듈 배포
kubectl apply -f ksa-java/k8s/

# MBA 모듈 배포
kubectl apply -f mba-java/k8s/

# MBC 모듈 배포
kubectl apply -f mbc-java/k8s/

# MBC01 모듈 배포
kubectl apply -f mbc01-java/k8s/

# API Gateway 배포
kubectl apply -f spring-cloud-apigateway-service/k8s/
```

## 상태 확인

### 1. 전체 상태 확인
```bash
k8s-status.bat
```

### 2. 개별 확인 명령어
```bash
# Namespace 확인
kubectl get namespace skax-eatool

# 모든 리소스 확인
kubectl get all -n skax-eatool

# Pod 상태 확인
kubectl get pods -n skax-eatool

# Service 상태 확인
kubectl get services -n skax-eatool

# Ingress 상태 확인
kubectl get ingress -n skax-eatool
```

## 접속 방법

### 1. Ingress를 통한 접속
- **호스트**: `skax-eatool.local`
- **경로**:
  - `/kji` → KJI 서비스
  - `/ksa` → KSA 서비스
  - `/mba` → MBA 서비스
  - `/mbc` → MBC 서비스
  - `/mbc01` → MBC01 서비스
  - `/` → API Gateway

### 2. Port Forward를 통한 접속
```bash
# KJI 서비스
kubectl port-forward -n skax-eatool svc/kji-service 8080:8080

# KSA 서비스
kubectl port-forward -n skax-eatool svc/ksa-service 8081:8080

# MBA 서비스
kubectl port-forward -n skax-eatool svc/mba-service 8084:8084

# MBC 서비스
kubectl port-forward -n skax-eatool svc/mbc-service 8085:8085

# MBC01 서비스
kubectl port-forward -n skax-eatool svc/mbc01-service 8086:8080

# API Gateway
kubectl port-forward -n skax-eatool svc/apigateway-service 8000:80
```

## 로그 확인

```bash
# 특정 Pod의 로그 확인
kubectl logs -n skax-eatool <pod-name>

# Pod 로그 실시간 확인
kubectl logs -f -n skax-eatool <pod-name>

# 모든 Pod의 로그 확인
kubectl logs -n skax-eatool -l app=kji
kubectl logs -n skax-eatool -l app=ksa
kubectl logs -n skax-eatool -l app=mba
kubectl logs -n skax-eatool -l app=mbc
kubectl logs -n skax-eatool -l app=mbc01
kubectl logs -n skax-eatool -l app=apigateway
```

## 삭제 방법

### 1. 전체 삭제
```bash
k8s-delete-all.bat
```

### 2. 개별 삭제
```bash
# 특정 모듈 삭제
kubectl delete -f kji-java/k8s/

# Namespace 전체 삭제
kubectl delete namespace skax-eatool
```

## 설정 변경

### 1. ConfigMap 수정
```bash
# ConfigMap 편집
kubectl edit configmap kji-config -n skax-eatool

# Pod 재시작 (설정 적용)
kubectl rollout restart deployment kji-deployment -n skax-eatool
```

### 2. Secret 수정
```bash
# Secret 편집
kubectl edit secret kji-secret -n skax-eatool

# Pod 재시작 (설정 적용)
kubectl rollout restart deployment kji-deployment -n skax-eatool
```

## 문제 해결

### 1. Pod가 Running 상태가 아닌 경우
```bash
# Pod 상세 정보 확인
kubectl describe pod <pod-name> -n skax-eatool

# Pod 로그 확인
kubectl logs <pod-name> -n skax-eatool
```

### 2. Service 연결 문제
```bash
# Service 상세 정보 확인
kubectl describe service <service-name> -n skax-eatool

# Endpoint 확인
kubectl get endpoints -n skax-eatool
```

### 3. Ingress 문제
```bash
# Ingress 상세 정보 확인
kubectl describe ingress skax-eatool-ingress -n skax-eatool

# Ingress Controller 로그 확인
kubectl logs -n ingress-nginx deployment/ingress-nginx-controller
```

## 리소스 요구사항

### 1. 최소 요구사항
- **CPU**: 4 cores
- **메모리**: 8GB RAM
- **스토리지**: 20GB

### 2. 권장 요구사항
- **CPU**: 8 cores
- **메모리**: 16GB RAM
- **스토리지**: 50GB

## 보안 고려사항

1. **Secret 관리**
   - 프로덕션 환경에서는 Kubernetes Secret 대신 외부 Secret 관리 도구 사용 권장
   - 예: HashiCorp Vault, AWS Secrets Manager

2. **네트워크 정책**
   - 필요한 경우 NetworkPolicy를 설정하여 Pod 간 통신 제한

3. **RBAC 설정**
   - 필요한 경우 Role과 RoleBinding을 설정하여 권한 관리

## 모니터링

### 1. Prometheus 메트릭
- 각 서비스의 `/actuator/prometheus` 엔드포인트에서 메트릭 수집 가능

### 2. Health Check
- 각 서비스의 `/actuator/health` 엔드포인트로 상태 확인 가능

### 3. 로그 수집
- ELK Stack 또는 Fluentd를 사용한 중앙화된 로그 수집 권장 