package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 사용자 생성 요청 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequestDto {
    
    @JsonProperty("username")
    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 3, max = 50, message = "사용자명은 3자 이상 50자 이하여야 합니다.")
    private String username;
    
    @JsonProperty("password")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상 100자 이하여야 합니다.")
    private String password;
    
    @JsonProperty("email")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private String email;
    
    @JsonProperty("fullName")
    @NotBlank(message = "전체 이름은 필수입니다.")
    @Size(max = 100, message = "전체 이름은 100자 이하여야 합니다.")
    private String fullName;
    
    @JsonProperty("department")
    @Size(max = 100, message = "부서명은 100자 이하여야 합니다.")
    private String department;
    
    @JsonProperty("role")
    @Size(max = 50, message = "역할은 50자 이하여야 합니다.")
    private String role;
    
    @JsonProperty("phone")
    @Size(max = 20, message = "전화번호는 20자 이하여야 합니다.")
    private String phone;
    
    @JsonProperty("address")
    @Size(max = 255, message = "주소는 255자 이하여야 합니다.")
    private String address;
    
    // 기본 생성자
    public CreateUserRequestDto() {}
    
    // 생성자
    public CreateUserRequestDto(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }
    
    // Getter와 Setter
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    public boolean hasDepartment() {
        return department != null && !department.trim().isEmpty();
    }
    
    public boolean hasRole() {
        return role != null && !role.trim().isEmpty();
    }
    
    public boolean hasPhone() {
        return phone != null && !phone.trim().isEmpty();
    }
    
    public boolean hasAddress() {
        return address != null && !address.trim().isEmpty();
    }
    
    public String getDefaultRole() {
        return role != null ? role : "USER";
    }
    
    public String getDefaultDepartment() {
        return department != null ? department : "일반";
    }
    
    @Override
    public String toString() {
        return "CreateUserRequestDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
} 