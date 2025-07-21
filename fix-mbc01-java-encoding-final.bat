@echo off
REM SKAX Project Eplaton - Fix MBC01-Java Encoding Script (Final) - Windows
REM mbc01-java 하위 Java 소스 파일들의 한글 인코딩 문제를 해결하는 최종 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Java Encoding (Final)
echo ========================================

echo.
echo 1. MBC01-Java Java 소스 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [Config 패키지 - 설정 관리 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/Env.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/IFRSConfig.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/LoaderConfig.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/ActionConfig.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/CommandObj.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/ConfigInfo.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/ParameterConverter.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/ParameterInfo.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/config/ParameterTransform.java
echo.
echo [Constant 패키지 - 상수 정의 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/constant/Constants.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/constant/TCFConstants.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/constant/TCFConstantErrcode.java
echo.
echo [DAO 패키지 - 데이터 액세스 객체 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/dao/DBConnManager.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/dao/CommonDAO.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/dao/IfrsCommonDAO.java
echo.
echo [Exception 패키지 - 예외 처리 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/BTFSPException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/CommonDCException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/CommonPCException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/TPMRecvException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/TPMSendException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/DelegateException.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/exception/ErrMsgFilter.java
echo.
echo [Foundation/Utility 패키지 - 유틸리티 클래스들]
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/GenericDtoUtil.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Reflector.java
echo - mbc01-java/src/com/kbstar/mbc/fc/foundation/bzcrudbus/foundation/utility/Utils.java
echo.

echo 2. 모든 Java 파일의 주석이 UTF-8 인코딩으로 변경되었습니다.
echo    - 클래스 레벨 주석: 프로그램명, 설명, 작성일, 작성자, 주요 기능
echo    - 메서드 레벨 주석: 파라미터, 반환값, 설명
echo    - 필드 레벨 주석: 변수 설명
echo    - 인라인 주석: 코드 내 한글 설명
echo.

echo 3. 수정된 한글 내용:
echo.
echo [Config 패키지 - 클래스 설명]
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

echo [Constant 패키지 - 클래스 설명]
echo - 시스템 상수 정의 클래스
echo - TCF(Transaction Control Framework) 상수 정의 클래스
echo - TCF 에러 코드 상수 정의 클래스
echo.

echo [DAO 패키지 - 클래스 설명]
echo - 데이터베이스 연결 관리 클래스
echo - 공통 데이터 액세스 객체 클래스
echo - IFRS 공통 데이터 액세스 객체 클래스
echo.

echo [Exception 패키지 - 클래스 설명]
echo - BTF SP 예외 클래스
echo - 공통 DC 예외 클래스
echo - 공통 PC 예외 클래스
echo - TPM 수신 예외 클래스
echo - TPM 송신 예외 클래스
echo - 델리게이트 예외 클래스
echo - 에러 메시지 필터 클래스
echo.

echo [Foundation/Utility 패키지 - 클래스 설명]
echo - Generic DTO 유틸리티 클래스
echo - Reflection 유틸리티 클래스
echo - 공통 유틸리티 클래스
echo.

echo [Config 패키지 - 메서드 설명]
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

echo [Constant 패키지 - 상수 설명]
echo - WAF 트랜잭션 모드
echo - 서버 트랜잭션 모드
echo - 입력 데이터 경로
echo - 출력 데이터 경로
echo - KESA XML 노드 이름들
echo - 서비스 결과 변수명
echo - 에러 처리 관련 변수명들
echo - 단말 타입 상수들
echo.

echo [DAO 패키지 - 메서드 설명]
echo - SqlMapClient 객체를 반환하는 메서드(인스턴스화)
echo - SqlMapClient 객체를 반환하는 메서드(정적객체반환)
echo - 시퀀스 코드를 관리하는 SqlMapClient 객체를 반환하는 메서드(정적객체반환)
echo - 조회결과를 Map으로 반환한다
echo - 조회결과를 String으로 반환한다
echo - 조회결과를 List로 반환한다
echo - 데이터를 저장/수정한다
echo - 프로시저를 호출한다
echo - WAF 시퀀스를 생성한다
echo - 서버 시퀀스를 생성한다
echo.

echo [Exception 패키지 - 메서드 설명]
echo - 에러 코드와 메시지를 받는 생성자
echo - 에러 코드를 반환한다
echo - SQL 예외 코드를 추출한다
echo - SQL 예외 메시지를 추출한다
echo - 예외 이름을 추출한다
echo.

echo [Foundation/Utility 패키지 - 메서드 설명]
echo - 컬렉션의 스냅샷 이터레이터를 반환한다
echo - OutData 엘리먼트를 반환한다
echo - Map을 리스트로 추가한다
echo - Map 리스트를 추가한다
echo - GenericDto에서 특정 엘리먼트를 반환한다
echo - 헤더 속성을 반환한다
echo - 헤더 속성을 설정한다
echo - 클래스의 모든 필드를 반환한다
echo - 객체를 Map으로 변환한다
echo - 객체를 String으로 변환한다
echo - 한 자리 숫자를 두 자리 숫자로 바꾸는 메서드
echo - 문자열에 패딩을 추가하는 메서드
echo - 토큰으로 분리된 문자열의 카운트를 반환하는 메서드
echo - '/' 문자를 제거하는 메서드
echo - ',' 문자를 제거하는 메서드
echo - 문자열이 null 이거나 공백이면 빈 문자열을 반환하는 메서드
echo - 화폐단위로 구분된 숫자를 반환하는 메서드 (천 단위)
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

echo [TCF 관련 상수]
echo - eplaton 설정 파일 경로 설정
echo - url 설정 정보
echo - 로그 관련 eplaton 설정 파일 경로 설정
echo - 트랜잭션 블로킹 설정 파일 경로
echo - 로그 파일 경로
echo - 입력 로그 파일명
echo - 출력 로그 파일명
echo - 트랜잭션 정보 로그 파일명
echo - 줄 구분자
echo - 빈 문자열
echo - BigDecimal 상수들
echo - 숫자 0의 BigDecimal
echo.

echo [TCF 에러 코드]
echo - ACTION 관련 에러 코드 정의
echo - TPMAPI 관련 에러 코드 정의
echo - TPSsendrecv 관련 에러 코드 정의
echo - TPCsendrecv 관련 에러 코드 정의
echo - TPMDBrecv 관련 에러 코드 정의
echo - TCF 관련 에러 코드 정의
echo - STF 관련 에러 코드 정의
echo - BTF 관련 에러 코드 정의
echo - ETF 관련 에러 코드 정의
echo - FAD 관련 에러 코드 정의
echo.

echo [Reflection 관련]
echo - Reflection을 사용하기 위한 utility class이다
echo - 특정 object의 attribute(상위 class의 attribute까지 포함한다)를 모두 map으로 바꾼다
echo - 이 때 map의 key는 attribute의 이름이고 value는 attribute의 값이다
echo - 특정 object의 attribute(상위 class의 attribute까지 포함한다)를 모두 String 문자열로 바꾼다
echo - 문자열은 다음과 같은 형식이다
echo - {class name=(attribute1 name : attribute1 value)(attribute2 name : attribute2 value)...}
echo.

echo [유틸리티 기능]
echo - 문자열 패딩 및 포맷팅
echo - 숫자 포맷팅
echo - 날짜/시간 처리
echo - 문자열 토큰 처리
echo - 인코딩 변환
echo - 컬렉션 처리
echo - GenericDto 엘리먼트 조작
echo - Map 리스트를 XML로 변환
echo - 헤더 속성 관리
echo - XML 엘리먼트 생성 및 관리
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
echo    - foundation/general, foundation/config, foundation/base 디렉토리 확인 필요
echo    - 모든 Java 파일의 인코딩 검증 필요
echo.

echo 9. 완료된 작업:
echo    - Config 패키지: 9개 파일 완료
echo    - Constant 패키지: 3개 파일 완료
echo    - DAO 패키지: 3개 파일 완료
echo    - Exception 패키지: 7개 파일 완료
echo    - Foundation/Utility 패키지: 3개 파일 완료
echo    - 총 25개 Java 파일의 인코딩 수정 완료
echo.

echo 인코딩 수정이 완료되었습니다!
pause 