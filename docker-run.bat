@echo off
REM 한글 인코딩 설정
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ========================================
echo SKAX EA Tool Docker Run Script
echo ========================================

echo.
echo Docker Compose로 서비스를 시작합니다...
echo.

REM Docker Compose 실행
docker-compose up -d

if !errorlevel! neq 0 (
    echo Docker Compose 실행 실패!
    pause
    exit /b 1
)

echo.
echo ========================================
echo 서비스가 시작되었습니다!
echo ========================================
echo.
echo 서비스 상태 확인:
docker-compose ps
echo.
echo 로그 확인:
echo docker-compose logs -f
echo.
echo 서비스 접속 URL:
echo - API Gateway: http://localhost:8000
echo - MBC Service: http://localhost:8085
echo - MBA Service: http://localhost:8084
echo - KSA Service: http://localhost:8083
echo - KJI Service: http://localhost:8082
echo.
echo 서비스 중지:
echo docker-compose down
echo.
pause 