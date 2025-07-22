package com.skax.eatool.mbc.ac.usermgtac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.usermgtas.ASMBC75Z03;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 상세 조회 Application Control
 * 
 * 프로그램명: ACMBC75Z03.java
 * 설명: 사용자 상세 조회 요청을 처리하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 상세 조회 요청 처리 (GET, POST)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/user/detail")
@CrossOrigin(origins = "*")
public class ACMBC75Z03 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC75Z03.class);

    /**
     * 사용자 상세 조회 처리 (GET)
     * 
     * @param userId 사용자 ID
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserDetail(@RequestParam String userId) throws NewBusinessException {
        logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 시작 (GET)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(userId);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            input.put("userId", userId);

            ASMBC75Z03 asMbc75Z03 = new ASMBC75Z03();
            NewKBData result = asMbc75Z03.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 상세 조회가 완료되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC75Z03 - 사용자 상세 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "사용자 상세 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 사용자 상세 조회 처리 (POST)
     * 
     * @param requestBody 요청 본문
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> getUserDetailPost(@RequestBody Map<String, Object> requestBody)
            throws NewBusinessException {
        logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 시작 (POST)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(requestBody);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);

            // 요청 본문의 모든 파라미터를 input에 추가
            for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
                input.put(entry.getKey(), entry.getValue());
            }

            ASMBC75Z03 asMbc75Z03 = new ASMBC75Z03();
            NewKBData result = asMbc75Z03.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "사용자 상세 조회가 완료되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC75Z03 - 사용자 상세 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "사용자 상세 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            ASMBC75Z03 asMbc75Z03 = new ASMBC75Z03();
            NewKBData result = asMbc75Z03.execute(reqData);

            logger.debug("ACMBC75Z03 - 사용자 상세 조회 요청 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC75Z03 - 사용자 상세 조회 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("사용자 상세 조회 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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

        // 기본적인 입력 데이터 검증
        if (input == null) {
            throw new NewBusinessException("입력 데이터가 null입니다.");
        }

        // userId 파라미터 검증
        Object userId = input.get("userId");
        if (userId == null || userId.toString().trim().isEmpty()) {
            throw new NewBusinessException("사용자 ID는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC75Z03 - 입력 데이터 검증 완료");
    }

    /**
     * 입력 데이터 검증 (String용)
     * 
     * @param userId 사용자 ID
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(String userId) throws NewBusinessException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new NewBusinessException("사용자 ID는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC75Z03 - 입력 데이터 검증 완료");
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

        // userId 파라미터 검증
        Object userId = requestBody.get("userId");
        if (userId == null || userId.toString().trim().isEmpty()) {
            throw new NewBusinessException("사용자 ID는 필수 입력 항목입니다.");
        }

        logger.debug("ACMBC75Z03 - 입력 데이터 검증 완료");
    }
}