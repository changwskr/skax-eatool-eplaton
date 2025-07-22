package com.skax.eatool.mbc.web.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.mbc.as.accountas.ASMBC71001;
import com.skax.eatool.mbc.as.accountas.ASMBC72001;
import com.skax.eatool.mbc.as.accountas.ASMBC72002;
import com.skax.eatool.mbc.as.accountas.ASMBC73001;
import com.skax.eatool.mbc.as.accountas.ASMBC74001;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 계정 관리 컨트롤러
 * AS 서비스를 통한 계정 관리 엔드포인트
 */
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ASMBC71001 accountCreateService;

    @Autowired
    private ASMBC72001 accountListService;

    @Autowired
    private ASMBC72002 accountDetailService;

    @Autowired
    private ASMBC73001 accountUpdateService;

    @Autowired
    private ASMBC74001 accountDeleteService;

    /**
     * 계정 목록 조회 (AS 서비스 통한)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAccounts(
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        logger.info("=== AccountController.getAccounts START ===");
        
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // 검색 조건 설정
            AccountPDTO searchCriteria = new AccountPDTO();
            searchCriteria.setAccountType(accountType);
            searchCriteria.setStatus(status);
            searchCriteria.setCurrency(currency);
            searchCriteria.setAccountNumber(searchKeyword); // 키워드 검색용
            
            // 페이징 정보 설정
            inputDto.put("pageNumber", page);
            inputDto.put("pageSize", size);
            inputDto.put("AccountPDTO", searchCriteria);
            
            // AS 서비스 호출
            NewKBData result = accountListService.execute(kbData);
            
            // 결과 처리
            List<AccountPDTO> accounts = (List<AccountPDTO>) result.getOutputGenericDto()
                .using(NewGenericDto.OUTDATA)
                .get("AccountPDTO");
            
            if (accounts == null) {
                accounts = new java.util.ArrayList<>();
            }
            
            // 페이징 처리
            int total = accounts.size();
            int start = (page - 1) * size;
            int end = Math.min(start + size, total);
            
            List<AccountPDTO> pagedAccounts = accounts.subList(start, end);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", pagedAccounts);
            response.put("pagination", Map.of(
                "page", page,
                "size", size,
                "total", total,
                "totalPages", (int) Math.ceil((double) total / size)
            ));
            
            logger.info("=== AccountController.getAccounts - Found {} accounts ===", total);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.getAccounts ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 상세 조회 (AS 서비스 통한)
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable String accountNumber) {
        logger.info("=== AccountController.getAccount START - accountNumber: {} ===", accountNumber);
        
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // 검색 조건 설정
            AccountPDTO searchCriteria = new AccountPDTO();
            searchCriteria.setAccountNumber(accountNumber);
            
            inputDto.put("AccountPDTO", searchCriteria);
            
            // AS 서비스 호출
            NewKBData result = accountDetailService.execute(kbData);
            
            // 결과 처리
            AccountPDTO account = (AccountPDTO) result.getOutputGenericDto()
                .using(NewGenericDto.OUTDATA)
                .get("AccountPDTO");
            
            Map<String, Object> response = new HashMap<>();
            if (account != null) {
                response.put("success", true);
                response.put("data", account);
                logger.info("=== AccountController.getAccount - Account found ===");
            } else {
                response.put("success", false);
                response.put("error", "Account not found");
                logger.warn("=== AccountController.getAccount - Account not found ===");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.getAccount ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 생성 (AS 서비스 통한)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody AccountPDTO accountPDTO) {
        logger.info("=== AccountController.createAccount START ===");
        
        try {
            // 기본값 설정
            if (accountPDTO.getStatus() == null || accountPDTO.getStatus().isEmpty()) {
                accountPDTO.setStatus("ACTIVE");
            }
            if (accountPDTO.getCurrency() == null || accountPDTO.getCurrency().isEmpty()) {
                accountPDTO.setCurrency("KRW");
            }
            
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            inputDto.put("AccountPDTO", accountPDTO);
            
            // AS 서비스 호출
            NewKBData result = accountCreateService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Account created successfully");
            response.put("data", accountPDTO);
            
            logger.info("=== AccountController.createAccount - Account created: {} ===", accountPDTO.getAccountNumber());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.createAccount ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 수정 (AS 서비스 통한)
     */
    @PutMapping("/{accountNumber}")
    public ResponseEntity<Map<String, Object>> updateAccount(
            @PathVariable String accountNumber,
            @RequestBody AccountPDTO accountPDTO) {
        
        logger.info("=== AccountController.updateAccount START - accountNumber: {} ===", accountNumber);
        
        try {
            accountPDTO.setAccountNumber(accountNumber);
            
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            inputDto.put("AccountPDTO", accountPDTO);
            
            // AS 서비스 호출
            NewKBData result = accountUpdateService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Account updated successfully");
            response.put("data", accountPDTO);
            
            logger.info("=== AccountController.updateAccount - Account updated ===");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.updateAccount ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 삭제 (AS 서비스 통한)
     */
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable String accountNumber) {
        logger.info("=== AccountController.deleteAccount START - accountNumber: {} ===", accountNumber);
        
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // 삭제할 계정 정보 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountNumber(accountNumber);
            
            inputDto.put("AccountPDTO", accountPDTO);
            
            // AS 서비스 호출 (ASMBC74001 사용)
            NewKBData result = accountDeleteService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Account deleted successfully");
            
            logger.info("=== AccountController.deleteAccount - Account deleted ===");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.deleteAccount ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 통계 조회 (AS 서비스 통한)
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAccountStats() {
        logger.info("=== AccountController.getAccountStats START ===");
        
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // AS 서비스 호출
            NewKBData result = accountListService.execute(kbData);
            
            // 결과 처리
            List<AccountPDTO> allAccounts = (List<AccountPDTO>) result.getOutputGenericDto()
                .using(NewGenericDto.OUTDATA)
                .get("AccountPDTO");
            
            if (allAccounts == null) {
                allAccounts = new java.util.ArrayList<>();
            }
            
            // 통계 계산
            long totalAccounts = allAccounts.size();
            long activeAccounts = allAccounts.stream()
                .filter(acc -> "ACTIVE".equals(acc.getStatus()))
                .count();
            
            double totalBalance = allAccounts.stream()
                .mapToDouble(acc -> {
                    try {
                        return acc.getNetAmount() != null ? Double.parseDouble(acc.getNetAmount()) : 0.0;
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .sum();
            
            double avgBalance = totalAccounts > 0 ? totalBalance / totalAccounts : 0.0;
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalAccounts", totalAccounts);
            stats.put("activeAccounts", activeAccounts);
            stats.put("inactiveAccounts", totalAccounts - activeAccounts);
            stats.put("totalBalance", totalBalance);
            stats.put("averageBalance", avgBalance);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", stats);
            
            logger.info("=== AccountController.getAccountStats - Stats calculated ===");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("=== AccountController.getAccountStats ERROR: {} ===", e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}