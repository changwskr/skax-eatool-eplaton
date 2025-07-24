@echo off
echo ========================================
echo MBC 서비스 실행 (메모리 최적화)
echo ========================================
echo.

REM 현재 디렉토리를 MBC 디렉토리로 변경
cd /d "%~dp0\mbc-java"

echo 현재 디렉토리: %CD%
echo.

REM 포트 8084 사용 중인 프로세스 종료
echo 포트 8084 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8084') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

echo ========================================
echo MBC 서비스 시작 중... (메모리 최적화)
echo ========================================
echo.
echo JVM 메모리 설정:
echo - Xms: 256MB (초기 힙 크기)
echo - Xmx: 512MB (최대 힙 크기)
echo - XX:MaxMetaspaceSize: 128MB (메타스페이스 최대 크기)
echo.
echo 접속 URL:
echo - MBC Service: http://localhost:8084
echo - Health Check: http://localhost:8084/actuator/health
echo.
echo 종료하려면 Ctrl+C를 누르세요.
echo.

REM Spring Boot 애플리케이션 실행 (메모리 최적화)
set MAVEN_OPTS=-Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m
mvn spring-boot:run

echo.
echo MBC 서비스가 종료되었습니다.
pause 