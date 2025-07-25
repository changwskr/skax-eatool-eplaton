@echo off
echo ========================================
echo SKAX EATool Eplaton K8s 삭제 스크립트
echo ========================================

echo.
echo 모든 리소스 삭제 중...
kubectl delete namespace skax-eatool

echo.
echo ========================================
echo 삭제 완료!
echo ========================================
echo.
pause 