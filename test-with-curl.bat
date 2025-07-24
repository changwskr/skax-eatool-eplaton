@echo off
echo ========================================
echo API Gateway 자동화 테스트 (curl 사용)
echo ========================================
echo.

echo 테스트 전 확인사항:
echo 1. API Gateway가 실행 중인지 확인 (포트 8000)
echo 2. MBA 서비스가 실행 중인지 확인 (포트 8081)
echo 3. MBC 서비스가 실행 중인지 확인 (포트 8084)
echo.
pause

echo ========================================
echo 1. API Gateway Health Check
echo ========================================
echo.
echo API Gateway Health Check 테스트...
curl -s http://localhost:8000/actuator/health
if %errorlevel% neq 0 (
    echo ❌ API Gateway Health Check 실패
    echo API Gateway가 실행되지 않았습니다.
    pause
    exit /b 1
) else (
    echo ✅ API Gateway Health Check 성공
)
echo.

echo ========================================
echo 2. MBA 서비스 직접 접근 테스트
echo ========================================
echo.
echo MBA 서비스 직접 접근 테스트...
curl -s http://localhost:8081
if %errorlevel% neq 0 (
    echo ❌ MBA 서비스 직접 접근 실패
    echo MBA 서비스가 실행되지 않았습니다.
) else (
    echo ✅ MBA 서비스 직접 접근 성공
)
echo.

echo ========================================
echo 3. MBA 서비스 Gateway 접근 테스트
echo ========================================
echo.
echo MBA 서비스 Gateway 접근 테스트...
curl -s http://localhost:8000/mba
if %errorlevel% neq 0 (
    echo ❌ MBA 서비스 Gateway 접근 실패
) else (
    echo ✅ MBA 서비스 Gateway 접근 성공
)
echo.

echo ========================================
echo 4. MBC 서비스 직접 접근 테스트
echo ========================================
echo.
echo MBC 서비스 직접 접근 테스트...
curl -s http://localhost:8084
if %errorlevel% neq 0 (
    echo ❌ MBC 서비스 직접 접근 실패
    echo MBC 서비스가 실행되지 않았습니다.
) else (
    echo ✅ MBC 서비스 직접 접근 성공
)
echo.

echo ========================================
echo 5. MBC 서비스 Gateway 접근 테스트
echo ========================================
echo.
echo MBC 서비스 Gateway 접근 테스트...
curl -s http://localhost:8000/mbc
if %errorlevel% neq 0 (
    echo ❌ MBC 서비스 Gateway 접근 실패
) else (
    echo ✅ MBC 서비스 Gateway 접근 성공
)
echo.

echo ========================================
echo 6. 필터 헤더 확인 테스트
echo ========================================
echo.
echo 필터 헤더 확인 테스트...
curl -s -v http://localhost:8000/mba 2>&1 | findstr "mba-request\|mba-response"
if %errorlevel% neq 0 (
    echo ❌ 필터 헤더 확인 실패
) else (
    echo ✅ 필터 헤더 확인 성공
)
echo.

echo ========================================
echo 테스트 완료!
echo ========================================
echo.
echo 수동 테스트 URL:
echo - API Gateway Health: http://localhost:8000/actuator/health
echo - MBA Service via Gateway: http://localhost:8000/mba
echo - MBC Service via Gateway: http://localhost:8000/mbc
echo.
pause 