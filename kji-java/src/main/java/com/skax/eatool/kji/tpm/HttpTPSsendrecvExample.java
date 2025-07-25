package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * HttpTPSsendrecv 모듈 사용 예제
 * 
 * 이 클래스는 HttpTPSsendrecv의 다양한 메서드 사용법을 보여줍니다.
 * 애플리케이션 시작 시 자동으로 실행됩니다.
 */
@Component
public class HttpTPSsendrecvExample implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HttpTPSsendrecvExample.class);

    @Autowired
    private HttpTPSsendrecv httpTpsSendRecv;

    @Override
    public void run(String... args) throws Exception {
        logger.info("==================[HttpTPSsendrecvExample] - 시작");
        
        // 기본 헤더 설정
        setupDefaultHeaders();
        
        // 1. send 메서드 예제 (동기)
        exampleSendMethod();
        
        // 2. sendAsync 메서드 예제 (비동기)
        exampleSendAsyncMethod();
        
        // 3. sendPostRequest 예제
        exampleSendPostRequest();
        
        // 4. sendGetRequest 예제
        exampleSendGetRequest();
        
        // 5. sendRequest 예제 (다양한 HTTP 메서드)
        exampleSendRequest();
        
        // 6. 에러 처리 예제
        exampleErrorHandling();
        
        logger.info("==================[HttpTPSsendrecvExample] - 완료");
    }

    /**
     * 기본 헤더 설정 예제
     */
    private void setupDefaultHeaders() {
        logger.info("==================[HttpTPSsendrecvExample.setupDefaultHeaders] - 기본 헤더 설정");
        
        httpTpsSendRecv.setDefaultHeader("User-Agent", "HttpTPSsendrecv-Example/1.0");
        httpTpsSendRecv.setDefaultHeader("Accept", "application/json");
        httpTpsSendRecv.setDefaultHeader("Content-Type", "application/json");
        httpTpsSendRecv.setDefaultHeader("X-Client-ID", "example-client");
        
        logger.info("설정된 기본 헤더: {}", httpTpsSendRecv.getDefaultHeaders());
    }

    /**
     * send 메서드 예제 (동기 호출)
     */
    private void exampleSendMethod() {
        logger.info("==================[HttpTPSsendrecvExample.exampleSendMethod] - 동기 호출 예제");
        
        try {
            // POST 요청 예제
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Custom-Header", "custom-value");
            
            TpsRequest postRequest = new TpsRequest();
            postRequest.setMethod("POST");
            postRequest.setTargetUrl("http://localhost:8085/mbc/eplaton/api/execute");
            postRequest.setHeaders(headers);
            postRequest.setBodyJson("{\"message\":\"Hello from send method\",\"timestamp\":" + System.currentTimeMillis() + "}");
            
            TpsResponse postResponse = httpTpsSendRecv.send(postRequest);
            logger.info("POST 응답 - 상태: {}, 본문: {}", postResponse.getStatusCode(), postResponse.getBody());
            
            // GET 요청 예제
            TpsRequest getRequest = new TpsRequest();
            getRequest.setMethod("GET");
            getRequest.setTargetUrl("http://localhost:8085/mbc/eplaton/api/health");
            
            TpsResponse getResponse = httpTpsSendRecv.send(getRequest);
            logger.info("GET 응답 - 상태: {}, 본문: {}", getResponse.getStatusCode(), getResponse.getBody());
            
        } catch (TpsException e) {
            logger.error("send 메서드 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * sendAsync 메서드 예제 (비동기 호출)
     */
    private void exampleSendAsyncMethod() {
        logger.info("==================[HttpTPSsendrecvExample.exampleSendAsyncMethod] - 비동기 호출 예제");
        
        try {
            CountDownLatch latch = new CountDownLatch(2);
            
            // 비동기 POST 요청
            TpsRequest asyncPostRequest = new TpsRequest();
            asyncPostRequest.setMethod("POST");
            asyncPostRequest.setTargetUrl("https://httpbin.org/post");
            asyncPostRequest.setBodyJson("{\"async\":\"true\",\"message\":\"Hello from async method\"}");
            
            Mono<TpsResponse> asyncPostMono = httpTpsSendRecv.sendAsync(asyncPostRequest, "ASYNC_TX_001");
            asyncPostMono.subscribe(
                response -> {
                    logger.info("비동기 POST 응답 - 상태: {}, 본문: {}", response.getStatusCode(), response.getBody());
                    latch.countDown();
                },
                error -> {
                    logger.error("비동기 POST 오류: {}", error.getMessage(), error);
                    latch.countDown();
                }
            );
            
            // 비동기 GET 요청
            TpsRequest asyncGetRequest = new TpsRequest();
            asyncGetRequest.setMethod("GET");
            asyncGetRequest.setTargetUrl("https://httpbin.org/delay/2"); // 2초 지연
            
            Mono<TpsResponse> asyncGetMono = httpTpsSendRecv.sendAsync(asyncGetRequest, "ASYNC_TX_002");
            asyncGetMono.subscribe(
                response -> {
                    logger.info("비동기 GET 응답 - 상태: {}, 본문: {}", response.getStatusCode(), response.getBody());
                    latch.countDown();
                },
                error -> {
                    logger.error("비동기 GET 오류: {}", error.getMessage(), error);
                    latch.countDown();
                }
            );
            
            // 모든 비동기 요청 완료 대기 (최대 10초)
            latch.await(10, TimeUnit.SECONDS);
            
        } catch (Exception e) {
            logger.error("sendAsync 메서드 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * sendPostRequest 예제
     */
    private void exampleSendPostRequest() {
        logger.info("==================[HttpTPSsendrecvExample.exampleSendPostRequest] - POST 요청 예제");
        
        try {
            // JSON 데이터 준비
            Map<String, Object> postData = new HashMap<>();
            postData.put("name", "홍길동");
            postData.put("age", 30);
            postData.put("email", "hong@example.com");
            postData.put("active", true);
            postData.put("tags", new String[]{"java", "spring", "reactive"});
            
            // POST 요청 실행
            Map<String, Object> response = httpTpsSendRecv.sendPostRequest("https://httpbin.org/post", postData);
            
            logger.info("POST 요청 응답:");
            logger.info("  성공: {}", response.get("success"));
            logger.info("  상태 코드: {}", response.get("statusCode"));
            logger.info("  응답 본문: {}", response.get("body"));
            logger.info("  응답 헤더: {}", response.get("headers"));
            
        } catch (Exception e) {
            logger.error("sendPostRequest 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * sendGetRequest 예제
     */
    private void exampleSendGetRequest() {
        logger.info("==================[HttpTPSsendrecvExample.exampleSendGetRequest] - GET 요청 예제");
        
        try {
            // GET 요청 실행
            Map<String, Object> response = httpTpsSendRecv.sendGetRequest("https://httpbin.org/get?param1=value1&param2=value2");
            
            logger.info("GET 요청 응답:");
            logger.info("  성공: {}", response.get("success"));
            logger.info("  상태 코드: {}", response.get("statusCode"));
            logger.info("  응답 본문: {}", response.get("body"));
            logger.info("  응답 헤더: {}", response.get("headers"));
            
        } catch (Exception e) {
            logger.error("sendGetRequest 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * sendRequest 예제 (다양한 HTTP 메서드)
     */
    private void exampleSendRequest() {
        logger.info("==================[HttpTPSsendrecvExample.exampleSendRequest] - 다양한 HTTP 메서드 예제");
        
        try {
            // PUT 요청 예제
            Map<String, Object> putData = new HashMap<>();
            putData.put("id", 123);
            putData.put("name", "김철수");
            putData.put("updated", true);
            
            Map<String, Object> putResponse = httpTpsSendRecv.sendRequest("https://httpbin.org/put", "PUT", putData);
            logger.info("PUT 요청 응답 - 성공: {}, 상태: {}", putResponse.get("success"), putResponse.get("statusCode"));
            
            // DELETE 요청 예제
            Map<String, Object> deleteData = new HashMap<>();
            deleteData.put("id", 456);
            deleteData.put("reason", "테스트 삭제");
            
            Map<String, Object> deleteResponse = httpTpsSendRecv.sendRequest("https://httpbin.org/delete", "DELETE", deleteData);
            logger.info("DELETE 요청 응답 - 성공: {}, 상태: {}", deleteResponse.get("success"), deleteResponse.get("statusCode"));
            
            // PATCH 요청 예제
            Map<String, Object> patchData = new HashMap<>();
            patchData.put("id", 789);
            patchData.put("status", "updated");
            
            Map<String, Object> patchResponse = httpTpsSendRecv.sendRequest("https://httpbin.org/patch", "PATCH", patchData);
            logger.info("PATCH 요청 응답 - 성공: {}, 상태: {}", patchResponse.get("success"), patchResponse.get("statusCode"));
            
        } catch (Exception e) {
            logger.error("sendRequest 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * 에러 처리 예제
     */
    private void exampleErrorHandling() {
        logger.info("==================[HttpTPSsendrecvExample.exampleErrorHandling] - 에러 처리 예제");
        
        try {
            // 404 에러 테스트
            Map<String, Object> notFoundResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/status/404");
            logger.info("404 에러 처리 - 성공: {}, 상태: {}", notFoundResponse.get("success"), notFoundResponse.get("statusCode"));
            
            // 500 에러 테스트
            Map<String, Object> serverErrorResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/status/500");
            logger.info("500 에러 처리 - 성공: {}, 상태: {}", serverErrorResponse.get("success"), serverErrorResponse.get("statusCode"));
            
            // 타임아웃 테스트
            Map<String, Object> timeoutResponse = httpTpsSendRecv.sendGetRequest("https://httpbin.org/delay/10");
            logger.info("타임아웃 처리 - 성공: {}, 상태: {}", timeoutResponse.get("success"), timeoutResponse.get("statusCode"));
            
        } catch (Exception e) {
            logger.error("에러 처리 예제 오류: {}", e.getMessage(), e);
        }
    }

    /**
     * 모듈 정보 조회 예제
     */
    public void showModuleInfo() {
        logger.info("==================[HttpTPSsendrecvExample.showModuleInfo] - 모듈 정보");
        
        Map<String, Object> moduleInfo = httpTpsSendRecv.getModuleInfo();
        logger.info("모듈 정보:");
        moduleInfo.forEach((key, value) -> logger.info("  {}: {}", key, value));
    }
} 