@echo off
echo ========================================
echo MBC 서비스 /mbc/home 접근 테스트
echo ========================================
echo.

echo 1. MBC 서비스 포트 확인 (8085):
netstat -ano | findstr :8085
echo.

echo 2. MBC 서비스 직접 접근 테스트:
echo.
echo - 홈페이지 접근:
curl -X GET http://localhost:8085/mbc/home
echo.
echo - API 엔드포인트:
curl -X GET http://localhost:8085/mbc/api
echo.
echo - 상태 확인:
curl -X GET http://localhost:8085/mbc/status
echo.

echo 3. API Gateway를 통한 접근 테스트:
echo.
echo - Gateway를 통한 홈페이지:
curl -X GET http://localhost:8000/mbc/home
echo.
echo - Gateway를 통한 API:
curl -X GET http://localhost:8000/mbc/api
echo.

echo.
echo ========================================
echo 테스트 완료
echo ========================================
pause 