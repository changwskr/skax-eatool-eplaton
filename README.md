# SKAX EA Tool Eplaton

> **SKAX EA Tool Eplaton** - Spring Boot 기반의 엔터프라이즈 아키텍처 도구 플랫폼

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://openjdk.java.net/projects/jdk/11/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-KBSTAR%20Internal-red.svg)](LICENSE)

## 📋 목차

- [프로젝트 개요](#프로젝트-개요)
- [프로젝트 구조](#프로젝트-구조)
- [모듈 설명](#모듈-설명)
- [기술 스택](#기술-스택)
- [빌드 및 실행](#빌드-및-실행)
- [개발 환경 설정](#개발-환경-설정)
- [문제 해결](#문제-해결)
- [API 문서](#api-문서)
- [라이센스](#라이센스)

## 🎯 프로젝트 개요

**SKAX EA Tool Eplaton**은 KBSTAR에서 개발한 엔터프라이즈 아키텍처 도구 플랫폼입니다. Spring Boot 기반의 멀티모듈 프로젝트로 구성되어 있으며, KSA 프레임워크를 기반으로 한 다양한 비즈니스 애플리케이션을 제공합니다.

### 주요 특징

- 🏗️ **멀티모듈 아키텍처**: Maven 기반의 모듈화된 구조
- 🔧 **Spring Boot 2.7.18**: 최신 Spring Boot 프레임워크 활용
- 📊 **MyBatis**: 효율적인 데이터베이스 접근
- 🔐 **Spring Security**: 보안 기능 제공
- 📝 **RESTful API**: 표준 REST API 제공
- 🎨 **모던 UI**: 현대적인 웹 인터페이스

## 📁 프로젝트 구조

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

## 🧩 모듈 설명

### 1. **ksa-lib** - KSA 라이브러리
- **역할**: KSA 프레임워크의 핵심 라이브러리
- **기능**: KSA 관련 JAR 파일들 포함 및 공유
- **의존성**: 다른 모든 모듈의 기반 라이브러리

### 2. **ksa-java** - KSA Java 구현
- **역할**: KSA 프레임워크의 Java 구현체
- **기능**: Spring Boot 기반의 KSA 애플리케이션
- **특징**: KSA 관련 설정 및 구현 제공

### 3. **kji-lib** - KJI 라이브러리
- **역할**: KJI 프레임워크의 라이브러리
- **기능**: KJI 관련 외부 JAR 파일들 공유
- **의존성**: kji-java 모듈의 기반

### 4. **kji-java** - KJI Java 구현
- **역할**: KJI 프레임워크의 Java 구현체
- **기능**: KJI 관련 비즈니스 로직 구현
- **특징**: 독립적인 KJI 애플리케이션

### 5. **mbc01-lib** - MBC01 외부 JAR 공유
- **역할**: MBC01 외부 JAR 파일 공유 모듈
- **기능**: mbc01-java에서 생성한 JAR 파일들을 외부로 공유
- **특징**: runtime 디렉토리에 JAR 파일들 포함

### 6. **mbc01-java** - MBC01 메인 애플리케이션
- **역할**: MBC01 메인 스프링 부트 애플리케이션
- **기능**: 공통 라이브러리 및 비즈니스 로직 구현
- **의존성**: ksa-lib, ksa-java
- **특징**: JAR 파일들을 생성하여 mbc01-lib로 공유

### 7. **mbc-java** - MBC 메인 애플리케이션
- **역할**: MBC 메인 스프링 부트 애플리케이션
- **기능**: 웹 서비스, REST API 제공
- **의존성**: ksa-lib, mbc01-lib
- **특징**: 최종 사용자 애플리케이션

## 🛠️ 기술 스택

### **Backend**
- **Java**: 11
- **Spring Boot**: 2.7.18
- **Spring Framework**: 5.3.31
- **Spring Security**: 5.7.11
- **MyBatis**: 3.5.13
- **Maven**: 멀티모듈 프로젝트

### **Database**
- **H2 Database**: 2.1.214 (개발용)
- **MySQL**: 운영 환경

### **Build & Deploy**
- **Maven**: 3.6+
- **Java**: OpenJDK 11

### **External Libraries**
- **JDOM**: 1.1.3 (XML 처리)
- **Log4j**: 1.2.17 (로깅)
- **Apache Commons**: HttpClient, IO, Digester
- **Apache Velocity**: 1.7 (템플릿 엔진)

## 🚀 빌드 및 실행

### **전체 프로젝트 빌드**

```bash
# 전체 프로젝트 빌드
mvn clean install -DskipTests

# 또는 단계별 빌드 (권장)
./build-step-by-step.bat
```

### **개별 모듈 빌드**

```bash
# ksa-lib 빌드
mvn clean install -pl ksa-lib -DskipTests

# mbc01-java 빌드
mvn clean install -pl mbc01-java -DskipTests

# mbc-java 빌드
mvn clean install -pl mbc-java -DskipTests
```

### **애플리케이션 실행**

```bash
# KSA 애플리케이션 실행
cd ksa-java
mvn spring-boot:run

# MBC01 애플리케이션 실행
cd mbc01-java
mvn spring-boot:run

# MBC 메인 애플리케이션 실행
cd mbc-java
mvn spring-boot:run
```

### **환경별 실행**

```bash
# 개발 환경
mvn spring-boot:run -Dspring.profiles.active=dev

# 운영 환경
mvn spring-boot:run -Dspring.profiles.active=prod

# 테스트 환경
mvn spring-boot:run -Dspring.profiles.active=test
```

## ⚙️ 개발 환경 설정

### **필수 요구사항**

- **Java**: OpenJDK 11 이상
- **Maven**: 3.6 이상
- **IDE**: IntelliJ IDEA, Eclipse, VS Code
- **Git**: 버전 관리

### **IDE 설정**

#### **IntelliJ IDEA**
1. 프로젝트 열기: `skax-eatool-eplaton` 폴더 선택
2. Maven 프로젝트 import
3. Java 11 SDK 설정
4. UTF-8 인코딩 설정

#### **Eclipse**
1. Import → Existing Maven Projects
2. Java 11 JRE 설정
3. UTF-8 인코딩 설정

### **환경 변수 설정**

```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-11
set MAVEN_HOME=C:\Program Files\Apache\maven
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export MAVEN_HOME=/opt/apache-maven
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

## 🔧 문제 해결

### **빌드 문제**

#### **의존성 문제**
```bash
# Maven 로컬 저장소 정리
mvn dependency:purge-local-repository

# 의존성 트리 확인
mvn dependency:tree

# 단계별 빌드
./build-step-by-step.bat
```

#### **인코딩 문제**
```bash
# 인코딩 문제 해결 스크립트 실행
./fix-encoding.bat

# Maven 빌드 시 인코딩 지정
mvn clean compile -Dfile.encoding=UTF-8
```

### **실행 문제**

#### **포트 충돌**
```bash
# 포트 사용 확인
netstat -ano | findstr :8080

# 다른 포트로 실행
mvn spring-boot:run -Dserver.port=8081
```

#### **메모리 부족**
```bash
# JVM 메모리 설정
mvn spring-boot:run -Xmx2048m -Xms1024m
```

### **자주 발생하는 문제**

1. **JAR 파일 누락**: `BUILD_TROUBLESHOOTING.md` 참조
2. **인코딩 문제**: `ENCODING_GUIDE.md` 참조
3. **의존성 충돌**: Maven dependency tree 확인

## 📚 API 문서

### **접속 정보**

| 애플리케이션 | URL | 설명 |
|-------------|-----|------|
| KSA 애플리케이션 | http://localhost:8080/ksa | KSA 프레임워크 |
| MBC01 애플리케이션 | http://localhost:8080/mbc01 | MBC01 공통 모듈 |
| MBC 애플리케이션 | http://localhost:8080/mbc | 메인 애플리케이션 |
| H2 콘솔 | http://localhost:8080/mbc/h2-console | 데이터베이스 관리 |
| Actuator | http://localhost:8080/mbc/actuator | 모니터링 |

### **주요 기능**

#### **1. 사용자 관리 (User Management)**
- 사용자 등록/수정/삭제
- 권한 관리
- 로그인/로그아웃

#### **2. 계정 관리 (Account Management)**
- 계정 정보 관리
- 계정 상태 관리

#### **3. 리포트 생성 (Report Generation)**
- 다양한 리포트 생성
- PDF, Excel 출력

#### **4. 시스템 관리 (System Management)**
- 시스템 설정
- 로그 관리
- 모니터링

## 🛡️ 보안

### **Spring Security 설정**
- 기본 인증 및 권한 관리
- CSRF 보호
- XSS 방지

### **데이터 보안**
- 민감한 정보 암호화
- 로그 보안
- 접근 제어

## 📊 모니터링

### **Actuator 엔드포인트**
- `/actuator/health`: 상태 확인
- `/actuator/info`: 애플리케이션 정보
- `/actuator/metrics`: 메트릭 정보

### **로깅**
- Log4j 설정
- 로그 레벨 관리
- 로그 파일 관리

## 🤝 기여 가이드

### **개발 워크플로우**

1. **브랜치 생성**
   ```bash
   git checkout -b feature/새로운기능
   ```

2. **개발 및 테스트**
   ```bash
   mvn clean test
   ```

3. **커밋 및 푸시**
   ```bash
   git add .
   git commit -m "feat: 새로운 기능 추가"
   git push origin feature/새로운기능
   ```

### **코딩 컨벤션**

- **Java**: Google Java Style Guide 준수
- **XML**: 2칸 들여쓰기
- **주석**: 한글 주석 사용
- **네이밍**: camelCase 사용

## 📄 라이센스

이 프로젝트는 **KBSTAR 내부 프로젝트**입니다.

---

## 📞 지원

### **문서**
- [빌드 문제 해결 가이드](BUILD_TROUBLESHOOTING.md)
- [인코딩 문제 해결 가이드](ENCODING_GUIDE.md)

### **연락처**
- **개발팀**: KBSTAR 개발팀
- **이메일**: dev@kbstar.com

---

**SKAX EA Tool Eplaton** - KBSTAR 엔터프라이즈 아키텍처 도구 플랫폼
