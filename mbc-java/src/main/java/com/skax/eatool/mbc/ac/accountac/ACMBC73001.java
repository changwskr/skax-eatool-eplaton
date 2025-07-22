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
 * 계정 삭제 Application Control
 * 
 * 프로그램명: ACMBC73001.java
 * 설명: 계정 삭제 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 삭제 요청 처리 (GET, POST, DELETE)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/account/delete")
@Tag(name = "계정 관리", description = "계정 삭제 관련 API")
public class ACMBC73001 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC73001.class);

    @Autowired
    private ASMBC74001 asMbc74001;

    /**
     * 계정 삭제 처리 (POST)
     * 
     * @param accountPDTO 계정 정보
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> deleteAccountPost(@RequestBody AccountPDTO accountPDTO)
            throws NewBusinessException {
        logger.debug("ACMBC73001 - 계정 삭제 요청 처리 시작 (POST)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);
            // 명령어 설정 (DELETE)
            input.put("command", "DELETE");

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 삭제되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC73001 - 계정 삭제 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC73001 - 계정 삭제 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 삭제 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 삭제 처리 (DELETE)
     * 
     * @param accountId 계좌번호
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Map<String, Object>> deleteAccountDelete(
            @PathVariable String accountId) throws NewBusinessException {
        logger.info("=== ACMBC73001.deleteAccountDelete START ===", "ACMBC73001");
        logger.info("=== ACMBC73001.deleteAccountDelete - Input: accountId=" + accountId + " ===", "ACMBC73001");

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
            // 명령어 설정 (DELETE)
            input.put("command", "DELETE");

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 삭제되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC73001.deleteAccountDelete - Response: success=true, message=계정이 성공적으로 삭제되었습니다. ===", "ACMBC73001");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                Object accountListObj = output.get("AccountPDTOList");
                if (accountListObj instanceof java.util.List) {
                    java.util.List<?> tempList = (java.util.List<?>) accountListObj;
                    if (tempList != null && !tempList.isEmpty()) {
                        Object obj = tempList.get(0);
                        if (obj instanceof AccountPDTO) {
                            AccountPDTO resultAccount = (AccountPDTO) obj;
                            logger.info("=== ACMBC73001.deleteAccountDelete - Output AccountPDTO: accountNumber=" + resultAccount.getAccountNumber() + 
                                       ", name=" + resultAccount.getName() + ", status=" + resultAccount.getStatus() + " ===", "ACMBC73001");
                        }
                    }
                }
            }

            logger.info("=== ACMBC73001.deleteAccountDelete - Success: accountId=" + accountId + " ===", "ACMBC73001");
            logger.info("=== ACMBC73001.deleteAccountDelete END ===", "ACMBC73001");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC73001.deleteAccountDelete - Error: " + e.getMessage() + " ===", "ACMBC73001");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 삭제 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC73001.deleteAccountDelete END ===", "ACMBC73001");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 삭제 처리 (GET)
     * 
     * @param accountId 계좌번호
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> deleteAccount(
            @RequestParam(value = "accountId", required = true) String accountId) throws NewBusinessException {
        logger.info("=== ACMBC73001.deleteAccount START ===", "ACMBC73001");
        logger.info("=== ACMBC73001.deleteAccount - Input: accountId=" + accountId + " ===", "ACMBC73001");

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
            // 명령어 설정 (DELETE)
            input.put("command", "DELETE");

            ASMBC74001 asMbc74001 = new ASMBC74001();
            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 삭제되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC73001.deleteAccount - Response: success=true, message=계정이 성공적으로 삭제되었습니다. ===", "ACMBC73001");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                Object accountListObj = output.get("AccountPDTOList");
                if (accountListObj instanceof java.util.List) {
                    java.util.List<?> tempList = (java.util.List<?>) accountListObj;
                    if (tempList != null && !tempList.isEmpty()) {
                        Object obj = tempList.get(0);
                        if (obj instanceof AccountPDTO) {
                            AccountPDTO resultAccount = (AccountPDTO) obj;
                            logger.info("=== ACMBC73001.deleteAccount - Output AccountPDTO: accountNumber=" + resultAccount.getAccountNumber() + 
                                       ", name=" + resultAccount.getName() + ", status=" + resultAccount.getStatus() + " ===", "ACMBC73001");
                        }
                    }
                }
            }

            logger.info("=== ACMBC73001.deleteAccount - Success: accountId=" + accountId + " ===", "ACMBC73001");
            logger.info("=== ACMBC73001.deleteAccount END ===", "ACMBC73001");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC73001.deleteAccount - Error: " + e.getMessage() + " ===", "ACMBC73001");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 삭제 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC73001.deleteAccount END ===", "ACMBC73001");
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
        logger.debug("ACMBC73001 - 계정 삭제 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            ASMBC74001 asMbc74001 = new ASMBC74001();
            NewKBData result = asMbc74001.execute(reqData);

            logger.debug("ACMBC73001 - 계정 삭제 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC73001 - 계정 삭제 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("계정 삭제 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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

        logger.debug("ACMBC73001 - 입력 데이터 검증 완료");
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

        logger.debug("ACMBC73001 - 입력 데이터 검증 완료");
    }
}