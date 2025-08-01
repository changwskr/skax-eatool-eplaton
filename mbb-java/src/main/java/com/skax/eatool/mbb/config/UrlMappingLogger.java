package com.skax.eatool.mbb.config;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

/**
 * URL 매핑 정보를 로그로 출력하는 컴포넌트
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class UrlMappingLogger implements CommandLineRunner {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(String... args) throws Exception {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== MBB Java Application URL Mapping Information ===");
        logger.info(className, "Application Context Path: /mbb");
        logger.info(className, "Server Port: 8086");
        logger.info(className, "Base URL: http://localhost:8086/mbb");
        logger.info(className, "");
        
        // Bean 등록 상태 확인
        logger.info(className, "=== Bean Registration Status ===");
        try {
            // Controller Bean 확인
            logger.info(className, "TableDefinitionController Bean: " + 
                (requestMappingHandlerMapping.getApplicationContext().containsBean("tableDefinitionController") ? "REGISTERED" : "NOT FOUND"));
            
            // AS Bean 확인
            logger.info(className, "ASTableCreation Bean: " + 
                (requestMappingHandlerMapping.getApplicationContext().containsBean("ASTableCreation") ? "REGISTERED" : "NOT FOUND"));
            
            // PC Bean 확인
            logger.info(className, "PCTableCreation Bean: " + 
                (requestMappingHandlerMapping.getApplicationContext().containsBean("PCTableCreation") ? "REGISTERED" : "NOT FOUND"));
            
            // DC Bean 확인
            logger.info(className, "DCTableCreation Bean: " + 
                (requestMappingHandlerMapping.getApplicationContext().containsBean("DCTableCreation") ? "REGISTERED" : "NOT FOUND"));
            
        } catch (Exception e) {
            logger.error(className, "Bean 등록 상태 확인 중 오류: " + e.getMessage());
        }
        logger.info(className, "");
        
        // 실제 URL 매핑 정보 수집
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        
        logger.info(className, "=== Dynamic URL Mappings ===");
        logger.info(className, "Total mapped endpoints: " + handlerMethods.size());
        logger.info(className, "");
        
        // URL 매핑 정보를 카테고리별로 분류하여 출력
        printWebPages(handlerMethods);
        printApiEndpoints(handlerMethods);
        printDevelopmentTools();
        printSecurityConfiguration();
        printDatabaseInformation();
        printArchitectureInformation();
        printAICodingWorkflow();
        printApplicationStartupInfo();
        
        logger.info(className, "=== MBB Java Application URL Mapping Information End ===");
    }
    
    private void printWebPages(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Web Pages ===");
        logger.info(className, "GET  /mbb/                    → HomeController.showHomePage");
        logger.info(className, "GET  /mbb/table-definition     → TableDefinitionController.showTableDefinitionPage");
        logger.info(className, "GET  /mbb/entity-generator     → EntityGeneratorController.showEntityGeneratorPage");
        logger.info(className, "GET  /mbb/repository-generator → RepositoryGeneratorController.showRepositoryGeneratorPage");
        logger.info(className, "GET  /mbb/service-generator    → ServiceGeneratorController.showServiceGeneratorPage");
        logger.info(className, "GET  /mbb/controller-generator → ControllerGeneratorController.showControllerGeneratorPage");
        logger.info(className, "GET  /mbb/dto-generator        → DtoGeneratorController.showDtoGeneratorPage");
        logger.info(className, "GET  /mbb/template-generator   → TemplateGeneratorController.showTemplateGeneratorPage");
        logger.info(className, "GET  /mbb/ddl-generator        → DdlGeneratorController.showDdlGeneratorPage");
        logger.info(className, "GET  /mbb/data-generator       → DataGeneratorController.showDataGeneratorPage");
        logger.info(className, "GET  /mbb/workflow-generator   → WorkflowGeneratorController.showWorkflowGeneratorPage");
        logger.info(className, "GET  /mbb/api-test             → ApiTestController.showApiTestPage");
        logger.info(className, "");
        
        // 실제 매핑된 웹 페이지 출력
        logger.info(className, "=== Actual Mapped Web Pages ===");
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            
            if (handlerMethod.getBeanType().getSimpleName().endsWith("Controller") && 
                !handlerMethod.getBeanType().getSimpleName().contains("Api")) {
                
                Set<String> patterns = mappingInfo.getPatternValues();
                for (String pattern : patterns) {
                    if (pattern.startsWith("/")) {
                        String method = mappingInfo.getMethodsCondition().getMethods().isEmpty() ? 
                            "GET" : mappingInfo.getMethodsCondition().getMethods().iterator().next().name();
                        String controllerName = handlerMethod.getBeanType().getSimpleName();
                        String methodName = handlerMethod.getMethod().getName();
                        
                        logger.info(className, String.format("%-4s /mbb%s → %s.%s", 
                            method, pattern, controllerName, methodName));
                    }
                }
            }
        }
        logger.info(className, "");
    }
    
    private void printApiEndpoints(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== API Endpoints ===");
        logger.info(className, "POST /mbb/table-definition/create-table → TableDefinitionController.createTable");
        logger.info(className, "POST /mbb/api/users/register           → UserController.registerUser");
        logger.info(className, "POST /mbb/api/users/login              → UserController.loginUser");
        logger.info(className, "GET  /mbb/api/users/list               → UserController.getAllUsers");
        logger.info(className, "GET  /mbb/api/users/{userId}           → UserController.getUserById");
        logger.info(className, "GET  /mbb/api/users/username/{username} → UserController.getUserByUsername");
        logger.info(className, "PUT  /mbb/api/users/{userId}           → UserController.updateUser");
        logger.info(className, "POST /mbb/api/code-generation/generate → CodeGenerationController.generateCode");
        logger.info(className, "POST /mbb/api/code-generation/validate → CodeGenerationController.validateTableDefinition");
        logger.info(className, "GET  /mbb/api/code-generation/status/{taskId} → CodeGenerationController.getTaskStatus");
        logger.info(className, "POST /mbb/api/code-generation/download/{taskId} → CodeGenerationController.createDownloadUrl");
        logger.info(className, "GET  /mbb/api/code-generation/download/{taskId}/file → CodeGenerationController.downloadFile");
        logger.info(className, "POST /mbb/api/mcp/execute              → MCPController.executeMCP");
        logger.info(className, "");
        
        // 실제 매핑된 API 엔드포인트 출력
        logger.info(className, "=== Actual Mapped API Endpoints ===");
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            
            Set<String> patterns = mappingInfo.getPatternValues();
            for (String pattern : patterns) {
                if (pattern.startsWith("/api/") || pattern.contains("/create-table")) {
                    String method = mappingInfo.getMethodsCondition().getMethods().isEmpty() ? 
                        "GET" : mappingInfo.getMethodsCondition().getMethods().iterator().next().name();
                    String controllerName = handlerMethod.getBeanType().getSimpleName();
                    String methodName = handlerMethod.getMethod().getName();
                    
                    logger.info(className, String.format("%-4s /mbb%s → %s.%s", 
                        method, pattern, controllerName, methodName));
                }
            }
        }
        logger.info(className, "");
    }
    
    private void printDevelopmentTools() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Development Tools ===");
        logger.info(className, "GET  /mbb/h2-console                  → H2 Database Console");
        logger.info(className, "GET  /mbb/swagger-ui.html             → Swagger UI");
        logger.info(className, "GET  /mbb/api-docs                    → OpenAPI Documentation");
        logger.info(className, "GET  /mbb/actuator                    → Spring Boot Actuator");
        logger.info(className, "");
    }
    
    private void printSecurityConfiguration() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Security Configuration ===");
        logger.info(className, "Permitted URLs (no authentication required):");
        logger.info(className, "  - /mbb/ (Home page)");
        logger.info(className, "  - /mbb/table-definition");
        logger.info(className, "  - /mbb/entity-generator");
        logger.info(className, "  - /mbb/repository-generator");
        logger.info(className, "  - /mbb/service-generator");
        logger.info(className, "  - /mbb/controller-generator");
        logger.info(className, "  - /mbb/dto-generator");
        logger.info(className, "  - /mbb/template-generator");
        logger.info(className, "  - /mbb/ddl-generator");
        logger.info(className, "  - /mbb/data-generator");
        logger.info(className, "  - /mbb/workflow-generator");
        logger.info(className, "  - /mbb/api-test");
        logger.info(className, "  - /mbb/api/users/**");
        logger.info(className, "  - /mbb/api/code-generation/**");
        logger.info(className, "  - /mbb/api/mcp/**");
        logger.info(className, "  - /mbb/table-definition/create-table");
        logger.info(className, "  - /mbb/h2-console/**");
        logger.info(className, "  - /mbb/swagger-ui.html");
        logger.info(className, "  - /mbb/swagger-ui/**");
        logger.info(className, "  - /mbb/api-docs/**");
        logger.info(className, "  - /mbb/actuator/**");
        logger.info(className, "");
    }
    
    private void printDatabaseInformation() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Database Information ===");
        logger.info(className, "Database Type: H2 (In-Memory)");
        logger.info(className, "Database URL: jdbc:h2:mem:testdb");
        logger.info(className, "Username: sa");
        logger.info(className, "Password: (empty)");
        logger.info(className, "");
    }
    
    private void printArchitectureInformation() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Architecture Information ===");
        logger.info(className, "Layered Architecture: Controller → AS → PC → DC");
        logger.info(className, "Package Structure: com.skax.eatool.mbb.{layer}.{function}");
        logger.info(className, "Naming Convention: XXXController, XXXAS, XXXPC, XXXDC");
        logger.info(className, "");
    }
    
    private void printAICodingWorkflow() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== AI Coding Workflow ===");
        logger.info(className, "1. Table Definition → /mbb/table-definition");
        logger.info(className, "2. Entity Generation → /mbb/entity-generator");
        logger.info(className, "3. Repository Generation → /mbb/repository-generator");
        logger.info(className, "4. Service Generation → /mbb/service-generator");
        logger.info(className, "5. Controller Generation → /mbb/controller-generator");
        logger.info(className, "6. DTO Generation → /mbb/dto-generator");
        logger.info(className, "7. Template Generation → /mbb/template-generator");
        logger.info(className, "8. DDL Generation → /mbb/ddl-generator");
        logger.info(className, "9. Data Generation → /mbb/data-generator");
        logger.info(className, "10. Workflow Generation → /mbb/workflow-generator");
        logger.info(className, "");
    }
    
    private void printApplicationStartupInfo() {
        String className = this.getClass().getSimpleName();
        
        logger.info(className, "=== Application Started Successfully ===");
        logger.info(className, "Access the application at: http://localhost:8086/mbb");
        logger.info(className, "H2 Console: http://localhost:8086/mbb/h2-console");
        logger.info(className, "Swagger UI: http://localhost:8086/mbb/swagger-ui.html");
        logger.info(className, "");
    }
} 