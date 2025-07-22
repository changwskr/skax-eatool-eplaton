package com.skax.eatool.mbc.dc.usermgtdc;

import java.util.Date;

/**
 * 사용자 엔티티 클래스
 * 
 * 프로그램명: User.java
 * 설명: 사용자 정보를 담는 엔티티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 기본 정보 저장
 * - 사용자 상세 정보 저장
 * - 데이터베이스 테이블과 매핑
 * 
 * @version 1.0
 */
public class User {
    
    // 사용자 기본 정보
    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String role;
    private String status;
    private Date createdDate;
    private Date updatedDate;
    
    // 사용자 상세 정보
    private String department;
    private String position;
    private String employeeId;
    private Date birthDate;
    private String address;
    private String emergencyContact;
    private String emergencyContactName;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Integer loginCount;
    
    // 생성자
    public User() {}
    
    public User(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }
    
    public User(String userId, String userName, String email, String phone, String role, String status) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }
    
    // Getter/Setter 메서드들
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public Date getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(Date updatedDate) { this.updatedDate = updatedDate; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    
    public Date getLastLoginDate() { return lastLoginDate; }
    public void setLastLoginDate(Date lastLoginDate) { this.lastLoginDate = lastLoginDate; }
    
    public Integer getLoginCount() { return loginCount; }
    public void setLoginCount(Integer loginCount) { this.loginCount = loginCount; }
    
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
