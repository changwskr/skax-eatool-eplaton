# Spring Cloud API Gateway Service

SKAX EA Tool의 Spring Cloud API Gateway 서비스입니다.

## 개요

이 서비스는 모든 마이크로서비스의 진입점 역할을 하며, 다음과 같은 기능을 제공합니다:

- **라우팅**: 요청을 적절한 마이크로서비스로 전달
- **필터링**: 요청/응답에 대한 전역 필터 적용
- **로드 밸런싱**: 서비스 인스턴스 간 부하 분산
- **인증/인가**: JWT 토큰 기반 인증 처리
- **모니터링**: 요청/응답 로깅 및 메트릭 수집

## 서비스 구성

### 라우팅 설정

다음 서비스들이 API Gateway를 통해 라우팅됩니다:

| 서비스명 | 경로 | 포트 | 설명 |
|---------|------|------|------|
| MBA-SERVICE | `/mba/**` | 8081 | Master Business Application |
| KSA-SERVICE | `/ksa/**` | 8082 | Kesa Service Application |
| KJI-SERVICE | `/kji/**` | 8083 | Kesa Java Interface |
| MBC-SERVICE | `/mbc/**` | 8084 | Master Business Component |
| MBC01-SERVICE | `/mbc01/**` | 8085 | MBC01 Service |

### 필터 구성

- **GlobalFilter**: 모든 요청에 대한 전역 필터
- **CustomFilter**: 커스텀 비즈니스 로직 필터
- **LoggingFilter**: 요청/응답 로깅 필터
- **AuthorizationHeaderFilter**: JWT 토큰 인증 필터

## 실행 방법

### 1. Eureka Server 실행

먼저 Eureka Server가 실행되어야 합니다:

```bash
# Eureka Server 실행 (포트 8761)
java -jar eureka-server.jar
```

### 2. API Gateway 실행

```bash
cd spring-cloud-apigateway-service
mvn spring-boot:run
```

### 3. 서비스 등록

각 마이크로서비스를 실행하면 자동으로 Eureka에 등록됩니다.

## 접속 URL

- **API Gateway**: http://localhost:8000
- **Eureka Dashboard**: http://localhost:8761
- **MBA Service**: http://localhost:8000/mba
- **KSA Service**: http://localhost:8000/ksa
- **KJI Service**: http://localhost:8000/kji
- **MBC Service**: http://localhost:8000/mbc
- **MBC01 Service**: http://localhost:8000/mbc01

## 설정 파일

### application.yml

주요 설정:

```yaml
server:
  port: 8000

spring:
  application:
    name: apigateway-service
  
  cloud:
    gateway:
      routes:
        - id: mba-service
          uri: lb://MBA-SERVICE
          predicates:
            - Path=/mba/**
          filters:
            - AddRequestHeader=mba-request, mba-request-header
            - AddResponseHeader=mba-response, mba-response-header
            - CustomFilter
            - LoggingFilter

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

## 모니터링

### Actuator 엔드포인트

- **Health Check**: http://localhost:8000/actuator/health
- **Metrics**: http://localhost:8000/actuator/metrics
- **HTTP Trace**: http://localhost:8000/actuator/httptrace
- **Prometheus**: http://localhost:8000/actuator/prometheus

### 로그 확인

```bash
# 애플리케이션 로그
tail -f logs/apigateway-service.log

# 필터 로그
grep "Filter" logs/apigateway-service.log
```

## 개발 가이드

### 새로운 라우트 추가

`application.yml`에 새로운 라우트를 추가:

```yaml
- id: new-service
  uri: lb://NEW-SERVICE
  predicates:
    - Path=/new-service/**
  filters:
    - AddRequestHeader=new-request, new-request-header
    - CustomFilter
```

### 커스텀 필터 개발

1. `GlobalFilter` 인터페이스 구현
2. 필터 로직 작성
3. `application.yml`에 필터 등록

```java
@Component
public class CustomFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 필터 로직 구현
        return chain.filter(exchange);
    }
}
```

## 문제 해결

### 서비스 연결 실패

1. Eureka Server가 실행 중인지 확인
2. 서비스가 Eureka에 등록되었는지 확인
3. 서비스 포트가 올바른지 확인

### 라우팅 오류

1. `application.yml`의 라우트 설정 확인
2. 서비스 이름이 Eureka에 등록된 이름과 일치하는지 확인
3. 필터 설정 확인

## 기술 스택

- **Spring Boot**: 2.7.18
- **Spring Cloud**: 2021.0.8
- **Spring Cloud Gateway**: 3.1.8
- **Netflix Eureka Client**: 3.1.8
- **Java**: 11

## 라이선스

SKAX EA Tool 프로젝트의 일부입니다. 