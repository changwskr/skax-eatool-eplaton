package com.skax.eatool.mbb.as.entityas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.EntityGenerationDDTO;
import com.skax.eatool.mbb.dc.dto.TableColumnInfoDDTO;
import com.skax.eatool.mbb.pc.entitypc.PCEntityGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Entity 생성 AS (Application Service)
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class ASEntityGeneration {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private PCEntityGeneration pcEntityGeneration;

    /**
     * 사용 가능한 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록
     */
    public List<String> getAvailableTables() {
        String className = this.getClass().getSimpleName();
        String methodName = "getAvailableTables";
        logger.info(className, "[" + methodName + "] 테이블 목록 조회 AS 시작");
        
        try {
            NewKBData result = pcEntityGeneration.getAvailableTables();
            
            if (result.getValid()) {
                @SuppressWarnings("unchecked")
                List<String> tables = (List<String>) result.getOutputGenericDto().get("tables");
                logger.info(className, "[" + methodName + "] 테이블 목록 조회 AS 완료: " + tables.size() + "개 테이블");
                return tables;
            } else {
                logger.error(className, "[" + methodName + "] 테이블 목록 조회 AS 실패: " + result.getErrorMessage());
                return new ArrayList<>();
            }
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] 테이블 목록 조회 AS 중 오류 발생: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 테이블의 상세 정보를 조회합니다.
     *
     * @param tableName 테이블명
     * @return 테이블 컬럼 정보 목록
     */
    public List<TableColumnInfoDDTO> getTableColumns(String tableName) {
        String className = this.getClass().getSimpleName();
        String methodName = "getTableColumns";
        logger.info(className, "[" + methodName + "] 테이블 컬럼 정보 조회 AS 시작: " + tableName);
        
        try {
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "[" + methodName + "] 테이블명이 비어있습니다.");
                return new ArrayList<>();
            }
            
            NewKBData result = pcEntityGeneration.getTableColumns(tableName.trim());
            
            if (result.getValid()) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> columnMaps = (List<Map<String, Object>>) result.getOutputGenericDto().get("columns");
                
                // Map을 TableColumnInfoDDTO로 변환
                List<TableColumnInfoDDTO> columns = convertMapToTableColumnInfoList(columnMaps);
                
                logger.info(className, "[" + methodName + "] 테이블 컬럼 정보 조회 AS 완료: " + tableName + " (" + columns.size() + "개 컬럼)");
                return columns;
            } else {
                logger.error(className, "[" + methodName + "] 테이블 컬럼 정보 조회 AS 실패: " + result.getErrorMessage());
                return new ArrayList<>();
            }
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] 테이블 컬럼 정보 조회 AS 중 오류 발생: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 테이블 정보를 조회하여 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @param packageName 패키지명
     * @return Entity 생성 결과
     */
    public EntityGenerationDDTO generateEntityFromTable(String tableName, String packageName) {
        String className = this.getClass().getSimpleName();
        String methodName = "generateEntityFromTable";
        logger.info(className, "[" + methodName + "] Entity 생성 AS 시작: " + tableName);
        
        try {
            // 입력값 검증
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "[" + methodName + "] 테이블명이 비어있습니다.");
                return createErrorResult("테이블명이 비어있습니다.");
            }
            
            // 패키지명이 없으면 기본값 설정
            if (packageName == null || packageName.trim().isEmpty()) {
                packageName = "com.skax.eatool.entity";
            }
            
            // PC 호출하여 Entity 생성
            NewKBData result = pcEntityGeneration.generateEntityFromTable(tableName.trim(), packageName.trim());
            
            if (result.getValid()) {
                // NewKBData를 EntityGenerationDDTO로 변환
                EntityGenerationDDTO entityResult = convertNewKBDataToEntityGenerationDDTO(result);
                logger.info(className, "[" + methodName + "] Entity 생성 AS 완료: " + tableName);
                return entityResult;
            } else {
                logger.error(className, "[" + methodName + "] Entity 생성 AS 실패: " + tableName + " - " + result.getErrorMessage());
                return createErrorResult(result.getErrorMessage());
            }
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] Entity 생성 AS 중 오류 발생: " + e.getMessage(), e);
            return createErrorResult("Entity 생성 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 테이블명과 컬럼 정보를 함께 받아 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @param packageName 패키지명
     * @param columnsObj 컬럼 정보 객체
     * @return Entity 생성 결과
     */
    public EntityGenerationDDTO generateEntityWithColumns(String tableName, String packageName, Object columnsObj) {
        String className = this.getClass().getSimpleName();
        String methodName = "generateEntityWithColumns";
        logger.info(className, "[" + methodName + "] Entity 생성 AS 시작 (컬럼 정보 포함): " + tableName);

        try {
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "[" + methodName + "] 테이블명이 비어있습니다.");
                return createErrorResult("테이블명이 비어있습니다.");
            }

            if (packageName == null || packageName.trim().isEmpty()) {
                packageName = "com.skax.eatool.entity";
            }

            if (columnsObj == null) {
                logger.error(className, "[" + methodName + "] 컬럼 정보가 비어있습니다.");
                return createErrorResult("컬럼 정보가 비어있습니다.");
            }

            // PC 호출하여 Entity 생성
            Map<String, Object> request = new java.util.HashMap<>();
            request.put("tableName", tableName.trim());
            request.put("packageName", packageName.trim());
            request.put("columns", columnsObj);
            
            NewKBData result = pcEntityGeneration.generateEntityWithColumns(request);

            if (result.getValid()) {
                // NewKBData를 EntityGenerationDDTO로 변환
                EntityGenerationDDTO entityResult = convertNewKBDataToEntityGenerationDDTO(result);
                logger.info(className, "[" + methodName + "] Entity 생성 AS 완료 (컬럼 정보 포함): " + tableName);
                return entityResult;
            } else {
                logger.error(className, "[" + methodName + "] Entity 생성 AS 실패 (컬럼 정보 포함): " + tableName + " - " + result.getErrorMessage());
                return createErrorResult(result.getErrorMessage());
            }

        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] Entity 생성 AS 중 오류 발생: " + e.getMessage(), e);
            return createErrorResult("Entity 생성 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * Map을 TableColumnInfoDDTO 리스트로 변환합니다.
     */
    private List<TableColumnInfoDDTO> convertMapToTableColumnInfoList(List<Map<String, Object>> columnMaps) {
        List<TableColumnInfoDDTO> columns = new ArrayList<>();

        if (columnMaps != null) {
            for (Map<String, Object> columnMap : columnMaps) {
                TableColumnInfoDDTO column = new TableColumnInfoDDTO();

                column.setColumnName((String) columnMap.get("columnName"));
                column.setDataType((String) columnMap.get("dataType"));

                Object columnSize = columnMap.get("columnSize");
                if (columnSize instanceof Number) {
                    column.setColumnSize(((Number) columnSize).intValue());
                }

                Object nullable = columnMap.get("nullable");
                if (nullable instanceof Boolean) {
                    column.setNullable((Boolean) nullable);
                }

                column.setDefaultValue((String) columnMap.get("defaultValue"));
                column.setRemarks((String) columnMap.get("remarks"));

                Object primaryKey = columnMap.get("primaryKey");
                if (primaryKey instanceof Boolean) {
                    column.setPrimaryKey((Boolean) primaryKey);
                }

                Object ordinalPosition = columnMap.get("ordinalPosition");
                if (ordinalPosition instanceof Number) {
                    column.setOrdinalPosition(((Number) ordinalPosition).intValue());
                }

                Object decimalDigits = columnMap.get("decimalDigits");
                if (decimalDigits instanceof Number) {
                    column.setDecimalDigits(((Number) decimalDigits).intValue());
                }

                Object autoIncrement = columnMap.get("autoIncrement");
                if (autoIncrement instanceof Boolean) {
                    column.setAutoIncrement((Boolean) autoIncrement);
                }

                columns.add(column);
            }
        }

        return columns;
    }

    /**
     * NewKBData를 EntityGenerationDDTO로 변환합니다.
     */
    private EntityGenerationDDTO convertNewKBDataToEntityGenerationDDTO(NewKBData result) {
        EntityGenerationDDTO entityResult = new EntityGenerationDDTO();
        entityResult.setSuccess(true);
        entityResult.setMessage(result.getMessage());
        
        // 출력 데이터 설정
        entityResult.setEntityCode((String) result.getOutputGenericDto().get("entityCode"));
        entityResult.setTableName((String) result.getOutputGenericDto().get("tableName"));
        entityResult.setColumns((List<TableColumnInfoDDTO>) result.getOutputGenericDto().get("columns"));
        entityResult.setClassName((String) result.getOutputGenericDto().get("className"));
        entityResult.setPackageName((String) result.getOutputGenericDto().get("packageName"));
        entityResult.setFilePath((String) result.getOutputGenericDto().get("filePath"));
        
        return entityResult;
    }

    /**
     * 오류 결과를 생성합니다.
     */
    private EntityGenerationDDTO createErrorResult(String message) {
        EntityGenerationDDTO result = new EntityGenerationDDTO();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
} 