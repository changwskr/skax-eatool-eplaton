package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MBC 웹 설정 클래스
 * 
 * Gateway 환경에서 MBC 서비스의 웹 관련 설정을 담당합니다.
 * 
 * 주요 기능:
 * - 정적 리소스 처리 설정 (Gateway 경로 고려)
 * - CORS 설정 (API Gateway와의 통신)
 * - 인터셉터 설정 (필요시)
 * 
 * Gateway 환경 특성:
 * - 모든 요청은 /mbc/ 경로로 시작
 * - 정적 리소스는 Gateway를 통해 접근
 * - 컨트롤러가 직접 요청 처리
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 * @since 2024
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

            // Swagger UI 리소스 (OpenAPI 3.0)
            registry.addResourceHandler("/swagger-ui/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                    .setCachePeriod(0);
            
            // Swagger UI 추가 리소스
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/")
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
            // 전체 CORS 설정
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            
            // EPlaton API 전용 CORS 설정
            registry.addMapping("/mbc/eplaton/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
            
            logger.info("CORS 설정 완료 - EPlaton API 포함");
            
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

    /**
     * RestTemplate Bean
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}