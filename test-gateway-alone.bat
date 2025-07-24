@echo off
echo ========================================
echo API Gateway 단독 테스트
echo ========================================
echo.

REM 현재 디렉토리를 API Gateway 디렉토리로 변경
cd /d "%~dp0\spring-cloud-apigateway-service"

echo 현재 디렉토리: %CD%
echo.

REM 포트 8000 사용 중인 프로세스 종료
echo 포트 8000 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

echo ========================================
echo API Gateway 서비스 시작 중...
echo ========================================
echo.
echo 접속 URL:
echo - API Gateway: http://localhost:8000
echo - Health Check: http://localhost:8000/actuator/health
echo - Metrics: http://localhost:8000/actuator/metrics
echo.
echo 테스트 방법:
echo 1. 브라우저에서 http://localhost:8000/actuator/health 접속
echo 2. 정상 응답 확인 후 다음 단계 진행
echo.
echo 종료하려면 Ctrl+C를 누르세요.
echo.

REM Spring Boot 애플리케이션 실행
mvn spring-boot:run

echo.
echo API Gateway 서비스가 종료되었습니다.
pause 