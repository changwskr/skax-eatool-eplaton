package com.skax.eatool.mbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 환경별 설정 파일 관리자
 * 
 * 프로그램 내에서 다양한 환경파일을 동적으로 로드하고 관리하는 클래스입니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Component
public class ConfigurationManager {

    private static final NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("ConfigurationManager");

    @Autowired
    private Environment environment;

    @Autowired
    private EnvironmentConfig environmentConfig;

    private Map<String, Properties> profileProperties;
    private Map<String, String> environmentSettings;

    /**
     * 초기화
     */
    @PostConstruct
    public void init() {
        logger.info("=== 설정 관리자 초기화 ===", "ConfigurationManager");

        profileProperties = new HashMap<>();
        environmentSettings = new HashMap<>();

        // 환경별 설정 초기화
        initializeEnvironmentSettings();

        // 프로파일별 속성 로드
        loadProfileProperties();

        logger.info("=== 설정 관리자 초기화 완료 ===", "ConfigurationManager");
    }

    /**
     * 환경별 설정 초기화
     */
    private void initializeEnvironmentSettings() {
        String[] activeProfiles = environment.getActiveProfiles();

        for (String profile : activeProfiles) {
            switch (profile) {
                case "dev":
                    environmentSettings.put("database.type", "H2_IN_MEMORY");
                    environmentSettings.put("logging.level", "DEBUG");
                    environmentSettings.put("swagger.enabled", "true");
                    environmentSettings.put("h2.console.enabled", "true");
                    environmentSettings.put("security.level", "RELAXED");
                    break;

                case "local":
                    environmentSettings.put("database.type", "H2_IN_MEMORY_MYSQL");
                    environmentSettings.put("logging.level", "DEBUG");
                    environmentSettings.put("swagger.enabled", "true");
                    environmentSettings.put("h2.console.enabled", "true");
                    environmentSettings.put("security.level", "VERY_RELAXED");
                    environmentSettings.put("devtools.enabled", "true");
                    break;

                case "test":
                    environmentSettings.put("database.type", "H2_FILE");
                    environmentSettings.put("logging.level", "INFO");
                    environmentSettings.put("swagger.enabled", "true");
                    environmentSettings.put("h2.console.enabled", "true");
                    environmentSettings.put("security.level", "TEST");
                    environmentSettings.put("cache.enabled", "false");
                    break;

                case "prod":
                    environmentSettings.put("database.type", "MYSQL");
                    environmentSettings.put("logging.level", "WARN");
                    environmentSettings.put("swagger.enabled", "false");
                    environmentSettings.put("h2.console.enabled", "false");
                    environmentSettings.put("security.level", "STRICT");
                    environmentSettings.put("cache.enabled", "true");
                    break;

                default:
                    environmentSettings.put("database.type", "H2_IN_MEMORY");
                    environmentSettings.put("logging.level", "INFO");
                    environmentSettings.put("swagger.enabled", "true");
                    environmentSettings.put("h2.console.enabled", "true");
                    environmentSettings.put("security.level", "DEFAULT");
                    break;
            }
        }

        logger.info("환경 설정 초기화 완료: " + environmentSettings, "ConfigurationManager");
    }

    /**
     * 프로파일별 속성 로드
     */
    private void loadProfileProperties() {
        String[] activeProfiles = environment.getActiveProfiles();

        for (String profile : activeProfiles) {
            Properties props = new Properties();

            // 프로파일별 기본 속성 설정
            switch (profile) {
                case "dev":
                    props.setProperty("server.port", "8080");
                    props.setProperty("spring.datasource.url", "jdbc:h2:mem:mbcdb_dev");
                    props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
                    props.setProperty("spring.jpa.show-sql", "true");
                    break;

                case "local":
                    props.setProperty("server.port", "8080");
                    props.setProperty("spring.datasource.url", "jdbc:h2:mem:mbcdb_local;MODE=MySQL");
                    props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
                    props.setProperty("spring.jpa.show-sql", "true");
                    props.setProperty("spring.devtools.restart.enabled", "true");
                    break;

                case "test":
                    props.setProperty("server.port", "8081");
                    props.setProperty("spring.datasource.url", "jdbc:h2:file:./data/mbcdb_test");
                    props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
                    props.setProperty("spring.jpa.show-sql", "true");
                    break;

                case "prod":
                    props.setProperty("server.port", "8080");
                    props.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/mbc_prod");
                    props.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
                    props.setProperty("spring.jpa.show-sql", "false");
                    break;
            }

            profileProperties.put(profile, props);
            logger.info("프로파일 " + profile + " 속성 로드 완료", "ConfigurationManager");
        }
    }

    /**
     * 특정 프로파일의 속성 가져오기
     */
    public Properties getProfileProperties(String profile) {
        return profileProperties.get(profile);
    }

    /**
     * 현재 활성 프로파일의 속성 가져오기
     */
    public Properties getCurrentProfileProperties() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return profileProperties.get(activeProfiles[0]);
        }
        return new Properties();
    }

    /**
     * 환경 설정 값 가져오기
     */
    public String getEnvironmentSetting(String key) {
        return environmentSettings.get(key);
    }

    /**
     * 환경 설정 값 가져오기 (기본값 포함)
     */
    public String getEnvironmentSetting(String key, String defaultValue) {
        return environmentSettings.getOrDefault(key, defaultValue);
    }

    /**
     * 모든 환경 설정 가져오기
     */
    public Map<String, String> getAllEnvironmentSettings() {
        return new HashMap<>(environmentSettings);
    }

    /**
     * 환경 정보 요약 반환
     */
    public Map<String, Object> getEnvironmentSummary() {
        Map<String, Object> summary = new HashMap<>();

        summary.put("activeProfiles", environment.getActiveProfiles());
        summary.put("defaultProfiles", environment.getDefaultProfiles());
        summary.put("environmentSettings", environmentSettings);
        summary.put("environmentInfo", environmentConfig.getEnvironmentInfo());

        return summary;
    }

    /**
     * 설정 유효성 검사
     */
    public boolean validateConfiguration() {
        try {
            // 필수 설정 확인
            String[] requiredSettings = {
                    "database.type",
                    "logging.level",
                    "security.level"
            };

            for (String setting : requiredSettings) {
                if (!environmentSettings.containsKey(setting)) {
                    logger.warn("필수 설정 누락: " + setting, "ConfigurationManager");
                    return false;
                }
            }

            logger.info("설정 유효성 검사 통과", "ConfigurationManager");
            return true;

        } catch (Exception e) {
            logger.error("설정 유효성 검사 실패: " + e.getMessage(), "ConfigurationManager");
            return false;
        }
    }

    /**
     * 환경별 설정 정보 출력
     */
    public void printEnvironmentInfo() {
        logger.info("=== 환경별 설정 정보 ===", "ConfigurationManager");

        String[] activeProfiles = environment.getActiveProfiles();
        logger.info("활성 프로파일: " + String.join(", ", activeProfiles), "ConfigurationManager");

        for (Map.Entry<String, String> entry : environmentSettings.entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue(), "ConfigurationManager");
        }

        EnvironmentInfo envInfo = environmentConfig.getEnvironmentInfo();
        logger.info("애플리케이션 이름: " + envInfo.getApplicationName(), "ConfigurationManager");
        logger.info("시스템 이름: " + envInfo.getSystemName(), "ConfigurationManager");
        logger.info("시스템 버전: " + envInfo.getSystemVersion(), "ConfigurationManager");
        logger.info("디버그 모드: " + envInfo.isDebugMode(), "ConfigurationManager");
        logger.info("캐시 활성화: " + envInfo.isCacheEnabled(), "ConfigurationManager");

        logger.info("=== 환경별 설정 정보 완료 ===", "ConfigurationManager");
    }
}