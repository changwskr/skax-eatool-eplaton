@echo off
echo ========================================
echo API Gateway + MBA + MBC 통합 테스트 (최소 메모리)
echo ========================================
echo.

REM 포트 사용 중인 프로세스 종료
echo 포트 8000, 8084, 8085 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8084') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8085') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

echo ========================================
echo 1단계: MBA 서비스 시작 (최소 메모리)
echo ========================================
echo.
echo MBA 서비스를 백그라운드에서 시작합니다...
cd /d "%~dp0\mba-java"
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC
start "MBA Service" cmd /k "mvn spring-boot:run"
echo.

REM MBA 서비스 시작 대기
echo MBA 서비스 시작 대기 중... (15초)
timeout /t 15 /nobreak >nul

echo ========================================
echo 2단계: MBC 서비스 시작 (최소 메모리)
echo ========================================
echo.
echo MBC 서비스를 백그라운드에서 시작합니다...
cd /d "%~dp0\mbc-java"
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC
start "MBC Service" cmd /k "mvn spring-boot:run"
echo.

REM MBC 서비스 시작 대기
echo MBC 서비스 시작 대기 중... (15초)
timeout /t 15 /nobreak >nul

echo ========================================
echo 3단계: API Gateway 시작 (최소 메모리)
echo ========================================
echo.
echo API Gateway를 백그라운드에서 시작합니다...
cd /d "%~dp0\spring-cloud-apigateway-service"
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC
start "API Gateway" cmd /k "mvn spring-boot:run"
echo.

REM API Gateway 시작 대기
echo API Gateway 시작 대기 중... (20초)
timeout /t 20 /nobreak >nul

echo ========================================
echo 통합 테스트 준비 완료! (최소 메모리)
echo ========================================
echo.
echo 접속 URL:
echo - API Gateway Health: http://localhost:8000/actuator/health
echo - MBA Service 직접: http://localhost:8084
echo - MBA Service via Gateway: http://localhost:8000/mba
echo - MBC Service 직접: http://localhost:8085
echo - MBC Service via Gateway: http://localhost:8000/mbc
echo.
echo 테스트 방법:
echo 1. 브라우저에서 http://localhost:8000/actuator/health 접속
echo 2. http://localhost:8000/mba 접속하여 MBA 서비스 응답 확인
echo 3. http://localhost:8000/mbc 접속하여 MBC 서비스 응답 확인
echo 4. 각 서비스의 직접 접속도 확인
echo.
echo 메모리 사용량:
echo - 각 서비스: 128MB-256MB (총 약 768MB)
echo - 시스템 요구사항: 최소 2GB RAM
echo.
echo 서비스 종료하려면 각 터미널 창에서 Ctrl+C를 누르세요.
echo.
pause 