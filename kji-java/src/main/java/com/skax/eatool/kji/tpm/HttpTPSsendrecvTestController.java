package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpTPSsendrecv 모듈 테스트용 REST API 컨트롤러
 * 
 * 웹 브라우저나 API 클라이언트에서 HttpTPSsendrecv의 기능을 테스트할 수 있습니다.
 */
@RestController
@RequestMapping("/kji/tpm/test")
@CrossOrigin(origins = "*")
public class HttpTPSsendrecvTestController {

    private static final Logger logger = LoggerFactory.getLogger(HttpTPSsendrecvTestController.class);

    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    @Autowired
    private HttpTPSsendrecvUsageGuide usageGuide;

    /**
     * 모듈 정보 조회
     */
    @GetMapping("/module-info")
    public ResponseEntity<Map<String, Object>> getModuleInfo() {
        logger.info("==================[HttpTPSsendrecvTestController.getModuleInfo] - 모듈 정보 조회");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("moduleInfo", httpTpsSendRecv.getModuleInfo());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * 기본 GET 요청 테스트
     */
    @GetMapping("/test-get")
    public ResponseEntity<Map<String, Object>> testGetRequest() {
        logger.info("==================[HttpTPSsendrecvTestController.testGetRequest] - GET 요청 테스트");
        
        try {
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest("http://localhost:8085/mbc/eplaton/api/health");
            response.put("testType", "GET");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("GET 요청 테스트 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("testType", "GET");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 기본 POST 요청 테스트
     */
    @PostMapping("/test-post")
    public ResponseEntity<Map<String, Object>> testPostRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[HttpTPSsendrecvTestController.testPostRequest] - POST 요청 테스트");
        
        try {
            Map<String, Object> response = httpTpsSendRecv.sendPostRequest("http://localhost:8085/mbc/eplaton/api/execute", requestBody);
            response.put("testType", "POST");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("POST 요청 테스트 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("testType", "POST");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 커스텀 HTTP 요청 테스트
     */
    @PostMapping("/test-custom")
    public ResponseEntity<Map<String, Object>> testCustomRequest(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[HttpTPSsendrecvTestController.testCustomRequest] - 커스텀 요청 테스트");
        
        try {
            String method = (String) requestBody.getOrDefault("method", "GET");
            String url = (String) requestBody.get("url");
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) requestBody.get("data");
            
            if (url == null) {
                throw new IllegalArgumentException("URL is required");
            }
            
            Map<String, Object> response = httpTpsSendRecv.sendRequest(url, method, data);
            response.put("testType", "CUSTOM");
            response.put("requestMethod", method);
            response.put("requestUrl", url);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("커스텀 요청 테스트 실패: {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            errorResponse.put("testType", "CUSTOM");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * 에러 처리 테스트
     */
    @GetMapping("/test-errors")
    public ResponseEntity<Map<String, Object>> testErrorHandling() {
        logger.info("==================[HttpTPSsendrecvTestController.testErrorHandling] - 에러 처리 테스트");
        
        Map<String, Object> results = new HashMap<>();
        results.put("success", true);
        results.put("testType", "ERROR_HANDLING");
        results.put("timestamp", System.currentTimeMillis());
        results.put("tests", new HashMap<String, Object>());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> tests = (Map<String, Object>) results.get("tests");
        
        // 404 에러 테스트
        try {
            Map<String, Object> notFoundResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/status/404");
            tests.put("404_error", notFoundResponse);
        } catch (Exception e) {
            tests.put("404_error", Map.of("error", e.getMessage()));
        }
        
        // 500 에러 테스트
        try {
            Map<String, Object> serverErrorResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/status/500");
            tests.put("500_error", serverErrorResponse);
        } catch (Exception e) {
            tests.put("500_error", Map.of("error", e.getMessage()));
        }
        
        // 타임아웃 테스트
        try {
            Map<String, Object> timeoutResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/delay/10");
            tests.put("timeout", timeoutResponse);
        } catch (Exception e) {
            tests.put("timeout", Map.of("error", e.getMessage()));
        }
        
        return ResponseEntity.ok(results);
    }

    /**
     * 배치 처리 테스트
     */
    @GetMapping("/test-batch")
    public ResponseEntity<Map<String, Object>> testBatchProcessing() {
        logger.info("==================[HttpTPSsendrecvTestController.testBatchProcessing] - 배치 처리 테스트");
        
        Map<String, Object> results = new HashMap<>();
        results.put("success", true);
        results.put("testType", "BATCH_PROCESSING");
        results.put("timestamp", System.currentTimeMillis());
        results.put("batchResults", new HashMap<String, Object>());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> batchResults = (Map<String, Object>) results.get("batchResults");
        
        String[] endpoints = {"get", "post", "put", "delete"};
        String[] methods = {"GET", "POST", "PUT", "DELETE"};
        
        for (int i = 0; i < endpoints.length; i++) {
            try {
                String url = "https://httpbin.org/" + endpoints[i];
                Map<String, Object> data = Map.of("batch", i + 1, "method", methods[i]);
                
                Map<String, Object> response = httpTpsSendRecv.sendRequest(url, methods[i], data);
                batchResults.put("batch_" + (i + 1), response);
                
            } catch (Exception e) {
                batchResults.put("batch_" + (i + 1), Map.of("error", e.getMessage()));
            }
        }
        
        return ResponseEntity.ok(results);
    }

    /**
     * 사용 가이드 실행
     */
    @PostMapping("/run-usage-guide")
    public ResponseEntity<Map<String, Object>> runUsageGuide(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[HttpTPSsendrecvTestController.runUsageGuide] - 사용 가이드 실행");
        
        String guideType = (String) requestBody.getOrDefault("guideType", "basic");
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("guideType", guideType);
        response.put("timestamp", System.currentTimeMillis());
        
        try {
            switch (guideType) {
                case "basic":
                    usageGuide.basicSynchronousRequest();
                    break;
                case "complex":
                    usageGuide.complexPostRequest();
                    break;
                case "async":
                    usageGuide.asynchronousRequestHandling();
                    break;
                case "batch":
                    usageGuide.batchProcessing();
                    break;
                case "error":
                    usageGuide.errorHandlingAndRetry();
                    break;
                case "user":
                    usageGuide.userManagementScenario();
                    break;
                case "parallel":
                    usageGuide.highPerformanceParallelProcessing();
                    break;
                case "monitoring":
                    usageGuide.monitoringAndHealthCheck();
                    break;
                case "headers":
                    usageGuide.customHeaderManagement();
                    break;
                default:
                    response.put("error", "Unknown guide type: " + guideType);
                    return ResponseEntity.badRequest().body(response);
            }
            
            response.put("message", "Usage guide executed successfully: " + guideType);
            
        } catch (Exception e) {
            logger.error("사용 가이드 실행 실패: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 헬스체크
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("==================[HttpTPSsendrecvTestController.healthCheck] - 헬스체크");
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "HttpTPSsendrecvTestController");
        response.put("timestamp", System.currentTimeMillis());
        response.put("moduleInfo", httpTpsSendRecv.getModuleInfo());
        
        return ResponseEntity.ok(response);
    }
} 