@echo off
echo ========================================
echo MBA 서비스 로그인 테스트
echo ========================================
echo.

echo 1. MBA 홈페이지 접근 테스트...
curl -s -o nul -w "MBA Home: %%{http_code}\n" http://localhost:8084/mba/home

echo.
echo 2. MBA 로그인 페이지 접근 테스트...
curl -s -o nul -w "MBA Login Page: %%{http_code}\n" http://localhost:8084/mba/auth/login

echo.
echo 3. MBA 헬스 체크 테스트...
curl -s -o nul -w "MBA Health: %%{http_code}\n" http://localhost:8084/mba/actuator/health

echo.
echo 4. 브라우저에서 확인할 수 있는 URL들:
echo.
echo 직접 접근:
echo - MBA 홈: http://localhost:8084/mba/home
echo - MBA 로그인: http://localhost:8084/mba/auth/login
echo.
echo API Gateway를 통한 접근:
echo - MBA 홈 via Gateway: http://localhost:8000/mba/home
echo - MBA 로그인 via Gateway: http://localhost:8000/mba/auth/login
echo.
echo 로그인 정보:
echo - 사용자명: admin
echo - 비밀번호: admin
echo.
echo 테스트 완료! 