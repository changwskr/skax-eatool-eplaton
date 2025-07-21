package com.skax.eatool.mbc.ac.accountac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.accountas.ASMBC71001;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 계정 생성 Application Control
 * 
 * 프로그램명: ACMBC71001.java
 * 설명: 계정 생성 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 생성 요청 처리 (GET, POST)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@Controller
@RequestMapping({ "/api/account/create", "/mbc/account/create" })
@CrossOrigin(origins = "*")
@Tag(name = "계정 관리", description = "계정 생성 관련 API")
public class ACMBC71001 implements NewIApplicationService {

    protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    /**
     * 계정 생성 웹 페이지 표시
     * 
     * @param model 모델
     * @return 뷰 이름
     */
    @GetMapping("/page")
    public String showAccountCreatePage(Model model) {
        logger.info("ACMBC71001 - 계정 생성 웹 페이지 요청", "ACMBC71001");

        model.addAttribute("pageTitle", "계정 생성");
        model.addAttribute("apiEndpoint", "/api/account/create");
        model.addAttribute("controllerName", "ACMBC71001");

        return "account/create";
    }

    /**
     * 계정 생성 처리 (POST) - REST API
     * 
     * @param accountPDTO 계정 정보
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    @ResponseBody
    @Operation(summary = "계정 생성", description = "새로운 계정을 생성합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "계정 정보", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountPDTO.class), examples = @ExampleObject(name = "계정 생성 예시", value = "{\"accountId\": \"ACC001\", \"accountName\": \"테스트 계정\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "성공 응답", value = "{\"success\": true, \"message\": \"계정이 성공적으로 생성되었습니다.\", \"data\": {}}"))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "오류 응답", value = "{\"success\": false, \"message\": \"계정 생성 처리 중 오류가 발생했습니다.\"}")))
    })
    public ResponseEntity<Map<String, Object>> createAccountPost(@RequestBody AccountPDTO accountPDTO)
            throws NewBusinessException {
        logger.debug("ACMBC71001 - 계정 생성 요청 처리 시작 (POST)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);

            ASMBC71001 asMbc71001 = new ASMBC71001();
            NewKBData result = asMbc71001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 생성되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC71001 - 계정 생성 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC71001 - 계정 생성 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 생성 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 계정 생성 처리 (GET)
     * 
     * @param accountId   계좌번호
     * @param accountName 계정명
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    @Operation(summary = "계정 생성 (GET)", description = "GET 방식으로 새로운 계정을 생성합니다. (테스트용)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Map<String, Object>> createAccountGet(
            @Parameter(description = "계좌번호", required = true, example = "ACC001") @RequestParam String accountId,
            @Parameter(description = "계정명", required = true, example = "테스트 계정") @RequestParam String accountName)
            throws NewBusinessException {
        logger.debug("ACMBC71001 - 계정 생성 요청 처리 시작 (GET)");

        try {
            AccountPDTO accountPDTO = new AccountPDTO();
            accountPDTO.setAccountId(accountId);
            accountPDTO.setAccountName(accountName);

            // 1. 입력 데이터 검증
            validateInputData(accountPDTO);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("AccountPDTO", accountPDTO);

            ASMBC71001 asMbc71001 = new ASMBC71001();
            NewKBData result = asMbc71001.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "계정이 성공적으로 생성되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC71001 - 계정 생성 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC71001 - 계정 생성 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "계정 생성 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        logger.debug("ACMBC71001 - 계정 생성 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            ASMBC71001 asMbc71001 = new ASMBC71001();
            NewKBData result = asMbc71001.execute(reqData);

            logger.debug("ACMBC71001 - 계정 생성 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC71001 - 계정 생성 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("계정 생성 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        if (accountPDTO.getAccountId() == null || accountPDTO.getAccountId().trim().isEmpty()) {
            throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
        }

        // 추가 검증 로직은 AccountPDTO의 구조에 따라 구현
        logger.debug("ACMBC71001 - 입력 데이터 검증 완료");
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
        if (accountPDTO.getAccountId() == null || accountPDTO.getAccountId().trim().isEmpty()) {
            throw new NewBusinessException("계좌번호는 필수 입력 항목입니다.");
        }

        // 추가 검증 로직은 AccountPDTO의 구조에 따라 구현
        logger.debug("ACMBC71001 - 입력 데이터 검증 완료");
    }
}