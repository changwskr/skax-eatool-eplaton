package com.skax.eatool.mbc.as.reportas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.mbc.pc.dto.ReportPDTO;
import com.skax.eatool.mbc.pc.reportpc.PCReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 보고서 Application Service
 * 
 * 프로그램명: ASMBC76001.java
 * 설명: 보고서 관련 애플리케이션 서비스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 목록 조회
 * - 보고서 상세 조회
 * - 계정 통계 조회
 * - 사용자 통계 조회
 * 
 * @version 1.0
 */
@Service
public class ASMBC76001 {
    
    private static final Logger logger = LoggerFactory.getLogger(ASMBC76001.class);
    
    @Autowired
    private PCReport pcReport;
    
    /**
     * 애플리케이션 서비스 실행
     */
    public NewKBData execute(NewKBData reqData) throws NewBusinessException {
        logger.info("=== ASMBC76001.execute START ===");
        logger.info("입력 NewKBData: " + (reqData != null ? "NOT NULL" : "NULL"));
        
        try {
            // NULL 체크
            if (reqData == null) {
                logger.error("NewKBData가 NULL입니다.");
                throw new NewBusinessException("NewKBData는 필수입니다.");
            }
            
            if (reqData.getInputGenericDto() == null) {
                logger.error("InputGenericDto가 NULL입니다.");
                throw new NewBusinessException("InputGenericDto는 필수입니다.");
            }
            
            // 입력 데이터에서 command와 ReportPDTO 추출
            String command = reqData.getInputGenericDto().getString("command");
            ReportPDTO reportPDTO = (ReportPDTO) reqData.getInputGenericDto().get("ReportPDTO");
            
            logger.info("추출된 command: " + (command != null ? command : "NULL"));
            logger.info("추출된 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"));
            
            if (reportPDTO != null) {
                logger.info("추출된 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
                logger.info("추출된 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
                logger.info("추출된 ReportPDTO - reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"));
            }
            
            // command에 따른 처리 분기
            NewKBData resultData = new NewKBData();
            
            switch (command) {
                case "LIST":
                    resultData = handleList(reportPDTO);
                    break;
                case "READ":
                    resultData = handleRead(reportPDTO);
                    break;
                case "ACCOUNT_STATS":
                    resultData = handleAccountStatistics(reportPDTO);
                    break;
                case "USER_STATS":
                    resultData = handleUserStatistics(reportPDTO);
                    break;
                default:
                    throw new NewBusinessException("지원하지 않는 명령입니다: " + command);
            }
            
            logger.info("처리 완료: " + command);
            logger.info("결과 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"));
            logger.info("=== ASMBC76001.execute END ===");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC76001.execute 중 비즈니스 오류: " + e.getMessage());
            logger.info("=== ASMBC76001.execute END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC76001.execute 중 오류 발생: " + e.getMessage());
            logger.info("=== ASMBC76001.execute END (ERROR) ===");
            throw new NewBusinessException("애플리케이션 서비스 실행 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 보고서 목록 조회 처리
     */
    private NewKBData handleList(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== ASMBC76001.handleList START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"));
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
            logger.info("입력 ReportPDTO - page: " + (reportPDTO.getPage() != null ? reportPDTO.getPage() : "NULL"));
            logger.info("입력 ReportPDTO - size: " + (reportPDTO.getSize() != null ? reportPDTO.getSize() : "NULL"));
        }
        
        try {
            // NULL 체크
            if (reportPDTO == null) {
                logger.error("ReportPDTO가 NULL입니다.");
                throw new NewBusinessException("ReportPDTO는 필수입니다.");
            }
            
            // PC 호출
            List<ReportPDTO> reportList = pcReport.getListReport(reportPDTO);
            
            logger.info("PC 호출 결과 - reportList: " + (reportList != null ? "NOT NULL, 크기: " + reportList.size() : "NULL"));
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("ReportPDTOList", reportList);
            
            logger.info("결과 NewKBData 생성 완료");
            logger.info("=== ASMBC76001.handleList END ===");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC76001.handleList 중 비즈니스 오류: " + e.getMessage());
            logger.info("=== ASMBC76001.handleList END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC76001.handleList 중 오류 발생: " + e.getMessage());
            logger.info("=== ASMBC76001.handleList END (ERROR) ===");
            throw new NewBusinessException("보고서 목록 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 보고서 상세 조회 처리
     */
    private NewKBData handleRead(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== ASMBC76001.handleRead START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"));
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
        }
        
        try {
            // NULL 체크
            if (reportPDTO == null) {
                logger.error("ReportPDTO가 NULL입니다.");
                throw new NewBusinessException("ReportPDTO는 필수입니다.");
            }
            
            if (reportPDTO.getReportId() == null || reportPDTO.getReportId().trim().isEmpty()) {
                logger.error("보고서 ID가 NULL이거나 비어있습니다.");
                throw new NewBusinessException("보고서 ID는 필수입니다.");
            }
            
            // PC 호출
            ReportPDTO report = pcReport.getReport(reportPDTO.getReportId());
            
            logger.info("PC 호출 결과 - report: " + (report != null ? "NOT NULL" : "NULL"));
            
            if (report != null) {
                logger.info("PC 호출 결과 - report.reportId: " + (report.getReportId() != null ? report.getReportId() : "NULL"));
                logger.info("PC 호출 결과 - report.reportName: " + (report.getReportName() != null ? report.getReportName() : "NULL"));
            }
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("ReportPDTO", report);
            
            logger.info("결과 NewKBData 생성 완료");
            logger.info("=== ASMBC76001.handleRead END ===");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC76001.handleRead 중 비즈니스 오류: " + e.getMessage());
            logger.info("=== ASMBC76001.handleRead END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC76001.handleRead 중 오류 발생: " + e.getMessage());
            logger.info("=== ASMBC76001.handleRead END (ERROR) ===");
            throw new NewBusinessException("보고서 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 계정 통계 조회 처리
     */
    private NewKBData handleAccountStatistics(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== ASMBC76001.handleAccountStatistics START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"));
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
            logger.info("입력 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
            logger.info("입력 ReportPDTO - reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"));
            logger.info("입력 ReportPDTO - page: " + (reportPDTO.getPage() != null ? reportPDTO.getPage() : "NULL"));
            logger.info("입력 ReportPDTO - size: " + (reportPDTO.getSize() != null ? reportPDTO.getSize() : "NULL"));
        }
        
        try {
            // PC 호출
            ReportPDTO statistics = pcReport.getAccountStatistics(reportPDTO);
            
            logger.info("PC 호출 결과 - statistics: " + (statistics != null ? "NOT NULL" : "NULL"));
            if (statistics != null) {
                logger.info("PC 호출 결과 - statistics.reportId: " + (statistics.getReportId() != null ? statistics.getReportId() : "NULL"));
                logger.info("PC 호출 결과 - statistics.reportType: " + (statistics.getReportType() != null ? statistics.getReportType() : "NULL"));
                logger.info("PC 호출 결과 - statistics.reportName: " + (statistics.getReportName() != null ? statistics.getReportName() : "NULL"));
                logger.info("PC 호출 결과 - statistics.totalCount: " + (statistics.getTotalCount() != null ? statistics.getTotalCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.activeCount: " + (statistics.getActiveCount() != null ? statistics.getActiveCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.inactiveCount: " + (statistics.getInactiveCount() != null ? statistics.getInactiveCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.summaryData: " + (statistics.getSummaryData() != null ? "NOT NULL" : "NULL"));
                logger.info("PC 호출 결과 - statistics.chartData: " + (statistics.getChartData() != null ? "NOT NULL" : "NULL"));
            }
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("ReportPDTO", statistics);
            
            logger.info("결과 NewKBData 생성 완료");
            logger.info("=== ASMBC76001.handleAccountStatistics END ===");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC76001.handleAccountStatistics 중 비즈니스 오류: " + e.getMessage());
            logger.info("=== ASMBC76001.handleAccountStatistics END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC76001.handleAccountStatistics 중 오류 발생: " + e.getMessage());
            logger.info("=== ASMBC76001.handleAccountStatistics END (ERROR) ===");
            throw new NewBusinessException("계정 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 사용자 통계 조회 처리
     */
    private NewKBData handleUserStatistics(ReportPDTO reportPDTO) throws NewBusinessException {
        logger.info("=== ASMBC76001.handleUserStatistics START ===");
        logger.info("입력 ReportPDTO: " + (reportPDTO != null ? "NOT NULL" : "NULL"));
        
        if (reportPDTO != null) {
            logger.info("입력 ReportPDTO - reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
            logger.info("입력 ReportPDTO - reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
            logger.info("입력 ReportPDTO - reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"));
            logger.info("입력 ReportPDTO - page: " + (reportPDTO.getPage() != null ? reportPDTO.getPage() : "NULL"));
            logger.info("입력 ReportPDTO - size: " + (reportPDTO.getSize() != null ? reportPDTO.getSize() : "NULL"));
        }
        
        try {
            // PC 호출
            ReportPDTO statistics = pcReport.getUserStatistics(reportPDTO);
            
            logger.info("PC 호출 결과 - statistics: " + (statistics != null ? "NOT NULL" : "NULL"));
            if (statistics != null) {
                logger.info("PC 호출 결과 - statistics.reportId: " + (statistics.getReportId() != null ? statistics.getReportId() : "NULL"));
                logger.info("PC 호출 결과 - statistics.reportType: " + (statistics.getReportType() != null ? statistics.getReportType() : "NULL"));
                logger.info("PC 호출 결과 - statistics.reportName: " + (statistics.getReportName() != null ? statistics.getReportName() : "NULL"));
                logger.info("PC 호출 결과 - statistics.totalCount: " + (statistics.getTotalCount() != null ? statistics.getTotalCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.activeCount: " + (statistics.getActiveCount() != null ? statistics.getActiveCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.inactiveCount: " + (statistics.getInactiveCount() != null ? statistics.getInactiveCount() : "NULL"));
                logger.info("PC 호출 결과 - statistics.summaryData: " + (statistics.getSummaryData() != null ? "NOT NULL" : "NULL"));
                logger.info("PC 호출 결과 - statistics.chartData: " + (statistics.getChartData() != null ? "NOT NULL" : "NULL"));
            }
            
            // 결과를 NewKBData에 저장
            NewKBData resultData = new NewKBData();
            resultData.getOutputGenericDto().put("ReportPDTO", statistics);
            
            logger.info("결과 NewKBData 생성 완료");
            logger.info("=== ASMBC76001.handleUserStatistics END ===");
            
            return resultData;
            
        } catch (NewBusinessException e) {
            logger.error("ASMBC76001.handleUserStatistics 중 비즈니스 오류: " + e.getMessage());
            logger.info("=== ASMBC76001.handleUserStatistics END (BUSINESS_ERROR) ===");
            throw e;
        } catch (Exception e) {
            logger.error("ASMBC76001.handleUserStatistics 중 오류 발생: " + e.getMessage());
            logger.info("=== ASMBC76001.handleUserStatistics END (ERROR) ===");
            throw new NewBusinessException("사용자 통계 조회 중 오류가 발생했습니다.", e);
        }
    }
} 