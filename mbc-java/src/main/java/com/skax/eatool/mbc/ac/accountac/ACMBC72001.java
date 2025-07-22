package com.skax.eatool.mbc.ac.accountac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.accountas.ASMBC72001;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 계정 조회 Application Control
 * 
 * 프로그램명: ACMBC72001.java
 * 설명: 계정 조회 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 조회 요청 처리 (GET, POST)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/api/account/read")
@Tag(name = "계정 관리", description = "계정 조회 관련 API")
public class ACMBC72001 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC72001.class);

    @Autowired
    private ASMBC72001 asMbc72001;

    /**
     * 계정 조회 처리 (GET)
     * 
     * @param accountId 계좌번호
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAccountDetail(
            @RequestParam(value = "accountId", required = true) String accountId) throws NewBusinessException {
        logger.info("=== ACMBC72001.getAccountDetail START ===");
        logger.info("=== ACMBC72001.getAccountDetail - Input: accountId=" + accountId + " ===");

        try {
            // 1. 입력 데이터 검증
            if (accountId == null || accountId.trim().isEmpty()) {
                throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
            }

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountNumber(accountId);
            input.put("AccountPDTO", accountPDTO);

            NewKBData result = asMbc72001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 조회가 완료되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC72001.getAccountDetail - Response: success=true, message=계정 조회가 완료되었습니다. ===");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                AccountPDTO resultAccount = (AccountPDTO) output.get("AccountPDTO");
                if (resultAccount != null) {
                    logger.info("=== ACMBC72001.getAccountDetail - Output AccountPDTO: accountNumber=" + resultAccount.getAccountNumber() + 
                               ", name=" + resultAccount.getName() + ", accountType=" + resultAccount.getAccountType() + 
                               ", status=" + resultAccount.getStatus() + ", currency=" + resultAccount.getCurrency() + 
                               ", netAmount=" + resultAccount.getNetAmount() + ", interestRate=" + resultAccount.getInterestRate() + 
                               ", identificationNumber=" + resultAccount.getIdentificationNumber() + ", createdDate=" + resultAccount.getCreatedDate() + 
                               ", updatedDate=" + resultAccount.getUpdatedDate() + " ===");
                }
            }

            logger.info("=== ACMBC72001.getAccountDetail - Success: accountId=" + accountId + " ===");
            logger.info("=== ACMBC72001.getAccountDetail END ===");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC72001.getAccountDetail - Error: " + e.getMessage() + " ===");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC72001.getAccountDetail END ===");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 조회 처리 (POST)
     * 
     * @param accountPDTO 계정 정보
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> getAccountPost(@RequestBody AccountPDTO accountPDTO)
            throws NewBusinessException {
        logger.debug("ACMBC72001 - 계정 조회 요청 처리 시작 (POST)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);

            NewKBData result = asMbc72001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 조회가 완료되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC72001 - 계정 조회 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC72001 - 계정 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 기존 execute 메서드 (호환성 유지)
     * 
     * @param reqData 요청 데이터
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    public NewKBData execute(NewKBData reqData) throws NewBusinessException {
        logger.debug("ACMBC72001 - 계정 조회 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            NewKBData result = asMbc72001.execute(reqData);

            logger.debug("ACMBC72001 - 계정 조회 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC72001 - 계정 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("계정 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
        }
    }

    /**
     * 입력 데이터 검증 (NewKBData용)
     * 
     * @param reqData 요청 데이터
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(NewKBData reqData) throws NewBusinessException {
        NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);

        // AccountPDTO 존재 여부 확인
        AccountPDTO accountPDTO = (AccountPDTO) input.get("AccountPDTO");

        // 필수 필드 검증
        if (accountPDTO == null) {
            throw new NewBusinessException("AccountPDTO가 null입니다.");
        }

        // 계좌번호 검증 (필수 필드)
        if (accountPDTO.getAccountNumber() == null || accountPDTO.getAccountNumber().trim().isEmpty()) {
            throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC72001 - 입력 데이터 검증 완료");
    }

    /**
     * 입력 데이터 검증 (AccountPDTO용)
     * 
     * @param accountPDTO 계정 정보
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(AccountPDTO accountPDTO) throws NewBusinessException {
        if (accountPDTO == null) {
            throw new NewBusinessException("AccountPDTO가 null입니다.");
        }

        // 계좌번호 검증 (필수 필드)
        if (accountPDTO.getAccountNumber() == null || accountPDTO.getAccountNumber().trim().isEmpty()) {
            throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC72001 - 입력 데이터 검증 완료");
    }
}