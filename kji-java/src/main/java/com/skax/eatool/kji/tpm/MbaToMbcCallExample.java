package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * MbaToMbcCallExample - MBA에서 MBC로 호출하는 샘플 예제
 *
 * 이 클래스는 HttpTPSsendrecv를 사용하여 mba-java에서 mbc-java로
 * HTTP 요청을 보내는 다양한 시나리오를 보여줍니다.
 *
 * @author EPlaton Framework Team
 * @version 1.0
 */
@Component
public class MbaToMbcCallExample implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MbaToMbcCallExample.class);

    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    // MBC 서버 기본 설정
    private static final String MBC_BASE_URL = "http://localhost:8085";
    private static final String MBA_BASE_URL = "http://localhost:8084";

    @Override
    public void run(String... args) throws Exception {
        logger.info("==================[MbaToMbcCallExample] - MBA에서 MBC 호출 샘플 시작");

        try {
            // 1. 기본 헤더 설정
            setupDefaultHeaders();

            // 2. MBC 헬스체크 호출
            callMbcHealthCheck();

            // 3. MBC 서비스 정보 조회
            getMbcServiceInfo();

            // 4. MBC 비즈니스 로직 실행
            executeMbcBusinessLogic();

            // 5. MBC 사용자 관리 API 호출
            callMbcUserManagement();

            // 6. MBC 계정 관리 API 호출
            callMbcAccountManagement();

            // 7. 에러 처리 예제
            demonstrateErrorHandling();

            logger.info("==================[MbaToMbcCallExample] - 모든 샘플 완료");

        } catch (Exception e) {
            logger.error("==================[MbaToMbcCallExample] - 샘플 실행 중 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * 기본 헤더 설정
     */
    private void setupDefaultHeaders() {
        logger.info("==================[MbaToMbcCallExample.setupDefaultHeaders] - 기본 헤더 설정");

        httpTpsSendRecv.setDefaultHeader("User-Agent", "MBA-EPlaton/1.0");
        httpTpsSendRecv.setDefaultHeader("Accept", "application/json");
        httpTpsSendRecv.setDefaultHeader("Content-Type", "application/json");
        httpTpsSendRecv.setDefaultHeader("X-Source-System", "MBA");
        httpTpsSendRecv.setDefaultHeader("X-Target-System", "MBC");

        logger.info("설정된 기본 헤더: {}", httpTpsSendRecv.getDefaultHeaders());
    }

    /**
     * MBC 헬스체크 호출
     */
    private void callMbcHealthCheck() {
        logger.info("==================[MbaToMbcCallExample.callMbcHealthCheck] - MBC 헬스체크 호출");

        try {
            String healthCheckUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(healthCheckUrl);
            
            logger.info("MBC 헬스체크 응답: {}", response);
            
            if (Boolean.TRUE.equals(response.get("success"))) {
                logger.info("✅ MBC 서버가 정상 상태입니다.");
            } else {
                logger.warn("⚠️ MBC 서버에 문제가 있습니다.");
            }

        } catch (TpsException e) {
            logger.error("❌ MBC 헬스체크 실패: {}", e.getMessage());
        }
    }

    /**
     * MBC 서비스 정보 조회
     */
    private void getMbcServiceInfo() {
        logger.info("==================[MbaToMbcCallExample.getMbcServiceInfo] - MBC 서비스 정보 조회");

        try {
            String serviceInfoUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(serviceInfoUrl);
            
            logger.info("MBC 서비스 정보: {}", response);

        } catch (TpsException e) {
            logger.error("❌ MBC 서비스 정보 조회 실패: {}", e.getMessage());
        }
    }

    /**
     * MBC 비즈니스 로직 실행
     */
    private void executeMbcBusinessLogic() {
        logger.info("==================[MbaToMbcCallExample.executeMbcBusinessLogic] - MBC 비즈니스 로직 실행");

        try {
            String executeUrl = MBC_BASE_URL + "/mbc/eplaton/api/execute";
            
            // MBC로 전송할 비즈니스 데이터
            Map<String, Object> businessData = new HashMap<>();
            businessData.put("bankCode", "001");
            businessData.put("branchCode", "001");
            businessData.put("userId", "USER001");
            businessData.put("systemName", "CashCard");
            businessData.put("actionName", "CashCardBizAction");
            businessData.put("operationName", "COMMO1000");
            businessData.put("operationMethod", "getCardInfo");
            businessData.put("reqName", "CardInfoRequest");
            
            // 요청 데이터 구성
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("requestData", businessData);
            requestBody.put("timestamp", System.currentTimeMillis());
            requestBody.put("requestId", "REQ_" + System.currentTimeMillis());

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(executeUrl, requestBody);
            
            logger.info("MBC 비즈니스 로직 실행 결과: {}", response);

        } catch (TpsException e) {
            logger.error("❌ MBC 비즈니스 로직 실행 실패: {}", e.getMessage());
        }
    }

    /**
     * MBC 사용자 관리 API 호출
     */
    private void callMbcUserManagement() {
        logger.info("==================[MbaToMbcCallExample.callMbcUserManagement] - MBC 사용자 관리 API 호출");

        try {
            String userManagementUrl = MBC_BASE_URL + "/user/management";
            
            // 사용자 생성 요청 데이터
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", "testuser");
            userData.put("email", "test@example.com");
            userData.put("role", "USER");
            userData.put("status", "ACTIVE");

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(userManagementUrl, userData);
            
            logger.info("MBC 사용자 관리 API 응답: {}", response);

        } catch (TpsException e) {
            logger.error("❌ MBC 사용자 관리 API 호출 실패: {}", e.getMessage());
        }
    }

    /**
     * MBC 계정 관리 API 호출
     */
    private void callMbcAccountManagement() {
        logger.info("==================[MbaToMbcCallExample.callMbcAccountManagement] - MBC 계정 관리 API 호출");

        try {
            String accountManagementUrl = MBC_BASE_URL + "/account/management";
            
            // 계정 정보 조회 요청
            Map<String, Object> accountData = new HashMap<>();
            accountData.put("accountNumber", "1234567890");
            accountData.put("accountType", "SAVINGS");

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(accountManagementUrl, accountData);
            
            logger.info("MBC 계정 관리 API 응답: {}", response);

        } catch (TpsException e) {
            logger.error("❌ MBC 계정 관리 API 호출 실패: {}", e.getMessage());
        }
    }

    /**
     * 에러 처리 예제
     */
    private void demonstrateErrorHandling() {
        logger.info("==================[MbaToMbcCallExample.demonstrateErrorHandling] - 에러 처리 예제");

        // 1. 존재하지 않는 URL 호출
        try {
            String invalidUrl = MBC_BASE_URL + "/nonexistent/api";
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(invalidUrl);
            logger.info("존재하지 않는 URL 응답: {}", response);
        } catch (TpsException e) {
            logger.info("✅ 예상된 에러 처리됨: {}", e.getMessage());
        }

        // 2. 잘못된 데이터로 POST 요청
        try {
            String executeUrl = MBC_BASE_URL + "/mbc/eplaton/api/execute";
            Map<String, Object> invalidData = new HashMap<>();
            invalidData.put("invalidField", "invalidValue");
            
            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(executeUrl, invalidData);
            logger.info("잘못된 데이터 POST 응답: {}", response);
        } catch (TpsException e) {
            logger.info("✅ 예상된 에러 처리됨: {}", e.getMessage());
        }
    }

    /**
     * TPS 요청을 사용한 고급 호출 예제
     */
    public void advancedTpsCallExample() {
        logger.info("==================[MbaToMbcCallExample.advancedTpsCallExample] - 고급 TPS 호출 예제");

        try {
            // TpsRequest 객체 생성
            TpsRequest tpsRequest = new TpsRequest();
            tpsRequest.setTargetUrl(MBC_BASE_URL + "/mbc/eplaton/api/execute");
            tpsRequest.setMethod("POST");
            
            // 커스텀 헤더 추가
            tpsRequest.addHeader("X-Custom-Header", "CustomValue");
            tpsRequest.addHeader("X-Request-Source", "MBA-Advanced");
            
            // JSON 본문 설정
            Map<String, Object> businessData = new HashMap<>();
            businessData.put("operation", "ADVANCED_OPERATION");
            businessData.put("parameters", Map.of("param1", "value1", "param2", "value2"));
            
            tpsRequest.setBodyJson(convertMapToJson(businessData));

            // TPS 요청 전송
            TpsResponse tpsResponse = httpTpsSendRecv.send(tpsRequest);
            
            logger.info("고급 TPS 호출 결과 - Status: {}, Body: {}", 
                       tpsResponse.getStatusCode(), tpsResponse.getBody());

        } catch (TpsException e) {
            logger.error("❌ 고급 TPS 호출 실패: {}", e.getMessage());
        }
    }

    /**
     * 비동기 TPS 호출 예제
     */
    public void asyncTpsCallExample() {
        logger.info("==================[MbaToMbcCallExample.asyncTpsCallExample] - 비동기 TPS 호출 예제");

        try {
            // TpsRequest 객체 생성
            TpsRequest tpsRequest = new TpsRequest();
            tpsRequest.setTargetUrl(MBC_BASE_URL + "/mbc/eplaton/api/execute");
            tpsRequest.setMethod("POST");
            
            Map<String, Object> asyncData = new HashMap<>();
            asyncData.put("operation", "ASYNC_OPERATION");
            asyncData.put("async", true);
            
            tpsRequest.setBodyJson(convertMapToJson(asyncData));

            // 비동기 TPS 요청 전송
            String txId = "ASYNC_TX_" + System.currentTimeMillis();
            httpTpsSendRecv.sendAsync(tpsRequest, txId)
                .subscribe(
                    response -> logger.info("✅ 비동기 TPS 호출 성공 - TxId: {}, Status: {}", 
                                          txId, response.getStatusCode()),
                    error -> logger.error("❌ 비동기 TPS 호출 실패 - TxId: {}, Error: {}", 
                                        txId, error.getMessage())
                );

        } catch (Exception e) {
            logger.error("❌ 비동기 TPS 호출 설정 실패: {}", e.getMessage());
        }
    }

    /**
     * Map을 JSON 문자열로 변환 (간단한 구현)
     */
    private String convertMapToJson(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return "{}";
        }

        StringBuilder json = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                json.append(",");
            }
            first = false;

            String key = entry.getKey();
            Object value = entry.getValue();

            json.append("\"").append(escapeJson(key)).append("\":");

            if (value == null) {
                json.append("null");
            } else if (value instanceof String) {
                json.append("\"").append(escapeJson((String) value)).append("\"");
            } else if (value instanceof Number || value instanceof Boolean) {
                json.append(value.toString());
            } else {
                json.append("\"").append(escapeJson(value.toString())).append("\"");
            }
        }

        json.append("}");
        return json.toString();
    }

    /**
     * JSON 문자열 이스케이프
     */
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }

        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
} 