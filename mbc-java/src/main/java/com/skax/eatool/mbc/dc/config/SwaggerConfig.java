package com.skax.eatool.mbc.dc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Arrays;

/**
 * Swagger/OpenAPI 설정
 * 
 * 프로그램명: SwaggerConfig.java
 * 설명: Swagger UI 및 OpenAPI 문서화 설정
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - OpenAPI 3.0 스펙 기반 API 문서화
 * - Swagger UI 인터페이스 제공
 * - API 정보 및 서버 정보 설정
 * 
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

        /**
         * OpenAPI 설정
         * 
         * @return OpenAPI 설정 객체
         */
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("MBC Project API Documentation")
                                                .description("SKAX Project Eplaton - MBC 시스템 API 문서")
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                                .name("SKAX Project Team")
                                                                .email("skax@kbstar.com")
                                                                .url("https://www.kbstar.com"))
                                                .license(new License()
                                                                .name("KB Star License")
                                                                .url("https://www.kbstar.com/license")))
                                .servers(Arrays.asList(
                                                new Server()
                                                                .url("http://localhost:8080")
                                                                .description("개발 서버"),
                                                new Server()
                                                                .url("https://api.mbc.kbstar.com")
                                                                .description("운영 서버")));
        }
}