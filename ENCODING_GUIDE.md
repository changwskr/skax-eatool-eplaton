# 인코딩 문제 해결 가이드

## 문제 상황

프로젝트의 한글 주석이나 문자열이 깨져서 표시되는 문제가 발생할 수 있습니다.

## 해결 방법

### 1. Maven 설정

모든 `pom.xml` 파일에 다음 설정이 추가되어 있습니다:

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
</properties>
```

### 2. IDE 설정

#### Eclipse

- `Window` → `Preferences` → `General` → `Workspace`
- `Text file encoding`을 `UTF-8`로 설정
- 각 프로젝트의 `.settings/org.eclipse.core.resources.prefs` 파일에 UTF-8 설정 추가

#### IntelliJ IDEA

- `File` → `Settings` → `Editor` → `File Encodings`
- `Global Encoding`과 `Project Encoding`을 `UTF-8`로 설정
- `Properties Files`의 `Default encoding for properties files`를 `UTF-8`로 설정

#### VS Code

- `.vscode/settings.json` 파일에 UTF-8 설정 추가
- `Ctrl+Shift+P` → `Preferences: Open Settings (JSON)`에서 설정 확인

### 3. 실행 스크립트

다음 스크립트를 실행하여 인코딩 문제를 해결할 수 있습니다:

```bash
# Windows
fix-encoding.bat

# Linux/Mac
./fix-encoding.sh
```

### 4. 수동 해결 방법

#### Java 파일 인코딩 확인

```bash
# Windows
file -i *.java

# Linux/Mac
file -i *.java
```

#### Maven 빌드 시 인코딩 지정

```bash
mvn clean compile -Dfile.encoding=UTF-8
```

#### JVM 옵션 설정

```bash
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
```

### 5. 파일별 인코딩 확인

#### Java 소스 파일

- 모든 Java 파일은 UTF-8로 저장되어야 합니다
- 주석의 한글이 깨지지 않도록 확인

#### 설정 파일

- `application.yml`, `application-dev.yml`, `application-prod.yml`
- `pom.xml` 파일들
- 모든 설정 파일이 UTF-8로 저장되어야 합니다

#### 문서 파일

- `README.md`
- 기타 마크다운 파일들
- 모든 문서 파일이 UTF-8로 저장되어야 합니다

### 6. 문제 해결 체크리스트

- [ ] Maven 설정에서 UTF-8 인코딩 지정
- [ ] IDE에서 UTF-8 인코딩 설정
- [ ] Java 파일이 UTF-8로 저장됨
- [ ] 설정 파일이 UTF-8로 저장됨
- [ ] 문서 파일이 UTF-8로 저장됨
- [ ] JVM 옵션에서 UTF-8 인코딩 지정
- [ ] 프로젝트 빌드 및 실행 테스트

### 7. 추가 도움말

문제가 지속되는 경우:

1. IDE를 완전히 재시작
2. 프로젝트를 다시 import
3. 모든 파일을 UTF-8로 다시 저장
4. Maven 캐시 삭제 후 재빌드

```bash
# Maven 캐시 삭제
mvn dependency:purge-local-repository
```
