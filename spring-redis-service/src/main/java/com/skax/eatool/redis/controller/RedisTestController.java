package com.skax.eatool.redis.controller;

import com.skax.eatool.redis.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 테스트용 REST API 컨트롤러
 * 
 * Redis 기능을 테스트할 수 있는 REST API 제공
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
@Slf4j
public class RedisTestController {

    private final RedisCacheService redisCacheService;

    /**
     * Redis에 키-값 저장
     * 
     * @param key 키
     * @param value 값
     * @return 저장 결과
     */
    @PostMapping("/put")
    public ResponseEntity<Map<String, Object>> put(
            @RequestParam String key, 
            @RequestParam String value) {
        
        log.info("Redis 저장 요청. Key: {}, Value: {}", key, value);
        
        try {
            redisCacheService.putValue(key, value);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "값이 성공적으로 저장되었습니다.");
            response.put("key", key);
            response.put("value", value);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis 저장 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "저장 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Redis에서 값 조회
     * 
     * @param key 키
     * @return 조회된 값
     */
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> get(@RequestParam String key) {
        
        log.info("Redis 조회 요청. Key: {}", key);
        
        try {
            String value = redisCacheService.getValue(key);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("key", key);
            response.put("value", value);
            response.put("exists", value != null);
            
            if (value != null) {
                response.put("message", "값이 성공적으로 조회되었습니다.");
            } else {
                response.put("message", "키를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis 조회 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "조회 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Redis에서 키 삭제
     * 
     * @param key 키
     * @return 삭제 결과
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestParam String key) {
        
        log.info("Redis 삭제 요청. Key: {}", key);
        
        try {
            boolean deleted = redisCacheService.delete(key);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("key", key);
            response.put("deleted", deleted);
            
            if (deleted) {
                response.put("message", "키가 성공적으로 삭제되었습니다.");
            } else {
                response.put("message", "삭제할 키를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis 삭제 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "삭제 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 키 존재 여부 확인
     * 
     * @param key 키
     * @return 존재 여부
     */
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Object>> exists(@RequestParam String key) {
        
        log.info("Redis 키 존재 확인 요청. Key: {}", key);
        
        try {
            boolean exists = redisCacheService.exists(key);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("key", key);
            response.put("exists", exists);
            response.put("message", exists ? "키가 존재합니다." : "키가 존재하지 않습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis 키 존재 확인 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "확인 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 키의 TTL 조회
     * 
     * @param key 키
     * @return TTL 정보
     */
    @GetMapping("/ttl")
    public ResponseEntity<Map<String, Object>> getTtl(@RequestParam String key) {
        
        log.info("Redis TTL 조회 요청. Key: {}", key);
        
        try {
            long ttl = redisCacheService.getTtl(key);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("key", key);
            response.put("ttl", ttl);
            
            if (ttl == -2) {
                response.put("message", "키가 존재하지 않습니다.");
            } else if (ttl == -1) {
                response.put("message", "키에 만료 시간이 설정되지 않았습니다.");
            } else {
                response.put("message", "키의 만료 시간: " + ttl + "초");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis TTL 조회 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "TTL 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 키의 TTL 설정
     * 
     * @param key 키
     * @param ttl 만료 시간 (초)
     * @return 설정 결과
     */
    @PostMapping("/ttl")
    public ResponseEntity<Map<String, Object>> setTtl(
            @RequestParam String key, 
            @RequestParam long ttl) {
        
        log.info("Redis TTL 설정 요청. Key: {}, TTL: {}초", key, ttl);
        
        try {
            boolean result = redisCacheService.setTtl(key, ttl);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("key", key);
            response.put("ttl", ttl);
            response.put("set", result);
            
            if (result) {
                response.put("message", "TTL이 성공적으로 설정되었습니다.");
            } else {
                response.put("message", "TTL 설정에 실패했습니다. 키가 존재하지 않을 수 있습니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis TTL 설정 중 오류 발생. Key: {}, TTL: {}, Error: {}", key, ttl, e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "TTL 설정 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Redis 상태 확인
     * 
     * @return Redis 상태 정보
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        
        log.info("Redis 상태 확인 요청");
        
        try {
            // 간단한 테스트 키로 Redis 연결 확인
            String testKey = "health_check_" + System.currentTimeMillis();
            redisCacheService.putValue(testKey, "OK", Duration.ofSeconds(10));
            String testValue = redisCacheService.getValue(testKey);
            redisCacheService.delete(testKey);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("status", "OK");
            response.put("message", "Redis 서버가 정상적으로 작동하고 있습니다.");
            response.put("testResult", "OK".equals(testValue));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Redis 상태 확인 중 오류 발생. Error: {}", e.getMessage(), e);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("status", "ERROR");
            response.put("message", "Redis 서버 연결에 문제가 있습니다: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 