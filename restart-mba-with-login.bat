@echo off
echo ========================================
echo MBA 서비스 재시작 (로그인 템플릿 추가됨)
echo ========================================
echo.

echo 1. 실행 중인 MBA 서비스 종료...
taskkill /f /im java.exe 2>nul
timeout /t 3 /nobreak >nul

echo 2. MBA 타겟 디렉토리 클리어...
cd mba-java
if exist target rmdir /s /q target
echo 타겟 디렉토리 클리어 완료

echo 3. MBA 서비스 재시작...
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Xms256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m" 