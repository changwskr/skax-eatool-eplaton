package com.skax.eatool.mbb.workflow.business.pc;

import com.skax.eatool.mbb.workflow.business.dc.WorkflowDC;
import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 워크플로우 Presentation Controller
 * 프레젠테이션 로직을 처리하고 DC를 호출하여 데이터 접근을 담당
 */
@Component
public class WorkflowPC {

    @Autowired
    private WorkflowDC workflowDC;

    // ==================== 1단계: 테이블 정의서 입력 ====================
    
    /**
     * 테이블 정의서 저장 (Domain DTO 사용)
     */
    public WorkflowDomainDto saveTableDefinition(WorkflowDomainDto domainDto) {
        try {
            // 프레젠테이션 로직 처리
            String tableName = domainDto.getTableDefinition().getTableName();
            String tableDefinition = domainDto.getTableDefinition().getDefinitionText();
            
            // DC 호출하여 데이터 저장
            return workflowDC.saveTableDefinition(domainDto);
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
            // 프레젠테이션 로직 처리
            String tableName = domainDto.getTableDefinition().getTableName();
            String databaseType = domainDto.getTableDefinition().getDatabaseType();
            
            // DC 호출하여 DDL 생성
            return workflowDC.generateDDL(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getEntityGeneration().getEntityName();
            String packageName = domainDto.getEntityGeneration().getPackageName();
            
            // DC 호출하여 Entity 생성
            return workflowDC.generateEntity(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용 가능한 테이블 목록 조회
     */
    public WorkflowDomainDto getAvailableTables() {
        try {
            // DC 호출하여 테이블 목록 조회
            return workflowDC.getAvailableTables();
        } catch (Exception e) {
            throw new RuntimeException("테이블 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 테이블 정보 조회 (Domain DTO 사용)
     */
    public WorkflowDomainDto getTableInfo(String tableName) {
        try {
            // DC 호출하여 테이블 정보 조회
            return workflowDC.getTableInfo(tableName);
        } catch (Exception e) {
            throw new RuntimeException("테이블 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * Entity 생성 (컬럼 정보 포함, Domain DTO 사용)
     */
    public WorkflowDomainDto generateEntityWithColumns(WorkflowDomainDto domainDto) {
        try {
            // 프레젠테이션 로직 처리
            String tableName = domainDto.getEntityGeneration().getTableName();
            String packageName = domainDto.getEntityGeneration().getPackageName();
            List<WorkflowDomainDto.ColumnDefinitionDomain> columns = domainDto.getEntityGeneration().getColumns();
            
            // DC 호출하여 Entity 생성
            return workflowDC.generateEntityWithColumns(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getRepositoryGeneration().getEntityName();
            String packageName = domainDto.getRepositoryGeneration().getPackageName();
            
            // DC 호출하여 Repository 생성
            return workflowDC.generateRepository(domainDto);
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
            
            // DC 호출
            return workflowDC.generateDTO(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getServiceGeneration().getEntityName();
            String packageName = domainDto.getServiceGeneration().getPackageName();
            
            // DC 호출하여 Service 생성
            return workflowDC.generateService(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getControllerGeneration().getEntityName();
            String packageName = domainDto.getControllerGeneration().getPackageName();
            
            // DC 호출하여 Controller 생성
            return workflowDC.generateController(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getTemplateGeneration().getEntityName();
            String packageName = domainDto.getTemplateGeneration().getPackageName();
            
            // DC 호출하여 HTML 템플릿 생성
            return workflowDC.generateTemplate(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getSwaggerGeneration().getEntityName();
            String packageName = domainDto.getSwaggerGeneration().getPackageName();
            
            // DC 호출하여 Swagger 문서 생성
            return workflowDC.generateSwaggerDocumentation(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getDataGeneration().getEntityName();
            String tableName = domainDto.getDataGeneration().getTableName();
            
            // DC 호출하여 초기 데이터 생성
            return workflowDC.generateInitialData(domainDto);
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
            // 프레젠테이션 로직 처리
            String entityName = domainDto.getFullWorkflow().getEntityName();
            String tableName = domainDto.getFullWorkflow().getTableName();
            String packageName = domainDto.getFullWorkflow().getPackageName();
            
            // DC 호출하여 전체 워크플로우 실행
            return workflowDC.executeFullWorkflow(domainDto);
        } catch (Exception e) {
            throw new RuntimeException("전체 워크플로우 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 