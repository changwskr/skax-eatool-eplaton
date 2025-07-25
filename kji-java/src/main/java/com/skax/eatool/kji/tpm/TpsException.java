package com.skax.eatool.kji.tpm;

/**
 * TPS 통신 예외
 * 
 * HTTP 통신 중 발생하는 예외를 처리하는 클래스입니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
public class TpsException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String errorCode;
    private int httpStatus;
    private String txId;
    
    // 기본 생성자
    public TpsException() {
        super();
    }
    
    // 메시지만 지정하는 생성자
    public TpsException(String message) {
        super(message);
    }
    
    // 메시지와 원인을 지정하는 생성자
    public TpsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // 원인만 지정하는 생성자
    public TpsException(Throwable cause) {
        super(cause);
    }
    
    // 에러 코드와 메시지를 지정하는 생성자
    public TpsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    // 모든 필드를 지정하는 생성자
    public TpsException(String errorCode, String message, int httpStatus, String txId) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.txId = txId;
    }
    
    // Getter와 Setter
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public int getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public String getTxId() {
        return txId;
    }
    
    public void setTxId(String txId) {
        this.txId = txId;
    }
    
    /**
     * 네트워크 관련 에러인지 확인
     */
    public boolean isNetworkError() {
        return getCause() instanceof java.net.SocketTimeoutException ||
               getCause() instanceof java.net.ConnectException ||
               getCause() instanceof java.net.NoRouteToHostException ||
               getCause() instanceof java.net.UnknownHostException;
    }
    
    /**
     * 타임아웃 에러인지 확인
     */
    public boolean isTimeoutError() {
        return getCause() instanceof java.util.concurrent.TimeoutException ||
               getCause() instanceof java.net.SocketTimeoutException ||
               getMessage().contains("timeout");
    }
    
    /**
     * 재시도 가능한 에러인지 확인
     */
    public boolean isRetryable() {
        return isNetworkError() || isTimeoutError() || 
               (httpStatus >= 500 && httpStatus < 600) ||
               httpStatus == 408 || // Request Timeout
               httpStatus == 429;   // Too Many Requests
    }
    
    @Override
    public String toString() {
        return "TpsException{" +
                "errorCode='" + errorCode + '\'' +
                ", httpStatus=" + httpStatus +
                ", txId='" + txId + '\'' +
                ", message='" + getMessage() + '\'' +
                ", retryable=" + isRetryable() +
                '}';
    }
} 