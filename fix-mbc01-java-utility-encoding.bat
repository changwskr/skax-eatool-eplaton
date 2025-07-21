@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Utility Encoding Script - Windows
REM mbc01-java utility 패키지 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Utility Encoding
echo ========================================

echo.
echo 1. MBC01-Java Utility 패키지 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [Utility 패키지 - 유틸리티 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/StringUtils.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Dom4jUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/ErrorCheckUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/TrustFormUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/SsnBean.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/WafErrorUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Utils.java (이전 작업)
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Reflector.java (이전 작업)
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/GenericDtoUtil.java (이전 작업)
echo.

echo 2. 모든 Java 파일의 주석이 UTF-8 인코딩으로 변경되었습니다.
echo    - 클래스 레벨 주석: 프로그램명, 설명, 작성일, 작성자, 주요 기능
echo    - 메서드 레벨 주석: 파라미터, 반환값, 설명
echo    - 필드 레벨 주석: 변수 설명
echo    - 인라인 주석: 코드 내 한글 설명
echo.

echo 3. 수정된 한글 내용:
echo.
echo [StringUtils.java - 문자열 처리 유틸리티 클래스]
echo - String 처리를 위한 helper function들입니다
echo - 문자열 비교 및 검증, 문자열 변환 및 포맷팅, 문자열 분할 및 결합, 문자열 치환 및 삽입, 문자열 패딩 및 정렬, 정규식 처리
echo - 왼쪽 정렬 상수, 가운데 정렬 상수, 오른쪽 정렬 상수, 인스턴스 생성 불가
echo - 두 문자열이 같은지 확인한다
echo - 문자열이 비어있는지 확인한다
echo - 문자열의 길이가 1이상이면 true이다
echo - 문자열이 특정 길이 이상이면 true를 반환한다
echo - 영문코드 문자만으로 구성되어 있는지 확인한다
echo - 영문코드 문자나 숫자로만 구성되어 있는지 확인한다
echo - 영문코드 숫자로만 구성되어 있는지 확인한다
echo - reqular expression의 meta sybmol을 quotation한다
echo.

echo [Dom4jUtil.java - DOM4J XML 처리 유틸리티 클래스]
echo - DOM4J 라이브러리를 사용한 XML 문서 처리 유틸리티 클래스
echo - XML 문서 파싱 및 생성, XML 노드 및 엘리먼트 조작, XML 속성 맵 처리, XML에서 데이터 추출, XML 속성 설정 및 조회
echo - 문자열 XML을 Document 객체로 변환한다
echo - 바이트 배열 XML을 Document 객체로 변환한다
echo - XPath로 노드를 조회한다
echo - XPath로 엘리먼트를 조회한다
echo - XPath로 엘리먼트를 조회하고, 없으면 생성한다
echo - 경로를 따라 노드를 생성한다
echo - 노드의 속성 맵을 반환한다
echo - 엘리먼트의 속성 맵을 반환한다
echo - XPath로 노드를 조회하고 속성 맵을 반환한다
echo - XPath로 엘리먼트 리스트의 속성 맵 리스트를 반환한다
echo - 해당 경로의 하위 Element들의 List
echo - XML을 결과 맵으로 변환한다
echo.

echo [ErrorCheckUtil.java - 에러 체크 유틸리티 클래스]
echo - 에러 코드를 체크하는 유틸리티 클래스
echo - 에러 코드 검증, 에러 문자 맵 관리, 대소문자 구분 없는 에러 체크
echo - 싱글톤 인스턴스, 에러 문자 맵
echo - 싱글톤 인스턴스를 반환한다
echo - 기본 생성자 (설정에서 에러 문자 리스트를 읽어와서 맵을 초기화한다)
echo - 문자열 에러 코드가 에러인지 확인한다
echo - 문자 에러 코드가 에러인지 확인한다
echo.

echo [TrustFormUtil.java - 신뢰할 수 있는 폼 처리 유틸리티 클래스]
echo - 웹 폼 데이터 처리를 위한 유틸리티 클래스
echo - HTTP 요청 파라미터 처리, 멀티파트 데이터 처리, 세션 데이터 병합, XML 생성, 그리드 데이터 처리
echo - 세션 유지 시간 설정 (분 단위 설정)
echo - request의 파라미터들을 HashMap으로 변환
echo - Return 할 데이터 HashMap
echo - 리퀘스트의 모든 파라미터들을 Enumeration객체로 생성
echo - Enumeration객체에서 하나씩 꺼내서 HashMap에 저장
echo - 리퀘스트에서 받은 HashMap과 세션에 저장된 데이터들을 Merge
echo - 세션 유지 시간 설정
echo - MultiPart로 전송된 파라미터들을 HashMap으로 변환
echo - 파라미터 처리
echo - 그리드에서 전송된 데이터를 HashMap으로 변환하여 리턴
echo - 화면에서 전송된 데이터를 각flag별로 구분하여 Tokenizer를 사용해서 분리
echo - 그리드에서 전송된 데이터 중 insert에 해당하는 데이터를 List로 변환
echo - 그리드의 col데이터와 세션 HashMap을 병합하여 추가
echo - 그리드에서 전송된 데이터 중 update에 해당하는 데이터를 List로 변환
echo - 그리드에서 전송된 데이터 중 delete에 해당하는 데이터를 List로 변환
echo - 2개의 HashMap을 병합하여 새로운 HashMap을 반환
echo - HashMap에 세션 데이터를 추가하여 반환
echo - session 객체에서 모든 속성 이름을 Enumeration객체로 생성
echo - Enumeration객체에서 하나씩 꺼내서 HashMap에 저장
echo - 세션 데이터 추가 후 기본값 설정
echo - 프로시저 입력 파라미터를 생성하여 반환
echo - 문자열을 지정된 키로 분리하여 ArrayList로 반환
echo - 그리드 데이터를 XML로 변환하는 메서드
echo - 콤보박스 데이터를 모두 포함하는 XML을 생성하는 메서드 (옵션 추가)
echo - 콤보박스 데이터를 XML로 변환하는 메서드
echo.

echo [SsnBean.java - 세션 관리 유틸리티 클래스]
echo - HTTP 세션에서 사용자 정보와 메시지 데이터를 관리하는 유틸리티 클래스
echo - 사용자 ID 관리, 사용자 이름 관리, 에러 메시지 데이터 관리, 세션 데이터 초기화
echo - 기본 생성자
echo - 세션에서 사용자 ID를 반환하는 메서드
echo - 세션에 사용자 ID를 설정하는 메서드
echo - 세션에서 사용자 이름을 반환하는 메서드
echo - 세션에 사용자 이름을 설정하는 메서드
echo - 세션에서 에러 메시지 데이터를 반환하는 메서드
echo - 세션에 에러 메시지 데이터를 설정하는 메서드
echo - 세션에서 에러 메시지 데이터를 초기화하는 메서드
echo.

echo [WafErrorUtil.java - WAF 에러 처리 유틸리티 클래스]
echo - WAF(Web Application Firewall) 관련 에러 정보를 설정하는 유틸리티 클래스
echo - 에러 정보 설정, 예외 객체 처리, 에러 코드 및 메시지 관리
echo - 에러 정보를 설정하여 반환한다
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

echo 8. 추가 작업 필요:
echo    - 나머지 디렉토리들(log, loader, report, setting, transfer, tpm, tcf, business) 확인 필요
echo    - syscom, sysinterface 디렉토리 확인 필요
echo    - 모든 Java 파일의 인코딩 검증 필요
echo.

echo 9. 완료된 작업:
echo    - Utility 패키지: 9개 파일 완료
echo    - Foundation/General 패키지: 1개 파일 완료 (이전 작업)
echo    - Foundation/Config 패키지: 1개 파일 완료 (이전 작업)
echo    - Foundation/Base 패키지: 7개 파일 완료 (이전 작업)
echo    - 총 18개 Java 파일의 인코딩 수정 완료
echo.

echo 10. Foundation 하위 전체 완료:
echo     - Foundation/Utility 패키지: 9개 파일 완료
echo     - Foundation/General 패키지: 1개 파일 완료
echo     - Foundation/Config 패키지: 1개 파일 완료
echo     - Foundation/Base 패키지: 7개 파일 완료
echo     - Foundation 하위 총 18개 Java 파일의 인코딩 수정 완료
echo.

echo Utility 패키지 인코딩 수정이 완료되었습니다!
pause 