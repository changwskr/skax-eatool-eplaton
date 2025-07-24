package com.skax.eatool.mbc.web.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swagger UI 컨트롤러
 * 
 * Gateway 환경에서 Swagger UI에 접근할 수 있도록 리다이렉트합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 * @since 2024
 */
@Controller
@RequestMapping("/swagger")
public class SwaggerController {
    
    private static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);
    
    /**
     * Swagger UI 메인 페이지 리다이렉트
     */
    @GetMapping("/ui")
    public String swaggerUi() {
        logger.info("=== SwaggerController.swaggerUi START ===");
        logger.info("Swagger UI 리다이렉트: /swagger-ui/index.html");
        logger.info("=== SwaggerController.swaggerUi END ===");
        return "redirect:/swagger-ui/index.html";
    }
    
    /**
     * Swagger UI 인덱스 페이지 리다이렉트
     */
    @GetMapping("/ui.html")
    public String swaggerUiHtml() {
        logger.info("=== SwaggerController.swaggerUiHtml START ===");
        logger.info("Swagger UI HTML 리다이렉트: /swagger-ui/index.html");
        logger.info("=== SwaggerController.swaggerUiHtml END ===");
        return "redirect:/swagger-ui/index.html";
    }
    
    /**
     * Swagger UI 루트 경로 리다이렉트
     */
    @GetMapping("")
    public String swaggerRoot() {
        logger.info("=== SwaggerController.swaggerRoot START ===");
        logger.info("Swagger 루트 리다이렉트: /swagger-ui/index.html");
        logger.info("=== SwaggerController.swaggerRoot END ===");
        return "redirect:/swagger-ui/index.html";
    }
} 