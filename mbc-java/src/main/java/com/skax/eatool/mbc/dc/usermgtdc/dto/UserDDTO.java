package com.skax.eatool.mbc.dc.usermgtdc.dto;

import java.util.Date;
import java.util.List;

/**
 * 사용자 관리 Data Domain Transfer Object
 * 
 * 프로그램명: UserDDTO.java
 * 설명: 사용자 관리 데이터 전송 객체 (Data Layer)
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 정보 전송 (PC <-> DC 간)
 * - 검색 조건 전송
 * - 페이징 정보 전송
 * 
 * @version 1.0
 */
public class UserDDTO {
    
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
    
    // 검색 조건
    private String searchKeyword;
    private String searchType;
    private String roleFilter;
    private String statusFilter;
    private String departmentFilter;
    
    // 페이징 정보
    private Integer page;
    private Integer size;
    private Integer totalCount;
    private List<UserDDTO> userList;
    
    // 생성자
    public UserDDTO() {}
    
    public UserDDTO(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
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
    
    public String getSearchKeyword() { return searchKeyword; }
    public void setSearchKeyword(String searchKeyword) { this.searchKeyword = searchKeyword; }
    
    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }
    
    public String getRoleFilter() { return roleFilter; }
    public void setRoleFilter(String roleFilter) { this.roleFilter = roleFilter; }
    
    public String getStatusFilter() { return statusFilter; }
    public void setStatusFilter(String statusFilter) { this.statusFilter = statusFilter; }
    
    public String getDepartmentFilter() { return departmentFilter; }
    public void setDepartmentFilter(String departmentFilter) { this.departmentFilter = departmentFilter; }
    
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    
    public Integer getTotalCount() { return totalCount; }
    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }
    
    public List<UserDDTO> getUserList() { return userList; }
    public void setUserList(List<UserDDTO> userList) { this.userList = userList; }
    
    @Override
    public String toString() {
        return "UserDDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
