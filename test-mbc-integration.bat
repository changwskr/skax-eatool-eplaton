@echo off
echo ========================================
echo API Gateway + MBC 서비스 연동 테스트
echo ========================================
echo.

REM 포트 사용 중인 프로세스 종료
echo 포트 8000, 8084 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8084') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

echo ========================================
echo 1단계: MBC 서비스 시작
echo ========================================
echo.
echo MBC 서비스를 백그라운드에서 시작합니다...
cd /d "%~dp0\mbc-java"
start "MBC Service" cmd /k "mvn spring-boot:run"
echo.

REM MBC 서비스 시작 대기
echo MBC 서비스 시작 대기 중... (10초)
timeout /t 10 /nobreak >nul

echo ========================================
echo 2단계: API Gateway 시작
echo ========================================
echo.
echo API Gateway를 백그라운드에서 시작합니다...
cd /d "%~dp0\spring-cloud-apigateway-service"
start "API Gateway" cmd /k "mvn spring-boot:run"
echo.

REM API Gateway 시작 대기
echo API Gateway 시작 대기 중... (15초)
timeout /t 15 /nobreak >nul

echo ========================================
echo 테스트 준비 완료!
echo ========================================
echo.
echo 접속 URL:
echo - API Gateway Health: http://localhost:8000/actuator/health
echo - MBC Service 직접: http://localhost:8084
echo - MBC Service via Gateway: http://localhost:8000/mbc
echo.
echo 테스트 방법:
echo 1. 브라우저에서 http://localhost:8000/actuator/health 접속
echo 2. http://localhost:8000/mbc 접속하여 MBC 서비스 응답 확인
echo 3. http://localhost:8084 접속하여 직접 MBC 서비스 응답 확인
echo.
echo 서비스 종료하려면 각 터미널 창에서 Ctrl+C를 누르세요.
echo.
pause 