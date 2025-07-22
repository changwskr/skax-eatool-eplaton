package com.skax.eatool.mbc.dc.accountdc.dto;

import java.util.Date;

/**
 * 계정 검색 DTO
 * 계정 목록 조회 시 검색 조건을 담는 데이터 전송 객체
 */
public class AccountSearchDto {
    
    private String accountId;           // 계정 ID
    private String accountNumber;       // 계정 번호
    private String name;                // 계정명
    private String email;               // 이메일
    private String phone;               // 전화번호
    private String status;              // 계정 상태
    private String role;                // 역할
    private String department;          // 부서
    private String position;            // 직급
    private Date createDateFrom;        // 생성일 시작
    private Date createDateTo;          // 생성일 종료
    private Date lastLoginDateFrom;     // 마지막 로그인 시작
    private Date lastLoginDateTo;       // 마지막 로그인 종료
    private Integer pageNumber;         // 페이지 번호
    private Integer pageSize;           // 페이지 크기
    private String sortField;           // 정렬 필드
    private String sortOrder;           // 정렬 순서 (ASC, DESC)
    private String searchKeyword;       // 검색 키워드 (통합 검색)

    // 기본 생성자
    public AccountSearchDto() {
        this.pageNumber = 1;
        this.pageSize = 10;
        this.sortOrder = "DESC";
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

    public Date getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(Date createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public Date getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(Date createDateTo) {
        this.createDateTo = createDateTo;
    }

    public Date getLastLoginDateFrom() {
        return lastLoginDateFrom;
    }

    public void setLastLoginDateFrom(Date lastLoginDateFrom) {
        this.lastLoginDateFrom = lastLoginDateFrom;
    }

    public Date getLastLoginDateTo() {
        return lastLoginDateTo;
    }

    public void setLastLoginDateTo(Date lastLoginDateTo) {
        this.lastLoginDateTo = lastLoginDateTo;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    /**
     * 검색 조건이 있는지 확인
     * @return 검색 조건 존재 여부
     */
    public boolean hasSearchCondition() {
        return accountId != null || accountNumber != null || name != null || 
               email != null || phone != null || status != null || role != null ||
               department != null || position != null || searchKeyword != null;
    }

    /**
     * 날짜 범위 검색 조건이 있는지 확인
     * @return 날짜 범위 검색 조건 존재 여부
     */
    public boolean hasDateRangeCondition() {
        return createDateFrom != null || createDateTo != null || 
               lastLoginDateFrom != null || lastLoginDateTo != null;
    }

    @Override
    public String toString() {
        return "AccountSearchDto{" +
                "accountId='" + accountId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", searchKeyword='" + searchKeyword + '\'' +
                '}';
    }
} 