package com.example.apigatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.example.apigatewayservice.filter.CustomFilter;
import com.example.apigatewayservice.filter.LoggingFilter;
import com.example.apigatewayservice.filter.AuthorizationHeaderFilter;

/**
 * Spring Cloud Gateway Filter Configuration
 * 
 * 현재 application.yml에서 사용되는 라우팅과 필터 구성을 Java 코드로 구현한 버전입니다.
 * application.yml과 동일한 라우팅 규칙을 제공합니다.
 * 
 * @author SKAX
 * @version 1.0.0
 * @since 2024
 */
@Configuration
public class FilterConfig {

    @Autowired
    private CustomFilter customFilter;
    
    @Autowired
    private LoggingFilter loggingFilter;
    
    @Autowired
    private Environment environment;

    /**
     * Gateway Routes Configuration
     * application.yml의 라우팅 설정과 동일한 구성을 Java 코드로 구현
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        
        System.out.println("★★★★★★FilterConfig.gatewayRoutes()-호출★★★★★★★");
        
        // AuthorizationHeaderFilter 인스턴스 생성
        AuthorizationHeaderFilter authorizationHeaderFilter = new AuthorizationHeaderFilter(environment);
        
        return builder.routes()
                // ========================================
                // SKAX EA Tool 서비스 라우팅
                // ========================================
                
                // MBA (Master Business Application) 서비스
                .route(r -> r.path("/mba/**")
                        .filters(f -> f
                                .addRequestHeader("mba-request", "mba-request-header")
                                .addResponseHeader("mba-response", "mba-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config()))
                                .filter(loggingFilter.apply(new LoggingFilter.Config() {{
                                    setBaseMessage("MBA Service Request");
                                    setPreLogger(true);
                                    setPostLogger(true);
                                }})))
                        .uri("http://localhost:8084"))
                
                // KSA (Kesa Service Application) 서비스
                .route(r -> r.path("/ksa/**")
                        .filters(f -> f
                                .addRequestHeader("ksa-request", "ksa-request-header")
                                .addResponseHeader("ksa-response", "ksa-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8082"))
                
                // KJI (Kesa Java Interface) 서비스
                .route(r -> r.path("/kji/**")
                        .filters(f -> f
                                .addRequestHeader("kji-request", "kji-request-header")
                                .addResponseHeader("kji-response", "kji-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8083"))
                
                // MBC (Master Business Component) 서비스
                .route(r -> r.path("/mbc/**")
                        .filters(f -> f
                                .addRequestHeader("mbc-request", "mbc-request-header")
                                .addResponseHeader("mbc-response", "mbc-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8085"))
                
                // MBC01 서비스
                .route(r -> r.path("/mbc01/**")
                        .filters(f -> f
                                .addRequestHeader("mbc01-request", "mbc01-request-header")
                                .addResponseHeader("mbc01-response", "mbc01-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8085"))
                
                // ========================================
                // 기존 서비스 라우팅 (참고용)
                // ========================================
                
                // First Service
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f
                                .addRequestHeader("first-request", "first-request-header2")
                                .addResponseHeader("first-response", "first-response-header2")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8081"))
                
                // Second Service
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f
                                .addRequestHeader("second-request", "second-request-header2")
                                .addResponseHeader("second-response", "second-response-header2")
                                .filter(customFilter.apply(new CustomFilter.Config()))
                                .filter(loggingFilter.apply(new LoggingFilter.Config() {{
                                    setBaseMessage("Hi, there.");
                                    setPreLogger(true);
                                    setPostLogger(true);
                                }})))
                        .uri("http://localhost:8082"))
                
                // Third Service
                .route(r -> r.path("/third-service/**")
                        .filters(f -> f
                                .addRequestHeader("third-request", "third-request-header2")
                                .addResponseHeader("third-response", "third-response-header2")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8083"))
                
                // User Service - Login
                .route(r -> r.path("/user-service/login")
                        .and().method("POST")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8081"))
                
                // User Service - Users
                .route(r -> r.path("/user-service/users")
                        .and().method("POST")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8081"))
                
                // User Service - Actuator
                .route(r -> r.path("/user-service/actuator/**")
                        .and().method("GET", "POST")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8081"))
                
                // User Service - GET (with Authorization)
                .route(r -> r.path("/user-service/**")
                        .and().method("GET")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                                .filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config())))
                        .uri("http://localhost:8081"))
                
                // Order Service - Actuator
                .route(r -> r.path("/order-service/actuator/**")
                        .and().method("GET", "POST")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/order-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8082"))
                
                // Order Service
                .route(r -> r.path("/order-service/**")
                        .uri("http://localhost:8082"))
                
                // Catalog Service - Actuator
                .route(r -> r.path("/catalog-service/actuator/**")
                        .and().method("GET", "POST")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/catalog-service/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8083"))
                
                // Catalog Service
                .route(r -> r.path("/catalog-service/**")
                        .uri("http://localhost:8083"))
                
                .build();
    }
}
