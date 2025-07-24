# Docker 배포 문제 해결 가이드

## "no main manifest attribute, in app.jar" 오류 해결

### 문제 원인
이 오류는 JAR 파일에 메인 클래스가 정의되지 않았거나 JAR 파일이 제대로 생성되지 않았을 때 발생합니다.

### 해결 방법

#### 1. Maven 빌드 확인
```bash
# 각 프로젝트에서 Maven 빌드 실행
cd mbc-java
mvn clean package -DskipTests

cd ../mba-java
mvn clean package -DskipTests

cd ../ksa-java
mvn clean package -DskipTests

cd ../kji-java
mvn clean package -DskipTests

cd ../spring-cloud-apigateway-service
mvn clean package -DskipTests
```

#### 2. JAR 파일 확인
```bash
# JAR 파일이 제대로 생성되었는지 확인
ls -la mbc-java/target/*.jar
ls -la mba-java/target/*.jar
ls -la ksa-java/target/*.jar
ls -la kji-java/target/*.jar
ls -la spring-cloud-apigateway-service/target/*.jar
```

#### 3. JAR 파일 내용 확인
```bash
# JAR 파일의 MANIFEST.MF 확인
jar tf mbc-java/target/mbc-java-*.jar | grep MANIFEST
jar tf mba-java/target/mba-java-*.jar | grep MANIFEST
jar tf ksa-java/target/ksa-java-*.jar | grep MANIFEST
jar tf kji-java/target/kji-java-*.jar | grep MANIFEST
jar tf spring-cloud-apigateway-service/target/spring-cloud-apigateway-service-*.jar | grep MANIFEST
```

#### 4. MANIFEST.MF 내용 확인
```bash
# MANIFEST.MF 파일 내용 확인
unzip -p mbc-java/target/mbc-java-*.jar META-INF/MANIFEST.MF
unzip -p mba-java/target/mba-java-*.jar META-INF/MANIFEST.MF
unzip -p ksa-java/target/ksa-java-*.jar META-INF/MANIFEST.MF
unzip -p kji-java/target/kji-java-*.jar META-INF/MANIFEST.MF
unzip -p spring-cloud-apigateway-service/target/spring-cloud-apigateway-service-*.jar META-INF/MANIFEST.MF
```

### 예상되는 MANIFEST.MF 내용
```
Manifest-Version: 1.0
Implementation-Title: mbc-java
Implementation-Version: 1.0.0-SNAPSHOT
Start-Class: com.skax.eatool.mbc.MbcApplication
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Spring-Boot-Version: 2.7.18
Main-Class: org.springframework.boot.loader.JarLauncher
```

## 기타 일반적인 문제들

### 1. 포트 충돌
```bash
# 사용 중인 포트 확인
netstat -ano | findstr :8000
netstat -ano | findstr :8082
netstat -ano | findstr :8083
netstat -ano | findstr :8084
netstat -ano | findstr :8085

# 포트를 사용하는 프로세스 종료
taskkill /PID [프로세스ID] /F
```

### 2. 메모리 부족
```bash
# Docker Desktop 메모리 설정 확인
# Docker Desktop > Settings > Resources > Memory > 4GB 이상 권장

# 컨테이너 메모리 사용량 확인
docker stats
```

### 3. 네트워크 문제
```bash
# Docker 네트워크 확인
docker network ls
docker network inspect skax-eatool-eplaton-master_skax-network

# 컨테이너 간 통신 테스트
docker exec skax-mbc-service ping skax-api-gateway
```

### 4. 빌드 실패
```bash
# Maven 캐시 정리
mvn clean

# Docker 이미지 정리
docker system prune -a

# 다시 빌드
./docker-build-utf8.bat
```

## 디버깅 명령어

### 1. 컨테이너 로그 확인
```bash
# 모든 서비스 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f mbc-service
docker-compose logs -f mba-service
docker-compose logs -f api-gateway
```

### 2. 컨테이너 내부 접속
```bash
# 컨테이너 내부 접속
docker exec -it skax-mbc-service /bin/bash
docker exec -it skax-mba-service /bin/bash
docker exec -it skax-api-gateway /bin/bash
```

### 3. JAR 파일 실행 테스트
```bash
# 컨테이너 내부에서 JAR 파일 직접 실행 테스트
docker exec -it skax-mbc-service java -jar app.jar
```

### 4. 환경 변수 확인
```bash
# 컨테이너 환경 변수 확인
docker exec skax-mbc-service env
docker exec skax-mba-service env
docker exec skax-api-gateway env
```

## 해결된 설정들

### 1. Spring Boot Maven Plugin 설정
모든 프로젝트의 `pom.xml`에 다음 설정이 추가되었습니다:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <mainClass>com.skax.eatool.mbc.MbcApplication</mainClass>
        <layout>JAR</layout>
        <executable>true</executable>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. Dockerfile 설정
모든 Dockerfile에서 표준적인 JAR 실행 방식을 사용합니다:

```dockerfile
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

### 3. 메인 클래스 확인
각 서비스의 메인 클래스:
- MBC: `com.skax.eatool.mbc.MbcApplication`
- MBA: `com.skax.eatool.mba.MbaApplication`
- KSA: `com.skax.eatool.ksa.KsaApplication`
- KJI: `com.skax.eatool.kji.KjiApplication`
- API Gateway: `com.example.apigatewayservice.ApigatewayServiceApplication`

## 권장 해결 순서

1. **Maven 빌드 재실행**
   ```bash
   ./docker-build-utf8.bat
   ```

2. **JAR 파일 확인**
   ```bash
   # target 디렉토리에 JAR 파일이 생성되었는지 확인
   ls -la */target/*.jar
   ```

3. **Docker 이미지 재빌드**
   ```bash
   docker-compose down
   docker system prune -a
   docker-compose up --build
   ```

4. **로그 확인**
   ```bash
   docker-compose logs -f
   ```

## 추가 지원

문제가 지속되면 다음 정보를 확인해주세요:

1. Maven 버전: `mvn -version`
2. Java 버전: `java -version`
3. Docker 버전: `docker --version`
4. 운영체제 정보
5. 전체 빌드 로그
6. 컨테이너 로그 