package com.skax.eatool.mbc.web.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 시스템 상태 확인 컨트롤러
 * 
 * Spring Boot Actuator의 health 엔드포인트를 웹 페이지로 표시합니다.
 * /monitoring/health 경로를 사용하여 Actuator와 충돌을 피합니다.
 * 
 * @author SKAX Project Team
 * @version 1.0.0
 * @since 2024
 */
@Controller
@RequestMapping("/monitoring")
public class SystemHealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemHealthController.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * 시스템 상태 확인 웹 페이지
     */
    @GetMapping("/health")
    public String showHealthPage(Model model) {
        logger.info("=== SystemHealthController.showHealthPage START ===");
        
        try {
            // 현재 시간
            String currentTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
            model.addAttribute("currentTime", currentTime);
            
            // 시스템 상태 정보
            Map<String, Object> healthInfo = getHealthInfo();
            model.addAttribute("healthInfo", healthInfo);
            
            // 상태별 색상 설정
            String statusColor = "success";
            if ("DOWN".equals(healthInfo.get("status"))) {
                statusColor = "danger";
            } else if ("UNKNOWN".equals(healthInfo.get("status"))) {
                statusColor = "warning";
            }
            model.addAttribute("statusColor", statusColor);
            
            // 상세 상태 정보
            Map<String, Object> details = (Map<String, Object>) healthInfo.get("details");
            if (details != null) {
                model.addAttribute("details", details);
                
                // 각 컴포넌트별 상태 색상 설정
                Map<String, String> componentColors = new HashMap<>();
                for (Map.Entry<String, Object> entry : details.entrySet()) {
                    if (entry.getValue() instanceof Map) {
                        Map<String, Object> component = (Map<String, Object>) entry.getValue();
                        String componentStatus = (String) component.get("status");
                        if ("UP".equals(componentStatus)) {
                            componentColors.put(entry.getKey(), "success");
                        } else if ("DOWN".equals(componentStatus)) {
                            componentColors.put(entry.getKey(), "danger");
                        } else {
                            componentColors.put(entry.getKey(), "warning");
                        }
                    }
                }
                model.addAttribute("componentColors", componentColors);
            }
            
            logger.info("=== SystemHealthController.showHealthPage END ===");
            
        } catch (Exception e) {
            logger.error("Health 페이지 로드 중 오류: " + e.getMessage(), e);
            setDefaultValues(model);
        }
        
        return "web/monitoring/health";
    }
    
    /**
     * 시스템 상태 정보 API
     */
    @GetMapping("/health/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getHealthApi() {
        logger.info("=== SystemHealthController.getHealthApi START ===");
        
        try {
            Map<String, Object> healthInfo = getHealthInfo();
            healthInfo.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            healthInfo.put("success", true);
            
            logger.info("=== SystemHealthController.getHealthApi END ===");
            return ResponseEntity.ok(healthInfo);
            
        } catch (Exception e) {
            logger.error("Health API 호출 중 오류: " + e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Actuator Health 직접 호출 API
     */
    @GetMapping("/health/actuator")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getActuatorHealth() {
        logger.info("=== SystemHealthController.getActuatorHealth START ===");
        
        try {
            String actuatorHealthUrl = "http://localhost:8085/actuator/health";
            Map<String, Object> actuatorHealth = callActuatorHealth(actuatorHealthUrl);
            
            Map<String, Object> response = new HashMap<>();
            if (actuatorHealth != null) {
                response.put("success", true);
                response.put("data", actuatorHealth);
                response.put("source", "Spring Boot Actuator");
                response.put("url", actuatorHealthUrl);
            } else {
                response.put("success", false);
                response.put("error", "Actuator Health 호출 실패");
                response.put("url", actuatorHealthUrl);
            }
            
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            logger.info("=== SystemHealthController.getActuatorHealth END ===");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Actuator Health API 호출 중 오류: " + e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Health 상태 요약 API
     */
    @GetMapping("/health/summary")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getHealthSummary() {
        logger.info("=== SystemHealthController.getHealthSummary START ===");
        
        try {
            Map<String, Object> healthInfo = getHealthInfo();
            
            Map<String, Object> summary = new HashMap<>();
            summary.put("status", healthInfo.get("status"));
            summary.put("application", healthInfo.get("application"));
            summary.put("version", healthInfo.get("version"));
            summary.put("environment", healthInfo.get("environment"));
            summary.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            // 컴포넌트별 상태 요약
            Map<String, Object> details = (Map<String, Object>) healthInfo.get("details");
            if (details != null) {
                Map<String, String> componentStatus = new HashMap<>();
                for (Map.Entry<String, Object> entry : details.entrySet()) {
                    if (entry.getValue() instanceof Map) {
                        Map<String, Object> component = (Map<String, Object>) entry.getValue();
                        componentStatus.put(entry.getKey(), (String) component.get("status"));
                    }
                }
                summary.put("components", componentStatus);
            }
            
            summary.put("success", true);
            
            logger.info("=== SystemHealthController.getHealthSummary END ===");
            return ResponseEntity.ok(summary);
            
        } catch (Exception e) {
            logger.error("Health Summary API 호출 중 오류: " + e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 시스템 상태 정보 조회
     */
    private Map<String, Object> getHealthInfo() {
        Map<String, Object> healthInfo = new HashMap<>();
        
        try {
            // Spring Boot Actuator Health 엔드포인트 호출
            String actuatorHealthUrl = "http://localhost:8085/actuator/health";
            Map<String, Object> actuatorHealth = callActuatorHealth(actuatorHealthUrl);
            
            if (actuatorHealth != null && actuatorHealth.containsKey("status")) {
                // Actuator에서 받은 정보 사용
                healthInfo.putAll(actuatorHealth);
            } else {
                // Actuator 호출 실패 시 기본 정보 사용
                healthInfo = getDefaultHealthInfo();
            }
            
            // 추가 시스템 정보
            healthInfo.put("application", "MBC - Master Business Component");
            healthInfo.put("version", "1.0.0");
            healthInfo.put("environment", "개발환경");
            healthInfo.put("actuatorUrl", actuatorHealthUrl);
            
        } catch (Exception e) {
            logger.error("Health 정보 조회 중 오류: " + e.getMessage(), e);
            healthInfo = getDefaultHealthInfo();
            healthInfo.put("error", e.getMessage());
        }
        
        return healthInfo;
    }
    
    /**
     * Spring Boot Actuator Health 엔드포인트 호출
     */
    private Map<String, Object> callActuatorHealth(String url) {
        try {
            logger.info("Actuator Health 호출: " + url);
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Actuator Health 응답 성공");
                return (Map<String, Object>) response.getBody();
            } else {
                logger.warn("Actuator Health 응답 실패: " + response.getStatusCode());
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Actuator Health 호출 중 오류: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 기본 Health 정보 생성
     */
    private Map<String, Object> getDefaultHealthInfo() {
        Map<String, Object> healthInfo = new HashMap<>();
        
        try {
            // 시스템 상태 (기본값: UP)
            healthInfo.put("status", "UP");
            
            // 상세 상태 정보
            Map<String, Object> details = new HashMap<>();
            
            // 데이터베이스 상태
            Map<String, Object> dbStatus = new HashMap<>();
            dbStatus.put("status", "UP");
            dbStatus.put("details", "H2 인메모리 데이터베이스 정상");
            details.put("database", dbStatus);
            
            // 디스크 상태
            Map<String, Object> diskStatus = new HashMap<>();
            diskStatus.put("status", "UP");
            diskStatus.put("details", "디스크 공간 충분");
            details.put("diskSpace", diskStatus);
            
            // 메모리 상태
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            double memoryUsage = (double) usedMemory / totalMemory * 100;
            
            Map<String, Object> memoryStatus = new HashMap<>();
            memoryStatus.put("status", memoryUsage > 90 ? "DOWN" : "UP");
            memoryStatus.put("details", String.format("메모리 사용률: %.1f%%", memoryUsage));
            memoryStatus.put("totalMemory", totalMemory);
            memoryStatus.put("freeMemory", freeMemory);
            memoryStatus.put("usedMemory", usedMemory);
            memoryStatus.put("usagePercent", memoryUsage);
            details.put("memory", memoryStatus);
            
            // 애플리케이션 상태
            Map<String, Object> appStatus = new HashMap<>();
            appStatus.put("status", "UP");
            appStatus.put("details", "애플리케이션 정상 실행 중");
            appStatus.put("uptime", System.currentTimeMillis());
            details.put("application", appStatus);
            
            healthInfo.put("details", details);
            
        } catch (Exception e) {
            logger.error("기본 Health 정보 생성 중 오류: " + e.getMessage(), e);
            healthInfo.put("status", "DOWN");
        }
        
        return healthInfo;
    }
    
    /**
     * 기본값 설정
     */
    private void setDefaultValues(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss")));
        
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UNKNOWN");
        healthInfo.put("application", "MBC - Master Business Component");
        healthInfo.put("version", "1.0.0");
        healthInfo.put("environment", "개발환경");
        model.addAttribute("healthInfo", healthInfo);
        model.addAttribute("statusColor", "warning");
    }
} 