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
        
        // MBC 연동을 위한 특별 CORS 설정
        registry.addMapping("/mba/eplaton/mbc-call/**")
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
     * 
     * Gateway 환경에서는 API Gateway가 라우팅을 담당하므로
     * 각 서비스의 컨트롤러가 직접 요청을 처리합니다.
     * 따라서 뷰 컨트롤러 설정은 필요하지 않습니다.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Gateway 환경에서는 컨트롤러가 직접 처리하므로 뷰 컨트롤러 설정 제거
        // 예: /mba/home -> HomeController가 직접 처리
        // 예: /mba/auth/login -> AuthController가 직접 처리
    }
} 