package com.skax.eatool.mbc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Swagger 테스트 컨트롤러
 * 
 * 프로그램명: SwaggerTestController.java
 * 설명: Swagger UI 테스트를 위한 간단한 API 컨트롤러
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - Swagger UI 테스트용 API
 * - 다양한 HTTP 메서드 테스트
 * - API 문서화 예시
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
@Tag(name = "테스트 API", description = "Swagger UI 테스트용 API")
public class SwaggerTestController {

    /**
     * 간단한 GET 테스트
     * 
     * @param name 이름
     * @return 응답 데이터
     */
    @GetMapping("/hello")
    @Operation(summary = "Hello API", description = "간단한 인사말을 반환하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "성공 응답", value = "{\"message\": \"Hello, World!\", \"name\": \"홍길동\"}")))
    })
    public ResponseEntity<Map<String, Object>> hello(
            @Parameter(description = "이름", required = false, example = "홍길동") @RequestParam(value = "name", defaultValue = "World") String name) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("name", name);
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

    /**
     * POST 테스트
     * 
     * @param requestBody 요청 본문
     * @return 응답 데이터
     */
    @PostMapping("/echo")
    @Operation(summary = "Echo API", description = "요청 데이터를 그대로 반환하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Map<String, Object>> echo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "요청 예시", value = "{\"message\": \"Hello\", \"data\": \"test\"}"))) @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new HashMap<>();
        response.put("echo", requestBody);
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT 테스트
     * 
     * @param id          ID
     * @param requestBody 요청 본문
     * @return 응답 데이터
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Update API", description = "데이터 업데이트 테스트 API")
    public ResponseEntity<Map<String, Object>> update(
            @Parameter(description = "업데이트할 ID", required = true, example = "123") @PathVariable String id,
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("updatedData", requestBody);
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE 테스트
     * 
     * @param id ID
     * @return 응답 데이터
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete API", description = "데이터 삭제 테스트 API")
    public ResponseEntity<Map<String, Object>> delete(
            @Parameter(description = "삭제할 ID", required = true, example = "123") @PathVariable String id) {

        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("message", "삭제되었습니다.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        return ResponseEntity.ok(response);
    }
}