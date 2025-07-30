# SKAX EATool Eplaton CI/CD 프로젝트

이 프로젝트는 SKAX EATool Eplaton 애플리케이션의 CI/CD 파이프라인을 관리하는 저장소입니다.

## 📁 프로젝트 구조

```
spring-cicd/
├── .github/
│   └── workflows/
│       ├── ci-cd-pipeline.yml      # 메인 CI/CD 파이프라인
│       ├── rollback.yml            # 롤백 워크플로우
│       └── security-scan.yml       # 보안 스캔 워크플로우
├── scripts/
│   ├── deploy.sh                   # 배포 스크립트
│   ├── rollback.sh                 # 롤백 스크립트
│   ├── smoke-tests.sh              # 스모크 테스트 스크립트
│   ├── integration-tests.sh        # 통합 테스트 스크립트
│   ├── production-tests.sh         # 프로덕션 테스트 스크립트
│   └── verify-rollback.sh          # 롤백 검증 스크립트
├── templates/
│   └── ingress.yaml                # Ingress 템플릿
├── configs/
│   └── sonar-project.properties    # SonarQube 설정
└── README.md                       # 이 파일
```

## 🚀 CI/CD 파이프라인 개요

### 1. 메인 파이프라인 (`ci-cd-pipeline.yml`)

#### 트리거 조건
- `main`, `develop` 브랜치에 푸시
- `main` 브랜치로 Pull Request
- Release 생성
- 수동 실행 (workflow_dispatch)

#### 주요 단계
1. **코드 품질 검사** - SonarQube 분석
2. **빌드 및 테스트** - 각 모듈별 빌드 및 테스트
3. **보안 스캔** - Trivy를 통한 취약점 검사
4. **Docker 이미지 빌드** - 멀티 플랫폼 이미지 빌드
5. **개발 환경 배포** - develop 브랜치
6. **스테이징 환경 배포** - main 브랜치
7. **프로덕션 환경 배포** - Release 생성 시
8. **알림** - Slack을 통한 배포 상태 알림

### 2. 롤백 워크플로우 (`rollback.yml`)

#### 기능
- 수동 롤백 실행
- 환경별 롤백 지원
- 개별 서비스 또는 전체 롤백
- 롤백 검증 및 알림

### 3. 보안 스캔 워크플로우 (`security-scan.yml`)

#### 기능
- 매일 자동 보안 스캔
- OWASP Dependency Check
- Trivy 취약점 스캔
- Snyk 보안 분석
- 컨테이너 이미지 스캔

## 🔧 환경 설정

### GitHub Secrets 설정

```bash
# Kubernetes 클러스터 접근 정보
KUBE_CONFIG_DEV=base64_encoded_kubeconfig_dev
KUBE_CONFIG_STAGING=base64_encoded_kubeconfig_staging
KUBE_CONFIG_PROD=base64_encoded_kubeconfig_prod

# 알림 설정
SLACK_WEBHOOK=https://hooks.slack.com/services/...
TEAMS_WEBHOOK=https://outlook.office.com/webhook/...

# 보안 스캔
SONAR_TOKEN=your_sonarqube_token
SNYK_TOKEN=your_snyk_token

# 외부 서비스 인증
DOCKER_REGISTRY_USERNAME=username
DOCKER_REGISTRY_PASSWORD=password
```

### 환경별 설정

#### 개발 환경 (Development)
- **레플리카 수**: 1
- **리소스**: 256Mi 메모리, 100m CPU
- **자동 배포**: develop 브랜치 푸시 시

#### 스테이징 환경 (Staging)
- **레플리카 수**: 2
- **리소스**: 512Mi 메모리, 200m CPU
- **자동 배포**: main 브랜치 푸시 시

#### 프로덕션 환경 (Production)
- **레플리카 수**: 3
- **리소스**: 1Gi 메모리, 500m CPU
- **자동 배포**: Release 생성 시

## 📋 사용 방법

### 1. 자동 배포

```bash
# 개발 환경 배포
git push origin develop

# 스테이징 환경 배포
git push origin main

# 프로덕션 환경 배포
git tag v1.0.0
git push origin v1.0.0
```

### 2. 수동 배포

GitHub Actions 탭에서 `ci-cd-pipeline` 워크플로우를 선택하고 수동 실행:

1. **Environment** 선택
2. **Run workflow** 클릭

### 3. 롤백 실행

GitHub Actions 탭에서 `rollback` 워크플로우를 선택:

1. **Environment** 선택
2. **Deployment** 선택 (개별 서비스 또는 all)
3. **Run workflow** 클릭

## 🧪 테스트

### 스모크 테스트
```bash
./scripts/smoke-tests.sh development
./scripts/smoke-tests.sh staging
./scripts/smoke-tests.sh production
```

### 통합 테스트
```bash
./scripts/integration-tests.sh staging
```

### 프로덕션 테스트
```bash
./scripts/production-tests.sh
```

## 📊 모니터링

### Health Check 엔드포인트
- `/actuator/health` - 서비스 상태
- `/actuator/info` - 서비스 정보
- `/actuator/metrics` - 메트릭 정보

### 로그 확인
```bash
# 특정 서비스 로그
kubectl logs -f deployment/kji-deployment -n skax-eatool

# 모든 서비스 로그
kubectl logs -f -l app=kji -n skax-eatool
```

## 🔒 보안

### 보안 스캔 결과
- GitHub Security 탭에서 확인
- SARIF 형식으로 결과 업로드
- 자동 알림 설정

### 취약점 관리
- CVSS 7.0 이상 취약점 시 빌드 실패
- 정기적인 보안 스캔 실행
- 컨테이너 이미지 보안 검사

## 🚨 문제 해결

### 일반적인 문제들

#### 1. 배포 실패
```bash
# Pod 상태 확인
kubectl get pods -n skax-eatool

# Pod 상세 정보 확인
kubectl describe pod <pod-name> -n skax-eatool

# 로그 확인
kubectl logs <pod-name> -n skax-eatool
```

#### 2. 이미지 풀 실패
```bash
# 이미지 태그 확인
kubectl get deployment -n skax-eatool -o yaml | grep image:

# 이미지 빌드 상태 확인
docker images | grep skax-eatool
```

#### 3. 서비스 연결 문제
```bash
# 서비스 상태 확인
kubectl get services -n skax-eatool

# 엔드포인트 확인
kubectl get endpoints -n skax-eatool
```

## 📈 성능 최적화

### 빌드 최적화
- Maven 캐시 활용
- Docker 레이어 캐시 활용
- 병렬 빌드 실행

### 배포 최적화
- Rolling Update 전략
- Health Check 활용
- 리소스 제한 설정

## 🤝 기여 가이드

### 워크플로우 수정
1. `.github/workflows/` 디렉토리에서 수정
2. 테스트 환경에서 검증
3. Pull Request 생성

### 스크립트 수정
1. `scripts/` 디렉토리에서 수정
2. 로컬에서 테스트
3. 문서 업데이트

## 📞 지원

### 연락처
- **개발팀**: dev-team@company.com
- **운영팀**: ops-team@company.com
- **보안팀**: security-team@company.com

### 문서
- [Kubernetes 배포 가이드](../K8S_DEPLOYMENT_README.md)
- [Docker 배포 가이드](../DOCKER_DEPLOYMENT.md)
- [빌드 트러블슈팅](../BUILD_TROUBLESHOOTING.md) 