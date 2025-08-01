package com.skax.eatool.mbb.web.controller.mcpcontroller;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.codegenas.ASMBB75001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * MCP (Model Context Protocol) 컨트롤러
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/mcp")
@CrossOrigin(origins = "*")
public class MCPController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private ASMBB75001 asMbb75001;
    
    /**
     * MCP 서버 상태 확인
     * 
     * @return 서버 상태
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== MCP Health Check ===");
        
        Map<String, Object> response = Map.of(
            "status", "UP",
            "service", "MCP Server",
            "timestamp", System.currentTimeMillis()
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 코드 생성 요청
     * 
     * @param request 요청 데이터
     * @return 생성 결과
     */
    @PostMapping("/generate")
    public ResponseEntity<NewKBData> generateCode(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== MCP Code Generation Request ===");
        
        try {
            // 1. 요청 데이터를 NewKBData로 변환
            NewKBData reqData = new NewKBData();
            reqData.setCommand("GENERATE");
            reqData.setTaskName((String) request.get("taskName"));
            reqData.setDescription((String) request.get("description"));
            reqData.setTableDefinition(request.get("tableDefinition"));
            reqData.setTasks((java.util.List<String>) request.get("tasks"));
            
            // 2. AS 호출
            NewKBData result = asMbb75001.execute(reqData);
            
            logger.info(className, "=== MCP Code Generation Success ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== MCP Code Generation Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("코드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 테이블 정의 검증
     * 
     * @param request 테이블 정의
     * @return 검증 결과
     */
    @PostMapping("/validate")
    public ResponseEntity<NewKBData> validateTable(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== MCP Table Validation Request ===");
        
        try {
            // 1. 요청 데이터를 NewKBData로 변환
            NewKBData reqData = new NewKBData();
            reqData.setCommand("VALIDATE");
            reqData.setTableDefinition(request.get("tableDefinition"));
            
            // 2. AS 호출
            NewKBData result = asMbb75001.execute(reqData);
            
            logger.info(className, "=== MCP Table Validation Success ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== MCP Table Validation Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("테이블 검증 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 작업 상태 조회
     * 
     * @param taskId 작업 ID
     * @return 작업 상태
     */
    @GetMapping("/status/{taskId}")
    public ResponseEntity<NewKBData> getTaskStatus(@PathVariable String taskId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== MCP Task Status Request ===");
        
        try {
            // 1. 요청 데이터를 NewKBData로 변환
            NewKBData reqData = new NewKBData();
            reqData.setCommand("STATUS");
            reqData.setTaskId(taskId);
            
            // 2. AS 호출
            NewKBData result = asMbb75001.execute(reqData);
            
            logger.info(className, "=== MCP Task Status Success ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== MCP Task Status Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("작업 상태 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 파일 다운로드 URL 생성
     * 
     * @param taskId 작업 ID
     * @return 다운로드 URL
     */
    @PostMapping("/download/{taskId}")
    public ResponseEntity<NewKBData> createDownloadUrl(@PathVariable String taskId) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "=== MCP Download URL Request ===");
        
        try {
            // 1. 요청 데이터를 NewKBData로 변환
            NewKBData reqData = new NewKBData();
            reqData.setCommand("DOWNLOAD");
            reqData.setTaskId(taskId);
            
            // 2. AS 호출
            NewKBData result = asMbb75001.execute(reqData);
            
            logger.info(className, "=== MCP Download URL Success ===");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error(className, "=== MCP Download URL Error: " + e.getMessage() + " ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("다운로드 URL 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 