package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * 감사 로그 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditLogDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("action")
    private String action;
    
    @JsonProperty("resource")
    private String resource;
    
    @JsonProperty("resourceId")
    private String resourceId;
    
    @JsonProperty("details")
    private String details;
    
    @JsonProperty("ipAddress")
    private String ipAddress;
    
    @JsonProperty("userAgent")
    private String userAgent;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    
    // 기본 생성자
    public AuditLogDto() {}
    
    // 생성자
    public AuditLogDto(Long userId, String username, String action, String resource, String resourceId) {
        this.userId = userId;
        this.username = username;
        this.action = action;
        this.resource = resource;
        this.resourceId = resourceId;
        this.status = "SUCCESS";
    }
    
    // Getter와 Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getResource() {
        return resource;
    }
    
    public void setResource(String resource) {
        this.resource = resource;
    }
    
    public String getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // 비즈니스 메서드
    public boolean isSuccess() {
        return "SUCCESS".equals(status);
    }
    
    public boolean isFailure() {
        return "FAILURE".equals(status);
    }
    
    public String getActionDescription() {
        switch (action) {
            case "CREATE": return "생성";
            case "READ": return "조회";
            case "UPDATE": return "수정";
            case "DELETE": return "삭제";
            case "LOGIN": return "로그인";
            case "LOGOUT": return "로그아웃";
            default: return action;
        }
    }
    
    public String getResourceDescription() {
        switch (resource) {
            case "USER": return "사용자";
            case "ROLE": return "역할";
            case "PERMISSION": return "권한";
            case "SYSTEM": return "시스템";
            default: return resource;
        }
    }
    
    @Override
    public String toString() {
        return "AuditLogDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", resource='" + resource + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
} 