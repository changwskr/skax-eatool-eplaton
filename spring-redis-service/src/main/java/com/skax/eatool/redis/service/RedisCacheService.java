package com.skax.eatool.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Redis 캐시 서비스
 * 
 * Redis 연동 비즈니스 로직을 처리하는 서비스
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 키-값을 Redis에 저장 (기본 TTL: 10분)
     * 
     * @param key 키
     * @param value 값
     */
    public void putValue(String key, String value) {
        putValue(key, value, Duration.ofMinutes(10));
    }

    /**
     * 키-값을 Redis에 저장 (사용자 정의 TTL)
     * 
     * @param key 키
     * @param value 값
     * @param ttl 만료 시간
     */
    public void putValue(String key, String value, Duration ttl) {
        try {
            redisTemplate.opsForValue().set(key, value, ttl);
            log.info("Redis에 값이 저장되었습니다. Key: {}, TTL: {}초", key, ttl.getSeconds());
        } catch (Exception e) {
            log.error("Redis 저장 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            throw new RuntimeException("Redis 저장 실패", e);
        }
    }

    /**
     * Redis에서 값을 조회
     * 
     * @param key 키
     * @return 값 (없으면 null)
     */
    public String getValue(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                log.info("Redis에서 값이 조회되었습니다. Key: {}", key);
            } else {
                log.warn("Redis에서 키를 찾을 수 없습니다. Key: {}", key);
            }
            return value;
        } catch (Exception e) {
            log.error("Redis 조회 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            throw new RuntimeException("Redis 조회 실패", e);
        }
    }

    /**
     * 키가 Redis에 존재하는지 확인
     * 
     * @param key 키
     * @return 존재 여부
     */
    public boolean exists(String key) {
        try {
            Boolean exists = redisTemplate.hasKey(key);
            log.info("Redis 키 존재 확인. Key: {}, Exists: {}", key, exists);
            return exists != null && exists;
        } catch (Exception e) {
            log.error("Redis 키 존재 확인 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Redis에서 키 삭제
     * 
     * @param key 키
     * @return 삭제 성공 여부
     */
    public boolean delete(String key) {
        try {
            Boolean deleted = redisTemplate.delete(key);
            log.info("Redis에서 키가 삭제되었습니다. Key: {}, Deleted: {}", key, deleted);
            return deleted != null && deleted;
        } catch (Exception e) {
            log.error("Redis 키 삭제 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 키의 TTL(Time To Live) 조회
     * 
     * @param key 키
     * @return TTL (초, -1: 만료 없음, -2: 키 없음)
     */
    public long getTtl(String key) {
        try {
            Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            log.info("Redis 키 TTL 조회. Key: {}, TTL: {}초", key, ttl);
            return ttl != null ? ttl : -2;
        } catch (Exception e) {
            log.error("Redis TTL 조회 중 오류 발생. Key: {}, Error: {}", key, e.getMessage(), e);
            return -2;
        }
    }

    /**
     * 키의 TTL 설정
     * 
     * @param key 키
     * @param ttl 만료 시간 (초)
     * @return 설정 성공 여부
     */
    public boolean setTtl(String key, long ttl) {
        try {
            Boolean result = redisTemplate.expire(key, Duration.ofSeconds(ttl));
            log.info("Redis 키 TTL 설정. Key: {}, TTL: {}초, Result: {}", key, ttl, result);
            return result != null && result;
        } catch (Exception e) {
            log.error("Redis TTL 설정 중 오류 발생. Key: {}, TTL: {}, Error: {}", key, ttl, e.getMessage(), e);
            return false;
        }
    }
} 