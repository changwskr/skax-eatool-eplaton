@echo off
REM 한글 인코딩 설정
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ========================================
echo SKAX EA Tool Docker Build Script
echo ========================================

echo.
echo 1. Maven 빌드 시작...
echo.

REM MBC Java 빌드
echo [1/5] MBC Java 빌드 중...
cd mbc-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo MBC Java 빌드 실패!
    pause
    exit /b 1
)
cd ..

REM MBA Java 빌드
echo [2/5] MBA Java 빌드 중...
cd mba-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo MBA Java 빌드 실패!
    pause
    exit /b 1
)
cd ..

REM KSA Java 빌드
echo [3/5] KSA Java 빌드 중...
cd ksa-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo KSA Java 빌드 실패!
    pause
    exit /b 1
)
cd ..

REM KJI Java 빌드
echo [4/5] KJI Java 빌드 중...
cd kji-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo KJI Java 빌드 실패!
    pause
    exit /b 1
)
cd ..

REM API Gateway 빌드
echo [5/5] API Gateway 빌드 중...
cd spring-cloud-apigateway-service
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo API Gateway 빌드 실패!
    pause
    exit /b 1
)
cd ..

echo.
echo 2. Docker 이미지 빌드 시작...
echo.

REM Docker 이미지 빌드
echo [1/5] MBC Service Docker 이미지 빌드 중...
docker build -t skax-mbc-service:latest ./mbc-java
if !errorlevel! neq 0 (
    echo MBC Service Docker 빌드 실패!
    pause
    exit /b 1
)

echo [2/5] MBA Service Docker 이미지 빌드 중...
docker build -t skax-mba-service:latest ./mba-java
if !errorlevel! neq 0 (
    echo MBA Service Docker 빌드 실패!
    pause
    exit /b 1
)

echo [3/5] KSA Service Docker 이미지 빌드 중...
docker build -t skax-ksa-service:latest ./ksa-java
if !errorlevel! neq 0 (
    echo KSA Service Docker 빌드 실패!
    pause
    exit /b 1
)

echo [4/5] KJI Service Docker 이미지 빌드 중...
docker build -t skax-kji-service:latest ./kji-java
if !errorlevel! neq 0 (
    echo KJI Service Docker 빌드 실패!
    pause
    exit /b 1
)

echo [5/5] API Gateway Docker 이미지 빌드 중...
docker build -t skax-api-gateway:latest ./spring-cloud-apigateway-service
if !errorlevel! neq 0 (
    echo API Gateway Docker 빌드 실패!
    pause
    exit /b 1
)

echo.
echo ========================================
echo 모든 빌드가 완료되었습니다!
echo ========================================
echo.
echo 생성된 Docker 이미지:
docker images | findstr skax
echo.
echo 다음 명령어로 서비스를 실행하세요:
echo docker-compose up -d
echo.
pause 