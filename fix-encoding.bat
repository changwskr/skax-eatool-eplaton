@echo off
REM SKAX Project Eplaton - Fix Encoding Script (Windows)
REM 한글 인코딩 문제를 해결하는 스크립트

echo ========================================
echo SKAX Project Eplaton - Fix Encoding
echo ========================================

echo.
echo 1. Creating Maven standard directory structure...
call migrate-sources.bat

echo.
echo 2. Setting file encoding to UTF-8...

REM Set JAVA_TOOL_OPTIONS for UTF-8
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

REM Set Maven encoding options
set MAVEN_OPTS=-Dfile.encoding=UTF-8

echo.
echo 3. Cleaning and rebuilding project...
call mvn clean compile -Dfile.encoding=UTF-8

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Build failed! Please check the encoding settings.
    pause
    exit /b 1
)

echo.
echo 4. Encoding fix completed!
echo.
echo Important notes:
echo - All Java files should be saved in UTF-8 encoding
echo - IDE should be configured to use UTF-8 encoding
echo - Maven is configured to use UTF-8 encoding
echo.
echo Next steps:
echo 1. Open your IDE and set encoding to UTF-8
echo 2. Reopen all Java files to ensure proper encoding
echo 3. Run 'mvn clean install' to build the project
echo 4. Run 'build-and-run.bat' to start the application

pause 