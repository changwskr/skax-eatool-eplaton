package com.skax.eatool.mba.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MBA Swagger/OpenAPI 설정 클래스
 * 
 * API 문서화를 위한 Swagger 설정을 관리합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 설정 빈
     */
    @Bean
    public OpenAPI mbaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MBA API Documentation")
                        .description("Master Business Application API Documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("KBSTAR")
                                .email("kbstar@skax.com")
                                .url("https://www.skax.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081/mba")
                                .description("MBA Development Server"),
                        new Server()
                                .url("https://mba.skax.com")
                                .description("MBA Production Server")
                ));
    }
} 