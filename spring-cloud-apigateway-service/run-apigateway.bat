@echo off
echo ========================================
echo Spring Cloud API Gateway Service
echo ========================================
echo.

REM 현재 디렉토리를 API Gateway 디렉토리로 변경
cd /d "%~dp0"

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

REM 기존 프로세스 종료 (포트 8000 사용 중인 프로세스)
echo 포트 8000 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

REM Maven 의존성 다운로드
echo Maven 의존성 다운로드 중...
mvn dependency:resolve
if %errorlevel% neq 0 (
    echo 오류: Maven 의존성 다운로드에 실패했습니다.
    pause
    exit /b 1
)
echo.

REM 애플리케이션 컴파일
echo 애플리케이션 컴파일 중...
mvn clean compile
if %errorlevel% neq 0 (
    echo 오류: 애플리케이션 컴파일에 실패했습니다.
    pause
    exit /b 1
)
echo.

echo ========================================
echo API Gateway 서비스 시작 중...
echo ========================================
echo.
echo 접속 URL:
echo - API Gateway: http://localhost:8000
echo - Eureka Dashboard: http://localhost:8761
echo - Health Check: http://localhost:8000/actuator/health
echo.
echo 서비스 라우팅:
echo - MBA Service: http://localhost:8000/mba
echo - KSA Service: http://localhost:8000/ksa
echo - KJI Service: http://localhost:8000/kji
echo - MBC Service: http://localhost:8000/mbc
echo - MBC01 Service: http://localhost:8000/mbc01
echo.
echo 종료하려면 Ctrl+C를 누르세요.
echo.

REM Spring Boot 애플리케이션 실행
mvn spring-boot:run

echo.
echo API Gateway 서비스가 종료되었습니다.
pause 