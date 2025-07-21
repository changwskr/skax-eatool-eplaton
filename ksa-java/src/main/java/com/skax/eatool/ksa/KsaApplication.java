package com.skax.eatool.ksa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * KSA Framework Java Implementation
 * Spring Boot Application for KSA Framework
 * 
 * @author SKAX EA Tool Team
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.skax.eatool.ksa",
    "com.skax.eatool.ksa.controller",
    "com.skax.eatool.ksa.service",
    "com.skax.eatool.ksa.config"
})
public class KsaApplication {

    /**
     * Main method to start the KSA application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(KsaApplication.class, args);
    }
} 