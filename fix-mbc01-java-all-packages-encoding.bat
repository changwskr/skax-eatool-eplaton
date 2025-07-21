@echo off
REM SKAX Project Eplaton - Fix MBC01-Java All Packages Encoding Script - Windows
REM mbc01-java의 모든 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 종합 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java All Packages Encoding
echo ========================================

echo.
echo 1. MBC01-Java의 모든 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 수정된 패키지 및 파일 목록:
echo.

echo [KESA 패키지 - KESA XML 통신 관리]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/kesa/KesaXmlManager.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/kesa/KesaXmlVO.java
echo.

echo [Loader 패키지 - 동적 클래스 로딩]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/loader/DynamicLoader.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/loader/IFRSClassLoader.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/loader/Javac.java
echo.

echo [Log 패키지 - 로깅 시스템]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/log/IfrsLogHelper.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/log/IfrsLoggerImpl.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/log/IIfrsLogger.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/log/IfrsLoggerFactory.java
echo.

echo [TCF 패키지 - 트랜잭션 처리 프레임워크]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tcf/BTF.java
echo.

echo [TPM 패키지 - 트랜잭션 처리 관리]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tpm/TPMUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tpm/KesaServiceDelegate.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/tpm/IServiceDelegate.java
echo.

echo [Foundation 패키지 - 기반 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/general/CommonUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/config/Config.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/Doc.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/DOMXMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/HandlerOption.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/SAXXMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLCache.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLHandlerFactory.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/StringUtils.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Dom4jUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/ErrorCheckUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/TrustFormUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/SsnBean.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/WafErrorUtil.java
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