@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Foundation Encoding Script - Windows
REM mbc01-java foundation 하위 Java 소스 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Foundation Encoding
echo ========================================

echo.
echo 1. MBC01-Java Foundation 하위 Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [Foundation/General 패키지 - 공통 유틸리티 클래스]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/general/CommonUtil.java
echo.
echo [Foundation/Config 패키지 - 설정 관리 클래스]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/config/Config.java
echo.
echo [Foundation/Base 패키지 - XML 처리 기반 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/Doc.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/DOMXMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/HandlerOption.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/SAXXMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLCache.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLHandler.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/base/XMLHandlerFactory.java
echo.

echo 2. 모든 Java 파일의 주석이 UTF-8 인코딩으로 변경되었습니다.
echo    - 클래스 레벨 주석: 프로그램명, 설명, 작성일, 작성자, 주요 기능
echo    - 메서드 레벨 주석: 파라미터, 반환값, 설명
echo    - 필드 레벨 주석: 변수 설명
echo    - 인라인 주석: 코드 내 한글 설명
echo.

echo 3. 수정된 한글 내용:
echo.
echo [Foundation/General 패키지 - 클래스 설명]
echo - 공통 유틸리티 클래스
echo - ePlaton 해외 뱅킹 패키지의 공통 유틸리티 기능을 제공하는 클래스
echo - 문자열 처리 및 변환, 숫자 변환 및 포맷팅, 날짜/시간 처리, 파일 처리, 데이터베이스 연결, 인코딩 변환, 시스템 정보 조회
echo.

echo [Foundation/Config 패키지 - 클래스 설명]
echo - 설정 관리 클래스
echo - XML 설정 파일을 읽어서 two level의 Node 구조로 관리하며, 같은 level의 Node에서는 중복된 element Name이 존재하지 않는다
echo - XML 설정 파일 관리, 싱글톤 패턴 구현, 설정 값 조회, Element 객체 반환
echo.

echo [Foundation/Base 패키지 - 클래스 설명]
echo - XML 문서 관리 클래스 (Doc.java)
echo - DOM XML 핸들러 클래스 (DOMXMLHandler.java)
echo - XML 핸들러 옵션 클래스 (HandlerOption.java)
echo - SAX XML 핸들러 클래스 (SAXXMLHandler.java)
echo - XML 캐시 관리 클래스 (XMLCache.java)
echo - XML 핸들러 추상 클래스 (XMLHandler.java)
echo - XML 핸들러 팩토리 클래스 (XMLHandlerFactory.java)
echo.

echo [Foundation/General 패키지 - 메서드 설명]
echo - 문자열이 숫자인지 확인하는 메서드
echo - 구분자로 분리된 문자열에서 특정 순서의 문자열을 추출하는 메서드
echo - 문자열 치환 메서드
echo - 시간 간격을 계산한다
echo - 시스템 출력 메서드
echo - len 길이 만큼 문자열을 자른다
echo - 특정 위치에서 문자열을 자른다
echo - ASCII를 KSC5601로 변환한다
echo - KSC5601을 8859_1로 변환한다
echo - 문자열의 한글/영문 여부를 확인한다
echo - 문자열이 한글/영문 문자를 포함하는지 확인한다
echo - 문자열이 null인지 확인하고, null이면 빈 문자열을 반환한다
echo - 날짜 변환
echo - 대소문자 변환
echo - 문자열 비교
echo - 파일 처리
echo - 파일 정보 조회
echo - 디렉토리 삭제
echo - 시스템 정보 조회
echo - 현재 날짜와 시간을 반환한다
echo - 시간 간격을 계산한다
echo - 인코딩 변환
echo - HOST SEQ 조회
echo - 로그 기록
echo - 프로퍼티 파일을 읽어와서 파라미터를 지정하고 파일이 존재하는지 확인한다
echo - 숫자를 소수점 2자리까지 포맷팅한다
echo - 날짜 포맷
echo.

echo [Foundation/Config 패키지 - 메서드 설명]
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - Config.xml 파일에서 element name으로 해당 Text를 찾아서 반환한다
echo - 1 level Element Name으로 Element Object를 찾아서 반환한다
echo.

echo [Foundation/Base 패키지 - 메서드 설명]
echo - 파싱하고자 하는 XML 파일과 관련 설정을 받아서 Document 초기화를 수행한다
echo - 설정된 옵션에 따라 XMLHandlerFactory에서 XMLHandler를 생성하고, XML 파일을 Document로 변환한다
echo - 파일이 변경되었는지 확인하여, 변경되었다면 Document를 다시 파싱하고, Document를 반환한다
echo - 파일의 변경 여부를 반환한다
echo - 파일의 마지막 수정 시간을 반환한다
echo - DOMXMLHandler의 설정 옵션을 설정한다
echo - 구현되지 않은 Method (항상 null을 return 한다)
echo - XML 문서를 JDOM API의 Document Object로 변환하여 반환한다
echo - JDOM API의 DOMBuilder 객체를 생성하여 반환한다
echo - 기본 생성자 (XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을 기본값으로 설정한다)
echo - XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을 설정한다
echo - XMLHandler Type을 설정한다
echo - 현재 설정된 XMLHandler Type을 반환한다
echo - 사용할 XML Parser를 설정한다
echo - 현재 설정된 XML Parser의 Class Name을 반환한다
echo - 유효성 검사 옵션을 설정한다
echo - 현재 설정된 유효성 검사 옵션을 반환한다
echo - SAXXMLHandler의 설정 옵션을 설정한다
echo - JDOM API의 SAXBuilder 객체를 생성하여 반환한다
echo - XML 문서를 JDOM API의 Document Object로 변환하여 반환한다
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - Doc를 담을 HashMap을 초기화한다
echo - Key(XML File name)에 해당하는 Doc Object를 cache에서 검색하여, 있으면 해당 Doc의 Document Object를 반환하고, 없으면 새로 파싱하여 cache에 저장하고, Document를 반환한다
echo - 유효성 검사가 포함된 XML을 반환한다
echo - XML File을 읽기 위한 Handler들의 공통 기본 Interface를 정의한 abstract Class
echo - 기본 생성자
echo - JDOM API의 SAXBuilder 또는 DOMBuilder Object를 생성한다
echo - XML 파일을 파싱하여 Document 객체를 반환한다
echo - XML 파일명을 받아서 파싱하여 Document 객체를 반환한다
echo - 기본 생성자
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - 옵션 조건에 따라 적절한 XMLHandler를 생성한다
echo.

echo [Foundation/General 패키지 - 상수 및 변수 설명]
echo - 문자열이 숫자인지 확인하는 메서드
echo - 구분자로 분리된 문자열에서 특정 순서의 문자열을 추출하는 메서드
echo - 문자열 치환 메서드
echo - 시간 간격을 계산한다
echo - 시스템 출력 메서드
echo - len 길이 만큼 문자열을 자른다
echo - 특정 위치에서 문자열을 자른다
echo - ASCII를 KSC5601로 변환한다
echo - KSC5601을 8859_1로 변환한다
echo - 문자열의 한글/영문 여부를 확인한다 (return val : 0 한글, 1 영문, 2 한글/영문)
echo - 문자열이 한글/영문 문자를 포함하는지 확인한다 (문자열의 바이트 길이와 문자열 길이가 다르면 한글/영문 문자를 포함한다고 판단)
echo - 문자열이 null인지 확인하고, null이면 빈 문자열을 반환한다
echo - 날짜 변환
echo - 대소문자 변환
echo - 문자열 비교
echo - 파일 처리
echo - 파일 정보 조회 (filepath ex) C:\\MYTEST\\CHB\\src\\common)
echo - 복사된 파일 크기, error : -1
echo - 디렉토리 삭제 (args : C:\windows)
echo - write a file (mode append) (path2filename=="./kk.txt", writebuff : 쓸 문자열, -1 : error , 0 : success)
echo - 시스템 정보 조회
echo - 현재 날짜와 시간을 반환한다
echo - 시간 간격을 계산한다 (초 단위로 계산)
echo - 인코딩 변환
echo - HOST SEQ 조회 (CREATE SEQUENCE SQ_FALOGGHT_HOSTSEQ)
echo - 로그 기록 (파일이 존재하지 않으면 생성하고 기록한다)
echo - 프로퍼티 파일을 읽어와서 파라미터를 지정하고 파일이 존재하는지 확인한다
echo - 숫자를 소수점 2자리까지 포맷팅한다 (pattern : ######.##, double : 123456.789)
echo - 날짜 포맷 (포맷 규칙: 0 전체 문자열, # 전체 문자열 중 0이 아닌 숫자만 표시, . 소수점 이하 자리수 표시, , 천 단위 구분자, ; 소수점 이하 숫자 표시, - 음수 표시, % 100% 표시, 표시 규칙 : 소수점 이하 첫 번째 자리까지 표시, ' 한글 주석 표시)
echo - 17번 루프(Prime Number) 반복 (Prime Number는 소수를 의미한다, 다른 숫자를 사용하면 다른 결과가 나올 수 있다)
echo - 랜덤 숫자를 얻는다
echo - 0~100 범위의 랜덤 숫자를 반환 (소수 결과는 다른 결과가 나올 수 있다)
echo - 시스템 정보 조회
echo - 파일 내용을 읽어온다
echo - 파일 읽기
echo - 파일 크기, 파일이 존재합니다
echo - 파일이 존재하지 않습니다
echo - 한글 문자열
echo - 한글 테스트
echo - testing....한글...
echo - 한글 테스트
echo - 저장 성공 여부
echo - config.ini 내용
echo.

echo [Foundation/Config 패키지 - 상수 및 변수 설명]
echo - 설정 파일 이름
echo - 싱글톤 인스턴스
echo - 기본 생성자
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - Config.xml 파일에서 element name으로 해당 Text를 찾아서 반환한다
echo - 1 level Element Name으로 Element Object를 찾아서 반환한다 (2 Level이 없는 Config Data를 위해 사용한다)
echo.

echo [Foundation/Base 패키지 - 상수 및 변수 설명]
echo - Document 객체
echo - 마지막 수정 시간
echo - 문서 파일명
echo - 핸들러 옵션
echo - 파싱하고자 하는 XML 파일과 관련 설정을 받아서 Document 초기화를 수행한다
echo - 설정된 옵션에 따라 XMLHandlerFactory에서 XMLHandler를 생성하고, XML 파일을 Document로 변환한다
echo - 파일이 변경되었는지 확인하여, 변경되었다면 Document를 다시 파싱하고, Document를 반환한다
echo - 파일의 변경 여부를 반환한다 (변경되지 않았으면 false, 변경되었으면 true)
echo - 파일의 마지막 수정 시간을 반환한다
echo - DOMXMLHandler의 설정 옵션을 설정한다
echo - 구현되지 않은 Method (항상 null을 return 한다)
echo - XML 문서를 JDOM API의 Document Object로 변환하여 반환한다
echo - JDOM API의 DOMBuilder 객체를 생성하여 반환한다
echo - SAX 핸들러 타입
echo - DOM 핸들러 타입
echo - 핸들러 타입
echo - 파서 이름
echo - 유효성 검사 여부
echo - 기본 생성자 (XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을 기본값으로 설정한다)
echo - XML File을 파싱할 때, 사용할 XMLHandler의 Parser, 유효성 검사 옵션을 설정한다
echo - XMLHandler Type을 설정한다
echo - 현재 설정된 XMLHandler Type을 반환한다
echo - 사용할 XML Parser를 설정한다
echo - 현재 설정된 XML Parser의 Class Name을 반환한다
echo - 유효성 검사 옵션을 설정한다
echo - 현재 설정된 유효성 검사 옵션을 반환한다
echo - SAXXMLHandler의 설정 옵션을 설정한다
echo - JDOM API의 SAXBuilder 객체를 생성하여 반환한다
echo - XML 문서를 JDOM API의 Document Object로 변환하여 반환한다
echo - 싱글톤 인스턴스
echo - 핸들러 옵션
echo - 캐시 맵 (Map으로 된 Cache 구조)
echo - 기본 생성자
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - Doc를 담을 HashMap을 초기화한다
echo - Key(XML File name)에 해당하는 Doc Object를 cache에서 검색하여, 있으면 해당 Doc의 Document Object를 반환하고, 없으면 새로 파싱하여 cache에 저장하고, Document를 반환한다
echo - 유효성 검사가 포함된 XML을 반환한다
echo - XML File을 읽기 위한 Handler들의 공통 기본 Interface를 정의한 abstract Class
echo - 핸들러 옵션
echo - 기본 생성자
echo - JDOM API의 SAXBuilder 또는 DOMBuilder Object를 생성한다
echo - XML 파일을 파싱하여 Document 객체를 반환한다
echo - XML 파일명을 받아서 파싱하여 Document 객체를 반환한다
echo - 싱글톤 인스턴스
echo - 기본 생성자
echo - 싱글톤 인스턴스를 생성하여 반환한다
echo - 옵션 조건에 따라 적절한 XMLHandler를 생성한다
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
echo    - Foundation/General 패키지: 1개 파일 완료
echo    - Foundation/Config 패키지: 1개 파일 완료
echo    - Foundation/Base 패키지: 7개 파일 완료
echo    - 총 9개 Java 파일의 인코딩 수정 완료
echo.

echo 10. Foundation 하위 전체 완료:
echo     - Foundation/Utility 패키지: 3개 파일 완료 (이전 작업)
echo     - Foundation/General 패키지: 1개 파일 완료
echo     - Foundation/Config 패키지: 1개 파일 완료
echo     - Foundation/Base 패키지: 7개 파일 완료
echo     - Foundation 하위 총 12개 Java 파일의 인코딩 수정 완료
echo.

echo Foundation 하위 인코딩 수정이 완료되었습니다!
pause 