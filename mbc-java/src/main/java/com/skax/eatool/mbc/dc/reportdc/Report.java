package com.skax.eatool.mbc.dc.reportdc;

import java.util.Date;

/**
 * 보고서 Entity
 * 
 * 프로그램명: Report.java
 * 설명: 보고서 관련 데이터베이스 테이블과 매핑되는 엔티티
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 기본 정보 저장
 * - 보고서 생성/수정 이력 관리
 * 
 * @version 1.0
 */
public class Report {
    
    // 기본 정보
    private String reportId;
    private String reportType; // ACCOUNT, USER, SYSTEM
    private String reportName;
    private String reportDescription;
    private String reportStatus; // ACTIVE, INACTIVE, DRAFT
    
    // 생성/수정 정보
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    
    // 보고서 설정
    private String reportConfig; // JSON 형태의 설정 정보
    private String reportTemplate; // 보고서 템플릿 정보
    
    // 생성자
    public Report() {}
    
    public Report(String reportId, String reportType, String reportName) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportName = reportName;
    }
    
    // Getter/Setter
    public String getReportId() {
        return reportId;
    }
    
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportName() {
        return reportName;
    }
    
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public String getReportDescription() {
        return reportDescription;
    }
    
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
    
    public String getReportStatus() {
        return reportStatus;
    }
    
    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public Date getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public String getReportConfig() {
        return reportConfig;
    }
    
    public void setReportConfig(String reportConfig) {
        this.reportConfig = reportConfig;
    }
    
    public String getReportTemplate() {
        return reportTemplate;
    }
    
    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }
    
    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", reportType='" + reportType + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                '}';
    }
} 