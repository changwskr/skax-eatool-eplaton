package com.skax.eatool.mbc.ac.runtimeac;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.as.runtimeas.ASMBC759Z1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 런타임 특별 처리 Application Control
 * 
 * 프로그램명: ACMBC759Z1.java
 * 설명: 런타임 특별 처리를 담당하는 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 런타임 특별 처리 (GET, POST)
 * - 입력 데이터 검증
 * - AS 호출 및 결과 반환
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/runtime/special")
@CrossOrigin(origins = "*")
public class ACMBC759Z1 implements NewIApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ACMBC759Z1.class);

    /**
     * 런타임 특별 처리 (GET)
     * 
     * @param specialType 특별 처리 타입 (선택적)
     * @param specialData 특별 처리 데이터 (선택적)
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> processSpecialRequest(
            @RequestParam(value = "specialType", required = false) String specialType,
            @RequestParam(value = "specialData", required = false) String specialData) throws NewBusinessException {
        logger.debug("ACMBC759Z1 - 런타임 특별 처리 시작 (GET)");

        try {
            // 1. 입력 데이터 검증
            validateInputData(specialType, specialData);

            // 2. AS 호출
            NewKBData reqData = new NewKBData();
            NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
            if (specialType != null && !specialType.trim().isEmpty()) {
                input.put("specialType", specialType);
            }
            if (specialData != null && !specialData.trim().isEmpty()) {
                input.put("specialData", specialData);
            }

            ASMBC759Z1 asMbc759Z1 = new ASMBC759Z1();
            NewKBData result = asMbc759Z1.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "런타임 특별 처리가 완료되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC759Z1 - 런타임 특별 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC759Z1 - 런타임 특별 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "런타임 특별 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 런타임 특별 처리 (POST)
     * 
     * @param requestBody 요청 본문
     * @return 응답 데이터
     * @throws NewBusinessException 비즈니스 예외
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> processSpecialRequestPost(@RequestBody Map<String, Object> requestBody)
            throws NewBusinessException {
        logger.debug("ACMBC759Z1 - 런타임 특별 처리 시작 (POST)");

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

            ASMBC759Z1 asMbc759Z1 = new ASMBC759Z1();
            NewKBData result = asMbc759Z1.execute(reqData);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "런타임 특별 처리가 완료되었습니다.");
            response.put("data", result);

            logger.debug("ACMBC759Z1 - 런타임 특별 처리 완료");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("ACMBC759Z1 - 런타임 특별 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "런타임 특별 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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
        logger.debug("ACMBC759Z1 - 런타임 특별 처리 시작");

        try {
            // 1. 입력 데이터 검증
            validateInputData(reqData);

            // 2. AS 호출
            ASMBC759Z1 asMbc759Z1 = new ASMBC759Z1();
            NewKBData result = asMbc759Z1.execute(reqData);

            logger.debug("ACMBC759Z1 - 런타임 특별 처리 완료");
            return result;

        } catch (Exception e) {
            logger.error("ACMBC759Z1 - 런타임 특별 처리 중 오류 발생: " + e.getMessage(), String.valueOf(e));
            throw new NewBusinessException("런타임 특별 처리 중 오류가 발생했습니다. 원인: " + e.getMessage());
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

        logger.debug("ACMBC759Z1 - 입력 데이터 검증 완료");
    }

    /**
     * 입력 데이터 검증 (파라미터용)
     * 
     * @param specialType 특별 처리 타입
     * @param specialData 특별 처리 데이터
     * @throws NewBusinessException 검증 실패 시
     */
    private void validateInputData(String specialType, String specialData) throws NewBusinessException {
        // 특별 처리는 선택적 파라미터이므로 기본 검증만 수행
        logger.debug("ACMBC759Z1 - 입력 데이터 검증 완료");
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

        logger.debug("ACMBC759Z1 - 입력 데이터 검증 완료");
    }
}