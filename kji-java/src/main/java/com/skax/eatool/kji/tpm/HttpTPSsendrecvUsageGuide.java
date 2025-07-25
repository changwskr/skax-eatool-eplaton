package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HttpTPSsendrecv 상세 사용 가이드
 * 
 * 실제 비즈니스 시나리오에서 HttpTPSsendrecv를 어떻게 사용하는지 보여줍니다.
 */
@Component
public class HttpTPSsendrecvUsageGuide {

    private static final Logger logger = LoggerFactory.getLogger(HttpTPSsendrecvUsageGuide.class);

    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 1. 기본적인 동기 HTTP 요청
     */
    public void basicSynchronousRequest() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.basicSynchronousRequest] - 기본 동기 요청");
        
        try {
            // 간단한 GET 요청
            TpsRequest request = new TpsRequest();
            request.setMethod("GET");
            request.setTargetUrl("https://httpbin.org/json");
            
            TpsResponse response = httpTpsSendRecv.send(request);
            logger.info("기본 GET 요청 성공 - 상태: {}", response.getStatusCode());
            
        } catch (TpsException e) {
            logger.error("기본 동기 요청 실패: {}", e.getMessage());
        }
    }

    /**
     * 2. 복잡한 POST 요청 (JSON 데이터 포함)
     */
    public void complexPostRequest() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.complexPostRequest] - 복잡한 POST 요청");
        
        try {
            // 커스텀 헤더 설정
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer your-token-here");
            headers.put("X-API-Version", "v1");
            headers.put("X-Request-Source", "HttpTPSsendrecv");
            
            // 복잡한 JSON 데이터
            String jsonBody = "{\n" +
                "  \"user\": {\n" +
                "    \"id\": 12345,\n" +
                "    \"name\": \"김철수\",\n" +
                "    \"email\": \"kim@example.com\",\n" +
                "    \"profile\": {\n" +
                "      \"age\": 30,\n" +
                "      \"department\": \"IT\",\n" +
                "      \"skills\": [\"Java\", \"Spring\", \"Reactive\"]\n" +
                "    }\n" +
                "  },\n" +
                "  \"metadata\": {\n" +
                "    \"timestamp\": " + System.currentTimeMillis() + ",\n" +
                "    \"version\": \"1.0.0\",\n" +
                "    \"source\": \"HttpTPSsendrecv\"\n" +
                "  }\n" +
                "}";
            
            TpsRequest request = new TpsRequest();
            request.setMethod("POST");
            request.setTargetUrl("https://httpbin.org/post");
            request.setHeaders(headers);
            request.setBodyJson(jsonBody);
            
            TpsResponse response = httpTpsSendRecv.send(request);
            logger.info("복잡한 POST 요청 성공 - 상태: {}, 응답 크기: {} bytes", 
                       response.getStatusCode(), response.getBody().length());
            
        } catch (TpsException e) {
            logger.error("복잡한 POST 요청 실패: {}", e.getMessage());
        }
    }

    /**
     * 3. 비동기 요청 처리 (Reactive)
     */
    public void asynchronousRequestHandling() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.asynchronousRequestHandling] - 비동기 요청 처리");
        
        // 여러 비동기 요청을 동시에 처리
        String[] urls = {
            "https://httpbin.org/delay/1",
            "https://httpbin.org/delay/2", 
            "https://httpbin.org/delay/3"
        };
        
        for (int i = 0; i < urls.length; i++) {
            final int index = i;
            final String url = urls[i];
            
            TpsRequest request = new TpsRequest();
            request.setMethod("GET");
            request.setTargetUrl(url);
            
            String txId = "ASYNC_BATCH_" + (index + 1);
            
            httpTpsSendRecv.sendAsync(request, txId)
                .subscribe(
                    response -> logger.info("비동기 요청 {} 완료 - 상태: {}", index + 1, response.getStatusCode()),
                    error -> logger.error("비동기 요청 {} 실패: {}", index + 1, error.getMessage())
                );
        }
    }

    /**
     * 4. 배치 처리 (여러 요청을 순차적으로 처리)
     */
    public void batchProcessing() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.batchProcessing] - 배치 처리");
        
        String[] endpoints = {
            "https://httpbin.org/get",
            "https://httpbin.org/post", 
            "https://httpbin.org/put",
            "https://httpbin.org/delete"
        };
        
        String[] methods = {"GET", "POST", "PUT", "DELETE"};
        
        for (int i = 0; i < endpoints.length; i++) {
            try {
                TpsRequest request = new TpsRequest();
                request.setMethod(methods[i]);
                request.setTargetUrl(endpoints[i]);
                
                if (methods[i].equals("POST") || methods[i].equals("PUT")) {
                    request.setBodyJson("{\"batch\": " + (i + 1) + ", \"method\": \"" + methods[i] + "\"}");
                }
                
                TpsResponse response = httpTpsSendRecv.send(request);
                logger.info("배치 요청 {} 완료 - {} {}: {}", 
                           i + 1, methods[i], endpoints[i], response.getStatusCode());
                
                // 요청 간 간격 (서버 부하 방지)
                Thread.sleep(100);
                
            } catch (Exception e) {
                logger.error("배치 요청 {} 실패: {}", i + 1, e.getMessage());
            }
        }
    }

    /**
     * 5. 에러 처리 및 재시도
     */
    public void errorHandlingAndRetry() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.errorHandlingAndRetry] - 에러 처리 및 재시도");
        
        // 의도적으로 실패할 수 있는 요청들
        String[] problematicUrls = {
            "https://httpbin.org/status/404",      // 404 에러
            "https://httpbin.org/status/500",      // 500 에러
            "https://httpbin.org/delay/15",        // 타임아웃
            "https://invalid-domain-12345.com",    // 연결 실패
            "https://httpbin.org/json"             // 성공 케이스
        };
        
        for (String url : problematicUrls) {
            try {
                TpsRequest request = new TpsRequest();
                request.setMethod("GET");
                request.setTargetUrl(url);
                
                TpsResponse response = httpTpsSendRecv.send(request);
                logger.info("요청 성공: {} - 상태: {}", url, response.getStatusCode());
                
            } catch (TpsException e) {
                logger.warn("요청 실패: {} - 오류: {}", url, e.getMessage());
                // 여기서 추가적인 에러 처리 로직을 구현할 수 있습니다
            }
        }
    }

    /**
     * 6. 실제 비즈니스 시나리오: 사용자 관리 API 호출
     */
    public void userManagementScenario() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.userManagementScenario] - 사용자 관리 시나리오");
        
        try {
            // 1. 사용자 목록 조회
            Map<String, Object> userListResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/json");
            logger.info("사용자 목록 조회 완료");
            
            // 2. 새 사용자 생성
            Map<String, Object> newUser = new HashMap<>();
            newUser.put("username", "newuser");
            newUser.put("email", "newuser@example.com");
            newUser.put("fullName", "새로운 사용자");
            newUser.put("role", "USER");
            
            Map<String, Object> createResponse = httpTpsSendRecv.sendPostRequest("https://httpbin.org/post", newUser);
            logger.info("사용자 생성 완료");
            
            // 3. 사용자 정보 수정
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("id", 123);
            updateData.put("email", "updated@example.com");
            updateData.put("status", "ACTIVE");
            
            Map<String, Object> updateResponse = httpTpsSendRecv.sendRequest("https://httpbin.org/put", "PUT", updateData);
            logger.info("사용자 정보 수정 완료");
            
            // 4. 사용자 삭제
            Map<String, Object> deleteData = new HashMap<>();
            deleteData.put("id", 123);
            deleteData.put("reason", "계정 비활성화");
            
            Map<String, Object> deleteResponse = httpTpsSendRecv.sendRequest("https://httpbin.org/delete", "DELETE", deleteData);
            logger.info("사용자 삭제 완료");
            
        } catch (Exception e) {
            logger.error("사용자 관리 시나리오 실패: {}", e.getMessage());
        }
    }

    /**
     * 7. 고성능 병렬 처리
     */
    public void highPerformanceParallelProcessing() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.highPerformanceParallelProcessing] - 고성능 병렬 처리");
        
        int requestCount = 20;
        CompletableFuture<Void>[] futures = new CompletableFuture[requestCount];
        
        for (int i = 0; i < requestCount; i++) {
            final int index = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                try {
                    TpsRequest request = new TpsRequest();
                    request.setMethod("GET");
                    request.setTargetUrl("https://httpbin.org/json");
                    
                    TpsResponse response = httpTpsSendRecv.send(request);
                    logger.debug("병렬 요청 {} 완료 - 상태: {}", index + 1, response.getStatusCode());
                    
                } catch (Exception e) {
                    logger.error("병렬 요청 {} 실패: {}", index + 1, e.getMessage());
                }
            }, executorService);
        }
        
        // 모든 요청 완료 대기
        CompletableFuture.allOf(futures).join();
        logger.info("모든 병렬 요청 완료 (총 {}개)", requestCount);
    }

    /**
     * 8. 모니터링 및 헬스체크
     */
    public void monitoringAndHealthCheck() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.monitoringAndHealthCheck] - 모니터링 및 헬스체크");
        
        // 모듈 정보 조회
        Map<String, Object> moduleInfo = httpTpsSendRecv.getModuleInfo();
        logger.info("HttpTPSsendrecv 모듈 정보:");
        moduleInfo.forEach((key, value) -> logger.info("  {}: {}", key, value));
        
        // 헬스체크 엔드포인트 호출
        try {
            Map<String, Object> healthResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/status/200");
            logger.info("헬스체크 성공: {}", healthResponse.get("success"));
            
        } catch (Exception e) {
            logger.error("헬스체크 실패: {}", e.getMessage());
        }
    }

    /**
     * 9. 커스텀 헤더 관리
     */
    public void customHeaderManagement() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.customHeaderManagement] - 커스텀 헤더 관리");
        
        // 기본 헤더 설정
        httpTpsSendRecv.setDefaultHeader("X-Application", "HttpTPSsendrecv-Example");
        httpTpsSendRecv.setDefaultHeader("X-Environment", "development");
        httpTpsSendRecv.setDefaultHeader("X-Version", "1.0.0");
        
        // 요청별 커스텀 헤더
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("X-Request-ID", "req-" + System.currentTimeMillis());
        customHeaders.put("X-User-ID", "user-12345");
        customHeaders.put("X-Session-ID", "session-67890");
        
        try {
            TpsRequest request = new TpsRequest();
            request.setMethod("POST");
            request.setTargetUrl("https://httpbin.org/post");
            request.setHeaders(customHeaders);
            request.setBodyJson("{\"message\":\"커스텀 헤더 테스트\"}");
            
            TpsResponse response = httpTpsSendRecv.send(request);
            logger.info("커스텀 헤더 요청 성공 - 상태: {}", response.getStatusCode());
            
        } catch (TpsException e) {
            logger.error("커스텀 헤더 요청 실패: {}", e.getMessage());
        }
    }

    /**
     * 10. 리소스 정리
     */
    public void cleanup() {
        logger.info("==================[HttpTPSsendrecvUsageGuide.cleanup] - 리소스 정리");
        
        // ExecutorService 종료
        executorService.shutdown();
        logger.info("ExecutorService 종료 완료");
    }
} 