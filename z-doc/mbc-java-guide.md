# MBC Java 개발 가이드

## 목차
1. [개발 환경 설정](#1-개발-환경-설정)
2. [프로젝트 구조](#2-프로젝트-구조)
3. [코딩 컨벤션](#3-코딩-컨벤션)
4. [개발 프로세스](#4-개발-프로세스)
5. [배포 및 운영](#5-배포-및-운영)

## 1. 개발 환경 설정

### 1.1 JDK 11 설치

**Windows 환경**
```bash
# 1. Oracle JDK 11 다운로드 및 설치
# https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html

# 2. 환경 변수 설정
JAVA_HOME=C:\Program Files\Java\jdk-11.0.x
PATH=%JAVA_HOME%\bin;%PATH%

# 3. 설치 확인
java -version
javac -version
```

**Linux/Mac 환경**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-11-jdk

# CentOS/RHEL
sudo yum install java-11-openjdk-devel

# macOS (Homebrew)
brew install openjdk@11
```

### 1.2 IDE 설정 (IntelliJ IDEA 권장)

**IntelliJ IDEA 설정**
1. **프로젝트 설정**
   - File → Project Structure → Project Settings
   - Project SDK: 11
   - Project language level: 11

2. **코드 스타일 설정**
   - File → Settings → Editor → Code Style → Java
   - Import scheme: GoogleStyle 또는 프로젝트 표준

3. **플러그인 설치**
   - SonarLint: 코드 품질 검사
   - CheckStyle-IDEA: 코드 스타일 검사
   - SpotBugs: 버그 패턴 검사
   - Rainbow Brackets: 괄호 색상 구분

**Eclipse 설정 (대안)**
```xml
<!-- .settings/org.eclipse.jdt.ui.prefs -->
eclipse.preferences.version=1
editor.save.participant.enabled=false
formatter_profile=_JavaConventions
```

### 1.3 Git 설정

**Git 초기 설정**
```bash
# 사용자 정보 설정
git config --global user.name "Your Name"
git config --global user.email "your.email@company.com"

# 기본 브랜치 설정
git config --global init.defaultBranch main

# 자격 증명 저장
git config --global credential.helper store
```

**Git Hooks 설정**
```bash
# pre-commit hook 예시
#!/bin/sh
# .git/hooks/pre-commit

# 코드 스타일 검사
mvn checkstyle:check

# 단위 테스트 실행
mvn test

# 빌드 검증
mvn compile
```

### 1.4 Maven/Gradle 설정

**Maven 설정 (권장)**
```xml
<!-- settings.xml -->
<settings>
    <localRepository>${user.home}/.m2/repository</localRepository>
    
    <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <spring.profiles.active>dev</spring.profiles.active>
            </properties>
        </profile>
    </profiles>
</settings>
```

**Gradle 설정 (대안)**
```groovy
// build.gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.18'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}

java {
    sourceCompatibility = '11'
}

repositories {
    mavenCentral()
    mavenLocal()
}
```

## 2. 프로젝트 구조

### 2.1 레이어드 아키텍처 (Controller-Service-Repository)

**표준 3-Tier 아키텍처**
```
src/main/java/com/skax/eatool/mbc/
├── web/                    # Presentation Layer
│   ├── controller/         # REST API 컨트롤러
│   └── dto/               # Data Transfer Objects
├── service/               # Business Logic Layer
│   ├── impl/              # 서비스 구현체
│   └── interface/         # 서비스 인터페이스
└── repository/            # Data Access Layer
    ├── mapper/            # MyBatis Mapper
    └── entity/            # JPA 엔티티
```

**컨트롤러 예시**
```java
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        log.info("사용자 조회 요청: {}", id);
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
```

**서비스 예시**
```java
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDto getUserById(Long id) {
        log.debug("사용자 조회 시작: {}", id);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        return UserDto.from(user);
    }
}
```

### 2.2 패키지 구조

**전체 패키지 구조**
```
com.skax.eatool.mbc/
├── config/                # 설정 클래스
│   ├── DatabaseConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── web/                   # 웹 레이어
│   ├── controller/
│   │   ├── UserController.java
│   │   └── AccountController.java
│   └── dto/
│       ├── UserDto.java
│       └── AccountDto.java
├── service/               # 서비스 레이어
│   ├── UserService.java
│   ├── AccountService.java
│   └── impl/
│       ├── UserServiceImpl.java
│       └── AccountServiceImpl.java
├── repository/            # 데이터 액세스 레이어
│   ├── UserRepository.java
│   ├── AccountRepository.java
│   └── mapper/
│       ├── UserMapper.java
│       └── AccountMapper.java
├── entity/                # 엔티티 클래스
│   ├── User.java
│   └── Account.java
├── exception/             # 예외 클래스
│   ├── UserNotFoundException.java
│   └── BusinessException.java
└── util/                  # 유틸리티 클래스
    ├── DateUtil.java
    └── ValidationUtil.java
```

### 2.3 명명 규칙

**컴포넌트별 명명 규칙**
```java
// PC (Presentation Controller) 컴포넌트: XXXPC
@Controller
public class UserManagementPC {
    // 사용자 관리 화면 컨트롤러
}

// DC (Data Controller) 컴포넌트: XXXDC
@RestController
public class UserApiDC {
    // 사용자 API 컨트롤러
}

// DTO 컴포넌트: XXXDto
public class UserDto {
    private Long id;
    private String name;
    private String email;
    // getter, setter, builder
}

// Mapper: XXXMapper
@Mapper
public interface UserMapper {
    List<User> selectAllUsers();
    User selectUserById(Long id);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(Long id);
}
```

**클래스 명명 규칙**
```java
// 엔티티: 명사형, 단수형
public class User { }
public class Account { }

// 서비스: 명사형 + Service
public interface UserService { }
public class UserServiceImpl { }

// 컨트롤러: 명사형 + Controller
public class UserController { }

// 예외: 명사형 + Exception
public class UserNotFoundException extends RuntimeException { }

// 유틸리티: 명사형 + Util
public class DateUtil { }
```

## 3. 코딩 컨벤션

### 3.1 Java 코드 스타일

**Google Java Style Guide 준수**
```java
// 클래스 선언
public final class UserService {
    
    // 상수는 대문자와 언더스코어
    private static final String DEFAULT_ROLE = "USER";
    private static final int MAX_RETRY_COUNT = 3;
    
    // 필드는 private final 우선
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // 생성자
    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // 메서드는 public, protected, private 순서
    public UserDto createUser(CreateUserRequest request) {
        validateUserRequest(request);
        User user = mapToUser(request);
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
    
    private void validateUserRequest(CreateUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 null일 수 없습니다.");
        }
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
    }
    
    private User mapToUser(CreateUserRequest request) {
        return User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .role(DEFAULT_ROLE)
            .build();
    }
}
```

**메서드 길이 및 복잡도**
```java
// 좋은 예: 메서드가 짧고 명확함
public UserDto getUserById(Long id) {
    validateId(id);
    User user = findUserById(id);
    return UserDto.from(user);
}

private void validateId(Long id) {
    if (id == null || id <= 0) {
        throw new IllegalArgumentException("유효하지 않은 ID입니다: " + id);
    }
}

private User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
}

// 나쁜 예: 메서드가 너무 길고 복잡함
public UserDto getUserById(Long id) {
    if (id == null || id <= 0) {
        throw new IllegalArgumentException("유효하지 않은 ID입니다: " + id);
    }
    
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isEmpty()) {
        throw new UserNotFoundException("사용자를 찾을 수 없습니다: " + id);
    }
    
    User user = userOptional.get();
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail());
    // ... 더 많은 매핑 로직
    
    return userDto;
}
```

### 3.2 주석 작성 규칙

**JavaDoc 주석**
```java
/**
 * 사용자 서비스 클래스
 * 
 * <p>사용자 관련 비즈니스 로직을 처리하는 서비스입니다.
 * 사용자 생성, 조회, 수정, 삭제 기능을 제공합니다.</p>
 * 
 * @author 개발팀
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional
public class UserService {
    
    /**
     * 사용자 ID로 사용자를 조회합니다.
     * 
     * <p>지정된 ID의 사용자가 존재하지 않으면 
     * {@link UserNotFoundException}을 발생시킵니다.</p>
     * 
     * @param id 조회할 사용자의 ID
     * @return 사용자 정보 DTO
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     * @throws IllegalArgumentException ID가 null이거나 음수인 경우
     */
    public UserDto getUserById(Long id) {
        // 구현 로직
    }
}
```

**인라인 주석**
```java
// 좋은 예: 복잡한 비즈니스 로직에 대한 설명
public void processUserRegistration(User user) {
    // 1. 이메일 중복 검사
    if (userRepository.existsByEmail(user.getEmail())) {
        throw new DuplicateEmailException("이미 등록된 이메일입니다.");
    }
    
    // 2. 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    
    // 3. 기본 역할 설정 (신규 사용자는 USER 역할)
    user.setRole(Role.USER);
    
    // 4. 사용자 저장
    userRepository.save(user);
    
    // 5. 환영 이메일 발송
    emailService.sendWelcomeEmail(user.getEmail());
}

// 나쁜 예: 명확하지 않은 주석
public void processUserRegistration(User user) {
    // 사용자 처리
    if (userRepository.existsByEmail(user.getEmail())) {
        throw new DuplicateEmailException("이미 등록된 이메일입니다.");
    }
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setRole(Role.USER);
    userRepository.save(user);
    emailService.sendWelcomeEmail(user.getEmail());
}
```

### 3.3 로깅 가이드

**SLF4J + Logback 사용**
```java
@Slf4j
@Service
public class UserService {
    
    public UserDto createUser(CreateUserRequest request) {
        log.info("사용자 생성 시작: email={}", request.getEmail());
        
        try {
            validateUserRequest(request);
            User user = mapToUser(request);
            User savedUser = userRepository.save(user);
            
            log.info("사용자 생성 완료: userId={}, email={}", 
                    savedUser.getId(), savedUser.getEmail());
            
            return UserDto.from(savedUser);
            
        } catch (Exception e) {
            log.error("사용자 생성 실패: email={}, error={}", 
                     request.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
    
    public UserDto getUserById(Long id) {
        log.debug("사용자 조회 요청: id={}", id);
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("사용자를 찾을 수 없음: id={}", id);
                return new UserNotFoundException("사용자를 찾을 수 없습니다: " + id);
            });
        
        log.debug("사용자 조회 완료: id={}, email={}", id, user.getEmail());
        return UserDto.from(user);
    }
}
```

**로그 레벨 설정**
```xml
<!-- logback-spring.xml -->
<configuration>
    <springProfile name="dev">
        <root level="DEBUG">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>
    
    <springProfile name="prod">
        <root level="INFO">
            <appender-ref ref="FILE"/>
        </root>
        
        <!-- 특정 패키지만 DEBUG 레벨 -->
        <logger name="com.skax.eatool.mbc" level="DEBUG"/>
    </springProfile>
</configuration>
```

### 3.4 예외 처리 가이드

**커스텀 예외 클래스**
```java
// 비즈니스 예외 기본 클래스
public abstract class BusinessException extends RuntimeException {
    
    private final String errorCode;
    
    protected BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    protected BusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

// 구체적인 예외 클래스들
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
}

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(String message) {
        super(message, "DUPLICATE_EMAIL");
    }
}
```

**예외 처리 패턴**
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        log.warn("사용자를 찾을 수 없음: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .errorCode(e.getErrorCode())
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException e) {
        log.warn("유효성 검사 실패: {}", e.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
            .errorCode("VALIDATION_ERROR")
            .message(e.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e) {
        log.error("예상치 못한 오류 발생", e);
        
        ErrorResponse error = ErrorResponse.builder()
            .errorCode("INTERNAL_ERROR")
            .message("서버 내부 오류가 발생했습니다.")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

## 4. 개발 프로세스

### 4.1 브랜치 전략

**Git Flow 브랜치 전략**
```bash
# 메인 브랜치
main          # 프로덕션 배포용
develop       # 개발 통합용

# 보조 브랜치
feature/*     # 기능 개발용
release/*     # 릴리스 준비용
hotfix/*      # 긴급 수정용
```

**브랜치 생성 및 작업 흐름**
```bash
# 1. 기능 개발 시작
git checkout develop
git pull origin develop
git checkout -b feature/user-management

# 2. 개발 작업
git add .
git commit -m "feat: 사용자 관리 기능 추가"
git push origin feature/user-management

# 3. Pull Request 생성
# develop 브랜치로 머지 요청

# 4. 릴리스 준비
git checkout develop
git checkout -b release/v1.2.0
# 버그 수정 및 문서 업데이트
git checkout main
git merge release/v1.2.0
git tag -a v1.2.0 -m "Release version 1.2.0"
```

**커밋 메시지 규칙**
```bash
# 형식: <type>(<scope>): <subject>

# 타입
feat: 새로운 기능
fix: 버그 수정
docs: 문서 수정
style: 코드 스타일 변경
refactor: 코드 리팩토링
test: 테스트 추가/수정
chore: 빌드 프로세스 또는 보조 도구 변경

# 예시
feat(user): 사용자 등록 기능 추가
fix(auth): 로그인 인증 오류 수정
docs(api): API 문서 업데이트
refactor(service): 사용자 서비스 리팩토링
```

### 4.2 코드 리뷰 프로세스

**Pull Request 템플릿**
```markdown
## 변경 사항
- [ ] 새로운 기능 추가
- [ ] 버그 수정
- [ ] 문서 업데이트
- [ ] 코드 리팩토링

## 변경 내용 상세
- 사용자 관리 기능 추가
- 로그인 인증 로직 개선

## 테스트
- [ ] 단위 테스트 추가/수정
- [ ] 통합 테스트 추가/수정
- [ ] 수동 테스트 완료

## 체크리스트
- [ ] 코드 스타일 가이드 준수
- [ ] 주석 작성 완료
- [ ] 예외 처리 구현
- [ ] 로깅 추가
- [ ] 보안 검토 완료

## 관련 이슈
Closes #123
```

**코드 리뷰 체크리스트**
```markdown
## 기능적 검토
- [ ] 요구사항을 정확히 구현했는가?
- [ ] 예외 상황을 고려했는가?
- [ ] 성능에 문제는 없는가?

## 코드 품질
- [ ] 코드 스타일을 준수했는가?
- [ ] 중복 코드가 없는가?
- [ ] 메서드가 너무 길지 않은가?
- [ ] 변수명이 명확한가?

## 보안
- [ ] SQL 인젝션 방지
- [ ] XSS 방지
- [ ] 인증/인가 검증
- [ ] 민감한 정보 노출 방지

## 테스트
- [ ] 단위 테스트 커버리지
- [ ] 통합 테스트 구현
- [ ] 엣지 케이스 테스트
```

### 4.3 테스트 코드 작성

**단위 테스트 (JUnit 5)**
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("사용자 생성 성공")
    void createUser_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("test@example.com")
            .password("password123")
            .name("테스트 사용자")
            .build();
        
        User savedUser = User.builder()
            .id(1L)
            .email("test@example.com")
            .name("테스트 사용자")
            .role(Role.USER)
            .build();
        
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // When
        UserDto result = userService.createUser(request);
        
        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getName()).isEqualTo("테스트 사용자");
        
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("중복 이메일로 사용자 생성 실패")
    void createUser_DuplicateEmail_ThrowsException() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("existing@example.com")
            .password("password123")
            .name("테스트 사용자")
            .build();
        
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(request))
            .isInstanceOf(DuplicateEmailException.class)
            .hasMessage("이미 등록된 이메일입니다.");
        
        verify(userRepository).existsByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }
}
```

**통합 테스트**
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class UserIntegrationTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("사용자 생성 및 조회 통합 테스트")
    void createAndFindUser_Integration() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("integration@example.com")
            .password("password123")
            .name("통합 테스트 사용자")
            .build();
        
        // When
        UserDto createdUser = userService.createUser(request);
        UserDto foundUser = userService.getUserById(createdUser.getId());
        
        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("integration@example.com");
        assertThat(foundUser.getName()).isEqualTo("통합 테스트 사용자");
    }
}
```

**테스트 실행**
```bash
# 전체 테스트 실행
mvn test

# 특정 테스트 클래스 실행
mvn test -Dtest=UserServiceTest

# 특정 테스트 메서드 실행
mvn test -Dtest=UserServiceTest#createUser_Success

# 테스트 커버리지 확인
mvn jacoco:report
```

### 4.4 CI/CD 파이프라인

**GitLab CI/CD 설정**
```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - quality
  - package
  - deploy

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository"

cache:
  paths:
    - .m2/repository

build:
  stage: build
  script:
    - mvn clean compile
  artifacts:
    paths:
      - target/
    expire_in: 1 hour

test:
  stage: test
  script:
    - mvn test
    - mvn jacoco:report
  coverage: '/Total.*?([0-9]{1,3})%/'
  artifacts:
    reports:
      junit: target/surefire-reports/TEST-*.xml
      coverage_report:
        coverage_format: cobertura
        path: target/site/jacoco/jacoco.xml

quality:
  stage: quality
  script:
    - mvn checkstyle:check
    - mvn spotbugs:check
    - mvn sonar:sonar
  only:
    - merge_requests
    - main

package:
  stage: package
  script:
    - mvn package -DskipTests
  artifacts:
    paths:
      - target/*.jar
    expire_in: 1 week

deploy-dev:
  stage: deploy
  script:
    - kubectl apply -f k8s/dev/
  environment:
    name: development
    url: https://dev.mbc.example.com
  only:
    - develop

deploy-prod:
  stage: deploy
  script:
    - kubectl apply -f k8s/prod/
  environment:
    name: production
    url: https://mbc.example.com
  when: manual
  only:
    - main
```

## 5. 배포 및 운영

### 5.1 배포 프로세스

**Docker 이미지 빌드**
```dockerfile
# Dockerfile
FROM openjdk:11-jre-slim

WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY target/mbc-java-*.jar app.jar

# 헬스체크 설정
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Kubernetes 배포 설정**
```yaml
# k8s/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mbc-java
  labels:
    app: mbc-java
spec:
  replicas: 3
  selector:
    matchLabels:
      app: mbc-java
  template:
    metadata:
      labels:
        app: mbc-java
    spec:
      containers:
      - name: mbc-java
        image: mbc-java:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: DB_URL
          valueFrom:
            secretKeyRef:
              name: mbc-secret
              key: db-url
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
```

**서비스 설정**
```yaml
# k8s/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: mbc-java-service
spec:
  selector:
    app: mbc-java
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: LoadBalancer
```

### 5.2 모니터링

**Spring Boot Actuator 설정**
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
```

**Prometheus 설정**
```yaml
# prometheus.yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'mbc-java'
    static_configs:
      - targets: ['mbc-java-service:8080']
    metrics_path: '/actuator/prometheus'
```

**Grafana 대시보드**
```json
{
  "dashboard": {
    "title": "MBC Java Application",
    "panels": [
      {
        "title": "HTTP 요청 수",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_server_requests_seconds_count[5m])",
            "legendFormat": "{{method}} {{uri}}"
          }
        ]
      },
      {
        "title": "응답 시간",
        "type": "graph",
        "targets": [
          {
            "expr": "rate(http_server_requests_seconds_sum[5m]) / rate(http_server_requests_seconds_count[5m])",
            "legendFormat": "{{method}} {{uri}}"
          }
        ]
      }
    ]
  }
}
```

### 5.3 로그 관리

**로그 설정**
```xml
<!-- logback-spring.xml -->
<configuration>
    <springProfile name="prod">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>/app/logs/mbc-java.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>/app/logs/mbc-java.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
                <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                    <maxFileSize>100MB</maxFileSize>
                </timeBasedFileNamingAndTriggeringPolicy>
                <maxHistory>30</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
        
        <root level="INFO">
            <appender-ref ref="FILE"/>
        </root>
    </springProfile>
</configuration>
```

**ELK Stack 로그 수집**
```yaml
# docker-compose.logging.yml
version: '3.8'
services:
  filebeat:
    image: docker.elastic.co/beats/filebeat:7.17.0
    volumes:
      - ./logs:/app/logs:ro
      - ./filebeat.yml:/usr/share/filebeat/filebeat.yml:ro
    environment:
      - ELASTICSEARCH_HOSTS=http://elasticsearch:9200
```

### 5.4 장애 대응

**헬스체크 및 자동 복구**
```yaml
# k8s/hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: mbc-java-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: mbc-java
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
```

**장애 대응 절차**
```bash
#!/bin/bash
# incident-response.sh

# 1. 서비스 상태 확인
kubectl get pods -n mbc
kubectl get services -n mbc

# 2. 로그 확인
kubectl logs -f deployment/mbc-java -n mbc

# 3. 리소스 사용량 확인
kubectl top pods -n mbc

# 4. 스케일링 (필요시)
kubectl scale deployment mbc-java --replicas=5 -n mbc

# 5. 롤백 (필요시)
kubectl rollout undo deployment/mbc-java -n mbc
```

**알림 설정**
```yaml
# alertmanager.yml
global:
  slack_api_url: 'https://hooks.slack.com/services/YOUR/SLACK/WEBHOOK'

route:
  group_by: ['alertname']
  group_wait: 10s
  group_interval: 10s
  repeat_interval: 1h
  receiver: 'slack-notifications'

receivers:
- name: 'slack-notifications'
  slack_configs:
  - channel: '#alerts'
    title: '{{ .GroupLabels.alertname }}'
    text: '{{ range .Alerts }}{{ .Annotations.summary }}{{ end }}'
```

---

## 부록

### A. 개발 도구 설정
- IntelliJ IDEA 설정 가이드
- VS Code 설정 가이드
- Git 설정 가이드

### B. 유용한 명령어 모음
```bash
# Maven 명령어
mvn clean install -DskipTests
mvn spring-boot:run -Dspring.profiles.active=dev
mvn dependency:tree

# Docker 명령어
docker build -t mbc-java .
docker run -p 8080:8080 mbc-java
docker logs <container-id>

# Kubernetes 명령어
kubectl apply -f k8s/
kubectl get pods -n mbc
kubectl logs -f <pod-name> -n mbc
kubectl exec -it <pod-name> -n mbc -- /bin/bash
```

### C. 문제 해결 가이드
- 빌드 오류 해결
- 런타임 오류 해결
- 성능 문제 해결
- 보안 문제 해결

