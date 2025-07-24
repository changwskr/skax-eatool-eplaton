package com.skax.eatool.mbc.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Cloud API Gateway Service Application
 * 
 * SKAX EA Tool의 API Gateway 서비스입니다.
 * 모든 마이크로서비스의 진입점 역할을 합니다.
 * 
 * @author SKAX
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication
public class ApigatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayServiceApplication.class, args);
    }

    /**
     * HTTP 트레이스 리포지토리 빈
     * API Gateway를 통한 모든 요청/응답을 추적할 수 있습니다.
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
} 