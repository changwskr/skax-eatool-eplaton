# SKAX-EATOOL-EPLATON 개발 아키텍처 & 접근 가이드

## 0. 접근 가이드 (Access Guide)

### 0.1. 저장소 접근
- **Git 저장소 주소**: (사내 GitLab/GitHub 등 실제 주소 기입)
- **권한**: 개발팀/협력사 계정 필요, 접근 권한 요청은 관리자에게 문의
- **Clone 명령어**:
  ```bash
  git clone <저장소 주소>
  cd skax-eatool-eplaton
  ```

### 0.2. 로컬 개발 환경 세팅
- **필수 설치**: Java 11+, Maven 3.6+, Git, IDE(IntelliJ/Eclipse 등)
- **환경 변수**:
  - JAVA_HOME, MAVEN_HOME, PATH 설정
- **IDE Import**:
  - IntelliJ: Open → 폴더 선택 → Maven 프로젝트 자동 인식
  - Eclipse: Import → Existing Maven Projects
- **인코딩**: UTF-8 (IDE, Maven 모두)

### 0.3. 모듈별 접근 방법
- **도메인별 Java 모듈**: `ksa-java`, `kji-java`, `mbc01-java`, `mbc-java`
  - 각 모듈 디렉토리에서 독립적으로 개발/실행/테스트 가능
  - 예시:
    ```bash
    cd ksa-java
    mvn spring-boot:run
    ```
- **lib 모듈**: `ksa-lib`, `kji-lib`, `mbc01-lib`
  - 외부 JAR 관리 및 배포용, 직접 실행 X
  - 필요시 runtime 디렉토리에 JAR 수동 배치

### 0.4. 빌드/실행/테스트 접근
- **전체 빌드**: 프로젝트 루트에서
  ```bash
  mvn clean install -DskipTests
  # 또는
  ./build-step-by-step.bat
  ```
- **개별 모듈 빌드/실행**:
  ```bash
  cd mbc-java
  mvn clean install -DskipTests
  mvn spring-boot:run
  ```
- **테스트 실행**:
  ```bash
  mvn test
  ```
- **운영/개발/테스트 환경 분리**:
  ```bash
  mvn spring-boot:run -Dspring.profiles.active=dev
  mvn spring-boot:run -Dspring.profiles.active=prod
  ```

### 0.5. Web 접근 방법 (Web Access)
- **기본 접속 URL** (로컬 개발 환경 기준)
  | 애플리케이션 | URL | 설명 |
  |-------------|-----|------|
  | KSA         | http://localhost:8080/ksa      | KSA 도메인 서비스 |
  | KJI         | http://localhost:8080/kji      | KJI 도메인 서비스 |
  | MBC01       | http://localhost:8080/mbc01    | MBC01 공통 모듈 |
  | MBC         | http://localhost:8080/mbc      | 메인 애플리케이션 |
  | H2 콘솔     | http://localhost:8080/mbc/h2-console | 내장 DB 관리 |
  | Actuator    | http://localhost:8080/mbc/actuator  | 모니터링 엔드포인트 |

- **실행 방법**
  1. 해당 모듈 디렉토리에서 `mvn spring-boot:run` 실행
  2. 브라우저에서 위 URL로 접속
  3. (운영/테스트 환경은 포트 및 URL이 다를 수 있음)

- **인증/권한**
  - 기본적으로 Spring Security 적용 (로그인 필요)
  - 최초 계정/비밀번호는 운영팀 또는 README 참고
  - 권한별 접근 제한: 관리자/일반 사용자 등

- **문제 발생 시**
  - 포트 충돌: `server.port` 변경 또는 다른 포트로 실행
    ```bash
    mvn spring-boot:run -Dserver.port=8081
    ```
  - 방화벽/네트워크: 로컬 방화벽, VPN, 사내망 등 확인
  - 인증 오류: 계정/비밀번호 확인, 관리자 문의
  - 로그 확인: `logs/`, 콘솔 출력, Actuator 엔드포인트 활용

### 0.6. 문제 발생 시 접근
- **빌드 실패/의존성 문제**: `mvn dependency:tree`, `mvn clean install -U`, `build-step-by-step.bat` 활용
- **JAR 파일 잠금**: IDE 종료 후 빌드, install 방식 권장
- **인코딩 문제**: `fix-encoding.bat` 실행, IDE/Maven 인코딩 확인
- **지원 문서**: README.md, PROJECT_STRUCTURE.md, BUILD_TROUBLESHOOTING.md, ENCODING_GUIDE.md 참고

---

## 1. 개발 환경 및 도구

- **Java**: OpenJDK 11 이상
- **Maven**: 3.6 이상 (멀티모듈 지원)
- **IDE**: IntelliJ IDEA, Eclipse, VS Code 등
- **Git**: 버전 관리
- **빌드 스크립트**: build-step-by-step.bat
- **운영체제**: Windows, Linux, Mac 지원

---

## 2. 개발 브랜치 전략

- **main**: 운영(배포)용 브랜치
- **develop**: 통합 개발 브랜치
- **feature/**: 기능 개발 브랜치 (ex. feature/login)
- **bugfix/**: 버그 수정 브랜치 (ex. bugfix/login-error)
- **release/**: 릴리즈 준비 브랜치
- **hotfix/**: 운영 긴급 수정 브랜치

### 브랜치 워크플로우
1. main → develop 분기
2. develop → feature/bugfix 분기
3. feature/bugfix → develop 병합 (PR)
4. develop → release → main 병합
5. hotfix는 main에서 분기 후 main/develop에 병합

---

## 3. 코드 작성 및 커밋 규칙

### 3.1. 코딩 컨벤션
- **Java**: Google Java Style Guide 준수
- **XML**: 2칸 들여쓰기, UTF-8 인코딩
- **네이밍**: 클래스/메서드 camelCase, 상수는 대문자+언더스코어
- **주석**: 한글 주석 허용, Javadoc 적극 활용

### 3.2. 커밋 메시지 규칙
- `feat: 기능 추가`
- `fix: 버그 수정`
- `refactor: 리팩토링`
- `docs: 문서 변경`
- `test: 테스트 코드 추가/수정`
- `chore: 기타 변경사항`

예시:
```
feat: 사용자 로그인 기능 추가
fix: 인코딩 문제 해결
```

---

## 4. 개발/빌드/테스트/배포 프로세스

### 4.1. 개발
- 각 도메인별 Java 모듈에서 기능 개발
- 공통/외부 라이브러리는 lib 모듈의 runtime에 수동 배치
- IDE에서 모듈별로 독립 개발 가능

### 4.2. 빌드
- 전체 빌드: `mvn clean install -DskipTests` 또는 `build-step-by-step.bat`
- 개별 모듈 빌드: `mvn clean install -pl <module> -DskipTests`
- 빌드 산출물(JAR)은 로컬 Maven 저장소에 install

### 4.3. 테스트
- 단위 테스트: `mvn test` 또는 IDE 내장 테스트 실행
- 통합 테스트: 각 모듈별로 통합 테스트 코드 작성 권장
- 테스트 커버리지 도구(JaCoCo 등) 활용 가능

### 4.4. 배포
- 빌드된 JAR을 운영 환경에 배포
- 외부 JAR은 lib 모듈 runtime에서 관리
- 운영 배포 전 main 브랜치 기준으로 최종 빌드 권장

---

## 5. 의존성 및 모듈 관리

- lib 모듈은 외부 JAR 배포용, 직접적인 Maven dependency로 사용하지 않음
- Java 모듈은 필요한 경우 system scope 또는 install 방식으로 의존성 관리
- 의존성 충돌 발생 시 `mvn dependency:tree`로 확인

---

## 6. 인코딩 및 환경 설정

- 모든 소스/리소스 파일은 UTF-8 인코딩 권장
- IDE 및 Maven 빌드 시 인코딩 옵션 명시
- Windows 환경에서 인코딩 문제 발생 시 `fix-encoding.bat` 활용

---

## 7. 자주 발생하는 문제 및 해결법

- **JAR 파일 잠금**: IDE 종료 후 빌드/복사, install 방식 권장
- **의존성 충돌**: dependency:tree로 확인, 버전 명시
- **인코딩 문제**: 인코딩 옵션 확인, 스크립트 활용
- **빌드 실패**: 단계별 빌드, 캐시/로컬 저장소 정리

---

## 8. 참고 문서 및 지원

- [README.md] : 전체 아키텍처 및 빌드 가이드
- [PROJECT_STRUCTURE.md] : 모듈 구조 및 설명
- [BUILD_TROUBLESHOOTING.md] : 빌드 문제 해결
- [ENCODING_GUIDE.md] : 인코딩 문제 해결

---

> 본 가이드는 SKAX-EATOOL-EPLATON 프로젝트의 개발 표준과 워크플로우, 그리고 실질적인 접근 방법을 정리한 문서입니다. 추가 문의는 개발팀에 연락해 주세요. 