@echo off
echo ========================================
echo API Gateway 완전 재빌드 및 재시작
echo ========================================
echo.

echo 1. 실행 중인 API Gateway 종료...
taskkill /f /im java.exe 2>nul
timeout /t 3 /nobreak >nul

echo 2. API Gateway 타겟 디렉토리 완전 클리어...
cd spring-cloud-apigateway-service
if exist target rmdir /s /q target
echo 타겟 디렉토리 클리어 완료

echo 3. Maven 의존성 다운로드 및 컴파일...
mvn clean compile -DskipTests
if %errorlevel% neq 0 (
    echo 컴파일 오류가 발생했습니다.
    pause
    exit /b 1
)

echo 4. API Gateway 서비스 시작...
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m" 