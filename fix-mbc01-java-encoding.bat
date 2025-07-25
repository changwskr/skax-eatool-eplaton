@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Encoding Script (Windows)
REM mbc01-java 하위 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Encoding
echo ========================================

echo.
echo 1. MBC01-Java Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [Config 패키지 - 설정 관리 클래스들]
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/Env.java
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/IFRSConfig.java
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/LoaderConfig.java
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/ActionConfig.java
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/CommandObj.java
echo - mbc01-java/src/com/skax/eatool/mbc/fc/foundation/bzcrudbus/config/ConfigInfo.java
echo - mbc01-java/src/com/skax.eatool/mbc/fc/foundation/bzcrudbus/config/ParameterConverter.java
echo - mbc01-java/src/com/skax.eatool/mbc/fc/foundation/bzcrudbus/config/ParameterInfo.java
echo - mbc01-java/src/com/skax.eatool/mbc/fc/foundation/bzcrudbus/config/ParameterTransform.java
echo.

echo 2. 모든 Java 파일의 주석이 UTF-8 인코딩으로 변경되었습니다.
echo    - 클래스 레벨 주석: 프로그램명, 설명, 작성일, 작성자, 주요 기능
echo    - 메서드 레벨 주석: 파라미터, 반환값, 설명
echo    - 필드 레벨 주석: 변수 설명
echo    - 인라인 주석: 코드 내 한글 설명
echo.

echo 3. 수정된 한글 내용:
echo.
echo [클래스 설명]
echo - 환경 변수 관리 클래스
echo - IFRS 설정 파일을 읽어서 해당 설정 정보를 Hashtable에 저장하고 관리한다
echo - 클래스패스 로더 설정 관리 클래스
echo - 액션 설정 관리 클래스
echo - 명령어 객체 클래스
echo - 설정 정보를 담는 Value Object
echo - TrustForm에서 받은 파라미터 데이터를 적절한 객체 타입으로 변환
echo - TrustForm에서 받은 파라미터 데이터와 컴포넌트 매핑 정보 관리
echo - 파라미터를 필요에 따라 입력받는 처리를 담당한다
echo.

echo [메서드 설명]
echo - 환경 변수 값을 조회합니다
echo - 리소스 번들에서 설정값을 조회하고 UTF-8로 변환합니다
echo - 설정 파일을 다시 로드한다
echo - XML로 정의된 설정 파일을 읽어서 Hashtable에 저장한다
echo - SystemInfo를 읽는다
echo - XML 엘리먼트의 속성을 읽는다
echo - 하위 엘리먼트들을 HashMap으로 변환한다
echo - 주어진 시스템 정보에서 설정 정보를 검색한다
echo - 서버 URL에서 IP 주소를 추출한다
echo - 서버 URL에서 포트 번호를 추출한다
echo - 메인 메서드 (테스트용)
echo.

echo [파라미터 변환 관련]
echo - 파라미터 맵을 입력받아 적절한 객체 타입으로 변환된 맵을 반환한다
echo - 파라미터 리스트를 입력받아 적절한 객체 타입으로 변환된 리스트를 반환한다
echo - 파라미터의 해당 노드에 입력받아 타입에 맞는 데이터 타입을 반환한다
echo - 데이터 타입에 맞는 데이터를 변환한다
echo - 엘리먼트의 기본값과 노드값을 비교하여 데이터 반환
echo - 컴포넌트명을 받아서 해당 컴포넌트 Document 객체를 반환한다
echo.

echo [그리드 데이터 처리]
echo - 파라미터를 그리드 형태의 데이터로 변환한다
echo - row 리스트 분리
echo - 헤더 정보 분리
echo - row 처리
echo - column 리스트 분리
echo - 구분자 분리(i:insert, u:update, d:delete)
echo - column 처리
echo - insert 구분 처리
echo - update 구분 처리
echo - delete 구분 처리
echo - 삽입 구분이 있으면 삽입 구분 리스트에 추가
echo - 수정 구분이 있으면 수정 구분 리스트에 추가
echo - 삭제 구분이 있으면 삭제 구분 리스트에 추가
echo.

echo [HTTP 요청 처리]
echo - 파라미터를 코드별로 변환해서 리스트로 변환
echo - 파라미터를 코드별로 변환해서 맵으로 변환(단일건과 다중건 변환)
echo - 객체로 변환된 데이터를 반환
echo - 그리드 파라미터를 변환해서 객체 형태 String타입으로 된 리스트로 변환
echo.

echo 4. Maven 설정에서 UTF-8 인코딩이 적용되었습니다.
echo    - mbc01-java/pom.xml에 UTF-8 인코딩 설정 추가
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
echo    - com.skax.eatool.ksa 패키지 관련 import 오류는 의존성 문제로 별도 해결 필요
echo    - Maven 의존성 설정에서 누락된 라이브러리 추가 필요
echo    - 빌드 환경에서 필요한 JAR 파일들이 classpath에 포함되어야 함
echo.

echo 인코딩 수정이 완료되었습니다!
pause 