package com.skax.eatool.kji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KJI Web Configuration
 * 
 * KJI 서비스의 웹 관련 설정을 관리합니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * 정적 리소스 핸들러 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("==================[KJI WebConfig.addResourceHandlers START]");
        
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
                    .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                    .setCachePeriod(0);
            
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/")
                    .setCachePeriod(0);

            // H2 Console 리소스
            registry.addResourceHandler("/h2-console/**")
                    .addResourceLocations("classpath:/META-INF/resources/h2-console/")
                    .setCachePeriod(0);
            
            logger.info("==================[KJI WebConfig.addResourceHandlers END] - 정적 리소스 핸들러 설정 완료");
            
        } catch (Exception e) {
            logger.error("==================[KJI WebConfig.addResourceHandlers ERROR] - {}", e.getMessage(), e);
        }
    }

    /**
     * CORS 설정
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("==================[KJI WebConfig.addCorsMappings START]");
        
        try {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            
            logger.info("==================[KJI WebConfig.addCorsMappings END] - CORS 설정 완료");
            
        } catch (Exception e) {
            logger.error("==================[KJI WebConfig.addCorsMappings ERROR] - {}", e.getMessage(), e);
        }
    }

    /**
     * RestTemplate Bean
     */
    @Bean
    public RestTemplate restTemplate() {
        logger.info("==================[KJI WebConfig.restTemplate] - RestTemplate Bean 생성");
        return new RestTemplate();
    }
} 