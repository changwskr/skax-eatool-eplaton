package com.skax.eatool.mbc.config;

import java.util.Arrays;

/**
 * 환경 정보 DTO 클래스
 * 
 * 환경별 설정 정보를 담는 데이터 전송 객체입니다.
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
public class EnvironmentInfo {

    private String applicationName;
    private String environment;
    private boolean debugMode;
    private String dataAccessType;
    private String dataAccessDescription;
    private String systemName;
    private String systemVersion;
    private String systemDescription;
    private int transactionTimeout;
    private int maxRetryCount;
    private boolean auditLogging;
    private boolean cacheEnabled;
    private int cacheTtl;
    private int cacheMaxSize;
    private String[] activeProfiles;

    // Builder 패턴
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EnvironmentInfo info = new EnvironmentInfo();

        public Builder applicationName(String applicationName) {
            info.applicationName = applicationName;
            return this;
        }

        public Builder environment(String environment) {
            info.environment = environment;
            return this;
        }

        public Builder debugMode(boolean debugMode) {
            info.debugMode = debugMode;
            return this;
        }

        public Builder dataAccessType(String dataAccessType) {
            info.dataAccessType = dataAccessType;
            return this;
        }

        public Builder dataAccessDescription(String dataAccessDescription) {
            info.dataAccessDescription = dataAccessDescription;
            return this;
        }

        public Builder systemName(String systemName) {
            info.systemName = systemName;
            return this;
        }

        public Builder systemVersion(String systemVersion) {
            info.systemVersion = systemVersion;
            return this;
        }

        public Builder systemDescription(String systemDescription) {
            info.systemDescription = systemDescription;
            return this;
        }

        public Builder transactionTimeout(int transactionTimeout) {
            info.transactionTimeout = transactionTimeout;
            return this;
        }

        public Builder maxRetryCount(int maxRetryCount) {
            info.maxRetryCount = maxRetryCount;
            return this;
        }

        public Builder auditLogging(boolean auditLogging) {
            info.auditLogging = auditLogging;
            return this;
        }

        public Builder cacheEnabled(boolean cacheEnabled) {
            info.cacheEnabled = cacheEnabled;
            return this;
        }

        public Builder cacheTtl(int cacheTtl) {
            info.cacheTtl = cacheTtl;
            return this;
        }

        public Builder cacheMaxSize(int cacheMaxSize) {
            info.cacheMaxSize = cacheMaxSize;
            return this;
        }

        public Builder activeProfiles(String[] activeProfiles) {
            info.activeProfiles = activeProfiles;
            return this;
        }

        public EnvironmentInfo build() {
            return info;
        }
    }

    // Getter 메서드들
    public String getApplicationName() {
        return applicationName;
    }

    public String getEnvironment() {
        return environment;
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

    public String[] getActiveProfiles() {
        return activeProfiles;
    }

    // Setter 메서드들
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setDataAccessType(String dataAccessType) {
        this.dataAccessType = dataAccessType;
    }

    public void setDataAccessDescription(String dataAccessDescription) {
        this.dataAccessDescription = dataAccessDescription;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public void setSystemDescription(String systemDescription) {
        this.systemDescription = systemDescription;
    }

    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public void setAuditLogging(boolean auditLogging) {
        this.auditLogging = auditLogging;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setCacheTtl(int cacheTtl) {
        this.cacheTtl = cacheTtl;
    }

    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public void setActiveProfiles(String[] activeProfiles) {
        this.activeProfiles = activeProfiles;
    }

    @Override
    public String toString() {
        return "EnvironmentInfo{" +
                "applicationName='" + applicationName + '\'' +
                ", environment='" + environment + '\'' +
                ", debugMode=" + debugMode +
                ", dataAccessType='" + dataAccessType + '\'' +
                ", dataAccessDescription='" + dataAccessDescription + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", systemDescription='" + systemDescription + '\'' +
                ", transactionTimeout=" + transactionTimeout +
                ", maxRetryCount=" + maxRetryCount +
                ", auditLogging=" + auditLogging +
                ", cacheEnabled=" + cacheEnabled +
                ", cacheTtl=" + cacheTtl +
                ", cacheMaxSize=" + cacheMaxSize +
                ", activeProfiles=" + Arrays.toString(activeProfiles) +
                '}';
    }
}