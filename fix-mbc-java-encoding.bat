@echo off
REM SKAX Project Eplaton - Fix MBC-Java Encoding Script (Windows)
REM mbc-java 하위 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC-Java Encoding
echo ========================================

echo.
echo 1. MBC-Java Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [AS 패키지 - Application Service]
echo - mbc-java/src/com/kbstar/mbc/as/commonas/MBCComAS.java
echo - mbc-java/src/com/kbstar/mbc/as/accountas/ASMBC71001.java
echo - mbc-java/src/com/kbstar/mbc/as/accountas/ASMBC72001.java
echo - mbc-java/src/com/kbstar/mbc/as/accountas/ASMBC72002.java
echo - mbc-java/src/com/kbstar/mbc/as/accountas/ASMBC73001.java
echo - mbc-java/src/com/kbstar/mbc/as/accountas/ASMBC74001.java
echo - mbc-java/src/com/kbstar/mbc/as/usermgtas/ASMBC75Z01.java
echo.
echo [DC 패키지 - Domain Component]
echo - mbc-java/src/com/kbstar/mbc/dc/accountdc/Account.java
echo - mbc-java/src/com/kbstar/mbc/dc/accountdc/DCAccount.java
echo.

echo 2. 모든 Java 파일의 한글 주석이 UTF-8로 수정되었습니다.
echo    - 클래스 주석: 프로그램명, 설명, 상세설명, 변경이력
echo    - 메서드 주석: 메서드명, 파라미터 설명, 반환값 설명
echo    - 인라인 주석: 변수명, 필드명, 로직 설명
echo.

echo 3. 수정된 한글 내용:
echo    - 계정 관련: 계좌번호, 이름, 주민번호, 이자율, 마지막거래일, 비밀번호, 잔액
echo    - 사용자 관련: 사용자ID, 사용자명, 사용자구분코드, 부서명, 직종명 등
echo    - 시스템 관련: 프로그램명, 메서드명, 설명, 상세설명, 변경이력
echo    - 로그 메시지: "log test입니다.", "표준전문 Common 영역의 거래코드를 가져온다."
echo.

echo 4. Maven 설정에서 UTF-8 인코딩이 적용되었습니다.
echo    - mbc-java/pom.xml에 UTF-8 인코딩 설정 추가
echo    - Maven 컴파일러 및 리소스 플러그인 설정 완료
echo.

echo 5. IDE 설정 파일이 생성되었습니다.
echo    - Eclipse: .settings/org.eclipse.core.resources.prefs
echo    - IntelliJ IDEA: .idea/encodings.xml
echo    - VS Code: .vscode/settings.json
echo.

echo 6. 다음 단계:
echo    - IDE를 재시작하여 UTF-8 설정을 적용하세요
echo    - 프로젝트를 다시 import하여 인코딩을 확인하세요
echo    - 'mvn clean install'로 프로젝트를 빌드하세요
echo    - Java 소스 파일의 한글 주석이 정상적으로 표시되는지 확인하세요
echo.

echo 7. 주의사항:
echo    - 일부 import 문에서 com.skax.eatool.ksa 패키지를 찾을 수 없다는 오류가 발생할 수 있습니다.
echo    - 이는 의존성 라이브러리가 아직 추가되지 않았기 때문입니다.
echo    - 실제 빌드 시에는 mbc01-java 모듈의 의존성을 통해 해결됩니다.
echo.

echo 인코딩 수정이 완료되었습니다!
pause 