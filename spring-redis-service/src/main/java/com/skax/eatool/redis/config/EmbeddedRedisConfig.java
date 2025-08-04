package com.skax.eatool.redis.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;

/**
 * Embedded Redis 설정
 * 
 * 개발 환경에서 Redis 서버를 자동으로 기동/중지하는 설정
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
@Configuration
@Profile({"local", "dev", "test"})  // 운영환경은 제외
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    /**
     * 애플리케이션 시작 시 Redis 서버 기동
     */
    @PostConstruct
    public void startRedis() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
        System.out.println("✅ Embedded Redis 서버가 시작되었습니다. (포트: 6379)");
    }

    /**
     * 애플리케이션 종료 시 Redis 서버 중지
     */
    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            System.out.println("🛑 Embedded Redis 서버가 중지되었습니다.");
        }
    }
} 