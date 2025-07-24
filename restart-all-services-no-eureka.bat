@echo off
echo ========================================
echo 모든 서비스 재시작 (Eureka 제거됨)
echo ========================================
echo.

echo 1. 실행 중인 모든 Java 프로세스 종료...
taskkill /f /im java.exe 2>nul
timeout /t 5 /nobreak >nul

echo 2. 타겟 디렉토리들 클리어...
if exist mbc-java\target rmdir /s /q mbc-java\target
if exist mba-java\target rmdir /s /q mba-java\target
if exist spring-cloud-apigateway-service\target rmdir /s /q spring-cloud-apigateway-service\target
echo 타겟 디렉토리 클리어 완료

echo 3. 서비스들 재시작...
echo.

echo [1/3] MBC 서비스 시작...
start "MBC Service" cmd /k "cd mbc-java && mvn spring-boot:run -Dspring-boot.run.jvmArguments=\"-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m\""

echo [2/3] MBA 서비스 시작...
start "MBA Service" cmd /k "cd mba-java && mvn spring-boot:run -Dspring-boot.run.jvmArguments=\"-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m\""

echo [3/3] API Gateway 시작...
start "API Gateway" cmd /k "cd spring-cloud-apigateway-service && mvn spring-boot:run -Dspring-boot.run.jvmArguments=\"-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m\""

echo.
echo 모든 서비스가 시작되었습니다.
echo.
echo 서비스 접근 URL:
echo - MBC: http://localhost:8085/mbc/home
echo - MBA: http://localhost:8084/mba/home
echo - API Gateway: http://localhost:8000
echo.
echo 테스트를 위해 30초 대기...
timeout /t 30 /nobreak >nul

echo.
echo 서비스 테스트 시작...
call test-all-services.bat 