package com.skax.eatool.mbb.web.controller.codegencontroller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.codegenas.ASMBB75001;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 코드 생성 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/code-generation")
@CrossOrigin(origins = "*")
@Tag(name = "AI Coding Workflow", description = "AI 코딩 워크플로우 API")
public class CodeGenerationController {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    @Autowired
    private ASMBB75001 asCodeGeneration;

    /**
     * 코드를 생성한다.
     * 
     * @param request 코드 생성 요청
     * @return 생성 결과
     */
    @PostMapping("/generate")
    @Operation(summary = "코드 생성", description = "테이블 정의를 기반으로 코드를 생성합니다.")
    public ResponseEntity<NewKBData> generateCode(@RequestBody CodeGenerationRequest request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== CodeGenerationController.generateCode START ===");
        
        try {
            // 1. 요청 데이터 구성
            NewKBData reqData = new NewKBData();
            reqData.setCommand("GENERATE");
            reqData.setTaskName(request.getTaskName());
            reqData.setDescription(request.getDescription());
            reqData.setTableDefinition(request.getTableDefinition());
            reqData.setTasks(request.getTasks());
            
            // 2. AS 호출하여 코드 생성
            NewKBData result = asCodeGeneration.execute(reqData);
            
            logger.info(className, "=== CodeGenerationController.generateCode END ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== CodeGenerationController.generateCode - Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("코드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 테이블 정의를 검증한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 검증 결과
     */
    @PostMapping("/validate")
    @Operation(summary = "테이블 정의 검증", description = "테이블 정의의 유효성을 검증합니다.")
    public ResponseEntity<NewKBData> validateTableDefinition(@RequestBody TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== CodeGenerationController.validateTableDefinition START ===");
        
        try {
            // 1. 요청 데이터 구성
            NewKBData reqData = new NewKBData();
            reqData.setCommand("VALIDATE");
            reqData.setTableDefinition(tableDefinition);
            
            // 2. AS 호출하여 검증 실행
            NewKBData result = asCodeGeneration.execute(reqData);
            
            logger.info(className, "=== CodeGenerationController.validateTableDefinition END ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== CodeGenerationController.validateTableDefinition - Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("테이블 정의 검증 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 작업 상태를 조회한다.
     * 
     * @param taskId 작업 ID
     * @return 작업 상태
     */
    @GetMapping("/status/{taskId}")
    @Operation(summary = "작업 상태 조회", description = "코드 생성 작업의 상태를 조회합니다.")
    public ResponseEntity<NewKBData> getTaskStatus(@PathVariable String taskId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== CodeGenerationController.getTaskStatus START ===");
        
        try {
            // 1. 요청 데이터 구성
            NewKBData reqData = new NewKBData();
            reqData.setCommand("STATUS");
            reqData.setTaskId(taskId);
            
            // 2. AS 호출하여 작업 상태 조회
            NewKBData result = asCodeGeneration.execute(reqData);
            
            logger.info(className, "=== CodeGenerationController.getTaskStatus END ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== CodeGenerationController.getTaskStatus - Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("작업 상태 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 파일 다운로드 URL을 생성한다.
     * 
     * @param taskId 작업 ID
     * @return 다운로드 URL
     */
    @PostMapping("/download/{taskId}")
    @Operation(summary = "파일 다운로드 URL 생성", description = "생성된 파일의 다운로드 URL을 생성합니다.")
    public ResponseEntity<NewKBData> createDownloadUrl(@PathVariable String taskId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== CodeGenerationController.createDownloadUrl START ===");
        
        try {
            // 1. 요청 데이터 구성
            NewKBData reqData = new NewKBData();
            reqData.setCommand("DOWNLOAD");
            reqData.setTaskId(taskId);
            
            // 2. AS 호출하여 다운로드 URL 생성
            NewKBData result = asCodeGeneration.execute(reqData);
            
            logger.info(className, "=== CodeGenerationController.createDownloadUrl END ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== CodeGenerationController.createDownloadUrl - Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("다운로드 URL 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * 파일을 다운로드한다.
     * 
     * @param taskId 작업 ID
     * @param response HTTP 응답
     */
    @GetMapping("/download/{taskId}/file")
    @Operation(summary = "파일 다운로드", description = "생성된 파일을 다운로드합니다.")
    public void downloadFile(@PathVariable String taskId, HttpServletResponse response) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== CodeGenerationController.downloadFile START ===");
        
        try {
            // 파일 다운로드 로직 구현
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"generated-code-" + taskId + ".zip\"");
            
            // TODO: 실제 파일 다운로드 로직 구현
            
            logger.info(className, "=== CodeGenerationController.downloadFile END ===");
            
        } catch (Exception e) {
            logger.error(className, "=== CodeGenerationController.downloadFile - Error: " + e.getMessage() + " ===");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    
    /**
     * 코드 생성 요청 클래스
     */
    public static class CodeGenerationRequest {
        private String taskName;
        private String description;
        private TableDefinitionDDTO tableDefinition;
        private List<String> tasks;
        
        // Getters and Setters
        public String getTaskName() { return taskName; }
        public void setTaskName(String taskName) { this.taskName = taskName; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public TableDefinitionDDTO getTableDefinition() { return tableDefinition; }
        public void setTableDefinition(TableDefinitionDDTO tableDefinition) { this.tableDefinition = tableDefinition; }
        
        public List<String> getTasks() { return tasks; }
        public void setTasks(List<String> tasks) { this.tasks = tasks; }
    }
} 