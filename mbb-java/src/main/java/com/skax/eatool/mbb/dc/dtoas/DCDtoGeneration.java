package com.skax.eatool.mbb.dc.dtoas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO 생성 Data Controller
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class DCDtoGeneration {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    private final String className = this.getClass().getSimpleName();
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * DTO 생성 실행
     * 
     * @param requestData 요청 데이터
     * @return DTO 생성 결과
     */
    public NewKBData generateDto(Map<String, Object> requestData) {
        String methodName = "generateDto";
        logger.info(className, "[" + methodName + "] DTO 생성 DC 시작");
        
        try {
            // 요청 데이터에서 필요한 정보 추출
            String basePackage = (String) requestData.get("basePackage");
            String entityName = (String) requestData.get("entityName");
            String requestPackage = (String) requestData.get("requestPackage");
            String responsePackage = (String) requestData.get("responsePackage");
            String domainPackage = (String) requestData.get("domainPackage");
            
            if (basePackage == null || entityName == null) {
                logger.error(className, "[" + methodName + "] 필수 파라미터가 누락되었습니다");
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("필수 파라미터가 누락되었습니다 (basePackage, entityName)");
                return errorResponse;
            }
            
            // Entity 클래스에서 필드 정보 가져오기
            List<ColumnInfo> columns = getEntityFields(entityName);
            
            // DTO 파일 생성
            String[] generatedFiles = createDtoFiles(basePackage, entityName, requestPackage, responsePackage, domainPackage, columns);
            
            // 성공 응답 생성
            NewKBData successResponse = new NewKBData();
            successResponse.setValid(true);
            successResponse.setMessage("DTO 생성이 완료되었습니다");
            successResponse.getOutputGenericDto().put("generatedFiles", java.util.Arrays.asList(generatedFiles));
            successResponse.getOutputGenericDto().put("entityName", entityName);
            successResponse.getOutputGenericDto().put("basePackage", basePackage);
            
            logger.info(className, "[" + methodName + "] DTO 생성 DC 완료");
            return successResponse;
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] DTO 생성 DC 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("DTO 생성 DC 중 오류 발생: " + e.getMessage());
            return errorResponse;
        }
    }
    
    /**
     * DTO 파일들을 생성합니다
     * 
     * @param basePackage 기본 패키지
     * @param entityName 엔티티 이름
     * @param requestPackage 요청 패키지
     * @param responsePackage 응답 패키지
     * @param domainPackage 도메인 패키지
     * @param columns 테이블 컬럼 정보
     * @return 생성된 파일 목록
     */
    private String[] createDtoFiles(String basePackage, String entityName, String requestPackage, String responsePackage, String domainPackage, List<ColumnInfo> columns) throws IOException {
        String methodName = "createDtoFiles";
        logger.info(className, "[" + methodName + "] DTO 파일 생성 시작");
        
        // 기본값 설정
        if (requestPackage == null) requestPackage = "transfer";
        if (responsePackage == null) responsePackage = "transfer";
        if (domainPackage == null) domainPackage = "domain";
        
        // 생성할 파일 목록
        String[] files = {
            basePackage + "." + requestPackage + "." + entityName + "RequestDto.java",
            basePackage + "." + responsePackage + "." + entityName + "ResponseDto.java",
            basePackage + "." + domainPackage + "." + entityName + "DomainDto.java"
        };
        
        // 프로젝트 루트 디렉토리 찾기 (mbb-java 디렉토리의 상위)
        String currentDir = System.getProperty("user.dir");
        logger.info(className, "[" + methodName + "] 현재 작업 디렉토리: " + currentDir);
        
        // mbb-java 디렉토리가 포함된 경우 상위 디렉토리로 이동
        String projectRoot = currentDir;
        if (currentDir.contains("mbb-java")) {
            projectRoot = currentDir.substring(0, currentDir.indexOf("mbb-java"));
            logger.info(className, "[" + methodName + "] 프로젝트 루트 디렉토리: " + projectRoot);
        }
        
        // 파일 생성 경로 설정
        String baseDir = projectRoot + "/z-generated-files/src/main/java/" + basePackage.replace(".", "/");
        
        logger.info(className, "[" + methodName + "] 파일 생성 경로: " + baseDir);
        
        // RequestDto 생성
        createRequestDto(baseDir, basePackage, entityName, requestPackage, columns);
        
        // ResponseDto 생성
        createResponseDto(baseDir, basePackage, entityName, responsePackage, columns);
        
        // DomainDto 생성
        createDomainDto(baseDir, basePackage, entityName, domainPackage, columns);
        
        logger.info(className, "[" + methodName + "] DTO 파일 생성 완료");
        return files;
    }
    
    /**
     * RequestDto 파일을 생성합니다
     */
    private void createRequestDto(String baseDir, String basePackage, String entityName, String requestPackage, List<ColumnInfo> columns) throws IOException {
        String className = entityName + "RequestDto";
        String packageName = basePackage + "." + requestPackage;
        
        StringBuilder fields = new StringBuilder();
        for (ColumnInfo column : columns) {
            String javaType = getJavaType(column.getType());
            String fieldName = column.getName();
            
            fields.append("    /**\n");
            fields.append("     * ").append(column.getRemarks() != null ? column.getRemarks() : fieldName).append("\n");
            fields.append("     */\n");
            fields.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n\n");
        }
        
        // 필요한 import 문들 생성
        StringBuilder imports = new StringBuilder();
        imports.append("import lombok.Data;\n");
        imports.append("import lombok.NoArgsConstructor;\n");
        imports.append("import lombok.AllArgsConstructor;\n");
        imports.append("import javax.validation.constraints.*;\n");
        
        // 날짜 타입이 있는 경우 import 추가
        boolean hasDateType = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).contains("LocalDate") || 
            getJavaType(col.getType()).contains("LocalDateTime") || 
            getJavaType(col.getType()).contains("LocalTime"));
        
        if (hasDateType) {
            imports.append("import java.time.*;\n");
        }
        
        // BigDecimal 타입이 있는 경우 import 추가
        boolean hasBigDecimal = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).equals("BigDecimal"));
        
        if (hasBigDecimal) {
            imports.append("import java.math.BigDecimal;\n");
        }
        
        String content = String.format(
            "package %s;\n\n" +
            "%s\n" +
            "/**\n" +
            " * %s 요청 DTO\n" +
            " * \n" +
            " * @author AI Assistant\n" +
            " * @version 1.0\n" +
            " * @since 2024-01-01\n" +
            " */\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class %s {\n\n" +
            "%s" +
            "}",
            packageName, imports.toString(), entityName, className, fields.toString()
        );
        
        writeFile(baseDir, requestPackage, className, content);
    }
    
    /**
     * ResponseDto 파일을 생성합니다
     */
    private void createResponseDto(String baseDir, String basePackage, String entityName, String responsePackage, List<ColumnInfo> columns) throws IOException {
        String className = entityName + "ResponseDto";
        String packageName = basePackage + "." + responsePackage;
        
        StringBuilder fields = new StringBuilder();
        for (ColumnInfo column : columns) {
            String javaType = getJavaType(column.getType());
            String fieldName = column.getName();
            
            fields.append("    /**\n");
            fields.append("     * ").append(column.getRemarks() != null ? column.getRemarks() : fieldName).append("\n");
            fields.append("     */\n");
            fields.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n\n");
        }
        
        // 필요한 import 문들 생성
        StringBuilder imports = new StringBuilder();
        imports.append("import lombok.Data;\n");
        imports.append("import lombok.NoArgsConstructor;\n");
        imports.append("import lombok.AllArgsConstructor;\n");
        
        // 날짜 타입이 있는 경우 import 추가
        boolean hasDateType = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).contains("LocalDate") || 
            getJavaType(col.getType()).contains("LocalDateTime") || 
            getJavaType(col.getType()).contains("LocalTime"));
        
        if (hasDateType) {
            imports.append("import java.time.*;\n");
        }
        
        // BigDecimal 타입이 있는 경우 import 추가
        boolean hasBigDecimal = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).equals("BigDecimal"));
        
        if (hasBigDecimal) {
            imports.append("import java.math.BigDecimal;\n");
        }
        
        String content = String.format(
            "package %s;\n\n" +
            "%s\n" +
            "/**\n" +
            " * %s 응답 DTO\n" +
            " * \n" +
            " * @author AI Assistant\n" +
            " * @version 1.0\n" +
            " * @since 2024-01-01\n" +
            " */\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class %s {\n\n" +
            "%s" +
            "}",
            packageName, imports.toString(), entityName, className, fields.toString()
        );
        
        writeFile(baseDir, responsePackage, className, content);
    }
    
    /**
     * DomainDto 파일을 생성합니다
     */
    private void createDomainDto(String baseDir, String basePackage, String entityName, String domainPackage, List<ColumnInfo> columns) throws IOException {
        String className = entityName + "DomainDto";
        String packageName = basePackage + "." + domainPackage;
        
        StringBuilder fields = new StringBuilder();
        for (ColumnInfo column : columns) {
            String javaType = getJavaType(column.getType());
            String fieldName = column.getName();
            
            fields.append("    /**\n");
            fields.append("     * ").append(column.getRemarks() != null ? column.getRemarks() : fieldName).append("\n");
            fields.append("     */\n");
            fields.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n\n");
        }
        
        // 필요한 import 문들 생성
        StringBuilder imports = new StringBuilder();
        imports.append("import lombok.Data;\n");
        imports.append("import lombok.NoArgsConstructor;\n");
        imports.append("import lombok.AllArgsConstructor;\n");
        
        // 날짜 타입이 있는 경우 import 추가
        boolean hasDateType = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).contains("LocalDate") || 
            getJavaType(col.getType()).contains("LocalDateTime") || 
            getJavaType(col.getType()).contains("LocalTime"));
        
        if (hasDateType) {
            imports.append("import java.time.*;\n");
        }
        
        // BigDecimal 타입이 있는 경우 import 추가
        boolean hasBigDecimal = columns.stream().anyMatch(col -> 
            getJavaType(col.getType()).equals("BigDecimal"));
        
        if (hasBigDecimal) {
            imports.append("import java.math.BigDecimal;\n");
        }
        
        String content = String.format(
            "package %s;\n\n" +
            "%s\n" +
            "/**\n" +
            " * %s 도메인 DTO\n" +
            " * \n" +
            " * @author AI Assistant\n" +
            " * @version 1.0\n" +
            " * @since 2024-01-01\n" +
            " */\n" +
            "@Data\n" +
            "@NoArgsConstructor\n" +
            "@AllArgsConstructor\n" +
            "public class %s {\n\n" +
            "%s" +
            "}",
            packageName, imports.toString(), entityName, className, fields.toString()
        );
        
        writeFile(baseDir, domainPackage, className, content);
    }
    
    /**
     * 파일을 생성합니다
     */
    private void writeFile(String baseDir, String packageDir, String className, String content) throws IOException {
        // 전체 디렉토리 경로 생성
        Path fullDirPath = Paths.get(baseDir, packageDir.replace(".", "/"));
        
        logger.info(className, "디렉토리 생성 시도: " + fullDirPath.toAbsolutePath());
        Files.createDirectories(fullDirPath);
        logger.info(className, "디렉토리 생성 완료: " + fullDirPath.toAbsolutePath());
        
        // 파일 경로 생성
        Path filePath = fullDirPath.resolve(className + ".java");
        logger.info(className, "파일 경로: " + filePath.toAbsolutePath());
        
        // 파일 생성
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write(content);
            logger.info(className, "파일 내용 작성 완료");
        }
        
        logger.info(className, "파일 생성 완료: " + filePath.toAbsolutePath());
    }
    
    /**
     * Entity 클래스에서 필드 정보를 가져옵니다
     */
    private List<ColumnInfo> getEntityFields(String entityName) throws IOException {
        String methodName = "getEntityFields";
        logger.info(className, "[" + methodName + "] Entity 필드 정보 조회 시작: " + entityName);
        
        List<ColumnInfo> columns = new ArrayList<>();
        
        // Entity 파일 경로
        String currentDir = System.getProperty("user.dir");
        String projectRoot = currentDir;
        if (currentDir.contains("mbb-java")) {
            projectRoot = currentDir.substring(0, currentDir.indexOf("mbb-java"));
        }
        
        String entityFilePath = projectRoot + "z-generated-files/src/main/java/com/skax/eatool/mbb/workflow/business/dc/entity/" + entityName + ".java";
        Path entityPath = Paths.get(entityFilePath);
        
        logger.info(className, "[" + methodName + "] Entity 파일 경로: " + entityPath.toAbsolutePath());
        
        if (!Files.exists(entityPath)) {
            logger.warn(className, "[" + methodName + "] Entity 파일이 존재하지 않습니다: " + entityPath);
            // 기본 컬럼 정보 생성
            columns = createDefaultColumns(entityName);
            return columns;
        }
        
        // Entity 파일 읽기
        List<String> lines = Files.readAllLines(entityPath);
        
        for (String line : lines) {
            line = line.trim();
            
            // private 필드 찾기
            if (line.startsWith("private") && line.contains(";")) {
                // @Column 어노테이션 찾기
                String columnName = null;
                String columnType = null;
                String remarks = null;
                
                // 이전 줄에서 @Column 어노테이션 확인
                int lineIndex = lines.indexOf(line);
                if (lineIndex > 0) {
                    String prevLine = lines.get(lineIndex - 1).trim();
                    if (prevLine.startsWith("@Column")) {
                        // @Column(name = "USER_ID", nullable = false, length = 255)
                        if (prevLine.contains("name =")) {
                            int nameStart = prevLine.indexOf("name =") + 6;
                            int nameEnd = prevLine.indexOf(",", nameStart);
                            if (nameEnd == -1) nameEnd = prevLine.indexOf(")", nameStart);
                            if (nameEnd > nameStart) {
                                columnName = prevLine.substring(nameStart, nameEnd).trim().replace("\"", "");
                            }
                        }
                    }
                }
                
                // 필드 정보 파싱
                // private String userName;
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String fieldType = parts[1];
                    String fieldName = parts[2].replace(";", "");
                    
                    // 컬럼명이 없으면 필드명 사용
                    if (columnName == null) {
                        columnName = fieldName;
                    }
                    
                    // Java 타입을 DB 타입으로 변환
                    columnType = getDbTypeFromJavaType(fieldType);
                    
                    ColumnInfo column = new ColumnInfo();
                    column.setName(columnName);
                    column.setType(columnType);
                    column.setRemarks(fieldName + " 필드");
                    column.setNullable(true);
                    
                    columns.add(column);
                    logger.info(className, "[" + methodName + "] 필드 정보: " + columnName + " (" + fieldType + " -> " + columnType + ")");
                }
            }
        }
        
        logger.info(className, "[" + methodName + "] Entity 필드 정보 조회 완료: " + columns.size() + "개 필드");
        return columns;
    }
    
    /**
     * 기본 컬럼 정보를 생성합니다 (Entity와 1:1 매핑)
     */
    private List<ColumnInfo> createDefaultColumns(String tableName) {
        List<ColumnInfo> columns = new ArrayList<>();
        
        // 일반적인 Entity 필드들
        ColumnInfo id = new ColumnInfo();
        id.setName("id");
        id.setType("BIGINT");
        id.setSize(20);
        id.setNullable(false);
        id.setRemarks("기본 키");
        columns.add(id);
        
        ColumnInfo name = new ColumnInfo();
        name.setName("name");
        name.setType("VARCHAR");
        name.setSize(100);
        name.setNullable(true);
        name.setRemarks("이름");
        columns.add(name);
        
        ColumnInfo email = new ColumnInfo();
        email.setName("email");
        email.setType("VARCHAR");
        email.setSize(200);
        email.setNullable(true);
        email.setRemarks("이메일");
        columns.add(email);
        
        ColumnInfo username = new ColumnInfo();
        username.setName("username");
        username.setType("VARCHAR");
        username.setSize(50);
        username.setNullable(true);
        username.setRemarks("사용자명");
        columns.add(username);
        
        ColumnInfo createdAt = new ColumnInfo();
        createdAt.setName("createdAt");
        createdAt.setType("TIMESTAMP");
        createdAt.setSize(0);
        createdAt.setNullable(true);
        createdAt.setRemarks("생성일시");
        columns.add(createdAt);
        
        ColumnInfo updatedAt = new ColumnInfo();
        updatedAt.setName("updatedAt");
        updatedAt.setType("TIMESTAMP");
        updatedAt.setSize(0);
        updatedAt.setNullable(true);
        updatedAt.setRemarks("수정일시");
        columns.add(updatedAt);
        
        ColumnInfo active = new ColumnInfo();
        active.setName("active");
        active.setType("BOOLEAN");
        active.setSize(1);
        active.setNullable(true);
        active.setRemarks("활성화 여부");
        columns.add(active);
        
        logger.info(className, "기본 컬럼 생성 완료: " + columns.size() + "개 컬럼");
        return columns;
    }
    
    /**
     * 컬럼 정보를 담는 클래스
     */
    private static class ColumnInfo {
        private String name;
        private String type;
        private int size;
        private boolean nullable;
        private String defaultValue;
        private String remarks;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        public boolean isNullable() { return nullable; }
        public void setNullable(boolean nullable) { this.nullable = nullable; }
        public String getDefaultValue() { return defaultValue; }
        public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }
    
    /**
     * 데이터베이스 타입을 Java 타입으로 변환합니다
     */
    private String getJavaType(String dbType) {
        if (dbType == null) return "String";
        
        String type = dbType.toUpperCase();
        switch (type) {
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
            case "LONGTEXT":
                return "String";
            case "INT":
            case "INTEGER":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "SMALLINT":
                return "Short";
            case "TINYINT":
                return "Byte";
            case "DECIMAL":
            case "NUMERIC":
                return "BigDecimal";
            case "FLOAT":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "DATE":
                return "LocalDate";
            case "DATETIME":
            case "TIMESTAMP":
                return "LocalDateTime";
            case "TIME":
                return "LocalTime";
            case "BOOLEAN":
            case "BIT":
                return "Boolean";
            case "BLOB":
            case "LONGBLOB":
                return "byte[]";
            default:
                return "String";
        }
    }

    /**
     * Java 타입을 데이터베이스 타입으로 변환합니다
     */
    private String getDbTypeFromJavaType(String javaType) {
        switch (javaType) {
            case "String":
                return "VARCHAR";
            case "Integer":
            case "int":
                return "INT";
            case "Long":
            case "long":
                return "BIGINT";
            case "Short":
            case "short":
                return "SMALLINT";
            case "Byte":
            case "byte":
                return "TINYINT";
            case "BigDecimal":
                return "DECIMAL";
            case "Float":
            case "float":
                return "FLOAT";
            case "Double":
            case "double":
                return "DOUBLE";
            case "LocalDate":
                return "DATE";
            case "LocalDateTime":
                return "DATETIME";
            case "LocalTime":
                return "TIME";
            case "Boolean":
            case "boolean":
                return "BOOLEAN";
            case "byte[]":
                return "BLOB";
            default:
                return "VARCHAR";
        }
    }
} 