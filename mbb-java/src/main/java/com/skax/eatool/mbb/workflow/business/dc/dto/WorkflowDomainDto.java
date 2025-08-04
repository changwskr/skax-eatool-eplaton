package com.skax.eatool.mbb.workflow.business.dc.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 워크플로우 업무 도메인 DTO
 * 내부 비즈니스 로직에서 사용하는 도메인 전용 데이터 전송 객체
 */
@Data
public class WorkflowDomainDto {
    
    // ==================== 테이블 정의서 도메인 ====================
    
    /**
     * 테이블 정의서 정보
     */
    private TableDefinitionDomain tableDefinition;
    
    /**
     * 컬럼 정의 정보
     */
    private List<ColumnDefinitionDomain> columns;
    
    // ==================== 코드 생성 도메인 ====================
    
    /**
     * 엔티티 생성 정보
     */
    private EntityGenerationDomain entityGeneration;
    
    /**
     * Repository 생성 정보
     */
    private RepositoryGenerationDomain repositoryGeneration;
    
    /**
     * Service 생성 정보
     */
    private ServiceGenerationDomain serviceGeneration;
    
    /**
     * Process 생성 정보
     */
    private ProcessGenerationDomain processGeneration;
    
    /**
     * Domain Service 생성 정보
     */
    private DomainServiceGenerationDomain domainServiceGeneration;
    
    /**
     * Controller 생성 정보
     */
    private ControllerGenerationDomain controllerGeneration;
    
    /**
     * DTO 생성 정보
     */
    private DtoGenerationDomain dtoGeneration;
    
    /**
     * HTML 템플릿 생성 정보
     */
    private TemplateGenerationDomain templateGeneration;
    
    /**
     * Swagger 문서 생성 정보
     */
    private SwaggerGenerationDomain swaggerGeneration;
    
    /**
     * 초기 데이터 생성 정보
     */
    private DataGenerationDomain dataGeneration;
    
    /**
     * 전체 워크플로우 정보
     */
    private FullWorkflowDomain fullWorkflow;
    
    /**
     * 테이블 정보
     */
    private TableInfoDomain tableInfo;
    
    /**
     * 사용 가능한 테이블 목록
     */
    private List<String> availableTables;
    
    // ==================== 워크플로우 상태 도메인 ====================
    
    /**
     * 워크플로우 실행 상태
     */
    private WorkflowStatusDomain workflowStatus;
    
    /**
     * 생성된 코드 결과
     */
    private GeneratedCodeDomain generatedCode;
    
    // ==================== 내부 도메인 클래스들 ====================
    
    /**
     * 테이블 정의서 도메인
     */
    @Data
    public static class TableDefinitionDomain {
        private String tableName;
        private String tableComment;
        private String databaseType;
        private String schemaName;
        private String definitionText;
        private String jsonDefinition;
        private boolean isActive;
        private String createdBy;
        private String updatedBy;
    }
    
    /**
     * 컬럼 정의 도메인
     */
    @Data
    public static class ColumnDefinitionDomain {
        private String columnName;
        private String dataType;
        private Integer columnSize;
        private boolean nullable;
        private boolean primaryKey;
        private String defaultValue;
        private String comment;
        private String javaType;
        private String javaFieldName;
        private boolean isAutoIncrement;
        private String validationRule;
    }
    
    /**
     * 엔티티 생성 도메인
     */
    @Data
    public static class EntityGenerationDomain {
        private String entityName;
        private String packageName;
        private String tableName;
        private List<FieldDefinitionDomain> fields;
        private List<ColumnDefinitionDomain> columns;
        private boolean useLombok;
        private boolean useValidation;
        private String baseEntityClass;
        private Map<String, Object> annotations;
    }
    
    /**
     * 필드 정의 도메인
     */
    @Data
    public static class FieldDefinitionDomain {
        private String fieldName;
        private String javaType;
        private String columnName;
        private String annotation;
        private boolean isPrimaryKey;
        private boolean isNullable;
        private String validation;
        private String comment;
    }
    
    /**
     * Repository 생성 도메인
     */
    @Data
    public static class RepositoryGenerationDomain {
        private String repositoryName;
        private String entityName;
        private String packageName;
        private String baseRepositoryClass;
        private List<String> customMethods;
        private boolean useJpaRepository;
        private boolean useCustomRepository;
    }
    
    /**
     * Service 생성 도메인
     */
    @Data
    public static class ServiceGenerationDomain {
        private String serviceName;
        private String entityName;
        private String packageName;
        private String baseServiceClass;
        private List<String> businessMethods;
        private boolean useTransaction;
        private boolean useValidation;
    }
    
    /**
     * Process 생성 도메인
     */
    @Data
    public static class ProcessGenerationDomain {
        private String processName;
        private String entityName;
        private String packageName;
        private String baseProcessClass;
        private List<String> processMethods;
        private boolean useTransaction;
        private boolean useValidation;
        private String processType; // BUSINESS, WORKFLOW, BATCH
    }
    
    /**
     * Domain Service 생성 도메인
     */
    @Data
    public static class DomainServiceGenerationDomain {
        private String domainServiceName;
        private String entityName;
        private String packageName;
        private String baseDomainServiceClass;
        private List<String> domainMethods;
        private boolean useTransaction;
        private boolean useValidation;
        private String domainType; // CORE, APPLICATION, INFRASTRUCTURE
    }
    
    /**
     * Controller 생성 도메인
     */
    @Data
    public static class ControllerGenerationDomain {
        private String controllerName;
        private String entityName;
        private String packageName;
        private String basePath;
        private List<String> endpoints;
        private boolean useSwagger;
        private boolean useValidation;
        private String responseType;
    }
    
    /**
     * DTO 생성 도메인
     */
    @Data
    public static class DtoGenerationDomain {
        private String dtoName;
        private String entityName;
        private String packageName;
        private String basePackage;
        private String requestPackage;
        private String responsePackage;
        private String domainPackage;
        private String requestClassName;
        private String responseClassName;
        private String domainClassName;
        private List<String> fields;
        private boolean useLombok;
        private boolean useValidation;
        private String dtoType; // REQUEST, RESPONSE, BOTH
        private boolean generateController;
        private boolean generateService;
        private boolean generatePC;
        private boolean generateDC;
        private boolean generateValidator;
        private boolean generateMapper;
        private boolean generateValidationResult;
    }
    
    /**
     * HTML 템플릿 생성 도메인
     */
    @Data
    public static class TemplateGenerationDomain {
        private String templateName;
        private String entityName;
        private String packageName;
        private String templateType; // LIST, FORM, DETAIL
        private String templateEngine; // THYMELEAF, FREEMARKER
        private Map<String, Object> templateData;
        private String outputPath;
    }
    
    /**
     * Swagger 문서 생성 도메인
     */
    @Data
    public static class SwaggerGenerationDomain {
        private String apiTitle;
        private String apiVersion;
        private String apiDescription;
        private String basePackage;
        private String entityName;
        private String packageName;
        private List<String> endpoints;
        private boolean generateYaml;
        private boolean generateJson;
        private String outputPath;
    }
    
    /**
     * 초기 데이터 생성 도메인
     */
    @Data
    public static class DataGenerationDomain {
        private String tableName;
        private String entityName;
        private List<Map<String, Object>> sampleData;
        private String dataType; // INSERT, UPDATE, DELETE
        private String outputFormat; // SQL, JSON, CSV
        private int recordCount;
    }
    
    /**
     * 전체 워크플로우 도메인
     */
    @Data
    public static class FullWorkflowDomain {
        private String entityName;
        private String packageName;
        private String tableName;
        private String workflowType;
        private Map<String, Object> workflowConfig;
        private List<String> steps;
        private boolean autoExecute;
    }
    
    /**
     * 테이블 정보 도메인
     */
    @Data
    public static class TableInfoDomain {
        private String tableName;
        private String tableComment;
        private List<ColumnInfoDomain> columns;
        private String databaseType;
        private String schemaName;
        private Map<String, Object> metadata;
    }
    
    /**
     * 컬럼 정보 도메인
     */
    @Data
    public static class ColumnInfoDomain {
        private String columnName;
        private String dataType;
        private Integer columnSize;
        private boolean nullable;
        private boolean primaryKey;
        private String defaultValue;
        private String comment;
        private String javaType;
        private String javaFieldName;
        private boolean isAutoIncrement;
        private String validationRule;
    }
    
    /**
     * 워크플로우 상태 도메인
     */
    @Data
    public static class WorkflowStatusDomain {
        private int currentStep;
        private int totalSteps;
        private String currentStepName;
        private String status; // READY, RUNNING, COMPLETED, ERROR
        private String message;
        private long startTime;
        private long endTime;
        private List<StepStatusDomain> stepStatuses;
    }
    
    /**
     * 단계 상태 도메인
     */
    @Data
    public static class StepStatusDomain {
        private int stepNumber;
        private String stepName;
        private String status; // READY, RUNNING, COMPLETED, ERROR
        private String message;
        private long executionTime;
        private String outputPath;
    }
    
    /**
     * 생성된 코드 도메인
     */
    @Data
    public static class GeneratedCodeDomain {
        private String ddlCode;
        private String entityCode;
        private String repositoryCode;
        private String requestDtoCode;
        private String responseDtoCode;
        private String domainDtoCode;
        private String controllerCode;
        private String serviceCode;
        private String pcCode;
        private String dcCode;
        private String validatorCode;
        private String mapperCode;
        private String templateCode;
        private String swaggerCode;
        private String initialDataCode;
        private String processCode;
        private String domainServiceCode;
        private String dataCode;
        private Map<String, String> filePaths;
        private Map<String, String> additionalFiles;
        private List<String> generatedFiles;
    }
    
    // ==================== 유틸리티 메서드들 ====================
    
    /**
     * 도메인 DTO 생성 (테이블 정의서용)
     */
    public static WorkflowDomainDto createTableDefinition(String tableName, String definition) {
        WorkflowDomainDto dto = new WorkflowDomainDto();
        TableDefinitionDomain tableDef = new TableDefinitionDomain();
        tableDef.setTableName(tableName);
        tableDef.setDefinitionText(definition);
        tableDef.setActive(true);
        dto.setTableDefinition(tableDef);
        return dto;
    }
    
    /**
     * 도메인 DTO 생성 (엔티티 생성용)
     */
    public static WorkflowDomainDto createEntityGeneration(String entityName, String packageName, String tableName) {
        WorkflowDomainDto dto = new WorkflowDomainDto();
        EntityGenerationDomain entityGen = new EntityGenerationDomain();
        entityGen.setEntityName(entityName);
        entityGen.setPackageName(packageName);
        entityGen.setTableName(tableName);
        entityGen.setUseLombok(true);
        entityGen.setUseValidation(true);
        dto.setEntityGeneration(entityGen);
        return dto;
    }
    
    /**
     * 도메인 DTO 생성 (전체 워크플로우용)
     */
    public static WorkflowDomainDto createFullWorkflow(String entityName, String packageName, String tableName) {
        WorkflowDomainDto dto = new WorkflowDomainDto();
        
        // 엔티티 생성 설정
        EntityGenerationDomain entityGen = new EntityGenerationDomain();
        entityGen.setEntityName(entityName);
        entityGen.setPackageName(packageName);
        entityGen.setTableName(tableName);
        entityGen.setUseLombok(true);
        entityGen.setUseValidation(true);
        dto.setEntityGeneration(entityGen);
        
        // Repository 생성 설정
        RepositoryGenerationDomain repoGen = new RepositoryGenerationDomain();
        repoGen.setRepositoryName(entityName + "Repository");
        repoGen.setEntityName(entityName);
        repoGen.setPackageName(packageName + ".repository");
        repoGen.setUseJpaRepository(true);
        dto.setRepositoryGeneration(repoGen);
        
        // Service 생성 설정
        ServiceGenerationDomain serviceGen = new ServiceGenerationDomain();
        serviceGen.setServiceName(entityName + "Service");
        serviceGen.setEntityName(entityName);
        serviceGen.setPackageName(packageName + ".service");
        serviceGen.setUseTransaction(true);
        serviceGen.setUseValidation(true);
        dto.setServiceGeneration(serviceGen);
        
        // Controller 생성 설정
        ControllerGenerationDomain controllerGen = new ControllerGenerationDomain();
        controllerGen.setControllerName(entityName + "Controller");
        controllerGen.setEntityName(entityName);
        controllerGen.setPackageName(packageName + ".controller");
        controllerGen.setBasePath("/api/" + entityName.toLowerCase() + "s");
        controllerGen.setUseSwagger(true);
        controllerGen.setUseValidation(true);
        dto.setControllerGeneration(controllerGen);
        
        // 워크플로우 상태 초기화
        WorkflowStatusDomain status = new WorkflowStatusDomain();
        status.setCurrentStep(1);
        status.setTotalSteps(11);
        status.setStatus("READY");
        status.setCurrentStepName("테이블 정의서 입력");
        dto.setWorkflowStatus(status);
        
        return dto;
    }
} 