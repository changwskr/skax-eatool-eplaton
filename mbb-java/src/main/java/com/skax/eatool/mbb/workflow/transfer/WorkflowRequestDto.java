package com.skax.eatool.mbb.workflow.transfer;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 워크플로우 요청 DTO
 * 외부 시스템에서 워크플로우 서비스로 전송하는 요청 데이터
 */
@Data
public class WorkflowRequestDto {
    
    // ==================== 기본 정보 ====================
    
    /**
     * 요청 ID
     */
    private String requestId;
    
    /**
     * 요청 타임스탬프
     */
    private long timestamp;
    
    /**
     * 요청 사용자
     */
    private String userId;
    
    // ==================== 테이블 정의서 정보 ====================
    
    /**
     * 테이블명
     */
    private String tableName;
    
    /**
     * 테이블 정의서 내용
     */
    private String tableDefinition;
    
    // ==================== DTO 생성 정보 ====================
    
    /**
     * DTO 생성 정보
     */
    private DtoGenerationRequest dtoGeneration;
    
    // ==================== 내부 클래스들 ====================
    
    /**
     * DTO 생성 요청 정보
     */
    @Data
    public static class DtoGenerationRequest {
        private String entityName;
        private String basePackage;
        private String requestPackage;
        private String responsePackage;
        private String domainPackage;
        private String requestClassName;
        private String responseClassName;
        private String domainClassName;
        private boolean generateController;
        private boolean generateService;
        private boolean generatePC;
        private boolean generateDC;
        private boolean generateValidator;
        private boolean generateMapper;
        private boolean generateValidationResult;
    }
} 