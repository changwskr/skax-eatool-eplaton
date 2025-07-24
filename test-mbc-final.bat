@echo off
echo ========================================
echo MBC 서비스 최종 테스트
echo ========================================
echo.

echo 1. MBC 홈페이지 접근 테스트 (기본 템플릿)...
curl -s -o nul -w "MBC Home (Default): %%{http_code}\n" http://localhost:8085/mbc/home

echo.
echo 2. MBC 홈페이지 접근 테스트 (최소 템플릿)...
curl -s -o nul -w "MBC Home (Minimal): %%{http_code}\n" http://localhost:8085/mbc/home?template=minimal

echo.
echo 3. MBC API 상태 테스트...
curl -s -o nul -w "MBC API: %%{http_code}\n" http://localhost:8085/mbc/api

echo.
echo 4. MBC 상태 확인 테스트...
curl -s -o nul -w "MBC Status: %%{http_code}\n" http://localhost:8085/mbc/status

echo.
echo 5. MBC 헬스 체크 테스트...
curl -s -o nul -w "MBC Health: %%{http_code}\n" http://localhost:8085/mbc/actuator/health

echo.
echo 6. 브라우저에서 확인할 수 있는 URL들:
echo.
echo 기본 템플릿:
echo - MBC 홈: http://localhost:8085/mbc/home
echo.
echo 최소 템플릿 (문제 발생 시):
echo - MBC 홈 (최소): http://localhost:8085/mbc/home?template=minimal
echo.
echo API 엔드포인트:
echo - MBC API: http://localhost:8085/mbc/api
echo - MBC 상태: http://localhost:8085/mbc/status
echo - MBC 헬스: http://localhost:8085/mbc/actuator/health
echo.
echo API Gateway를 통한 접근:
echo - MBC 홈 via Gateway: http://localhost:8000/mbc/home
echo.
echo 테스트 완료! 