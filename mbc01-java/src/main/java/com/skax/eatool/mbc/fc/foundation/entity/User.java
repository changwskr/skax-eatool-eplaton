package com.skax.eatool.mbc.fc.foundation.entity;

import java.time.LocalDateTime;

/**
 * 사용자 엔티티 클래스
 * 
 * 프로그램명: User.java
 * 설명: 사용자 정보를 담는 DTO 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 기본 정보 관리
 * - JDBC Template 매핑
 * 
 * @version 1.0
 */
public class User extends BaseEntity {

    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private UserStatus status = UserStatus.ACTIVE;
    private LocalDateTime lastLoginDate;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * 사용자 상태 열거형
     */
    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED, DELETED
    }
}