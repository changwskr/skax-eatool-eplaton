# IDE 설정 가이드

## IntelliJ IDEA 설정

### 1. 프로젝트 설정
- **Project SDK**: 11
- **Project language level**: 11
- **Module SDK**: 11

### 2. 코드 스타일 설정
- **File → Settings → Editor → Code Style → Java**
- **Import scheme**: GoogleStyle
- **Tab size**: 4
- **Indent**: 4 spaces
- **Continuation indent**: 8 spaces
- **Right margin**: 120

### 3. 플러그인 설치
- **SonarLint**: 코드 품질 검사
- **CheckStyle-IDEA**: 코드 스타일 검사
- **SpotBugs**: 버그 패턴 검사
- **Rainbow Brackets**: 괄호 색상 구분
- **Lombok Plugin**: Lombok 지원

### 4. Git 설정
- **VCS → Git**: 프로젝트 Git 연동
- **Commit template**: 커밋 메시지 템플릿 설정

## Eclipse 설정

### 1. Java 설정
- **Window → Preferences → Java → Compiler**
- **Compiler compliance level**: 11
- **Source compatibility**: 11

### 2. 코드 스타일
- **Window → Preferences → Java → Code Style → Formatter**
- **Profile**: GoogleStyle
- **Indentation**: 4 spaces

### 3. 플러그인 설치
- **Checkstyle Plugin**
- **SpotBugs Plugin**
- **SonarLint Plugin**

## VS Code 설정

### 1. 확장 프로그램
- **Extension Pack for Java**
- **Spring Boot Extension Pack**
- **Checkstyle for Java**
- **SonarLint**

### 2. 설정 파일
```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
    "java.format.settings.profile": "GoogleStyle"
}
```

## 공통 설정

### 1. 인코딩 설정
- **UTF-8** 사용
- **Line endings**: LF (Unix)

### 2. 파일 템플릿
- **Class template**: Javadoc 포함
- **Method template**: Javadoc 포함

### 3. 디버깅 설정
- **Remote debugging**: 5005 포트
- **Hot reload**: 활성화 