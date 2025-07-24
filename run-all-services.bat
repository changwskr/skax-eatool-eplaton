@echo off
echo ========================================
echo SKAX EA Tool - 전체 서비스 실행
echo ========================================
echo.

REM 현재 디렉토리 확인
echo 현재 디렉토리: %CD%
echo.

REM Java 버전 확인
echo Java 버전 확인 중...
java -version
if %errorlevel% neq 0 (
    echo 오류: Java가 설치되지 않았거나 PATH에 설정되지 않았습니다.
    pause
    exit /b 1
)
echo.

REM Maven 버전 확인
echo Maven 버전 확인 중...
mvn -version
if %errorlevel% neq 0 (
    echo 오류: Maven이 설치되지 않았거나 PATH에 설정되지 않았습니다.
    pause
    exit /b 1
)
echo.

REM 포트 사용 중인 프로세스 종료
echo 포트 사용 중인 프로세스 확인 및 종료...
echo 포트 8000 (API Gateway) 확인 중...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)

echo 포트 8081 (MBA Service) 확인 중...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8081') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)

echo 포트 8761 (Eureka Server) 확인 중...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8761') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

REM 로그 디렉토리 생성
if not exist "logs" mkdir logs
echo.

echo ========================================
echo 서비스 실행 순서
echo ========================================
echo 1. Eureka Server (포트 8761)
echo 2. API Gateway (포트 8000)
echo 3. MBA Service (포트 8081)
echo 4. 기타 서비스들...
echo.

echo 각 서비스는 별도의 명령 프롬프트 창에서 실행됩니다.
echo 모든 서비스가 시작되면 다음 URL로 접속할 수 있습니다:
echo.
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8000
echo - MBA Service: http://localhost:8000/mba
echo.

echo 서비스를 시작하시겠습니까? (Y/N)
set /p choice=
if /i "%choice%" neq "Y" (
    echo 서비스 실행이 취소되었습니다.
    pause
    exit /b 0
)

echo.

REM Eureka Server 실행 (새 창에서)
echo Eureka Server를 시작합니다...
start "Eureka Server" cmd /k "cd /d %CD% && echo Eureka Server 시작 중... && java -jar eureka-server.jar"

REM 잠시 대기
timeout /t 10 /nobreak >nul

REM API Gateway 실행 (새 창에서)
echo API Gateway를 시작합니다...
start "API Gateway" cmd /k "cd /d %CD%\spring-cloud-apigateway-service && mvn spring-boot:run"

REM 잠시 대기
timeout /t 15 /nobreak >nul

REM MBA Service 실행 (새 창에서)
echo MBA Service를 시작합니다...
start "MBA Service" cmd /k "cd /d %CD%\mba-java && mvn spring-boot:run"

echo.
echo ========================================
echo 모든 서비스가 시작되었습니다!
echo ========================================
echo.
echo 접속 URL:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8000
echo - MBA Service: http://localhost:8000/mba
echo.
echo 각 서비스 창에서 Ctrl+C를 눌러 서비스를 종료할 수 있습니다.
echo.
pause 