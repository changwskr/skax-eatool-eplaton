package com.skax.eatool.mbc.web.testas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Swagger 테스트 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/swagger/test")
@CrossOrigin(origins = "*")
@Tag(name = "테스트 API", description = "Swagger UI 테스트용 API")
public class SwaggerTestController {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerTestController.class);

    /**
     * API 테스트 웹 페이지
     */
    @GetMapping("")
    public String showTestPage(Model model) {
        logger.info("=== SwaggerTestController.showTestPage START ===");
        
        model.addAttribute("title", "API 테스트");
        model.addAttribute("description", "Swagger UI 테스트용 API들을 테스트할 수 있습니다.");
        model.addAttribute("baseUrl", "/mbc/swagger/test");
        
        logger.info("=== SwaggerTestController.showTestPage END ===");
        return "web/swagger/test";
    }

    /**
     * 기본 Hello API
     */
    @GetMapping("/hello")
    @Operation(summary = "Hello API", description = "기본 Hello API 테스트")
    public ResponseEntity<Map<String, Object>> hello(
            @Parameter(description = "이름", required = false, example = "홍길동") @RequestParam(value = "name", defaultValue = "World") String name) {

        logger.info("=== SwaggerTestController.hello START ===");
        logger.info("=== SwaggerTestController.hello - name: {} ===", name);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("name", name);
        response.put("timestamp", System.currentTimeMillis());

        logger.info("=== SwaggerTestController.hello END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * Echo API
     */
    @PostMapping("/echo")
    @Operation(summary = "Echo API", description = "요청 데이터를 그대로 반환하는 API")
    public ResponseEntity<Map<String, Object>> echo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "요청 예시", value = "{\"message\": \"Hello\", \"data\": \"test\"}"))) @RequestBody Map<String, Object> requestBody) {

        logger.info("=== SwaggerTestController.echo START ===");
        logger.info("=== SwaggerTestController.echo - requestBody: {} ===", requestBody);
        
        Map<String, Object> response = new HashMap<>();
        response.put("echo", requestBody);
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        logger.info("=== SwaggerTestController.echo END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * Update API
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update API", description = "데이터 업데이트 API")
    public ResponseEntity<Map<String, Object>> update(
            @Parameter(description = "업데이트할 ID", required = true, example = "123") @PathVariable String id,
            @RequestBody Map<String, Object> requestBody) {

        logger.info("=== SwaggerTestController.update START ===");
        logger.info("=== SwaggerTestController.update - id: {}, requestBody: {} ===", id, requestBody);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("updatedData", requestBody);
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        logger.info("=== SwaggerTestController.update END ===");
        return ResponseEntity.ok(response);
    }

    /**
     * Delete API
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete API", description = "데이터 삭제 API")
    public ResponseEntity<Map<String, Object>> delete(
            @Parameter(description = "삭제할 ID", required = true, example = "123") @PathVariable String id) {

        logger.info("=== SwaggerTestController.delete START ===");
        logger.info("=== SwaggerTestController.delete - id: {} ===", id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("message", "삭제되었습니다.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        logger.info("=== SwaggerTestController.delete END ===");
        return ResponseEntity.ok(response);
    }
} 