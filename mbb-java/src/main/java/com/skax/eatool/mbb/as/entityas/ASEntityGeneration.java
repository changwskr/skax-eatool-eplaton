package com.skax.eatool.mbb.as.entityas;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.EntityGenerationDDTO;
import com.skax.eatool.mbb.dc.dto.TableColumnInfoDDTO;
import com.skax.eatool.mbb.dc.entitydc.DCEntityGeneration;
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
    private DCEntityGeneration dcEntityGeneration;

    /**
     * 사용 가능한 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록
     */
    public List<String> getAvailableTables() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 목록 조회 AS 시작");
        
        try {
            List<String> tables = dcEntityGeneration.getAvailableTables();
            logger.info(className, "테이블 목록 조회 AS 완료: " + tables.size() + "개 테이블");
            return tables;
            
        } catch (Exception e) {
            logger.error(className, "테이블 목록 조회 AS 중 오류 발생: " + e.getMessage(), e);
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
        logger.info(className, "테이블 컬럼 정보 조회 AS 시작: " + tableName);
        
        try {
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "테이블명이 비어있습니다.");
                return new ArrayList<>();
            }
            
            List<TableColumnInfoDDTO> columns = dcEntityGeneration.getTableColumns(tableName.trim());
            logger.info(className, "테이블 컬럼 정보 조회 AS 완료: " + tableName + " (" + columns.size() + "개 컬럼)");
            return columns;
            
        } catch (Exception e) {
            logger.error(className, "테이블 컬럼 정보 조회 AS 중 오류 발생: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 테이블 정보를 조회하여 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @return Entity 생성 결과
     */
    public EntityGenerationDDTO generateEntityFromTable(String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 AS 시작: " + tableName);
        
        try {
            // 입력값 검증
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "테이블명이 비어있습니다.");
                return createErrorResult("테이블명이 비어있습니다.");
            }
            
            // DC 호출하여 Entity 생성
            EntityGenerationDDTO result = dcEntityGeneration.generateEntityFromTable(tableName.trim());
            
            if (result.isSuccess()) {
                logger.info(className, "Entity 생성 AS 완료: " + tableName);
            } else {
                logger.error(className, "Entity 생성 AS 실패: " + tableName + " - " + result.getMessage());
            }
            
            return result;
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 AS 중 오류 발생: " + e.getMessage(), e);
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
        logger.info(className, "Entity 생성 AS 시작 (컬럼 정보 포함): " + tableName);

        try {
            if (tableName == null || tableName.trim().isEmpty()) {
                logger.error(className, "테이블명이 비어있습니다.");
                return createErrorResult("테이블명이 비어있습니다.");
            }

            if (packageName == null || packageName.trim().isEmpty()) {
                packageName = "com.skax.eatool.entity";
            }

            if (columnsObj == null) {
                logger.error(className, "컬럼 정보가 비어있습니다.");
                return createErrorResult("컬럼 정보가 비어있습니다.");
            }

            List<TableColumnInfoDDTO> columns = convertToTableColumnInfoList(columnsObj);

            if (columns.isEmpty()) {
                logger.error(className, "유효한 컬럼 정보가 없습니다.");
                return createErrorResult("유효한 컬럼 정보가 없습니다.");
            }

            EntityGenerationDDTO result = dcEntityGeneration.generateEntityWithColumns(tableName.trim(), packageName.trim(), columns);

            if (result.isSuccess()) {
                logger.info(className, "Entity 생성 AS 완료 (컬럼 정보 포함): " + tableName);
            } else {
                logger.error(className, "Entity 생성 AS 실패 (컬럼 정보 포함): " + tableName + " - " + result.getMessage());
            }

            return result;

        } catch (Exception e) {
            logger.error(className, "Entity 생성 AS 중 오류 발생: " + e.getMessage(), e);
            return createErrorResult("Entity 생성 중 오류 발생: " + e.getMessage());
        }
    }

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

                    columns.add(column);
                }
            }
        }
        return columns;
    }

    /**
     * 에러 결과를 생성합니다.
     *
     * @param message 에러 메시지
     * @return 에러 결과
     */
    private EntityGenerationDDTO createErrorResult(String message) {
        EntityGenerationDDTO result = new EntityGenerationDDTO();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
} 