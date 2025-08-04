package com.skax.eatool.mbb.dc.repositorydc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Repository 생성 Data Controller
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Component
public class DCRepositoryGeneration {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    private final String className = this.getClass().getSimpleName();
    
    /**
     * Repository 생성 실행
     * 
     * @param requestData 요청 데이터
     * @return Repository 생성 결과
     */
    public NewKBData generateRepository(Map<String, Object> requestData) {
        String methodName = "generateRepository";
        logger.info(className, "[" + methodName + "] Repository 생성 DC 시작");
        
        try {
            // 요청 데이터에서 필요한 정보 추출
            String entityName = (String) requestData.get("entityName");
            String basePackage = (String) requestData.get("basePackage");
            String repositoryPackage = (String) requestData.get("repositoryPackage");
            String domainPackage = (String) requestData.get("domainPackage");
            String repositoryType = (String) requestData.get("repositoryType");
            
            Boolean generateInterface = (Boolean) requestData.get("generateInterface");
            Boolean generateImpl = (Boolean) requestData.get("generateImpl");
            Boolean generateCustomMethods = (Boolean) requestData.get("generateCustomMethods");
            Boolean generatePagination = (Boolean) requestData.get("generatePagination");
            Boolean generateSearch = (Boolean) requestData.get("generateSearch");
            Boolean generateAudit = (Boolean) requestData.get("generateAudit");
            
            if (entityName == null || basePackage == null) {
                logger.error(className, "[" + methodName + "] 필수 파라미터가 누락되었습니다");
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("필수 파라미터가 누락되었습니다 (entityName, basePackage)");
                return errorResponse;
            }
            
            // 기본값 설정
            if (repositoryPackage == null) repositoryPackage = "business.dc.repository";
            if (domainPackage == null) domainPackage = "business.dc.dto";
            if (repositoryType == null) repositoryType = "JPA";
            
            if (generateInterface == null) generateInterface = true;
            if (generateImpl == null) generateImpl = true;
            if (generateCustomMethods == null) generateCustomMethods = true;
            if (generatePagination == null) generatePagination = true;
            if (generateSearch == null) generateSearch = true;
            if (generateAudit == null) generateAudit = false;
            
            // Entity 필드 정보 가져오기
            List<FieldInfo> entityFields = getEntityFields(entityName);
            
            // Repository 파일 생성
            List<String> generatedFiles = createRepositoryFiles(
                basePackage, entityName, repositoryPackage, domainPackage, 
                repositoryType, entityFields, generateInterface, generateImpl, 
                generateCustomMethods, generatePagination, generateSearch, generateAudit
            );
            
            // 성공 응답 생성
            NewKBData successResponse = new NewKBData();
            successResponse.setValid(true);
            successResponse.setMessage("Repository 생성이 완료되었습니다");
            successResponse.getOutputGenericDto().put("generatedFiles", generatedFiles);
            successResponse.getOutputGenericDto().put("entityName", entityName);
            successResponse.getOutputGenericDto().put("basePackage", basePackage);
            successResponse.getOutputGenericDto().put("repositoryPackage", repositoryPackage);
            successResponse.getOutputGenericDto().put("domainPackage", domainPackage);
            successResponse.getOutputGenericDto().put("repositoryType", repositoryType);
            successResponse.getOutputGenericDto().put("generateInterface", generateInterface);
            successResponse.getOutputGenericDto().put("generateImpl", generateImpl);
            successResponse.getOutputGenericDto().put("generateCustomMethods", generateCustomMethods);
            successResponse.getOutputGenericDto().put("generatePagination", generatePagination);
            successResponse.getOutputGenericDto().put("generateSearch", generateSearch);
            successResponse.getOutputGenericDto().put("generateAudit", generateAudit);
            successResponse.getOutputGenericDto().put("fieldCount", entityFields.size());
            
            logger.info(className, "[" + methodName + "] Repository 생성 DC 완료 - Entity: " + entityName + ", Type: " + repositoryType + ", Files: " + generatedFiles.size());
            return successResponse;
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] Repository 생성 DC 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Repository 생성 DC 중 오류 발생: " + e.getMessage());
            return errorResponse;
        }
    }
    
    /**
     * Entity 클래스에서 필드 정보를 가져옵니다
     */
    private List<FieldInfo> getEntityFields(String entityName) throws IOException {
        String methodName = "getEntityFields";
        logger.info(className, "[" + methodName + "] Entity 필드 정보 조회 시작: " + entityName);
        
        List<FieldInfo> fields = new ArrayList<>();
        
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
            // 기본 필드 정보 생성
            fields = createDefaultFields(entityName);
            return fields;
        }
        
        // Entity 파일 읽기
        List<String> lines = Files.readAllLines(entityPath);
        
        for (String line : lines) {
            line = line.trim();
            
            // private 필드 찾기
            if (line.startsWith("private") && line.contains(";")) {
                // @Column 어노테이션 찾기
                String columnName = null;
                String fieldType = null;
                String fieldName = null;
                
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
                    fieldType = parts[1];
                    fieldName = parts[2].replace(";", "");
                    
                    // 컬럼명이 없으면 필드명 사용
                    if (columnName == null) {
                        columnName = fieldName;
                    }
                    
                    FieldInfo field = new FieldInfo();
                    field.setName(fieldName);
                    field.setType(fieldType);
                    field.setColumnName(columnName);
                    field.setRemarks(fieldName + " 필드");
                    
                    fields.add(field);
                    logger.info(className, "[" + methodName + "] 필드 정보: " + fieldName + " (" + fieldType + ") -> " + columnName);
                }
            }
        }
        
        logger.info(className, "[" + methodName + "] Entity 필드 정보 조회 완료: " + fields.size() + "개 필드");
        return fields;
    }
    
    /**
     * 기본 필드 정보를 생성합니다
     */
    private List<FieldInfo> createDefaultFields(String entityName) {
        List<FieldInfo> fields = new ArrayList<>();
        
        FieldInfo id = new FieldInfo();
        id.setName("id");
        id.setType("Long");
        id.setColumnName("ID");
        id.setRemarks("기본 키");
        fields.add(id);
        
        FieldInfo name = new FieldInfo();
        name.setName("name");
        name.setType("String");
        name.setColumnName("NAME");
        name.setRemarks("이름");
        fields.add(name);
        
        FieldInfo email = new FieldInfo();
        email.setName("email");
        email.setType("String");
        email.setColumnName("EMAIL");
        email.setRemarks("이메일");
        fields.add(email);
        
        FieldInfo createdAt = new FieldInfo();
        createdAt.setName("createdAt");
        createdAt.setType("LocalDateTime");
        createdAt.setColumnName("CREATED_AT");
        createdAt.setRemarks("생성일시");
        fields.add(createdAt);
        
        logger.info(className, "기본 필드 생성 완료: " + fields.size() + "개 필드");
        return fields;
    }
    
    /**
     * Repository 파일들을 생성합니다
     */
    private List<String> createRepositoryFiles(String basePackage, String entityName, String repositoryPackage, 
                                             String domainPackage, String repositoryType, List<FieldInfo> entityFields,
                                             Boolean generateInterface, Boolean generateImpl, Boolean generateCustomMethods,
                                             Boolean generatePagination, Boolean generateSearch, Boolean generateAudit) throws IOException {
        String methodName = "createRepositoryFiles";
        logger.info(className, "[" + methodName + "] Repository 파일 생성 시작");
        
        List<String> generatedFiles = new ArrayList<>();
        
        // 프로젝트 루트 디렉토리 찾기
        String currentDir = System.getProperty("user.dir");
        String projectRoot = currentDir;
        if (currentDir.contains("mbb-java")) {
            projectRoot = currentDir.substring(0, currentDir.indexOf("mbb-java"));
        }
        
        String baseDir = projectRoot + "/z-generated-files/src/main/java/" + basePackage.replace(".", "/");
        logger.info(className, "[" + methodName + "] 파일 생성 경로: " + baseDir);
        
        // JPA Repository 생성
        if ("JPA".equals(repositoryType)) {
            if (generateInterface) {
                createJpaRepositoryInterface(baseDir, basePackage, entityName, repositoryPackage, domainPackage, entityFields, generateCustomMethods, generatePagination, generateSearch);
                generatedFiles.add(entityName + "Repository.java");
            }
            
            if (generateImpl) {
                createJpaRepositoryImpl(baseDir, basePackage, entityName, repositoryPackage, domainPackage, entityFields, generateCustomMethods, generatePagination, generateSearch, generateAudit);
                generatedFiles.add(entityName + "RepositoryImpl.java");
            }
        }
        
        logger.info(className, "[" + methodName + "] Repository 파일 생성 완료: " + generatedFiles.size() + "개 파일");
        return generatedFiles;
    }
    
    /**
     * JPA Repository Interface를 생성합니다
     */
    private void createJpaRepositoryInterface(String baseDir, String basePackage, String entityName, 
                                            String repositoryPackage, String domainPackage, List<FieldInfo> entityFields,
                                            Boolean generateCustomMethods, Boolean generatePagination, Boolean generateSearch) throws IOException {
        String className = entityName + "Repository";
        String packageName = basePackage + "." + repositoryPackage;
        
        StringBuilder imports = new StringBuilder();
        imports.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        imports.append("import org.springframework.data.jpa.repository.Query;\n");
        imports.append("import org.springframework.data.repository.query.Param;\n");
        imports.append("import org.springframework.stereotype.Repository;\n");
        imports.append("import java.util.List;\n");
        imports.append("import java.util.Optional;\n");
        
        // 날짜 타입이 있는 경우 import 추가
        boolean hasDateType = entityFields.stream().anyMatch(field -> 
            field.getType().contains("LocalDate") || 
            field.getType().contains("LocalDateTime") || 
            field.getType().contains("LocalTime"));
        
        if (hasDateType) {
            imports.append("import java.time.*;\n");
        }
        
        // Entity와 Domain DTO import
        imports.append("import ").append(basePackage).append(".business.dc.entity.").append(entityName).append(";\n");
        imports.append("import ").append(basePackage).append(".").append(domainPackage).append(".").append(entityName).append("DomainDto;\n");
        
        StringBuilder methods = new StringBuilder();
        
        // 기본 CRUD 메서드들
        methods.append("    // 기본 CRUD 메서드\n");
        methods.append("    List<").append(entityName).append("> findAll();\n\n");
        methods.append("    Optional<").append(entityName).append("> findById(Long id);\n\n");
        methods.append("    ").append(entityName).append(" save(").append(entityName).append(" entity);\n\n");
        methods.append("    void deleteById(Long id);\n\n");
        methods.append("    long count();\n\n");
        
        // 커스텀 메서드들
        if (generateCustomMethods) {
            methods.append("    // 커스텀 메서드\n");
            
            // 이름으로 검색
            if (entityFields.stream().anyMatch(field -> "name".equals(field.getName()))) {
                methods.append("    List<").append(entityName).append("> findByName(String name);\n\n");
                methods.append("    Optional<").append(entityName).append("> findByNameIgnoreCase(String name);\n\n");
            }
            
            // 이메일로 검색
            if (entityFields.stream().anyMatch(field -> "email".equals(field.getName()))) {
                methods.append("    Optional<").append(entityName).append("> findByEmail(String email);\n\n");
            }
            
            // 사용자명으로 검색
            if (entityFields.stream().anyMatch(field -> "userName".equals(field.getName()))) {
                methods.append("    Optional<").append(entityName).append("> findByUserName(String userName);\n\n");
            }
        }
        
        // 페이징 지원
        if (generatePagination) {
            imports.append("import org.springframework.data.domain.Page;\n");
            imports.append("import org.springframework.data.domain.Pageable;\n");
            methods.append("    // 페이징 메서드\n");
            methods.append("    Page<").append(entityName).append("> findAll(Pageable pageable);\n\n");
        }
        
        // 검색 기능
        if (generateSearch) {
            methods.append("    // 검색 메서드\n");
            methods.append("    @Query(\"SELECT e FROM ").append(entityName).append(" e WHERE e.name LIKE %:keyword% OR e.email LIKE %:keyword%\")\n");
            methods.append("    List<").append(entityName).append("> searchByKeyword(@Param(\"keyword\") String keyword);\n\n");
        }
        
        String content = String.format(
            "package %s;\n\n" +
            "%s\n" +
            "/**\n" +
            " * %s Repository Interface\n" +
            " * \n" +
            " * @author AI Assistant\n" +
            " * @version 1.0\n" +
            " * @since 2024-01-01\n" +
            " */\n" +
            "@Repository\n" +
            "public interface %s extends JpaRepository<%s, Long> {\n\n" +
            "%s" +
            "}",
            packageName, imports.toString(), entityName, className, entityName, methods.toString()
        );
        
        writeFile(baseDir, repositoryPackage, className, content);
    }
    
    /**
     * JPA Repository Implementation을 생성합니다
     */
    private void createJpaRepositoryImpl(String baseDir, String basePackage, String entityName, 
                                       String repositoryPackage, String domainPackage, List<FieldInfo> entityFields,
                                       Boolean generateCustomMethods, Boolean generatePagination, Boolean generateSearch, Boolean generateAudit) throws IOException {
        String className = entityName + "RepositoryImpl";
        String packageName = basePackage + "." + repositoryPackage;
        
        StringBuilder imports = new StringBuilder();
        imports.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        imports.append("import org.springframework.stereotype.Service;\n");
        imports.append("import org.springframework.transaction.annotation.Transactional;\n");
        imports.append("import java.util.List;\n");
        imports.append("import java.util.Optional;\n");
        imports.append("import java.util.stream.Collectors;\n");
        
        // 날짜 타입이 있는 경우 import 추가
        boolean hasDateType = entityFields.stream().anyMatch(field -> 
            field.getType().contains("LocalDate") || 
            field.getType().contains("LocalDateTime") || 
            field.getType().contains("LocalTime"));
        
        if (hasDateType) {
            imports.append("import java.time.*;\n");
        }
        
        // Entity와 Domain DTO import
        imports.append("import ").append(basePackage).append(".business.dc.entity.").append(entityName).append(";\n");
        imports.append("import ").append(basePackage).append(".").append(domainPackage).append(".").append(entityName).append("DomainDto;\n");
        
        StringBuilder methods = new StringBuilder();
        
        // 기본 CRUD 메서드들
        methods.append("    @Override\n");
        methods.append("    public List<").append(entityName).append("DomainDto> findAll() {\n");
        methods.append("        return ").append(entityName.toLowerCase()).append("Repository.findAll().stream()\n");
        methods.append("                .map(this::convertToDomainDto)\n");
        methods.append("                .collect(Collectors.toList());\n");
        methods.append("    }\n\n");
        
        methods.append("    @Override\n");
        methods.append("    public Optional<").append(entityName).append("DomainDto> findById(Long id) {\n");
        methods.append("        return ").append(entityName.toLowerCase()).append("Repository.findById(id)\n");
        methods.append("                .map(this::convertToDomainDto);\n");
        methods.append("    }\n\n");
        
        methods.append("    @Override\n");
        methods.append("    public ").append(entityName).append("DomainDto save(").append(entityName).append("DomainDto domainDto) {\n");
        methods.append("        ").append(entityName).append(" entity = convertToEntity(domainDto);\n");
        methods.append("        ").append(entityName).append(" savedEntity = ").append(entityName.toLowerCase()).append("Repository.save(entity);\n");
        methods.append("        return convertToDomainDto(savedEntity);\n");
        methods.append("    }\n\n");
        
        methods.append("    @Override\n");
        methods.append("    public void deleteById(Long id) {\n");
        methods.append("        ").append(entityName.toLowerCase()).append("Repository.deleteById(id);\n");
        methods.append("    }\n\n");
        
        methods.append("    @Override\n");
        methods.append("    public long count() {\n");
        methods.append("        return ").append(entityName.toLowerCase()).append("Repository.count();\n");
        methods.append("    }\n\n");
        
        // 변환 메서드들
        methods.append("    // Entity ↔ Domain DTO 변환 메서드\n");
        methods.append("    private ").append(entityName).append("DomainDto convertToDomainDto(").append(entityName).append(" entity) {\n");
        methods.append("        if (entity == null) return null;\n");
        methods.append("        \n");
        methods.append("        ").append(entityName).append("DomainDto dto = new ").append(entityName).append("DomainDto();\n");
        
        for (FieldInfo field : entityFields) {
            methods.append("        dto.set").append(capitalize(field.getName())).append("(entity.get").append(capitalize(field.getName())).append("());\n");
        }
        
        methods.append("        return dto;\n");
        methods.append("    }\n\n");
        
        methods.append("    private ").append(entityName).append(" convertToEntity(").append(entityName).append("DomainDto dto) {\n");
        methods.append("        if (dto == null) return null;\n");
        methods.append("        \n");
        methods.append("        ").append(entityName).append(" entity = new ").append(entityName).append("();\n");
        
        for (FieldInfo field : entityFields) {
            methods.append("        entity.set").append(capitalize(field.getName())).append("(dto.get").append(capitalize(field.getName())).append("());\n");
        }
        
        methods.append("        return entity;\n");
        methods.append("    }\n\n");
        
        String content = String.format(
            "package %s;\n\n" +
            "%s\n" +
            "/**\n" +
            " * %s Repository Implementation\n" +
            " * \n" +
            " * @author AI Assistant\n" +
            " * @version 1.0\n" +
            " * @since 2024-01-01\n" +
            " */\n" +
            "@Service\n" +
            "@Transactional\n" +
            "public class %s implements %sRepository {\n\n" +
            "    @Autowired\n" +
            "    private %sRepository %sRepository;\n\n" +
            "%s" +
            "}",
            packageName, imports.toString(), entityName, className, entityName, entityName, entityName.toLowerCase(), methods.toString()
        );
        
        writeFile(baseDir, repositoryPackage, className, content);
    }
    
    /**
     * 파일을 생성합니다
     */
    private void writeFile(String baseDir, String packageDir, String className, String content) throws IOException {
        logger.info(className, "writeFile 시작 - baseDir: " + baseDir + ", packageDir: " + packageDir + ", className: " + className);
        
        // 전체 디렉토리 경로 생성
        Path fullDirPath = Paths.get(baseDir, packageDir.replace(".", "/"));
        
        logger.info(className, "디렉토리 생성 시도: " + fullDirPath.toAbsolutePath());
        
        // 디렉토리가 존재하는지 확인
        if (!Files.exists(fullDirPath)) {
            logger.info(className, "디렉토리가 존재하지 않음, 생성 시도");
            Files.createDirectories(fullDirPath);
            logger.info(className, "디렉토리 생성 완료: " + fullDirPath.toAbsolutePath());
        } else {
            logger.info(className, "디렉토리가 이미 존재함: " + fullDirPath.toAbsolutePath());
        }
        
        // 파일 경로 생성
        Path filePath = fullDirPath.resolve(className + ".java");
        logger.info(className, "파일 경로: " + filePath.toAbsolutePath());
        
        // 파일 생성
        try {
            logger.info(className, "파일 생성 시도");
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                writer.write(content);
                logger.info(className, "파일 내용 작성 완료");
            }
            logger.info(className, "파일 생성 완료: " + filePath.toAbsolutePath());
            
            // 파일이 실제로 생성되었는지 확인
            if (Files.exists(filePath)) {
                logger.info(className, "파일 존재 확인됨: " + filePath.toAbsolutePath());
                logger.info(className, "파일 크기: " + Files.size(filePath) + " bytes");
            } else {
                logger.error(className, "파일이 생성되지 않음: " + filePath.toAbsolutePath());
            }
            
        } catch (Exception e) {
            logger.error(className, "파일 생성 중 오류 발생: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 첫 글자를 대문자로 변환합니다
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * 필드 정보를 담는 클래스
     */
    private static class FieldInfo {
        private String name;
        private String type;
        private String columnName;
        private String remarks;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getColumnName() { return columnName; }
        public void setColumnName(String columnName) { this.columnName = columnName; }
        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }
} 