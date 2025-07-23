package com.skax.eatool.mba.as.usermgmtas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 사용자 상태 업데이트 요청 DTO
 * 
 * @author skax
 * @version 1.0
 * @since 2024-01-01
 */
public class UpdateUserStatusRequestDto {
    
    @NotBlank(message = "사용자 ID는 필수입니다")
    private String userId;
    
    @NotBlank(message = "상태는 필수입니다")
    @Pattern(regexp = "^(ACTIVE|INACTIVE|SUSPENDED)$", message = "상태는 ACTIVE, INACTIVE, SUSPENDED 중 하나여야 합니다")
    private String status;
    
    private String reason;
    
    // 기본 생성자
    public UpdateUserStatusRequestDto() {}
    
    // 전체 생성자
    public UpdateUserStatusRequestDto(String userId, String status, String reason) {
        this.userId = userId;
        this.status = status;
        this.reason = reason;
    }
    
    // Getter와 Setter
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "UpdateUserStatusRequestDto{" +
                "userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
} 