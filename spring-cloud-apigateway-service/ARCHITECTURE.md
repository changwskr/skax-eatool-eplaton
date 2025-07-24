# Spring Cloud API Gateway Architecture

## 개요

Spring Cloud API Gateway는 SKAX EA Tool의 모든 마이크로서비스에 대한 단일 진입점을 제공합니다.

## 아키텍처 구성

```
┌─────────────────────────────────────────────────────────────┐
│                    Client Applications                      │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                Spring Cloud API Gateway                     │
│                    (Port: 8000)                            │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Global    │  │   Custom    │  │  Logging    │        │
│  │   Filter    │  │   Filter    │  │   Filter    │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Eureka Server                            │
│                    (Port: 8761)                            │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Microservices                            │
├─────────────────┬─────────────────┬─────────────────────────┤
│   MBA Service   │   KSA Service   │   KJI Service          │
│   (Port: 8081)  │   (Port: 8082)  │   (Port: 8083)        │
├─────────────────┼─────────────────┼─────────────────────────┤
│   MBC Service   │  MBC01 Service  │   Other Services       │
│   (Port: 8084)  │   (Port: 8085)  │                        │
└─────────────────┴─────────────────┴─────────────────────────┘
```

## 라우팅 규칙

### 경로 기반 라우팅

| 서비스 | 경로 패턴 | 대상 서비스 | 설명 |
|--------|-----------|-------------|------|
| MBA | `/mba/**` | MBA-SERVICE | Master Business Application |
| KSA | `/ksa/**` | KSA-SERVICE | Kesa Service Application |
| KJI | `/kji/**` | KJI-SERVICE | Kesa Java Interface |
| MBC | `/mbc/**` | MBC-SERVICE | Master Business Component |
| MBC01 | `/mbc01/**` | MBC01-SERVICE | MBC01 Service |

### HTTP 메서드 기반 라우팅

```yaml
- id: user-service
  uri: lb://USER-SERVICE
  predicates:
    - Path=/user-service/login
    - Method=POST
```

## 필터 체인

### 1. GlobalFilter
- 모든 요청에 적용되는 전역 필터
- 기본 메시지와 로깅 설정

### 2. CustomFilter
- 비즈니스 로직을 위한 커스텀 필터
- 요청/응답 헤더 추가

### 3. LoggingFilter
- 요청/응답 로깅
- 성능 모니터링

### 4. AuthorizationHeaderFilter
- JWT 토큰 인증
- 보안 검증

## 필터 실행 순서

```
Request → GlobalFilter → CustomFilter → LoggingFilter → AuthorizationHeaderFilter → Target Service
```

## 로드 밸런싱

Spring Cloud Gateway는 Netflix Ribbon을 사용하여 클라이언트 사이드 로드 밸런싱을 제공합니다.

### 라운드 로빈 방식
- 기본 로드 밸런싱 알고리즘
- 서비스 인스턴스 간 순차적 분배

### 가중치 기반 방식
- 서비스 인스턴스별 가중치 설정 가능
- 트래픽 분배 비율 조정

## 서비스 디스커버리

### Eureka Client
- 서비스 등록/해제 자동화
- 헬스 체크 기반 상태 관리
- 서비스 목록 캐싱

### 서비스 등록
```yaml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka
```

## 모니터링 및 관찰성

### Actuator 엔드포인트
- `/actuator/health`: 서비스 상태 확인
- `/actuator/metrics`: 메트릭 수집
- `/actuator/httptrace`: HTTP 요청/응답 추적
- `/actuator/prometheus`: Prometheus 메트릭

### 로깅
- 요청/응답 로그
- 필터 실행 로그
- 에러 로그

## 보안

### JWT 토큰 인증
- Authorization 헤더 검증
- 토큰 유효성 검사
- 권한 기반 접근 제어

### CORS 설정
- 크로스 오리진 요청 처리
- 허용된 도메인 설정

## 성능 최적화

### 캐싱
- 서비스 목록 캐싱
- 응답 캐싱 (필요시)

### 연결 풀링
- HTTP 클라이언트 연결 풀
- 연결 재사용

### 압축
- 응답 압축 (gzip)
- 대역폭 절약

## 장애 처리

### Circuit Breaker
- 서비스 장애 시 빠른 실패
- 장애 전파 방지

### Fallback
- 서비스 장애 시 대체 응답
- 사용자 경험 개선

### Retry
- 일시적 장애 시 재시도
- 지수 백오프 적용

## 확장성

### 수평 확장
- API Gateway 인스턴스 추가
- 로드 밸런서를 통한 분산

### 수직 확장
- 리소스 증가 (CPU, 메모리)
- 성능 향상

## 배포 전략

### Blue-Green 배포
- 무중단 배포
- 롤백 용이성

### Canary 배포
- 점진적 배포
- 위험 최소화

## 개발 환경

### 로컬 개발
```bash
# API Gateway 실행
cd spring-cloud-apigateway-service
mvn spring-boot:run
```

### Docker 배포
```dockerfile
FROM openjdk:11-jre-slim
COPY target/apigateway-service.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 운영 환경

### 로그 관리
- 구조화된 로그 (JSON)
- 로그 집계 및 분석
- 알림 설정

### 모니터링
- Prometheus 메트릭 수집
- Grafana 대시보드
- 알림 규칙 설정

### 백업 및 복구
- 설정 파일 백업
- 장애 복구 절차
- 데이터 복구 계획 