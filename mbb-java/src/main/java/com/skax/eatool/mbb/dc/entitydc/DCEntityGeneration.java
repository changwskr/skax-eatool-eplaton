package com.skax.eatool.mbb.dc.entitydc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.EntityGenerationDDTO;
import com.skax.eatool.mbb.dc.dto.TableColumnInfoDDTO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

/**
 * Entity 생성 DC (Data Controller)
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class DCEntityGeneration {

    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());

    /**
     * 사용 가능한 테이블 목록을 조회합니다.
     *
     * @return 테이블 목록
     */
    public List<String> getAvailableTables() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 목록 조회 시작");
        
        List<String> tables = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            
            logger.info(className, "테이블 목록 조회 완료: " + tables.size() + "개 테이블");
            return tables;
            
        } catch (Exception e) {
            logger.error(className, "테이블 목록 조회 중 오류 발생: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 테이블의 컬럼 정보를 조회합니다.
     *
     * @param tableName 테이블명
     * @return 컬럼 정보 목록
     */
    public List<TableColumnInfoDDTO> getTableColumns(String tableName) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "테이블 컬럼 정보 조회 시작: " + tableName);
        
        List<TableColumnInfoDDTO> columns = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName.toUpperCase(), null);
            
            while (rs.next()) {
                TableColumnInfoDDTO column = new TableColumnInfoDDTO();
                column.setColumnName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getString("TYPE_NAME"));
                column.setColumnSize(rs.getInt("COLUMN_SIZE"));
                column.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                column.setDefaultValue(rs.getString("COLUMN_DEF"));
                column.setRemarks(rs.getString("REMARKS"));
                column.setPrimaryKey(isPrimaryKey(metaData, tableName, rs.getString("COLUMN_NAME")));
                column.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
                column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                column.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT")));
                
                columns.add(column);
            }
            
            logger.info(className, "테이블 컬럼 정보 조회 완료: " + tableName + " (" + columns.size() + "개 컬럼)");
            return columns;
            
        } catch (Exception e) {
            logger.error(className, "테이블 컬럼 정보 조회 중 오류 발생: " + e.getMessage(), e);
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
        logger.info(className, "Entity 생성 시작: " + tableName);
        
        try {
            // 테이블 정보 조회
            List<TableColumnInfoDDTO> columns = getTableColumns(tableName);
            
            if (columns.isEmpty()) {
                logger.error(className, "테이블 정보를 찾을 수 없습니다: " + tableName);
                return createErrorResult("테이블 정보를 찾을 수 없습니다: " + tableName);
            }
            
            // Entity 클래스 생성
            String entityCode = generateEntityCode(tableName, columns);
            
            EntityGenerationDDTO result = new EntityGenerationDDTO();
            result.setTableName(tableName);
            result.setColumns(columns);
            result.setEntityCode(entityCode);
            result.setSuccess(true);
            result.setMessage("Entity 생성 성공");
            
            logger.info(className, "Entity 생성 완료: " + tableName);
            return result;
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 중 오류 발생: " + e.getMessage(), e);
            return createErrorResult("Entity 생성 실패: " + e.getMessage());
        }
    }

    /**
     * 테이블명과 컬럼 정보를 함께 받아 Entity를 생성합니다.
     *
     * @param tableName 테이블명
     * @param packageName 패키지명
     * @param columns 컬럼 정보 리스트
     * @return Entity 생성 결과
     */
    public EntityGenerationDDTO generateEntityWithColumns(String tableName, String packageName, List<TableColumnInfoDDTO> columns) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "Entity 생성 시작 (컬럼 정보 포함): " + tableName);
        
        try {
            if (columns == null || columns.isEmpty()) {
                logger.error(className, "컬럼 정보가 비어있습니다: " + tableName);
                return createErrorResult("컬럼 정보가 비어있습니다: " + tableName);
            }
            
            // Entity 클래스 생성
            String entityCode = generateEntityCodeWithPackage(tableName, packageName, columns);
            String className2 = convertToClassName(tableName);
            
            // 파일 시스템에 실제 파일 생성
            String filePath = createEntityFile(packageName, className2, entityCode);
            
            EntityGenerationDDTO result = new EntityGenerationDDTO();
            result.setTableName(tableName);
            result.setPackageName(packageName);
            result.setColumns(columns);
            result.setEntityCode(entityCode);
            result.setClassName(className2);
            result.setFilePath(filePath);
            result.setSuccess(true);
            result.setMessage("Entity 생성 성공");
            
            logger.info(className, "Entity 생성 완료 (컬럼 정보 포함): " + tableName + " -> " + filePath);
            return result;
            
        } catch (Exception e) {
            logger.error(className, "Entity 생성 중 오류 발생: " + e.getMessage(), e);
            return createErrorResult("Entity 생성 실패: " + e.getMessage());
        }
    }

    /**
     * Primary Key 여부를 확인합니다.
     *
     * @param metaData 데이터베이스 메타데이터
     * @param tableName 테이블명
     * @param columnName 컬럼명
     * @return Primary Key 여부
     */
    private boolean isPrimaryKey(DatabaseMetaData metaData, String tableName, String columnName) {
        try {
            ResultSet rs = metaData.getPrimaryKeys(null, null, tableName.toUpperCase());
            while (rs.next()) {
                if (columnName.equalsIgnoreCase(rs.getString("COLUMN_NAME"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("isPrimaryKey", "Primary Key 조회 실패: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Entity 클래스 코드를 생성합니다.
     *
     * @param tableName 테이블명
     * @param columns 컬럼 정보 리스트
     * @return 생성된 Entity 코드
     */
    private String generateEntityCode(String tableName, List<TableColumnInfoDDTO> columns) {
        return generateEntityCodeWithPackage(tableName, "com.skax.eatool.entity", columns);
    }

    /**
     * 패키지명을 포함한 Entity 클래스 코드를 생성합니다.
     *
     * @param tableName 테이블명
     * @param packageName 패키지명
     * @param columns 컬럼 정보 리스트
     * @return 생성된 Entity 코드
     */
    private String generateEntityCodeWithPackage(String tableName, String packageName, List<TableColumnInfoDDTO> columns) {
        StringBuilder code = new StringBuilder();
        
        // 패키지 선언
        code.append("package ").append(packageName).append(";\n\n");
        
        // import 문
        code.append("import lombok.Data;\n");
        code.append("import lombok.EqualsAndHashCode;\n");
        code.append("import javax.persistence.*;\n");
        code.append("import java.time.LocalDateTime;\n");
        code.append("import java.time.LocalDate;\n");
        code.append("import java.math.BigDecimal;\n\n");
        
        // 클래스 시작
        String className = convertToClassName(tableName);
        code.append("/**\n");
        code.append(" * ").append(className).append(" Entity\n");
        code.append(" * \n");
        code.append(" * @author AI Assistant\n");
        code.append(" * @version 1.0\n");
        code.append(" * @since 2024-01-01\n");
        code.append(" */\n");
        code.append("@Data\n");
        code.append("@EqualsAndHashCode(callSuper = false)\n");
        code.append("@Entity\n");
        code.append("@Table(name = \"").append(tableName).append("\")\n");
        code.append("public class ").append(className).append(" {\n\n");
        
        // 필드 생성
        for (TableColumnInfoDDTO column : columns) {
            // 주석
            if (column.getRemarks() != null && !column.getRemarks().isEmpty()) {
                code.append("    /**\n");
                code.append("     * ").append(column.getRemarks()).append("\n");
                code.append("     */\n");
            }
            
            // 컬럼 어노테이션
            code.append("    @Column(name = \"").append(column.getColumnName()).append("\"");
            if (!column.isNullable()) {
                code.append(", nullable = false");
            }
            if (column.getColumnSize() > 0) {
                code.append(", length = ").append(column.getColumnSize());
            }
            code.append(")\n");
            
            // Primary Key 어노테이션
            if (column.isPrimaryKey()) {
                code.append("    @Id\n");
                code.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            }
            
            // 필드 선언
            String javaType = convertToJavaType(column.getDataType());
            String fieldName = convertToFieldName(column.getColumnName());
            code.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n\n");
        }
        
        // 클래스 종료
        code.append("}\n");
        
        return code.toString();
    }

    /**
     * 테이블명을 클래스명으로 변환합니다.
     *
     * @param tableName 테이블명
     * @return 클래스명
     */
    private String convertToClassName(String tableName) {
        StringBuilder className = new StringBuilder();
        boolean nextUpper = true;
        
        for (char c : tableName.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    className.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    className.append(Character.toLowerCase(c));
                }
            }
        }
        
        return className.toString();
    }

    /**
     * 컬럼명을 필드명으로 변환합니다.
     *
     * @param columnName 컬럼명
     * @return 필드명
     */
    private String convertToFieldName(String columnName) {
        StringBuilder fieldName = new StringBuilder();
        boolean nextUpper = false;
        
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    fieldName.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    fieldName.append(Character.toLowerCase(c));
                }
            }
        }
        
        return fieldName.toString();
    }

    /**
     * 데이터베이스 타입을 Java 타입으로 변환합니다.
     *
     * @param dbType 데이터베이스 타입
     * @return Java 타입
     */
    private String convertToJavaType(String dbType) {
        if (dbType == null) {
            return "String";
        }
        
        switch (dbType.toUpperCase()) {
            case "BIGINT":
                return "Long";
            case "INT":
            case "INTEGER":
                return "Integer";
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "DOUBLE":
            case "FLOAT":
                return "Double";
            case "BOOLEAN":
            case "BIT":
                return "Boolean";
            case "DATETIME":
            case "TIMESTAMP":
                return "LocalDateTime";
            case "DATE":
                return "LocalDate";
            case "DECIMAL":
            case "NUMERIC":
                return "BigDecimal";
            default:
                return "String";
        }
    }

    /**
     * 오류 결과를 생성합니다.
     *
     * @param message 오류 메시지
     * @return 오류 결과
     */
    private EntityGenerationDDTO createErrorResult(String message) {
        EntityGenerationDDTO result = new EntityGenerationDDTO();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    /**
     * Entity 파일을 파일 시스템에 생성합니다.
     *
     * @param packageName 패키지명
     * @param className 클래스명
     * @param entityCode Entity 코드
     * @return 생성된 파일 경로
     */
    private String createEntityFile(String packageName, String className, String entityCode) throws Exception {
        String className3 = this.getClass().getSimpleName();
        logger.info(className3, "Entity 파일 생성 시작: " + className);
        
        // 프로젝트 루트 디렉토리 찾기
        String projectRoot = System.getProperty("user.dir");
        String generatedDir = projectRoot + "/z-generated-files/src/main/java";
        
        // 생성된 Entity 파일들을 저장할 디렉토리 생성
        File dir = new File(generatedDir);
        if (!dir.exists()) {
            dir.mkdirs();
            logger.info(className3, "Entity 파일 디렉토리 생성: " + generatedDir);
        }
        
        // 패키지 디렉토리 생성
        String packagePath = packageName.replace('.', '/');
        File packageDir = new File(generatedDir + "/" + packagePath);
        if (!packageDir.exists()) {
            packageDir.mkdirs();
            logger.info(className3, "패키지 디렉토리 생성: " + packageDir.getAbsolutePath());
        }
        
        // 파일 생성
        String fileName = className + ".java";
        File entityFile = new File(packageDir, fileName);
        
        try (FileWriter writer = new FileWriter(entityFile)) {
            writer.write(entityCode);
            writer.flush();
        }
        
        String filePath = entityFile.getAbsolutePath();
        logger.info(className3, "Entity 파일 생성 완료: " + filePath);
        
        return filePath;
    }
} 