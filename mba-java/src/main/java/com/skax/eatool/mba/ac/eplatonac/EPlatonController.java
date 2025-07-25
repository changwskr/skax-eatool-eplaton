package com.skax.eatool.mba.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.skax.eatool.kji.tpm.*;
import com.skax.eatool.kji.tpm.HttpTPSsendrecv;
import com.skax.eatool.kji.tpm.TpsRequest;
import com.skax.eatool.kji.tpm.TpsResponse;

/**
 * EPlaton Framework Controller
 * 
 * Provides REST API endpoints for EPlaton business delegate operations
 * using POST method for all operations.
 */
@Controller
@RequestMapping("/mba/eplaton")
public class EPlatonController {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private EPlatonBizDelegateSBBean ePlatonBizDelegateSBBean;
    
    @Autowired
    private TPSsendrecv tpsSendRecv;
    
    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    /**
     * EPlaton 메인 페이지 - 관리 페이지로 리다이렉트
     */
    @GetMapping("")
    public String eplatonMain() {
        logger.info("==================[EPlatonController.eplatonMain START]");
        logger.info("==================[EPlatonController.eplatonMain END] - Redirecting to manage page");
        return "redirect:/eplaton/manage";
    }

    /**
     * EPlaton 관리 페이지 표시
     */
    @GetMapping("/manage")
    public String showEPlatonManagePage(Model model) {
        logger.info("==================[EPlatonController.showEPlatonManagePage START]");

        // 기본 설정값들을 모델에 추가
        model.addAttribute("defaultBankCode", "001");
        model.addAttribute("defaultBranchCode", "001");
        model.addAttribute("defaultUserId", "USER001");
        model.addAttribute("defaultSystemName", "CashCard");
        model.addAttribute("defaultActionName", "CashCardBizAction");
        model.addAttribute("defaultOperationName", "COMMO1000");
        model.addAttribute("defaultOperationMethod", "getCardInfo");
        model.addAttribute("defaultReqName", "CardInfoRequest");

        logger.info("==================[EPlatonController.showEPlatonManagePage END]");
        return "eplaton/manage";
    }

    /**
     * Execute EPlaton business delegate operation
     * 
     * @param requestBody Request body containing operation details
     * @return ResponseEntity with operation result
     */
    @PostMapping("/api/execute")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeOperation START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Execute business delegate operation
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.execute(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.executeOperation END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeOperation ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON001");
            errorResponse.put("errorMessage", "Operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Execute read-only EPlaton business delegate operation
     * 
     * @param requestBody Request body containing operation details
     * @return ResponseEntity with operation result
     */
    @PostMapping("/api/execute-readonly")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeReadOnlyOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeReadOnlyOperation START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Execute read-only business delegate operation
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.executeReadOnly(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.executeReadOnlyOperation END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeReadOnlyOperation ERROR] - {}", e.getMessage(),
                    e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON002");
            errorResponse.put("errorMessage", "Read-only operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Route to specific business action
     * 
     * @param requestBody Request body containing action details
     * @return ResponseEntity with action result
     */
    @PostMapping("/api/route-action")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> routeToAction(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.routeToAction START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Route to specific business action
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.routeToAction(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.routeToAction END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.routeToAction ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON003");
            errorResponse.put("errorMessage", "Action routing failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint
     * 
     * @return ResponseEntity with health status
     */
    @PostMapping("/api/health")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("==================[EPlatonController.healthCheck START]");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("status", "UP");
        response.put("service", "EPlatonController");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "EPlaton Controller is running");

        logger.info("==================[EPlatonController.healthCheck END] - Success");
        return ResponseEntity.ok(response);
    }

    // ================== MBC 호출 API 엔드포인트 ==================

    /**
     * MBC EPlaton API 호출 - 일반 실행
     * 
     * @param requestBody 요청 데이터
     * @return ResponseEntity with MBC operation result
     */
    @PostMapping("/api/mbc/execute")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeMbcOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeMbcOperation START] - Request: {}", requestBody);

        try {
            // KJI HttpTPSsendrecv를 통해 MBC 호출
            String mbcUrl = "http://localhost:8085/mbc/eplaton/api/execute";
            Map<String, Object> mbcResponse = httpTpsSendRecv.sendPostRequest(mbcUrl, requestBody);

            logger.info("==================[EPlatonController.executeMbcOperation END] - MBC Response: {}", mbcResponse);
            return ResponseEntity.ok(mbcResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeMbcOperation ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMBC001");
            errorResponse.put("errorMessage", "MBC operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton API 호출 - 읽기 전용 실행
     * 
     * @param requestBody 요청 데이터
     * @return ResponseEntity with MBC operation result
     */
    @PostMapping("/api/mbc/execute-readonly")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeMbcReadOnlyOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeMbcReadOnlyOperation START] - Request: {}", requestBody);

        try {
            // KJI HttpTPSsendrecv를 통해 MBC 호출
            String mbcUrl = "http://localhost:8085/mbc/eplaton/api/execute-readonly";
            Map<String, Object> mbcResponse = httpTpsSendRecv.sendPostRequest(mbcUrl, requestBody);

            logger.info("==================[EPlatonController.executeMbcReadOnlyOperation END] - MBC Response: {}", mbcResponse);
            return ResponseEntity.ok(mbcResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeMbcReadOnlyOperation ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMBC002");
            errorResponse.put("errorMessage", "MBC read-only operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton API 호출 - 라우팅 액션
     * 
     * @param requestBody 요청 데이터
     * @return ResponseEntity with MBC operation result
     */
    @PostMapping("/api/mbc/route-action")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> routeMbcAction(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.routeMbcAction START] - Request: {}", requestBody);

        try {
            // KJI HttpTPSsendrecv를 통해 MBC 호출
            String mbcUrl = "http://localhost:8085/mbc/eplaton/api/route-action";
            Map<String, Object> mbcResponse = httpTpsSendRecv.sendPostRequest(mbcUrl, requestBody);

            logger.info("==================[EPlatonController.routeMbcAction END] - MBC Response: {}", mbcResponse);
            return ResponseEntity.ok(mbcResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.routeMbcAction ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMBC003");
            errorResponse.put("errorMessage", "MBC route action failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * MBC EPlaton API 호출 - 헬스 체크
     * 
     * @return ResponseEntity with MBC health status
     */
    @PostMapping("/api/mbc/health")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkMbcHealth() {
        logger.info("==================[EPlatonController.checkMbcHealth START]");

        try {
            // KJI HttpTPSsendrecv를 통해 MBC 헬스 체크 호출
            String mbcUrl = "http://localhost:8085/mbc/eplaton/api/health";
            Map<String, Object> mbcResponse = httpTpsSendRecv.sendGetRequest(mbcUrl);

            logger.info("==================[EPlatonController.checkMbcHealth END] - MBC Response: {}", mbcResponse);
            return ResponseEntity.ok(mbcResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.checkMbcHealth ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMBC004");
            errorResponse.put("errorMessage", "MBC health check failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * MBC 서비스 정보 조회
     * 
     * @return ResponseEntity with MBC service information
     */
    @GetMapping("/api/mbc/service-info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getMbcServiceInfo() {
        logger.info("==================[EPlatonController.getMbcServiceInfo START]");

        try {
            // KJI HttpTPSsendrecv를 통해 MBC 서비스 정보 조회
            String mbcUrl = "http://localhost:8085/mbc/eplaton/api/health";
            Map<String, Object> mbcResponse = httpTpsSendRecv.sendGetRequest(mbcUrl);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("serviceInfo", mbcResponse);
            response.put("timestamp", LocalDateTime.now().toString());

            logger.info("==================[EPlatonController.getMbcServiceInfo END] - Service Info: {}", mbcResponse);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.getMbcServiceInfo ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMBC005");
            errorResponse.put("errorMessage", "Failed to get MBC service info: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ================== 범용 HTTP 호출 API 엔드포인트 ==================

    /**
     * 범용 HTTP POST 요청 전송
     * 
     * @param requestBody 요청 데이터 (targetUrl, requestData 포함)
     * @return ResponseEntity with HTTP response
     */
    @PostMapping("/api/http/post")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendHttpPostRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.sendHttpPostRequest START] - Request: {}", requestBody);

        try {
            String targetUrl = (String) requestBody.get("targetUrl");
            @SuppressWarnings("unchecked")
            Map<String, Object> requestData = (Map<String, Object>) requestBody.get("requestData");
            
            if (targetUrl == null || targetUrl.trim().isEmpty()) {
                throw new IllegalArgumentException("targetUrl is required");
            }
            
            if (requestData == null) {
                requestData = new HashMap<>();
            }

            // KJI HttpTPSsendrecv를 통해 HTTP POST 요청 전송
            Map<String, Object> httpResponse = httpTpsSendRecv.sendPostRequest(targetUrl, requestData);

            logger.info("==================[EPlatonController.sendHttpPostRequest END] - Response: {}", httpResponse);
            return ResponseEntity.ok(httpResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.sendHttpPostRequest ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP001");
            errorResponse.put("errorMessage", "HTTP POST request failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 범용 HTTP GET 요청 전송
     * 
     * @param requestBody 요청 데이터 (targetUrl 포함)
     * @return ResponseEntity with HTTP response
     */
    @PostMapping("/api/http/get")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendHttpGetRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.sendHttpGetRequest START] - Request: {}", requestBody);

        try {
            String targetUrl = (String) requestBody.get("targetUrl");
            
            if (targetUrl == null || targetUrl.trim().isEmpty()) {
                throw new IllegalArgumentException("targetUrl is required");
            }

            // KJI HttpTPSsendrecv를 통해 HTTP GET 요청 전송
            Map<String, Object> httpResponse = httpTpsSendRecv.sendGetRequest(targetUrl);

            logger.info("==================[EPlatonController.sendHttpGetRequest END] - Response: {}", httpResponse);
            return ResponseEntity.ok(httpResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.sendHttpGetRequest ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP002");
            errorResponse.put("errorMessage", "HTTP GET request failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 범용 HTTP 요청 전송 (메서드 지정 가능)
     * 
     * @param requestBody 요청 데이터 (targetUrl, method, requestData 포함)
     * @return ResponseEntity with HTTP response
     */
    @PostMapping("/api/http/request")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendHttpRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.sendHttpRequest START] - Request: {}", requestBody);

        try {
            String targetUrl = (String) requestBody.get("targetUrl");
            String method = (String) requestBody.get("method");
            @SuppressWarnings("unchecked")
            Map<String, Object> requestData = (Map<String, Object>) requestBody.get("requestData");
            
            if (targetUrl == null || targetUrl.trim().isEmpty()) {
                throw new IllegalArgumentException("targetUrl is required");
            }
            
            if (method == null || method.trim().isEmpty()) {
                method = "GET";
            }
            
            if (requestData == null) {
                requestData = new HashMap<>();
            }

            // KJI HttpTPSsendrecv를 통해 HTTP 요청 전송
            Map<String, Object> httpResponse = httpTpsSendRecv.sendRequest(targetUrl, method, requestData);

            logger.info("==================[EPlatonController.sendHttpRequest END] - Response: {}", httpResponse);
            return ResponseEntity.ok(httpResponse);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.sendHttpRequest ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP003");
            errorResponse.put("errorMessage", "HTTP request failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * TPSsendrecv 모듈 정보 조회
     * 
     * @return ResponseEntity with module information
     */
    @GetMapping("/api/module-info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getModuleInfo() {
        logger.info("==================[EPlatonController.getModuleInfo START]");

        try {
            Map<String, Object> moduleInfo = tpsSendRecv.getModuleInfo();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("moduleInfo", moduleInfo);
            response.put("timestamp", LocalDateTime.now().toString());

            logger.info("==================[EPlatonController.getModuleInfo END] - Module Info: {}", moduleInfo);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.getModuleInfo ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EMOD001");
            errorResponse.put("errorMessage", "Failed to get module info: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ================== HttpTPSsendrecv API 엔드포인트 ==================

    /**
     * HttpTPSsendrecv 모듈 정보 조회
     * 
     * @return ResponseEntity with HttpTPSsendrecv module information
     */
    @GetMapping("/api/http-tps/module-info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getHttpTpsModuleInfo() {
        logger.info("==================[EPlatonController.getHttpTpsModuleInfo START]");

        try {
            Map<String, Object> moduleInfo = httpTpsSendRecv.getModuleInfo();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("moduleInfo", moduleInfo);
            response.put("timestamp", LocalDateTime.now().toString());

            logger.info("==================[EPlatonController.getHttpTpsModuleInfo END] - Module Info: {}", moduleInfo);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.getHttpTpsModuleInfo ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP001");
            errorResponse.put("errorMessage", "Failed to get HttpTPSsendrecv module info: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * HttpTPSsendrecv를 사용한 TPS 요청 전송
     * 
     * @param requestBody 요청 데이터 (TpsRequest 형식)
     * @return ResponseEntity with TPS response
     */
    @PostMapping("/api/http-tps/send")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendTpsRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.sendTpsRequest START] - Request: {}", requestBody);

        try {
            // Map을 TpsRequest로 변환
            com.skax.eatool.kji.tpm.TpsRequest tpsRequest = createTpsRequestFromMap(requestBody);
            
            // HttpTPSsendrecv를 통해 TPS 요청 전송
            com.skax.eatool.kji.tpm.TpsResponse tpsResponse = httpTpsSendRecv.send(tpsRequest);
            
            // TpsResponse를 Map으로 변환
            Map<String, Object> response = convertTpsResponseToMap(tpsResponse);

            logger.info("==================[EPlatonController.sendTpsRequest END] - Response: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.sendTpsRequest ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP002");
            errorResponse.put("errorMessage", "TPS request failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * HttpTPSsendrecv를 사용한 비동기 TPS 요청 전송
     * 
     * @param requestBody 요청 데이터 (TpsRequest 형식)
     * @return ResponseEntity with TPS response
     */
    @PostMapping("/api/http-tps/send-async")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendTpsRequestAsync(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.sendTpsRequestAsync START] - Request: {}", requestBody);

        try {
            // Map을 TpsRequest로 변환
            com.skax.eatool.kji.tpm.TpsRequest tpsRequest = createTpsRequestFromMap(requestBody);
            
            // 트랜잭션 ID 생성
            String txId = "TX_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
            
            // HttpTPSsendrecv를 통해 비동기 TPS 요청 전송
            com.skax.eatool.kji.tpm.TpsResponse tpsResponse = httpTpsSendRecv.sendAsync(tpsRequest, txId).block();
            
            if (tpsResponse == null) {
                throw new RuntimeException("Async request timeout");
            }
            
            // TpsResponse를 Map으로 변환
            Map<String, Object> response = convertTpsResponseToMap(tpsResponse);

            logger.info("==================[EPlatonController.sendTpsRequestAsync END] - Response: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.sendTpsRequestAsync ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EHTTP003");
            errorResponse.put("errorMessage", "Async TPS request failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Map을 TpsRequest로 변환
     */
    private com.skax.eatool.kji.tpm.TpsRequest createTpsRequestFromMap(Map<String, Object> requestBody) {
        com.skax.eatool.kji.tpm.TpsRequest request = new com.skax.eatool.kji.tpm.TpsRequest();
        
        if (requestBody.containsKey("targetUrl")) {
            request.setTargetUrl((String) requestBody.get("targetUrl"));
        }
        
        if (requestBody.containsKey("method")) {
            request.setMethod((String) requestBody.get("method"));
        }
        
        if (requestBody.containsKey("bodyJson")) {
            request.setBodyJson((String) requestBody.get("bodyJson"));
        }
        
        if (requestBody.containsKey("headers")) {
            @SuppressWarnings("unchecked")
            Map<String, String> headers = (Map<String, String>) requestBody.get("headers");
            request.setHeaders(headers);
        }
        
        return request;
    }

    /**
     * TpsResponse를 Map으로 변환
     */
    private Map<String, Object> convertTpsResponseToMap(com.skax.eatool.kji.tpm.TpsResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", response.isSuccess());
        result.put("statusCode", response.getStatusCode());
        result.put("body", response.getBody());
        result.put("headers", response.getHeaders());
        result.put("txId", response.getTxId());
        result.put("responseTime", response.getResponseTime());
        result.put("contentType", response.getContentType());
        result.put("timestamp", LocalDateTime.now().toString());
        return result;
    }

    /**
     * Create EPlatonEvent from request body
     */
    private EPlatonEvent createEPlatonEventFromRequest(Map<String, Object> requestBody) {
        EPlatonEvent event = new EPlatonEvent();

        // Set common information
        EPlatonCommonDTO common = new EPlatonCommonDTO();
        if (requestBody.containsKey("common")) {
            Map<String, Object> commonData = (Map<String, Object>) requestBody.get("common");
            if (commonData.containsKey("bankCode")) {
                common.setBankCode((String) commonData.get("bankCode"));
            }
            if (commonData.containsKey("branchCode")) {
                common.setBranchCode((String) commonData.get("branchCode"));
            }
            if (commonData.containsKey("userId")) {
                common.setUserID((String) commonData.get("userId"));
            }
        }
        event.setCommon(common);

        // Set TPSVCINFO
        TPSVCINFODTO tpmsvc = new TPSVCINFODTO();
        if (requestBody.containsKey("actionName")) {
            tpmsvc.setAction_name((String) requestBody.get("actionName"));
        }
        if (requestBody.containsKey("systemName")) {
            tpmsvc.setSystem_name((String) requestBody.get("systemName"));
        }
        if (requestBody.containsKey("operationName")) {
            tpmsvc.setOperation_name((String) requestBody.get("operationName"));
        }
        if (requestBody.containsKey("operationMethod")) {
            tpmsvc.setOperation_method((String) requestBody.get("operationMethod"));
        }
        if (requestBody.containsKey("reqName")) {
            tpmsvc.setReqName((String) requestBody.get("reqName"));
        }

        // Set current timestamp
        String currentDate = LocalDateTime.now().format(DATE_FORMATTER);
        String currentTime = LocalDateTime.now().format(TIME_FORMATTER);
        tpmsvc.setSystem_date(currentDate);
        tpmsvc.setWeb_intime(currentTime);

        event.setTPSVCINFODTO(tpmsvc);

        // Set request data
        if (requestBody.containsKey("request")) {
            // Create a simple DTO for request data
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setData(requestBody.get("request"));
            event.setRequest(requestDTO);
        }

        // Set action
        if (requestBody.containsKey("action")) {
            event.setAction((String) requestBody.get("action"));
        }

        // Set source and type
        event.setSource("REST_API");
        event.setType("POST");

        return event;
    }

    /**
     * Create response from EPlatonEvent
     */
    private Map<String, Object> createResponseFromEvent(EPlatonEvent event) {
        Map<String, Object> response = new HashMap<>();

        if (event != null) {
            response.put("success", true);
            response.put("timestamp", LocalDateTime.now().toString());

            // Add TPSVCINFO
            if (event.getTPSVCINFODTO() != null) {
                Map<String, Object> tpmsvc = new HashMap<>();
                tpmsvc.put("actionName", event.getTPSVCINFODTO().getAction_name());
                tpmsvc.put("systemName", event.getTPSVCINFODTO().getSystem_name());
                tpmsvc.put("operationName", event.getTPSVCINFODTO().getOperation_name());
                tpmsvc.put("errorCode", event.getTPSVCINFODTO().getErrorcode());
                tpmsvc.put("errorMessage", event.getTPSVCINFODTO().getError_message());
                tpmsvc.put("systemDate", event.getTPSVCINFODTO().getSystem_date());
                tpmsvc.put("webInTime", event.getTPSVCINFODTO().getWeb_intime());
                tpmsvc.put("webOutTime", event.getTPSVCINFODTO().getWeb_outtime());
                response.put("tpmsvc", tpmsvc);
            }

            // Add common info
            if (event.getCommon() != null) {
                Map<String, Object> common = new HashMap<>();
                common.put("bankCode", event.getCommon().getBankCode());
                common.put("branchCode", event.getCommon().getBranchCode());
                common.put("userId", event.getCommon().getUserID());
                response.put("common", common);
            }

            // Add response data
            if (event.getResponse() != null) {
                response.put("response", event.getResponse());
            }

            // Add request data
            if (event.getRequest() != null) {
                response.put("request", event.getRequest());
            }

            // Add action
            response.put("action", event.getAction());
            response.put("source", event.getSource());
            response.put("type", event.getType());

        } else {
            response.put("success", false);
            response.put("errorMessage", "No response event received");
        }

        return response;
    }

    /**
     * Simple DTO for request data
     */
    private static class RequestDTO implements IDTO {
        private String id;
        private String status;
        private String message;
        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getStatus() {
            return status;
        }

        @Override
        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "RequestDTO{id='" + id + "', status='" + status + "', message='" + message + "', data=" + data + "}";
        }
    }
}
