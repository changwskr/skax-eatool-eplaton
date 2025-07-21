@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Business Encoding Script - Windows
REM mbc01-java business 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Business Encoding
echo ========================================

echo.
echo 1. MBC01-Java Business 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 수정된 파일 목록:
echo.

echo [Business 패키지 - 비즈니스 로직 계층]
echo.
echo [AS 패키지 - Application Service]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/business/as/ASComExec.java
echo.
echo [DC 패키지 - Domain Component]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/business/dc/DCComExec.java
echo.
echo [IC 패키지 - Integration Component]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/business/ic/ICComExec.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/business/ic/IICComExec.java
echo.
echo [PC 패키지 - Process Component]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/business/pc/PCComExec.java
echo.

echo ========================================
echo 수정 내용 요약:
echo ========================================
echo.
echo 1. 모든 Java 소스 파일의 인코딩을 UTF-8로 변환
echo 2. 깨진 한글 주석을 올바른 UTF-8 인코딩으로 수정
echo 3. 클래스 레벨 JavaDoc 주석 추가
echo 4. 메서드 및 필드 레벨 주석 개선
echo 5. 일관된 주석 스타일 적용
echo.

echo ========================================
echo 수정된 클래스 상세 정보:
echo ========================================
echo.
echo [ASComExec.java]
echo - AS 공통 실행 클래스
echo - TCF를 통한 비즈니스 로직 실행
echo - TPM을 통한 데이터 수신 및 전송
echo - 에러 처리 및 예외 관리
echo.
echo [DCComExec.java]
echo - DC 공통 실행 클래스
echo - 데이터베이스 접근 및 CRUD 작업
echo - iBATIS SQL 매퍼를 통한 데이터베이스 접근
echo - 데이터 조회 (Map, String, List 형태)
echo - 데이터 저장 (INSERT, UPDATE, DELETE)
echo - 싱글톤 패턴 구현
echo.
echo [ICComExec.java]
echo - IC 공통 실행 클래스
echo - DC와 PC 사이의 중간 계층 역할
echo - 데이터 조회 (단일 데이터, 리스트)
echo - 데이터 저장 (INSERT, UPDATE, DELETE)
echo - 그리드 데이터 처리 (CRUD 작업)
echo.
echo [IICComExec.java]
echo - IC 공통 실행 인터페이스
echo - Integration Component 공통 실행을 위한 인터페이스
echo - 데이터 조회, 저장, 그리드 처리 메서드 정의
echo.
echo [PCComExec.java]
echo - PC 공통 실행 클래스
echo - 비즈니스 로직 처리와 DC 계층 연동
echo - 데이터 조회 (단일 데이터, 리스트)
echo - 데이터 저장 (INSERT, UPDATE, DELETE)
echo - 그리드 데이터 처리 (CRUD 작업)
echo.

echo ========================================
echo 아키텍처 계층 구조:
echo ========================================
echo.
echo AS (Application Service) - 애플리케이션 서비스 계층
echo - TCF를 통한 비즈니스 로직 실행
echo - TPM을 통한 데이터 전송
echo.
echo PC (Process Component) - 프로세스 컴포넌트 계층
echo - 비즈니스 로직 처리
echo - DC 계층과의 연동
echo.
echo IC (Integration Component) - 통합 컴포넌트 계층
echo - DC와 PC 사이의 중간 계층
echo - 데이터 변환 및 처리
echo.
echo DC (Domain Component) - 도메인 컴포넌트 계층
echo - 데이터베이스 접근
echo - CRUD 작업 수행
echo.

echo ========================================
echo 다음 단계:
echo ========================================
echo.
echo 1. IDE에서 프로젝트를 다시 로드하여 변경사항 확인
echo 2. 빌드 테스트 실행
echo 3. 한글 주석이 올바르게 표시되는지 확인
echo 4. 필요시 추가 인코딩 수정 작업 진행
echo.

echo ========================================
echo 주의사항:
echo ========================================
echo.
echo 1. 모든 파일이 UTF-8 인코딩으로 저장되었습니다.
echo 2. IDE 설정에서 파일 인코딩을 UTF-8로 설정하세요.
echo 3. 빌드 도구(Maven/Gradle)에서도 UTF-8 인코딩을 사용하세요.
echo 4. 새로운 파일 생성 시 반드시 UTF-8 인코딩을 사용하세요.
echo.

echo ========================================
echo 인코딩 수정 완료!
echo ========================================
echo.
pause 