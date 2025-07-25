package com.skax.eatool.mba.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * MbaToMbcEplatonController - MBA에서 MBC의 EPlaton API 호출 컨트롤러
 *
 * HttpTPSsendrecv를 사용하여 mba-java에서 mbc-java의 eplaton API를 호출하는 예제입니다.
 *
 * @author EPlaton Framework Team
 * @version 1.0
 */
@Controller
@RequestMapping("/mba/eplaton/mbc-call")
public class MbaToMbcEplatonController {

    private static final Logger logger = LoggerFactory.getLogger(MbaToMbcEplatonController.class);

    @Autowired
    private com.skax.eatool.kji.tpm.HttpTPSsendrecv httpTpsSendRecv;

    // MBC 서버 기본 설정
    private static final String MBC_BASE_URL = "http://localhost:8085";

    /**
     * MBC EPlaton API 호출 메인 페이지
     */
    @GetMapping("")
    public String getMbcCallPage() {
        logger.info("==================[MbaToMbcEplatonController.getMbcCallPage] - MBC 호출 페이지 표시");
        return "eplaton/mbc-call";
    }

    /**
     * MBC EPlaton 헬스체크 호출
     */
    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcEplatonHealth() {
        logger.info("==================[MbaToMbcEplatonController.callMbcEplatonHealth] - MBC EPlaton 헬스체크 호출");

        try {
            String healthCheckUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(healthCheckUrl);
            
            logger.info("MBC EPlaton 헬스체크 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC EPlaton 헬스체크 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC EPlaton 헬스체크 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton 비즈니스 로직 실행
     */
    @PostMapping("/execute")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcEplatonExecute(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcEplatonController.callMbcEplatonExecute] - MBC EPlaton 비즈니스 로직 실행");

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
            requestData.put("source", "MBA-EPlaton");

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(executeUrl, requestData);
            
            logger.info("MBC EPlaton 비즈니스 로직 실행 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC EPlaton 비즈니스 로직 실행 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC EPlaton 비즈니스 로직 실행 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton 읽기 전용 실행
     */
    @PostMapping("/execute-readonly")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcEplatonExecuteReadOnly(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcEplatonController.callMbcEplatonExecuteReadOnly] - MBC EPlaton 읽기 전용 실행");

        try {
            String executeReadOnlyUrl = MBC_BASE_URL + "/mbc/eplaton/api/execute-readonly";
            
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
                businessData.put("readOnly", true);
            }
            
            // 요청 데이터 구성
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("requestData", businessData);
            requestData.put("timestamp", System.currentTimeMillis());
            requestData.put("requestId", "REQ_READONLY_" + System.currentTimeMillis());
            requestData.put("source", "MBA-EPlaton");

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(executeReadOnlyUrl, requestData);
            
            logger.info("MBC EPlaton 읽기 전용 실행 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC EPlaton 읽기 전용 실행 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC EPlaton 읽기 전용 실행 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton 액션 라우팅
     */
    @PostMapping("/route-action")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcEplatonRouteAction(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcEplatonController.callMbcEplatonRouteAction] - MBC EPlaton 액션 라우팅");

        try {
            String routeActionUrl = MBC_BASE_URL + "/mbc/eplaton/api/route-action";
            
            // 기본 라우팅 데이터 (요청이 없으면 기본값 사용)
            Map<String, Object> routingData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                routingData.putAll(requestBody);
            } else {
                routingData.put("actionName", "CashCardBizAction");
                routingData.put("operationName", "COMMO1000");
                routingData.put("parameters", Map.of(
                    "cardNumber", "1234567890123456",
                    "cardType", "CREDIT"
                ));
            }
            
            // 요청 데이터 구성
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("routingData", routingData);
            requestData.put("timestamp", System.currentTimeMillis());
            requestData.put("requestId", "REQ_ROUTE_" + System.currentTimeMillis());
            requestData.put("source", "MBA-EPlaton");

            Map<String, Object> response = httpTpsSendRecv.sendPostRequest(routeActionUrl, requestData);
            
            logger.info("MBC EPlaton 액션 라우팅 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC EPlaton 액션 라우팅 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC EPlaton 액션 라우팅 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton 서비스 정보 조회
     */
    @GetMapping("/service-info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcEplatonServiceInfo() {
        logger.info("==================[MbaToMbcEplatonController.callMbcEplatonServiceInfo] - MBC EPlaton 서비스 정보 조회");

        try {
            String serviceInfoUrl = MBC_BASE_URL + "/mbc/eplaton/api/health";
            
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest(serviceInfoUrl);
            
            logger.info("MBC EPlaton 서비스 정보 조회 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("MBC EPlaton 서비스 정보 조회 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "MBC EPlaton 서비스 정보 조회 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 커스텀 MBC 요청
     */
    @PostMapping("/custom-request")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcCustomRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcEplatonController.callMbcCustomRequest] - 커스텀 MBC 요청");

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

            // MBC 서버 URL로 변환
            if (!url.startsWith("http")) {
                url = MBC_BASE_URL + url;
            }

            Map<String, Object> response = httpTpsSendRecv.sendRequest(url, method, data);
            
            logger.info("커스텀 MBC 요청 성공: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("커스텀 MBC 요청 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "커스텀 MBC 요청 실패: " + e.getMessage());
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 고급 TPS 호출 예제
     */
    @PostMapping("/advanced-tps")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> callMbcAdvancedTps(@RequestBody(required = false) Map<String, Object> requestBody) {
        logger.info("==================[MbaToMbcEplatonController.callMbcAdvancedTps] - 고급 TPS 호출");

        try {
            // TpsRequest 객체 생성
            com.skax.eatool.kji.tpm.TpsRequest tpsRequest = new com.skax.eatool.kji.tpm.TpsRequest();
            tpsRequest.setTargetUrl(MBC_BASE_URL + "/mbc/eplaton/api/execute");
            tpsRequest.setMethod("POST");
            
            // 커스텀 헤더 추가
            tpsRequest.addHeader("X-Custom-Header", "MBA-EPlaton-Advanced");
            tpsRequest.addHeader("X-Request-Source", "MBA-EPlaton");
            tpsRequest.addHeader("X-Request-Type", "Advanced-TPS");
            
            // JSON 본문 설정
            Map<String, Object> businessData = new HashMap<>();
            if (requestBody != null && !requestBody.isEmpty()) {
                businessData.putAll(requestBody);
            } else {
                businessData.put("operation", "ADVANCED_OPERATION");
                businessData.put("parameters", Map.of("param1", "value1", "param2", "value2"));
                businessData.put("advanced", true);
            }
            
            tpsRequest.setBodyJson(convertMapToJson(businessData));

            // TPS 요청 전송
            com.skax.eatool.kji.tpm.TpsResponse tpsResponse = httpTpsSendRecv.send(tpsRequest);
            
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