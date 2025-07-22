package com.skax.eatool.mbc.ac.accountac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.accountas.ASMBC74001;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 계정 상세 조회 Application Control
 * 
 * 프로그램명: ACMBC72001.java
 * 설명: 계정 상세 조회 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 상세 조회 요청 처리 (GET)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/account/detail")
@Tag(name = "계정 관리", description = "계정 상세 조회 관련 API")
public class ACMBC72001 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC72001.class);

    @Autowired
    private ASMBC74001 asMbc74001;

    /**
     * 계정 상세 조회 처리 (GET)
     * 
     * @param accountId 계정 번호 (Path Variable)
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> getAccountDetail(
            @PathVariable("accountId") String accountId) throws NewBusinessException {
        logger.info("=== ACMBC72001.getAccountDetail START ===");
        logger.info("=== ACMBC72001.getAccountDetail - Input: accountId=" + accountId + " ===");

        try {
            // 1. 입력 데이터 검증
            validateInputData(accountId);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            
            // AccountPDTO 생성 및 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountNumber(accountId);
            
            input.put("AccountPDTO", accountPDTO);
            // 명령어 설정 (READ)
            input.put("command", "READ");

            NewKBData result = asMbc74001.execute(reqData);

            // 3. 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                
                // AccountPDTO 추출
                AccountPDTO accountDetail = null;
                if (output != null) {
                    // AccountPDTOList에서 첫 번째 항목을 가져오기 시도
                    Object accountListObj = output.get("AccountPDTOList");
                    if (accountListObj instanceof java.util.List) {
                        java.util.List<?> tempList = (java.util.List<?>) accountListObj;
                        if (tempList != null && !tempList.isEmpty()) {
                            Object obj = tempList.get(0);
                            if (obj instanceof AccountPDTO) {
                                accountDetail = (AccountPDTO) obj;
                            }
                        }
                    }
                }
                
                if (accountDetail != null) {
                    response.put("success", true);
                    response.put("message", "계정 상세 조회가 완료되었습니다.");
                    response.put("data", accountDetail);
                    
                    logger.info("=== ACMBC72001.getAccountDetail - Success: found account ===");
                    logger.info("=== ACMBC72001.getAccountDetail - Account: accountNumber=" + accountDetail.getAccountNumber() + 
                               ", name=" + accountDetail.getName() + ", accountType=" + accountDetail.getAccountType() + " ===");
                } else {
                    response.put("success", false);
                    response.put("message", "계정을 찾을 수 없습니다: " + accountId);
                    logger.warn("=== ACMBC72001.getAccountDetail - Account not found: " + accountId + " ===");
                }
            } else {
                response.put("success", false);
                response.put("message", "계정 상세 조회 처리 중 오류가 발생했습니다.");
                logger.error("=== ACMBC72001.getAccountDetail - Result is null ===");
            }

            logger.info("=== ACMBC72001.getAccountDetail END ===");
            return ResponseEntity.ok(response);

        } catch (NewBusinessException e) {
            logger.error("=== ACMBC72001.getAccountDetail - Business Exception: " + e.getMessage() + " ===");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "계정 상세 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            logger.error("=== ACMBC72001.getAccountDetail - Unexpected Exception: " + e.getMessage() + " ===", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "계정 상세 조회 처리 중 예상치 못한 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 입력 데이터 검증
     * 
     * @param accountId 계정 번호
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(String accountId) throws NewBusinessException {
        if (accountId == null || accountId.trim().isEmpty()) {
            logger.error("=== ACMBC72001.validateInputData - accountId is null or empty ===");
            throw new NewBusinessException("B0000001", "계정 번호는 필수입니다.");
        }
        
        logger.info("=== ACMBC72001.validateInputData - Validation passed: accountId=" + accountId + " ===");
    }

    /**
     * NewIApplicationService 인터페이스 구현
     */
    public NewKBData execute(NewKBData reqData) throws NewBusinessException {
        // 이 메서드는 직접 호출되지 않으며, REST API를 통해 호출됩니다.
        throw new NewBusinessException("B0000002", "This method should not be called directly");
    }
}