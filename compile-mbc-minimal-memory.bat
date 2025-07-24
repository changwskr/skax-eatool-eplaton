@echo off
echo ========================================
echo MBC 서비스 컴파일 (최소 메모리)
echo ========================================
echo.

REM 현재 디렉토리를 MBC 디렉토리로 변경
cd /d "%~dp0\mbc-java"

echo 현재 디렉토리: %CD%
echo.

echo ========================================
echo MBC 서비스 컴파일 중... (최소 메모리)
echo ========================================
echo.
echo JVM 메모리 설정:
echo - Xms: 128MB (초기 힙 크기)
echo - Xmx: 256MB (최대 힙 크기)
echo - XX:MaxMetaspaceSize: 64MB (메타스페이스 최대 크기)
echo - XX:+UseSerialGC (시리얼 가비지 컬렉터 사용)
echo.

REM Maven 메모리 설정 (최소)
set MAVEN_OPTS=-Xms128m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseSerialGC

REM Maven 컴파일 실행
echo Maven 컴파일 시작...
mvn clean compile -q

if %errorlevel% equ 0 (
    echo.
    echo ✅ MBC 서비스 컴파일 성공!
    echo.
    echo 다음 단계:
    echo 1. run-mbc-minimal-memory.bat 실행하여 서비스 시작
    echo 2. http://localhost:8084 접속하여 확인
) else (
    echo.
    echo ❌ MBC 서비스 컴파일 실패!
    echo 오류 코드: %errorlevel%
    echo.
    echo 해결 방법:
    echo 1. 시스템 메모리 확인 (최소 2GB 필요)
    echo 2. 다른 프로그램 완전 종료 후 재시도
    echo 3. 시스템 재부팅 후 재시도
)

echo.
pause 