package com.skax.eatool.mbb.pc.entitypc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.entitydc.DCEntityGeneration;
import com.skax.eatool.mbb.dc.dto.EntityGenerationDDTO;
import com.skax.eatool.mbb.dc.dto.TableColumnInfoDDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity 생성 PC (Presentation Controller)
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class PCEntityGeneration {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private DCEntityGeneration dcEntityGeneration;

    /**
     * 사용 가능한 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록이 포함된 응답
     */
    public NewKBData getAvailableTables() {
        String className = this.getClass().getSimpleName();
        String methodName = "getAvailableTables";
        logger.info(className, "[" + methodName + "] 테이블 목록 조회 PC 시작");
        
        NewKBData response = new NewKBData();
        
        try {
            List<String> tables = dcEntityGeneration.getAvailableTables();
            
            response.setValid(true);
            response.setMessage("테이블 목록 조회 성공");
            response.getOutputGenericDto().put("tables", tables);
            
            logger.info(className, "[" + methodName + "] 테이블 목록 조회 PC 완료: " + tables.size() + "개 테이블");
            
        } catch (Exception e) {
            response.setValid(false);
            response.setErrorMessage("테이블 목록 조회 실패: " + e.getMessage());
            
            logger.error(className, "[" + methodName + "] 테이블 목록 조회 PC 중 오류 발생: " + e.getMessage(), e);
        }
        
        return response;
    }

    /**
     * 테이블의 컬럼 정보를 조회합니다.
     *
     * @param tableName 테이블명
     * @return 컬럼 정보가 포함된 응답
     */
    public NewKBData getTableColumns(String tableName) {
        String className = this.getClass().getSimpleName();
        String methodName = "getTableColumns";
        logger.info(className, "[" + methodName + "] 테이블 컬럼 정보 조회 PC 시작: " + tableName);
        
        NewKBData response = new NewKBData();
        
        try {
            List<TableColumnInfoDDTO> columns = dcEntityGeneration.getTableColumns(tableName);
            
            response.setValid(true);
            response.setMessage("테이블 컬럼 정보 조회 성공");
            response.getOutputGenericDto().put("tableName", tableName);
            
            // 컬럼 정보를 Map 형태로 변환하여 JSON 직렬화 문제 해결
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
            
            response.getOutputGenericDto().put("columns", columnMaps);
            
            logger.info(className, "[" + methodName + "] 테이블 컬럼 정보 조회 PC 완료: " + tableName + " (" + columns.size() + "개 컬럼)");
            
        } catch (Exception e) {
            response.setValid(false);
            response.setErrorMessage("테이블 컬럼 정보 조회 실패: " + e.getMessage());
            
            logger.error(className, "[" + methodName + "] 테이블 컬럼 정보 조회 PC 중 오류 발생: " + e.getMessage(), e);
        }
        
        return response;
    }

    /**
     * 테이블 정보를 조회하여 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @param packageName 패키지명
     * @return Entity 생성 결과
     */
    public NewKBData generateEntityFromTable(String tableName, String packageName) {
        String className = this.getClass().getSimpleName();
        String methodName = "generateEntityFromTable";
        logger.info(className, "[" + methodName + "] Entity 생성 PC 시작: " + tableName);
        
        NewKBData response = new NewKBData();
        
        try {
            // 패키지명이 없으면 기본값 설정
            if (packageName == null || packageName.trim().isEmpty()) {
                packageName = "com.skax.eatool.entity";
            }
            
            EntityGenerationDDTO result = dcEntityGeneration.generateEntityFromTable(tableName, packageName);
            
            if (result.isSuccess()) {
                response.setValid(true);
                response.setMessage("Entity 생성 성공");
                response.getOutputGenericDto().put("entityCode", result.getEntityCode());
                response.getOutputGenericDto().put("tableName", result.getTableName());
                response.getOutputGenericDto().put("columns", result.getColumns());
                response.getOutputGenericDto().put("className", result.getClassName());
                response.getOutputGenericDto().put("packageName", result.getPackageName());
                response.getOutputGenericDto().put("filePath", result.getFilePath());
                
                logger.info(className, "[" + methodName + "] Entity 생성 PC 완료: " + tableName);
            } else {
                response.setValid(false);
                response.setErrorMessage("Entity 생성 실패: " + result.getMessage());
                
                logger.error(className, "[" + methodName + "] Entity 생성 PC 실패: " + tableName + " - " + result.getMessage());
            }
            
        } catch (Exception e) {
            response.setValid(false);
            response.setErrorMessage("Entity 생성 중 오류 발생: " + e.getMessage());
            
            logger.error(className, "[" + methodName + "] Entity 생성 PC 중 오류 발생: " + e.getMessage(), e);
        }
        
        return response;
    }

    /**
     * 테이블명과 컬럼 정보를 함께 받아 Entity를 생성합니다.
     *
     * @param request 요청 데이터
     * @return Entity 생성 결과
     */
    public NewKBData generateEntityWithColumns(Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        String methodName = "generateEntityWithColumns";
        String tableName = (String) request.get("tableName");
        String packageName = (String) request.get("packageName");
        Object columnsObj = request.get("columns");

        logger.info(className, "[" + methodName + "] Entity 생성 PC 시작 (컬럼 정보 포함): " + tableName);

        NewKBData response = new NewKBData();

        try {
            // 컬럼 정보를 TableColumnInfoDDTO 리스트로 변환
            List<TableColumnInfoDDTO> columns = convertToTableColumnInfoList(columnsObj);
            
            EntityGenerationDDTO result = dcEntityGeneration.generateEntityWithColumns(tableName, packageName, columns);

            if (result.isSuccess()) {
                response.setValid(true);
                response.setMessage("Entity 생성 성공");
                response.getOutputGenericDto().put("entityCode", result.getEntityCode());
                response.getOutputGenericDto().put("tableName", result.getTableName());
                response.getOutputGenericDto().put("columns", result.getColumns());
                response.getOutputGenericDto().put("className", result.getClassName());
                response.getOutputGenericDto().put("packageName", result.getPackageName());
                response.getOutputGenericDto().put("filePath", result.getFilePath());

                logger.info(className, "[" + methodName + "] Entity 생성 PC 완료 (컬럼 정보 포함): " + tableName);
            } else {
                response.setValid(false);
                response.setErrorMessage("Entity 생성 실패: " + result.getMessage());

                logger.error(className, "[" + methodName + "] Entity 생성 PC 실패 (컬럼 정보 포함): " + tableName + " - " + result.getMessage());
            }

        } catch (Exception e) {
            response.setValid(false);
            response.setErrorMessage("Entity 생성 중 오류 발생: " + e.getMessage());

            logger.error(className, "[" + methodName + "] Entity 생성 PC 중 오류 발생: " + e.getMessage(), e);
        }
        return response;
    }
    
    /**
     * 컬럼 정보를 TableColumnInfoDDTO 리스트로 변환합니다.
     */
    @SuppressWarnings("unchecked")
    private List<TableColumnInfoDDTO> convertToTableColumnInfoList(Object columnsObj) {
        List<TableColumnInfoDDTO> columns = new ArrayList<>();

        if (columnsObj instanceof List) {
            List<Object> columnList = (List<Object>) columnsObj;

            for (Object columnObj : columnList) {
                if (columnObj instanceof Map) {
                    Map<String, Object> columnMap = (Map<String, Object>) columnObj;
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
        }

        return columns;
    }
} 