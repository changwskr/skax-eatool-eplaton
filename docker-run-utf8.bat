@echo off
REM UTF-8 인코딩으로 한글 표시 설정
chcp 65001 >nul
setlocal enabledelayedexpansion

title SKAX EA Tool Docker Run

echo.
echo ========================================
echo   SKAX EA Tool Docker Run Script
echo ========================================
echo.

echo Docker Compose로 서비스를 시작합니다...
echo.

REM Docker Compose 실행
echo [INFO] Docker Compose 실행 중...
docker-compose up -d

if !errorlevel! neq 0 (
    echo [ERROR] Docker Compose 실행 실패!
    echo.
    echo 문제 해결 방법:
    echo 1. Docker Desktop이 실행 중인지 확인
    echo 2. 포트가 사용 중인지 확인 (8000, 8082-8085)
    echo 3. docker-compose.yml 파일이 올바른지 확인
    echo.
    pause
    exit /b 1
)

echo [SUCCESS] Docker Compose 실행 완료
echo.

echo ========================================
echo   서비스가 성공적으로 시작되었습니다!
echo ========================================
echo.

echo [INFO] 서비스 상태 확인 중...
docker-compose ps
echo.

echo [INFO] 서비스 접속 정보:
echo ----------------------------------------
echo - API Gateway: http://localhost:8000
echo - MBC Service:  http://localhost:8085
echo - MBA Service:  http://localhost:8084
echo - KSA Service:  http://localhost:8083
echo - KJI Service:  http://localhost:8082
echo ----------------------------------------
echo.

echo [INFO] 유용한 명령어:
echo - 로그 확인:     docker-compose logs -f
echo - 서비스 중지:   docker-compose down
echo - 서비스 재시작: docker-compose restart
echo - 특정 서비스 로그: docker-compose logs -f [서비스명]
echo.

echo [INFO] 주요 엔드포인트:
echo - 메인 홈:       http://localhost:8000/mbc/home
echo - MBA 로그인:    http://localhost:8000/mba/auth/login
echo - Swagger UI:    http://localhost:8000/mbc/swagger-ui/index.html
echo - Health Check:  http://localhost:8000/actuator/health
echo.

pause 