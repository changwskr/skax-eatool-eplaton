package com.skax.eatool.mbb.workflow.business.as;

import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;
import com.skax.eatool.mbb.workflow.business.pc.WorkflowPC;
import com.skax.eatool.mbb.web.controller.apitestcontroller.helper.WorkflowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 워크플로우 Application Service
 * 비즈니스 로직을 처리하고 PC를 호출하여 데이터 처리를 담당
 */
@Service
@Transactional
public class WorkflowAS {

    @Autowired
    private WorkflowPC workflowPC;

    // ==================== 1단계: 테이블 정의서 입력 ====================
    
    /**
     * 테이블 정의서 저장 (Domain DTO 사용)
     */
    public WorkflowDomainDto saveTableDefinition(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getTableDefinition() == null || 
                domainDto.getTableDefinition().getTableName() == null || 
                domainDto.getTableDefinition().getTableName().trim().isEmpty()) {
                throw new IllegalArgumentException("테이블명은 필수입니다.");
            }
            
            // PC 호출하여 데이터 처리
            return workflowPC.saveTableDefinition(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("테이블 정의서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 2단계: DDL 자동 Export ====================
    
    /**
     * DDL 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateDDL(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getTableDefinition() == null || 
                domainDto.getTableDefinition().getTableName() == null || 
                domainDto.getTableDefinition().getTableName().trim().isEmpty()) {
                throw new IllegalArgumentException("테이블명은 필수입니다.");
            }
            
            // PC 호출하여 DDL 생성
            return workflowPC.generateDDL(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 3단계: Entity 자동 생성 ====================
    
    /**
     * Entity 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateEntity(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getEntityGeneration() == null || 
                domainDto.getEntityGeneration().getEntityName() == null || 
                domainDto.getEntityGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 Entity 생성
            return workflowPC.generateEntity(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용 가능한 테이블 목록 조회
     */
    public WorkflowDomainDto getAvailableTables() {
        try {
            return workflowPC.getAvailableTables();
        } catch (Exception e) {
            throw new RuntimeException("테이블 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 테이블 정보 조회 (Domain DTO 사용)
     */
    public WorkflowDomainDto getTableInfo(String tableName) {
        try {
            if (tableName == null || tableName.trim().isEmpty()) {
                throw new IllegalArgumentException("테이블명은 필수입니다.");
            }
            
            return workflowPC.getTableInfo(tableName);
        } catch (Exception e) {
            throw new RuntimeException("테이블 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * Entity 생성 (컬럼 정보 포함, Domain DTO 사용)
     */
    public WorkflowDomainDto generateEntityWithColumns(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getEntityGeneration() == null || 
                domainDto.getEntityGeneration().getTableName() == null || 
                domainDto.getEntityGeneration().getTableName().trim().isEmpty()) {
                throw new IllegalArgumentException("테이블명은 필수입니다.");
            }
            
            if (domainDto.getEntityGeneration().getPackageName() == null || 
                domainDto.getEntityGeneration().getPackageName().trim().isEmpty()) {
                throw new IllegalArgumentException("패키지명은 필수입니다.");
            }
            
            // PC 호출하여 Entity 생성
            return workflowPC.generateEntityWithColumns(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 4단계: Repository 자동 생성 ====================
    
    /**
     * Repository 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateRepository(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getRepositoryGeneration() == null || 
                domainDto.getRepositoryGeneration().getEntityName() == null || 
                domainDto.getRepositoryGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 Repository 생성
            return workflowPC.generateRepository(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Repository 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 5단계: DTO & Validator 생성 ====================
    
    /**
     * DTO 생성
     */
    public WorkflowDomainDto generateDTO(WorkflowDomainDto domainDto) {
        try {
            // 입력 검증
            if (domainDto.getDtoGeneration() == null) {
                throw new IllegalArgumentException("DTO 생성 정보가 없습니다.");
            }
            
            if (WorkflowHelper.isValidString(domainDto.getDtoGeneration().getEntityName())) {
                throw new IllegalArgumentException("엔티티명이 유효하지 않습니다.");
            }
            
            // PC 호출
            WorkflowDomainDto resultDto = workflowPC.generateDTO(domainDto);
            
            // 워크플로우 상태 업데이트
            WorkflowDomainDto.WorkflowStatusDomain status = new WorkflowDomainDto.WorkflowStatusDomain();
            status.setCurrentStep(4);
            status.setTotalSteps(11);
            status.setStatus("COMPLETED");
            status.setCurrentStepName("DTO 생성 완료");
            status.setMessage("DTO가 성공적으로 생성되었습니다.");
            resultDto.setWorkflowStatus(status);
            
            return resultDto;
        } catch (Exception e) {
            throw new RuntimeException("DTO 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 6단계: Service 자동 생성 ====================
    
    /**
     * Service 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateService(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getServiceGeneration() == null || 
                domainDto.getServiceGeneration().getEntityName() == null || 
                domainDto.getServiceGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 Service 생성
            return workflowPC.generateService(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Service 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 7단계: Controller 자동 생성 ====================
    
    /**
     * Controller 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateController(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getControllerGeneration() == null || 
                domainDto.getControllerGeneration().getEntityName() == null || 
                domainDto.getControllerGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 Controller 생성
            return workflowPC.generateController(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Controller 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 8단계: HTML 템플릿 생성 ====================
    
    /**
     * HTML 템플릿 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateTemplate(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getTemplateGeneration() == null || 
                domainDto.getTemplateGeneration().getEntityName() == null || 
                domainDto.getTemplateGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 HTML 템플릿 생성
            return workflowPC.generateTemplate(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("HTML 템플릿 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 9단계: Swagger 자동 문서화 ====================
    
    /**
     * Swagger 문서 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateSwaggerDocumentation(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getSwaggerGeneration() == null || 
                domainDto.getSwaggerGeneration().getEntityName() == null || 
                domainDto.getSwaggerGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 Swagger 문서 생성
            return workflowPC.generateSwaggerDocumentation(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Swagger 문서 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 10단계: 초기 데이터 생성 ====================
    
    /**
     * 초기 데이터 생성 (Domain DTO 사용)
     */
    public WorkflowDomainDto generateInitialData(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getDataGeneration() == null || 
                domainDto.getDataGeneration().getEntityName() == null || 
                domainDto.getDataGeneration().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 초기 데이터 생성
            return workflowPC.generateInitialData(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("초기 데이터 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 전체 워크플로우 ====================
    
    /**
     * 전체 워크플로우 실행 (Domain DTO 사용)
     */
    public WorkflowDomainDto executeFullWorkflow(WorkflowDomainDto domainDto) {
        try {
            // 비즈니스 로직 검증
            if (domainDto.getFullWorkflow() == null || 
                domainDto.getFullWorkflow().getEntityName() == null || 
                domainDto.getFullWorkflow().getEntityName().trim().isEmpty()) {
                throw new IllegalArgumentException("Entity명은 필수입니다.");
            }
            
            // PC 호출하여 전체 워크플로우 실행
            return workflowPC.executeFullWorkflow(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("전체 워크플로우 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 