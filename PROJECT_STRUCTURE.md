# SKAX EA Tool Eplaton - 완전 가이드

> **SKAX EA Tool Eplaton** - skax/eatool 엔터프라이즈 아키텍처 도구 플랫폼 완전 가이드

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://openjdk.java.net/projects/jdk/11/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-skax.eatool%20Internal-red.svg)](LICENSE)

---

## 📋 목차

1. [프로젝트 개요](#1-프로젝트-개요)
2. [아키텍처 및 구조](#2-아키텍처-및-구조)
3. [모듈 상세 설명](#3-모듈-상세-설명)
4. [개발 환경 설정](#4-개발-환경-설정)
5. [빌드 및 배포](#5-빌드-및-배포)
6. [실행 및 테스트](#6-실행-및-테스트)
7. [문제 해결](#7-문제-해결)
8. [개발 가이드](#8-개발-가이드)
9. [API 문서](#9-api-문서)
10. [모니터링 및 로깅](#10-모니터링-및-로깅)

---

## 1. 프로젝트 개요

### 1.1 프로젝트 정보
- **프로젝트명**: SKAX EA Tool Eplaton
- **패키지명**: `com.skax.eatool`
- **버전**: 1.0.0-SNAPSHOT
- **기술 스택**: Spring Boot 2.7.18, Java 11, Maven
- **개발사**: skax.eatool

### 1.2 프로젝트 목적
- 엔터프라이즈 아키텍처 도구 플랫폼 제공
- KSA 프레임워크 기반 비즈니스 애플리케이션 개발
- 멀티모듈 아키텍처를 통한 모듈화된 개발
- 외부 JAR 파일 공유 및 관리

### 1.3 주요 특징
- 🏗️ **멀티모듈 아키텍처**: Maven 기반 모듈화
- 🔧 **Spring Boot**: 최신 프레임워크 활용
- 📊 **MyBatis**: 효율적인 데이터베이스 접근
- 🔐 **Spring Security**: 보안 기능 제공
- 📝 **RESTful API**: 표준 REST API 제공

---

## 2. 아키텍처 및 구조

### 2.1 전체 프로젝트 구조
```
skax-eatool-eplaton/
├── 📄 pom.xml                           # 최상위 부모 프로젝트
├── 📁 ksa-lib/                          # KSA 라이브러리 모듈
│   ├── 📄 pom.xml
│   ├── 📁 src/
│   └── 📁 runtime/                      # 외부 JAR 파일들
├── 📁 ksa-java/                         # KSA Java 구현 모듈
│   ├── 📄 pom.xml
│   └── 📁 src/
├── 📁 kji-lib/                          # KJI 라이브러리 모듈
│   ├── 📄 pom.xml
│   └── 📁 runtime/
├── 📁 kji-java/                         # KJI Java 구현 모듈
│   ├── 📄 pom.xml
│   └── 📁 src/
├── 📁 mbc01-lib/                        # MBC01 외부 JAR 공유 모듈
│   ├── 📄 pom.xml
│   └── 📁 runtime/                      # MBC01 JAR 파일들
├── 📁 mbc01-java/                       # MBC01 메인 애플리케이션 모듈
│   ├── 📄 pom.xml
│   └── 📁 src/
├── 📁 mbc-java/                         # MBC 메인 애플리케이션 모듈
│   ├── 📄 pom.xml
│   └── 📁 src/
├── 📄 README.md                          # 프로젝트 가이드
├── 📄 BUILD_TROUBLESHOOTING.md          # 빌드 문제 해결 가이드
├── 📄 ENCODING_GUIDE.md                 # 인코딩 문제 해결 가이드
└── 📁 zdoc/                             # 프로젝트 문서
```

### 2.2 의존성 구조
```
ksa-java → ksa-lib
kji-java → kji-lib
mbc01-java → ksa-lib + ksa-java
mbc-java → ksa-lib + mbc01-lib
```

### 2.3 패키지 구조
```
com.skax.eatool
├── ksa                    # KSA 프레임워크
├── kji                    # KJI 프레임워크
├── mbc                    # MBC 메인 애플리케이션
└── mbc01                  # MBC01 공통 모듈
```

---

## 3. 모듈 상세 설명

### 3.1 ksa-lib (KSA 라이브러리)
**역할**: KSA 프레임워크의 핵심 라이브러리
- **패키지**: `com.skax.eatool.ksa`
- **기능**: KSA 관련 JAR 파일들 포함 및 공유
- **의존성**: 다른 모든 모듈의 기반 라이브러리
- **특징**: runtime 디렉토리에 외부 JAR 파일들 보관

### 3.2 ksa-java (KSA Java 구현)
**역할**: KSA 프레임워크의 Java 구현체
- **패키지**: `com.skax.eatool.ksa`
- **기능**: Spring Boot 기반의 KSA 애플리케이션
- **특징**: KSA 관련 설정 및 구현 제공
- **의존성**: ksa-lib

### 3.3 kji-lib (KJI 라이브러리)
**역할**: KJI 프레임워크의 라이브러리
- **패키지**: `com.skax.eatool.kji`
- **기능**: KJI 관련 외부 JAR 파일들 공유
- **의존성**: kji-java 모듈의 기반
- **특징**: 독립적인 KJI 라이브러리

### 3.4 kji-java (KJI Java 구현)
**역할**: KJI 프레임워크의 Java 구현체
- **패키지**: `com.skax.eatool.kji`
- **기능**: KJI 관련 비즈니스 로직 구현
- **특징**: 독립적인 KJI 애플리케이션
- **의존성**: kji-lib

### 3.5 mbc01-lib (MBC01 외부 JAR 공유)
**역할**: MBC01 외부 JAR 파일 공유 모듈
- **패키지**: `com.skax.eatool.mbc01`
- **기능**: mbc01-java에서 생성한 JAR 파일들을 외부로 공유
- **특징**: runtime 디렉토리에 JAR 파일들 포함
- **의존성**: mbc01-java

### 3.6 mbc01-java (MBC01 메인 애플리케이션)
**역할**: MBC01 메인 스프링 부트 애플리케이션
- **패키지**: `com.skax.eatool.mbc01`
- **기능**: 공통 라이브러리 및 비즈니스 로직 구현
- **의존성**: ksa-lib, ksa-java
- **특징**: JAR 파일들을 생성하여 mbc01-lib로 공유

### 3.7 mbc-java (MBC 메인 애플리케이션)
**역할**: MBC 메인 스프링 부트 애플리케이션
- **패키지**: `com.skax.eatool.mbc`
- **기능**: 웹 서비스, REST API 제공
- **의존성**: ksa-lib, mbc01-lib
- **특징**: 최종 사용자 애플리케이션

---

## 4. 개발 환경 설정

### 4.1 필수 요구사항
- **Java**: OpenJDK 11 이상
- **Maven**: 3.6 이상
- **IDE**: IntelliJ IDEA, Eclipse, VS Code
- **Git**: 버전 관리
- **OS**: Windows, Linux, macOS

### 4.2 환경 변수 설정

#### Windows
```batch
set JAVA_HOME=C:\Program Files\Java\jdk-11
set MAVEN_HOME=C:\Program Files\Apache\maven
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
```

#### Linux/Mac
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export MAVEN_HOME=/opt/apache-maven
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

### 4.3 IDE 설정

#### IntelliJ IDEA
1. **프로젝트 열기**: `skax-eatool-eplaton` 폴더 선택
2. **Maven 프로젝트 import**: 자동으로 Maven 프로젝트 인식
3. **Java 11 SDK 설정**: Project Structure → Project SDK
4. **UTF-8 인코딩 설정**: Settings → Editor → File Encodings
5. **Maven 설정**: Settings → Build Tools → Maven

#### Eclipse
1. **Import**: File → Import → Existing Maven Projects
2. **Java 11 JRE 설정**: Window → Preferences → Java → Installed JREs
3. **UTF-8 인코딩 설정**: Window → Preferences → General → Workspace
4. **Maven 설정**: Window → Preferences → Maven

#### VS Code
1. **Java Extension Pack 설치**
2. **Maven Extension 설치**
3. **UTF-8 설정**: `.vscode/settings.json`
4. **Java 11 설정**: Java Runtime Configuration

### 4.4 프로젝트 클론 및 설정
```bash
# 프로젝트 클론
git clone [repository-url]
cd skax-eatool-eplaton

# 의존성 확인
mvn dependency:tree

# 프로젝트 빌드
mvn clean install -DskipTests
```

---

## 5. 빌드 및 배포

### 5.1 빌드 방법

#### 전체 프로젝트 빌드
```bash
# 전체 프로젝트 빌드
mvn clean install -DskipTests

# 또는 단계별 빌드 (권장)
./build-step-by-step.bat
```

#### 개별 모듈 빌드
```bash
# ksa-lib 빌드
mvn clean install -pl ksa-lib -DskipTests

# ksa-java 빌드
mvn clean install -pl ksa-java -DskipTests

# mbc01-java 빌드
mvn clean install -pl mbc01-java -DskipTests

# mbc-java 빌드
mvn clean install -pl mbc-java -DskipTests
```

### 5.2 단계별 빌드 프로세스

#### 1단계: ksa-lib 빌드
```bash
cd ksa-lib
mvn clean install -DskipTests
cd ..
```

#### 2단계: ksa-java 빌드
```bash
cd ksa-java
mvn clean install -DskipTests
cd ..
```

#### 3단계: kji-lib 빌드
```bash
cd kji-lib
mvn clean install -DskipTests
cd ..
```

#### 4단계: kji-java 빌드
```bash
cd kji-java
mvn clean install -DskipTests
cd ..
```

#### 5단계: mbc01-java 빌드
```bash
cd mbc01-java
mvn clean install -DskipTests
cd ..
```

#### 6단계: mbc01-lib 빌드
```bash
cd mbc01-lib
mvn clean install -DskipTests
cd ..
```

#### 7단계: mbc-java 빌드
```bash
cd mbc-java
mvn clean install -DskipTests
cd ..
```

### 5.3 배포 방법

#### JAR 파일 생성
```bash
# 각 모듈별 JAR 파일 생성
mvn clean package -DskipTests

# 실행 가능한 JAR 파일 생성
mvn clean package spring-boot:repackage -DskipTests
```

#### Docker 배포
```dockerfile
# Dockerfile 예시
FROM openjdk:11-jre-slim
COPY target/mbc-java-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 6. 실행 및 테스트

### 6.1 애플리케이션 실행

#### KSA 애플리케이션 실행
```bash
cd ksa-java
mvn spring-boot:run
```

#### MBC01 애플리케이션 실행
```bash
cd mbc01-java
mvn spring-boot:run
```

#### MBC 메인 애플리케이션 실행
```bash
cd mbc-java
mvn spring-boot:run
```

### 6.2 환경별 실행

#### 개발 환경
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

#### 운영 환경
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```

#### 테스트 환경
```bash
mvn spring-boot:run -Dspring.profiles.active=test
```

### 6.3 접속 정보

| 애플리케이션 | URL | 포트 | 설명 |
|-------------|-----|------|------|
| KSA 애플리케이션 | http://localhost:8080/ksa | 8080 | KSA 프레임워크 |
| MBC01 애플리케이션 | http://localhost:8080/mbc01 | 8080 | MBC01 공통 모듈 |
| MBC 애플리케이션 | http://localhost:8080/mbc | 8080 | 메인 애플리케이션 |
| H2 콘솔 | http://localhost:8080/mbc/h2-console | 8080 | 데이터베이스 관리 |
| Actuator | http://localhost:8080/mbc/actuator | 8080 | 모니터링 |

### 6.4 테스트 실행

#### 단위 테스트
```bash
# 전체 테스트 실행
mvn test

# 특정 모듈 테스트
mvn test -pl mbc-java

# 특정 테스트 클래스 실행
mvn test -Dtest=UserServiceTest
```

#### 통합 테스트
```bash
# 통합 테스트 실행
mvn verify

# 테스트 커버리지 확인
mvn jacoco:report
```

---

## 7. 문제 해결

### 7.1 빌드 문제

#### 의존성 문제
```bash
# Maven 로컬 저장소 정리
mvn dependency:purge-local-repository

# 의존성 트리 확인
mvn dependency:tree

# 단계별 빌드
./build-step-by-step.bat
```

#### 인코딩 문제
```bash
# 인코딩 문제 해결 스크립트 실행
./fix-encoding.bat

# Maven 빌드 시 인코딩 지정
mvn clean compile -Dfile.encoding=UTF-8
```

#### JAR 파일 누락
```bash
# runtime 디렉토리 확인
ls ksa-lib/runtime/
ls mbc01-lib/runtime/

# 누락된 JAR 파일 복사
cp ksa-java/target/ksa-java-*.jar ksa-lib/runtime/
cp mbc01-java/target/mbc01-java-*.jar mbc01-lib/runtime/
```

### 7.2 실행 문제

#### 포트 충돌
```bash
# 포트 사용 확인
netstat -ano | findstr :8080

# 다른 포트로 실행
mvn spring-boot:run -Dserver.port=8081
```

#### 메모리 부족
```bash
# JVM 메모리 설정
mvn spring-boot:run -Xmx2048m -Xms1024m
```

#### 데이터베이스 연결 문제
```bash
# H2 데이터베이스 확인
java -cp h2-*.jar org.h2.tools.Server

# 데이터베이스 설정 확인
cat src/main/resources/application.yml
```

### 7.3 자주 발생하는 문제

#### 1. JAR 파일 누락
**증상**: `ClassNotFoundException` 또는 `NoClassDefFoundError`
**해결**: `BUILD_TROUBLESHOOTING.md` 참조

#### 2. 인코딩 문제
**증상**: 한글 주석이나 문자열이 깨짐
**해결**: `ENCODING_GUIDE.md` 참조

#### 3. 의존성 충돌
**증상**: `ClassNotFoundException` 또는 버전 충돌
**해결**: Maven dependency tree 확인

#### 4. Spring Boot 시작 실패
**증상**: 애플리케이션 시작 시 오류
**해결**: 로그 확인 및 설정 파일 점검

---

## 8. 개발 가이드

### 8.1 새로운 기능 추가

#### 1. 공통 기능 추가 (mbc01-java)
```java
// 패키지: com.skax.eatool.mbc01
@Service
public class CommonService {
    // 공통 비즈니스 로직 구현
}
```

#### 2. 비즈니스 로직 구현 (mbc-java)
```java
// 패키지: com.skax.eatool.mbc
@RestController
@RequestMapping("/api")
public class BusinessController {
    // REST API 구현
}
```

#### 3. 설정 추가
```yaml
# application.yml
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
```

### 8.2 코드 컨벤션

#### Java 코드
- **네이밍**: camelCase 사용
- **주석**: 한글 주석 사용
- **패키지**: `com.skax.eatool` 하위에 구성
- **클래스**: PascalCase 사용

#### XML 설정
- **들여쓰기**: 2칸 사용
- **네이밍**: kebab-case 사용
- **주석**: 한글 주석 사용

#### Maven 설정
- **버전 관리**: properties 섹션에서 중앙 관리
- **의존성**: 명시적 버전 지정
- **플러그인**: 최신 버전 사용

### 8.3 Git 워크플로우

#### 브랜치 전략
```bash
# 기능 개발
git checkout -b feature/새로운기능

# 버그 수정
git checkout -b hotfix/버그수정

# 릴리즈
git checkout -b release/1.0.0
```

#### 커밋 메시지
```bash
# 기능 추가
git commit -m "feat: 사용자 관리 기능 추가"

# 버그 수정
git commit -m "fix: 로그인 오류 수정"

# 문서 업데이트
git commit -m "docs: README 업데이트"
```

### 8.4 테스트 작성

#### 단위 테스트
```java
@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    void 사용자_등록_테스트() {
        // 테스트 코드 작성
    }
}
```

#### 통합 테스트
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    
    @Test
    void 사용자_목록_조회_테스트() {
        // REST API 테스트
    }
}
```

---

## 9. API 문서

### 9.1 주요 기능

#### 1. 사용자 관리 (User Management)
- **엔드포인트**: `/api/users`
- **기능**: 사용자 등록/수정/삭제/조회
- **권한**: 관리자 권한 필요

#### 2. 계정 관리 (Account Management)
- **엔드포인트**: `/api/accounts`
- **기능**: 계정 정보 관리, 상태 관리
- **권한**: 사용자 권한 필요

#### 3. 리포트 생성 (Report Generation)
- **엔드포인트**: `/api/reports`
- **기능**: 다양한 리포트 생성, PDF/Excel 출력
- **권한**: 리포트 권한 필요

#### 4. 시스템 관리 (System Management)
- **엔드포인트**: `/api/system`
- **기능**: 시스템 설정, 로그 관리, 모니터링
- **권한**: 시스템 관리자 권한 필요

### 9.2 REST API 예시

#### 사용자 조회
```http
GET /api/users
Authorization: Bearer {token}
Content-Type: application/json

Response:
{
  "users": [
    {
      "id": 1,
      "username": "user1",
      "email": "user1@example.com",
      "role": "USER"
    }
  ]
}
```

#### 사용자 등록
```http
POST /api/users
Authorization: Bearer {token}
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "role": "USER"
}
```

### 9.3 에러 코드

| 코드 | 메시지 | 설명 |
|------|--------|------|
| 200 | OK | 성공 |
| 400 | Bad Request | 잘못된 요청 |
| 401 | Unauthorized | 인증 실패 |
| 403 | Forbidden | 권한 없음 |
| 404 | Not Found | 리소스 없음 |
| 500 | Internal Server Error | 서버 오류 |

---

## 10. 모니터링 및 로깅

### 10.1 Actuator 엔드포인트

#### 상태 확인
```http
GET /actuator/health
```

#### 애플리케이션 정보
```http
GET /actuator/info
```

#### 메트릭 정보
```http
GET /actuator/metrics
```

#### 환경 정보
```http
GET /actuator/env
```

### 10.2 로깅 설정

#### Log4j 설정
```xml
<!-- log4j.xml -->
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>
```

#### 로그 레벨 설정
```yaml
# application.yml
logging:
  level:
    com.skax.eatool: DEBUG
    org.springframework: INFO
    org.hibernate: WARN
```

### 10.3 모니터링 도구

#### Spring Boot Actuator
- **Health Check**: 애플리케이션 상태 확인
- **Metrics**: 성능 메트릭 수집
- **Info**: 애플리케이션 정보 제공

#### 로그 분석
- **로그 파일**: `logs/application.log`
- **로그 레벨**: DEBUG, INFO, WARN, ERROR
- **로그 포맷**: 시간, 스레드, 레벨, 클래스, 메시지

---

## 📞 지원 및 연락처

### 문서
- [빌드 문제 해결 가이드](BUILD_TROUBLESHOOTING.md)
- [인코딩 문제 해결 가이드](ENCODING_GUIDE.md)
- [프로젝트 구조 문서](PROJECT_STRUCTURE.md)

### 연락처
- **개발팀**: skax.eatool 개발팀
- **이메일**: dev@skax.eatool.com
- **내부 시스템**: skax.eatool 내부 개발 시스템

---

## 📄 라이센스

이 프로젝트는 **skax.eatool 내부 프로젝트**입니다.

---

**SKAX EA Tool Eplaton** - skax.eatool 엔터프라이즈 아키텍처 도구 플랫폼 완전 가이드

*마지막 업데이트: 2024년*
