# SKAX Project Eplaton - 빌드 문제 해결 가이드

## 현재 문제 상황

### 1. 의존성 문제
- 누락된 JAR 파일들 (KSA 라이브러리, iBatis, Hibernate 등)
- Maven Central에서 사용할 수 없는 라이브러리들

### 2. 컴파일 오류
- JDOM, Log4j, Apache Commons 등의 라이브러리 부재
- Java 버전 불일치 (8 vs 11)

## 해결 방법

### 방법 1: 단계별 빌드 (권장)
```bash
# 1. ksa-lib 먼저 빌드
mvn clean install -pl ksa-lib -DskipTests

# 2. mbc01-java 빌드
mvn clean install -pl mbc01-java -DskipTests

# 3. 모듈별 빌드
mvn clean install -pl [모듈명] -DskipTests

# 4. mbc-java 빌드
mvn clean install -pl mbc-java -DskipTests
```

### 방법 2: 배치 파일 사용
```bash
# build-step-by-step.bat 실행
build-step-by-step.bat
```

### 방법 3: 전체 프로젝트 빌드
```bash
mvn clean install -DskipTests
```

## 누락된 JAR 파일들

### ksa-lib/runtime/ (필요한 JAR들)
- kesa-app-container.jar
- kesa-model.jar
- KESA_CRYPTO.jar
- ibatis.jar
- hibernate3.jar
- commons-httpclient.jar
- commons-lang.jar
- commons-logging.jar
- commons-dbcp.jar
- commons-pool.jar
- commons-beanutils.jar
- commons-codec.jar
- commons-collections.jar
- commons-net.jar
- xerces.jar
- xercesImpl.jar
- xml-apis.jar
- dom4j.jar
- jaxen.jar
- jaxb-api.jar
- jaxb-impl.jar
- jaxb-libs.jar
- log4j.jar
- ehcache.jar
- antlr.jar
- asm.jar
- cglib.jar
- dependency-0.2.jar
- relaxngDatatype.jar
- trailor.jar
- WSE_BASE64.jar
- jta.jar
- xsdlib.jar

### mbc01-lib/runtime/ (필요한 JAR들)
- rtftemplate-1.0.1-b14.jar
- velocity-dep-1.5.jar
- jdom.jar
- commons-digester-1.7.jar
- commons-io-1.1.jar

### mbc-java/lib/ (필요한 JAR들)
- ifrs_foundation.jar

## 임시 해결책

### 1. Maven Central 라이브러리로 교체
다음 라이브러리들은 Maven Central에서 사용 가능한 버전으로 교체됨:
- JDOM: 1.1.3
- Log4j: 1.2.17
- Apache Commons HttpClient: 3.1
- Apache Commons IO: 2.11.0
- Apache Commons Digester: 2.1
- Apache Velocity: 1.7

### 2. 누락된 라이브러리 처리
다음 라이브러리들은 원본 JAR 파일이 필요함:
- RTF Template
- KSA 관련 라이브러리들
- iBatis, Hibernate (구버전)

## 권장사항

1. **원본 프로젝트에서 JAR 파일 수집**: 원본 프로젝트의 lib 디렉토리에서 누락된 JAR 파일들을 수집
2. **의존성 정리**: 가능한 경우 Maven Central에서 사용 가능한 라이브러리로 교체
3. **단계적 빌드**: 모듈별로 순차적으로 빌드하여 문제를 격리

## 문제 해결 순서

1. `ksa-lib` 모듈 빌드
2. `mbc01-java` 모듈 빌드 (의존성 추가 후)
3. 모듈별 빌드
4. `mbc-java` 모듈 빌드

각 단계에서 오류가 발생하면 해당 모듈의 의존성을 확인하고 수정하세요. 