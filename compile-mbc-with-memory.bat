@echo off
echo ========================================
echo MBC 서비스 컴파일 (메모리 최적화)
echo ========================================
echo.

REM 현재 디렉토리를 MBC 디렉토리로 변경
cd /d "%~dp0\mbc-java"

echo 현재 디렉토리: %CD%
echo.

echo ========================================
echo MBC 서비스 컴파일 중... (메모리 최적화)
echo ========================================
echo.
echo JVM 메모리 설정:
echo - Xms: 256MB (초기 힙 크기)
echo - Xmx: 512MB (최대 힙 크기)
echo - XX:MaxMetaspaceSize: 128MB (메타스페이스 최대 크기)
echo.

REM Maven 메모리 설정
set MAVEN_OPTS=-Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m

REM Maven 컴파일 실행
echo Maven 컴파일 시작...
mvn clean compile

if %errorlevel% equ 0 (
    echo.
    echo ✅ MBC 서비스 컴파일 성공!
    echo.
    echo 다음 단계:
    echo 1. run-mbc-with-memory.bat 실행하여 서비스 시작
    echo 2. http://localhost:8084 접속하여 확인
) else (
    echo.
    echo ❌ MBC 서비스 컴파일 실패!
    echo 오류 코드: %errorlevel%
    echo.
    echo 해결 방법:
    echo 1. 시스템 메모리 확인
    echo 2. 다른 프로그램 종료 후 재시도
    echo 3. 더 낮은 메모리 설정으로 재시도
)

echo.
pause 