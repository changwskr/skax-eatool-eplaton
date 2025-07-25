@echo off
echo ========================================
echo SKAX EATool Eplaton K8s 상태 확인
echo ========================================

echo.
echo 1. Namespace 확인...
kubectl get namespace skax-eatool

echo.
echo 2. 모든 리소스 확인...
kubectl get all -n skax-eatool

echo.
echo 3. Pod 상태 확인...
kubectl get pods -n skax-eatool

echo.
echo 4. Service 상태 확인...
kubectl get services -n skax-eatool

echo.
echo 5. Ingress 상태 확인...
kubectl get ingress -n skax-eatool

echo.
echo 6. ConfigMap 확인...
kubectl get configmaps -n skax-eatool

echo.
echo 7. Secret 확인...
kubectl get secrets -n skax-eatool

echo.
echo ========================================
echo 상태 확인 완료!
echo ========================================
echo.
pause 