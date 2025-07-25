package com.skax.eatool.mbc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * 환경별 설정 관리 클래스
 * 
 * 프로그램 내에서 다양한 환경파일을 읽고 처리하는 설정 클래스입니다.
 * 
 * @author skax.eatool
 * @version 1.0.0
 * @since 2024
 */
@Configuration
public class EnvironmentConfig {

    private static final NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("EnvironmentConfig");

    private final Environment environment;

    @Value("${spring.application.name:mbc-application}")
    private String applicationName;

    @Value("${app.environment:dev}")
    private String appEnvironment;

    @Value("${app.debug:false}")
    private boolean debugMode;

    @Value("${app.data-access.type:mybatis}")
    private String dataAccessType;

    @Value("${app.data-access.description:}")
    private String dataAccessDescription;

    @Value("${mbc.system.name:MBC System}")
    private String systemName;

    @Value("${mbc.system.version:1.0.0}")
    private String systemVersion;

    @Value("${mbc.system.description:}")
    private String systemDescription;

    @Value("${mbc.business.transaction-timeout:30}")
    private int transactionTimeout;

    @Value("${mbc.business.max-retry-count:3}")
    private int maxRetryCount;

    @Value("${mbc.business.audit-logging:true}")
    private boolean auditLogging;

    @Value("${mbc.cache.enabled:false}")
    private boolean cacheEnabled;

    @Value("${mbc.cache.ttl:300}")
    private int cacheTtl;

    @Value("${mbc.cache.max-size:1000}")
    private int cacheMaxSize;

    public EnvironmentConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * 초기화 시 환경 정보를 로그로 출력
     */
    @PostConstruct
    public void init() {
        logger.info("=== 환경 설정 초기화 ===", "EnvironmentConfig");
        logger.info("애플리케이션 이름: " + applicationName, "EnvironmentConfig");
        logger.info("활성 프로파일: " + Arrays.toString(environment.getActiveProfiles()), "EnvironmentConfig");
        logger.info("기본 프로파일: " + Arrays.toString(environment.getDefaultProfiles()), "EnvironmentConfig");
        logger.info("앱 환경: " + appEnvironment, "EnvironmentConfig");
        logger.info("디버그 모드: " + debugMode, "EnvironmentConfig");
        logger.info("데이터 접근 방식: " + dataAccessType, "EnvironmentConfig");
        logger.info("시스템 이름: " + systemName, "EnvironmentConfig");
        logger.info("시스템 버전: " + systemVersion, "EnvironmentConfig");
        logger.info("캐시 활성화: " + cacheEnabled, "EnvironmentConfig");
        logger.info("=== 환경 설정 초기화 완료 ===", "EnvironmentConfig");
    }

    /**
     * 현재 활성 프로파일 확인
     */
    public String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }

    /**
     * 특정 프로파일이 활성화되어 있는지 확인
     */
    public boolean isProfileActive(String profile) {
        return Arrays.asList(environment.getActiveProfiles()).contains(profile);
    }

    /**
     * 개발 환경인지 확인
     */
    public boolean isDevelopment() {
        return isProfileActive("dev") || isProfileActive("local");
    }

    /**
     * 운영 환경인지 확인
     */
    public boolean isProduction() {
        return isProfileActive("prod");
    }

    /**
     * 테스트 환경인지 확인
     */
    public boolean isTest() {
        return isProfileActive("test");
    }

    /**
     * 로컬 환경인지 확인
     */
    public boolean isLocal() {
        return isProfileActive("local");
    }

    /**
     * 환경별 설정 정보 반환
     */
    public EnvironmentInfo getEnvironmentInfo() {
        return EnvironmentInfo.builder()
                .applicationName(applicationName)
                .environment(appEnvironment)
                .debugMode(debugMode)
                .dataAccessType(dataAccessType)
                .dataAccessDescription(dataAccessDescription)
                .systemName(systemName)
                .systemVersion(systemVersion)
                .systemDescription(systemDescription)
                .transactionTimeout(transactionTimeout)
                .maxRetryCount(maxRetryCount)
                .auditLogging(auditLogging)
                .cacheEnabled(cacheEnabled)
                .cacheTtl(cacheTtl)
                .cacheMaxSize(cacheMaxSize)
                .activeProfiles(getActiveProfiles())
                .build();
    }

    // Getter 메서드들
    public String getApplicationName() {
        return applicationName;
    }

    public String getAppEnvironment() {
        return appEnvironment;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public String getDataAccessType() {
        return dataAccessType;
    }

    public String getDataAccessDescription() {
        return dataAccessDescription;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public String getSystemDescription() {
        return systemDescription;
    }

    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public boolean isAuditLogging() {
        return auditLogging;
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public int getCacheTtl() {
        return cacheTtl;
    }

    public int getCacheMaxSize() {
        return cacheMaxSize;
    }
}