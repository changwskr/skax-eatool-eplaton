@echo off
REM SKAX Project Eplaton - Fix MBC01-Java TCF Encoding Script - Windows
REM mbc01-java tcf 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java TCF Encoding
echo ========================================

echo.
echo 1. MBC01-Java TCF 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 수정된 파일 목록:
echo.

echo [TCF 패키지 - 트랜잭션 제어 프레임워크]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/AbstractTCF.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/TCF.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/BTF.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/STF.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/ETF.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/BTF_WP.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/BTF_SP.java
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
echo [AbstractTCF.java]
echo - TCF 추상 클래스
echo - TCF 프레임워크의 추상 클래스
echo - 컨텍스트 정보 관리
echo - STF, BTF, ETF 구조의 기본 정의
echo.
echo [TCF.java]
echo - TCF (Transaction Control Framework) 클래스
echo - 트랜잭션 제어 프레임워크
echo - STF, BTF, ETF 순차 실행
echo - 에러 처리 및 로깅
echo - WAF/Server 모드 지원
echo.
echo [BTF.java]
echo - BTF (Business Transaction Framework) 클래스
echo - 해외 은행 업무 처리를 위한 비즈니스 트랜잭션 프레임워크
echo - 트랜잭션 모드 관리 (WAF/Server)
echo - 에러 처리 및 로깅
echo - BTF_SP, BTF_WP 실행 관리
echo.
echo [STF.java]
echo - STF (Start Transaction Framework) 클래스
echo - 트랜잭션을 시작하기 위한 기본적인 설정을 담당
echo - 로그인 상태 확인 (WAF 모드)
echo - 에러 코드 초기화
echo - 시스템 날짜/시간 설정
echo - 트랜잭션 제어 및 로깅
echo.
echo [ETF.java]
echo - ETF (End Transaction Framework) 클래스
echo - Transaction Control Framework에서 트랜잭션을 완료
echo - 트랜잭션 완료 처리
echo - 로그 기록 및 정리
echo - 에러 처리 및 정리
echo - 패킷 이동 및 정리
echo.
echo [BTF_WP.java]
echo - BTF_WP (Business Transaction Framework - Web Processing) 클래스
echo - WAF 모드에서 비즈니스 로직을 처리
echo - PC 컴포넌트 호출 및 결과 처리
echo - XML 에러 체크 및 처리
echo - 에러 메시지 처리
echo - TPM 통신 처리
echo.
echo [BTF_SP.java]
echo - BTF_SP (Business Transaction Framework - Server Processing) 클래스
echo - Server 모드에서 비즈니스 로직을 처리
echo - PC 컴포넌트 호출 및 결과 처리
echo - TPM 통신 처리
echo - 에러 처리 및 예외 관리
echo - DC/PC 컴포넌트 간 데이터 전달
echo.

echo ========================================
echo TCF 프레임워크 구조:
echo ========================================
echo.
echo 1. TCF (Transaction Control Framework)
echo - 전체 트랜잭션을 처리하는 메인 프레임워크
echo - STF, BTF, ETF를 순차적으로 실행
echo - 에러 처리 및 로깅 관리
echo.
echo 2. STF (Start Transaction Framework)
echo - 트랜잭션 시작 전 기본 설정
echo - 로그인 상태 확인 (WAF 모드)
echo - 에러 코드 초기화 (I0000000)
echo - 시스템 날짜/시간 설정
echo.
echo 3. BTF (Business Transaction Framework)
echo - 비즈니스 로직 처리
echo - WAF 모드: BTF_WP 실행
echo - Server 모드: BTF_SP 실행
echo - PC 컴포넌트 호출 및 결과 처리
echo.
echo 4. ETF (End Transaction Framework)
echo - 트랜잭션 완료 처리
echo - 로그 기록 및 정리
echo - 패킷 이동 및 정리
echo - TPM 서비스 정보 설정
echo.

echo ========================================
echo 기술 스택:
echo ========================================
echo.
echo - TCF (Transaction Control Framework)
echo - TPM (Transaction Processing Monitor)
echo - IFRS 로깅 시스템
echo - WAF/Server 모드 지원
echo - XML 처리 및 에러 체크
echo - 예외 처리 및 에러 코드 관리
echo - 싱글톤 패턴
echo.

echo ========================================
echo 다음 단계:
echo ========================================
echo.
echo 1. IDE에서 프로젝트를 다시 로드하여 변경사항 확인
echo 2. 빌드 테스트 실행
echo 3. 한글 주석이 올바르게 표시되는지 확인
echo 4. TCF 프레임워크 동작 테스트
echo 5. 필요시 추가 인코딩 수정 작업 진행
echo.

echo ========================================
echo 주의사항:
echo ========================================
echo.
echo 1. 모든 파일이 UTF-8 인코딩으로 저장되었습니다.
echo 2. IDE 설정에서 파일 인코딩을 UTF-8로 설정하세요.
echo 3. 빌드 도구(Maven/Gradle)에서도 UTF-8 인코딩을 사용하세요.
echo 4. 새로운 파일 생성 시 반드시 UTF-8 인코딩을 사용하세요.
echo 5. TCF 프레임워크의 STF, BTF, ETF 순서를 유지하세요.
echo 6. WAF/Server 모드에 따른 적절한 컴포넌트 호출을 확인하세요.
echo.

echo ========================================
echo 인코딩 수정 완료!
echo ========================================
echo.
pause 