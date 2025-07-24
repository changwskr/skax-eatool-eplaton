@echo off
echo ========================================
echo 개별 서비스 테스트
echo ========================================
echo.

echo 1. MBA 서비스 테스트 (포트 8084):
echo.
echo MBA 서비스 직접 접속:
curl -X GET http://localhost:8084/mba
echo.
echo MBA 서비스 API:
curl -X GET http://localhost:8084/mba/api
echo.
echo MBA 서비스 상태:
curl -X GET http://localhost:8084/mba/status
echo.
echo.

echo 2. MBC 서비스 테스트 (포트 8085):
echo.
echo MBC 서비스 직접 접속:
curl -X GET http://localhost:8085/mbc/home
echo.
echo MBC 서비스 API:
curl -X GET http://localhost:8085/mbc/api
echo.
echo MBC 서비스 상태:
curl -X GET http://localhost:8085/mbc/status
echo.
echo.

echo 3. API Gateway 테스트 (포트 8000):
echo.
echo API Gateway Health:
curl -X GET http://localhost:8000/actuator/health
echo.
echo.

echo 4. API Gateway를 통한 라우팅 테스트:
echo.
echo MBA Service via Gateway:
curl -X GET http://localhost:8000/mba
echo.
echo MBC Service via Gateway:
curl -X GET http://localhost:8000/mbc/home
echo.
echo.

echo ========================================
echo 테스트 완료!
echo ========================================
echo.
echo 결과 해석:
echo - 200 OK: 정상 응답
echo - 404 Not Found: 서비스가 실행되지 않음
echo - Connection refused: 포트가 사용되지 않음
echo.
echo 서비스가 실행되지 않은 경우:
echo 1. run-mba-minimal-memory.bat 실행
echo 2. run-mbc-minimal-memory.bat 실행  
echo 3. run-apigateway-minimal-memory.bat 실행
echo.
pause 