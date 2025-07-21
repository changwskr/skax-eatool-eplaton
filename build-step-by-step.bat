@echo off
echo ========================================
echo SKAX EA Tool Eplaton - Step by Step Build
echo Package: com.skax.eatool
echo ========================================

echo.
echo Step 1: Building ksa-lib module...
cd ksa-lib
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: ksa-lib build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 2: Building ksa-java module...
cd ksa-java
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: ksa-java build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 2.1: Copying ksa-java JAR to ksa-lib/runtime...
if exist ksa-java\target\ksa-java-*.jar (
    copy ksa-java\target\ksa-java-*.jar ksa-lib\runtime\
    echo KSA Java JAR copied to ksa-lib/runtime
) else (
    echo Warning: ksa-java JAR file not found in target directory
)

echo.
echo Step 3: Building kji-lib module...
cd kji-lib
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: kji-lib build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 4: Building kji-java module...
cd kji-java
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: kji-java build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 4.1: Copying kji-java JAR to kji-lib/runtime...
if exist kji-java\target\kji-java-*.jar (
    copy kji-java\target\kji-java-*.jar kji-lib\runtime\
    echo KJI Java JAR copied to kji-lib/runtime
) else (
    echo Warning: kji-java JAR file not found in target directory
)

echo.
echo Step 5: Building mbc01-java module...
cd mbc01-java
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: mbc01-java build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 5.1: Copying mbc01-java JAR to mbc01-lib/runtime...
if exist mbc01-java\target\mbc01-java-*.jar (
    copy mbc01-java\target\mbc01-java-*.jar mbc01-lib\runtime\
    echo MBC01 Java JAR copied to mbc01-lib/runtime
) else (
    echo Warning: mbc01-java JAR file not found in target directory
)

echo.
echo Step 6: Preparing mbc01-lib (external JAR distribution)...
cd mbc01-lib
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: mbc01-lib build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo Step 7: Building mbc-java module...
cd mbc-java
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo Error: mbc-java build failed!
    pause
    exit /b 1
)
cd ..

echo.
echo ========================================
echo All modules built successfully!
echo ========================================
echo.
echo Next steps:
echo 1. Run KSA application: cd ksa-java && mvn spring-boot:run
echo 2. Run KJI application: cd kji-java && mvn spring-boot:run
echo 3. Run MBC01 application: cd mbc01-java && mvn spring-boot:run
echo 4. Run MBC application: cd mbc-java && mvn spring-boot:run
echo 5. Access KSA application: http://localhost:8080/ksa
echo 6. Access KJI application: http://localhost:8080/kji
echo 7. Access MBC01 application: http://localhost:8080/mbc01
echo 8. Access MBC application: http://localhost:8080/mbc
echo 9. Check H2 console: http://localhost:8080/mbc/h2-console
echo.
pause 