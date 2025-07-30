# SKAX EA Tool Eplaton - Cursor Rules

## 프로젝트 개요
- **프로젝트명**: SKAX EA Tool Eplaton
- **패키지명**: `com.skax.eatool`
- **기술 스택**: Spring Boot 2.7.18, Java 11, Maven
- **아키텍처**: 멀티모듈 레이어드 아키텍처

## 명명 규칙 (Naming Conventions)

### 컴포넌트별 명명 규칙
- **PC (Presentation Controller)**: `XXXPC` - 화면 컨트롤러
- **DC (Data Controller)**: `XXXDC` - API 컨트롤러  
- **DTO**: `XXXDto` - 데이터 전송 객체
- **Mapper**: `XXXMapper` - MyBatis 매퍼
- **Service**: `XXXService` / `XXXServiceImpl` - 비즈니스 로직
- **Repository**: `XXXRepository` - 데이터 액세스
- **Entity**: `XXX` - 엔티티 클래스 (명사형, 단수형)
- **Exception**: `XXXException` - 예외 클래스
- **Util**: `XXXUtil` - 유틸리티 클래스

### 패키지 구조
```
com.skax.eatool.{module}/
├── config/           # 설정 클래스
├── web/             # 웹 레이어
│   ├── controller/  # 컨트롤러
│   └── dto/         # DTO
├── service/         # 서비스 레이어
│   └── impl/        # 서비스 구현체
├── repository/      # 데이터 액세스 레이어
│   └── mapper/      # MyBatis 매퍼
├── entity/          # 엔티티
├── exception/       # 예외 클래스
└── util/            # 유틸리티
```

## 코딩 컨벤션

### Java 코드 스타일
- **Google Java Style Guide** 준수
- **들여쓰기**: 4칸 스페이스
- **최대 줄 길이**: 120자
- **한글 주석** 허용 및 권장
- **Javadoc** 적극 활용

### 클래스 작성 규칙
```java
/**
 * 클래스 설명
 * 
 * @author 작성자
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional
@Slf4j
public class UserService {
    
    // 상수는 대문자와 언더스코어
    private static final String DEFAULT_ROLE = "USER";
    
    // 필드는 private final 우선
    private final UserRepository userRepository;
    
    // 생성자 주입 권장
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 메서드는 public, protected, private 순서
    public UserDto createUser(CreateUserRequest request) {
        // 구현 로직
    }
}
```

### 로깅 가이드
```java
@Slf4j
public class UserService {
    
    public UserDto createUser(CreateUserRequest request) {
        log.info("사용자 생성 시작: email={}", request.getEmail());
        
        try {
            // 비즈니스 로직
            log.info("사용자 생성 완료: userId={}", savedUser.getId());
            return UserDto.from(savedUser);
        } catch (Exception e) {
            log.error("사용자 생성 실패: email={}, error={}", 
                     request.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}
```

### 예외 처리
```java
// 커스텀 예외 클래스
public abstract class BusinessException extends RuntimeException {
    private final String errorCode;
    
    protected BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

// 구체적인 예외
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
}
```

## 개발 프로세스

### Git 브랜치 전략
- **main**: 운영 배포용
- **develop**: 통합 개발용
- **feature/**: 기능 개발용
- **bugfix/**: 버그 수정용
- **release/**: 릴리스 준비용
- **hotfix/**: 긴급 수정용

### 커밋 메시지 규칙
```
<type>(<scope>): <subject>

타입:
- feat: 새로운 기능
- fix: 버그 수정
- docs: 문서 수정
- style: 코드 스타일 변경
- refactor: 코드 리팩토링
- test: 테스트 추가/수정
- chore: 빌드 프로세스 변경

예시:
feat(user): 사용자 등록 기능 추가
fix(auth): 로그인 인증 오류 수정
```

### 코드 리뷰 체크리스트
- [ ] 요구사항 정확히 구현
- [ ] 예외 상황 고려
- [ ] 코드 스타일 준수
- [ ] 중복 코드 제거
- [ ] 메서드 길이 적절
- [ ] 변수명 명확
- [ ] 보안 검토 완료
- [ ] 단위 테스트 작성

## 테스트 코드

### 단위 테스트 (JUnit 5)
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("사용자 생성 성공")
    void createUser_Success() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("test@example.com")
            .build();
        
        // When
        UserDto result = userService.createUser(request);
        
        // Then
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }
}
```

## 빌드 및 배포

### Maven 명령어
```bash
# 전체 빌드
mvn clean install -DskipTests

# 개별 모듈 빌드
mvn clean install -pl mbc-java -DskipTests

# 테스트 실행
mvn test

# Spring Boot 실행
mvn spring-boot:run
```

### Docker 설정
```dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 모니터링 및 로깅

### Spring Boot Actuator
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

### 로그 설정
```xml
<!-- logback-spring.xml -->
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/app/logs/application.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/app/logs/application-%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

## 보안 가이드

### Spring Security 설정
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
```

### 데이터 검증
```java
@Validated
@Service
public class UserService {
    
    public UserDto createUser(@Valid CreateUserRequest request) {
        // 비즈니스 로직
    }
}
```

## 성능 최적화

### JVM 설정
```bash
# 힙 메모리 설정
-Xms2g -Xmx4g

# GC 설정
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
```

### 데이터베이스 최적화
```java
// 페이징 처리
@GetMapping("/users")
public Page<UserDto> getUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
}

// 캐시 활용
@Cacheable("users")
public UserDto getUserById(Long id) {
    return userRepository.findById(id)
        .map(UserDto::from)
        .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
}
```

## 문제 해결

### 자주 발생하는 문제
1. **JAR 파일 누락**: `BUILD_TROUBLESHOOTING.md` 참조
2. **인코딩 문제**: UTF-8 설정 확인
3. **의존성 충돌**: `mvn dependency:tree` 확인
4. **메모리 부족**: JVM 힙 크기 조정

### 디버깅 도구
- **JProfiler**: CPU/메모리 프로파일링
- **VisualVM**: JVM 모니터링
- **Eclipse MAT**: 메모리 분석
- **Spring Boot DevTools**: 개발 편의성

## 참고 자료
- [MBC Java 개발 가이드](zDoc/mbc-java-guide.md)
- [프로젝트 구조 문서](PROJECT_STRUCTURE.md)
- [빌드 문제 해결](BUILD_TROUBLESHOOTING.md) 