
# zCICD 가이드 문서

## 목차
1. 개요
2. CI/CD 파이프라인 구성
3. 배포 프로세스
4. 환경 설정
5. 모니터링 및 알림
6. 문제 해결

## 1. 개요

### 1.1 CI/CD의 기본 개념

**CI (Continuous Integration)**
- 개발자들이 코드를 자주 병합하고 자동화된 빌드 및 테스트를 통해 문제를 조기에 발견하는 개발 방법론
- 주요 목표: 코드 품질 향상, 버그 조기 발견, 배포 시간 단축

**CD (Continuous Deployment/Delivery)**
- Continuous Delivery: 언제든지 프로덕션에 배포할 수 있는 상태로 유지
- Continuous Deployment: 모든 변경사항을 자동으로 프로덕션에 배포

### 1.2 프로젝트에서의 CI/CD 활용 방안

**현재 프로젝트 구조**
```
skax-eatool-eplaton-master/
├── kji-java/          # KJI 서비스
├── ksa-java/          # KSA 서비스  
├── mba-java/          # MBA 서비스
├── mbc-java/          # MBC 서비스
├── mbc01-java/        # MBC01 서비스
└── spring-cloud-apigateway-service/  # API Gateway
```

**CI/CD 적용 전략**
- 마이크로서비스 아키텍처에 맞는 개별 서비스별 파이프라인 구성
- 공통 라이브러리 (kji-lib, ksa-lib, mbc01-lib) 관리
- 컨테이너 기반 배포 (Docker, Kubernetes)

### 1.3 주요 도구 및 기술 스택

**빌드 도구**
- Maven: Java 프로젝트 빌드 및 의존성 관리
- Gradle: 대안 빌드 도구 (필요시)

**CI/CD 플랫폼**
- Jenkins: 오픈소스 CI/CD 서버
- GitLab CI/CD: GitLab 통합 CI/CD
- GitHub Actions: GitHub 통합 CI/CD

**컨테이너 및 오케스트레이션**
- Docker: 컨테이너화
- Kubernetes: 컨테이너 오케스트레이션
- Helm: Kubernetes 패키지 관리

**모니터링 및 로깅**
- Prometheus: 메트릭 수집
- Grafana: 시각화
- ELK Stack: 로그 관리

## 2. CI/CD 파이프라인 구성

### 2.1 빌드 자동화

**Maven 빌드 스크립트 예시**
```yaml
# .gitlab-ci.yml 또는 Jenkinsfile
stages:
  - build
  - test
  - package
  - deploy

build:
  stage: build
  script:
    - mvn clean compile
  artifacts:
    paths:
      - target/
    expire_in: 1 hour
```

**멀티 모듈 빌드**
```bash
# 전체 프로젝트 빌드
mvn clean install -DskipTests

# 특정 모듈만 빌드
mvn clean install -pl mbc-java -am
```

### 2.2 테스트 자동화

**단위 테스트**
```java
// JUnit 5 예시
@Test
@DisplayName("사용자 생성 테스트")
void testCreateUser() {
    User user = new User("test@example.com", "password");
    User savedUser = userService.createUser(user);
    
    assertThat(savedUser.getId()).isNotNull();
    assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
}
```

**통합 테스트**
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class UserIntegrationTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    void testUserCreationWithDatabase() {
        // 데이터베이스와 연동된 통합 테스트
    }
}
```

**테스트 실행 스크립트**
```yaml
test:
  stage: test
  script:
    - mvn test
    - mvn verify
  coverage: '/Total.*?([0-9]{1,3})%/'
  artifacts:
    reports:
      junit: target/surefire-reports/TEST-*.xml
```

### 2.3 코드 품질 검사

**SonarQube 설정**
```properties
# sonar-project.properties
sonar.projectKey=skax-eatool-eplaton
sonar.projectName=Skax EA Tool Eplaton
sonar.projectVersion=1.0.0

sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes
sonar.java.test.binaries=target/test-classes

sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

**코드 품질 검사 파이프라인**
```yaml
code-quality:
  stage: test
  script:
    - mvn sonar:sonar
      -Dsonar.host.url=$SONAR_HOST_URL
      -Dsonar.login=$SONAR_TOKEN
  only:
    - merge_requests
    - main
```

### 2.4 보안 검사

**OWASP Dependency Check**
```yaml
security-scan:
  stage: test
  script:
    - mvn org.owasp:dependency-check-maven:check
  artifacts:
    reports:
      dependency_scanning: target/dependency-check-report.xml
```

**Container Security Scanning**
```yaml
container-scan:
  stage: test
  script:
    - docker build -t $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA .
    - trivy image $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA
```

## 3. 배포 프로세스

### 3.1 개발 환경 배포

**개발 환경 특징**
- 빠른 배포 및 테스트
- 최소한의 리소스 사용
- 디버깅 용이성

**개발 환경 배포 스크립트**
```yaml
deploy-dev:
  stage: deploy
  environment:
    name: development
    url: https://dev.skax-eatool.com
  script:
    - kubectl apply -f k8s/dev/
    - kubectl rollout status deployment/mbc-java -n dev
  only:
    - develop
```

### 3.2 스테이징 환경 배포

**스테이징 환경 특징**
- 프로덕션과 유사한 환경
- 최종 테스트 및 검증
- 사용자 승인 프로세스

**스테이징 배포 파이프라인**
```yaml
deploy-staging:
  stage: deploy
  environment:
    name: staging
    url: https://staging.skax-eatool.com
  script:
    - kubectl apply -f k8s/staging/
    - kubectl rollout status deployment/mbc-java -n staging
  when: manual
  only:
    - main
```

### 3.3 프로덕션 환경 배포

**프로덕션 배포 전략**
- Blue-Green 배포
- Rolling Update
- Canary 배포

**프로덕션 배포 스크립트**
```yaml
deploy-production:
  stage: deploy
  environment:
    name: production
    url: https://skax-eatool.com
  script:
    - kubectl apply -f k8s/production/
    - kubectl rollout status deployment/mbc-java -n production
  when: manual
  only:
    - main
```

### 3.4 롤백 절차

**자동 롤백 조건**
- 헬스체크 실패
- 에러율 임계값 초과
- 응답 시간 임계값 초과

**롤백 스크립트**
```bash
#!/bin/bash
# rollback.sh
DEPLOYMENT_NAME=$1
NAMESPACE=$2
PREVIOUS_REVISION=$3

kubectl rollout undo deployment/$DEPLOYMENT_NAME -n $NAMESPACE --to-revision=$PREVIOUS_REVISION
kubectl rollout status deployment/$DEPLOYMENT_NAME -n $NAMESPACE
```

## 4. 환경 설정

### 4.1 개발 환경 구성

**개발 환경 설정 파일**
```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password: 
  
logging:
  level:
    com.skax.eatool: DEBUG
    org.springframework.web: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

**개발용 Docker Compose**
```yaml
# docker-compose.dev.yml
version: '3.8'
services:
  mbc-java:
    build: ./mbc-java
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    volumes:
      - ./logs:/app/logs
```

### 4.2 테스트 환경 구성

**테스트 환경 설정**
```yaml
# application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    username: sa
    password: 
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

logging:
  level:
    com.skax.eatool: INFO
```

### 4.3 운영 환경 구성

**운영 환경 설정**
```yaml
# application-prod.yml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

logging:
  level:
    com.skax.eatool: WARN
  file:
    name: /app/logs/application.log
```

### 4.4 환경별 설정 관리

**ConfigMap 예시**
```yaml
# k8s/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: mbc-java-config
data:
  application.yml: |
    spring:
      profiles:
        active: ${SPRING_PROFILES_ACTIVE}
    logging:
      level:
        com.skax.eatool: ${LOG_LEVEL}
```

**Secret 예시**
```yaml
# k8s/secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: mbc-java-secret
type: Opaque
data:
  db-password: <base64-encoded-password>
  api-key: <base64-encoded-api-key>
```

## 5. 모니터링 및 알림

### 5.1 파이프라인 모니터링

**Jenkins 파이프라인 모니터링**
```groovy
// Jenkinsfile
pipeline {
    agent any
    
    options {
        timeout(time: 1, unit: 'HOURS')
        timestamps()
    }
    
    post {
        always {
            // 빌드 결과 알림
            notifyBuildResult()
        }
        success {
            // 성공 알림
            notifySuccess()
        }
        failure {
            // 실패 알림
            notifyFailure()
        }
    }
}
```

### 5.2 배포 상태 모니터링

**Kubernetes 헬스체크**
```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mbc-java
spec:
  template:
    spec:
      containers:
      - name: mbc-java
        image: mbc-java:latest
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
```

### 5.3 알림 설정

**Slack 알림 설정**
```yaml
# .gitlab-ci.yml
notify-slack:
  stage: notify
  script:
    - |
      curl -X POST -H 'Content-type: application/json' \
      --data '{
        "text": "배포 완료: $CI_COMMIT_REF_NAME -> $CI_ENVIRONMENT_NAME"
      }' \
      $SLACK_WEBHOOK_URL
  when: on_success
```

**이메일 알림**
```yaml
notify-email:
  stage: notify
  script:
    - echo "배포 완료 알림" | mail -s "배포 완료" admin@skax.com
  when: on_success
```

### 5.4 로그 관리

**ELK Stack 구성**
```yaml
# docker-compose.logging.yml
version: '3.8'
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.0
    environment:
      - discovery.type=single-node
    ports:
      - "9200:9200"
  
  logstash:
    image: docker.elastic.co/logstash/logstash:7.17.0
    volumes:
      - ./logstash.conf:/usr/share/logstash/pipeline/logstash.conf
    ports:
      - "5044:5044"
  
  kibana:
    image: docker.elastic.co/kibana/kibana:7.17.0
    ports:
      - "5601:5601"
```

## 6. 문제 해결

### 6.1 일반적인 문제 해결 방법

**빌드 실패 문제**
```bash
# Maven 의존성 문제 해결
mvn dependency:resolve
mvn dependency:tree

# 캐시 클리어
mvn clean
rm -rf ~/.m2/repository
```

**Docker 빌드 문제**
```bash
# Docker 캐시 클리어
docker system prune -a

# 멀티스테이지 빌드 최적화
docker build --no-cache -t app:latest .
```

**Kubernetes 배포 문제**
```bash
# 파드 상태 확인
kubectl get pods -n <namespace>
kubectl describe pod <pod-name> -n <namespace>

# 로그 확인
kubectl logs <pod-name> -n <namespace>
kubectl logs <pod-name> -n <namespace> --previous
```

### 6.2 트러블슈팅 가이드

**메모리 부족 문제**
```yaml
# JVM 메모리 설정
resources:
  requests:
    memory: "512Mi"
    cpu: "250m"
  limits:
    memory: "1Gi"
    cpu: "500m"
```

**데이터베이스 연결 문제**
```bash
# 데이터베이스 연결 테스트
kubectl exec -it <pod-name> -n <namespace> -- nc -zv <db-host> <db-port>

# 데이터베이스 상태 확인
kubectl exec -it <pod-name> -n <namespace> -- mysql -h <db-host> -u <user> -p
```

### 6.3 긴급 상황 대응 절차

**서비스 다운 대응**
1. 즉시 롤백 실행
2. 로그 분석 및 원인 파악
3. 임시 조치 (스케일링, 리소스 증설)
4. 근본 원인 분석 및 수정
5. 재배포 및 검증

**데이터 손실 대응**
1. 백업 데이터 확인
2. 데이터 복구 절차 실행
3. 서비스 정상화 확인
4. 데이터 무결성 검증
5. 재발 방지 대책 수립

**보안 사고 대응**
1. 영향 범위 파악
2. 취약점 차단
3. 로그 분석 및 침입 경로 파악
4. 시스템 복구
5. 보안 강화 조치

---

## 부록

### A. 유용한 명령어 모음
```bash
# Kubernetes 명령어
kubectl get all -n <namespace>
kubectl scale deployment <deployment> --replicas=3 -n <namespace>
kubectl port-forward <pod> 8080:8080 -n <namespace>

# Docker 명령어
docker ps -a
docker logs <container>
docker exec -it <container> /bin/bash

# Maven 명령어
mvn clean install -DskipTests
mvn spring-boot:run -Dspring.profiles.active=dev
```

### B. 설정 파일 템플릿
- Jenkinsfile 템플릿
- GitLab CI/CD 템플릿
- Kubernetes 매니페스트 템플릿
- Docker Compose 템플릿

### C. 모니터링 대시보드
- Grafana 대시보드 설정
- Prometheus 알림 규칙
- 로그 분석 쿼리 예시
