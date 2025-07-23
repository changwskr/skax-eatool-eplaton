package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 사용자 수정 요청 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequestDto {
    
    @JsonProperty("email")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private String email;
    
    @JsonProperty("fullName")
    @Size(max = 100, message = "전체 이름은 100자 이하여야 합니다.")
    private String fullName;
    
    @JsonProperty("department")
    @Size(max = 100, message = "부서명은 100자 이하여야 합니다.")
    private String department;
    
    @JsonProperty("role")
    @Size(max = 50, message = "역할은 50자 이하여야 합니다.")
    private String role;
    
    @JsonProperty("status")
    @Size(max = 20, message = "상태는 20자 이하여야 합니다.")
    private String status;
    
    @JsonProperty("phone")
    @Size(max = 20, message = "전화번호는 20자 이하여야 합니다.")
    private String phone;
    
    @JsonProperty("address")
    @Size(max = 255, message = "주소는 255자 이하여야 합니다.")
    private String address;
    
    // 기본 생성자
    public UpdateUserRequestDto() {}
    
    // 생성자
    public UpdateUserRequestDto(String email, String fullName, String department, String role) {
        this.email = email;
        this.fullName = fullName;
        this.department = department;
        this.role = role;
    }
    
    // Getter와 Setter
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
    
    // 비즈니스 메서드
    public boolean hasEmail() {
        return email != null && !email.trim().isEmpty();
    }
    
    public boolean hasFullName() {
        return fullName != null && !fullName.trim().isEmpty();
    }
    
    public boolean hasDepartment() {
        return department != null && !department.trim().isEmpty();
    }
    
    public boolean hasRole() {
        return role != null && !role.trim().isEmpty();
    }
    
    public boolean hasStatus() {
        return status != null && !status.trim().isEmpty();
    }
    
    public boolean hasPhone() {
        return phone != null && !phone.trim().isEmpty();
    }
    
    public boolean hasAddress() {
        return address != null && !address.trim().isEmpty();
    }
    
    public boolean hasAnyField() {
        return hasEmail() || hasFullName() || hasDepartment() || 
               hasRole() || hasStatus() || hasPhone() || hasAddress();
    }
    
    public boolean isValidStatus() {
        if (status == null) return true;
        return "ACTIVE".equals(status) || "INACTIVE".equals(status) || 
               "SUSPENDED".equals(status) || "DELETED".equals(status);
    }
    
    public boolean isValidRole() {
        if (role == null) return true;
        return "ADMIN".equals(role) || "USER".equals(role) || 
               "MANAGER".equals(role) || "GUEST".equals(role);
    }
    
    @Override
    public String toString() {
        return "UpdateUserRequestDto{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
} 