package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * MbaToMbcCallTestController - MBA에서 MBC 호출 테스트용 REST API 컨트롤러
 *
 * 웹 브라우저나 API 클라이언트에서 MBA에서 MBC로의 호출을 테스트할 수 있습니다.
 *
 * @author EPlaton Framework Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/mba-mbc-test")
public class MbaToMbcCallTestController {

    private static final Logger logger = LoggerFactory.getLogger(MbaToMbcCallTestController.class);

    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    @Autowired
    private MbaToMbcCallExample mbaToMbcCallExample;

    // MBC 서버 기본 설정
    private static final String MBC_BASE_URL = "http://localhost:8085";

    /**
     * 테스트 메인 페이지
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getTestInfo() {
        logger.info("==================[MbaToMbcCallTestController.getTestInfo] - 테스트 정보 조회");

        Map<String, Object> response = new HashMap<>();
        response.put("service", "MbaToMbcCallTestController");
        response.put("description", "MBA에서 MBC로 호출하는 테스트 API");
        response.put("version", "1.0");
        response.put("availableEndpoints", new String[]{
            "GET /api/mba-mbc-test - 테스트 정보 조회",
            "GET /api/mba-mbc-test/mbc-health - MBC 헬스체크",
            "GET /api/mba-mbc-test/mbc-service-info - MBC 서비스 정보",
            "POST /api/mba-mbc-test/mbc-execute - MBC 비즈니스 로직 실행",
            "POST /api/mba-mbc-test/mbc-user-management - MBC 사용자 관리",
            "POST /api/mba-mbc-test/mbc-account-management - MBC 계정 관리",
            "POST /api/mba-mbc-test/advanced-tps - 고급 TPS 호출",
            "POST /api/mba-mbc-test/async-tps - 비동기 TPS 호출",
            "POST /api/mba-mbc-test/custom-request - 커스텀 요청"
        });
        response.put("mbcBaseUrl", MBC_BASE_URL);
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return ResponseEntity.ok(response);
    }

    /**
     * MBC 헬스체크 테스트
     */
    @GetMapping("/mbc-health")
    public ResponseEntity<Map<String, Object>> testMbcHealthCheck() {
        logger.info("==================[MbaToMbcCallTestController.testMbcHealthCheck] - MBC 헬스체크 테스트");

        try {
            String healthCheckUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(healthCheckUrl);
            
            logger.info("MBC 헬스체크 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC 헬스체크 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC 헬스체크 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC 서비스 정보 조회 테스트
     */
    @GetMapping("/mbc-service-info")
    public ResponseEntity<Map<String, Object>> testMbcServiceInfo() {
        logger.info("==================[MbaToMbcCallTestController.testMbcServiceInfo] - MBC 서비스 정보 조회 테스트");

        try {
            String serviceInfoUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(serviceInfoUrl);
            
            logger.info("MBC 서비스 정보 조회 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC 서비스 정보 조회 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC 서비스 정보 조회 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC 비즈니스 로직 실행 테스트
     */
    @PostMapping("/mbc-execute")
    public ResponseEntity<Map<String, Object>> testMbcExecute(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testMbcExecute] - MBC 비즈니스 로직 실행 테스트");

        try {
            String executeUrl = MBC_BASE_URL + "/mbc/eplaton/api/execute";
            
            // 기본 비즈니스 데이터 (요청이 없으면 기본값 사용)
            Map<String, Object> businessData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                businessData.putAll(requestBody);
            } else {
                businessData.put("bankCode", "001");
                businessData.put("branchCode", "001");
                businessData.put("userId", "USER001");
                businessData.put("systemName", "CashCard");
                businessData.put("actionName", "CashCardBizAction");
                businessData.put("operationName", "COMMO1000");
                businessData.put("operationMethod", "getCardInfo");
                businessData.put("reqName", "CardInfoRequest");
            }
            
            // 요청 데이터 구성
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("requestData", businessData);
            requestData.put("timestamp", System.currentTimeMillis());
            requestData.put("requestId", "REQ_" + System.currentTimeMillis());

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(executeUrl, requestData);
            
            logger.info("MBC 비즈니스 로직 실행 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC 비즈니스 로직 실행 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC 비즈니스 로직 실행 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC 사용자 관리 API 테스트
     */
    @PostMapping("/mbc-user-management")
    public ResponseEntity<Map<String, Object>> testMbcUserManagement(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testMbcUserManagement] - MBC 사용자 관리 API 테스트");

        try {
            String userManagementUrl = MBC_BASE_URL + "/user/management";
            
            // 기본 사용자 데이터 (요청이 없으면 기본값 사용)
            Map<String, Object> userData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                userData.putAll(requestBody);
            } else {
                userData.put("username", "testuser");
                userData.put("email", "test@example.com");
                userData.put("role", "USER");
                userData.put("status", "ACTIVE");
            }

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(userManagementUrl, userData);
            
            logger.info("MBC 사용자 관리 API 호출 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC 사용자 관리 API 호출 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC 사용자 관리 API 호출 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC 계정 관리 API 테스트
     */
    @PostMapping("/mbc-account-management")
    public ResponseEntity<Map<String, Object>> testMbcAccountManagement(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testMbcAccountManagement] - MBC 계정 관리 API 테스트");

        try {
            String accountManagementUrl = MBC_BASE_URL + "/account/management";
            
            // 기본 계정 데이터 (요청이 없으면 기본값 사용)
            Map<String, Object> accountData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                accountData.putAll(requestBody);
            } else {
                accountData.put("accountNumber", "1234567890");
                accountData.put("accountType", "SAVINGS");
            }

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(accountManagementUrl, accountData);
            
            logger.info("MBC 계정 관리 API 호출 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC 계정 관리 API 호출 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC 계정 관리 API 호출 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 고급 TPS 호출 테스트
     */
    @PostMapping("/advanced-tps")
    public ResponseEntity<Map<String, Object>> testAdvancedTpsCall(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testAdvancedTpsCall] - 고급 TPS 호출 테스트");

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
            if (requestBody != null && !requestBody.isEmpty()) {
                businessData.putAll(requestBody);
            } else {
                businessData.put("operation", "ADVANCED_OPERATION");
                businessData.put("parameters", Map.of("param1", "value1", "param2", "value2"));
            }
            
            tpsRequest.setBodyJson(convertMapToJson(businessData));

            // TPS 요청 전송
            TpsResponse tpsResponse = httpTpsSendRecv.send(tpsRequest);
            
            // 응답을 Map으로 변환
            Map<String, Object> response = new HashMap<>();
            response.put("success", tpsResponse.isSuccess());
            response.put("statusCode", tpsResponse.getStatusCode());
            response.put("body", tpsResponse.getBody());
            response.put("headers", tpsResponse.getHeaders());
            response.put("txId", tpsResponse.getTxId());
            response.put("responseTime", tpsResponse.getResponseTime());
            response.put("timestamp", java.time.LocalDateTime.now().toString());
            
            logger.info("고급 TPS 호출 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("고급 TPS 호출 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "고급 TPS 호출 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 비동기 TPS 호출 테스트
     */
    @PostMapping("/async-tps")
    public ResponseEntity<Map<String, Object>> testAsyncTpsCall(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testAsyncTpsCall] - 비동기 TPS 호출 테스트");

        try {
            // TpsRequest 객체 생성
            TpsRequest tpsRequest = new TpsRequest();
            tpsRequest.setTargetUrl(MBC_BASE_URL + "/mbc/eplaton/api/execute");
            tpsRequest.setMethod("POST");
            
            Map<String, Object> asyncData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                asyncData.putAll(requestBody);
            } else {
                asyncData.put("operation", "ASYNC_OPERATION");
                asyncData.put("async", true);
            }
            
            tpsRequest.setBodyJson(convertMapToJson(asyncData));

            // 비동기 TPS 요청 전송
            String txId = "ASYNC_TX_" + System.currentTimeMillis();
            TpsResponse tpsResponse = httpTpsSendRecv.sendAsync(tpsRequest, txId).block();
            
            if (tpsResponse == null) {
                throw new RuntimeException("비동기 요청이 null을 반환했습니다.");
            }
            
            // 응답을 Map으로 변환
            Map<String, Object> response = new HashMap<>();
            response.put("success", tpsResponse.isSuccess());
            response.put("statusCode", tpsResponse.getStatusCode());
            response.put("body", tpsResponse.getBody());
            response.put("headers", tpsResponse.getHeaders());
            response.put("txId", tpsResponse.getTxId());
            response.put("responseTime", tpsResponse.getResponseTime());
            response.put("timestamp", java.time.LocalDateTime.now().toString());
            
            logger.info("비동기 TPS 호출 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("비동기 TPS 호출 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "비동기 TPS 호출 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 커스텀 요청 테스트
     */
    @PostMapping("/custom-request")
    public ResponseEntity<Map<String, Object>> testCustomRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcCallTestController.testCustomRequest] - 커스텀 요청 테스트");

        try {
            String url = (String) requestBody.get("url");
            String method = (String) requestBody.get("method");
            Map<String, Object> data = (Map<String, Object>) requestBody.get("data");
            
            if (url == null || url.isEmpty()) {
                throw new IllegalArgumentException("URL은 필수입니다.");
            }
            
            if (method == null || method.isEmpty()) {
                method = "GET";
            }

            Map<String, Object> response = httpTpsSendRecv.sendRequest(url, method, data);
            
            logger.info("커스텀 요청 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("커스텀 요청 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "커스텀 요청 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
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