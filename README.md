# SKAX-EATOOL-EPLATON 프로젝트 통합 아키텍처 가이드

## 1. 프로젝트 개요

**SKAX-EATOOL-EPLATON**은 다양한 도메인(Java) 모듈과 외부 라이브러리 배포용 lib 모듈로 구성된 멀티 모듈 Maven 프로젝트입니다. 각 도메인별 비즈니스 로직, 공통/외부 라이브러리 관리, 빌드/배포 자동화를 목표로 설계되었습니다.

---

## 2. 전체 구조

```
skax-eatool-eplaton/
├── ksa-java/      # KSA 도메인 Java 애플리케이션
├── ksa-lib/       # KSA 관련 외부 JAR 배포용 모듈
├── kji-java/      # KJI 도메인 Java 애플리케이션
├── kji-lib/       # KJI 관련 외부 JAR 배포용 모듈
├── mbc01-java/    # MBC01 도메인 Java 라이브러리
├── mbc01-lib/     # MBC01 관련 외부 JAR 배포용 모듈
├── mbc-java/      # MBC 메인 Spring Boot 애플리케이션
├── README.md      # 프로젝트 설명 및 아키텍처 가이드
└── build-step-by-step.bat # 모듈별 빌드 자동화 스크립트
```

---

## 3. 모듈별 역할

### 3.1. Java 모듈
- **ksa-java, kji-java, mbc01-java, mbc-java**
  - 각 도메인별 비즈니스 로직, API, 서비스 구현
  - Spring Boot 기반(일부 모듈)
  - 빌드 시 JAR 파일이 생성되어 로컬 Maven 저장소에 install

### 3.2. Lib 모듈
- **ksa-lib, kji-lib, mbc01-lib**
  - 외부/공통 JAR 파일을 관리 및 배포
  - 실제로는 다른 모듈의 직접적인 Maven dependency가 아님
  - 필요시 runtime 디렉토리에 외부 JAR을 수동 배치

---

## 4. 의존성 및 빌드 구조

### 4.1. 의존성 관리
- **lib 모듈은 직접적인 Maven dependency로 사용하지 않음**
- 각 Java 모듈은 필요한 경우, lib 모듈의 runtime 디렉토리에 있는 JAR을 system scope로 참조하거나, install 방식으로 로컬 Maven 저장소에 등록하여 참조

### 4.2. 빌드 순서
1. `ksa-java`, `kji-java`, `mbc01-java` 등 Java 모듈을 먼저 빌드 (`mvn clean install`)
2. 각 Java 모듈의 JAR이 로컬 Maven 저장소에 등록됨
3. `mbc-java` 등 메인 애플리케이션이 위 JAR을 의존성으로 참조하여 빌드

### 4.3. 빌드 자동화
- `build-step-by-step.bat` 스크립트로 모듈별 빌드 순서 자동화
- 파일 복사 방식 대신 Maven install 방식 권장

---

## 5. 개발 및 운영 흐름

1. **개발**
   - 각 도메인별 Java 모듈에서 기능 개발
   - 공통/외부 라이브러리는 lib 모듈의 runtime에 수동 배치

2. **빌드**
   - Java 모듈 빌드 → JAR 생성 및 install
   - 메인 애플리케이션 빌드

3. **배포**
   - 빌드된 JAR을 운영 환경에 배포
   - 외부 JAR은 lib 모듈 runtime에서 관리

---

## 6. 의존성 예시 (pom.xml)

```xml
<!-- mbc-java/pom.xml -->
<dependency>
    <groupId>com.skax.eatool</groupId>
    <artifactId>ksa-java</artifactId>
    <version>${project.version}</version>
</dependency>
<dependency>
    <groupId>com.skax.eatool</groupId>
    <artifactId>mbc01-java</artifactId>
    <version>${project.version}</version>
</dependency>
<dependency>
    <groupId>com.skax.eatool</groupId>
    <artifactId>kji-java</artifactId>
    <version>${project.version}</version>
</dependency>
```

---

## 7. 주요 설계 원칙

- **모듈 간 강결합 최소화**: lib 모듈은 단순 JAR 배포용, 직접 의존성 X
- **빌드/배포 자동화**: install 방식, batch 스크립트 활용
- **확장성**: 도메인별 모듈 추가/확장 용이
- **외부 라이브러리 관리 일원화**: lib 모듈 runtime 디렉토리 활용

---

## 8. 참고

- **README.md**: 상세 빌드/실행/운영 가이드
- **PROJECT_STRUCTURE.md**: 전체 구조 및 각 모듈 설명
- **build-step-by-step.bat**: 빌드 자동화 예시

---

> 본 가이드는 SKAX-EATOOL-EPLATON 프로젝트의 구조와 운영 원칙을 한눈에 파악할 수 있도록 작성되었습니다. 추가적인 아키텍처 다이어그램, 상세 API 흐름, CI/CD 연동 등은 별도 요청 시 제공 가능합니다.
