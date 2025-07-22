package com.skax.eatool.mbc.dc.accountdc.dto;

import java.util.Date;

/**
 * 계정관리 DTO
 * 계정 생성, 수정, 조회에 사용되는 데이터 전송 객체
 */
public class AccountDto {
    
    private String accountId;           // 계정 ID
    private String accountNumber;       // 계정 번호
    private String name;                // 계정명
    private String identificationNumber; // 주민번호
    private String password;            // 비밀번호
    private String email;               // 이메일
    private String phone;               // 전화번호
    private String address;             // 주소
    private String status;              // 계정 상태 (ACTIVE, INACTIVE, LOCKED)
    private String role;                // 역할 (ADMIN, USER, MANAGER)
    private Date createDate;            // 생성일
    private Date updateDate;            // 수정일
    private String createUser;          // 생성자
    private String updateUser;          // 수정자
    private String department;          // 부서
    private String position;            // 직급
    private Date lastLoginDate;         // 마지막 로그인 일시
    private Integer loginFailCount;     // 로그인 실패 횟수
    private String description;         // 설명

    // 기본 생성자
    public AccountDto() {}

    // 생성자
    public AccountDto(String accountId, String accountNumber, String name) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.name = name;
    }

    // Getter와 Setter 메서드들
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accountId='" + accountId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
} 