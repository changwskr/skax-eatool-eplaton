@echo off
echo ========================================
echo 서비스 설정 검증
echo ========================================
echo.

echo 1. MBA 서비스 설정 확인:
echo - 포트: 8084
echo - Context Path: /mba
echo - Eureka: 비활성화
echo - 접속 URL: http://localhost:8084/mba
echo.

echo 2. MBC 서비스 설정 확인:
echo - 포트: 8085
echo - Context Path: /
echo - Eureka: 비활성화
echo - 접속 URL: http://localhost:8085
echo.

echo 3. API Gateway 설정 확인:
echo - 포트: 8000
echo - Eureka: 비활성화
echo - 라우팅: FilterConfig.java에서 처리
echo - 접속 URL: http://localhost:8000
echo.

echo 4. 라우팅 설정 확인:
echo - MBA via Gateway: http://localhost:8000/mba -> http://localhost:8084/mba
echo - MBC via Gateway: http://localhost:8000/mbc -> http://localhost:8085
echo.

echo 5. 현재 포트 사용 상태 확인:
echo.
netstat -ano | findstr :8084
netstat -ano | findstr :8085
netstat -ano | findstr :8000
echo.

echo ========================================
echo 설정 검증 완료!
echo ========================================
echo.
echo 다음 단계:
echo 1. run-mba-minimal-memory.bat - MBA 서비스 실행
echo 2. run-mbc-minimal-memory.bat - MBC 서비스 실행
echo 3. run-apigateway-minimal-memory.bat - API Gateway 실행
echo 4. test-services-individually.bat - 개별 테스트
echo 5. test-all-integration-minimal-memory.bat - 통합 테스트
echo.
pause 