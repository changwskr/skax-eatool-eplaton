package com.skax.eatool.mbc.pc.reportpc;

import com.skax.eatool.mbc.dc.reportdc.DCReport;
import com.skax.eatool.mbc.dc.reportdc.dto.ReportDDTO;
import com.skax.eatool.mbc.pc.dto.ReportPDTO;
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
 * 보고서 Process Component
 * 
 * 프로그램명: PCReport.java
 * 설명: 보고서 관련 프로세스 로직을 담당하는 Process Component
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - ReportPDTO와 ReportDDTO 간 변환
 * - 보고서 관련 비즈니스 로직 처리
 * - DC 레이어 호출
 * 
 * @version 1.0
 */
@Component
public class PCReport {
    
    private static final Logger logger = LoggerFactory.getLogger(PCReport.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private DCReport dcReport;
    
    /**
     * 보고서 목록 조회
     */
    public List<ReportPDTO> getListReport(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== PCReport.getListReport START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"), "PCReport");
        
        try {
            if (reportPDTO == null) {
                logger.error("ReportPDTO가 NULL입니다.", "PCReport");
                throw new NewBusinessException("ReportPDTO는 필수입니다.");
            }
            
            ReportDDTO searchCondition = convertToReportDDTO(reportPDTO);
            logger.info("변환된 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "PCReport");
            
            List<ReportDDTO> reportDDTOList = dcReport.getListReport(searchCondition);
            logger.info("DC 호출 결과 - reportDDTOList: " + (reportDDTOList != null ? "NOT NULL, 크기: " + reportDDTOList.size() : "NULL"), "PCReport");
            
            List<ReportPDTO> resultList = new ArrayList<>();
            for (ReportDDTO dto : reportDDTOList) {
                ReportPDTO pDto = convertToReportPDTO(dto);
                resultList.add(pDto);
            }
            
            logger.info("최종 결과 - resultList: " + (resultList != null ? "NOT NULL, 크기: " + resultList.size() : "NULL"), "PCReport");
            logger.info("=== PCReport.getListReport END ===");
            return resultList;
            
        } catch (NewBusinessException e) {
            logger.error("PCReport.getListReport 중 비즈니스 오류: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getListReport END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("PCReport.getListReport 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getListReport END (ERROR) ===");
            throw new NewBusinessException("보고서 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 보고서 상세 조회
     */
    public ReportPDTO getReport(String reportId) throws NewBusinessException {
        logger.info("=== PCReport.getReport START ===");
        logger.info("입력 reportId: " + (reportId != null ? reportId : "NULL"), "PCReport");
        
        try {
            if (reportId == null || reportId.trim().isEmpty()) {
                logger.error("reportId가 NULL이거나 비어있습니다.", "PCReport");
                throw new NewBusinessException("reportId는 필수입니다.");
            }
            
            ReportDDTO reportDDTO = dcReport.getReport(reportId);
            logger.info("DC 호출 결과 - reportDDTO: " + (reportDDTO != null ? "NOT NULL" : "NULL"), "PCReport");
            
            ReportPDTO result = convertToReportPDTO(reportDDTO);
            logger.info("변환된 ReportPDTO: " + (result != null ? "NOT NULL" : "NULL"), "PCReport");
            logger.info("=== PCReport.getReport END ===");
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("PCReport.getReport 중 비즈니스 오류: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getReport END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("PCReport.getReport 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getReport END (ERROR) ===");
            throw new NewBusinessException("보고서 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 계정 통계 조회
     */
    public ReportPDTO getAccountStatistics(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== PCReport.getAccountStatistics START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"), "PCReport");
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - page: " + (reportPDTO.getPage() != null ? reportPDTO.getPage() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - size: " + (reportPDTO.getSize() != null ? reportPDTO.getSize() : "NULL"), "PCReport");
        }
        
        try {
            ReportDDTO searchCondition = convertToReportDDTO(reportPDTO);
            logger.info("변환된 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "PCReport");
            if (searchCondition != null) {
                logger.info("변환된 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"), "PCReport");
                logger.info("변환된 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"), "PCReport");
                logger.info("변환된 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"), "PCReport");
            }
            
            ReportDDTO reportDDTO = dcReport.getAccountStatistics(searchCondition);
            logger.info("DC 호출 결과 - reportDDTO: " + (reportDDTO != null ? "NOT NULL" : "NULL"), "PCReport");
            if (reportDDTO != null) {
                logger.info("DC 호출 결과 - reportDDTO.reportId: " + (reportDDTO.getReportId() != null ? reportDDTO.getReportId() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.reportType: " + (reportDDTO.getReportType() != null ? reportDDTO.getReportType() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.reportName: " + (reportDDTO.getReportName() != null ? reportDDTO.getReportName() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.totalCount: " + (reportDDTO.getTotalCount() != null ? reportDDTO.getTotalCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.activeCount: " + (reportDDTO.getActiveCount() != null ? reportDDTO.getActiveCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.inactiveCount: " + (reportDDTO.getInactiveCount() != null ? reportDDTO.getInactiveCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.summaryData: " + (reportDDTO.getSummaryData() != null ? "NOT NULL" : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.chartData: " + (reportDDTO.getChartData() != null ? "NOT NULL" : "NULL"), "PCReport");
            }
            
            ReportPDTO result = convertToReportPDTO(reportDDTO);
            logger.info("변환된 ReportPDTO: " + (result != null ? "NOT NULL" : "NULL"), "PCReport");
            if (result != null) {
                logger.info("변환된 ReportPDTO - reportId: " + (result.getReportId() != null ? result.getReportId() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - reportType: " + (result.getReportType() != null ? result.getReportType() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - reportName: " + (result.getReportName() != null ? result.getReportName() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - totalCount: " + (result.getTotalCount() != null ? result.getTotalCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - activeCount: " + (result.getActiveCount() != null ? result.getActiveCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - inactiveCount: " + (result.getInactiveCount() != null ? result.getInactiveCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - summaryData: " + (result.getSummaryData() != null ? "NOT NULL" : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - chartData: " + (result.getChartData() != null ? "NOT NULL" : "NULL"), "PCReport");
            }
            logger.info("=== PCReport.getAccountStatistics END ===");
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("PCReport.getAccountStatistics 중 비즈니스 오류: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getAccountStatistics END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("PCReport.getAccountStatistics 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getAccountStatistics END (ERROR) ===");
            throw new NewBusinessException("계정 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 통계 조회
     */
    public ReportPDTO getUserStatistics(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== PCReport.getUserStatistics START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"), "PCReport");
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - page: " + (reportPDTO.getPage() != null ? reportPDTO.getPage() : "NULL"), "PCReport");
            logger.info("입력 ReportPDTO - size: " + (reportPDTO.getSize() != null ? reportPDTO.getSize() : "NULL"), "PCReport");
        }
        
        try {
            ReportDDTO searchCondition = convertToReportDDTO(reportPDTO);
            logger.info("변환된 ReportDDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"), "PCReport");
            if (searchCondition != null) {
                logger.info("변환된 ReportDDTO - reportId: " + (searchCondition.getReportId() != null ? searchCondition.getReportId() : "NULL"), "PCReport");
                logger.info("변환된 ReportDDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"), "PCReport");
                logger.info("변환된 ReportDDTO - reportName: " + (searchCondition.getReportName() != null ? searchCondition.getReportName() : "NULL"), "PCReport");
            }
            
            ReportDDTO reportDDTO = dcReport.getUserStatistics(searchCondition);
            logger.info("DC 호출 결과 - reportDDTO: " + (reportDDTO != null ? "NOT NULL" : "NULL"), "PCReport");
            if (reportDDTO != null) {
                logger.info("DC 호출 결과 - reportDDTO.reportId: " + (reportDDTO.getReportId() != null ? reportDDTO.getReportId() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.reportType: " + (reportDDTO.getReportType() != null ? reportDDTO.getReportType() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.reportName: " + (reportDDTO.getReportName() != null ? reportDDTO.getReportName() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.totalCount: " + (reportDDTO.getTotalCount() != null ? reportDDTO.getTotalCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.activeCount: " + (reportDDTO.getActiveCount() != null ? reportDDTO.getActiveCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.inactiveCount: " + (reportDDTO.getInactiveCount() != null ? reportDDTO.getInactiveCount() : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.summaryData: " + (reportDDTO.getSummaryData() != null ? "NOT NULL" : "NULL"), "PCReport");
                logger.info("DC 호출 결과 - reportDDTO.chartData: " + (reportDDTO.getChartData() != null ? "NOT NULL" : "NULL"), "PCReport");
            }
            
            ReportPDTO result = convertToReportPDTO(reportDDTO);
            logger.info("변환된 ReportPDTO: " + (result != null ? "NOT NULL" : "NULL"), "PCReport");
            if (result != null) {
                logger.info("변환된 ReportPDTO - reportId: " + (result.getReportId() != null ? result.getReportId() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - reportType: " + (result.getReportType() != null ? result.getReportType() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - reportName: " + (result.getReportName() != null ? result.getReportName() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - totalCount: " + (result.getTotalCount() != null ? result.getTotalCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - activeCount: " + (result.getActiveCount() != null ? result.getActiveCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - inactiveCount: " + (result.getInactiveCount() != null ? result.getInactiveCount() : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - summaryData: " + (result.getSummaryData() != null ? "NOT NULL" : "NULL"), "PCReport");
                logger.info("변환된 ReportPDTO - chartData: " + (result.getChartData() != null ? "NOT NULL" : "NULL"), "PCReport");
            }
            logger.info("=== PCReport.getUserStatistics END ===");
            return result;
            
        } catch (NewBusinessException e) {
            logger.error("PCReport.getUserStatistics 중 비즈니스 오류: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getUserStatistics END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("PCReport.getUserStatistics 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.getUserStatistics END (ERROR) ===");
            throw new NewBusinessException("사용자 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * ReportPDTO를 ReportDDTO로 변환
     */
    private ReportDDTO convertToReportDDTO(ReportPDTO pDto) {
        logger.info("=== PCReport.convertToReportDDTO START ===");
        logger.info("입력 ReportPDTO: " + (pDto != null ? "NOT NULL" : "NULL"), "PCReport");
        
        if (pDto == null) {
            logger.warn("입력 ReportPDTO가 NULL입니다.", "PCReport");
            logger.info("=== PCReport.convertToReportDDTO END (NULL_INPUT) ===");
            return null;
        }
        
        try {
            ReportDDTO dto = new ReportDDTO();
            dto.setReportId(pDto.getReportId());
            dto.setReportType(pDto.getReportType());
            dto.setReportName(pDto.getReportName());
            dto.setReportDescription(pDto.getReportDescription());
            dto.setPeriodType(pDto.getPeriodType());
            dto.setGroupBy(pDto.getGroupBy());
            dto.setPage(pDto.getPage());
            dto.setSize(pDto.getSize());
            dto.setSortBy(pDto.getSortBy());
            dto.setSortOrder(pDto.getSortOrder());
            dto.setReportStatus(pDto.getReportStatus());
            dto.setCreatedBy(pDto.getCreatedBy());
            dto.setUpdatedBy(pDto.getUpdatedBy());
            dto.setReportConfig(pDto.getReportConfig());
            dto.setReportTemplate(pDto.getReportTemplate());
            
            // 날짜 변환
            if (pDto.getStartDate() != null && !pDto.getStartDate().trim().isEmpty()) {
                try {
                    dto.setStartDate(dateFormat.parse(pDto.getStartDate()));
                } catch (Exception e) {
                    logger.warn("시작일 파싱 실패: " + pDto.getStartDate(), "PCReport");
                }
            }
            
            if (pDto.getEndDate() != null && !pDto.getEndDate().trim().isEmpty()) {
                try {
                    dto.setEndDate(dateFormat.parse(pDto.getEndDate()));
                } catch (Exception e) {
                    logger.warn("종료일 파싱 실패: " + pDto.getEndDate(), "PCReport");
                }
            }
            
            if (pDto.getCreatedDate() != null && !pDto.getCreatedDate().trim().isEmpty()) {
                try {
                    dto.setCreatedDate(dateFormat.parse(pDto.getCreatedDate()));
                } catch (Exception e) {
                    logger.warn("생성일 파싱 실패: " + pDto.getCreatedDate(), "PCReport");
                }
            }
            
            if (pDto.getUpdatedDate() != null && !pDto.getUpdatedDate().trim().isEmpty()) {
                try {
                    dto.setUpdatedDate(dateFormat.parse(pDto.getUpdatedDate()));
                } catch (Exception e) {
                    logger.warn("수정일 파싱 실패: " + pDto.getUpdatedDate(), "PCReport");
                }
            }
            
            logger.info("변환된 ReportDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "PCReport");
            logger.info("=== PCReport.convertToReportDDTO END ===");
            return dto;
            
        } catch (Exception e) {
            logger.error("ReportDDTO 변환 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.convertToReportDDTO END (ERROR) ===");
            return null;
        }
    }
    
    /**
     * ReportDDTO를 ReportPDTO로 변환
     */
    private ReportPDTO convertToReportPDTO(ReportDDTO dto) {
        logger.info("=== PCReport.convertToReportPDTO START ===");
        logger.info("입력 ReportDDTO: " + (dto != null ? "NOT NULL" : "NULL"), "PCReport");
        
        if (dto == null) {
            logger.warn("입력 ReportDDTO가 NULL입니다.", "PCReport");
            logger.info("=== PCReport.convertToReportPDTO END (NULL_INPUT) ===");
            return null;
        }
        
        try {
            ReportPDTO pDto = new ReportPDTO();
            pDto.setReportId(dto.getReportId());
            pDto.setReportType(dto.getReportType());
            pDto.setReportName(dto.getReportName());
            pDto.setReportDescription(dto.getReportDescription());
            pDto.setPeriodType(dto.getPeriodType());
            pDto.setGroupBy(dto.getGroupBy());
            pDto.setPage(dto.getPage());
            pDto.setSize(dto.getSize());
            pDto.setSortBy(dto.getSortBy());
            pDto.setSortOrder(dto.getSortOrder());
            pDto.setReportData(dto.getReportData());
            pDto.setSummaryData(dto.getSummaryData());
            pDto.setChartData(dto.getChartData());
            pDto.setTotalCount(dto.getTotalCount());
            pDto.setActiveCount(dto.getActiveCount());
            pDto.setInactiveCount(dto.getInactiveCount());
            pDto.setAverageValue(dto.getAverageValue());
            pDto.setMaxValue(dto.getMaxValue());
            pDto.setMinValue(dto.getMinValue());
            pDto.setReportStatus(dto.getReportStatus());
            pDto.setCreatedBy(dto.getCreatedBy());
            pDto.setUpdatedBy(dto.getUpdatedBy());
            pDto.setReportConfig(dto.getReportConfig());
            pDto.setReportTemplate(dto.getReportTemplate());
            
            // 날짜 변환
            if (dto.getStartDate() != null) {
                pDto.setStartDate(dateFormat.format(dto.getStartDate()));
            }
            
            if (dto.getEndDate() != null) {
                pDto.setEndDate(dateFormat.format(dto.getEndDate()));
            }
            
            if (dto.getCreatedDate() != null) {
                pDto.setCreatedDate(dateFormat.format(dto.getCreatedDate()));
            }
            
            if (dto.getUpdatedDate() != null) {
                pDto.setUpdatedDate(dateFormat.format(dto.getUpdatedDate()));
            }
            
            logger.info("변환된 ReportPDTO: " + (pDto != null ? "NOT NULL" : "NULL"), "PCReport");
            logger.info("=== PCReport.convertToReportPDTO END ===");
            return pDto;
            
        } catch (Exception e) {
            logger.error("ReportPDTO 변환 중 오류 발생: " + e.getMessage(), "PCReport");
            logger.info("=== PCReport.convertToReportPDTO END (ERROR) ===");
            return null;
        }
    }
} 