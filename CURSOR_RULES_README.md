# SKAX EA Tool Eplaton - Cursor Rules

## 📋 개요

이 프로젝트는 SKAX EA Tool Eplaton의 커서룰을 정의하고 코드 품질을 관리하기 위한 설정들을 포함합니다.

## 🏗️ 프로젝트 구조

```
.cursor/
├── rules/
│   ├── project-rules.md          # 프로젝트 커서룰
│   └── ide-settings.md           # IDE 설정 가이드
├── checkstyle.xml                # Checkstyle 설정
├── pom.xml                       # Maven 설정 (코드 품질 도구 포함)
└── .git/hooks/pre-commit         # Git pre-commit hook
```

## 🛠️ 설정된 도구들

### 1. **Checkstyle**
- **설정 파일**: `checkstyle.xml`
- **기준**: Google Java Style Guide
- **실행**: `mvn checkstyle:check`

### 2. **SpotBugs**
- **설정**: Maven 플러그인으로 구성
- **목적**: 잠재적 버그 패턴 검사
- **실행**: `mvn spotbugs:check`

### 3. **JaCoCo**
- **설정**: Maven 플러그인으로 구성
- **목적**: 코드 커버리지 측정
- **실행**: `mvn jacoco:report`

### 4. **SonarQube**
- **설정**: Maven 플러그인으로 구성
- **목적**: 종합적인 코드 품질 분석
- **실행**: `mvn sonar:sonar`

## 📝 명명 규칙

### 컴포넌트별 명명 규칙
- **PC (Presentation Controller)**: `XXXPC` - 화면 컨트롤러
- **DC (Data Controller)**: `XXXDC` - API 컨트롤러  
- **DTO**: `XXXDto` - 데이터 전송 객체
- **Mapper**: `XXXMapper` - MyBatis 매퍼
- **Service**: `XXXService` / `XXXServiceImpl` - 비즈니스 로직
- **Repository**: `XXXRepository` - 데이터 액세스
- **Entity**: `XXX` - 엔티티 클래스 (명사형, 단수형)
- **Exception**: `XXXException` - 예외 클래스
- **Util**: `XXXUtil` - 유틸리티 클래스

## 🔧 사용 방법

### 1. **코드 품질 검사**
```bash
# 전체 품질 검사
mvn clean install -P quality

# 개별 도구 실행
mvn checkstyle:check
mvn spotbugs:check
mvn jacoco:report
```

### 2. **Git Hooks**
```bash
# pre-commit hook 활성화
chmod +x .git/hooks/pre-commit

# 수동 실행
.git/hooks/pre-commit
```

### 3. **IDE 설정**
- IntelliJ IDEA: `ide-settings.md` 참조
- Eclipse: `ide-settings.md` 참조
- VS Code: `ide-settings.md` 참조

## 📊 코드 품질 지표

### 목표 지표
- **Checkstyle**: 0 violations
- **SpotBugs**: 0 bugs
- **JaCoCo**: 80% 이상 커버리지
- **SonarQube**: A 등급

### 모니터링
```bash
# 품질 리포트 생성
mvn clean install -P quality

# 커버리지 리포트 확인
open target/site/jacoco/index.html

# SpotBugs 리포트 확인
open target/spotbugsXml.xml
```

## 🚀 개발 워크플로우

### 1. **새로운 기능 개발**
```bash
# 브랜치 생성
git checkout -b feature/새로운기능

# 개발 작업
# ... 코드 작성 ...

# 품질 검사
mvn clean install -P quality

# 커밋
git add .
git commit -m "feat: 새로운 기능 추가"
```

### 2. **코드 리뷰**
- [ ] Checkstyle 규칙 준수
- [ ] SpotBugs 이슈 해결
- [ ] 테스트 커버리지 확인
- [ ] Javadoc 작성 완료
- [ ] 명명 규칙 준수

### 3. **배포 전 검사**
```bash
# 전체 검사 실행
mvn clean install -P quality

# 테스트 실행
mvn test

# 빌드 검증
mvn package
```

## 📚 참고 자료

- [MBC Java 개발 가이드](zDoc/mbc-java-guide.md)
- [프로젝트 구조 문서](PROJECT_STRUCTURE.md)
- [빌드 문제 해결](BUILD_TROUBLESHOOTING.md)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

## 🔧 문제 해결

### 자주 발생하는 문제

#### 1. **Checkstyle 오류**
```bash
# 특정 규칙 무시
@SuppressWarnings("checkstyle:methodname")
```

#### 2. **SpotBugs 경고**
```bash
# 특정 경고 무시
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
```

#### 3. **커버리지 부족**
- 테스트 케이스 추가
- 엣지 케이스 고려
- 예외 상황 테스트

## 📞 지원

코드 품질 관련 문의사항이 있으시면 개발팀에 문의해주세요.

---

**마지막 업데이트**: 2024-01-01
**버전**: 1.0.0 