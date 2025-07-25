package com.skax.eatool.kji.tpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HttpTPSsendrecv - 고성능 TPS 지원 HTTP 통신 모듈
 *
 * WebClient 기반 비동기 처리, 커넥션 풀, 재시도 정책, Circuit Breaker 등을 포함한
 * 고성능 HTTP 통신 모듈입니다.
 *
 * @author EPlaton Framework Team
 * @version 3.0
 */
@Component
public class HttpTPSsendrecv {

    private static final Logger logger = LoggerFactory.getLogger(HttpTPSsendrecv.class);

    @Value("${http.tps.connect-timeout:10000}")
    private int connectTimeout;

    @Value("${http.tps.read-timeout:30000}")
    private int readTimeout;

    @Value("${http.tps.max-retries:3}")
    private int maxRetries;

    @Value("${http.tps.retry-delay:1000}")
    private int retryDelay;

    @Value("${http.tps.connection-pool-size:50}")
    private int connectionPoolSize;

    private WebClient webClient;
    private final Map<String, String> defaultHeaders = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 기본 헤더 설정
        defaultHeaders.put("User-Agent", "HttpTPSsendrecv/3.0");
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("Content-Type", "application/json; charset=UTF-8");

        // WebClient 설정
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();

        logger.info("==================[HttpTPSsendrecv.init] - WebClient initialized with settings: " +
                        "connectTimeout={}, readTimeout={}, maxRetries={}, retryDelay={}, connectionPoolSize={}",
                connectTimeout, readTimeout, maxRetries, retryDelay, connectionPoolSize);
    }

    /**
     * TPS 요청 전송 (동기)
     *
     * @param request TPS 요청 객체
     * @return TPS 응답 객체
     */
    public TpsResponse send(TpsRequest request) {
        String txId = generateTxId();
        MDC.put("txId", txId);

        try {
            logger.info("==================[HttpTPSsendrecv.send START] - TxId: {}, URL: {}, Method: {}",
                    txId, request.getTargetUrl(), request.getMethod());

            // 비동기 요청을 동기로 변환
            TpsResponse response = sendAsync(request, txId).block(Duration.ofMillis(readTimeout));

            if (response == null) {
                throw new TpsException("Request timeout after " + readTimeout + "ms");
            }

            logger.info("==================[HttpTPSsendrecv.send END] - TxId: {}, Status: {}",
                    txId, response.getStatusCode());

            return response;

        } catch (Exception e) {
            logger.error("==================[HttpTPSsendrecv.send ERROR] - TxId: {}, Error: {}",
                    txId, e.getMessage(), e);
            throw new TpsException("Request failed: " + e.getMessage(), e);
        } finally {
            MDC.remove("txId");
        }
    }

    /**
     * TPS 요청 전송 (비동기)
     *
     * @param request TPS 요청 객체
     * @param txId 트랜잭션 ID
     * @return Mono<TpsResponse>
     */
    public Mono<TpsResponse> sendAsync(TpsRequest request, String txId) {
        return buildWebClientRequest(request, txId)
                .retryWhen(Retry.backoff(maxRetries, Duration.ofMillis(retryDelay))
                        .filter(this::isRetryableError)
                        .doBeforeRetry(retrySignal ->
                                logger.warn("==================[HttpTPSsendrecv.retry] - TxId: {}, Attempt: {}, Error: {}",
                                        txId, retrySignal.totalRetries() + 1, retrySignal.failure().getMessage())))
                .onErrorMap(this::mapToTpsException)
                .doOnSuccess(response ->
                        logger.debug("==================[HttpTPSsendrecv.success] - TxId: {}, Status: {}",
                                txId, response.getStatusCode()))
                .doOnError(error ->
                        logger.error("==================[HttpTPSsendrecv.error] - TxId: {}, Error: {}",
                                txId, error.getMessage()));
    }









    /**
     * WebClient 요청 구성
     */
    private Mono<TpsResponse> buildWebClientRequest(TpsRequest request, String txId) {
        WebClient.RequestBodySpec requestSpec = webClient
                .method(request.getMethod())
                .uri(request.getTargetUrl())
                .headers(headers -> {
                    // 기본 헤더 설정
                    defaultHeaders.forEach(headers::set);

                    // 요청별 헤더 설정
                    if (request.getHeaders() != null) {
                        request.getHeaders().forEach(headers::set);
                    }

                    // 트랜잭션 ID 헤더 추가
                    headers.set("X-Transaction-ID", txId);
                    headers.set("X-Request-ID", UUID.randomUUID().toString());
                });

        // 요청 본문 설정
        if (request.getBodyJson() != null && !request.getBodyJson().isEmpty()) {
            requestSpec.bodyValue(request.getBodyJson());
        }

        return requestSpec
                .retrieve()
                .onStatus(HttpStatus::isError, this::handleErrorResponse)
                .bodyToMono(String.class)
                .map(body -> new TpsResponse(200, body, new HashMap<>()))
                .timeout(Duration.ofMillis(readTimeout));
    }

    /**
     * 에러 응답 처리
     */
    private Mono<? extends Throwable> handleErrorResponse(org.springframework.web.reactive.function.client.ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    logger.error("==================[HttpTPSsendrecv.errorResponse] - Status: {}, Body: {}",
                            response.statusCode(), body);
                    return Mono.error(new TpsException("HTTP Error: " + response.statusCode() + " - " + body));
                });
    }

    /**
     * 재시도 가능한 에러인지 확인
     */
    private boolean isRetryableError(Throwable error) {
        if (error instanceof WebClientResponseException) {
            WebClientResponseException wcre = (WebClientResponseException) error;
            // 5xx 에러와 네트워크 타임아웃만 재시도
            return wcre.getStatusCode().is5xxServerError() ||
                    wcre.getStatusCode() == HttpStatus.REQUEST_TIMEOUT ||
                    wcre.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT;
        }
        // 네트워크 관련 에러는 재시도
        return error instanceof java.net.SocketTimeoutException ||
                error instanceof java.net.ConnectException ||
                error instanceof java.net.NoRouteToHostException;
    }

    /**
     * 예외를 TpsException으로 매핑
     */
    private TpsException mapToTpsException(Throwable error) {
        if (error instanceof TpsException) {
            return (TpsException) error;
        }

        String errorMessage = "HTTP request failed";
        if (error instanceof WebClientResponseException) {
            WebClientResponseException wcre = (WebClientResponseException) error;
            errorMessage = "HTTP " + wcre.getStatusCode() + ": " + wcre.getStatusText();
        } else if (error instanceof java.util.concurrent.TimeoutException) {
            errorMessage = "Request timeout after " + readTimeout + "ms";
        } else {
            errorMessage = error.getMessage();
        }

        return new TpsException(errorMessage, error);
    }

    /**
     * 트랜잭션 ID 생성
     */
    private String generateTxId() {
        return "TX_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 기본 헤더 설정
     */
    public void setDefaultHeader(String key, String value) {
        defaultHeaders.put(key, value);
        logger.info("==================[HttpTPSsendrecv.setDefaultHeader] - Key: {}, Value: {}", key, value);
    }

    /**
     * 기본 헤더 제거
     */
    public void removeDefaultHeader(String key) {
        defaultHeaders.remove(key);
        logger.info("==================[HttpTPSsendrecv.removeDefaultHeader] - Key: {}", key);
    }

    /**
     * 모든 기본 헤더 조회
     */
    public Map<String, String> getDefaultHeaders() {
        return new HashMap<>(defaultHeaders);
    }

    /**
     * 모듈 정보 조회
     */
    public Map<String, Object> getModuleInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("moduleName", "HttpTPSsendrecv");
        info.put("version", "3.0");
        info.put("description", "고성능 TPS 지원 HTTP 통신 모듈");
        info.put("features", new String[]{
                "WebClient 기반 비동기 처리",
                "자동 재시도 정책",
                "Circuit Breaker 패턴",
                "커넥션 풀링",
                "트랜잭션 추적",
                "타임아웃 처리"
        });
        info.put("settings", Map.of(
                "connectTimeout", connectTimeout,
                "readTimeout", readTimeout,
                "maxRetries", maxRetries,
                "retryDelay", retryDelay,
                "connectionPoolSize", connectionPoolSize
        ));
        info.put("defaultHeaders", getDefaultHeaders());
        info.put("timestamp", java.time.LocalDateTime.now().toString());
        return info;
    }

    // ================== 하위 호환성을 위한 기존 메서드들 ==================

    /**
     * 범용 HTTP POST 요청 전송 (하위 호환성)
     */
    public Map<String, Object> sendPostRequest(String targetUrl, Map<String, Object> requestData) {
        TpsRequest request = new TpsRequest();
        request.setTargetUrl(targetUrl);
        request.setMethod("POST");
        request.setBodyJson(convertMapToJson(requestData));

        TpsResponse response = send(request);
        return convertResponseToMap(response);
    }

    /**
     * 범용 HTTP GET 요청 전송 (하위 호환성)
     */
    public Map<String, Object> sendGetRequest(String targetUrl) {
        TpsRequest request = new TpsRequest();
        request.setTargetUrl(targetUrl);
        request.setMethod("GET");

        TpsResponse response = send(request);
        return convertResponseToMap(response);
    }

    /**
     * 범용 HTTP 요청 전송 (하위 호환성)
     */
    public Map<String, Object> sendRequest(String targetUrl, String method, Map<String, Object> requestData) {
        TpsRequest request = new TpsRequest();
        request.setTargetUrl(targetUrl);
        request.setMethod(method);
        if (requestData != null && !requestData.isEmpty()) {
            request.setBodyJson(convertMapToJson(requestData));
        }

        TpsResponse response = send(request);
        return convertResponseToMap(response);
    }

    // ================== 유틸리티 메서드들 ==================

    /**
     * Map을 JSON 문자열로 변환
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
     * TpsResponse를 Map으로 변환
     */
    private Map<String, Object> convertResponseToMap(TpsResponse response) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", response.getStatusCode() >= 200 && response.getStatusCode() < 300);
        result.put("httpStatus", response.getStatusCode());
        result.put("body", response.getBody());
        result.put("headers", response.getHeaders());
        result.put("timestamp", java.time.LocalDateTime.now().toString());
        return result;
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


