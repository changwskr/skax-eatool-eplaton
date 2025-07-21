@echo off
REM SKAX Project Eplaton - Build and Run Script (Windows)

echo ========================================
echo SKAX Project Eplaton - Build and Run
echo ========================================

echo.
echo 1. Building the entire project...
call mvn clean install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo 2. Starting the application...
echo.
echo Choose environment:
echo 1. Development (dev)
echo 2. Production (prod)
echo 3. Default
echo.
set /p choice="Enter your choice (1-3): "

if "%choice%"=="1" (
    echo Starting in development mode...
    cd mbc-java
    call mvn spring-boot:run -Dspring.profiles.active=dev
) else if "%choice%"=="2" (
    echo Starting in production mode...
    cd mbc-java
    call mvn spring-boot:run -Dspring.profiles.active=prod
) else (
    echo Starting in default mode...
    cd mbc-java
    call mvn spring-boot:run
)

echo.
echo Application started!
echo.
echo ========================================
echo Database Information
echo ========================================
echo - H2 Console: http://localhost:8080/mbc/h2-console
echo - JDBC URL: jdbc:h2:mem:mbcdb
echo - Username: sa
echo - Password: (empty)
echo.
echo ========================================
echo API Documentation
echo ========================================
echo - Application: http://localhost:8080/mbc
echo - Swagger UI: http://localhost:8080/mbc/swagger-ui.html
echo - OpenAPI JSON: http://localhost:8080/mbc/api-docs
echo - Actuator: http://localhost:8080/mbc/actuator
echo.
echo ========================================
echo Test Data Summary
echo ========================================
echo - Users: 10 (ADMIN: 1, MANAGER: 2, USER: 7)
echo - Accounts: 15 (NORMAL: 6, VIP: 5, ENTERPRISE: 4)
echo - Total Balance: 89,500,000 KRW
echo.
echo Press Ctrl+C to stop the application
pause 