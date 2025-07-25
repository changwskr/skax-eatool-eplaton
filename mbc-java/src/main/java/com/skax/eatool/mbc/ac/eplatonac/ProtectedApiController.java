package com.skax.eatool.mbc.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 보호된 API 테스트 컨트롤러
 * 
 * JWT 토큰 기반 인증이 필요한 API를 테스트합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/mbc/protected")
public class ProtectedApiController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProtectedApiController.class);
    
    /**
     * 보호된 API - 인증 필요
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        logger.info("==================[ProtectedApiController.getUserInfo] - 보호된 API 호출");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "보호된 API 접근 성공");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("user", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        
        logger.info("사용자 정보 조회 성공: {}", authentication.getName());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 보호된 API - POST 요청
     */
    @PostMapping("/secure-data")
    public ResponseEntity<Map<String, Object>> postSecureData(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[ProtectedApiController.postSecureData] - 보호된 POST API 호출");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "보호된 POST API 접근 성공");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("user", authentication.getName());
        response.put("requestData", requestBody);
        response.put("authenticated", authentication.isAuthenticated());
        
        logger.info("보안 데이터 처리 성공: {}", authentication.getName());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 관리자 전용 API - ROLE_ADMIN 권한 필요
     */
    @GetMapping("/admin-only")
    public ResponseEntity<Map<String, Object>> getAdminInfo() {
        logger.info("==================[ProtectedApiController.getAdminInfo] - 관리자 전용 API 호출");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "관리자 전용 API 접근 성공");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("user", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        
        logger.info("관리자 정보 조회 성공: {}", authentication.getName());
        
        return ResponseEntity.ok(response);
    }
} 