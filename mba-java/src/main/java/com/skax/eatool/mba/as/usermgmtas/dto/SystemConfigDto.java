package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * 시스템 설정 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemConfigDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("configKey")
    private String configKey;
    
    @JsonProperty("configValue")
    private String configValue;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("category")
    private String category;
    
    @JsonProperty("isActive")
    private boolean isActive;
    
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    public SystemConfigDto() {}
    
    // 생성자
    public SystemConfigDto(String configKey, String configValue, String description, String category) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.category = category;
        this.isActive = true;
    }
    
    // Getter와 Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getConfigKey() {
        return configKey;
    }
    
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
    
    public String getConfigValue() {
        return configValue;
    }
    
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // 비즈니스 메서드
    public boolean isUserManagement() {
        return "USER_MANAGEMENT".equals(category);
    }
    
    public boolean isSecurity() {
        return "SECURITY".equals(category);
    }
    
    public boolean isSystem() {
        return "SYSTEM".equals(category);
    }
    
    public boolean isNotification() {
        return "NOTIFICATION".equals(category);
    }
    
    public String getCategoryDescription() {
        switch (category) {
            case "USER_MANAGEMENT": return "사용자 관리";
            case "SECURITY": return "보안";
            case "SYSTEM": return "시스템";
            case "NOTIFICATION": return "알림";
            default: return category;
        }
    }
    
    @Override
    public String toString() {
        return "SystemConfigDto{" +
                "id=" + id +
                ", configKey='" + configKey + '\'' +
                ", configValue='" + configValue + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 