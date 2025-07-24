@echo off
echo ========================================
echo MBC 서비스 완전 캐시 클리어 및 재시작
echo ========================================
echo.

echo 1. 실행 중인 모든 Java 프로세스 종료...
taskkill /f /im java.exe 2>nul
timeout /t 5 /nobreak >nul

echo 2. MBC 타겟 디렉토리 완전 클리어...
cd mbc-java
if exist target rmdir /s /q target
echo 타겟 디렉토리 클리어 완료

echo 3. Maven 로컬 저장소 캐시 클리어...
mvn dependency:purge-local-repository -DmanualInclude="com.skax.eatool:mbc-*" -DactTransitively=false
echo Maven 캐시 클리어 완료

echo 4. MBC 서비스 완전 재빌드...
mvn clean compile -DskipTests
if %errorlevel% neq 0 (
    echo 컴파일 오류가 발생했습니다.
    pause
    exit /b 1
)

echo 5. MBC 서비스 재시작...
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m" 