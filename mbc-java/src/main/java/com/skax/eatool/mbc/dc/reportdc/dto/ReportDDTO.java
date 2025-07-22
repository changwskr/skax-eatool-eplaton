package com.skax.eatool.mbc.dc.reportdc.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 보고서 Data Domain Transfer Object
 * 
 * 프로그램명: ReportDDTO.java
 * 설명: 보고서 관련 데이터를 DC 레이어에서 전달하는 DTO
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 조회 조건 설정
 * - 보고서 결과 데이터 전달
 * - 통계 데이터 전달
 * 
 * @version 1.0
 */
public class ReportDDTO {
    
    // 보고서 기본 정보
    private String reportId;
    private String reportType; // ACCOUNT, USER, SYSTEM
    private String reportName;
    private String reportDescription;
    
    // 조회 조건
    private Date startDate;
    private Date endDate;
    private String periodType; // DAILY, WEEKLY, MONTHLY, YEARLY
    private String groupBy; // DATE, USER, ACCOUNT, STATUS
    
    // 페이징
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;
    
    // 결과 데이터
    private List<Map<String, Object>> reportData;
    private Map<String, Object> summaryData;
    private Map<String, Object> chartData;
    
    // 통계 정보
    private Long totalCount;
    private Long activeCount;
    private Long inactiveCount;
    private Double averageValue;
    private Double maxValue;
    private Double minValue;
    
    // 생성/수정 정보
    private Date createdDate;
    private Date updatedDate;
    
    // 추가 필드들
    private String reportStatus;
    private String createdBy;
    private String updatedBy;
    private String reportConfig;
    private String reportTemplate;
    
    // 생성자
    public ReportDDTO() {}
    
    public ReportDDTO(String reportType) {
        this.reportType = reportType;
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
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getPeriodType() {
        return periodType;
    }
    
    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
    
    public String getGroupBy() {
        return groupBy;
    }
    
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public List<Map<String, Object>> getReportData() {
        return reportData;
    }
    
    public void setReportData(List<Map<String, Object>> reportData) {
        this.reportData = reportData;
    }
    
    public Map<String, Object> getSummaryData() {
        return summaryData;
    }
    
    public void setSummaryData(Map<String, Object> summaryData) {
        this.summaryData = summaryData;
    }
    
    public Map<String, Object> getChartData() {
        return chartData;
    }
    
    public void setChartData(Map<String, Object> chartData) {
        this.chartData = chartData;
    }
    
    public Long getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
    
    public Long getActiveCount() {
        return activeCount;
    }
    
    public void setActiveCount(Long activeCount) {
        this.activeCount = activeCount;
    }
    
    public Long getInactiveCount() {
        return inactiveCount;
    }
    
    public void setInactiveCount(Long inactiveCount) {
        this.inactiveCount = inactiveCount;
    }
    
    public Double getAverageValue() {
        return averageValue;
    }
    
    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }
    
    public Double getMaxValue() {
        return maxValue;
    }
    
    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
    
    public Double getMinValue() {
        return minValue;
    }
    
    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
        return "ReportDDTO{" +
                "reportId='" + reportId + '\'' +
                ", reportType='" + reportType + '\'' +
                ", reportName='" + reportName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", periodType='" + periodType + '\'' +
                ", groupBy='" + groupBy + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", totalCount=" + totalCount +
                ", activeCount=" + activeCount +
                ", inactiveCount=" + inactiveCount +
                '}';
    }
} 