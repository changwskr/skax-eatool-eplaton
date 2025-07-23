# MBA (Master Business Application)

## 개요

MBA (Master Business Application)는 마스터 비즈니스 애플리케이션으로, 기업의 핵심 비즈니스 프로세스를 관리하고 통합하는 Spring Boot 기반 애플리케이션입니다.

## 주요 기능

- **사용자 관리**: 사용자 등록, 수정, 삭제 및 권한 관리
- **시스템 설정**: 애플리케이션 설정 관리
- **감사 로그**: 사용자 활동 추적 및 로그 관리
- **메뉴 관리**: 동적 메뉴 시스템
- **보안**: Spring Security 기반 인증 및 권한 관리
- **API 문서화**: Swagger/OpenAPI 기반 API 문서

## 기술 스택

- **Java**: 11
- **Spring Boot**: 2.7.18
- **Spring Security**: 보안 및 인증
- **H2 Database**: 인메모리 데이터베이스 (개발용)
- **Thymeleaf**: 템플릿 엔진
- **Swagger/OpenAPI**: API 문서화
- **Maven**: 빌드 도구

## 프로젝트 구조

```
mba-java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/skax/eatool/mba/
│   │   │       ├── MbaApplication.java          # 메인 애플리케이션 클래스
│   │   │       ├── config/                      # 설정 클래스들
│   │   │       │   ├── MbaConfig.java
│   │   │       │   ├── WebConfig.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── SwaggerConfig.java
│   │   │       ├── web/                         # 웹 컨트롤러들
│   │   │       │   └── home/
│   │   │       │       └── HomeController.java
│   │   │       ├── as/                          # Application Service
│   │   │       ├── dc/                          # Data Controller
│   │   │       ├── pc/                          # Presentation Controller
│   │   │       ├── ic/                          # Integration Controller
│   │   │       ├── ac/                          # Application Controller
│   │   │       └── util/                        # 유틸리티 클래스들
│   │   └── resources/
│   │       ├── application.yml                  # 기본 설정
│   │       ├── application-dev.yml              # 개발 환경 설정
│   │       ├── schema.sql                       # 데이터베이스 스키마
│   │       ├── data.sql                         # 초기 데이터
│   │       ├── logback-spring.xml               # 로깅 설정
│   │       └── templates/                       # Thymeleaf 템플릿
│   │           └── home/
│   │               └── index.html
│   └── test/                                    # 테스트 코드
├── pom.xml                                      # Maven 설정
└── README.md                                    # 프로젝트 문서
```

## 시작하기

### 사전 요구사항

- Java 11 이상
- Maven 3.6 이상

### 빌드 및 실행

1. **프로젝트 빌드**
   ```bash
   mvn clean compile
   ```

2. **애플리케이션 실행**
   ```bash
   mvn spring-boot:run
   ```

3. **JAR 파일로 빌드**
   ```bash
   mvn clean package
   java -jar target/mba-java-1.0.0-SNAPSHOT.jar
   ```

### 접속 정보

애플리케이션 실행 후 다음 URL로 접속할 수 있습니다:

- **홈 페이지**: http://localhost:8081/mba
- **Swagger UI**: http://localhost:8081/mba/swagger-ui.html
- **API 문서**: http://localhost:8081/mba/v3/api-docs
- **H2 콘솔**: http://localhost:8081/mba/h2-console
- **상태 확인**: http://localhost:8081/mba/actuator/health

### 기본 계정

- **관리자**: admin / admin123
- **매니저**: manager / admin123
- **일반 사용자**: user / admin123

## 설정

### 환경별 설정

- **개발 환경**: `application-dev.yml`
- **운영 환경**: `application-prod.yml`
- **테스트 환경**: `application-test.yml`

### 주요 설정 항목

- **서버 포트**: 8081
- **데이터베이스**: H2 인메모리
- **로그 레벨**: 개발 환경에서는 DEBUG, 운영 환경에서는 INFO
- **보안**: Spring Security 기반 인증

## 개발 가이드

### 새로운 기능 추가

1. **컨트롤러 추가**: `web` 패키지에 컨트롤러 클래스 생성
2. **서비스 추가**: `as` 패키지에 서비스 클래스 생성
3. **데이터 접근**: `dc` 패키지에 데이터 컨트롤러 생성
4. **템플릿 추가**: `templates` 디렉토리에 HTML 파일 생성

### 코드 컨벤션

- **패키지 명명**: 소문자 사용
- **클래스 명명**: PascalCase 사용
- **메서드 명명**: camelCase 사용
- **상수 명명**: UPPER_SNAKE_CASE 사용

### 테스트

```bash
# 단위 테스트 실행
mvn test

# 통합 테스트 실행
mvn verify
```

## 배포

### JAR 파일 배포

```bash
# 프로덕션 빌드
mvn clean package -Pprod

# 실행
java -jar target/mba-java-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
```

### Docker 배포

```bash
# Docker 이미지 빌드
docker build -t mba-java .

# Docker 컨테이너 실행
docker run -p 8081:8081 mba-java
```

## 문제 해결

### 일반적인 문제

1. **포트 충돌**: 8081 포트가 사용 중인 경우 `application.yml`에서 포트 변경
2. **데이터베이스 연결 오류**: H2 콘솔에서 데이터베이스 상태 확인
3. **로그 확인**: `logs` 디렉토리의 로그 파일 확인

### 로그 레벨 조정

`application.yml`에서 로그 레벨을 조정할 수 있습니다:

```yaml
logging:
  level:
    com.skax.eatool.mba: DEBUG
    org.springframework.web: DEBUG
```

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 연락처

- **개발자**: KBSTAR
- **이메일**: kbstar@skax.com
- **회사**: SKAX

## 변경 이력

### v1.0.0 (2024-01-01)
- 초기 버전 릴리스
- 기본 사용자 관리 기능
- 시스템 설정 관리
- 감사 로그 시스템
- Spring Security 기반 보안
- Swagger API 문서화 