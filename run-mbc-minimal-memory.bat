@echo off
echo ========================================
echo MBC 서비스 실행 (최소 메모리)
echo ========================================
echo.

REM 현재 디렉토리를 MBC 디렉토리로 변경
cd /d "%~dp0\mbc-java"

echo 현재 디렉토리: %CD%
echo.

REM 포트 8085 사용 중인 프로세스 종료
echo 포트 8085 사용 중인 프로세스 확인 및 종료...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8085') do (
    echo 프로세스 ID %%a 종료 중...
    taskkill /f /pid %%a 2>nul
)
echo.

echo ========================================
echo MBC 서비스 시작 중... (최소 메모리)
echo ========================================
echo.
echo JVM 메모리 설정:
echo - Xms: 128MB (초기 힙 크기)
echo - Xmx: 256MB (최대 힙 크기)
echo - XX:MaxMetaspaceSize: 64MB (메타스페이스 최대 크기)
echo - XX:+UseSerialGC (시리얼 가비지 컬렉터 사용)
echo.
echo 접속 URL:
echo - MBC Service: http://localhost:8085
echo - H2 Console: http://localhost:8085/h2-console
echo - Swagger UI: http://localhost:8085/swagger-ui.html
echo - Health Check: http://localhost:8085/actuator/health
echo.
echo 종료하려면 Ctrl+C를 누르세요.
echo.

REM Spring Boot 애플리케이션 실행 (최소 메모리)
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC
mvn spring-boot:run

echo.
echo MBC 서비스가 종료되었습니다.
pause 