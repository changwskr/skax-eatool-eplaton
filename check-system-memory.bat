@echo off
echo ========================================
echo 시스템 메모리 상태 확인
echo ========================================
echo.

echo 현재 시스템 메모리 정보:
echo.

REM 전체 물리 메모리 확인
for /f "tokens=2 delims==" %%a in ('wmic computersystem get TotalPhysicalMemory /value ^| find "TotalPhysicalMemory"') do set total_memory=%%a
set /a total_gb=%total_memory:~0,-1%/1024/1024/1024
echo 전체 물리 메모리: %total_gb% GB

REM 사용 가능한 메모리 확인
for /f "tokens=2 delims==" %%a in ('wmic OS get FreePhysicalMemory /value ^| find "FreePhysicalMemory"') do set free_memory=%%a
set /a free_mb=%free_memory%/1024
echo 사용 가능한 메모리: %free_mb% MB

REM 메모리 사용률 계산
set /a used_mb=%total_memory:~0,-1%/1024/1024-%free_memory%/1024
set /a usage_percent=(%used_mb%*100)/(%total_memory:~0,-1%/1024/1024)
echo 메모리 사용률: %usage_percent%%%

echo.
echo ========================================
echo 메모리 권장사항
echo ========================================
echo.

if %total_gb% lss 4 (
    echo ⚠️  경고: 시스템 메모리가 4GB 미만입니다.
    echo    권장: 최소 메모리 설정 사용
    echo    스크립트: compile-mbc-minimal-memory.bat
) else if %total_gb% lss 8 (
    echo ℹ️  정보: 시스템 메모리가 4-8GB입니다.
    echo    권장: 중간 메모리 설정 사용
    echo    스크립트: compile-mbc-with-memory.bat
) else (
    echo ✅ 좋음: 시스템 메모리가 8GB 이상입니다.
    echo    권장: 표준 메모리 설정 사용 가능
)

if %free_mb% lss 1024 (
    echo ⚠️  경고: 사용 가능한 메모리가 1GB 미만입니다.
    echo    권장: 다른 프로그램을 종료하고 재시도
) else if %free_mb% lss 2048 (
    echo ℹ️  정보: 사용 가능한 메모리가 1-2GB입니다.
    echo    권장: 최소 메모리 설정 사용
) else (
    echo ✅ 좋음: 충분한 사용 가능한 메모리가 있습니다.
)

echo.
echo ========================================
echo 실행 권장 순서
echo ========================================
echo.

if %free_mb% lss 1024 (
    echo 1. 다른 프로그램 종료 (브라우저, IDE 등)
    echo 2. compile-mbc-minimal-memory.bat 실행
    echo 3. run-mbc-minimal-memory.bat 실행
) else if %total_gb% lss 4 (
    echo 1. compile-mbc-minimal-memory.bat 실행
    echo 2. run-mbc-minimal-memory.bat 실행
) else (
    echo 1. compile-mbc-with-memory.bat 실행
    echo 2. run-mbc-with-memory.bat 실행
)

echo.
pause 