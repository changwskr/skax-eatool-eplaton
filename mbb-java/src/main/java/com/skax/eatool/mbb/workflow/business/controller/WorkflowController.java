package com.skax.eatool.mbb.workflow.business.controller;

import com.skax.eatool.mbb.workflow.business.as.WorkflowAS;
import com.skax.eatool.mbb.workflow.business.dc.WorkflowDC;
import com.skax.eatool.mbb.workflow.transfer.WorkflowRequestDto;
import com.skax.eatool.mbb.workflow.transfer.WorkflowResponseDto;
import com.skax.eatool.mbb.workflow.business.dc.dto.WorkflowDomainDto;
import com.skax.eatool.mbb.workflow.business.pc.WorkflowPC;
import com.skax.eatool.mbb.web.controller.apitestcontroller.helper.WorkflowHelper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("[WorkflowController] tableDefinitionPage START");
        try {
            model.addAttribute("title", "테이블 정의서 입력");
            log.info("[WorkflowController] tableDefinitionPage END");
            return "table-definition";
        } catch (Exception e) {
            log.error("[WorkflowController] tableDefinitionPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 테이블 정의서 저장
     */
    @PostMapping("/table-definition/save")
    @ResponseBody
    public WorkflowResponseDto saveTableDefinition(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] saveTableDefinition START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.saveTableDefinition(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] saveTableDefinition END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] saveTableDefinition ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("테이블 정의서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 2단계: DDL 자동 Export ====================
    
    /**
     * DDL 생성 페이지
     */
    @GetMapping("/ddl-generator")
    public String ddlGeneratorPage(Model model) {
        log.info("[WorkflowController] ddlGeneratorPage START");
        try {
            model.addAttribute("title", "DDL 자동 생성");
            log.info("[WorkflowController] ddlGeneratorPage END");
            return "ddl-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] ddlGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * DDL 생성 실행
     */
    @PostMapping("/ddl-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateDDL(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateDDL START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateDDL(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateDDL END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateDDL ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 3단계: Entity 자동 생성 ====================
    
    /**
     * Entity 생성 페이지
     */
    @GetMapping("/entity-generator")
    public String entityGeneratorPage(Model model) {
        log.info("[WorkflowController] entityGeneratorPage START");
        try {
            model.addAttribute("title", "Entity 자동 생성");
            log.info("[WorkflowController] entityGeneratorPage END");
            return "entity-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] entityGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Entity 생성 실행
     */
    @PostMapping("/entity-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateEntity(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateEntity START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateEntity(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateEntity END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateEntity ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 고급 Entity 생성 페이지 (테이블 정보 기반)
     */
    @GetMapping("/entity/entity-generation")
    public String entityGenerationPage(Model model) {
        log.info("[WorkflowController] entityGenerationPage START");
        try {
            model.addAttribute("title", "Entity 생성 (고급)");
            log.info("[WorkflowController] entityGenerationPage END");
            return "entity/entity-generation";
        } catch (Exception e) {
            log.error("[WorkflowController] entityGenerationPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 테이블 목록 조회
     */
    @GetMapping("/api/entity/tables")
    @ResponseBody
    public WorkflowResponseDto getAvailableTables() {
        log.info("[WorkflowController] getAvailableTables START");
        try {
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.getAvailableTables();
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] getAvailableTables END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] getAvailableTables ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("테이블 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 테이블 정보 조회
     */
    @GetMapping("/api/entity/table-info/{tableName}")
    @ResponseBody
    public WorkflowResponseDto getTableInfo(@PathVariable String tableName) {
        log.info("[WorkflowController] getTableInfo START");
        try {
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.getTableInfo(tableName);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] getTableInfo END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] getTableInfo ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("테이블 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * Entity 생성 (컬럼 정보 포함)
     */
    @PostMapping("/api/entity/generate-with-columns")
    @ResponseBody
    public WorkflowResponseDto generateEntityWithColumns(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateEntityWithColumns START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToEntityDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateEntityWithColumns(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateEntityWithColumns END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateEntityWithColumns ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Entity 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 4단계: Repository 자동 생성 ====================
    
    // Repository 생성은 별도 Controller에서 처리
    // @GetMapping("/repository-generator")
    // @PostMapping("/repository-generator/generate")

    // ==================== 5단계: DTO & Validator 생성 ====================
    
    /**
     * DTO 생성 페이지
     */
    @GetMapping("/dto-generator")
    public String dtoGeneratorPage() {
        log.info("[WorkflowController] dtoGeneratorPage START");
        try {
            return "dto-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] dtoGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * DTO 생성 실행
     */
    @PostMapping("/dto/generate")
    @ResponseBody
    public WorkflowResponseDto generateDTO(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateDTO START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateDTO(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateDTO END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateDTO ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("DTO 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 6단계: Service 자동 생성 ====================
    
    /**
     * Service 생성 페이지
     */
    @GetMapping("/service-generator-sample")
    public String serviceGeneratorPage(Model model) {
        log.info("[WorkflowController] serviceGeneratorPage START");
        try {
            model.addAttribute("title", "Service 자동 생성");
            log.info("[WorkflowController] serviceGeneratorPage END");
            return "service-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] serviceGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Service 생성 실행
     */
    @PostMapping("/service-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateService(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateService START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateService(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateService END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateService ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Service 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 7단계: Process 자동 생성 ====================
    
    /**
     * Process 생성 페이지
     */
    @GetMapping("/process-generator-sample")
    public String processGeneratorPage(Model model) {
        log.info("[WorkflowController] processGeneratorPage START");
        try {
            model.addAttribute("title", "Process 자동 생성");
            log.info("[WorkflowController] processGeneratorPage END");
            return "process-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] processGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Process 생성 실행
     */
    @PostMapping("/process-generator/generate-sample")
    @ResponseBody
    public WorkflowResponseDto generateProcess(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateProcess START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateProcess(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateProcess END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateProcess ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Process 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 8단계: Domain Service 자동 생성 ====================
    
    /**
     * Domain Service 생성 페이지
     */
    @GetMapping("/domain-service-generator-sample")
    public String domainServiceGeneratorPage(Model model) {
        log.info("[WorkflowController] domainServiceGeneratorPage START");
        try {
            model.addAttribute("title", "Domain Service 자동 생성");
            log.info("[WorkflowController] domainServiceGeneratorPage END");
            return "domain-service-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] domainServiceGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Domain Service 생성 실행
     */
    @PostMapping("/domain-service-generator/generate-sample")
    @ResponseBody
    public WorkflowResponseDto generateDomainService(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateDomainService START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateDomainService(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateDomainService END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateDomainService ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Domain Service 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 9단계: Controller 자동 생성 ====================
    
    /**
     * Controller 생성 페이지
     */
    @GetMapping("/controller-generator")
    public String controllerGeneratorPage(Model model) {
        log.info("[WorkflowController] controllerGeneratorPage START");
        try {
            model.addAttribute("title", "Controller 자동 생성");
            log.info("[WorkflowController] controllerGeneratorPage END");
            return "controller-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] controllerGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Controller 생성 실행
     */
    @PostMapping("/controller-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateController(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateController START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateController(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateController END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateController ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Controller 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 8단계: HTML 템플릿 생성 ====================
    
    /**
     * HTML 템플릿 생성 페이지
     */
    @GetMapping("/template-generator")
    public String templateGeneratorPage(Model model) {
        log.info("[WorkflowController] templateGeneratorPage START");
        try {
            model.addAttribute("title", "HTML 템플릿 생성");
            log.info("[WorkflowController] templateGeneratorPage END");
            return "template-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] templateGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * HTML 템플릿 생성 실행
     */
    @PostMapping("/template-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateTemplate(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateTemplate START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateTemplate(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateTemplate END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateTemplate ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("HTML 템플릿 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 9단계: Swagger 자동 문서화 ====================
    
    /**
     * Swagger 문서화 페이지
     */
    @GetMapping("/swagger-documentation")
    public String swaggerDocumentationPage(Model model) {
        log.info("[WorkflowController] swaggerDocumentationPage START");
        try {
            model.addAttribute("title", "Swagger 자동 문서화");
            log.info("[WorkflowController] swaggerDocumentationPage END");
            return "swagger-documentation";
        } catch (Exception e) {
            log.error("[WorkflowController] swaggerDocumentationPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Swagger 문서 생성
     */
    @PostMapping("/swagger-documentation/generate")
    @ResponseBody
    public WorkflowResponseDto generateSwaggerDocumentation(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateSwaggerDocumentation START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateSwaggerDocumentation(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateSwaggerDocumentation END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateSwaggerDocumentation ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("Swagger 문서 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 10단계: 초기 데이터 생성 ====================
    
    /**
     * 초기 데이터 생성 페이지
     */
    @GetMapping("/data-generator")
    public String dataGeneratorPage(Model model) {
        log.info("[WorkflowController] dataGeneratorPage START");
        try {
            model.addAttribute("title", "초기 데이터 생성");
            log.info("[WorkflowController] dataGeneratorPage END");
            return "data-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] dataGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 초기 데이터 생성 실행
     */
    @PostMapping("/data-generator/generate")
    @ResponseBody
    public WorkflowResponseDto generateInitialData(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] generateInitialData START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.generateInitialData(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] generateInitialData END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] generateInitialData ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("초기 데이터 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 11단계: API 테스트 ====================
    
    /**
     * API 테스트 페이지
     */
    @GetMapping("/api-test")
    public String apiTestPage(Model model) {
        log.info("[WorkflowController] apiTestPage START");
        try {
            model.addAttribute("title", "API 테스트");
            log.info("[WorkflowController] apiTestPage END");
            return "api-test";
        } catch (Exception e) {
            log.error("[WorkflowController] apiTestPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    // ==================== 전체 워크플로우 ====================
    
    /**
     * 전체 워크플로우 페이지
     */
    @GetMapping("/workflow-generator")
    public String workflowGeneratorPage(Model model) {
        log.info("[WorkflowController] workflowGeneratorPage START");
        try {
            model.addAttribute("title", "전체 워크플로우");
            log.info("[WorkflowController] workflowGeneratorPage END");
            return "workflow-generator";
        } catch (Exception e) {
            log.error("[WorkflowController] workflowGeneratorPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 전체 워크플로우 실행
     */
    @PostMapping("/workflow-generator/execute")
    @ResponseBody
    public WorkflowResponseDto executeFullWorkflow(@RequestBody WorkflowRequestDto requestDto) {
        log.info("[WorkflowController] executeFullWorkflow START");
        try {
            // Transfer DTO를 Domain DTO로 변환
            WorkflowDomainDto domainDto = WorkflowHelper.convertRequestToFullWorkflowDomain(requestDto);
            
            // AS 호출하여 Domain DTO 처리
            WorkflowDomainDto resultDomainDto = workflowAS.executeFullWorkflow(domainDto);
            
            // Domain DTO를 Response DTO로 변환
            WorkflowResponseDto response = WorkflowHelper.convertDomainToResponse(resultDomainDto);
            log.info("[WorkflowController] executeFullWorkflow END");
            return response;
        } catch (Exception e) {
            log.error("[WorkflowController] executeFullWorkflow ERROR: {}", e.getMessage());
            return WorkflowHelper.createErrorResponse("전체 워크플로우 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // ==================== 추가 도구들 ====================
    
    /**
     * H2 Console 테스트 페이지
     */
    @GetMapping("/h2-console-test")
    public String h2ConsoleTestPage(Model model) {
        log.info("[WorkflowController] h2ConsoleTestPage START");
        try {
            model.addAttribute("title", "H2 Console 테스트");
            log.info("[WorkflowController] h2ConsoleTestPage END");
            return "h2-console-test";
        } catch (Exception e) {
            log.error("[WorkflowController] h2ConsoleTestPage ERROR: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 홈 페이지
     */
    @GetMapping("/")
    public String homePage(Model model) {
        log.info("[WorkflowController] homePage START");
        try {
            model.addAttribute("title", "MBB Java - AI 코딩 워크플로우");
            log.info("[WorkflowController] homePage END");
            return "home";
        } catch (Exception e) {
            log.error("[WorkflowController] homePage ERROR: {}", e.getMessage());
            throw e;
        }
    }
    

} 