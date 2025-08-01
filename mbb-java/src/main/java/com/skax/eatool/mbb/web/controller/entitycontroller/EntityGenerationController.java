package com.skax.eatool.mbb.web.controller.entitycontroller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.as.entityas.ASEntityGeneration;
import com.skax.eatool.mbb.dc.dto.EntityGenerationDDTO;
import com.skax.eatool.mbb.dc.dto.TableColumnInfoDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
    private ASEntityGeneration asEntityGeneration;

    /**
     * 테이블명으로 Entity를 생성합니다.
     *
     * @param request 요청 데이터 (테이블명, 패키지명 포함)
     * @return Entity 생성 결과
     */
    @PostMapping("/generate-from-table")
    public ResponseEntity<NewKBData> generateEntityFromTable(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 API 호출");
        
        try {
            String tableName = (String) request.get("tableName");
            String packageName = (String) request.get("packageName");
            
            if (tableName == null || tableName.trim().isEmpty()) {
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("테이블명이 비어있습니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 패키지명이 없으면 기본값 설정
            if (packageName == null || packageName.trim().isEmpty()) {
                packageName = "com.skax.eatool.entity";
            }
            
            // AS 호출하여 Entity 생성
            EntityGenerationDDTO result = asEntityGeneration.generateEntityFromTable(tableName.trim(), packageName.trim());
            
            // EntityGenerationDDTO를 NewKBData로 변환
            NewKBData response = new NewKBData();
            if (result.isSuccess()) {
                response.setValid(true);
                response.setMessage("Entity 생성 성공");
                response.getOutputGenericDto().put("entityCode", result.getEntityCode());
                response.getOutputGenericDto().put("tableName", result.getTableName());
                response.getOutputGenericDto().put("columns", result.getColumns());
                response.getOutputGenericDto().put("className", result.getClassName());
                response.getOutputGenericDto().put("packageName", result.getPackageName());
                response.getOutputGenericDto().put("filePath", result.getFilePath());
                
                logger.info(className, "Entity 생성 API 완료");
                return ResponseEntity.ok(response);
            } else {
                response.setValid(false);
                response.setErrorMessage(result.getMessage());
                
                logger.error(className, "Entity 생성 API 실패: " + result.getMessage());
                return ResponseEntity.badRequest().body(response);
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
            String tableName = (String) request.get("tableName");
            String packageName = (String) request.get("packageName");
            Object columnsObj = request.get("columns");
            
            if (tableName == null || tableName.trim().isEmpty()) {
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("테이블명이 비어있습니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // AS 호출하여 Entity 생성
            EntityGenerationDDTO result = asEntityGeneration.generateEntityWithColumns(tableName, packageName, columnsObj);
            
            // EntityGenerationDDTO를 NewKBData로 변환
            NewKBData response = new NewKBData();
            if (result.isSuccess()) {
                response.setValid(true);
                response.setMessage("Entity 생성 성공");
                response.getOutputGenericDto().put("entityCode", result.getEntityCode());
                response.getOutputGenericDto().put("tableName", result.getTableName());
                response.getOutputGenericDto().put("columns", result.getColumns());
                response.getOutputGenericDto().put("className", result.getClassName());
                response.getOutputGenericDto().put("packageName", result.getPackageName());
                response.getOutputGenericDto().put("filePath", result.getFilePath());
                
                logger.info(className, "Entity 생성 API 완료 (컬럼 정보 포함)");
                return ResponseEntity.ok(response);
            } else {
                response.setValid(false);
                response.setErrorMessage(result.getMessage());
                
                logger.error(className, "Entity 생성 API 실패: " + result.getMessage());
                return ResponseEntity.badRequest().body(response);
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
     * 테이블명으로 Entity를 생성합니다. (GET 방식)
     *
     * @param tableName 테이블명
     * @return Entity 생성 결과
     */
    @GetMapping("/generate/{tableName}")
    public ResponseEntity<NewKBData> generateEntityFromTableName(@PathVariable String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 API 호출 (GET): " + tableName);
        
        try {
            // 기본 패키지명 설정
            String packageName = "com.skax.eatool.entity";
            
            // AS 호출하여 Entity 생성
            EntityGenerationDDTO result = asEntityGeneration.generateEntityFromTable(tableName, packageName);
            
            // EntityGenerationDDTO를 NewKBData로 변환
            NewKBData response = new NewKBData();
            if (result.isSuccess()) {
                response.setValid(true);
                response.setMessage("Entity 생성 성공");
                response.getOutputGenericDto().put("entityCode", result.getEntityCode());
                response.getOutputGenericDto().put("tableName", result.getTableName());
                response.getOutputGenericDto().put("columns", result.getColumns());
                response.getOutputGenericDto().put("className", result.getClassName());
                response.getOutputGenericDto().put("packageName", result.getPackageName());
                response.getOutputGenericDto().put("filePath", result.getFilePath());
                
                logger.info(className, "Entity 생성 API 완료 (GET): " + tableName);
                return ResponseEntity.ok(response);
            } else {
                response.setValid(false);
                response.setErrorMessage(result.getMessage());
                
                logger.error(className, "Entity 생성 API 실패 (GET): " + result.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 API 중 오류 발생 (GET): " + e.getMessage(), e);
            
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
            // AS 호출하여 테이블 목록 조회
            List<String> tables = asEntityGeneration.getAvailableTables();
            
            // List<String>을 NewKBData로 변환
            NewKBData response = new NewKBData();
            response.setValid(true);
            response.setMessage("테이블 목록 조회 성공");
            response.getOutputGenericDto().put("tables", tables);
            
            logger.info(className, "테이블 목록 조회 API 완료: " + tables.size() + "개 테이블");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "테이블 목록 조회 API 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("테이블 목록 조회 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 테이블 정보를 조회합니다.
     *
     * @param tableName 테이블명
     * @return 테이블 정보
     */
    @GetMapping("/table-info/{tableName}")
    public ResponseEntity<Map<String, Object>> getTableInfo(@PathVariable String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 정보 조회 API 호출: " + tableName);
        
        try {
            // AS 호출하여 테이블 컬럼 정보 조회
            List<TableColumnInfoDDTO> columns = asEntityGeneration.getTableColumns(tableName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "테이블 정보 조회 성공");
            response.put("tableName", tableName);
            
            // TableColumnInfoDDTO를 Map으로 변환
            List<Map<String, Object>> columnMaps = new ArrayList<>();
            for (TableColumnInfoDDTO column : columns) {
                Map<String, Object> columnMap = new HashMap<>();
                columnMap.put("columnName", column.getColumnName());
                columnMap.put("dataType", column.getDataType());
                columnMap.put("columnSize", column.getColumnSize());
                columnMap.put("nullable", column.isNullable());
                columnMap.put("defaultValue", column.getDefaultValue());
                columnMap.put("remarks", column.getRemarks());
                columnMap.put("primaryKey", column.isPrimaryKey());
                columnMap.put("ordinalPosition", column.getOrdinalPosition());
                columnMap.put("decimalDigits", column.getDecimalDigits());
                columnMap.put("autoIncrement", column.isAutoIncrement());
                columnMaps.add(columnMap);
            }
            response.put("columns", columnMaps);
            
            logger.info(className, "테이블 정보 조회 API 완료: " + tableName + " (" + columns.size() + "개 컬럼)");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "테이블 정보 조회 API 중 오류 발생: " + e.getMessage(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "테이블 정보 조회 중 오류 발생: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 