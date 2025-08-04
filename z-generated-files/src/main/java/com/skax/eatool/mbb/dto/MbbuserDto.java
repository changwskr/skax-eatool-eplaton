package com.skax.eatool.mbb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Mbbuser DTO
 * Mbbuser Entity와 1:1 매핑되는 데이터 전송 객체
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbbuserDto {
    
    @JsonProperty("userId")
    private String userId;
    
    @JsonProperty("userName")
    private String userName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("createdAt")
    private String createdAt;
    
    // 기본 생성자
    public MbbuserDto() {}
    
    // 전체 필드 생성자
    public MbbuserDto(String userId, String userName, String email, String createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
    }
    
    // Getter와 Setter
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    // 비즈니스 메서드
    public boolean isValidEmail() {
        return email != null && email.contains("@");
    }
    
    public String getDisplayName() {
        return userName != null ? userName : userId;
    }
    
    @Override
    public String toString() {
        return "MbbuserDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
} 