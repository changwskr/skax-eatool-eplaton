@echo off
echo ========================================
echo API Gateway 라우팅 테스트
echo ========================================
echo.

echo 현재 실행 중인 서비스 확인...
echo.

echo 1. API Gateway Health Check:
echo curl -X GET http://localhost:8000/actuator/health
curl -X GET http://localhost:8000/actuator/health
echo.
echo.

echo 2. MBA Service 직접 접속:
echo curl -X GET http://localhost:8084/mba
curl -X GET http://localhost:8084/mba
echo.
echo.

echo 3. MBA Service via Gateway:
echo curl -X GET http://localhost:8000/mba
curl -X GET http://localhost:8000/mba
echo.
echo.

echo 4. MBC Service 직접 접속:
echo curl -X GET http://localhost:8085
curl -X GET http://localhost:8085
echo.
echo.

echo 5. MBC Service via Gateway:
echo curl -X GET http://localhost:8000/mbc
curl -X GET http://localhost:8000/mbc
echo.
echo.

echo 6. Gateway Metrics:
echo curl -X GET http://localhost:8000/actuator/metrics
curl -X GET http://localhost:8000/actuator/metrics
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
pause 