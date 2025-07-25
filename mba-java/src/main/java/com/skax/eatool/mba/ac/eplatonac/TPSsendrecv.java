package com.skax.eatool.mba.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * TPSsendrecv - 범용 HTTP 통신 모듈
 * 
 * 호출 URL과 입력정보(JSON)를 받아서 원하는 방향으로 HTTP 통신을 수행하는 범용 모듈입니다.
 * 
 * @author EPlaton Framework Team
 * @version 2.0
 */
@Component
public class TPSsendrecv {

    private static final Logger logger = LoggerFactory.getLogger(TPSsendrecv.class);
    
    @Value("${mbc.service.url:http://localhost:8085}")
    private String mbcServiceUrl;
    
    @Value("${mbc.service.context-path:/mbc}")
    private String mbcContextPath;
    
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000; // 10초
    private static final int DEFAULT_READ_TIMEOUT = 30000;    // 30초
    
    /**
     * 범용 HTTP POST 요청 전송
     * 
     * @param targetUrl 대상 URL
     * @param requestData 요청 데이터 (JSON)
     * @return 응답 데이터
     */
    public Map<String, Object> sendPostRequest(String targetUrl, Map<String, Object> requestData) {
        return sendHttpRequest(targetUrl, "POST", requestData, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }
    
    /**
     * 범용 HTTP POST 요청 전송 (타임아웃 지정)
     * 
     * @param targetUrl 대상 URL
     * @param requestData 요청 데이터 (JSON)
     * @param connectTimeout 연결 타임아웃 (밀리초)
     * @param readTimeout 읽기 타임아웃 (밀리초)
     * @return 응답 데이터
     */
    public Map<String, Object> sendPostRequest(String targetUrl, Map<String, Object> requestData, 
                                             int connectTimeout, int readTimeout) {
        return sendHttpRequest(targetUrl, "POST", requestData, connectTimeout, readTimeout);
    }
    
    /**
     * 범용 HTTP GET 요청 전송
     * 
     * @param targetUrl 대상 URL
     * @return 응답 데이터
     */
    public Map<String, Object> sendGetRequest(String targetUrl) {
        return sendHttpRequest(targetUrl, "GET", new HashMap<>(), DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }
    
    /**
     * 범용 HTTP GET 요청 전송 (타임아웃 지정)
     * 
     * @param targetUrl 대상 URL
     * @param connectTimeout 연결 타임아웃 (밀리초)
     * @param readTimeout 읽기 타임아웃 (밀리초)
     * @return 응답 데이터
     */
    public Map<String, Object> sendGetRequest(String targetUrl, int connectTimeout, int readTimeout) {
        return sendHttpRequest(targetUrl, "GET", new HashMap<>(), connectTimeout, readTimeout);
    }
    
    /**
     * 범용 HTTP 요청 전송 (메서드 지정)
     * 
     * @param targetUrl 대상 URL
     * @param method HTTP 메서드 (GET, POST, PUT, DELETE 등)
     * @param requestData 요청 데이터 (JSON)
     * @return 응답 데이터
     */
    public Map<String, Object> sendRequest(String targetUrl, String method, Map<String, Object> requestData) {
        return sendHttpRequest(targetUrl, method, requestData, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }
    
    /**
     * 범용 HTTP 요청 전송 (모든 파라미터 지정)
     * 
     * @param targetUrl 대상 URL
     * @param method HTTP 메서드 (GET, POST, PUT, DELETE 등)
     * @param requestData 요청 데이터 (JSON)
     * @param connectTimeout 연결 타임아웃 (밀리초)
     * @param readTimeout 읽기 타임아웃 (밀리초)
     * @return 응답 데이터
     */
    public Map<String, Object> sendHttpRequest(String targetUrl, String method, Map<String, Object> requestData, 
                                             int connectTimeout, int readTimeout) {
        HttpURLConnection connection = null;
        
        try {
            logger.info("==================[TPSsendrecv.sendHttpRequest START] - URL: {}, Method: {}, Data: {}", 
                       targetUrl, method, requestData);
            
            // URL 생성 및 연결 설정
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.toUpperCase());
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "TPSsendrecv/2.0");
            
            // 타임아웃 설정
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            
            // 요청 데이터 전송 (POST, PUT, DELETE 등에만)
            if (("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || 
                 "DELETE".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) && 
                !requestData.isEmpty()) {
                connection.setDoOutput(true);
                String jsonRequest = convertMapToJson(requestData);
                
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            
            // 응답 처리
            int responseCode = connection.getResponseCode();
            logger.info("==================[TPSsendrecv.sendHttpRequest] - Response Code: {}", responseCode);
            
            Map<String, Object> response = new HashMap<>();
            
            if (responseCode >= 200 && responseCode < 300) {
                // 성공 응답
                response = readResponse(connection.getInputStream());
                response.put("success", true);
                response.put("httpStatus", responseCode);
                response.put("httpMethod", method.toUpperCase());
                response.put("targetUrl", targetUrl);
            } else {
                // 오류 응답
                response = readResponse(connection.getErrorStream());
                response.put("success", false);
                response.put("httpStatus", responseCode);
                response.put("httpMethod", method.toUpperCase());
                response.put("targetUrl", targetUrl);
                response.put("errorMessage", "HTTP Error: " + responseCode);
            }
            
            logger.info("==================[TPSsendrecv.sendHttpRequest END] - Response: {}", response);
            return response;
            
        } catch (Exception e) {
            logger.error("==================[TPSsendrecv.sendHttpRequest ERROR] - {}", e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "TPS001");
            errorResponse.put("errorMessage", "Communication error: " + e.getMessage());
            errorResponse.put("httpMethod", method.toUpperCase());
            errorResponse.put("targetUrl", targetUrl);
            errorResponse.put("timestamp", java.time.LocalDateTime.now().toString());
            
            return errorResponse;
            
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    // ================== MBC EPlaton 전용 메서드들 (하위 호환성) ==================
    
    /**
     * MBC EPlaton API 기본 URL 생성
     */
    private String getMbcEplatonBaseUrl() {
        return mbcServiceUrl + mbcContextPath + "/eplaton";
    }
    
    /**
     * EPlaton API 실행 (일반 실행) - 하위 호환성
     */
    public Map<String, Object> executeEPlatonOperation(Map<String, Object> requestData) {
        return sendPostRequest(getMbcEplatonBaseUrl() + "/api/execute", requestData);
    }
    
    /**
     * EPlaton API 실행 (읽기 전용) - 하위 호환성
     */
    public Map<String, Object> executeEPlatonReadOnlyOperation(Map<String, Object> requestData) {
        return sendPostRequest(getMbcEplatonBaseUrl() + "/api/execute-readonly", requestData);
    }
    
    /**
     * EPlaton API 라우팅 액션 - 하위 호환성
     */
    public Map<String, Object> routeEPlatonAction(Map<String, Object> requestData) {
        return sendPostRequest(getMbcEplatonBaseUrl() + "/api/route-action", requestData);
    }
    
    /**
     * EPlaton API 헬스 체크 - 하위 호환성
     */
    public Map<String, Object> checkEPlatonHealth() {
        return sendPostRequest(getMbcEplatonBaseUrl() + "/api/health", new HashMap<>());
    }
    
    // ================== 유틸리티 메서드들 ==================
    
    /**
     * 응답 스트림 읽기
     */
    private Map<String, Object> readResponse(InputStream inputStream) {
        Map<String, Object> response = new HashMap<>();
        
        if (inputStream == null) {
            response.put("success", false);
            response.put("errorMessage", "No response data");
            return response;
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            
            String jsonResponse = responseBuilder.toString();
            logger.debug("==================[TPSsendrecv.readResponse] - Raw Response: {}", jsonResponse);
            
            // JSON 응답을 Map으로 변환
            response = parseJsonResponse(jsonResponse);
            
        } catch (Exception e) {
            logger.error("==================[TPSsendrecv.readResponse ERROR] - {}", e.getMessage(), e);
            response.put("success", false);
            response.put("errorMessage", "Failed to read response: " + e.getMessage());
        }
        
        return response;
    }
    
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
     * JSON 문자열을 Map으로 파싱
     */
    private Map<String, Object> parseJsonResponse(String json) {
        Map<String, Object> result = new HashMap<>();
        
        if (json == null || json.trim().isEmpty()) {
            result.put("success", false);
            result.put("errorMessage", "Empty response");
            return result;
        }
        
        try {
            // 간단한 JSON 파싱 (실제 프로덕션에서는 Jackson이나 Gson 사용 권장)
            String trimmedJson = json.trim();
            
            if (trimmedJson.startsWith("{") && trimmedJson.endsWith("}")) {
                // 기본적인 키-값 추출
                String content = trimmedJson.substring(1, trimmedJson.length() - 1);
                String[] pairs = content.split(",");
                
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().replace("\"", "");
                        String value = keyValue[1].trim().replace("\"", "");
                        
                        // null 값 처리
                        if ("null".equals(value)) {
                            result.put(key, null);
                        } else {
                            result.put(key, value);
                        }
                    }
                }
            }
            
            // 기본 성공 상태 설정
            if (!result.containsKey("success")) {
                result.put("success", true);
            }
            
        } catch (Exception e) {
            logger.error("==================[TPSsendrecv.parseJsonResponse ERROR] - {}", e.getMessage(), e);
            result.put("success", false);
            result.put("errorMessage", "Failed to parse JSON: " + e.getMessage());
            result.put("rawResponse", json);
        }
        
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
    
    // ================== 설정 관리 메서드들 ==================
    
    /**
     * MBC 서비스 URL 설정
     */
    public void setMbcServiceUrl(String mbcServiceUrl) {
        this.mbcServiceUrl = mbcServiceUrl;
    }
    
    /**
     * MBC 컨텍스트 경로 설정
     */
    public void setMbcContextPath(String mbcContextPath) {
        this.mbcContextPath = mbcContextPath;
    }
    
    /**
     * 현재 설정된 MBC 서비스 정보 반환
     */
    public Map<String, String> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("mbcServiceUrl", mbcServiceUrl);
        info.put("mbcContextPath", mbcContextPath);
        info.put("fullEplatonUrl", getMbcEplatonBaseUrl());
        return info;
    }
    
    /**
     * 모듈 정보 반환
     */
    public Map<String, Object> getModuleInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("moduleName", "TPSsendrecv");
        info.put("version", "2.0");
        info.put("description", "범용 HTTP 통신 모듈");
        info.put("supportedMethods", new String[]{"GET", "POST", "PUT", "DELETE", "PATCH"});
        info.put("defaultConnectTimeout", DEFAULT_CONNECT_TIMEOUT);
        info.put("defaultReadTimeout", DEFAULT_READ_TIMEOUT);
        info.put("timestamp", java.time.LocalDateTime.now().toString());
        return info;
    }
} 