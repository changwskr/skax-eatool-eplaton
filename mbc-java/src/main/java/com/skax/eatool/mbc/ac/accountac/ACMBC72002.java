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
import org.springframework.stereotype.Controller;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 계정 수정 Application Control
 * 
 * 프로그램명: ACMBC72002.java
 * 설명: 계정 수정 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 수정 요청 처리 (GET, POST, PUT)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/account/update")
@Tag(name = "계정 관리", description = "계정 수정 관련 API")
public class ACMBC72002 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC72002.class);

    @Autowired
    private ASMBC74001 asMbc74001;

    /**
     * 계정 수정 처리 (POST)
     * 
     * @param accountPDTO 계정 정보
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> updateAccount(@RequestBody AccountPDTO accountPDTO)
            throws NewBusinessException {
        logger.info("=== ACMBC72002.updateAccount START ===", "ACMBC72002");
        logger.info("=== ACMBC72002.updateAccount - Input AccountPDTO: accountNumber=" + accountPDTO.getAccountNumber() + 
                   ", name=" + accountPDTO.getName() + ", accountType=" + accountPDTO.getAccountType() + 
                   ", status=" + accountPDTO.getStatus() + ", currency=" + accountPDTO.getCurrency() + 
                   ", netAmount=" + accountPDTO.getNetAmount() + ", interestRate=" + accountPDTO.getInterestRate() + 
                   ", identificationNumber=" + accountPDTO.getIdentificationNumber() + " ===", "ACMBC72002");

        try {
            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);
            // 명령어 설정 (UPDATE)
            input.put("command", "UPDATE");

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 수정되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC72002.updateAccount - Response: success=true, message=계정이 성공적으로 수정되었습니다. ===", "ACMBC72002");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                Object accountListObj = output.get("AccountPDTOList");
                if (accountListObj instanceof java.util.List) {
                    java.util.List<?> tempList = (java.util.List<?>) accountListObj;
                    if (tempList != null && !tempList.isEmpty()) {
                        Object obj = tempList.get(0);
                        if (obj instanceof AccountPDTO) {
                            AccountPDTO resultAccount = (AccountPDTO) obj;
                            logger.info("=== ACMBC72002.updateAccount - Output AccountPDTO: accountNumber=" + resultAccount.getAccountNumber() + 
                                       ", name=" + resultAccount.getName() + ", accountType=" + resultAccount.getAccountType() + 
                                       ", status=" + resultAccount.getStatus() + ", currency=" + resultAccount.getCurrency() + 
                                       ", netAmount=" + resultAccount.getNetAmount() + ", interestRate=" + resultAccount.getInterestRate() + 
                                       ", updatedDate=" + resultAccount.getUpdatedDate() + " ===", "ACMBC72002");
                        }
                    }
                }
            }

            logger.info("=== ACMBC72002.updateAccount - Success: accountNumber=" + accountPDTO.getAccountNumber() + " ===", "ACMBC72002");
            logger.info("=== ACMBC72002.updateAccount END ===", "ACMBC72002");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC72002.updateAccount - Error: " + e.getMessage() + " ===", "ACMBC72002");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 수정 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC72002.updateAccount END ===", "ACMBC72002");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 수정 처리 (PUT)
     * 
     * @param accountId 계좌번호
     * @param accountPDTO 계정 정보
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> updateAccountPut(
            @PathVariable String accountId,
            @RequestBody AccountPDTO accountPDTO) throws NewBusinessException {
        logger.info("=== ACMBC72002.updateAccountPut START ===", "ACMBC72002");
        logger.info("=== ACMBC72002.updateAccountPut - accountId: " + accountId + " ===", "ACMBC72002");
        logger.info("=== ACMBC72002.updateAccountPut - Input AccountPDTO: accountNumber=" + accountPDTO.getAccountNumber() + 
                   ", name=" + accountPDTO.getName() + ", accountType=" + accountPDTO.getAccountType() + 
                   ", status=" + accountPDTO.getStatus() + ", currency=" + accountPDTO.getCurrency() + 
                   ", netAmount=" + accountPDTO.getNetAmount() + ", interestRate=" + accountPDTO.getInterestRate() + 
                   ", identificationNumber=" + accountPDTO.getIdentificationNumber() + " ===", "ACMBC72002");

        try {
            // accountId와 accountPDTO의 accountNumber가 일치하는지 확인
            if (!accountId.equals(accountPDTO.getAccountNumber())) {
                accountPDTO.setAccountNumber(accountId);
                logger.info("=== ACMBC72002.updateAccountPut - Updated accountNumber to match path variable: " + accountId + " ===", "ACMBC72002");
            }

            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);
            // 명령어 설정 (UPDATE)
            input.put("command", "UPDATE");

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 수정되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC72002.updateAccountPut - Response: success=true, message=계정이 성공적으로 수정되었습니다. ===", "ACMBC72002");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                Object accountListObj = output.get("AccountPDTOList");
                if (accountListObj instanceof java.util.List) {
                    java.util.List<?> tempList = (java.util.List<?>) accountListObj;
                    if (tempList != null && !tempList.isEmpty()) {
                        Object obj = tempList.get(0);
                        if (obj instanceof AccountPDTO) {
                            AccountPDTO resultAccount = (AccountPDTO) obj;
                            logger.info("=== ACMBC72002.updateAccountPut - Output AccountPDTO: accountNumber=" + resultAccount.getAccountNumber() + 
                                       ", name=" + resultAccount.getName() + ", accountType=" + resultAccount.getAccountType() + 
                                       ", status=" + resultAccount.getStatus() + ", currency=" + resultAccount.getCurrency() + 
                                       ", netAmount=" + resultAccount.getNetAmount() + ", interestRate=" + resultAccount.getInterestRate() + 
                                       ", updatedDate=" + resultAccount.getUpdatedDate() + " ===", "ACMBC72002");
                        }
                    }
                }
            }

            logger.info("=== ACMBC72002.updateAccountPut - Success: accountNumber=" + accountPDTO.getAccountNumber() + " ===", "ACMBC72002");
            logger.info("=== ACMBC72002.updateAccountPut END ===", "ACMBC72002");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC72002.updateAccountPut - Error: " + e.getMessage() + " ===", "ACMBC72002");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 수정 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC72002.updateAccountPut END ===", "ACMBC72002");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 수정 처리 (GET)
     * 
     * @param accountId   계좌번호
     * @param accountName 계정명
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> updateAccountGet(
            @RequestParam String accountId,
            @RequestParam String accountName) throws NewBusinessException {
        logger.debug("ACMBC72002 - 계정 수정 요청 처리 시작 (GET)");

        try {
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountNumber(accountId);
            accountPDTO.setName(accountName);

            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);
            // 명령어 설정 (UPDATE)
            input.put("command", "UPDATE");

            ASMBC74001 asMbc74001 = new ASMBC74001();
            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 수정되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC72002 - 계정 수정 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC72002 - 계정 수정 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 수정 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        logger.debug("ACMBC72002 - 계정 수정 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            ASMBC74001 asMbc74001 = new ASMBC74001();
            NewKBData result = asMbc74001.execute(reqData);

            logger.debug("ACMBC72002 - 계정 수정 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC72002 - 계정 수정 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("계정 수정 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        if (accountPDTO == null) {
            throw new NewBusinessException("AccountPDTO가 null입니다.");
        }

        // 계좌번호 검증 (필수 필드)
        if (accountPDTO.getAccountNumber() == null || accountPDTO.getAccountNumber().trim().isEmpty()) {
            throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC72002 - 입력 데이터 검증 완료");
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

        logger.debug("ACMBC72002 - 입력 데이터 검증 완료");
    }
}