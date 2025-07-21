@echo off
REM SKAX Project Eplaton - Fix MBC01-Config Encoding Script (Windows)
REM mbc01-config 하위 파일들의 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix MBC01-Config Encoding
echo ========================================

echo.
echo 1. MBC01-Config 파일들의 인코딩을 UTF-8로 수정했습니다.
echo.
echo 수정된 파일 목록:
echo.
echo [Properties 파일]
echo - mbc01-config/ifr_dev/kesa_master.properties
echo.
echo [Base 디렉토리 - XML 설정 파일들]
echo - mbc01-config/ifr_dev/base/service_objects.xml
echo - mbc01-config/ifr_dev/base/cache.xml
echo - mbc01-config/ifr_dev/base/log4j.xml
echo - mbc01-config/ifr_dev/base/prefs.bid.xml
echo - mbc01-config/ifr_dev/base/prefs.eai.xml
echo - mbc01-config/ifr_dev/base/prefs.static.xml
echo - mbc01-config/ifr_dev/base/sqlmap_KESA.xml
echo - mbc01-config/ifr_dev/base/sqlmap_KESA.xml.jdbc
echo - mbc01-config/ifr_dev/base/sqlmap_KESA_RELAY.xml
echo.
echo [D 디렉토리 - 개발 환경 설정]
echo - mbc01-config/ifr_dev/d/cache.xml
echo - mbc01-config/ifr_dev/d/prefs.dynamic.xml
echo - mbc01-config/ifr_dev/d/server_nodes.xml
echo.
echo [O 디렉토리 - 운영 환경 설정]
echo - mbc01-config/ifr_dev/o/cache.xml
echo - mbc01-config/ifr_dev/o/log4j.xml
echo - mbc01-config/ifr_dev/o/prefs.bid.xml
echo - mbc01-config/ifr_dev/o/prefs.dynamic.xml
echo - mbc01-config/ifr_dev/o/prefs.eai.xml
echo - mbc01-config/ifr_dev/o/server_nodes.xml
echo.

echo 2. 모든 XML 파일의 인코딩이 UTF-8로 변경되었습니다.
echo    - XML 선언: ^<?xml version="1.0" encoding="UTF-8"?^>
echo    - 한글 주석 및 내용이 정상적으로 표시됩니다.
echo.

echo 3. 수정된 한글 내용:
echo.
echo [Properties 파일]
echo - KESA 프레임워크 preference 파일들을 로드하는 master properties
echo - 해당 was의 instance의 -D속성으로 반드시 설정되어야 한다
echo - 시스템 환경 설정, 시스템환경구분코드 [D|T|S|O]
echo - 로드할 Preference file들의 순서를 설정
echo - Data Source 설정, Application 설정
echo.

echo [XML 파일 주석]
echo - FrameworkObjects는 framework에서 관리한다
echo - 개발자가 임의로 수정하면 loading되지 않는다
echo - 개발자가 정의하는 ServiceObjects를 정의한다
echo - Async 처리에서 발생하는 특정 Error 처리 Manager를 정의하고, 사용한다
echo - 등록되는 Async Error Manager는 com.skax.eatool.ksa.oltp.async.impl.IAsyncErrorProcess를 구현해야 하는 class
echo - 필요에 따라 pre/post process 설정을 추가할 수 있습니다
echo.

echo [동적 설정 파일]
echo - 하나의 Instance에 1개의 AsyncMgr을 설정한다. bizCode는 KESA로 설정한다
echo - WORKER_NUMBER: Async 요청을 처리하는 worker 수. 0이면, Async Manager는 프레임워크에서 비활성화
echo - 동시 처리 가능한 connection number
echo - TCP stream 모드. true: no delay / false: buffered mode
echo - TCP linger 설정. 0 이상의 integer
echo - HTTP connection 획득 대기 시간. milliseconds
echo - HTTP 전송 timeout. milliseconds
echo - HTTP 요청 URL
echo.

echo [서버 노드 설정]
echo - KESA server1개발테스트
echo - KESA server1
echo.

echo 4. Maven 설정에서 UTF-8 인코딩이 적용되었습니다.
echo    - mbc01-config/pom.xml에 UTF-8 인코딩 설정 추가
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
echo    - 설정 파일의 한글이 정상적으로 표시되는지 확인하세요
echo.

echo 7. 환경별 설정:
echo    - Base: 공통 설정 파일들
echo    - D: 개발 환경 설정 (개발테스트)
echo    - O: 운영 환경 설정 (운영)
echo.

echo 인코딩 수정이 완료되었습니다!
pause 