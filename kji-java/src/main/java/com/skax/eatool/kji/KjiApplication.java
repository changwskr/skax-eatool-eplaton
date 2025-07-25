package com.skax.eatool.kji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * KJI Framework Java Implementation
 * Spring Boot Application for KJI Framework
 * 
 * @author SKAX EA Tool Team
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.skax.eatool.kji",
    "com.skax.eatool.kji.controller",
    "com.skax.eatool.kji.service",
    "com.skax.eatool.kji.config",
    "com.skax.eatool.kji.tpm"
})
public class KjiApplication {

    /**
     * Main method to start the KJI application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(KjiApplication.class, args);
    }
} 