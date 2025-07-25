package com.skax.eatool.kji.tpm;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * TPS 요청 객체
 *
 * HTTP 통신을 위한 요청 정보를 담는 클래스입니다.
 *
 * @author EPlaton Framework Team
 * @version 1.0
 */
public class TpsRequest {

    private String txId;
    private String targetUrl;
    private HttpMethod method = HttpMethod.GET;
    private Map<String, String> headers = new HashMap<>();
    private String bodyJson;
    private String bodyXml;
    private byte[] bodyBinary;

    // 기본 생성자
    public TpsRequest() {}

    // URL과 메서드만 지정하는 생성자
    public TpsRequest(String targetUrl, String method) {
        this.targetUrl = targetUrl;
        this.method = convertToHttpMethod(method);
    }

    // URL, 메서드, JSON 본문을 지정하는 생성자
    public TpsRequest(String targetUrl, String method, String bodyJson) {
        this.targetUrl = targetUrl;
        this.method = convertToHttpMethod(method);
        this.bodyJson = bodyJson;
    }

    // Getter와 Setter
    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }




    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = convertToHttpMethod(method);
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

    public String getBodyJson() {
        return bodyJson;
    }

    public void setBodyJson(String bodyJson) {
        this.bodyJson = bodyJson;
    }

    public String getBodyXml() {
        return bodyXml;
    }

    public void setBodyXml(String bodyXml) {
        this.bodyXml = bodyXml;
    }

    public byte[] getBodyBinary() {
        return bodyBinary;
    }

    public void setBodyBinary(byte[] bodyBinary) {
        this.bodyBinary = bodyBinary;
    }

    /**
     * 요청 본문이 있는지 확인
     */
    public boolean hasBody() {
        return (bodyJson != null && !bodyJson.isEmpty()) ||
                (bodyXml != null && !bodyXml.isEmpty()) ||
                (bodyBinary != null && bodyBinary.length > 0);
    }

    /**
     * JSON 본문이 있는지 확인
     */
    public boolean hasJsonBody() {
        return bodyJson != null && !bodyJson.isEmpty();
    }

    /**
     * XML 본문이 있는지 확인
     */
    public boolean hasXmlBody() {
        return bodyXml != null && !bodyXml.isEmpty();
    }

    /**
     * 바이너리 본문이 있는지 확인
     */
    public boolean hasBinaryBody() {
        return bodyBinary != null && bodyBinary.length > 0;
    }

    @Override
    public String toString() {
        return "TpsRequest{" +
                "txId='" + txId + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", hasBody=" + hasBody() +
                '}';
    }

    /**
     * String을 HttpMethod로 변환하는 헬퍼 메서드
     */
    private HttpMethod convertToHttpMethod(String method) {
        if (method == null || method.trim().isEmpty()) {
            return HttpMethod.GET;
        }
        
        try {
            return HttpMethod.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 잘못된 HTTP 메서드인 경우 기본값으로 GET 반환
            return HttpMethod.GET;
        }
    }
}

