package com.skax.eatool.mbb.dc.tablecreationdc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 테이블 생성 DC (Data Controller)
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class DCTableCreation {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    /**
     * 테이블을 생성합니다.
     *
     * @param tableDefinition 테이블 정의
     * @return 생성 성공 여부
     */
    public boolean createTable(TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 생성 시작: " + tableDefinition.getTableName());
        
        try {
            // DDL 생성
            String ddl = generateDDL(tableDefinition);
            logger.info(className, "생성된 DDL: " + ddl);
            
            // DDL 실행
            boolean result = executeDDL(ddl);
            
            if (result) {
                logger.info(className, "테이블 생성 성공: " + tableDefinition.getTableName());
            } else {
                logger.error(className, "테이블 생성 실패: " + tableDefinition.getTableName());
            }
            
            return result;
            
        } catch (Exception e) {
            logger.error(className, "테이블 생성 중 오류 발생: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * DDL을 생성합니다.
     *
     * @param tableDefinition 테이블 정의
     * @return 생성된 DDL
     */
    private String generateDDL(TableDefinitionDDTO tableDefinition) {
        StringBuilder ddl = new StringBuilder();
        
        // CREATE TABLE 문 시작
        ddl.append("CREATE TABLE ").append(tableDefinition.getTableName()).append(" (\n");
        
        List<TableDefinitionDDTO.ColumnDefinition> columns = tableDefinition.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            TableDefinitionDDTO.ColumnDefinition column = columns.get(i);
            
            // 컬럼명
            ddl.append("    ").append(column.getName()).append(" ");
            
            // 데이터 타입
            String dataType = getDataType(column.getType(), column.getLength());
            ddl.append(dataType);
            
            // 기본값
            if (column.getDefaultValue() != null && !column.getDefaultValue().isEmpty()) {
                ddl.append(" DEFAULT ").append(formatDefaultValue(column.getDefaultValue(), column.getType()));
            }
            
            // NULL/NOT NULL
            if (!column.isNullable()) {
                ddl.append(" NOT NULL");
            }
            
            // PRIMARY KEY
            if (column.isPk()) {
                ddl.append(" PRIMARY KEY");
            }
            
            // 마지막 컬럼이 아니면 쉼표 추가
            if (i < columns.size() - 1) {
                ddl.append(",");
            }
            
            ddl.append("\n");
        }
        
        ddl.append(")");
        
        // H2 데이터베이스는 COMMENT를 지원하지 않으므로 제거
        // if (tableDefinition.getTableComment() != null && !tableDefinition.getTableComment().isEmpty()) {
        //     ddl.append(" COMMENT '").append(tableDefinition.getTableComment()).append("'");
        // }
        
        return ddl.toString();
    }

    /**
     * 기본값을 적절한 형식으로 포맷팅합니다.
     *
     * @param defaultValue 기본값
     * @param columnType 컬럼 타입
     * @return 포맷팅된 기본값
     */
    private String formatDefaultValue(String defaultValue, String columnType) {
        // 숫자 타입인 경우 따옴표 없이 반환
        if ("Long".equals(columnType) || "Integer".equals(columnType) || 
            "Double".equals(columnType) || "BigDecimal".equals(columnType) ||
            "Boolean".equals(columnType)) {
            return defaultValue;
        }
        
        // 문자열 타입인 경우 따옴표로 감싸기
        if ("String".equals(columnType) || "LocalDateTime".equals(columnType) || 
            "LocalDate".equals(columnType)) {
            return "'" + defaultValue + "'";
        }
        
        // 기본적으로는 문자열로 처리
        return "'" + defaultValue + "'";
    }

    /**
     * Java 타입을 데이터베이스 타입으로 변환합니다.
     *
     * @param javaType Java 타입
     * @param length 길이
     * @return 데이터베이스 타입
     */
    private String getDataType(String javaType, Integer length) {
        switch (javaType) {
            case "Long":
                return "BIGINT";
            case "Integer":
                return "INT";
            case "String":
                return length != null ? "VARCHAR(" + length + ")" : "VARCHAR(255)";
            case "Double":
                return "DOUBLE";
            case "Boolean":
                return "BOOLEAN";
            case "LocalDateTime":
                return "DATETIME";
            case "LocalDate":
                return "DATE";
            case "BigDecimal":
                return "DECIMAL(19,2)";
            default:
                return "VARCHAR(255)";
        }
    }

    /**
     * DDL을 실행합니다.
     *
     * @param ddl 실행할 DDL
     * @return 실행 결과
     */
    private boolean executeDDL(String ddl) {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "데이터베이스 연결 시도...");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            logger.info(className, "데이터베이스 연결 성공");
            
            try (Statement statement = connection.createStatement()) {
                logger.info(className, "DDL 실행: " + ddl);
                statement.execute(ddl);
                logger.info(className, "DDL 실행 성공");
                
                // 테이블이 실제로 생성되었는지 확인
                try (Statement checkStatement = connection.createStatement()) {
                    checkStatement.execute("SELECT COUNT(*) FROM " + ddl.split(" ")[2]);
                    logger.info(className, "테이블 생성 확인 완료");
                } catch (SQLException checkException) {
                    logger.warn(className, "테이블 확인 중 경고: " + checkException.getMessage());
                }
                
                return true;
            }
            
        } catch (SQLException e) {
            logger.error(className, "DDL 실행 중 SQL 오류: " + e.getMessage());
            logger.error(className, "SQL State: " + e.getSQLState());
            logger.error(className, "Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            logger.error(className, "DDL 실행 중 일반 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 