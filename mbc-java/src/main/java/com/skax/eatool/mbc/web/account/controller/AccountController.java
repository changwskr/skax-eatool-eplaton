package com.skax.eatool.mbc.web.account.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 계정관리 컨트롤러
 * 계정 CRUD 작업을 처리하는 REST API 엔드포인트 제공
 */
@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"}, allowCredentials = "false")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ASMBC71001 accountCreateService;  // 계정 생성 서비스

    @Autowired
    private ASMBC72001 accountReadService;    // 계정 조회 서비스

    @Autowired
    private ASMBC72002 accountDetailService;  // 계정 상세 조회 서비스

    @Autowired
    private ASMBC73001 accountUpdateService;  // 계정 수정 서비스

    @Autowired
    private ASMBC74001 accountDeleteService;  // 계정 삭제 서비스

    /**
     * 계정 생성
     * @param accountData 생성할 계정 정보
     * @return 생성 결과
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, Object> accountData) {
        logger.info("=== AccountController.createAccount START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // AccountPDTO 생성 및 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountId((String) accountData.get("accountId"));
            accountPDTO.setAccountName((String) accountData.get("accountName"));
            accountPDTO.setAccountType((String) accountData.get("accountType"));
            accountPDTO.setAccountBalance((String) accountData.get("accountBalance"));
            accountPDTO.setAccountStatus((String) accountData.get("accountStatus"));
            
            inputDto.put("AccountPDTO", accountPDTO);
            
            // 서비스 호출
            NewKBData result = accountCreateService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 생성되었습니다.");
            response.put("data", result);
            
            logger.info("=== AccountController.createAccount END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.createAccount ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 목록 조회
     * @param searchData 검색 조건
     * @return 계정 목록
     */
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getAccountList(@RequestBody Map<String, Object> searchData) {
        logger.info("=== AccountController.getAccountList START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // 검색 조건 설정
            inputDto.put("searchKeyword", searchData.get("searchKeyword"));
            inputDto.put("accountType", searchData.get("accountType"));
            inputDto.put("pageNumber", searchData.get("pageNumber"));
            inputDto.put("pageSize", searchData.get("pageSize"));
            
            // 서비스 호출
            NewKBData result = accountReadService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            
            logger.info("=== AccountController.getAccountList END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.getAccountList ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 상세 조회
     * @param accountId 계정 ID
     * @return 계정 상세 정보
     */
    @GetMapping("/detail/{accountId}")
    public ResponseEntity<Map<String, Object>> getAccountDetail(@PathVariable String accountId) {
        logger.info("=== AccountController.getAccountDetail START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            inputDto.put("accountId", accountId);
            
            // 서비스 호출
            NewKBData result = accountDetailService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            
            logger.info("=== AccountController.getAccountDetail END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.getAccountDetail ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 수정
     * @param accountId 계정 ID
     * @param accountData 수정할 계정 정보
     * @return 수정 결과
     */
    @PutMapping("/update/{accountId}")
    public ResponseEntity<Map<String, Object>> updateAccount(
            @PathVariable String accountId, 
            @RequestBody Map<String, Object> accountData) {
        logger.info("=== AccountController.updateAccount START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            // AccountPDTO 생성 및 설정
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountId(accountId);
            accountPDTO.setAccountName((String) accountData.get("accountName"));
            accountPDTO.setAccountType((String) accountData.get("accountType"));
            accountPDTO.setAccountBalance((String) accountData.get("accountBalance"));
            accountPDTO.setAccountStatus((String) accountData.get("accountStatus"));
            
            inputDto.put("AccountPDTO", accountPDTO);
            
            // 서비스 호출
            NewKBData result = accountUpdateService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 수정되었습니다.");
            response.put("data", result);
            
            logger.info("=== AccountController.updateAccount END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.updateAccount ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 삭제
     * @param accountId 계정 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable String accountId) {
        logger.info("=== AccountController.deleteAccount START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            inputDto.put("accountId", accountId);
            
            // 서비스 호출
            NewKBData result = accountDeleteService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 삭제되었습니다.");
            response.put("data", result);
            
            logger.info("=== AccountController.deleteAccount END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.deleteAccount ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 상태 변경
     * @param accountId 계정 ID
     * @param status 변경할 상태
     * @return 상태 변경 결과
     */
    @PutMapping("/status/{accountId}")
    public ResponseEntity<Map<String, Object>> updateAccountStatus(
            @PathVariable String accountId, 
            @RequestParam String status) {
        logger.info("=== AccountController.updateAccountStatus START ===");
        try {
            // NewKBData 생성
            NewKBData kbData = new NewKBData();
            NewGenericDto inputDto = kbData.getInputGenericDto();
            
            inputDto.put("accountId", accountId);
            inputDto.put("status", status);
            
            // 서비스 호출
            NewKBData result = accountUpdateService.execute(kbData);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정 상태가 성공적으로 변경되었습니다.");
            response.put("data", result);
            
            logger.info("=== AccountController.updateAccountStatus END ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("=== AccountController.updateAccountStatus ERROR: " + e.getMessage() + " ===", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}