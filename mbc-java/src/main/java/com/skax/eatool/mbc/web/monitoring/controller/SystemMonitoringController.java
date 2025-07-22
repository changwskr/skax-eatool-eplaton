package com.skax.eatool.mbc.web.monitoring.controller;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 시스템 모니터링 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/mbc/monitoring")
@Tag(name = "System Monitoring", description = "시스템 모니터링 API")
public class SystemMonitoringController {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemMonitoringController.class);
    private final NewIKesaLogger kesaLogger = NewKesaLogHelper.getBiz();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 시스템 모니터링 대시보드
     */
    @GetMapping
    public String showMonitoringDashboard(Model model) {
        logger.info("=== SystemMonitoringController.showMonitoringDashboard START ===");
        kesaLogger.info("시스템 모니터링 대시보드 요청", "SystemMonitoringController");

        try {
            // 시스템 정보
            Map<String, Object> systemInfo = getSystemInfo();
            model.addAttribute("systemInfo", systemInfo);

            // 메모리 정보
            Map<String, Object> memoryInfo = getMemoryInfo();
            model.addAttribute("memoryInfo", memoryInfo);

            // 데이터베이스 정보
            Map<String, Object> databaseInfo = getDatabaseInfo();
            model.addAttribute("databaseInfo", databaseInfo);

            // 애플리케이션 정보
            Map<String, Object> applicationInfo = getApplicationInfo();
            model.addAttribute("applicationInfo", applicationInfo);

            // 현재 시간
            String currentTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
            model.addAttribute("currentTime", currentTime);

            kesaLogger.info("시스템 모니터링 대시보드 데이터 로드 완료", "SystemMonitoringController");

        } catch (Exception e) {
            kesaLogger.error("시스템 모니터링 대시보드 로드 중 오류: " + e.getMessage(), "SystemMonitoringController");
            setDefaultValues(model);
        }

        logger.info("=== SystemMonitoringController.showMonitoringDashboard END ===");
        return "web/monitoring/dashboard";
    }

    /**
     * 시스템 정보 API
     */
    @GetMapping("/api/system")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSystemInfoApi() {
        logger.info("=== SystemMonitoringController.getSystemInfoApi START ===");
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("systemInfo", getSystemInfo());
            response.put("memoryInfo", getMemoryInfo());
            response.put("databaseInfo", getDatabaseInfo());
            response.put("applicationInfo", getApplicationInfo());
            response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            response.put("success", true);
            
        } catch (Exception e) {
            logger.error("시스템 정보 API 호출 중 오류: " + e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        logger.info("=== SystemMonitoringController.getSystemInfoApi END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 정보 조회
     */
    private Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        systemInfo.put("osArch", System.getProperty("os.arch"));
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("javaVendor", System.getProperty("java.vendor"));
        systemInfo.put("userName", System.getProperty("user.name"));
        systemInfo.put("userHome", System.getProperty("user.home"));
        systemInfo.put("availableProcessors", osBean.getAvailableProcessors());
        systemInfo.put("systemLoadAverage", formatLoadAverage(osBean.getSystemLoadAverage()));
        
        return systemInfo;
    }

    /**
     * 메모리 정보 조회
     */
    private Map<String, Object> getMemoryInfo() {
        Map<String, Object> memoryInfo = new HashMap<>();
        
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        Runtime runtime = Runtime.getRuntime();
        
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        memoryInfo.put("totalMemory", formatBytes(totalMemory));
        memoryInfo.put("freeMemory", formatBytes(freeMemory));
        memoryInfo.put("usedMemory", formatBytes(usedMemory));
        memoryInfo.put("maxMemory", formatBytes(maxMemory));
        memoryInfo.put("memoryUsagePercent", formatPercent((double) usedMemory / totalMemory * 100));
        memoryInfo.put("heapMemoryUsage", formatBytes(memoryBean.getHeapMemoryUsage().getUsed()));
        memoryInfo.put("nonHeapMemoryUsage", formatBytes(memoryBean.getNonHeapMemoryUsage().getUsed()));
        
        return memoryInfo;
    }

    /**
     * 데이터베이스 정보 조회
     */
    private Map<String, Object> getDatabaseInfo() {
        Map<String, Object> databaseInfo = new HashMap<>();
        
        try {
            // 데이터베이스 연결 상태 확인
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            databaseInfo.put("status", "연결됨");
            databaseInfo.put("statusColor", "success");
            
            // ACCOUNT 테이블 정보
            try {
                int accountCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);
                databaseInfo.put("accountCount", accountCount);
            } catch (Exception e) {
                databaseInfo.put("accountCount", 0);
            }
            
            // 데이터베이스 타입
            databaseInfo.put("type", "H2 In-Memory");
            databaseInfo.put("url", "jdbc:h2:mem:mbcdb_dev");
            
        } catch (Exception e) {
            databaseInfo.put("status", "연결 오류");
            databaseInfo.put("statusColor", "danger");
            databaseInfo.put("accountCount", 0);
            databaseInfo.put("type", "Unknown");
            databaseInfo.put("url", "N/A");
        }
        
        return databaseInfo;
    }

    /**
     * 애플리케이션 정보 조회
     */
    private Map<String, Object> getApplicationInfo() {
        Map<String, Object> applicationInfo = new HashMap<>();
        
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        
        applicationInfo.put("applicationName", "SKAX-EATOOL MBC");
        applicationInfo.put("version", "2.0.0");
        applicationInfo.put("environment", "Development");
        applicationInfo.put("uptime", formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()));
        applicationInfo.put("threadCount", threadBean.getThreadCount());
        applicationInfo.put("peakThreadCount", threadBean.getPeakThreadCount());
        applicationInfo.put("daemonThreadCount", threadBean.getDaemonThreadCount());
        applicationInfo.put("startTime", LocalDateTime.now().minusNanos(ManagementFactory.getRuntimeMXBean().getUptime() * 1000000)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return applicationInfo;
    }

    /**
     * 기본값 설정
     */
    private void setDefaultValues(Model model) {
        Map<String, Object> defaultSystemInfo = new HashMap<>();
        defaultSystemInfo.put("osName", "Unknown");
        defaultSystemInfo.put("javaVersion", "Unknown");
        model.addAttribute("systemInfo", defaultSystemInfo);
        
        Map<String, Object> defaultMemoryInfo = new HashMap<>();
        defaultMemoryInfo.put("totalMemory", "0 MB");
        defaultMemoryInfo.put("usedMemory", "0 MB");
        model.addAttribute("memoryInfo", defaultMemoryInfo);
        
        Map<String, Object> defaultDatabaseInfo = new HashMap<>();
        defaultDatabaseInfo.put("status", "확인 불가");
        defaultDatabaseInfo.put("statusColor", "warning");
        model.addAttribute("databaseInfo", defaultDatabaseInfo);
        
        Map<String, Object> defaultApplicationInfo = new HashMap<>();
        defaultApplicationInfo.put("applicationName", "SKAX-EATOOL MBC");
        defaultApplicationInfo.put("version", "2.0.0");
        model.addAttribute("applicationInfo", defaultApplicationInfo);
    }

    /**
     * 바이트 단위 포맷팅
     */
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * 퍼센트 포맷팅
     */
    private String formatPercent(double percent) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(percent) + "%";
    }

    /**
     * 로드 평균 포맷팅
     */
    private String formatLoadAverage(double loadAverage) {
        if (loadAverage < 0) return "N/A";
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(loadAverage);
    }

    /**
     * 업타임 포맷팅
     */
    private String formatUptime(long uptime) {
        long days = uptime / (24 * 60 * 60 * 1000);
        long hours = (uptime % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutes = (uptime % (60 * 60 * 1000)) / (60 * 1000);
        long seconds = (uptime % (60 * 1000)) / 1000;
        
        if (days > 0) {
            return days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";
        } else if (hours > 0) {
            return hours + "시간 " + minutes + "분 " + seconds + "초";
        } else if (minutes > 0) {
            return minutes + "분 " + seconds + "초";
        } else {
            return seconds + "초";
        }
    }
} 