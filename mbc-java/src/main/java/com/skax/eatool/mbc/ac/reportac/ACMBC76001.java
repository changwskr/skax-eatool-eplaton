package com.skax.eatool.mbc.ac.reportac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.mbc.as.reportas.ASMBC76001;
import com.skax.eatool.mbc.pc.dto.ReportPDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 보고서 Application Control
 * 
 * 프로그램명: ACMBC76001.java
 * 설명: 보고서 관련 REST API 엔드포인트를 제공하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 보고서 목록 조회 API
 * - 보고서 상세 조회 API
 * - 계정 통계 조회 API
 * - 사용자 통계 조회 API
 * 
 * @version 1.0
 */
@RestController
@RequestMapping({ "/api/report", "/mbc/report" })
@Tag(name = "보고서 관리", description = "보고서 관련 API")
public class ACMBC76001 {
    
    private static final Logger logger = LoggerFactory.getLogger(ACMBC76001.class);
    
    @Autowired
    private ASMBC76001 asMbc76001;
    
    /**
     * 보고서 목록 조회 API
     */
    @GetMapping("/list")
    @Operation(summary = "보고서 목록 조회", description = "보고서 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getReportList(
            @Parameter(description = "보고서 타입") @RequestParam(required = false) String reportType,
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") Integer size) {
        
        logger.info("=== ACMBC76001.getReportList START ===");
        logger.info("입력 파라미터 - reportType: " + (reportType != null ? reportType : "NULL"));
        logger.info("입력 파라미터 - page: " + (page != null ? page : "NULL"));
        logger.info("입력 파라미터 - size: " + (size != null ? size : "NULL"));
        
        try {
            // NULL 체크 및 기본값 설정
            if (page == null) page = 1;
            if (size == null) size = 10;
            
            // 검색 조건 설정
            ReportPDTO searchCondition = new ReportPDTO();
            searchCondition.setReportType(reportType);
            searchCondition.setPage(page);
            searchCondition.setSize(size);
            
            logger.info("생성된 ReportPDTO - reportType: " + (searchCondition.getReportType() != null ? searchCondition.getReportType() : "NULL"));
            logger.info("생성된 ReportPDTO - page: " + (searchCondition.getPage() != null ? searchCondition.getPage() : "NULL"));
            logger.info("생성된 ReportPDTO - size: " + (searchCondition.getSize() != null ? searchCondition.getSize() : "NULL"));
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "LIST");
            reqData.getInputGenericDto().put("ReportPDTO", searchCondition);
            
            logger.info("AS 호출 전 NewKBData - command: LIST");
            logger.info("AS 호출 전 NewKBData - ReportPDTO: " + (searchCondition != null ? "NOT NULL" : "NULL"));
            
            NewKBData resultData = asMbc76001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"));
            
            // 결과 추출
            @SuppressWarnings("unchecked")
            List<ReportPDTO> reportList = (List<ReportPDTO>) resultData.getOutputGenericDto().get("ReportPDTOList");
            
            logger.info("추출된 ReportPDTOList: " + (reportList != null ? "NOT NULL, 크기: " + reportList.size() : "NULL"));
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "보고서 목록 조회가 완료되었습니다.");
            response.put("data", reportList);
            response.put("totalCount", reportList != null ? reportList.size() : 0);
            response.put("page", page);
            response.put("size", size);
            
            logger.info("응답 데이터 - success: true");
            logger.info("응답 데이터 - message: 보고서 목록 조회가 완료되었습니다.");
            logger.info("응답 데이터 - totalCount: " + (reportList != null ? reportList.size() : 0));
            logger.info("응답 데이터 - page: " + page);
            logger.info("응답 데이터 - size: " + size);
            logger.info("조회된 보고서 수: " + (reportList != null ? reportList.size() : 0));
            logger.info("=== ACMBC76001.getReportList END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("보고서 목록 조회 중 오류 발생: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "보고서 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getReportList END (ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * 보고서 상세 조회 API
     */
    @GetMapping("/detail/{reportId}")
    @Operation(summary = "보고서 상세 조회", description = "특정 보고서의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "보고서를 찾을 수 없음", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getReportDetail(
            @Parameter(description = "보고서 ID", required = true) @PathVariable String reportId) {
        
        logger.info("=== ACMBC76001.getReportDetail START ===");
        logger.info("입력 파라미터 - reportId: " + (reportId != null ? reportId : "NULL"));
        
        try {
            // NULL 체크
            if (reportId == null || reportId.trim().isEmpty()) {
                logger.error("보고서 ID가 NULL이거나 비어있습니다.");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "보고서 ID는 필수입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "READ");
            
            ReportPDTO reportPDTO = new ReportPDTO();
            reportPDTO.setReportId(reportId);
            reqData.getInputGenericDto().put("ReportPDTO", reportPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: READ");
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
            
            NewKBData resultData = asMbc76001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"));
            
            // 결과 추출
            ReportPDTO report = (ReportPDTO) resultData.getOutputGenericDto().get("ReportPDTO");
            
            logger.info("추출된 ReportPDTO: " + (report != null ? "NOT NULL" : "NULL"));
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "보고서 상세 조회가 완료되었습니다.");
            response.put("data", report);
            
            logger.info("응답 데이터 - success: true");
            logger.info("응답 데이터 - message: 보고서 상세 조회가 완료되었습니다.");
            logger.info("보고서 상세 조회 완료: " + reportId);
            logger.info("=== ACMBC76001.getReportDetail END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("보고서 상세 조회 중 비즈니스 오류: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "보고서 상세 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getReportDetail END (BUSINESS_ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("보고서 상세 조회 중 오류 발생: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "보고서 상세 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getReportDetail END (ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * 계정 통계 조회 API
     */
    @GetMapping("/account/statistics")
    @Operation(summary = "계정 통계 조회", description = "계정 관련 통계 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getAccountStatistics() {
        
        logger.info("=== ACMBC76001.getAccountStatistics START ===");
        logger.info("입력 파라미터: 없음 (GET 요청)");
        
        try {
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "ACCOUNT_STATS");
            
            ReportPDTO reportPDTO = new ReportPDTO();
            reportPDTO.setReportType("ACCOUNT");
            reqData.getInputGenericDto().put("ReportPDTO", reportPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: ACCOUNT_STATS");
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"));
            
            NewKBData resultData = asMbc76001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"));
            
            // 결과 추출
            ReportPDTO statistics = (ReportPDTO) resultData.getOutputGenericDto().get("ReportPDTO");
            
            logger.info("추출된 ReportPDTO: " + (statistics != null ? "NOT NULL" : "NULL"));
            if (statistics != null) {
                logger.info("추출된 ReportPDTO - reportId: " + (statistics.getReportId() != null ? statistics.getReportId() : "NULL"));
                logger.info("추출된 ReportPDTO - reportType: " + (statistics.getReportType() != null ? statistics.getReportType() : "NULL"));
                logger.info("추출된 ReportPDTO - reportName: " + (statistics.getReportName() != null ? statistics.getReportName() : "NULL"));
                logger.info("추출된 ReportPDTO - totalCount: " + (statistics.getTotalCount() != null ? statistics.getTotalCount() : "NULL"));
                logger.info("추출된 ReportPDTO - activeCount: " + (statistics.getActiveCount() != null ? statistics.getActiveCount() : "NULL"));
                logger.info("추출된 ReportPDTO - inactiveCount: " + (statistics.getInactiveCount() != null ? statistics.getInactiveCount() : "NULL"));
                logger.info("추출된 ReportPDTO - summaryData: " + (statistics.getSummaryData() != null ? "NOT NULL" : "NULL"));
                logger.info("추출된 ReportPDTO - chartData: " + (statistics.getChartData() != null ? "NOT NULL" : "NULL"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 통계 조회가 완료되었습니다.");
            response.put("data", statistics);
            
            logger.info("응답 데이터 - success: true");
            logger.info("응답 데이터 - message: 계정 통계 조회가 완료되었습니다.");
            logger.info("응답 데이터 - data: " + (statistics != null ? "NOT NULL" : "NULL"));
            logger.info("계정 통계 조회 완료");
            logger.info("=== ACMBC76001.getAccountStatistics END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("계정 통계 조회 중 비즈니스 오류: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "계정 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getAccountStatistics END (BUSINESS_ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("계정 통계 조회 중 오류 발생: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "계정 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getAccountStatistics END (ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * 사용자 통계 조회 API
     */
    @GetMapping("/user/statistics")
    @Operation(summary = "사용자 통계 조회", description = "사용자 관련 통계 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        
        logger.info("=== ACMBC76001.getUserStatistics START ===");
        logger.info("입력 파라미터: 없음 (GET 요청)");
        
        try {
            // AS 호출
            NewKBData reqData = new NewKBData();
            reqData.getInputGenericDto().put("command", "USER_STATS");
            
            ReportPDTO reportPDTO = new ReportPDTO();
            reportPDTO.setReportType("USER");
            reqData.getInputGenericDto().put("ReportPDTO", reportPDTO);
            
            logger.info("AS 호출 전 NewKBData - command: USER_STATS");
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportType: " + (reportPDTO.getReportType() != null ? reportPDTO.getReportType() : "NULL"));
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportId: " + (reportPDTO.getReportId() != null ? reportPDTO.getReportId() : "NULL"));
            logger.info("AS 호출 전 NewKBData - ReportPDTO.reportName: " + (reportPDTO.getReportName() != null ? reportPDTO.getReportName() : "NULL"));
            
            NewKBData resultData = asMbc76001.execute(reqData);
            
            logger.info("AS 호출 후 NewKBData: " + (resultData != null ? "NOT NULL" : "NULL"));
            
            // 결과 추출
            ReportPDTO statistics = (ReportPDTO) resultData.getOutputGenericDto().get("ReportPDTO");
            
            logger.info("추출된 ReportPDTO: " + (statistics != null ? "NOT NULL" : "NULL"));
            if (statistics != null) {
                logger.info("추출된 ReportPDTO - reportId: " + (statistics.getReportId() != null ? statistics.getReportId() : "NULL"));
                logger.info("추출된 ReportPDTO - reportType: " + (statistics.getReportType() != null ? statistics.getReportType() : "NULL"));
                logger.info("추출된 ReportPDTO - reportName: " + (statistics.getReportName() != null ? statistics.getReportName() : "NULL"));
                logger.info("추출된 ReportPDTO - totalCount: " + (statistics.getTotalCount() != null ? statistics.getTotalCount() : "NULL"));
                logger.info("추출된 ReportPDTO - activeCount: " + (statistics.getActiveCount() != null ? statistics.getActiveCount() : "NULL"));
                logger.info("추출된 ReportPDTO - inactiveCount: " + (statistics.getInactiveCount() != null ? statistics.getInactiveCount() : "NULL"));
                logger.info("추출된 ReportPDTO - summaryData: " + (statistics.getSummaryData() != null ? "NOT NULL" : "NULL"));
                logger.info("추출된 ReportPDTO - chartData: " + (statistics.getChartData() != null ? "NOT NULL" : "NULL"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 통계 조회가 완료되었습니다.");
            response.put("data", statistics);
            
            logger.info("응답 데이터 - success: true");
            logger.info("응답 데이터 - message: 사용자 통계 조회가 완료되었습니다.");
            logger.info("응답 데이터 - data: " + (statistics != null ? "NOT NULL" : "NULL"));
            logger.info("사용자 통계 조회 완료");
            logger.info("=== ACMBC76001.getUserStatistics END ===");
            
            return ResponseEntity.ok(response);
            
        } catch (NewBusinessException e) {
            logger.error("사용자 통계 조회 중 비즈니스 오류: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getUserStatistics END (BUSINESS_ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
            
        } catch (Exception e) {
            logger.error("사용자 통계 조회 중 오류 발생: " + e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자 통계 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            logger.info("오류 응답 데이터 - success: false");
            logger.info("오류 응답 데이터 - message: " + e.getMessage());
            logger.info("=== ACMBC76001.getUserStatistics END (ERROR) ===");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
} 