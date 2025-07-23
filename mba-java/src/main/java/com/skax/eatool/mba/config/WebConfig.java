package com.skax.eatool.mba.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MBA 웹 설정 클래스
 * 
 * 웹 관련 설정을 관리합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 설정
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 정적 리소스 핸들러 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 설정
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // Swagger UI 리소스 설정
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 뷰 컨트롤러 설정
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 기본 페이지 리다이렉트
        registry.addRedirectViewController("/", "/mba/home");
        
        // MBA 홈 페이지
        registry.addViewController("/mba/home").setViewName("home/index");
        
        // 로그인 페이지
        registry.addViewController("/mba/login").setViewName("auth/login");
        
        // 대시보드 페이지
        registry.addViewController("/mba/dashboard").setViewName("dashboard/index");
    }
} 