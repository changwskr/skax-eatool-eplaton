@echo off
echo ========================================
echo SKAX-EATOOL-EPLATON Build Process
echo ========================================
echo.
echo This script builds all modules using Maven install approach
echo Each module will be installed to local Maven repository
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
echo Step 4: Building mbc-java (main application)
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
echo Note: lib modules (ksa-lib, kji-lib, mbc01-lib) are now
echo used only for external JAR distribution and are not
echo required for the build process.
echo.
pause 