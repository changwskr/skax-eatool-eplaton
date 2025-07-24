# SKAX EA Tool Docker 배포 가이드

## 개요

이 문서는 SKAX EA Tool의 MBC, MBA, Gateway 서비스를 Docker 컨테이너로 배포하는 방법을 설명합니다.

## 사전 요구사항

- Docker Desktop 설치
- Maven 3.6+ 설치
- Java 11 설치

## 프로젝트 구조

```
skax-eatool-eplaton-master/
├── mbc-java/                    # MBC 서비스
│   ├── Dockerfile
│   ├── src/main/resources/application-docker.yml
│   └── pom.xml
├── mba-java/                    # MBA 서비스
│   ├── Dockerfile
│   ├── src/main/resources/application-docker.yml
│   └── pom.xml
├── ksa-java/                    # KSA 서비스
│   ├── Dockerfile
│   ├── src/main/resources/application-docker.yml
│   └── pom.xml
├── kji-java/                    # KJI 서비스
│   ├── Dockerfile
│   ├── src/main/resources/application-docker.yml
│   └── pom.xml
├── spring-cloud-apigateway-service/  # API Gateway
│   ├── Dockerfile
│   ├── src/main/resources/application-docker.yml
│   └── pom.xml
├── docker-compose.yml           # Docker Compose 설정
├── docker-build.bat             # 빌드 스크립트
├── docker-run.bat               # 실행 스크립트
└── .dockerignore                # Docker 제외 파일
```

## 배포 단계

### 1. 프로젝트 빌드

```bash
# 전체 프로젝트 빌드 및 Docker 이미지 생성
./docker-build.bat
```

또는 수동으로:

```bash
# Maven 빌드
cd mbc-java && mvn clean package -DskipTests && cd ..
cd mba-java && mvn clean package -DskipTests && cd ..
cd ksa-java && mvn clean package -DskipTests && cd ..
cd kji-java && mvn clean package -DskipTests && cd ..
cd spring-cloud-apigateway-service && mvn clean package -DskipTests && cd ..

# Docker 이미지 빌드
docker build -t skax-mbc-service:latest ./mbc-java
docker build -t skax-mba-service:latest ./mba-java
docker build -t skax-ksa-service:latest ./ksa-java
docker build -t skax-kji-service:latest ./kji-java
docker build -t skax-api-gateway:latest ./spring-cloud-apigateway-service
```

### 2. 서비스 실행

```bash
# Docker Compose로 모든 서비스 실행
./docker-run.bat
```

또는 수동으로:

```bash
# 백그라운드에서 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f
```

### 3. 서비스 상태 확인

```bash
# 컨테이너 상태 확인
docker-compose ps

# 서비스별 로그 확인
docker-compose logs mbc-service
docker-compose logs mba-service
docker-compose logs api-gateway
```

## 서비스 접속 정보

| 서비스 | 포트 | URL | 설명 |
|--------|------|-----|------|
| API Gateway | 8000 | http://localhost:8000 | 메인 진입점 |
| MBC Service | 8085 | http://localhost:8085 | 마스터 비즈니스 컴포넌트 |
| MBA Service | 8084 | http://localhost:8084 | 마스터 비즈니스 애플리케이션 |
| KSA Service | 8083 | http://localhost:8083 | 지식 서비스 애플리케이션 |
| KJI Service | 8082 | http://localhost:8082 | 지식 통합 애플리케이션 |

## 주요 엔드포인트

### API Gateway (8000)
- 홈: http://localhost:8000
- MBC: http://localhost:8000/mbc/home
- MBA: http://localhost:8000/mba/home
- KSA: http://localhost:8000/ksa/
- KJI: http://localhost:8000/kji/

### MBC Service (8085)
- 홈: http://localhost:8085/home
- 관리: http://localhost:8085/web/admin/home
- Swagger: http://localhost:8085/swagger-ui/index.html
- Health: http://localhost:8085/actuator/health

### MBA Service (8084)
- 홈: http://localhost:8084/home
- 로그인: http://localhost:8084/auth/login
- Swagger: http://localhost:8084/swagger-ui/index.html
- Health: http://localhost:8084/actuator/health

## 환경 설정

### Docker 환경 변수

각 서비스는 `application-docker.yml` 설정을 사용합니다:

- **데이터베이스**: H2 인메모리 데이터베이스
- **포트**: 각 서비스별 고유 포트
- **로깅**: Docker 환경에 최적화된 로깅 설정
- **Actuator**: 헬스체크 및 모니터링 엔드포인트 활성화

### 메모리 설정

기본 메모리 설정:
- 최소: 512MB
- 최대: 1024MB

필요시 `docker-compose.yml`에서 조정 가능:

```yaml
environment:
  - JAVA_OPTS=-Xms1024m -Xmx2048m
```

## 문제 해결

### 1. 포트 충돌

포트가 이미 사용 중인 경우:

```bash
# 사용 중인 포트 확인
netstat -ano | findstr :8000

# Docker Compose 포트 변경
# docker-compose.yml에서 ports 섹션 수정
```

### 2. 메모리 부족

```bash
# Docker Desktop 메모리 증가
# Docker Desktop > Settings > Resources > Memory

# 컨테이너 메모리 제한 확인
docker stats
```

### 3. 빌드 실패

```bash
# Maven 캐시 정리
mvn clean

# Docker 이미지 정리
docker system prune -a

# 다시 빌드
./docker-build.bat
```

### 4. 서비스 연결 실패

```bash
# 네트워크 확인
docker network ls
docker network inspect skax-eatool-eplaton-master_skax-network

# 컨테이너 로그 확인
docker-compose logs -f api-gateway
```

## 서비스 관리

### 서비스 중지

```bash
# 모든 서비스 중지
docker-compose down

# 특정 서비스만 중지
docker-compose stop mbc-service
```

### 서비스 재시작

```bash
# 모든 서비스 재시작
docker-compose restart

# 특정 서비스만 재시작
docker-compose restart mbc-service
```

### 로그 확인

```bash
# 모든 서비스 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f mbc-service
```

### 데이터베이스 접근

H2 콘솔 접근:
- MBC: http://localhost:8085/h2-console
- MBA: http://localhost:8084/h2-console
- KSA: http://localhost:8083/h2-console
- KJI: http://localhost:8082/h2-console

연결 정보:
- JDBC URL: `jdbc:h2:mem:service_db`
- Username: `sa`
- Password: (비어있음)

## 모니터링

### 헬스체크

```bash
# 서비스별 헬스체크
curl http://localhost:8000/actuator/health
curl http://localhost:8085/actuator/health
curl http://localhost:8084/actuator/health
```

### 메트릭스

```bash
# Prometheus 메트릭스
curl http://localhost:8000/actuator/prometheus
curl http://localhost:8085/actuator/prometheus
curl http://localhost:8084/actuator/prometheus
```

## 프로덕션 배포 고려사항

1. **데이터베이스**: H2 대신 PostgreSQL/MySQL 사용
2. **로깅**: ELK 스택 연동
3. **모니터링**: Prometheus + Grafana 연동
4. **보안**: HTTPS 설정, 보안 헤더 추가
5. **스케일링**: Kubernetes 배포 고려

## 지원

문제가 발생하면 다음을 확인하세요:

1. Docker Desktop이 실행 중인지 확인
2. 포트 충돌 여부 확인
3. 메모리 사용량 확인
4. 서비스 로그 확인
5. 네트워크 연결 상태 확인 