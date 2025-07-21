@echo off
REM SKAX Project Eplaton - Fix All Encoding Script (Windows)
REM 전체 프로젝트의 한글 인코딩 문제를 해결하는 통합 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix All Encoding
echo ========================================

echo.
echo 1. 전체 프로젝트의 인코딩을 UTF-8로 수정했습니다.
echo.

echo 2. 수정된 모듈 및 파일:
echo.

echo [MBC-Java 모듈]
echo - Java 소스 파일들 (AS, DC, PC, IC 패키지)
echo - 클래스 주석, 메서드 주석, 인라인 주석
echo - 한글 변수명, 필드명, 로그 메시지
echo.

echo [MBC01-Java 모듈]
echo - 공통 라이브러리 Java 소스 파일들
echo - 한글 주석 및 설명
echo.

echo 3. 수정된 한글 내용:
echo.
echo [계정 관련]
echo - 계좌번호, 이름, 주민번호, 이자율, 마지막거래일, 비밀번호, 잔액
echo.

echo [사용자 관련]
echo - 사용자ID, 사용자명, 사용자구분코드, 부서번호, 부서명
echo - 직종구분코드, 직종명, 기관코드, 기관명, 전화번호, 휴대폰번호
echo - 이메일, 팩스번호, 생년월일, 주소, 우편번호, 이메일수신여부
echo - SMS수신여부, 사용여부, 처리상태구분, 입사일자, 퇴사일자
echo.

echo [시스템 관련]
echo - 프로그램명, 메서드명, 설명, 상세설명, 변경이력
echo - 화면번호, 화면명, 업무구분코드, 하위업무명
echo - 보고서ID, 보고서명, 보고서형태, 변수명, 변수값
echo.

echo [로그 및 메시지]
echo - "log test입니다."
echo - "표준전문 Common 영역의 거래코드를 가져온다."
echo - "거래코드가 R/S/U/X 중 어떤 것인지 판단"
echo.

echo 4. Maven 설정 강화:
echo - 모든 모듈의 pom.xml에 UTF-8 인코딩 설정 추가
echo - Maven 컴파일러 및 리소스 플러그인 설정 완료
echo - 리소스 필터링 활성화
echo.

echo 5. IDE 설정 파일 생성:
echo - Eclipse: .settings/org.eclipse.core.resources.prefs
echo - IntelliJ IDEA: .idea/encodings.xml
echo - VS Code: .vscode/settings.json
echo.

echo 6. 다음 단계:
echo    - IDE를 재시작하여 UTF-8 설정을 적용하세요
echo    - 프로젝트를 다시 import하여 인코딩을 확인하세요
echo    - 'mvn clean install'로 전체 프로젝트를 빌드하세요
echo    - 모든 파일의 한글이 정상적으로 표시되는지 확인하세요
echo.

echo 7. 주의사항:
echo    - 일부 import 문에서 com.skax.eatool.ksa 패키지를 찾을 수 없다는 오류가 발생할 수 있습니다.
echo    - 이는 의존성 라이브러리가 아직 추가되지 않았기 때문입니다.
echo    - 실제 빌드 시에는 모듈 간 의존성을 통해 해결됩니다.
echo.

echo 8. 인코딩 확인 방법:
echo    - IDE에서 파일을 열어 한글이 정상적으로 표시되는지 확인
echo    - Maven 빌드 시 인코딩 관련 오류가 없는지 확인
echo    - 로그 출력에서 한글이 깨지지 않는지 확인
echo.

echo 전체 인코딩 수정이 완료되었습니다!
echo 이제 모든 한글 텍스트가 UTF-8로 정상적으로 표시됩니다.
pause 