# MBC01 Java Module

MBC01 Java 모듈은 MyBatis와 JPA를 동시에 사용할 수 있는 공통 라이브러리 모듈입니다.

## 주요 기능

- **MyBatis 지원**: 기존 MyBatis 기반 데이터 접근
- **JPA 지원**: Spring Data JPA 기반 데이터 접근
- **프로파일 기반 설정**: 환경에 따라 데이터 접근 방식 선택
- **동시 사용**: MyBatis와 JPA를 동시에 사용 가능

## 프로젝트 구조

```
mbc01-java/
├── src/main/java/com/kbstar/mbc/
│   ├── config/                    # 설정 클래스들
│   │   ├── DataAccessConfig.java  # 데이터 접근 방식 설정
│   │   ├── JpaConfig.java         # JPA 설정
│   │   ├── MyBatisConfig.java     # MyBatis 설정
│   │   └── DataAccessFactory.java # 데이터 접근 방식 팩토리
│   ├── fc/foundation/
│   │   ├── entity/                # JPA 엔티티
│   │   │   ├── BaseEntity.java    # 기본 엔티티
│   │   │   └── User.java          # 사용자 엔티티
│   │   ├── repository/jpa/        # JPA 리포지토리
│   │   │   └── UserRepository.java
│   │   ├── service/               # 서비스 인터페이스
│   │   │   └── UserService.java
│   │   ├── service/impl/          # 서비스 구현체
│   │   │   └── UserServiceImpl.java
│   │   └── test/                  # 테스트 클래스
│   │       └── DataAccessTest.java
│   └── ...
└── src/main/resources/
    ├── application.yml            # 기본 설정
    ├── application-mybatis.yml    # MyBatis 프로파일 설정
    ├── application-jpa.yml        # JPA 프로파일 설정
    └── sqlmap/                    # MyBatis 매퍼 XML
```

## 설정 방법

### 1. 프로파일 설정

#### MyBatis 사용 (기본값)

```bash
# JVM 옵션으로 설정
-Dspring.profiles.active=mybatis

# 또는 환경변수로 설정
export SPRING_PROFILES_ACTIVE=mybatis
```

#### JPA 사용

```bash
# JVM 옵션으로 설정
-Dspring.profiles.active=jpa

# 또는 환경변수로 설정
export SPRING_PROFILES_ACTIVE=jpa
```

### 2. application.yml 설정

```yaml
spring:
  profiles:
    active: mybatis # 또는 jpa

app:
  data-access:
    type: mybatis # 또는 jpa
```

## 사용 방법

### 1. JPA 사용 예제

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // JPA 리포지토리

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
```

### 2. MyBatis 사용 예제

```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper; // MyBatis 매퍼

    public User saveUser(User user) {
        userMapper.insertUser(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userMapper.selectAllUsers();
    }
}
```

### 3. 데이터 접근 방식 확인

```java
@Component
public class MyComponent {

    @Autowired
    private DataAccessFactory dataAccessFactory;

    public void checkDataAccess() {
        if (dataAccessFactory.isJpaEnabled()) {
            System.out.println("JPA is enabled");
        } else if (dataAccessFactory.isMyBatisEnabled()) {
            System.out.println("MyBatis is enabled");
        }
    }
}
```

## 의존성

### Maven Dependencies

```xml
<!-- JPA Dependencies -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>2.7.17</version>
</dependency>

<!-- MyBatis Dependencies -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.13</version>
</dependency>

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.1.1</version>
</dependency>

<!-- Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
    <scope>runtime</scope>
</dependency>
```

## 테스트

### 테스트 실행

```bash
# MyBatis 프로파일로 테스트
mvn test -Dspring.profiles.active=mybatis

# JPA 프로파일로 테스트
mvn test -Dspring.profiles.active=jpa
```

### 테스트 클래스

`DataAccessTest` 클래스를 통해 각 데이터 접근 방식의 동작을 확인할 수 있습니다.

## 주의사항

1. **프로파일 설정**: 반드시 올바른 프로파일을 설정해야 합니다.
2. **의존성 충돌**: MyBatis와 JPA 의존성이 충돌하지 않도록 주의하세요.
3. **트랜잭션 관리**: 각 데이터 접근 방식에 맞는 트랜잭션 설정을 사용하세요.

## 라이센스

이 프로젝트는 SKAX Project Team에서 개발되었습니다.
