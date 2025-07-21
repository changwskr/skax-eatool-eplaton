package com.skax.eatool.mbc.dc.usermgtdc.entity;

import java.time.LocalDateTime;

/**
 * 사용자 엔티티
 * 
 * 프로그램명: UserEntity.java
 * 설명: 사용자 정보를 담는 DTO 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * @version 1.0
 */
public class UserEntity {

    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userRole;
    private String useYn = "Y";
    private String delYn = "N";
    private String regDt;
    private String updDt;

    // 기본 생성자
    public UserEntity() {
    }

    // 생성자
    public UserEntity(String userId, String userName, String userEmail, String userPassword, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    // Getter and Setter methods
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUpdDt() {
        return updDt;
    }

    public void setUpdDt(String updDt) {
        this.updDt = updDt;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole='" + userRole + '\'' +
                ", useYn='" + useYn + '\'' +
                ", delYn='" + delYn + '\'' +
                ", regDt='" + regDt + '\'' +
                ", updDt='" + updDt + '\'' +
                '}';
    }
}