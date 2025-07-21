package com.skax.eatool.mbc.controller;

import com.skax.eatool.mbc.config.ConfigurationManager;
import com.skax.eatool.mbc.config.EnvironmentConfig;
import com.skax.eatool.mbc.config.EnvironmentInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 환경 설정 정보 REST API 컨트롤러
 * 
 * 프로그램 내에서 다양한 환경파일 정보를 REST API로 제공합니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/environment")
@Tag(name = "Environment Management", description = "환경 설정 관리 API")
public class EnvironmentController {

    @Autowired
    private ConfigurationManager configurationManager;

    @Autowired
    private EnvironmentConfig environmentConfig;

    /**
     * 환경 정보 요약 조회
     */
    @GetMapping("/summary")
    @Operation(summary = "환경 정보 요약 조회", description = "현재 활성화된 환경의 설정 정보를 요약하여 반환합니다.")
    public ResponseEntity<Map<String, Object>> getEnvironmentSummary() {
        Map<String, Object> summary = configurationManager.getEnvironmentSummary();
        return ResponseEntity.ok(summary);
    }

    /**
     * 환경 정보 상세 조회
     */
    @GetMapping("/info")
    @Operation(summary = "환경 정보 상세 조회", description = "현재 환경의 상세 설정 정보를 반환합니다.")
    public ResponseEntity<EnvironmentInfo> getEnvironmentInfo() {
        EnvironmentInfo info = environmentConfig.getEnvironmentInfo();
        return ResponseEntity.ok(info);
    }

    /**
     * 활성 프로파일 조회
     */
    @GetMapping("/profiles")
    @Operation(summary = "활성 프로파일 조회", description = "현재 활성화된 프로파일 목록을 반환합니다.")
    public ResponseEntity<Map<String, Object>> getActiveProfiles() {
        Map<String, Object> response = new HashMap<>();
        response.put("activeProfiles", environmentConfig.getActiveProfiles());
        response.put("isDevelopment", environmentConfig.isDevelopment());
        response.put("isProduction", environmentConfig.isProduction());
        response.put("isTest", environmentConfig.isTest());
        response.put("isLocal", environmentConfig.isLocal());
        return ResponseEntity.ok(response);
    }

    /**
     * 환경 설정 값 조회
     */
    @GetMapping("/settings")
    @Operation(summary = "환경 설정 값 조회", description = "모든 환경 설정 값을 반환합니다.")
    public ResponseEntity<Map<String, String>> getAllSettings() {
        Map<String, String> settings = configurationManager.getAllEnvironmentSettings();
        return ResponseEntity.ok(settings);
    }

    /**
     * 특정 설정 값 조회
     */
    @GetMapping("/settings/{key}")
    @Operation(summary = "특정 설정 값 조회", description = "지정된 키의 설정 값을 반환합니다.")
    public ResponseEntity<Map<String, String>> getSetting(@PathVariable String key) {
        String value = configurationManager.getEnvironmentSetting(key);
        Map<String, String> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        return ResponseEntity.ok(response);
    }

    /**
     * 설정 유효성 검사
     */
    @GetMapping("/validate")
    @Operation(summary = "설정 유효성 검사", description = "현재 환경 설정의 유효성을 검사합니다.")
    public ResponseEntity<Map<String, Object>> validateConfiguration() {
        boolean isValid = configurationManager.validateConfiguration();
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        response.put("message", isValid ? "설정이 유효합니다." : "설정에 문제가 있습니다.");
        return ResponseEntity.ok(response);
    }

    /**
     * 환경별 설정 정보 출력 (로그)
     */
    @PostMapping("/print-info")
    @Operation(summary = "환경 정보 로그 출력", description = "환경 설정 정보를 로그로 출력합니다.")
    public ResponseEntity<Map<String, String>> printEnvironmentInfo() {
        configurationManager.printEnvironmentInfo();
        Map<String, String> response = new HashMap<>();
        response.put("message", "환경 정보가 로그에 출력되었습니다.");
        return ResponseEntity.ok(response);
    }

    /**
     * 시스템 정보 조회
     */
    @GetMapping("/system")
    @Operation(summary = "시스템 정보 조회", description = "시스템 관련 정보를 반환합니다.")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();

        // 시스템 속성
        systemInfo.put("java.version", System.getProperty("java.version"));
        systemInfo.put("java.home", System.getProperty("java.home"));
        systemInfo.put("os.name", System.getProperty("os.name"));
        systemInfo.put("os.version", System.getProperty("os.version"));
        systemInfo.put("user.name", System.getProperty("user.name"));
        systemInfo.put("user.dir", System.getProperty("user.dir"));

        // 애플리케이션 정보
        EnvironmentInfo envInfo = environmentConfig.getEnvironmentInfo();
        systemInfo.put("applicationName", envInfo.getApplicationName());
        systemInfo.put("systemName", envInfo.getSystemName());
        systemInfo.put("systemVersion", envInfo.getSystemVersion());
        systemInfo.put("systemDescription", envInfo.getSystemDescription());

        // 메모리 정보
        Runtime runtime = Runtime.getRuntime();
        systemInfo.put("totalMemory", runtime.totalMemory());
        systemInfo.put("freeMemory", runtime.freeMemory());
        systemInfo.put("maxMemory", runtime.maxMemory());
        systemInfo.put("availableProcessors", runtime.availableProcessors());

        return ResponseEntity.ok(systemInfo);
    }

    /**
     * 환경별 설정 비교
     */
    @GetMapping("/compare")
    @Operation(summary = "환경별 설정 비교", description = "다양한 환경의 설정을 비교합니다.")
    public ResponseEntity<Map<String, Object>> compareEnvironments() {
        Map<String, Object> comparison = new HashMap<>();

        String[] profiles = { "dev", "local", "test", "prod" };

        for (String profile : profiles) {
            Map<String, String> profileSettings = new HashMap<>();

            switch (profile) {
                case "dev":
                    profileSettings.put("database.type", "H2_IN_MEMORY");
                    profileSettings.put("logging.level", "DEBUG");
                    profileSettings.put("swagger.enabled", "true");
                    profileSettings.put("security.level", "RELAXED");
                    break;
                case "local":
                    profileSettings.put("database.type", "H2_IN_MEMORY_MYSQL");
                    profileSettings.put("logging.level", "DEBUG");
                    profileSettings.put("swagger.enabled", "true");
                    profileSettings.put("security.level", "VERY_RELAXED");
                    break;
                case "test":
                    profileSettings.put("database.type", "H2_FILE");
                    profileSettings.put("logging.level", "INFO");
                    profileSettings.put("swagger.enabled", "true");
                    profileSettings.put("security.level", "TEST");
                    break;
                case "prod":
                    profileSettings.put("database.type", "MYSQL");
                    profileSettings.put("logging.level", "WARN");
                    profileSettings.put("swagger.enabled", "false");
                    profileSettings.put("security.level", "STRICT");
                    break;
            }

            comparison.put(profile, profileSettings);
        }

        return ResponseEntity.ok(comparison);
    }

    /**
     * 환경 설정 통계
     */
    @GetMapping("/stats")
    @Operation(summary = "환경 설정 통계", description = "환경 설정에 대한 통계 정보를 반환합니다.")
    public ResponseEntity<Map<String, Object>> getEnvironmentStats() {
        Map<String, Object> stats = new HashMap<>();

        EnvironmentInfo envInfo = environmentConfig.getEnvironmentInfo();
        Map<String, String> settings = configurationManager.getAllEnvironmentSettings();

        stats.put("totalSettings", settings.size());
        stats.put("cacheEnabled", envInfo.isCacheEnabled());
        stats.put("debugMode", envInfo.isDebugMode());
        stats.put("auditLogging", envInfo.isAuditLogging());
        stats.put("transactionTimeout", envInfo.getTransactionTimeout());
        stats.put("maxRetryCount", envInfo.getMaxRetryCount());
        stats.put("cacheTtl", envInfo.getCacheTtl());
        stats.put("cacheMaxSize", envInfo.getCacheMaxSize());

        return ResponseEntity.ok(stats);
    }
}