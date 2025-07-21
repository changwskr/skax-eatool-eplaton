@echo off
REM SKAX Project Eplaton - Source Migration Script (Windows)
REM 기존 소스 코드를 Maven 표준 디렉토리 구조로 이동

echo Starting source migration...

REM Create Maven standard directory structure
echo Creating Maven directory structure...

REM mbc01-java module
if not exist "mbc01-java\src\main\java" mkdir "mbc01-java\src\main\java"
if not exist "mbc01-java\src\main\resources" mkdir "mbc01-java\src\main\resources"
if not exist "mbc01-java\src\test\java" mkdir "mbc01-java\src\test\java"
if not exist "mbc01-java\src\test\resources" mkdir "mbc01-java\src\test\resources"



REM mbc-java module
if not exist "mbc-java\src\main\java" mkdir "mbc-java\src\main\java"
if not exist "mbc-java\src\main\resources" mkdir "mbc-java\src\main\resources"
if not exist "mbc-java\src\test\java" mkdir "mbc-java\src\test\java"
if not exist "mbc-java\src\test\resources" mkdir "mbc-java\src\test\resources"

REM Move existing source files
echo Moving source files...

REM Move mbc01-java source files
if exist "mbc01-java\src" (
    echo Moving mbc01-java source files...
    xcopy "mbc01-java\src\*" "mbc01-java\src\main\java\" /E /I /Y >nul 2>&1
)

REM Move mbc-java source files
if exist "mbc-java\src" (
    echo Moving mbc-java source files...
    xcopy "mbc-java\src\*" "mbc-java\src\main\java\" /E /I /Y >nul 2>&1
)

REM Move configuration files
echo Moving configuration files...



REM Create mapper directory for MyBatis
if not exist "mbc-java\src\main\resources\mapper" mkdir "mbc-java\src\main\resources\mapper"

echo Source migration completed!
echo.
echo Next steps:
echo 1. Review the moved files in each module
echo 2. Update package declarations if needed
echo 3. Run 'mvn clean install' to build the project
echo 4. Run 'cd mbc-java && mvn spring-boot:run' to start the application

pause 