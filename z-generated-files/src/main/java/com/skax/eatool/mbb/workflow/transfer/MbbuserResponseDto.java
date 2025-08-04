package com.skax.eatool.mbb.workflow.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.*;

/**
 * Mbbuser 응답 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MbbuserResponseDto {

    /**
     * 기본 키
     */
    private Long id;

    /**
     * 이름
     */
    private String name;

    /**
     * 이메일
     */
    private String email;

    /**
     * 사용자명
     */
    private String username;

    /**
     * 생성일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정일시
     */
    private LocalDateTime updatedAt;

    /**
     * 활성화 여부
     */
    private Boolean active;

}