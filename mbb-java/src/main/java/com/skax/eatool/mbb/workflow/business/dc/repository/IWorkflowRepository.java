package com.skax.eatool.mbb.workflow.business.dc.repository;

import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;

/**
 * 워크플로우 Repository 인터페이스
 * 데이터 접근 계층의 추상화를 제공
 */
public interface IWorkflowRepository {

    // ==================== 1단계: 테이블 정의서 입력 ====================
    
    /**
     * 테이블 정의서 저장 (Domain DTO 사용)
     */
    WorkflowDomainDto saveTableDefinition(WorkflowDomainDto domainDto);

    // ==================== 2단계: DDL 자동 Export ====================
    
    /**
     * DDL 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateDDL(WorkflowDomainDto domainDto);

    // ==================== 3단계: Entity 자동 생성 ====================
    
    /**
     * Entity 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateEntity(WorkflowDomainDto domainDto);

    /**
     * 사용 가능한 테이블 목록 조회 (Domain DTO 사용)
     */
    WorkflowDomainDto getAvailableTables();

    /**
     * 테이블 정보 조회 (Domain DTO 사용)
     */
    WorkflowDomainDto getTableInfo(String tableName);

    /**
     * Entity 생성 (컬럼 정보 포함, Domain DTO 사용)
     */
    WorkflowDomainDto generateEntityWithColumns(WorkflowDomainDto domainDto);

    // ==================== 4단계: Repository 자동 생성 ====================
    
    /**
     * Repository 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateRepository(WorkflowDomainDto domainDto);

    // ==================== 5단계: DTO & Validator 생성 ====================
    
    /**
     * DTO 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateDTO(WorkflowDomainDto domainDto);

    // ==================== 6단계: Service 자동 생성 ====================
    
    /**
     * Service 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateService(WorkflowDomainDto domainDto);

    // ==================== 7단계: Process 자동 생성 ====================
    
    /**
     * Process 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateProcess(WorkflowDomainDto domainDto);

    // ==================== 8단계: Domain Service 자동 생성 ====================
    
    /**
     * Domain Service 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateDomainService(WorkflowDomainDto domainDto);

    // ==================== 9단계: Controller 자동 생성 ====================
    
    /**
     * Controller 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateController(WorkflowDomainDto domainDto);

    // ==================== 8단계: HTML 템플릿 생성 ====================
    
    /**
     * HTML 템플릿 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateTemplate(WorkflowDomainDto domainDto);

    // ==================== 9단계: Swagger 자동 문서화 ====================
    
    /**
     * Swagger 문서 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateSwaggerDocumentation(WorkflowDomainDto domainDto);

    // ==================== 10단계: 초기 데이터 생성 ====================
    
    /**
     * 초기 데이터 생성 (Domain DTO 사용)
     */
    WorkflowDomainDto generateInitialData(WorkflowDomainDto domainDto);

    // ==================== 전체 워크플로우 ====================
    
    /**
     * 전체 워크플로우 실행 (Domain DTO 사용)
     */
    WorkflowDomainDto executeFullWorkflow(WorkflowDomainDto domainDto);
} 