@echo off
echo ========================================
echo SKAX-EATOOL-EPLATON Build Process
echo ========================================
echo.
echo This script builds all modules using Maven install approach
echo Each module will be installed to local Maven repository
echo AND copied to corresponding lib/runtime directory
echo.

echo Step 1: Building and installing ksa-java to local repository
cd ksa-java
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: ksa-java build failed
    pause
    exit /b 1
)
cd ..
echo ✓ ksa-java installed successfully

echo.
echo Step 1.1: Copying ksa-java JAR to ksa-lib/runtime
if exist "ksa-java\target\ksa-java-*.jar" (
    for /f "delims=" %%i in ('dir /b /o-n "ksa-java\target\ksa-java-*.jar" ^| findstr /r ".*" ^| tail -1') do (
        REM 기존 파일이 있으면 읽기 전용 속성 해제
        if exist "ksa-lib\runtime\%%i" (
            attrib -r "ksa-lib\runtime\%%i" >nul 2>&1
        )
        copy "ksa-java\target\%%i" "ksa-lib\runtime\" >nul
        echo ✓ ksa-java JAR copied to ksa-lib/runtime: %%i
    )
) else (
    echo Warning: ksa-java JAR not found in target directory
)

echo.
echo Step 2: Building and installing kji-java to local repository
cd kji-java
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: kji-java build failed
    pause
    exit /b 1
)
cd ..
echo ✓ kji-java installed successfully

echo.
echo Step 2.1: Copying kji-java JAR to kji-lib/runtime
if exist "kji-java\target\kji-java-*.jar" (
    for /f "delims=" %%i in ('dir /b /o-n "kji-java\target\kji-java-*.jar" ^| findstr /r ".*" ^| tail -1') do (
        REM 기존 파일이 있으면 읽기 전용 속성 해제
        if exist "kji-lib\runtime\%%i" (
            attrib -r "kji-lib\runtime\%%i" >nul 2>&1
        )
        copy "kji-java\target\%%i" "kji-lib\runtime\" >nul
        echo ✓ kji-java JAR copied to kji-lib/runtime: %%i
    )
) else (
    echo Warning: kji-java JAR not found in target directory
)

echo.
echo Step 3: Building and installing mbc01-java to local repository
cd mbc01-java
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: mbc01-java build failed
    pause
    exit /b 1
)
cd ..
echo ✓ mbc01-java installed successfully

echo.
echo Step 3.1: Copying mbc01-java JAR to mbc01-lib/runtime
if exist "mbc01-java\target\mbc01-java-*.jar" (
    for /f "delims=" %%i in ('dir /b /o-n "mbc01-java\target\mbc01-java-*.jar" ^| findstr /r ".*" ^| tail -1') do (
        REM 기존 파일이 있으면 읽기 전용 속성 해제
        if exist "mbc01-lib\runtime\%%i" (
            attrib -r "mbc01-lib\runtime\%%i" >nul 2>&1
        )
        copy "mbc01-java\target\%%i" "mbc01-lib\runtime\" >nul
        echo ✓ mbc01-java JAR copied to mbc01-lib/runtime: %%i
    )
) else (
    echo Warning: mbc01-java JAR not found in target directory
)

echo.
echo Step 4: Building mbc-java (main application)
echo         Uses standard Maven dependencies from local repository
cd mbc-java
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: mbc-java build failed
    pause
    exit /b 1
)
cd ..
echo ✓ mbc-java built successfully

echo.
echo ========================================
echo All modules built and installed successfully!
echo ========================================
echo.
echo Summary:
echo - ksa-java: installed to Maven repo + copied to ksa-lib/runtime
echo - kji-java: installed to Maven repo + copied to kji-lib/runtime  
echo - mbc01-java: installed to Maven repo + copied to mbc01-lib/runtime
echo - mbc-java: uses standard Maven dependencies
echo.
echo JAR files are available in:
echo - ksa-lib/runtime/
echo - kji-lib/runtime/
echo - mbc01-lib/runtime/
echo.
echo Note: lib modules (ksa-lib, kji-lib, mbc01-lib) are used only
echo for external JAR distribution and are not required for the build process.
echo.
pause 