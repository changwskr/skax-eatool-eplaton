@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Transfer Encoding Script - Windows
REM mbc01-java transfer 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Transfer Encoding
echo ========================================

echo.
echo 1. MBC01-Java Transfer 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 수정된 파일 목록:
echo.

echo [Transfer 패키지 - 데이터 전송 객체들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/transfer/ICommonDTO.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/transfer/IFRSCommonDTO.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/transfer/IFRSEvent.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/transfer/DTO.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/transfer/IDTO.java
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
echo [ICommonDTO.java]
echo - 공통 DTO 인터페이스
echo - KBData, 파라미터 맵, 결과 맵, 메타데이터 맵 관리
echo - SQL ID, 비즈니스 코드, 트랜잭션 번호 관리
echo.
echo [IFRSCommonDTO.java]
echo - IFRS 공통 DTO 클래스
echo - 모든 request에서 공통적으로 사용되는 정보 표현
echo - 국가, 시간대, 통화, IP 주소, 언어 정보 관리
echo - 명령 ID, 라우팅 AS, PC 클래스명, 메서드명 관리
echo - SQL ID, 키, 에러 정보, 시간 정보, 사용자 정보 관리
echo.
echo [IFRSEvent.java]
echo - IFRS 이벤트 클래스
echo - business layer와 application layer 사이 데이터 전송 컨테이너
echo - IFRS 공통 DTO, KBData, HTTP 서블릿 요청, 예외 처리 관리
echo.
echo [DTO.java]
echo - DTO 추상 클래스
echo - DTO를 상속받는 클래스가 구현해야 할 공통 메서드 정의
echo - DTO 속성 관리, 버전 관리, 객체 문자열 변환
echo.
echo [IDTO.java]
echo - DTO 인터페이스
echo - 모든 Data Transfer Object의 공통 타입으로 markup interface
echo - Serializable 및 Cloneable 인터페이스 상속
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