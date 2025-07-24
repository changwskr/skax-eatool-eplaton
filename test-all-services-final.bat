@echo off
echo ========================================
echo 모든 서비스 최종 통합 테스트
echo ========================================
echo.

echo 1. MBC 서비스 테스트...
echo [MBC] 홈페이지 접근 테스트 (기본: minimal)...
curl -s -o nul -w "MBC Home (Minimal): %%{http_code}\n" http://localhost:8085/mbc/home

echo [MBC] API 상태 테스트...
curl -s -o nul -w "MBC API: %%{http_code}\n" http://localhost:8085/mbc/api

echo [MBC] 헬스 체크 테스트...
curl -s -o nul -w "MBC Health: %%{http_code}\n" http://localhost:8085/mbc/actuator/health

echo.
echo 2. MBA 서비스 테스트...
echo [MBA] 홈페이지 접근 테스트...
curl -s -o nul -w "MBA Home: %%{http_code}\n" http://localhost:8084/mba/home

echo [MBA] 로그인 페이지 접근 테스트...
curl -s -o nul -w "MBA Login: %%{http_code}\n" http://localhost:8084/mba/auth/login

echo [MBA] 헬스 체크 테스트...
curl -s -o nul -w "MBA Health: %%{http_code}\n" http://localhost:8084/mba/actuator/health

echo.
echo 3. API Gateway 테스트...
echo [Gateway] 홈페이지 접근 테스트...
curl -s -o nul -w "Gateway Home: %%{http_code}\n" http://localhost:8000

echo [Gateway] MBC 라우팅 테스트...
curl -s -o nul -w "Gateway->MBC: %%{http_code}\n" http://localhost:8000/mbc/home

echo [Gateway] MBA 라우팅 테스트...
curl -s -o nul -w "Gateway->MBA: %%{http_code}\n" http://localhost:8000/mba/home

echo.
echo 4. 브라우저에서 확인할 수 있는 URL들:
echo.
echo 직접 접근:
echo - MBC (기본): http://localhost:8085/mbc/home
echo - MBC (simple): http://localhost:8085/mbc/home?template=simple
echo - MBA: http://localhost:8084/mba/home
echo - MBA 로그인: http://localhost:8084/mba/auth/login
echo.
echo API Gateway를 통한 접근:
echo - MBC via Gateway: http://localhost:8000/mbc/home
echo - MBA via Gateway: http://localhost:8000/mba/home
echo - MBA 로그인 via Gateway: http://localhost:8000/mba/auth/login
echo.
echo 로그인 정보:
echo - 사용자명: admin
echo - 비밀번호: admin
echo.
echo 모든 테스트 완료! 