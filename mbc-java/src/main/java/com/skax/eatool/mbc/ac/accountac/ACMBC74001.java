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
 * 계정 목록 조회 Application Control
 * 
 * 프로그램명: ACMBC74001.java
 * 설명: 계정 목록 조회 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 목록 조회 요청 처리 (GET, POST)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@Controller
@RequestMapping("/api/account/list")
@Tag(name = "계정 관리", description = "계정 목록 관련 API")
public class ACMBC74001 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC74001.class);

    @Autowired
    private ASMBC74001 asMbc74001;

    /**
     * 계정 목록 조회 처리 (GET)
     * 
     * @param page          페이지 번호 (선택적)
     * @param size          페이지 크기 (선택적)
     * @param searchKeyword 검색 키워드 (선택적)
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAccountList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "accountType", required = false) String accountType,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "currency", required = false) String currency) throws NewBusinessException {
        logger.info("=== ACMBC74001.getAccountList START ===", "ACMBC74001");
        logger.info("=== ACMBC74001.getAccountList - Input: page=" + page + ", size=" + size + 
                   ", searchKeyword=" + searchKeyword + ", accountType=" + accountType + 
                   ", status=" + status + ", currency=" + currency + " ===", "ACMBC74001");

        try {
            // 1. 입력 데이터 검증
            validateInputData(page, size, searchKeyword);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            
            // AccountPDTO 생성 및 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            
            // 검색 조건 설정
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                accountPDTO.setAccountNumber(searchKeyword);
            }
            if (accountType != null && !accountType.trim().isEmpty()) {
                accountPDTO.setAccountType(accountType);
            }
            if (status != null && !status.trim().isEmpty()) {
                accountPDTO.setStatus(status);
            }
            if (currency != null && !currency.trim().isEmpty()) {
                accountPDTO.setCurrency(currency);
            }
            
            // 페이징 정보 설정
            input.put("pageNumber", page);
            input.put("pageSize", size);
            input.put("AccountPDTO", accountPDTO);

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 목록 조회가 완료되었습니다.");
            response.put("data", result);
            response.put("page", page);
            response.put("size", size);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC74001.getAccountList - Response: success=true, message=계정 목록 조회가 완료되었습니다., page=" + page + ", size=" + size + " ===", "ACMBC74001");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                @SuppressWarnings("unchecked")
                java.util.List<AccountPDTO> accountList = (java.util.List<AccountPDTO>) output.get("AccountPDTO");
                if (accountList != null) {
                    logger.info("=== ACMBC74001.getAccountList - Output AccountPDTO List: count=" + accountList.size() + " ===", "ACMBC74001");
                    for (int i = 0; i < Math.min(accountList.size(), 3); i++) { // 최대 3개만 로그 출력
                        AccountPDTO account = accountList.get(i);
                        logger.info("=== ACMBC74001.getAccountList - Account[" + i + "]: accountNumber=" + account.getAccountNumber() + 
                                   ", name=" + account.getName() + ", accountType=" + account.getAccountType() + 
                                   ", status=" + account.getStatus() + ", currency=" + account.getCurrency() + 
                                   ", netAmount=" + account.getNetAmount() + " ===", "ACMBC74001");
                    }
                    if (accountList.size() > 3) {
                        logger.info("=== ACMBC74001.getAccountList - ... and " + (accountList.size() - 3) + " more accounts ===", "ACMBC74001");
                    }
                }
            }

            logger.info("=== ACMBC74001.getAccountList - Success: found accounts ===", "ACMBC74001");
            logger.info("=== ACMBC74001.getAccountList END ===", "ACMBC74001");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC74001.getAccountList - Error: " + e.getMessage() + " ===", "ACMBC74001");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 목록 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC74001.getAccountList END ===", "ACMBC74001");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 목록 조회 처리 (POST)
     * 
     * @param requestBody 요청 본문
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> getAccountListPost(@RequestBody Map<String, Object> requestBody)
            throws NewBusinessException {
        logger.info("=== ACMBC74001.getAccountListPost START ===", "ACMBC74001");
        logger.info("=== ACMBC74001.getAccountListPost - Input: " + requestBody.toString() + " ===", "ACMBC74001");

        try {
            // 1. 입력 데이터 검증
            validateInputData(requestBody);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);

            // AccountPDTO 생성 및 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            
            // 검색 조건 설정
            if (requestBody.containsKey("searchKeyword")) {
                accountPDTO.setAccountNumber((String) requestBody.get("searchKeyword"));
            }
            if (requestBody.containsKey("accountType")) {
                accountPDTO.setAccountType((String) requestBody.get("accountType"));
            }
            if (requestBody.containsKey("status")) {
                accountPDTO.setStatus((String) requestBody.get("status"));
            }
            if (requestBody.containsKey("currency")) {
                accountPDTO.setCurrency((String) requestBody.get("currency"));
            }
            
            // 페이징 정보 설정
            input.put("pageNumber", requestBody.getOrDefault("pageNumber", 1));
            input.put("pageSize", requestBody.getOrDefault("pageSize", 10));
            input.put("AccountPDTO", accountPDTO);

            NewKBData result = asMbc74001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 목록 조회가 완료되었습니다.");
            response.put("data", result);

            // 응답 데이터 로그 출력
            logger.info("=== ACMBC74001.getAccountListPost - Response: success=true, message=계정 목록 조회가 완료되었습니다. ===", "ACMBC74001");
            if (result != null && result.getOutputGenericDto() != null) {
                NewGenericDto output = result.getOutputGenericDto().using(NewGenericDto.OUTDATA);
                @SuppressWarnings("unchecked")
                java.util.List<AccountPDTO> accountList = (java.util.List<AccountPDTO>) output.get("AccountPDTO");
                if (accountList != null) {
                    logger.info("=== ACMBC74001.getAccountListPost - Output AccountPDTO List: count=" + accountList.size() + " ===", "ACMBC74001");
                    for (int i = 0; i < Math.min(accountList.size(), 3); i++) { // 최대 3개만 로그 출력
                        AccountPDTO account = accountList.get(i);
                        logger.info("=== ACMBC74001.getAccountListPost - Account[" + i + "]: accountNumber=" + account.getAccountNumber() + 
                                   ", name=" + account.getName() + ", accountType=" + account.getAccountType() + 
                                   ", status=" + account.getStatus() + ", currency=" + account.getCurrency() + 
                                   ", netAmount=" + account.getNetAmount() + " ===", "ACMBC74001");
                    }
                    if (accountList.size() > 3) {
                        logger.info("=== ACMBC74001.getAccountListPost - ... and " + (accountList.size() - 3) + " more accounts ===", "ACMBC74001");
                    }
                }
            }

            logger.info("=== ACMBC74001.getAccountListPost - Success: found accounts ===", "ACMBC74001");
            logger.info("=== ACMBC74001.getAccountListPost END ===", "ACMBC74001");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("=== ACMBC74001.getAccountListPost - Error: " + e.getMessage() + " ===", "ACMBC74001");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 목록 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            logger.info("=== ACMBC74001.getAccountListPost END ===", "ACMBC74001");
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
        logger.debug("ACMBC74001 - 계정 목록 조회 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            NewKBData result = asMbc74001.execute(reqData);

            logger.debug("ACMBC74001 - 계정 목록 조회 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC74001 - 계정 목록 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("계정 목록 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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

        // AccountPDTO 존재 여부 확인 (선택적)
        AccountPDTO accountPDTO = (AccountPDTO) input.get("AccountPDTO");
        if (accountPDTO != null) {
            // 추가 검증 로직은 필요에 따라 구현
            logger.debug("ACMBC74001 - AccountPDTO 검증 완료");
        }

        logger.debug("ACMBC74001 - 입력 데이터 검증 완료");
    }

    /**
     * 입력 데이터 검증 (파라미터용)
     * 
     * @param page          페이지 번호
     * @param size          페이지 크기
     * @param searchKeyword 검색 키워드
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(int page, int size, String searchKeyword) throws NewBusinessException {
        if (page < 1) {
            throw new NewBusinessException("페이지 번호는 1 이상이어야 합니다.");
        }
        if (size < 1 || size > 100) {
            throw new NewBusinessException("페이지 크기는 1~100 사이여야 합니다.");
        }

        logger.debug("ACMBC74001 - 입력 데이터 검증 완료");
    }

    /**
     * 입력 데이터 검증 (Map용)
     * 
     * @param requestBody 요청 본문
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(Map<String, Object> requestBody) throws NewBusinessException {
        if (requestBody == null) {
            throw new NewBusinessException("요청 본문이 null입니다.");
        }

        logger.debug("ACMBC74001 - 입력 데이터 검증 완료");
    }
}