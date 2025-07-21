package com.skax.eatool.mbc.web.testas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 테스트 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        logger.info("=== TestController.test START ===");
        logger.info("=== TestController.test END ===");
        return "Test endpoint is working!";
    }
} 