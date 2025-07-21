package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 웹 설정 클래스
 * 
 * 프로그램명: WebConfig.java
 * 설명: 웹 관련 설정을 담당하는 설정 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 정적 리소스 처리 설정
 * - 컨트롤러 우선 처리 설정
 * - CORS 설정
 * - 인터셉터 설정
 * 
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * 정적 리소스 핸들러 설정
     * 
     * @param registry 리소스 핸들러 레지스트리
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("=== WebConfig.addResourceHandlers START ===");
        
        try {
            // 기본 정적 리소스 처리
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/")
                    .setCachePeriod(3600); // 1시간 캐시

            // CSS, JS, 이미지 파일 처리
            registry.addResourceHandler("/css/**")
                    .addResourceLocations("classpath:/static/css/")
                    .setCachePeriod(3600);

            registry.addResourceHandler("/js/**")
                    .addResourceLocations("classpath:/static/js/")
                    .setCachePeriod(3600);

            registry.addResourceHandler("/images/**")
                    .addResourceLocations("classpath:/static/images/")
                    .setCachePeriod(3600);

            // Swagger UI 리소스
            registry.addResourceHandler("/swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                    .setCachePeriod(0);

            // H2 Console 리소스
            registry.addResourceHandler("/h2-console/**")
                    .addResourceLocations("classpath:/META-INF/resources/h2-console/")
                    .setCachePeriod(0);

            // /mbc/ 경로는 컨트롤러가 처리하도록 정적 리소스 매핑 제거
            // registry.addResourceHandler("/mbc/**")
            // .addResourceLocations("classpath:/static/mbc/")
            // .setCachePeriod(0);
            
            logger.info("정적 리소스 핸들러 설정 완료");
            
        } catch (Exception e) {
            logger.error("정적 리소스 핸들러 설정 중 오류 발생: " + e.getMessage(), e);
        }
        
        logger.info("=== WebConfig.addResourceHandlers END ===");
    }

    /**
     * CORS 설정
     * 
     * @param registry CORS 레지스트리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("=== WebConfig.addCorsMappings START ===");
        
        try {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            
            logger.info("CORS 설정 완료");
            
        } catch (Exception e) {
            logger.error("CORS 설정 중 오류 발생: " + e.getMessage(), e);
        }
        
        logger.info("=== WebConfig.addCorsMappings END ===");
    }

    /**
     * 인터셉터 설정 (필요시 사용)
     * 
     * @param registry 인터셉터 레지스트리
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("=== WebConfig.addInterceptors START ===");
        
        try {
            // 로깅 인터셉터 추가 (필요시)
            // registry.addInterceptor(new LoggingInterceptor())
            //     .addPathPatterns("/**")
            //     .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**");
            
            logger.info("인터셉터 설정 완료");
            
        } catch (Exception e) {
            logger.error("인터셉터 설정 중 오류 발생: " + e.getMessage(), e);
        }
        
        logger.info("=== WebConfig.addInterceptors END ===");
    }
}