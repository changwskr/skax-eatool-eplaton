package com.skax.eatool.mbc.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 토큰 테스트 컨트롤러
 * 
 * 개발 환경에서 JWT 토큰을 테스트하기 위한 API를 제공합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/mbc/jwt-test")
public class JwtTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtTestController.class);
    
    /**
     * 개발용 JWT 토큰 생성
     */
    @GetMapping("/generate-token")
    public ResponseEntity<Map<String, Object>> generateDevToken() {
        logger.info("==================[JwtTestController.generateDevToken] - 개발용 JWT 토큰 생성");
        
        // 개발 환경용 간단한 토큰 (실제 운영에서는 JWT 라이브러리 사용)
        String devToken = "dev-jwt-token-" + System.currentTimeMillis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "개발용 JWT 토큰 생성 성공");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("token", devToken);
        response.put("usage", "Authorization: Bearer " + devToken);
        response.put("note", "이 토큰은 개발 환경에서만 사용하세요");
        
        logger.info("개발용 JWT 토큰 생성: {}", devToken);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 토큰 테스트 - 토큰 없이 접근
     */
    @GetMapping("/test-no-token")
    public ResponseEntity<Map<String, Object>> testNoToken() {
        logger.info("==================[JwtTestController.testNoToken] - 토큰 없이 접근 테스트");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "토큰이 없어서 접근이 차단되었습니다");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("expected", "401 Unauthorized 또는 403 Forbidden");
        
        return ResponseEntity.status(401).body(response);
    }
    
    /**
     * 토큰 테스트 - 유효한 토큰으로 접근
     */
    @GetMapping("/test-with-token")
    public ResponseEntity<Map<String, Object>> testWithToken() {
        logger.info("==================[JwtTestController.testWithToken] - 유효한 토큰으로 접근 테스트");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "유효한 토큰으로 접근 성공");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("note", "이 API는 토큰이 있어야 접근 가능합니다");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 보안 설정 정보 조회
     */
    @GetMapping("/security-info")
    public ResponseEntity<Map<String, Object>> getSecurityInfo() {
        logger.info("==================[JwtTestController.getSecurityInfo] - 보안 설정 정보 조회");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("security", Map.of(
            "jwtEnabled", true,
            "csrfDisabled", false,
            "csrfIgnoredPaths", new String[]{
                "/mbc/eplaton/**",
                "/eplaton/**",
                "/h2-console/**"
            },
            "permittedPaths", new String[]{
                "/mbc/eplaton/**",
                "/eplaton/**",
                "/static/**",
                "/css/**",
                "/js/**",
                "/images/**",
                "/h2-console/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/**",
                "/",
                "/home",
                "/mbc"
            },
            "protectedPaths", new String[]{
                "/mbc/protected/**",
                "/mbc/jwt-test/**"
            }
        ));
        
        return ResponseEntity.ok(response);
    }
} 