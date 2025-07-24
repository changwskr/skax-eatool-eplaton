@echo off
echo ========================================
echo API Gateway 실행 (최소 메모리)
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
echo API Gateway 서비스 시작 중... (최소 메모리)
echo ========================================
echo.
echo JVM 메모리 설정:
echo - Xms: 128MB (초기 힙 크기)
echo - Xmx: 256MB (최대 힙 크기)
echo - XX:MaxMetaspaceSize: 64MB (메타스페이스 최대 크기)
echo - XX:+UseSerialGC (시리얼 가비지 컬렉터 사용)
echo.
echo 접속 URL:
echo - API Gateway: http://localhost:8000
echo - Health Check: http://localhost:8000/actuator/health
echo - Metrics: http://localhost:8000/actuator/metrics
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

REM Spring Boot 애플리케이션 실행 (최소 메모리)
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC
mvn spring-boot:run

echo.
echo API Gateway 서비스가 종료되었습니다.
pause 