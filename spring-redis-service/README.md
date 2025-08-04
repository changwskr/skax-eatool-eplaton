# Spring Redis Service

Spring Boot + Embedded Redis를 사용한 개발 환경용 Redis 서비스입니다.

## 🚀 주요 기능

- **Embedded Redis**: 개발 환경에서 Redis 서버 자동 기동/중지
- **Redis 캐시 서비스**: 키-값 저장, 조회, 삭제, TTL 관리
- **REST API**: Redis 기능을 테스트할 수 있는 REST API 제공
- **프로파일 분리**: 환경별 설정 분리 (local, dev, test, prod)

## 🏗️ 기술 스택

- **Java**: 11
- **Spring Boot**: 2.7.18
- **Redis 클라이언트**: Lettuce (Spring 기본)
- **Embedded Redis**: it.ozimov:embedded-redis:0.7.3
- **Build Tool**: Maven
- **테스트**: SpringBootTest + 내장 Redis 연동

## 📁 프로젝트 구조

```
com.skax.eatool.redis
├── config
│   └── EmbeddedRedisConfig.java         # Redis 내장 기동/중지 관리
├── service
│   └── RedisCacheService.java           # Redis 연동 비즈니스 로직
├── controller
│   └── RedisTestController.java         # 테스트용 REST API
└── RedisApplication.java                # SpringBootApplication 실행 진입점
```

## 🚀 실행 방법

### 1. 프로젝트 빌드
```bash
mvn clean compile
```

### 2. 애플리케이션 실행
```bash
# 로컬 환경 (Embedded Redis 사용)
mvn spring-boot:run -Dspring.profiles.active=local

# 개발 환경
mvn spring-boot:run -Dspring.profiles.active=dev

# 테스트 환경
mvn spring-boot:run -Dspring.profiles.active=test
```

### 3. 애플리케이션 접속
- **URL**: http://localhost:8087/redis-service
- **Context Path**: /redis-service
- **Redis 포트**: 6379

## 📋 API 엔드포인트

### Redis 상태 확인
```bash
GET /redis-service/redis/status
```

### 키-값 저장
```bash
POST /redis-service/redis/put?key=test&value=hello
```

### 값 조회
```bash
GET /redis-service/redis/get?key=test
```

### 키 삭제
```bash
DELETE /redis-service/redis/delete?key=test
```

### 키 존재 확인
```bash
GET /redis-service/redis/exists?key=test
```

### TTL 조회
```bash
GET /redis-service/redis/ttl?key=test
```

### TTL 설정
```bash
POST /redis-service/redis/ttl?key=test&ttl=3600
```

## 🧪 테스트

### 단위 테스트 실행
```bash
mvn test
```

### 통합 테스트 실행
```bash
mvn test -Dspring.profiles.active=test
```

### 테스트 커버리지 확인
```bash
mvn test jacoco:report
```

### 테스트 결과 확인
```bash
# 테스트 결과는 target/site/jacoco/index.html에서 확인 가능
```

## 🔧 설정

### application.yml
```yaml
server:
  port: 8087
  servlet:
    context-path: /redis-service

spring:
  application:
    name: spring-redis-service
  profiles:
    active: local
  
  redis:
    host: localhost
    port: 6379
    timeout: 3000ms
    database: 0
    lettuce:
      pool:
        max-active: 10
        max-idle: 5
        min-idle: 1
        max-wait: 3000ms
    connect-timeout: 2000ms
    socket-timeout: 2000ms

logging:
  level:
    com.skax.eatool.redis: DEBUG
    org.springframework.data.redis: DEBUG
    redis.embedded: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

### 프로파일별 설정
- **local/dev/test**: Embedded Redis 사용
- **prod**: 외부 Redis 서버 사용 (환경변수 설정)

## 🧪 테스트 시나리오

### 1. 기본 CRUD 테스트
```bash
# 1. 키-값 저장
curl -X POST "http://localhost:8087/redis-service/redis/put?key=user:1&value=John Doe"

# 2. 값 조회
curl "http://localhost:8087/redis-service/redis/get?key=user:1"

# 3. 키 존재 확인
curl "http://localhost:8087/redis-service/redis/exists?key=user:1"

# 4. TTL 설정
curl -X POST "http://localhost:8087/redis-service/redis/ttl?key=user:1&ttl=60"

# 5. TTL 조회
curl "http://localhost:8087/redis-service/redis/ttl?key=user:1"

# 6. 키 삭제
curl -X DELETE "http://localhost:8087/redis-service/redis/delete?key=user:1"
```

### 2. 성능 테스트
```bash
# 대량 데이터 저장 테스트
for i in {1..1000}; do
  curl -X POST "http://localhost:8087/redis-service/redis/put?key=test:$i&value=value$i"
done

# 대량 데이터 조회 테스트
for i in {1..1000}; do
  curl "http://localhost:8087/redis-service/redis/get?key=test:$i"
done
```

### 3. 에러 처리 테스트
```bash
# 존재하지 않는 키 조회
curl "http://localhost:8087/redis-service/redis/get?key=nonexistent"

# 잘못된 파라미터로 요청
curl -X POST "http://localhost:8087/redis-service/redis/put"

# Redis 서버 중지 후 테스트
# (Redis 서버를 중지한 후 API 호출하여 에러 처리 확인)
```

### 4. TTL 테스트
```bash
# 1초 TTL로 저장
curl -X POST "http://localhost:8087/redis-service/redis/put?key=temp&value=will_expire"
curl -X POST "http://localhost:8087/redis-service/redis/ttl?key=temp&ttl=1"

# 1초 후 조회 (만료 확인)
sleep 2
curl "http://localhost:8087/redis-service/redis/get?key=temp"
```

## 🔍 로그 확인

애플리케이션 실행 시 다음과 같은 로그를 확인할 수 있습니다:

```
✅ Embedded Redis 서버가 시작되었습니다. (포트: 6379)
```

### 로그 레벨 설정
```yaml
logging:
  level:
    com.skax.eatool.redis: DEBUG      # Redis 서비스 로그
    org.springframework.data.redis: DEBUG  # Spring Data Redis 로그
    redis.embedded: INFO               # Embedded Redis 로그
```

## 🚨 주의사항

1. **운영 환경**: prod 프로파일에서는 Embedded Redis가 비활성화됩니다.
2. **포트 충돌**: 6379 포트가 이미 사용 중인 경우 다른 포트로 변경하세요.
3. **메모리 사용량**: Embedded Redis는 추가 메모리를 사용합니다.
4. **테스트 환경**: 테스트 시 Embedded Redis가 자동으로 시작/중지됩니다.

## 🔄 확장 가능성

- **Redis Cluster**: Redis Cluster 구성 지원
- **Redis Sentinel**: Redis Sentinel 구성 지원
- **Spring Cache**: Spring Cache Manager 연동
- **Reactive Redis**: ReactiveRedisTemplate 사용

## 📝 라이선스

SKAX Inc. All rights reserved. 