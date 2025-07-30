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
 * Spring Cloud Gateway Filter Configuration.
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

    /** Custom Filter 인스턴스 */
    @Autowired
    private CustomFilter customFilter;

    /** Logging Filter 인스턴스 */
    @Autowired
    private LoggingFilter loggingFilter;

    /** Environment 인스턴스 */
    @Autowired
    private Environment environment;

    /**
     * Gateway Routes Configuration.
     * application.yml의 라우팅 설정과 동일한 구성을 Java 코드로 구현
     *
     * @param builder RouteLocatorBuilder 인스턴스
     * @return RouteLocator 설정된 라우터
     */
    @Bean
    public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder) {

        System.out.println("★★★★★★FilterConfig.gatewayRoutes()-호출★★★★★★★");

        // AuthorizationHeaderFilter 인스턴스 생성
        AuthorizationHeaderFilter authorizationHeaderFilter = 
            new AuthorizationHeaderFilter(environment);

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

                // User Service
                .route(r -> r.path("/user/**")
                        .filters(f -> f
                                .addRequestHeader("user-request", "user-request-header")
                                .addResponseHeader("user-response", "user-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8081"))

                // Account Service
                .route(r -> r.path("/account/**")
                        .filters(f -> f
                                .addRequestHeader("account-request", "account-request-header")
                                .addResponseHeader("account-response", "account-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8081"))

                // Report Service
                .route(r -> r.path("/report/**")
                        .filters(f -> f
                                .addRequestHeader("report-request", "report-request-header")
                                .addResponseHeader("report-response", "report-response-header")
                                .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8081"))

                // ========================================
                // 공통 서비스 라우팅
                // ========================================

                // Health Check
                .route(r -> r.path("/health/**")
                        .filters(f -> f
                                .addRequestHeader("health-request", "health-request-header")
                                .addResponseHeader("health-response", "health-response-header"))
                        .uri("http://localhost:8081"))

                // Actuator
                .route(r -> r.path("/actuator/**")
                        .filters(f -> f
                                .addRequestHeader("actuator-request", "actuator-request-header")
                                .addResponseHeader("actuator-response", "actuator-response-header"))
                        .uri("http://localhost:8081"))

                // Swagger UI
                .route(r -> r.path("/swagger-ui/**")
                        .filters(f -> f
                                .addRequestHeader("swagger-request", "swagger-request-header")
                                .addResponseHeader("swagger-response", "swagger-response-header"))
                        .uri("http://localhost:8081"))

                // API Documentation
                .route(r -> r.path("/api-docs/**")
                        .filters(f -> f
                                .addRequestHeader("api-docs-request", "api-docs-request-header")
                                .addResponseHeader("api-docs-response", "api-docs-response-header"))
                        .uri("http://localhost:8081"))

                .build();
    }
}
