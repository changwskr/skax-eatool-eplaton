package com.skax.eatool.mbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
 * 
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스 핸들러 설정
     * 
     * @param registry 리소스 핸들러 레지스트리
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 처리를 제한하여 컨트롤러가 우선적으로 처리되도록 설정
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // /mbc/ 경로는 컨트롤러가 처리하도록 정적 리소스 매핑 제거
        // registry.addResourceHandler("/mbc/**")
        // .addResourceLocations("classpath:/static/mbc/")
        // .setCachePeriod(0);
    }
}