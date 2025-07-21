# SKAX-EATOOL-EPLATON 개발 아키텍처 가이드

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

> 본 가이드는 SKAX-EATOOL-EPLATON 프로젝트의 개발 표준과 워크플로우를 정리한 문서입니다. 추가 문의는 개발팀에 연락해 주세요. 