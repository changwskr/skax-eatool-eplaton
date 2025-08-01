# MBB Java - AI Coding Workflow

## 📋 **프로젝트 개요**

MBB Java는 AI 코딩 워크플로우를 관리하는 Spring Boot 애플리케이션입니다. 테이블 정의를 기반으로 Java 코드를 자동 생성하는 기능을 제공합니다.

## 🏗️ **아키텍처**

### **계층 구조**
```
📦 MBB Java Architecture
├── 🎯 AS (Application Service) - 어플리케이션 서비스
│   └── ASMBB75001.java - AI 코딩 워크플로우 메인 서비스
├── 🔧 PC (Process Component) - 어플리케이션 프로세스
│   └── PCCodeGeneration.java - 코드 생성 프로세스 컴포넌트
├── 💾 DC (Domain Component) - 도메인 컴포넌트
│   ├── DCCodeGeneration.java - 코드 생성 도메인 컴포넌트
│   └── dto/ - 데이터 전송 객체
│       ├── TableDefinitionDDTO.java - 테이블 정의 DTO
│       ├── ColumnDefinitionDDTO.java - 컬럼 정의 DTO
│       └── CodeGenerationTaskDDTO.java - 코드 생성 작업 DTO
├── 🔌 IC (Interface Component) - 인터페이스 컴포넌트
│   └── (외부 시스템 연계)
├── 🛠️ FC (Foundation Component) - 공통 컴포넌트
│   └── template/TemplateService.java - 템플릿 처리 서비스
└── 🌐 Web - 웹 계층
    └── controller/CodeGenerationController.java - REST API 컨트롤러
```

## 🚀 **주요 기능**

### **1. 코드 생성 기능**
- **Entity 클래스 생성**: JPA Entity 클래스 자동 생성
- **Repository 인터페이스 생성**: Spring Data JPA Repository 자동 생성
- **Service 클래스 생성**: 비즈니스 로직 서비스 클래스 자동 생성
- **Controller 클래스 생성**: REST API 컨트롤러 자동 생성
- **HTML 파일 생성**: Thymeleaf 템플릿 기반 HTML 자동 생성
- **DDL 파일 생성**: 데이터베이스 테이블 DDL 자동 생성
- **초기 데이터 생성**: INSERT 문 자동 생성

### **2. API 기능**
- **코드 생성 API**: 테이블 정의를 기반으로 코드 생성
- **테이블 정의 검증 API**: 테이블 정의의 유효성 검증
- **작업 상태 조회 API**: 코드 생성 작업의 상태 조회
- **파일 다운로드 API**: 생성된 파일 다운로드

### **3. 템플릿 엔진**
- **FreeMarker**: 기본 템플릿 엔진
- **Velocity**: 대체 템플릿 엔진
- **템플릿 관리**: 동적 템플릿 처리

## 📦 **기술 스택**

| 분류 | 기술 | 버전 |
|------|------|------|
| **언어** | Java | 11 |
| **프레임워크** | Spring Boot | 2.7.18 |
| **데이터베이스** | H2 Database | 2.1.214 |
| **템플릿 엔진** | FreeMarker, Velocity | 2.3.31, 2.3 |
| **API 문서화** | Swagger/OpenAPI | 1.7.0 |
| **빌드 도구** | Maven | 3.8+ |

## 🛠️ **설치 및 실행**

### **1. 사전 요구사항**
- Java 11 이상
- Maven 3.8 이상
- IDE (IntelliJ IDEA 권장)

### **2. 프로젝트 빌드**
```bash
cd mbb-java
mvn clean install
```

### **3. 애플리케이션 실행**
```bash
mvn spring-boot:run
```

### **4. 접속 정보**
- **애플리케이션**: http://localhost:8085/mbb
- **Swagger UI**: http://localhost:8085/mbb/swagger-ui.html
- **H2 콘솔**: http://localhost:8085/mbb/h2-console

## 📝 **API 사용법**

### **1. 코드 생성 요청**
```bash
curl -X POST http://localhost:8085/mbb/api/mbb/generate \
  -H "Content-Type: application/json" \
  -d '{
    "taskName": "사용자 관리 시스템",
    "description": "사용자 계정 관리 기능",
    "tableDefinition": {
      "tableName": "user_account",
      "comment": "사용자 계정 정보",
      "columns": [
        {
          "name": "id",
          "type": "BIGINT",
          "pk": true,
          "nullable": false,
          "comment": "식별자"
        },
        {
          "name": "username",
          "type": "VARCHAR(50)",
          "nullable": false,
          "comment": "아이디"
        },
        {
          "name": "email",
          "type": "VARCHAR(100)",
          "nullable": false,
          "comment": "이메일"
        },
        {
          "name": "created_at",
          "type": "TIMESTAMP",
          "nullable": false,
          "default": "CURRENT_TIMESTAMP"
        }
      ]
    },
    "tasks": [
      "generate-entity",
      "generate-repository",
      "generate-service",
      "generate-controller",
      "generate-html",
      "generate-ddl",
      "generate-init-data"
    ]
  }'
```

### **2. 테이블 정의 검증**
```bash
curl -X POST http://localhost:8085/mbb/api/mbb/validate \
  -H "Content-Type: application/json" \
  -d '{
    "tableName": "user_account",
    "comment": "사용자 계정 정보",
    "columns": [...]
  }'
```

### **3. 작업 상태 조회**
```bash
curl -X GET http://localhost:8085/mbb/api/mbb/status/{taskId}
```

### **4. 파일 다운로드**
```bash
curl -X GET http://localhost:8085/mbb/api/mbb/download/{taskId}/file \
  -o generated-files.zip
```

## 📁 **프로젝트 구조**

```
mbb-java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/skax/eatool/mbb/
│   │   │       ├── MbbApplication.java          # 메인 애플리케이션
│   │   │       ├── as/                          # Application Service
│   │   │       │   └── codegenas/
│   │   │       │       └── ASMBB75001.java     # AI 코딩 워크플로우 서비스
│   │   │       ├── pc/                          # Process Component
│   │   │       │   └── codegenpc/
│   │   │       │       └── PCCodeGeneration.java # 코드 생성 프로세스
│   │   │       ├── dc/                          # Domain Component
│   │   │       │   ├── codegendc/
│   │   │       │   │   └── DCCodeGeneration.java # 코드 생성 도메인
│   │   │       │   └── dto/                     # DTO 클래스들
│   │   │       │       ├── TableDefinitionDDTO.java
│   │   │       │       ├── ColumnDefinitionDDTO.java
│   │   │       │       └── CodeGenerationTaskDDTO.java
│   │   │       ├── fc/                          # Foundation Component
│   │   │       │   └── template/
│   │   │       │       └── TemplateService.java # 템플릿 처리 서비스
│   │   │       └── web/                         # Web 계층
│   │   │           └── controller/
│   │   │               └── CodeGenerationController.java # REST API
│   │   └── resources/
│   │       ├── application.yml                  # 애플리케이션 설정
│   │       └── templates/                       # 템플릿 파일들
│   └── test/                                    # 테스트 코드
├── pom.xml                                      # Maven 설정
└── README.md                                    # 프로젝트 문서
```

## 🔧 **설정**

### **application.yml 주요 설정**
```yaml
server:
  port: 8085
  servlet:
    context-path: /mbb

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 

mbb:
  code-generation:
    output-dir: generated
    template-dir: templates
    max-file-size: 10MB
    supported-tasks:
      - generate-entity
      - generate-repository
      - generate-service
      - generate-controller
      - generate-html
      - generate-ddl
      - generate-init-data
```

## 📊 **지원하는 작업 타입**

| 작업 타입 | 설명 | 생성 파일 |
|----------|------|----------|
| `generate-entity` | JPA Entity 클래스 생성 | `{TableName}Entity.java` |
| `generate-repository` | Repository 인터페이스 생성 | `{TableName}Repository.java` |
| `generate-service` | Service 클래스 생성 | `{TableName}Service.java` |
| `generate-controller` | Controller 클래스 생성 | `{TableName}Controller.java` |
| `generate-html` | HTML 파일 생성 | `{tableName}-management.html` |
| `generate-ddl` | DDL 파일 생성 | `{tableName}.sql` |
| `generate-init-data` | 초기 데이터 파일 생성 | `{tableName}-init-data.sql` |

## 🐛 **문제 해결**

### **1. 빌드 오류**
```bash
# 의존성 문제 해결
mvn clean install -U

# Java 버전 확인
java -version
```

### **2. 실행 오류**
```bash
# 포트 충돌 확인
netstat -ano | findstr :8085

# 로그 확인
tail -f logs/mbb-java.log
```

### **3. API 오류**
- Swagger UI에서 API 테스트
- 로그 레벨을 DEBUG로 설정하여 상세 로그 확인

## 📈 **개발 로드맵**

### **v1.0.0 (현재)**
- ✅ 기본 코드 생성 기능
- ✅ REST API 제공
- ✅ 템플릿 엔진 지원
- ✅ 파일 다운로드 기능

### **v1.1.0 (계획)**
- 🔄 AI 모델 연동 (OpenAI GPT, Claude)
- 🔄 고급 템플릿 기능
- 🔄 코드 품질 검사
- 🔄 버전 관리 기능

### **v1.2.0 (계획)**
- 🔄 실시간 코드 생성
- 🔄 협업 기능
- 🔄 코드 리뷰 시스템
- 🔄 배포 자동화

## 🤝 **기여하기**

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 **라이선스**

이 프로젝트는 SKAX Inc.의 프로프라이어터리 소프트웨어입니다.

## 📞 **문의**

- **개발팀**: SKAX Team
- **이메일**: dev@skax.com
- **문서**: [API 문서](http://localhost:8085/mbb/swagger-ui.html) 