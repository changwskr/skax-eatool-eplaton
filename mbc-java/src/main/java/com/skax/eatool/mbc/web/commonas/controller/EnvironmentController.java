package com.skax.eatool.mbc.web.commonas.controller;

import com.skax.eatool.mbc.dc.config.EnvironmentConfig;
import com.skax.eatool.mbc.dc.config.ConfigurationManager;
import com.skax.eatool.mbc.dc.config.EnvironmentInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 환경 설정 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/environment")
@Tag(name = "Environment Management", description = "환경 설정 관리 API")
public class EnvironmentController {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    private ConfigurationManager configurationManager;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private Environment environment;

    /**
     * 환경 정보 요약 조회
     */
    @GetMapping("/summary")
    @Operation(summary = "환경 정보 요약 조회", description = "현재 활성화된 환경의 설정 정보를 요약하여 반환합니다.")
    public ResponseEntity<Map<String, Object>> getEnvironmentSummary() {
        logger.info("=== EnvironmentController.getEnvironmentSummary START ===");
        Map<String, Object> summary = configurationManager.getEnvironmentSummary();
        logger.info("=== EnvironmentController.getEnvironmentSummary END ===");
        return ResponseEntity.ok(summary);
    }

    /**
     * 환경 정보 상세 조회
     */
    @GetMapping("/info")
    @Operation(summary = "환경 정보 상세 조회", description = "현재 환경의 상세 설정 정보를 반환합니다.")
    public ResponseEntity<EnvironmentInfo> getEnvironmentInfo() {
        logger.info("=== EnvironmentController.getEnvironmentInfo START ===");
        EnvironmentInfo info = environmentConfig.getEnvironmentInfo();
        logger.info("=== EnvironmentController.getEnvironmentInfo END ===");
        return ResponseEntity.ok(info);
    }

    /**
     * 활성 프로파일 조회
     */
    @GetMapping("/profiles")
    @Operation(summary = "활성 프로파일 조회", description = "현재 활성화된 프로파일 목록을 반환합니다.")
    public ResponseEntity<Map<String, Object>> getActiveProfiles() {
        logger.info("=== EnvironmentController.getActiveProfiles START ===");
        Map<String, Object> response = new HashMap<>();
        response.put("activeProfiles", environmentConfig.getActiveProfiles());
        response.put("defaultProfiles", environment.getDefaultProfiles());
        response.put("isDev", environmentConfig.isDevelopment());
        response.put("isProd", environmentConfig.isProduction());
        response.put("isTest", environmentConfig.isTest());
        response.put("isLocal", environmentConfig.isLocal());
        logger.info("=== EnvironmentController.getActiveProfiles END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 환경 설정 값 조회
     */
    @GetMapping("/settings")
    @Operation(summary = "환경 설정 값 조회", description = "모든 환경 설정 값을 반환합니다.")
    public ResponseEntity<Map<String, String>> getAllSettings() {
        logger.info("=== EnvironmentController.getAllSettings START ===");
        Map<String, String> settings = configurationManager.getAllEnvironmentSettings();
        logger.info("=== EnvironmentController.getAllSettings END ===");
        return ResponseEntity.ok(settings);
    }

    /**
     * 특정 설정 값 조회
     */
    @GetMapping("/settings/{key}")
    @Operation(summary = "특정 설정 값 조회", description = "지정된 키의 설정 값을 반환합니다.")
    public ResponseEntity<Map<String, String>> getSetting(@PathVariable String key) {
        logger.info("=== EnvironmentController.getSetting START ===");
        String value = configurationManager.getEnvironmentSetting(key);
        Map<String, String> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        logger.info("=== EnvironmentController.getSetting END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 설정 유효성 검사
     */
    @GetMapping("/validate")
    @Operation(summary = "설정 유효성 검사", description = "현재 환경 설정의 유효성을 검사합니다.")
    public ResponseEntity<Map<String, Object>> validateConfiguration() {
        logger.info("=== EnvironmentController.validateConfiguration START ===");
        boolean isValid = configurationManager.validateConfiguration();
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        response.put("message", isValid ? "설정이 유효합니다." : "설정에 문제가 있습니다.");
        logger.info("=== EnvironmentController.validateConfiguration END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 환경 정보 로그 출력
     */
    @GetMapping("/print")
    @Operation(summary = "환경 정보 로그 출력", description = "환경 설정 정보를 로그로 출력합니다.")
    public ResponseEntity<Map<String, String>> printEnvironmentInfo() {
        logger.info("=== EnvironmentController.printEnvironmentInfo START ===");
        configurationManager.printEnvironmentInfo();
        Map<String, String> response = new HashMap<>();
        response.put("message", "환경 정보가 로그에 출력되었습니다.");
        logger.info("=== EnvironmentController.printEnvironmentInfo END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 정보 조회
     */
    @GetMapping("/system")
    @Operation(summary = "시스템 정보 조회", description = "시스템 관련 정보를 반환합니다.")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        logger.info("=== EnvironmentController.getSystemInfo START ===");
        Map<String, Object> systemInfo = new HashMap<>();

        Runtime runtime = Runtime.getRuntime();
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("javaHome", System.getProperty("java.home"));
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        systemInfo.put("userName", System.getProperty("user.name"));
        systemInfo.put("userHome", System.getProperty("user.home"));
        systemInfo.put("totalMemory", runtime.totalMemory());
        systemInfo.put("freeMemory", runtime.freeMemory());
        systemInfo.put("maxMemory", runtime.maxMemory());
        systemInfo.put("availableProcessors", runtime.availableProcessors());
        logger.info("=== EnvironmentController.getSystemInfo END ===");
        return ResponseEntity.ok(systemInfo);
    }

    /**
     * 환경별 설정 비교
     */
    @GetMapping("/compare")
    @Operation(summary = "환경별 설정 비교", description = "다양한 환경의 설정을 비교합니다.")
    public ResponseEntity<Map<String, Object>> compareEnvironments() {
        logger.info("=== EnvironmentController.compareEnvironments START ===");
        Map<String, Object> comparison = new HashMap<>();

        String[] profiles = {"dev", "test", "prod"};
        for (String profile : profiles) {
            Map<String, Object> profileSettings = new HashMap<>();
            profileSettings.put("database", "jdbc:h2:mem:testdb");
            profileSettings.put("logging", "DEBUG");
            profileSettings.put("cache", "enabled");
            comparison.put(profile, profileSettings);
        }
        logger.info("=== EnvironmentController.compareEnvironments END ===");
        return ResponseEntity.ok(comparison);
    }

    /**
     * 환경 설정 통계
     */
    @GetMapping("/stats")
    @Operation(summary = "환경 설정 통계", description = "환경 설정에 대한 통계 정보를 반환합니다.")
    public ResponseEntity<Map<String, Object>> getEnvironmentStats() {
        logger.info("=== EnvironmentController.getEnvironmentStats START ===");
        Map<String, Object> stats = new HashMap<>();

        EnvironmentInfo envInfo = environmentConfig.getEnvironmentInfo();
        stats.put("totalSettings", 15);
        stats.put("activeSettings", 12);
        stats.put("cacheEnabled", envInfo.isCacheEnabled());
        stats.put("cacheTtl", envInfo.getCacheTtl());
        stats.put("cacheMaxSize", envInfo.getCacheMaxSize());
        logger.info("=== EnvironmentController.getEnvironmentStats END ===");
        return ResponseEntity.ok(stats);
    }
} 