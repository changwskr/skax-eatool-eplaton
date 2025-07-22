package com.skax.eatool.mbc.dc.reportdc;

import com.skax.eatool.mbc.dc.reportdc.dto.ReportDDTO;
import com.skax.eatool.mbc.dc.reportdc.mapper.ReportMapper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 보고서 Domain Component
 * 
 * 프로그램명: DCReport.java
 * 설명: 보고서 관련 비즈니스 로직을 담당하는 Domain Component
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 목록 조회
 * - 보고서 상세 조회
 * - 통계 데이터 조회
 * - 차트 데이터 생성
 * 
 * @version 1.0
 */
@Component
public class DCReport {
    
    private static final Logger logger = LoggerFactory.getLogger(DCReport.class);
    
    @Autowired
    private ReportMapper reportMapper;
    
    /**
     * 보고서 목록 조회
     */
    public List<ReportDDTO> getListReport(ReportDDTO searchCondition) throws NewBusinessException {
        logger.info("=== DCReport.getListReport START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"));
        
        try {
            if (searchCondition == null) {
                logger.error("ReportDDTO가 NULL입니다.", "DCReport");
                throw new NewBusinessException("ReportDDTO는 필수입니다.");
            }
            
            List<Report> reportList = reportMapper.findBySearchCondition(searchCondition);
            logger.info("Mapper 호출 결과 - reportList: " + (reportList != null ? "NOT NULL, 크기: " + reportList.size() : "NULL"), "DCReport");
            
            List<ReportDDTO> resultList = new ArrayList<>();
            for (Report report : reportList) {
                ReportDDTO dto = convertToReportDDTO(report);
                resultList.add(dto);
            }
            
            logger.info("변환된 ReportDDTO 리스트: " + (resultList != null ? "NOT NULL, 크기: " + resultList.size() : "NULL"), "DCReport");
            logger.info("=== DCReport.getListReport END ===");
            return resultList;
            
        } catch (NewBusinessException e) {
            logger.error("DCReport.getListReport 중 비즈니스 오류: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getListReport END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCReport.getListReport 중 오류 발생: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getListReport END (ERROR) ===");
            throw new NewBusinessException("보고서 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 보고서 상세 조회
     */
    public ReportDDTO getReport(String reportId) throws NewBusinessException {
        logger.info("=== DCReport.getReport START ===");
        logger.info("입력 reportId: " + (reportId != null ? reportId : "NULL"), "DCReport");
        
        try {
            if (reportId == null || reportId.trim().isEmpty()) {
                logger.error("reportId가 NULL이거나 비어있습니다.", "DCReport");
                throw new NewBusinessException("reportId는 필수입니다.");
            }
            
            Report report = reportMapper.findByReportId(reportId);
            logger.info("Mapper 호출 결과 - report: " + (report != null ? "NOT NULL" : "NULL"), "DCReport");
            
            if (report == null) {
                logger.warn("보고서를 찾을 수 없습니다: " + reportId, "DCReport");
                throw new NewBusinessException("보고서를 찾을 수 없습니다: " + reportId);
            }
            
            ReportDDTO result = convertToReportDDTO(report);
            logger.info("변환된 ReportDDTO: " + (result != null ? "NOT NULL" : "NULL"), "DCReport");
            logger.info("=== DCReport.getReport END ===");
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("DCReport.getReport 중 비즈니스 오류: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getReport END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("DCReport.getReport 중 오류 발생: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getReport END (ERROR) ===");
            throw new NewBusinessException("보고서 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 계정 통계 조회
     */
    public ReportDDTO getAccountStatistics(ReportDDTO searchCondition) throws NewBusinessException {
        logger.info("=== DCReport.getAccountStatistics START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "DCReport");
        
        if (searchCondition != null) {
            logger.info("입력 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "DCReport");
        }
        
        try {
            Map<String, Object> statistics = reportMapper.getAccountStatistics(searchCondition);
            logger.info("Mapper 호출 결과 - statistics: " + (statistics != null ? "NOT NULL" : "NULL"), "DCReport");
            if (statistics != null) {
                logger.info("Mapper 호출 결과 - statistics.totalCount: " + (statistics.get("totalCount") != null ? statistics.get("totalCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.activeCount: " + (statistics.get("activeCount") != null ? statistics.get("activeCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.inactiveCount: " + (statistics.get("inactiveCount") != null ? statistics.get("inactiveCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.accountTypes: " + (statistics.get("accountTypes") != null ? "NOT NULL" : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.accountStatuses: " + (statistics.get("accountStatuses") != null ? "NOT NULL" : "NULL"), "DCReport");
            }
            
            // 프론트엔드에서 기대하는 데이터 구조로 변환
            Map<String, Object> summaryData = new HashMap<>();
            if (statistics != null) {
                summaryData.put("totalCount", statistics.get("totalCount"));
                summaryData.put("activeCount", statistics.get("activeCount"));
                summaryData.put("inactiveCount", statistics.get("inactiveCount"));
                summaryData.put("typeStatistics", statistics.get("accountTypes"));
                summaryData.put("statusStatistics", statistics.get("accountStatuses"));
            }
            
            ReportDDTO result = new ReportDDTO("ACCOUNT");
            result.setReportName("계정 통계 보고서");
            result.setReportDescription("계정 관련 통계 정보");
            result.setSummaryData(summaryData);
            
            // 통계 데이터 설정
            if (statistics != null) {
                result.setTotalCount((Long) statistics.get("totalCount"));
                result.setActiveCount((Long) statistics.get("activeCount"));
                result.setInactiveCount((Long) statistics.get("inactiveCount"));
            }
            
            logger.info("생성된 ReportDDTO: " + (result != null ? "NOT NULL" : "NULL"), "DCReport");
            if (result != null) {
                logger.info("생성된 ReportDDTO - reportId: " + (result.getReportId() != null ? result.getReportId() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - reportType: " + (result.getReportType() != null ? result.getReportType() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - reportName: " + (result.getReportName() != null ? result.getReportName() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - totalCount: " + (result.getTotalCount() != null ? result.getTotalCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - activeCount: " + (result.getActiveCount() != null ? result.getActiveCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - inactiveCount: " + (result.getInactiveCount() != null ? result.getInactiveCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - summaryData: " + (result.getSummaryData() != null ? "NOT NULL" : "NULL"), "DCReport");
            }
            logger.info("=== DCReport.getAccountStatistics END ===");
            return result;
            
        } catch (Exception e) {
            logger.error("DCReport.getAccountStatistics 중 오류 발생: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getAccountStatistics END (ERROR) ===");
            throw new NewBusinessException("계정 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 통계 조회
     */
    public ReportDDTO getUserStatistics(ReportDDTO searchCondition) throws NewBusinessException {
        logger.info("=== DCReport.getUserStatistics START ===");
        logger.info("입력 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "DCReport");
        
        if (searchCondition != null) {
            logger.info("입력 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"), "DCReport");
            logger.info("입력 ReportDDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"), "DCReport");
        }
        
        try {
            Map<String, Object> statistics = reportMapper.getUserStatistics(searchCondition);
            logger.info("Mapper 호출 결과 - statistics: " + (statistics != null ? "NOT NULL" : "NULL"), "DCReport");
            if (statistics != null) {
                logger.info("Mapper 호출 결과 - statistics.totalCount: " + (statistics.get("totalCount") != null ? statistics.get("totalCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.activeCount: " + (statistics.get("activeCount") != null ? statistics.get("activeCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.inactiveCount: " + (statistics.get("inactiveCount") != null ? statistics.get("inactiveCount") : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.userRoles: " + (statistics.get("userRoles") != null ? "NOT NULL" : "NULL"), "DCReport");
                logger.info("Mapper 호출 결과 - statistics.userStatuses: " + (statistics.get("userStatuses") != null ? "NOT NULL" : "NULL"), "DCReport");
            }
            
            // 프론트엔드에서 기대하는 데이터 구조로 변환
            Map<String, Object> summaryData = new HashMap<>();
            if (statistics != null) {
                summaryData.put("totalCount", statistics.get("totalCount"));
                summaryData.put("activeCount", statistics.get("activeCount"));
                summaryData.put("inactiveCount", statistics.get("inactiveCount"));
                summaryData.put("roleStatistics", statistics.get("userRoles"));
                summaryData.put("statusStatistics", statistics.get("userStatuses"));
            }
            
            ReportDDTO result = new ReportDDTO("USER");
            result.setReportName("사용자 통계 보고서");
            result.setReportDescription("사용자 관련 통계 정보");
            result.setSummaryData(summaryData);
            
            // 통계 데이터 설정
            if (statistics != null) {
                result.setTotalCount((Long) statistics.get("totalCount"));
                result.setActiveCount((Long) statistics.get("activeCount"));
                result.setInactiveCount((Long) statistics.get("inactiveCount"));
            }
            
            logger.info("생성된 ReportDDTO: " + (result != null ? "NOT NULL" : "NULL"), "DCReport");
            if (result != null) {
                logger.info("생성된 ReportDDTO - reportId: " + (result.getReportId() != null ? result.getReportId() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - reportType: " + (result.getReportType() != null ? result.getReportType() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - reportName: " + (result.getReportName() != null ? result.getReportName() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - totalCount: " + (result.getTotalCount() != null ? result.getTotalCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - activeCount: " + (result.getActiveCount() != null ? result.getActiveCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - inactiveCount: " + (result.getInactiveCount() != null ? result.getInactiveCount() : "NULL"), "DCReport");
                logger.info("생성된 ReportDDTO - summaryData: " + (result.getSummaryData() != null ? "NOT NULL" : "NULL"), "DCReport");
            }
            logger.info("=== DCReport.getUserStatistics END ===");
            return result;
            
        } catch (Exception e) {
            logger.error("DCReport.getUserStatistics 중 오류 발생: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.getUserStatistics END (ERROR) ===");
            throw new NewBusinessException("사용자 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Report 엔티티를 ReportDDTO로 변환
     */
    private ReportDDTO convertToReportDDTO(Report report) {
        logger.info("=== DCReport.convertToReportDDTO START ===");
        logger.info("입력 Report 엔티티: " + (report != null ? "NOT NULL" : "NULL"), "DCReport");
        
        if (report == null) {
            logger.warn("입력 Report 엔티티가 NULL입니다.", "DCReport");
            logger.info("=== DCReport.convertToReportDDTO END (NULL_INPUT) ===");
            return null;
        }
        
        try {
            ReportDDTO dto = new ReportDDTO();
            dto.setReportId(report.getReportId());
            dto.setReportType(report.getReportType());
            dto.setReportName(report.getReportName());
            dto.setReportDescription(report.getReportDescription());
            dto.setReportStatus(report.getReportStatus());
            dto.setCreatedBy(report.getCreatedBy());
            dto.setCreatedDate(report.getCreatedDate());
            dto.setUpdatedBy(report.getUpdatedBy());
            dto.setUpdatedDate(report.getUpdatedDate());
            dto.setReportConfig(report.getReportConfig());
            dto.setReportTemplate(report.getReportTemplate());
            
            logger.info("변환된 ReportDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "DCReport");
            logger.info("=== DCReport.convertToReportDDTO END ===");
            return dto;
            
        } catch (Exception e) {
            logger.error("ReportDDTO 변환 중 오류 발생: " + e.getMessage(), "DCReport");
            logger.info("=== DCReport.convertToReportDDTO END (ERROR) ===");
            return null;
        }
    }
} 