package com.skax.eatool.mba.as.usermgmtas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * API 응답 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("data")
    private T data;
    
    @JsonProperty("error")
    private String error;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    // 기본 생성자
    public ApiResponseDto() {
        this.timestamp = LocalDateTime.now();
    }
    
    // 생성자
    public ApiResponseDto(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    
    // 성공 응답 생성
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return new ApiResponseDto<>(true, "200", message, data);
    }
    
    public static <T> ApiResponseDto<T> success(T data) {
        return success(data, "요청이 성공적으로 처리되었습니다.");
    }
    
    public static <T> ApiResponseDto<T> success(String message) {
        return new ApiResponseDto<>(true, "200", message, null);
    }
    
    // 에러 응답 생성
    public static <T> ApiResponseDto<T> error(String code, String message) {
        ApiResponseDto<T> response = new ApiResponseDto<>(false, code, message, null);
        return response;
    }
    
    public static <T> ApiResponseDto<T> error(String code, String message, String error) {
        ApiResponseDto<T> response = new ApiResponseDto<>(false, code, message, null);
        response.setError(error);
        return response;
    }
    
    // 일반적인 에러 응답들
    public static <T> ApiResponseDto<T> badRequest(String message) {
        return error("400", message);
    }
    
    public static <T> ApiResponseDto<T> unauthorized(String message) {
        return error("401", message);
    }
    
    public static <T> ApiResponseDto<T> forbidden(String message) {
        return error("403", message);
    }
    
    public static <T> ApiResponseDto<T> notFound(String message) {
        return error("404", message);
    }
    
    public static <T> ApiResponseDto<T> conflict(String message) {
        return error("409", message);
    }
    
    public static <T> ApiResponseDto<T> internalServerError(String message) {
        return error("500", message);
    }
    
    public static <T> ApiResponseDto<T> internalServerError(String message, String error) {
        return error("500", message, error);
    }
    
    // Getter와 Setter
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "ApiResponseDto{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
} 