package com.skax.eatool.mbb.workflow.business.controller;

import com.skax.eatool.mbb.workflow.business.as.WorkflowAS;
import com.skax.eatool.mbb.workflow.business.dc.WorkflowDC;
import com.skax.eatool.mbb.workflow.transfer.WorkflowRequestDto;
import com.skax.eatool.mbb.workflow.transfer.WorkflowResponseDto;
import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;
import com.skax.eatool.mbb.workflow.business.pc.WorkflowPC;
import com.skax.eatool.mbb.web.controller.apitestcontroller.helper.WorkflowHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * AI 워크플로우 전체 과정을 관리하는 컨트롤러
 * URL 호출 -> Controller -> AS -> PC -> DC -> Repository -> DTO 흐름
 */
@Controller
@RequestMapping("/mbb")
public class WorkflowController {

    @Autowired
    private WorkflowAS workflowAS;

    @Autowired
    private WorkflowPC workflowPC;

    @Autowired
    private WorkflowDC workflowDC;

    // ==================== 1단계: 테이블 정의서 입력 ====================
    
    /**
     * 테이블 정의서 입력 페이지
     */
    @GetMapping("/table-definition")
    public String tableDefinitionPage(Model model) {
        model.addAttribute("title", "테이블 정의서 입력");
        return "table-definition";
    }

    /**
     * 테이블 정의서 저장
     */
    @PostMapping("/table-definition/save")
    @ResponseBody
    public WorkflowResponseDto saveTableDefinition(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.saveTableDefinition(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("테이블 정의서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 2단계: DDL 자동 Export ====================
    
    /**
     * DDL 생성 페이지
     */
    @GetMapping("/ddl-generator")
    public String ddlGeneratorPage(Model model) {
        model.addAttribute("title", "DDL 자동 생성");
        return "ddl-generator";
    }

    /**
     * DDL 생성 실행
     */
    @PostMapping("/ddl-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateDDL(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateDDL(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 3단계: Entity 자동 생성 ====================
    
    /**
     * Entity 생성 페이지
     */
    @GetMapping("/entity-generator")
    public String entityGeneratorPage(Model model) {
        model.addAttribute("title", "Entity 자동 생성");
        return "entity-generator";
    }

    /**
     * Entity 생성 실행
     */
    @PostMapping("/entity-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateEntity(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateEntity(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 고급 Entity 생성 페이지 (테이블 정보 기반)
     */
    @GetMapping("/entity/entity-generation")
    public String entityGenerationPage(Model model) {
        model.addAttribute("title", "Entity 생성 (고급)");
        return "entity/entity-generation";
    }

    /**
     * 테이블 목록 조회
     */
    @GetMapping("/api/entity/tables")
    @ResponseBody
    public WorkflowResponseDto getAvailableTables() {
        try {
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.getAvailableTables();
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("테이블 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 테이블 정보 조회
     */
    @GetMapping("/api/entity/table-info/{tableName}")
    @ResponseBody
    public WorkflowResponseDto getTableInfo(@PathVariable String tableName) {
        try {
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.getTableInfo(tableName);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("테이블 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * Entity 생성 (컬럼 정보 포함)
     */
    @PostMapping("/api/entity/generate-with-columns")
    @ResponseBody
    public WorkflowResponseDto generateEntityWithColumns(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToEntityDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateEntityWithColumns(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 4단계: Repository 자동 생성 ====================
    
    /**
     * Repository 생성 페이지
     */
    @GetMapping("/repository-generator")
    public String repositoryGeneratorPage(Model model) {
        model.addAttribute("title", "Repository 자동 생성");
        return "repository-generator";
    }

    /**
     * Repository 생성 실행
     */
    @PostMapping("/repository-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateRepository(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateRepository(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Repository 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 5단계: DTO & Validator 생성 ====================
    
    /**
     * DTO 생성 페이지
     */
    @GetMapping("/dto-generator")
    public String dtoGeneratorPage() {
        return "dto-generator";
    }

    /**
     * DTO 생성 실행
     */
    @PostMapping("/dto/generate")
    @ResponseBody
    public WorkflowResponseDto generateDTO(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateDTO(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("DTO 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 6단계: Service 자동 생성 ====================
    
    /**
     * Service 생성 페이지
     */
    @GetMapping("/service-generator")
    public String serviceGeneratorPage(Model model) {
        model.addAttribute("title", "Service 자동 생성");
        return "service-generator";
    }

    /**
     * Service 생성 실행
     */
    @PostMapping("/service-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateService(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateService(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Service 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 7단계: Controller 자동 생성 ====================
    
    /**
     * Controller 생성 페이지
     */
    @GetMapping("/controller-generator")
    public String controllerGeneratorPage(Model model) {
        model.addAttribute("title", "Controller 자동 생성");
        return "controller-generator";
    }

    /**
     * Controller 생성 실행
     */
    @PostMapping("/controller-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateController(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateController(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Controller 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 8단계: HTML 템플릿 생성 ====================
    
    /**
     * HTML 템플릿 생성 페이지
     */
    @GetMapping("/template-generator")
    public String templateGeneratorPage(Model model) {
        model.addAttribute("title", "HTML 템플릿 생성");
        return "template-generator";
    }

    /**
     * HTML 템플릿 생성 실행
     */
    @PostMapping("/template-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateTemplate(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateTemplate(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("HTML 템플릿 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 9단계: Swagger 자동 문서화 ====================
    
    /**
     * Swagger 문서화 페이지
     */
    @GetMapping("/swagger-documentation")
    public String swaggerDocumentationPage(Model model) {
        model.addAttribute("title", "Swagger 자동 문서화");
        return "swagger-documentation";
    }

    /**
     * Swagger 문서 생성
     */
    @PostMapping("/swagger-documentation/generate")
    @ResponseBody
    public WorkflowResponseDto generateSwaggerDocumentation(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateSwaggerDocumentation(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("Swagger 문서 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 10단계: 초기 데이터 생성 ====================
    
    /**
     * 초기 데이터 생성 페이지
     */
    @GetMapping("/data-generator")
    public String dataGeneratorPage(Model model) {
        model.addAttribute("title", "초기 데이터 생성");
        return "data-generator";
    }

    /**
     * 초기 데이터 생성 실행
     */
    @PostMapping("/data-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateInitialData(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateInitialData(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("초기 데이터 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 11단계: API 테스트 ====================
    
    /**
     * API 테스트 페이지
     */
    @GetMapping("/api-test")
    public String apiTestPage(Model model) {
        model.addAttribute("title", "API 테스트");
        return "api-test";
    }

    // ==================== 전체 워크플로우 ====================
    
    /**
     * 전체 워크플로우 페이지
     */
    @GetMapping("/workflow-generator")
    public String workflowGeneratorPage(Model model) {
        model.addAttribute("title", "전체 워크플로우");
        return "workflow-generator";
    }

    /**
     * 전체 워크플로우 실행
     */
    @PostMapping("/workflow-generator/execute")
    @ResponseBody
    public WorkflowResponseDto executeFullWorkflow(@RequestBody WorkflowRequestDto requestDto) {
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToFullWorkflowDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.executeFullWorkflow(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            return WorkflowHelper.convertDomainToResponse(resultDomainDto);
        } catch (Exception e) {
            return WorkflowHelper.createErrorResponse("전체 워크플로우 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 추가 도구들 ====================
    
    /**
     * H2 Console 테스트 페이지
     */
    @GetMapping("/h2-console-test")
    public String h2ConsoleTestPage(Model model) {
        model.addAttribute("title", "H2 Console 테스트");
        return "h2-console-test";
    }

    /**
     * 홈 페이지
     */
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "MBB Java - AI 코딩 워크플로우");
        return "home";
    }
    

} 