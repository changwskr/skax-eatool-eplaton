@echo off
REM UTF-8 인코딩으로 한글 표시 설정
chcp 65001 >nul
setlocal enabledelayedexpansion

title SKAX EA Tool Docker Build

echo.
echo ========================================
echo   SKAX EA Tool Docker Build Script
echo ========================================
echo.

echo [STEP 1] Maven 빌드 시작...
echo.

REM MBC Java 빌드
echo [1/5] MBC Java 빌드 중...
cd mbc-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo [ERROR] MBC Java 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] MBC Java 빌드 완료
cd ..

REM MBA Java 빌드
echo [2/5] MBA Java 빌드 중...
cd mba-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo [ERROR] MBA Java 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] MBA Java 빌드 완료
cd ..

REM KSA Java 빌드
echo [3/5] KSA Java 빌드 중...
cd ksa-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo [ERROR] KSA Java 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] KSA Java 빌드 완료
cd ..

REM KJI Java 빌드
echo [4/5] KJI Java 빌드 중...
cd kji-java
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo [ERROR] KJI Java 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] KJI Java 빌드 완료
cd ..

REM API Gateway 빌드
echo [5/5] API Gateway 빌드 중...
cd spring-cloud-apigateway-service
call mvn clean package -DskipTests
if !errorlevel! neq 0 (
    echo [ERROR] API Gateway 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] API Gateway 빌드 완료
cd ..

echo.
echo [STEP 2] Docker 이미지 빌드 시작...
echo.

REM Docker 이미지 빌드
echo [1/5] MBC Service Docker 이미지 빌드 중...
docker build -t skax-mbc-service:latest ./mbc-java
if !errorlevel! neq 0 (
    echo [ERROR] MBC Service Docker 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] MBC Service Docker 이미지 생성 완료

echo [2/5] MBA Service Docker 이미지 빌드 중...
docker build -t skax-mba-service:latest ./mba-java
if !errorlevel! neq 0 (
    echo [ERROR] MBA Service Docker 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] MBA Service Docker 이미지 생성 완료

echo [3/5] KSA Service Docker 이미지 빌드 중...
docker build -t skax-ksa-service:latest ./ksa-java
if !errorlevel! neq 0 (
    echo [ERROR] KSA Service Docker 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] KSA Service Docker 이미지 생성 완료

echo [4/5] KJI Service Docker 이미지 빌드 중...
docker build -t skax-kji-service:latest ./kji-java
if !errorlevel! neq 0 (
    echo [ERROR] KJI Service Docker 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] KJI Service Docker 이미지 생성 완료

echo [5/5] API Gateway Docker 이미지 빌드 중...
docker build -t skax-api-gateway:latest ./spring-cloud-apigateway-service
if !errorlevel! neq 0 (
    echo [ERROR] API Gateway Docker 빌드 실패!
    pause
    exit /b 1
)
echo [SUCCESS] API Gateway Docker 이미지 생성 완료

echo.
echo ========================================
echo   모든 빌드가 성공적으로 완료되었습니다!
echo ========================================
echo.
echo 생성된 Docker 이미지 목록:
echo ----------------------------------------
docker images | findstr skax
echo ----------------------------------------
echo.
echo 다음 명령어로 서비스를 실행하세요:
echo   docker-compose up -d
echo.
echo 또는 다음 스크립트를 실행하세요:
echo   docker-run.bat
echo.
pause 