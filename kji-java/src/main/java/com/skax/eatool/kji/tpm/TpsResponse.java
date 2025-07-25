package com.skax.eatool.kji.tpm;

import java.util.HashMap;
import java.util.Map;

/**
 * TPS 응답 객체
 * 
 * HTTP 통신 결과를 담는 클래스입니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
public class TpsResponse {
    
    private int statusCode;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private String txId;
    private long responseTime;
    private String contentType;
    
    // 기본 생성자
    public TpsResponse() {}
    
    // 상태 코드와 본문만 지정하는 생성자
    public TpsResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
    
    // 모든 필드를 지정하는 생성자
    public TpsResponse(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers != null ? headers : new HashMap<>();
    }
    
    // Getter와 Setter
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers != null ? headers : new HashMap<>();
    }
    
    public void addHeader(String key, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
    }
    
    public String getHeader(String key) {
        return headers != null ? headers.get(key) : null;
    }
    
    public String getTxId() {
        return txId;
    }
    
    public void setTxId(String txId) {
        this.txId = txId;
    }
    
    public long getResponseTime() {
        return responseTime;
    }
    
    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    /**
     * 성공 응답인지 확인 (2xx 상태 코드)
     */
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }
    
    /**
     * 클라이언트 에러인지 확인 (4xx 상태 코드)
     */
    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }
    
    /**
     * 서버 에러인지 확인 (5xx 상태 코드)
     */
    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 600;
    }
    
    /**
     * 리다이렉트인지 확인 (3xx 상태 코드)
     */
    public boolean isRedirect() {
        return statusCode >= 300 && statusCode < 400;
    }
    
    /**
     * JSON 응답인지 확인
     */
    public boolean isJsonResponse() {
        return contentType != null && contentType.contains("application/json");
    }
    
    /**
     * XML 응답인지 확인
     */
    public boolean isXmlResponse() {
        return contentType != null && (contentType.contains("application/xml") || contentType.contains("text/xml"));
    }
    
    /**
     * 응답 본문이 있는지 확인
     */
    public boolean hasBody() {
        return body != null && !body.isEmpty();
    }
    
    /**
     * 특정 헤더가 있는지 확인
     */
    public boolean hasHeader(String key) {
        return headers != null && headers.containsKey(key);
    }
    
    @Override
    public String toString() {
        return "TpsResponse{" +
                "statusCode=" + statusCode +
                ", txId='" + txId + '\'' +
                ", responseTime=" + responseTime + "ms" +
                ", contentType='" + contentType + '\'' +
                ", hasBody=" + hasBody() +
                ", headers=" + headers +
                '}';
    }
} 