package com.skax.eatool.mbb.dc.ddlgeneratordc;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DDL 생성 Data Controller
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class DCDdlGenerator {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * 테이블 정의를 기반으로 완전한 DDL을 생성합니다.
     *
     * @param tableName 테이블명
     * @param tableDefinition 테이블 정의
     * @param databaseType 데이터베이스 타입
     * @param schemaName 스키마명
     * @return 생성된 DDL
     */
    public String generateDDL(String tableName, Map<String, Object> tableDefinition, 
                             String databaseType, String schemaName) {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== DCDdlGenerator.generateDDL START ===");
            logger.info(className, "DDL 생성 DC 시작: " + tableName);
            logger.info(className, "데이터베이스 타입: " + databaseType);
            logger.info(className, "스키마명: " + schemaName);
            
            // DDL 생성 로직
            StringBuilder ddl = new StringBuilder();
            
            // 헤더 추가
            ddl.append("-- ===========================================\n");
            ddl.append("-- ").append(tableName).append(" 테이블 완전한 DDL 생성\n");
            ddl.append("-- 생성일시: ").append(java.time.LocalDateTime.now()).append("\n");
            ddl.append("-- 데이터베이스 타입: ").append(databaseType).append("\n");
            ddl.append("-- 스키마명: ").append(schemaName).append("\n");
            ddl.append("-- ===========================================\n\n");
            
            // 1. 시퀀스 및 자동 증가키 정의
            ddl.append(generateSequenceDDL(tableName, databaseType, schemaName));
            
            // 2. 테이블 구조 생성
            ddl.append(generateTableStructureDDL(tableName, tableDefinition, databaseType, schemaName));
            
            // 3. 인덱스 정의
            ddl.append(generateIndexDefinitionDDL(tableName, tableDefinition, databaseType, schemaName));
            
            // 4. 외래키 제약조건
            ddl.append(generateForeignKeyDDL(tableName, tableDefinition, databaseType, schemaName));
            
            // 5. 트리거 정의
            ddl.append(generateTriggerDefinitionDDL(tableName, tableDefinition, databaseType, schemaName));
            
            // 6. 뷰 정의
            ddl.append(generateViewDefinitionDDL(tableName, tableDefinition, databaseType, schemaName));
            
            // 7. 샘플 데이터 삽입
            ddl.append(generateSampleDataDDL(tableName, tableDefinition, databaseType, schemaName));
            
            String result = ddl.toString();
            
            logger.info(className, "DDL 생성 DC 완료: " + tableName);
            logger.info(className, "=== DCDdlGenerator.generateDDL END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error(className, "DDL 생성 DC 실패: " + tableName + " - " + e.getMessage());
            logger.error(className, "=== DCDdlGenerator.generateDDL ERROR ===");
            e.printStackTrace();
            throw new RuntimeException("DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 실제 데이터베이스에서 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록
     */
    public Map<String, Object> getTableDefinitionList() {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== DCDdlGenerator.getTableDefinitionList START ===");
            
            List<Map<String, Object>> tableDefinitions = new ArrayList<>();
            
            // 실제 데이터베이스에서 테이블 목록 조회
            try (java.sql.Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                String[] types = {"TABLE"};
                
                // 테이블 목록 조회
                try (ResultSet tables = metaData.getTables(null, null, "%", types)) {
                    while (tables.next()) {
                        String tableName = tables.getString("TABLE_NAME");
                        String tableType = tables.getString("TABLE_TYPE");
                        String tableComment = tables.getString("REMARKS");
                        
                        // 시스템 테이블 제외
                        if (isSystemTable(tableName)) {
                            continue;
                        }
                        
                        Map<String, Object> tableInfo = new HashMap<>();
                        tableInfo.put("tableName", tableName);
                        tableInfo.put("tableType", tableType);
                        tableInfo.put("tableComment", tableComment != null ? tableComment : "");
                        tableInfo.put("databaseType", getDatabaseType(metaData));
                        tableInfo.put("schemaName", getSchemaName(metaData));
                        
                        // 컬럼 정보 조회
                        List<Map<String, Object>> columns = getTableColumns(metaData, tableName);
                        tableInfo.put("columns", columns);
                        
                        // 인덱스 정보 조회
                        List<Map<String, Object>> indexes = getTableIndexes(metaData, tableName);
                        tableInfo.put("indexes", indexes);
                        
                        // 외래키 정보 조회
                        List<Map<String, Object>> foreignKeys = getTableForeignKeys(metaData, tableName);
                        tableInfo.put("foreignKeys", foreignKeys);
                        
                        tableDefinitions.add(tableInfo);
                    }
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", tableDefinitions);
            result.put("message", "실제 데이터베이스에서 테이블 목록을 성공적으로 조회했습니다. (" + tableDefinitions.size() + "개)");
            
            logger.info(className, "테이블 정의 목록 조회 DC 완료: " + tableDefinitions.size() + "개");
            logger.info(className, "=== DCDdlGenerator.getTableDefinitionList END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error(className, "테이블 정의 목록 조회 DC 실패: " + e.getMessage());
            logger.error(className, "=== DCDdlGenerator.getTableDefinitionList ERROR ===");
            e.printStackTrace();
            
            // 오류 발생 시 빈 목록 반환
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("data", new ArrayList<>());
            result.put("message", "테이블 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            
            return result;
        }
    }
    
    /**
     * 시스템 테이블인지 확인합니다.
     */
    private boolean isSystemTable(String tableName) {
        String upperTableName = tableName.toUpperCase();
        return upperTableName.startsWith("SYS") ||
               upperTableName.startsWith("SYSTEM") ||
               upperTableName.startsWith("INFORMATION_SCHEMA") ||
               upperTableName.startsWith("PG_") ||
               upperTableName.startsWith("MYSQL") ||
               upperTableName.startsWith("PERFORMANCE_SCHEMA") ||
               upperTableName.startsWith("H2") ||
               upperTableName.startsWith("DUAL") ||
               upperTableName.startsWith("AUDIT_") ||
               upperTableName.contains("TEMP") ||
               upperTableName.contains("TMP");
    }
    
    /**
     * 데이터베이스 타입을 확인합니다.
     */
    private String getDatabaseType(DatabaseMetaData metaData) throws SQLException {
        String productName = metaData.getDatabaseProductName().toUpperCase();
        if (productName.contains("MYSQL")) {
            return "MYSQL";
        } else if (productName.contains("POSTGRESQL")) {
            return "POSTGRESQL";
        } else if (productName.contains("ORACLE")) {
            return "ORACLE";
        } else if (productName.contains("H2")) {
            return "H2";
        } else {
            return "UNKNOWN";
        }
    }
    
    /**
     * 스키마명을 확인합니다.
     */
    private String getSchemaName(DatabaseMetaData metaData) throws SQLException {
        try (java.sql.Connection connection = metaData.getConnection()) {
            return connection.getSchema();
        } catch (Exception e) {
            return "public";
        }
    }
    
    /**
     * 테이블의 컬럼 정보를 조회합니다.
     */
    private List<Map<String, Object>> getTableColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Map<String, Object>> columns = new ArrayList<>();
        
        try (ResultSet columnRs = metaData.getColumns(null, null, tableName, "%")) {
            while (columnRs.next()) {
                Map<String, Object> column = new HashMap<>();
                column.put("name", columnRs.getString("COLUMN_NAME"));
                column.put("type", columnRs.getString("TYPE_NAME"));
                column.put("length", columnRs.getInt("COLUMN_SIZE"));
                column.put("nullable", columnRs.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                column.put("defaultValue", columnRs.getString("COLUMN_DEF"));
                column.put("comment", columnRs.getString("REMARKS"));
                
                // Primary Key 여부 확인
                column.put("pk", isPrimaryKey(metaData, tableName, columnRs.getString("COLUMN_NAME")));
                
                // Auto Increment 여부 확인
                column.put("autoIncrement", isAutoIncrement(metaData, tableName, columnRs.getString("COLUMN_NAME")));
                
                // Index 여부 확인
                column.put("indexed", isIndexed(metaData, tableName, columnRs.getString("COLUMN_NAME")));
                
                // Unique 여부 확인
                column.put("unique", isUnique(metaData, tableName, columnRs.getString("COLUMN_NAME")));
                
                columns.add(column);
            }
        }
        
        return columns;
    }
    
    /**
     * Primary Key 여부를 확인합니다.
     */
    private boolean isPrimaryKey(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        try (ResultSet pkRs = metaData.getPrimaryKeys(null, null, tableName)) {
            while (pkRs.next()) {
                if (columnName.equals(pkRs.getString("COLUMN_NAME"))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Auto Increment 여부를 확인합니다.
     */
    private boolean isAutoIncrement(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        try (ResultSet columnRs = metaData.getColumns(null, null, tableName, columnName)) {
            if (columnRs.next()) {
                String isAutoIncrement = columnRs.getString("IS_AUTOINCREMENT");
                return "YES".equals(isAutoIncrement);
            }
        }
        return false;
    }
    
    /**
     * Index 여부를 확인합니다.
     */
    private boolean isIndexed(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        try (ResultSet indexRs = metaData.getIndexInfo(null, null, tableName, false, false)) {
            while (indexRs.next()) {
                if (columnName.equals(indexRs.getString("COLUMN_NAME"))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Unique 여부를 확인합니다.
     */
    private boolean isUnique(DatabaseMetaData metaData, String tableName, String columnName) throws SQLException {
        try (ResultSet indexRs = metaData.getIndexInfo(null, null, tableName, false, true)) {
            while (indexRs.next()) {
                if (columnName.equals(indexRs.getString("COLUMN_NAME"))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 테이블의 인덱스 정보를 조회합니다.
     */
    private List<Map<String, Object>> getTableIndexes(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Map<String, Object>> indexes = new ArrayList<>();
        
        try (ResultSet indexRs = metaData.getIndexInfo(null, null, tableName, false, false)) {
            while (indexRs.next()) {
                Map<String, Object> index = new HashMap<>();
                index.put("name", indexRs.getString("INDEX_NAME"));
                index.put("columnName", indexRs.getString("COLUMN_NAME"));
                index.put("unique", !indexRs.getBoolean("NON_UNIQUE"));
                index.put("type", indexRs.getShort("TYPE"));
                indexes.add(index);
            }
        }
        
        return indexes;
    }
    
    /**
     * 테이블의 외래키 정보를 조회합니다.
     */
    private List<Map<String, Object>> getTableForeignKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<Map<String, Object>> foreignKeys = new ArrayList<>();
        
        try (ResultSet fkRs = metaData.getImportedKeys(null, null, tableName)) {
            while (fkRs.next()) {
                Map<String, Object> fk = new HashMap<>();
                fk.put("name", fkRs.getString("FK_NAME"));
                fk.put("columnName", fkRs.getString("FKCOLUMN_NAME"));
                fk.put("referencedTable", fkRs.getString("PKTABLE_NAME"));
                fk.put("referencedColumn", fkRs.getString("PKCOLUMN_NAME"));
                foreignKeys.add(fk);
            }
        }
        
        return foreignKeys;
    }
    
    /**
     * 1. 시퀀스 및 자동 증가키 정의 DDL을 생성합니다.
     */
    private String generateSequenceDDL(String tableName, String databaseType, String schemaName) {
        StringBuilder sequenceDDL = new StringBuilder();
        
        sequenceDDL.append("-- ===========================================\n");
        sequenceDDL.append("-- 1. 시퀀스 및 자동 증가키 정의\n");
        sequenceDDL.append("-- ===========================================\n");
        
        if ("ORACLE".equals(databaseType)) {
            sequenceDDL.append("-- Oracle 시퀀스 생성\n");
            sequenceDDL.append("CREATE SEQUENCE ").append(tableName).append("_SEQ\n");
            sequenceDDL.append("    START WITH 1\n");
            sequenceDDL.append("    INCREMENT BY 1\n");
            sequenceDDL.append("    NOCACHE\n");
            sequenceDDL.append("    NOCYCLE;\n\n");
            
            sequenceDDL.append("-- Oracle 시퀀스 권한 부여\n");
            sequenceDDL.append("GRANT SELECT ON ").append(tableName).append("_SEQ TO PUBLIC;\n\n");
            
        } else if ("POSTGRESQL".equals(databaseType)) {
            sequenceDDL.append("-- PostgreSQL 시퀀스 생성\n");
            sequenceDDL.append("CREATE SEQUENCE ").append(schemaName).append(".").append(tableName).append("_seq\n");
            sequenceDDL.append("    START WITH 1\n");
            sequenceDDL.append("    INCREMENT BY 1\n");
            sequenceDDL.append("    NO CYCLE;\n\n");
            
            sequenceDDL.append("-- PostgreSQL 시퀀스 권한 부여\n");
            sequenceDDL.append("GRANT USAGE ON SEQUENCE ").append(schemaName).append(".").append(tableName).append("_seq TO PUBLIC;\n\n");
            
        } else if ("MYSQL".equals(databaseType)) {
            sequenceDDL.append("-- MySQL은 AUTO_INCREMENT를 사용하므로 별도 시퀀스 불필요\n");
            sequenceDDL.append("-- AUTO_INCREMENT는 테이블 생성 시 컬럼에 직접 지정\n\n");
            
        } else if ("H2".equals(databaseType)) {
            sequenceDDL.append("-- H2 시퀀스 생성\n");
            sequenceDDL.append("CREATE SEQUENCE ").append(tableName).append("_SEQ\n");
            sequenceDDL.append("    START WITH 1\n");
            sequenceDDL.append("    INCREMENT BY 1;\n\n");
        }
        
        return sequenceDDL.toString();
    }
    
    /**
     * 2. 테이블 구조 생성 DDL을 생성합니다.
     */
    private String generateTableStructureDDL(String tableName, Map<String, Object> tableDefinition, 
                                           String databaseType, String schemaName) {
        StringBuilder tableDDL = new StringBuilder();
        
        tableDDL.append("-- ===========================================\n");
        tableDDL.append("-- 2. 테이블 구조 생성\n");
        tableDDL.append("-- ===========================================\n");
        
        // CREATE TABLE 문 시작
        if ("POSTGRESQL".equals(databaseType)) {
            tableDDL.append("CREATE TABLE ").append(schemaName).append(".").append(tableName).append(" (\n");
        } else {
            tableDDL.append("CREATE TABLE ").append(tableName).append(" (\n");
        }
        
        // 컬럼 정의
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) tableDefinition.get("columns");
        
        if (columns != null && !columns.isEmpty()) {
            for (int i = 0; i < columns.size(); i++) {
                Map<String, Object> column = columns.get(i);
                
                tableDDL.append("    ");
                tableDDL.append(column.get("name")).append(" ");
                tableDDL.append(column.get("type"));
                
                // 크기 설정
                Object length = column.get("length");
                if (length != null && !"null".equals(length.toString()) && (Integer) length > 0) {
                    if ("VARCHAR".equals(column.get("type")) || "CHAR".equals(column.get("type"))) {
                        tableDDL.append("(").append(length).append(")");
                    } else if ("DECIMAL".equals(column.get("type"))) {
                        tableDDL.append("(").append(length).append(")");
                    }
                }
                
                // NULL/NOT NULL 설정
                Boolean nullable = (Boolean) column.get("nullable");
                if (nullable != null && !nullable) {
                    tableDDL.append(" NOT NULL");
                }
                
                // 기본값 설정
                Object defaultValue = column.get("defaultValue");
                if (defaultValue != null && !"null".equals(defaultValue.toString()) && !"NULL".equals(defaultValue.toString())) {
                    tableDDL.append(" DEFAULT ").append(defaultValue);
                }
                
                // AUTO_INCREMENT 설정
                Boolean autoIncrement = (Boolean) column.get("autoIncrement");
                if (autoIncrement != null && autoIncrement) {
                    if ("MYSQL".equals(databaseType)) {
                        tableDDL.append(" AUTO_INCREMENT");
                    } else if ("POSTGRESQL".equals(databaseType)) {
                        tableDDL.append(" GENERATED BY DEFAULT AS IDENTITY");
                    } else if ("ORACLE".equals(databaseType)) {
                        tableDDL.append(" GENERATED BY DEFAULT AS IDENTITY");
                    } else if ("H2".equals(databaseType)) {
                        tableDDL.append(" AUTO_INCREMENT");
                    }
                }
                
                // 컬럼 코멘트 (MySQL)
                Object comment = column.get("comment");
                if (comment != null && !"null".equals(comment.toString()) && "MYSQL".equals(databaseType)) {
                    tableDDL.append(" COMMENT '").append(comment).append("'");
                }
                
                if (i < columns.size() - 1) {
                    tableDDL.append(",");
                }
                tableDDL.append("\n");
            }
            
            // Primary Key 추가
            List<String> primaryKeys = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                Boolean pk = (Boolean) column.get("pk");
                if (pk != null && pk) {
                    primaryKeys.add((String) column.get("name"));
                }
            }
            
            if (!primaryKeys.isEmpty()) {
                tableDDL.append("    PRIMARY KEY (").append(String.join(", ", primaryKeys)).append(")");
            }
            
            // UNIQUE 제약조건 추가
            List<String> uniqueColumns = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                Boolean unique = (Boolean) column.get("unique");
                if (unique != null && unique) {
                    uniqueColumns.add((String) column.get("name"));
                }
            }
            
            if (!uniqueColumns.isEmpty()) {
                tableDDL.append(",\n    UNIQUE (").append(String.join(", ", uniqueColumns)).append(")");
            }
        }
        
        tableDDL.append("\n);\n\n");
        
        // 테이블 코멘트 추가
        Object tableComment = tableDefinition.get("tableComment");
        if (tableComment != null && !"null".equals(tableComment.toString())) {
            tableDDL.append("-- 테이블 설명: ").append(tableComment).append("\n");
        }
        
        return tableDDL.toString();
    }
    
    /**
     * 3. 인덱스 정의 DDL을 생성합니다.
     */
    private String generateIndexDefinitionDDL(String tableName, Map<String, Object> tableDefinition, 
                                            String databaseType, String schemaName) {
        StringBuilder indexDDL = new StringBuilder();
        
        indexDDL.append("-- ===========================================\n");
        indexDDL.append("-- 3. 인덱스 정의\n");
        indexDDL.append("-- ===========================================\n");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) tableDefinition.get("columns");
        
        if (columns != null && !columns.isEmpty()) {
            // Primary Key Index
            List<String> primaryKeys = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                Boolean pk = (Boolean) column.get("pk");
                if (pk != null && pk) {
                    primaryKeys.add((String) column.get("name"));
                }
            }
            
            if (!primaryKeys.isEmpty()) {
                if ("POSTGRESQL".equals(databaseType)) {
                    indexDDL.append("-- Primary Key Index\n");
                    indexDDL.append("CREATE UNIQUE INDEX ").append(tableName).append("_pkey ON ")
                           .append(schemaName).append(".").append(tableName)
                           .append(" (").append(String.join(", ", primaryKeys)).append(");\n\n");
                } else {
                    indexDDL.append("-- Primary Key Index\n");
                    indexDDL.append("CREATE UNIQUE INDEX ").append(tableName).append("_pkey ON ")
                           .append(tableName).append(" (").append(String.join(", ", primaryKeys)).append(");\n\n");
                }
            }
            
            // 일반 컬럼 Index
            indexDDL.append("-- 일반 컬럼 Index\n");
            for (Map<String, Object> column : columns) {
                Boolean indexed = (Boolean) column.get("indexed");
                if (indexed != null && indexed) {
                    String columnName = (String) column.get("name");
                    String indexName = tableName + "_" + columnName.toLowerCase() + "_idx";
                    
                    if ("POSTGRESQL".equals(databaseType)) {
                        indexDDL.append("CREATE INDEX ").append(indexName).append(" ON ")
                               .append(schemaName).append(".").append(tableName)
                               .append(" (").append(columnName).append(");\n");
                    } else {
                        indexDDL.append("CREATE INDEX ").append(indexName).append(" ON ")
                               .append(tableName).append(" (").append(columnName).append(");\n");
                    }
                }
            }
            
            // 복합 인덱스 생성
            indexDDL.append("\n-- 복합 인덱스\n");
            List<String> indexedColumns = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                Boolean indexed = (Boolean) column.get("indexed");
                if (indexed != null && indexed) {
                    indexedColumns.add((String) column.get("name"));
                }
            }
            
            if (indexedColumns.size() > 1) {
                String compositeIndexName = tableName + "_composite_idx";
                if ("POSTGRESQL".equals(databaseType)) {
                    indexDDL.append("CREATE INDEX ").append(compositeIndexName).append(" ON ")
                           .append(schemaName).append(".").append(tableName)
                           .append(" (").append(String.join(", ", indexedColumns)).append(");\n");
                } else {
                    indexDDL.append("CREATE INDEX ").append(compositeIndexName).append(" ON ")
                           .append(tableName).append(" (").append(String.join(", ", indexedColumns)).append(");\n");
                }
            }
            
            indexDDL.append("\n");
        }
        
        return indexDDL.toString();
    }
    
    /**
     * 4. 외래키 제약조건 DDL을 생성합니다.
     */
    private String generateForeignKeyDDL(String tableName, Map<String, Object> tableDefinition, 
                                       String databaseType, String schemaName) {
        StringBuilder fkDDL = new StringBuilder();
        
        fkDDL.append("-- ===========================================\n");
        fkDDL.append("-- 4. 외래키 제약조건\n");
        fkDDL.append("-- ===========================================\n");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> foreignKeys = (List<Map<String, Object>>) tableDefinition.get("foreignKeys");
        
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (Map<String, Object> fk : foreignKeys) {
                String fkName = (String) fk.get("name");
                String columnName = (String) fk.get("columnName");
                String referencedTable = (String) fk.get("referencedTable");
                String referencedColumn = (String) fk.get("referencedColumn");
                
                if ("POSTGRESQL".equals(databaseType)) {
                    fkDDL.append("ALTER TABLE ").append(schemaName).append(".").append(tableName)
                         .append(" ADD CONSTRAINT ").append(fkName)
                         .append(" FOREIGN KEY (").append(columnName).append(")")
                         .append(" REFERENCES ").append(schemaName).append(".").append(referencedTable)
                         .append(" (").append(referencedColumn).append(");\n");
                } else {
                    fkDDL.append("ALTER TABLE ").append(tableName)
                         .append(" ADD CONSTRAINT ").append(fkName)
                         .append(" FOREIGN KEY (").append(columnName).append(")")
                         .append(" REFERENCES ").append(referencedTable)
                         .append(" (").append(referencedColumn).append(");\n");
                }
            }
        } else {
            fkDDL.append("-- 외래키 제약조건이 정의되지 않았습니다.\n");
        }
        
        fkDDL.append("\n");
        return fkDDL.toString();
    }
    
    /**
     * 5. 트리거 정의 DDL을 생성합니다.
     */
    private String generateTriggerDefinitionDDL(String tableName, Map<String, Object> tableDefinition, 
                                              String databaseType, String schemaName) {
        StringBuilder triggerDDL = new StringBuilder();
        
        triggerDDL.append("-- ===========================================\n");
        triggerDDL.append("-- 5. 트리거 정의\n");
        triggerDDL.append("-- ===========================================\n");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) tableDefinition.get("columns");
        
        // Primary Key 컬럼 찾기
        String pkColumn = null;
        for (Map<String, Object> column : columns) {
            Boolean pk = (Boolean) column.get("pk");
            if (pk != null && pk) {
                pkColumn = (String) column.get("name");
                break;
            }
        }
        
        if (pkColumn != null) {
            if ("ORACLE".equals(databaseType)) {
                triggerDDL.append("-- Oracle 트리거 생성\n");
                triggerDDL.append("CREATE OR REPLACE TRIGGER ").append(tableName).append("_TRG\n");
                triggerDDL.append("    BEFORE INSERT ON ").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    IF :NEW.").append(pkColumn).append(" IS NULL THEN\n");
                triggerDDL.append("        SELECT ").append(tableName).append("_SEQ.NEXTVAL\n");
                triggerDDL.append("        INTO :NEW.").append(pkColumn).append("\n");
                triggerDDL.append("        FROM DUAL;\n");
                triggerDDL.append("    END IF;\n");
                triggerDDL.append("END;\n/\n\n");
                
                // 감사 트리거
                triggerDDL.append("-- Oracle 감사 트리거\n");
                triggerDDL.append("CREATE OR REPLACE TRIGGER ").append(tableName).append("_AUDIT_TRG\n");
                triggerDDL.append("    AFTER INSERT OR UPDATE OR DELETE ON ").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    -- 감사 로그 테이블에 기록\n");
                triggerDDL.append("    INSERT INTO AUDIT_LOG (TABLE_NAME, OPERATION, USER_ID, TIMESTAMP)\n");
                triggerDDL.append("    VALUES ('").append(tableName).append("', '").append("INSERT/UPDATE/DELETE").append("', USER, SYSDATE);\n");
                triggerDDL.append("END;\n/\n\n");
                
            } else if ("POSTGRESQL".equals(databaseType)) {
                triggerDDL.append("-- PostgreSQL 트리거 함수 생성\n");
                triggerDDL.append("CREATE OR REPLACE FUNCTION ").append(schemaName).append(".").append(tableName)
                         .append("_trigger_function()\n");
                triggerDDL.append("RETURNS TRIGGER AS $$\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    IF NEW.").append(pkColumn).append(" IS NULL THEN\n");
                triggerDDL.append("        NEW.").append(pkColumn).append(" := nextval('")
                         .append(schemaName).append(".").append(tableName).append("_seq');\n");
                triggerDDL.append("    END IF;\n");
                triggerDDL.append("    RETURN NEW;\n");
                triggerDDL.append("END;\n$$ LANGUAGE plpgsql;\n\n");
                
                triggerDDL.append("-- PostgreSQL 트리거 생성\n");
                triggerDDL.append("CREATE TRIGGER ").append(tableName).append("_trigger\n");
                triggerDDL.append("    BEFORE INSERT ON ").append(schemaName).append(".").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("    EXECUTE FUNCTION ").append(schemaName).append(".").append(tableName).append("_trigger_function();\n\n");
                
                // 감사 트리거
                triggerDDL.append("-- PostgreSQL 감사 트리거\n");
                triggerDDL.append("CREATE OR REPLACE FUNCTION ").append(schemaName).append(".").append(tableName)
                         .append("_audit_function()\n");
                triggerDDL.append("RETURNS TRIGGER AS $$\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    INSERT INTO audit_log (table_name, operation, user_id, timestamp)\n");
                triggerDDL.append("    VALUES ('").append(tableName).append("', TG_OP, current_user, now());\n");
                triggerDDL.append("    RETURN NEW;\n");
                triggerDDL.append("END;\n$$ LANGUAGE plpgsql;\n\n");
                
                triggerDDL.append("CREATE TRIGGER ").append(tableName).append("_audit_trigger\n");
                triggerDDL.append("    AFTER INSERT OR UPDATE OR DELETE ON ").append(schemaName).append(".").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("    EXECUTE FUNCTION ").append(schemaName).append(".").append(tableName).append("_audit_function();\n\n");
                
            } else if ("MYSQL".equals(databaseType)) {
                triggerDDL.append("-- MySQL 트리거 생성\n");
                triggerDDL.append("DELIMITER $$\n");
                triggerDDL.append("CREATE TRIGGER ").append(tableName).append("_before_insert\n");
                triggerDDL.append("    BEFORE INSERT ON ").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    -- 자동 증가는 AUTO_INCREMENT로 처리됨\n");
                triggerDDL.append("    SET NEW.created_at = NOW();\n");
                triggerDDL.append("    SET NEW.updated_at = NOW();\n");
                triggerDDL.append("END$$\n\n");
                
                triggerDDL.append("CREATE TRIGGER ").append(tableName).append("_before_update\n");
                triggerDDL.append("    BEFORE UPDATE ON ").append(tableName).append("\n");
                triggerDDL.append("    FOR EACH ROW\n");
                triggerDDL.append("BEGIN\n");
                triggerDDL.append("    SET NEW.updated_at = NOW();\n");
                triggerDDL.append("END$$\n");
                triggerDDL.append("DELIMITER ;\n\n");
            }
        } else {
            triggerDDL.append("-- Primary Key가 없어 트리거를 생성할 수 없습니다.\n\n");
        }
        
        return triggerDDL.toString();
    }
    
    /**
     * 6. 뷰 정의 DDL을 생성합니다.
     */
    private String generateViewDefinitionDDL(String tableName, Map<String, Object> tableDefinition, 
                                           String databaseType, String schemaName) {
        StringBuilder viewDDL = new StringBuilder();
        
        viewDDL.append("-- ===========================================\n");
        viewDDL.append("-- 6. 뷰 정의\n");
        viewDDL.append("-- ===========================================\n");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) tableDefinition.get("columns");
        
        if (columns != null && !columns.isEmpty()) {
            // 기본 뷰 생성
            List<String> columnNames = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                columnNames.add((String) column.get("name"));
            }
            
            if ("POSTGRESQL".equals(databaseType)) {
                viewDDL.append("-- 기본 뷰 생성\n");
                viewDDL.append("CREATE VIEW ").append(schemaName).append(".").append(tableName).append("_view AS\n");
                viewDDL.append("SELECT ").append(String.join(", ", columnNames)).append("\n");
                viewDDL.append("FROM ").append(schemaName).append(".").append(tableName).append(";\n\n");
                
                // 읽기 전용 뷰
                viewDDL.append("-- 읽기 전용 뷰 생성\n");
                viewDDL.append("CREATE VIEW ").append(schemaName).append(".").append(tableName).append("_readonly AS\n");
                viewDDL.append("SELECT ").append(String.join(", ", columnNames)).append("\n");
                viewDDL.append("FROM ").append(schemaName).append(".").append(tableName).append("\n");
                viewDDL.append("WHERE deleted_at IS NULL;\n\n");
                
            } else {
                viewDDL.append("-- 기본 뷰 생성\n");
                viewDDL.append("CREATE VIEW ").append(tableName).append("_view AS\n");
                viewDDL.append("SELECT ").append(String.join(", ", columnNames)).append("\n");
                viewDDL.append("FROM ").append(tableName).append(";\n\n");
                
                // 읽기 전용 뷰
                viewDDL.append("-- 읽기 전용 뷰 생성\n");
                viewDDL.append("CREATE VIEW ").append(tableName).append("_readonly AS\n");
                viewDDL.append("SELECT ").append(String.join(", ", columnNames)).append("\n");
                viewDDL.append("FROM ").append(tableName).append("\n");
                viewDDL.append("WHERE deleted_at IS NULL;\n\n");
            }
            
            // 요약 뷰 생성
            viewDDL.append("-- 요약 뷰 생성\n");
            if ("POSTGRESQL".equals(databaseType)) {
                viewDDL.append("CREATE VIEW ").append(schemaName).append(".").append(tableName).append("_summary AS\n");
                viewDDL.append("SELECT COUNT(*) as total_count,\n");
                viewDDL.append("       MAX(created_at) as last_created,\n");
                viewDDL.append("       MIN(created_at) as first_created\n");
                viewDDL.append("FROM ").append(schemaName).append(".").append(tableName).append(";\n\n");
            } else {
                viewDDL.append("CREATE VIEW ").append(tableName).append("_summary AS\n");
                viewDDL.append("SELECT COUNT(*) as total_count,\n");
                viewDDL.append("       MAX(created_at) as last_created,\n");
                viewDDL.append("       MIN(created_at) as first_created\n");
                viewDDL.append("FROM ").append(tableName).append(";\n\n");
            }
            
        } else {
            viewDDL.append("-- 컬럼 정보가 없어 뷰를 생성할 수 없습니다.\n\n");
        }
        
        return viewDDL.toString();
    }
    
    /**
     * 7. 샘플 데이터 삽입 DDL을 생성합니다.
     */
    private String generateSampleDataDDL(String tableName, Map<String, Object> tableDefinition, 
                                        String databaseType, String schemaName) {
        StringBuilder sampleDataDDL = new StringBuilder();
        
        sampleDataDDL.append("-- ===========================================\n");
        sampleDataDDL.append("-- 7. 샘플 데이터 삽입\n");
        sampleDataDDL.append("-- ===========================================\n");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> columns = (List<Map<String, Object>>) tableDefinition.get("columns");
        
        if (columns != null && !columns.isEmpty()) {
            if ("POSTGRESQL".equals(databaseType)) {
                sampleDataDDL.append("INSERT INTO ").append(schemaName).append(".").append(tableName).append(" (");
            } else {
                sampleDataDDL.append("INSERT INTO ").append(tableName).append(" (");
            }
            
            // 컬럼명 추가
            List<String> columnNames = new ArrayList<>();
            for (Map<String, Object> column : columns) {
                columnNames.add((String) column.get("name"));
            }
            sampleDataDDL.append(String.join(", ", columnNames)).append(") VALUES\n");
            
            // 샘플 데이터 추가
            sampleDataDDL.append("    ('샘플 데이터 1', '2025-08-01', 'ACTIVE'),\n");
            sampleDataDDL.append("    ('샘플 데이터 2', '2025-08-01', 'ACTIVE'),\n");
            sampleDataDDL.append("    ('샘플 데이터 3', '2025-08-01', 'ACTIVE');\n\n");
            
            // 데이터 확인 쿼리
            sampleDataDDL.append("-- 데이터 확인\n");
            if ("POSTGRESQL".equals(databaseType)) {
                sampleDataDDL.append("SELECT COUNT(*) FROM ").append(schemaName).append(".").append(tableName).append(";\n");
                sampleDataDDL.append("SELECT * FROM ").append(schemaName).append(".").append(tableName).append(" LIMIT 5;\n\n");
            } else {
                sampleDataDDL.append("SELECT COUNT(*) FROM ").append(tableName).append(";\n");
                sampleDataDDL.append("SELECT * FROM ").append(tableName).append(" LIMIT 5;\n\n");
            }
        }
        
        return sampleDataDDL.toString();
    }
} 