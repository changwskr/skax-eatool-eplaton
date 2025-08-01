package com.skax.eatool.mbb.web.controller.entitycontroller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.pc.entitypc.PCEntityGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Entity 생성 Web Controller
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/entity")
public class EntityGenerationController {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private PCEntityGeneration pcEntityGeneration;

    /**
     * 테이블명으로 Entity를 생성합니다.
     *
     * @param request 요청 데이터 (테이블명 포함)
     * @return Entity 생성 결과
     */
    @PostMapping("/generate-from-table")
    public ResponseEntity<NewKBData> generateEntityFromTable(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 API 호출");
        
        try {
            String tableName = (String) request.get("tableName");
            if (tableName == null || tableName.trim().isEmpty()) {
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("테이블명이 비어있습니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            NewKBData result = pcEntityGeneration.generateEntityFromTable(tableName.trim());
            
            if (result.getValid()) {
                logger.info(className, "Entity 생성 API 완료");
                return ResponseEntity.ok(result);
            } else {
                logger.error(className, "Entity 생성 API 실패: " + result.getErrorMessage());
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Entity 생성 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 테이블명과 컬럼 정보를 함께 받아 Entity를 생성합니다.
     *
     * @param request 요청 데이터 (테이블명, 패키지명, 컬럼 정보 포함)
     * @return Entity 생성 결과
     */
    @PostMapping("/generate-with-columns")
    public ResponseEntity<NewKBData> generateEntityWithColumns(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 API 호출 (컬럼 정보 포함)");
        
        try {
            NewKBData result = pcEntityGeneration.generateEntityWithColumns(request);
            
            if (result.getValid()) {
                logger.info(className, "Entity 생성 API 완료 (컬럼 정보 포함)");
                return ResponseEntity.ok(result);
            } else {
                logger.error(className, "Entity 생성 API 실패: " + result.getErrorMessage());
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Entity 생성 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 테이블명으로 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @return Entity 생성 결과
     */
    @GetMapping("/generate/{tableName}")
    public ResponseEntity<NewKBData> generateEntityFromTableName(@PathVariable String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 API 호출 (테이블명: " + tableName + ")");
        
        try {
            NewKBData result = pcEntityGeneration.generateEntityFromTable(tableName);
            
            if (result.getValid()) {
                logger.info(className, "Entity 생성 API 완료: " + tableName);
                return ResponseEntity.ok(result);
            } else {
                logger.error(className, "Entity 생성 API 실패: " + tableName + " - " + result.getErrorMessage());
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Entity 생성 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 사용 가능한 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록
     */
    @GetMapping("/tables")
    public ResponseEntity<NewKBData> getAvailableTables() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 목록 조회 API 호출");
        
        try {
            NewKBData result = pcEntityGeneration.getAvailableTables();
            
            if (result.getValid()) {
                logger.info(className, "테이블 목록 조회 API 완료");
                return ResponseEntity.ok(result);
            } else {
                logger.error(className, "테이블 목록 조회 API 실패: " + result.getErrorMessage());
                return ResponseEntity.badRequest().body(result);
            }
            
        } catch (Exception e) {
            logger.error(className, "테이블 목록 조회 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("테이블 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 테이블의 상세 정보를 조회합니다.
     *
     * @param tableName 테이블명
     * @return 테이블 상세 정보
     */
    @GetMapping("/table-info/{tableName}")
    public ResponseEntity<Map<String, Object>> getTableInfo(@PathVariable String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 정보 조회 API 호출: " + tableName);
        
        try {
            NewKBData result = pcEntityGeneration.getTableColumns(tableName);
            
            if (result.getValid()) {
                logger.info(className, "테이블 정보 조회 API 완료: " + tableName);
                
                // 데이터 구조 확인을 위한 로그 추가
                logger.info(className, "응답 데이터 구조 확인:");
                logger.info(className, "- valid: " + result.getValid());
                logger.info(className, "- message: " + result.getMessage());
                logger.info(className, "- outputGenericDto: " + result.getOutputGenericDto());
                
                Object columns = result.getOutputGenericDto().get("columns");
                logger.info(className, "- columns: " + columns);
                if (columns != null) {
                    logger.info(className, "- columns type: " + columns.getClass().getName());
                    if (columns instanceof java.util.List) {
                        logger.info(className, "- columns size: " + ((java.util.List<?>) columns).size());
                    }
                }
                
                // 간단한 JSON 응답 생성
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("message", result.getMessage());
                response.put("tableName", result.getOutputGenericDto().get("tableName"));
                response.put("columns", result.getOutputGenericDto().get("columns"));
                
                return ResponseEntity.ok(response);
            } else {
                logger.error(className, "테이블 정보 조회 API 실패: " + result.getErrorMessage());
                
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("valid", false);
                errorResponse.put("errorMessage", result.getErrorMessage());
                
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
        } catch (Exception e) {
            logger.error(className, "테이블 정보 조회 API 중 오류 발생: " + e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("valid", false);
            errorResponse.put("errorMessage", "테이블 정보 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 