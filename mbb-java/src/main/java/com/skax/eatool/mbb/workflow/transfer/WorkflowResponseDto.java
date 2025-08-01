package com.skax.eatool.mbb.workflow.transfer;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 워크플로우 응답 DTO
 * 워크플로우 서비스에서 외부 시스템으로 반환하는 응답 데이터
 */
@Data
public class WorkflowResponseDto {
    
    // ==================== 기본 정보 ====================
    
    /**
     * 성공 여부
     */
    private boolean success;
    
    /**
     * 상태
     */
    private String status;
    
    /**
     * 메시지
     */
    private String message;
    
    /**
     * 오류 메시지
     */
    private String errorMessage;
    
    /**
     * 성공 메시지
     */
    private String successMessage;
    
    /**
     * 생성된 코드
     */
    private String generatedCode;
    
    /**
     * 생성된 파일 목록
     */
    private List<String> generatedFiles;
    
    /**
     * 파일 경로
     */
    private String filePath;
    
    // ==================== 생성된 코드 정보 ====================
    
    /**
     * 생성된 코드 정보
     */
    private GeneratedCodeResponse generatedCodeInfo;
    
    // ==================== 내부 클래스들 ====================
    
    /**
     * 생성된 코드 응답 정보
     */
    @Data
    public static class GeneratedCodeResponse {
        private String requestDtoCode;
        private String responseDtoCode;
        private String domainDtoCode;
        private String entityCode;
        private String repositoryCode;
        private String serviceCode;
        private String controllerCode;
        private String dtoCode;
        private String ddlCode;
        private String templateCode;
        private String swaggerCode;
        private String dataCode;
        private String initialDataCode;
    }
} 