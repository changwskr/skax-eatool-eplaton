@echo off
echo ========================================
echo SKAX EATool Eplaton K8s 배포 스크립트
echo ========================================

echo.
echo 1. Namespace 생성...
kubectl apply -f k8s-namespace.yaml

echo.
echo 2. KJI 모듈 배포...
kubectl apply -f kji-java/k8s/configmap.yaml
kubectl apply -f kji-java/k8s/secret.yaml
kubectl apply -f kji-java/k8s/deployment.yaml
kubectl apply -f kji-java/k8s/service.yaml

echo.
echo 3. KSA 모듈 배포...
kubectl apply -f ksa-java/k8s/configmap.yaml
kubectl apply -f ksa-java/k8s/secret.yaml
kubectl apply -f ksa-java/k8s/deployment.yaml
kubectl apply -f ksa-java/k8s/service.yaml

echo.
echo 4. MBA 모듈 배포...
kubectl apply -f mba-java/k8s/configmap.yaml
kubectl apply -f mba-java/k8s/secret.yaml
kubectl apply -f mba-java/k8s/deployment.yaml
kubectl apply -f mba-java/k8s/service.yaml

echo.
echo 5. MBC 모듈 배포...
kubectl apply -f mbc-java/k8s/configmap.yaml
kubectl apply -f mbc-java/k8s/secret.yaml
kubectl apply -f mbc-java/k8s/deployment.yaml
kubectl apply -f mbc-java/k8s/service.yaml

echo.
echo 6. MBC01 모듈 배포...
kubectl apply -f mbc01-java/k8s/configmap.yaml
kubectl apply -f mbc01-java/k8s/secret.yaml
kubectl apply -f mbc01-java/k8s/deployment.yaml
kubectl apply -f mbc01-java/k8s/service.yaml

echo.
echo 7. API Gateway 모듈 배포...
kubectl apply -f spring-cloud-apigateway-service/k8s/configmap.yaml
kubectl apply -f spring-cloud-apigateway-service/k8s/secret.yaml
kubectl apply -f spring-cloud-apigateway-service/k8s/deployment.yaml
kubectl apply -f spring-cloud-apigateway-service/k8s/service.yaml

echo.
echo 8. Ingress 배포...
kubectl apply -f k8s-ingress.yaml

echo.
echo ========================================
echo 배포 완료!
echo ========================================
echo.
echo 배포 상태 확인:
echo kubectl get all -n skax-eatool
echo.
echo 서비스 상태 확인:
echo kubectl get pods -n skax-eatool
echo kubectl get services -n skax-eatool
echo kubectl get ingress -n skax-eatool
echo.
pause 