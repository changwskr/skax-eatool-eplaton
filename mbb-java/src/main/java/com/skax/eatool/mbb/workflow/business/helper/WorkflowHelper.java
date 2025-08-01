package com.skax.eatool.mbb.workflow.business.helper;

import com.skax.eatool.mbb.workflow.transfer.WorkflowRequestDto;
import com.skax.eatool.mbb.workflow.transfer.WorkflowResponseDto;
import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 워크플로우 헬퍼 클래스
 * 순수 유틸리티 메서드들만 제공 (다른 레이어 참조 금지)
 * - DTO 변환
 * - 문자열 처리
 * - 타입 변환
 * - 유효성 검증
 */
@Component
public class WorkflowHelper {

    /**
     * Domain DTO를 Response DTO로 변환
     */
    public static WorkflowResponseDto convertDomainToResponse(WorkflowDomainDto domainDto) {
        WorkflowResponseDto responseDto = new WorkflowResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("요청이 성공적으로 처리되었습니다.");
        
        // 워크플로우 상태 정보 설정
        if (domainDto.getWorkflowStatus() != null) {
            responseDto.setStatus(domainDto.getWorkflowStatus().getStatus());
            responseDto.setMessage(domainDto.getWorkflowStatus().getMessage());
        }
        
        // 생성된 코드 정보 설정
        if (domainDto.getGeneratedCode() != null) {
            WorkflowResponseDto.GeneratedCodeResponse generatedCode = new WorkflowResponseDto.GeneratedCodeResponse();
            
            if (domainDto.getGeneratedCode().getRequestDtoCode() != null) {
                generatedCode.setRequestDtoCode(domainDto.getGeneratedCode().getRequestDtoCode());
            }
            if (domainDto.getGeneratedCode().getResponseDtoCode() != null) {
                generatedCode.setResponseDtoCode(domainDto.getGeneratedCode().getResponseDtoCode());
            }
            if (domainDto.getGeneratedCode().getDomainDtoCode() != null) {
                generatedCode.setDomainDtoCode(domainDto.getGeneratedCode().getDomainDtoCode());
            }
            
            responseDto.setGeneratedCodeInfo(generatedCode);
        }
        
        // 생성된 파일 목록 설정
        if (domainDto.getGeneratedCode() != null && domainDto.getGeneratedCode().getFilePaths() != null) {
            responseDto.setGeneratedFiles(new ArrayList<>(domainDto.getGeneratedCode().getFilePaths().values()));
        }
        
        return responseDto;
    }
    
    /**
     * Request DTO를 Domain DTO로 변환
     */
    public static WorkflowDomainDto convertRequestToDomain(WorkflowRequestDto requestDto) {
        WorkflowDomainDto domainDto = new WorkflowDomainDto();
        
        // DTO 생성 정보 설정
        if (requestDto.getDtoGeneration() != null) {
            WorkflowDomainDto.DtoGenerationDomain dtoGeneration = new WorkflowDomainDto.DtoGenerationDomain();
            dtoGeneration.setEntityName(requestDto.getDtoGeneration().getEntityName());
            dtoGeneration.setBasePackage(requestDto.getDtoGeneration().getBasePackage());
            dtoGeneration.setRequestPackage(requestDto.getDtoGeneration().getRequestPackage());
            dtoGeneration.setResponsePackage(requestDto.getDtoGeneration().getResponsePackage());
            dtoGeneration.setDomainPackage(requestDto.getDtoGeneration().getDomainPackage());
            dtoGeneration.setRequestClassName(requestDto.getDtoGeneration().getRequestClassName());
            dtoGeneration.setResponseClassName(requestDto.getDtoGeneration().getResponseClassName());
            dtoGeneration.setDomainClassName(requestDto.getDtoGeneration().getDomainClassName());
            dtoGeneration.setGenerateController(requestDto.getDtoGeneration().isGenerateController());
            dtoGeneration.setGenerateService(requestDto.getDtoGeneration().isGenerateService());
            dtoGeneration.setGeneratePC(requestDto.getDtoGeneration().isGeneratePC());
            dtoGeneration.setGenerateDC(requestDto.getDtoGeneration().isGenerateDC());
            dtoGeneration.setGenerateValidator(requestDto.getDtoGeneration().isGenerateValidator());
            dtoGeneration.setGenerateMapper(requestDto.getDtoGeneration().isGenerateMapper());
            dtoGeneration.setGenerateValidationResult(requestDto.getDtoGeneration().isGenerateValidationResult());
            domainDto.setDtoGeneration(dtoGeneration);
        }
        
        return domainDto;
    }
    
    /**
     * Entity 생성용 Request DTO를 Domain DTO로 변환
     */
    public static WorkflowDomainDto convertRequestToEntityDomain(WorkflowRequestDto requestDto) {
        return WorkflowDomainDto.createEntityGeneration(
            requestDto.getTableName(), 
            "com.skax.eatool.mbb", 
            requestDto.getTableName()
        );
    }
    
    /**
     * 전체 워크플로우용 Request DTO를 Domain DTO로 변환
     */
    public static WorkflowDomainDto convertRequestToFullWorkflowDomain(WorkflowRequestDto requestDto) {
        return WorkflowDomainDto.createFullWorkflow(
            requestDto.getTableName(), 
            "com.skax.eatool.mbb", 
            requestDto.getTableName()
        );
    }
    
    /**
     * 워크플로우 상태 검증
     */
    public static boolean isValidWorkflowStatus(WorkflowDomainDto domainDto) {
        if (domainDto.getWorkflowStatus() == null) {
            return false;
        }
        
        String status = domainDto.getWorkflowStatus().getStatus();
        return "READY".equals(status) || "RUNNING".equals(status) || "COMPLETED".equals(status);
    }
    
    /**
     * 워크플로우 에러 상태 확인
     */
    public static boolean isWorkflowError(WorkflowDomainDto domainDto) {
        if (domainDto.getWorkflowStatus() == null) {
            return false;
        }
        
        return "ERROR".equals(domainDto.getWorkflowStatus().getStatus());
    }
    
    /**
     * 워크플로우 완료 상태 확인
     */
    public static boolean isWorkflowCompleted(WorkflowDomainDto domainDto) {
        if (domainDto.getWorkflowStatus() == null) {
            return false;
        }
        
        return "COMPLETED".equals(domainDto.getWorkflowStatus().getStatus());
    }
    
    /**
     * 현재 워크플로우 단계 정보 가져오기
     */
    public static String getCurrentStepInfo(WorkflowDomainDto domainDto) {
        if (domainDto.getWorkflowStatus() == null) {
            return "알 수 없음";
        }
        
        WorkflowDomainDto.WorkflowStatusDomain status = domainDto.getWorkflowStatus();
        return String.format("단계 %d/%d: %s", 
            status.getCurrentStep(), 
            status.getTotalSteps(), 
            status.getCurrentStepName());
    }
    
    /**
     * 워크플로우 진행률 계산
     */
    public static double calculateProgress(WorkflowDomainDto domainDto) {
        if (domainDto.getWorkflowStatus() == null) {
            return 0.0;
        }
        
        WorkflowDomainDto.WorkflowStatusDomain status = domainDto.getWorkflowStatus();
        return (double) status.getCurrentStep() / status.getTotalSteps() * 100;
    }
    
    /**
     * 성공 응답 생성
     */
    public static WorkflowResponseDto createSuccessResponse(String message) {
        WorkflowResponseDto response = new WorkflowResponseDto();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }
    
    /**
     * 에러 응답 생성
     */
    public static WorkflowResponseDto createErrorResponse(String errorMessage) {
        WorkflowResponseDto response = new WorkflowResponseDto();
        response.setSuccess(false);
        response.setErrorMessage(errorMessage);
        return response;
    }
    
    /**
     * 워크플로우 상태 업데이트
     */
    public static void updateWorkflowStatus(WorkflowDomainDto domainDto, int currentStep, String stepName, String status, String message) {
        if (domainDto.getWorkflowStatus() == null) {
            domainDto.setWorkflowStatus(new WorkflowDomainDto.WorkflowStatusDomain());
        }
        
        WorkflowDomainDto.WorkflowStatusDomain workflowStatus = domainDto.getWorkflowStatus();
        workflowStatus.setCurrentStep(currentStep);
        workflowStatus.setCurrentStepName(stepName);
        workflowStatus.setStatus(status);
        workflowStatus.setMessage(message);
    }
    
    /**
     * 워크플로우 완료 처리
     */
    public static void completeWorkflow(WorkflowDomainDto domainDto, String message) {
        updateWorkflowStatus(domainDto, 11, "완료", "COMPLETED", message);
    }
    
    /**
     * 워크플로우 에러 처리
     */
    public static void errorWorkflow(WorkflowDomainDto domainDto, String errorMessage) {
        updateWorkflowStatus(domainDto, domainDto.getWorkflowStatus().getCurrentStep(), 
            "에러", "ERROR", errorMessage);
    }
    
    // ==================== 순수 유틸리티 메서드 ====================
    
    /**
     * 문자열을 카멜케이스로 변환
     */
    public static String toCamelCase(String str) {
        if (str == null || str.isEmpty()) return str;
        
        String[] words = str.toLowerCase().split("_");
        StringBuilder result = new StringBuilder(words[0]);
        
        for (int i = 1; i < words.length; i++) {
            result.append(capitalize(words[i]));
        }
        
        return result.toString();
    }
    
    /**
     * 문자열 첫 글자를 대문자로 변환
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * 데이터베이스 타입을 Java 타입으로 변환
     */
    public static String getJavaType(String dbType) {
        if (dbType == null) return "String";
        
        switch (dbType.toUpperCase()) {
            case "BIGINT":
            case "INT":
            case "INTEGER":
                return "Long";
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "DATETIME":
            case "TIMESTAMP":
                return "LocalDateTime";
            case "DATE":
                return "LocalDate";
            case "BOOLEAN":
            case "BIT":
                return "Boolean";
            case "DECIMAL":
            case "NUMERIC":
                return "BigDecimal";
            default:
                return "String";
        }
    }
    
    /**
     * 문자열 유효성 검증
     */
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * 패키지명 유효성 검증
     */
    public static boolean isValidPackageName(String packageName) {
        if (!isValidString(packageName)) {
            return false;
        }
        return packageName.matches("^[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)*$");
    }
    
    /**
     * 클래스명 유효성 검증
     */
    public static boolean isValidClassName(String className) {
        if (!isValidString(className)) {
            return false;
        }
        return className.matches("^[A-Z][a-zA-Z0-9]*$");
    }
    
    /**
     * 테이블명 유효성 검증
     */
    public static boolean isValidTableName(String tableName) {
        if (!isValidString(tableName)) {
            return false;
        }
        return tableName.matches("^[A-Z][A-Z0-9_]*$");
    }
} 