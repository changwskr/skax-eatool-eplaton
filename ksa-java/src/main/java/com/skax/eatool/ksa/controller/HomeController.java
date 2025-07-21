package com.skax.eatool.ksa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * KSA Home Controller
 * Provides basic endpoints for KSA application
 * 
 * @author SKAX EA Tool Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    /**
     * Health check endpoint
     * 
     * @return health status
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "KSA Framework");
        response.put("version", "1.0.0");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    /**
     * Welcome endpoint
     * 
     * @return welcome message
     */
    @GetMapping("/welcome")
    public Map<String, String> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to KSA Framework!");
        response.put("description", "KSA Framework Java Implementation");
        return response;
    }

    /**
     * Root endpoint
     * 
     * @return application info
     */
    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "KSA Framework");
        response.put("version", "1.0.0");
        response.put("endpoints", new String[]{
            "/api/health",
            "/api/welcome",
            "/actuator/health",
            "/h2-console"
        });
        return response;
    }
} 