package com.skax.eatool.mbb.dc.dtoas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            
            // DTO 파일 생성
            String[] generatedFiles = createDtoFiles(basePackage, entityName, requestPackage, responsePackage, domainPackage);
            
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
     * @return 생성된 파일 목록
     */
    private String[] createDtoFiles(String basePackage, String entityName, String requestPackage, String responsePackage, String domainPackage) throws IOException {
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
        createRequestDto(baseDir, basePackage, entityName, requestPackage);
        
        // ResponseDto 생성
        createResponseDto(baseDir, basePackage, entityName, responsePackage);
        
        // DomainDto 생성
        createDomainDto(baseDir, basePackage, entityName, domainPackage);
        
        logger.info(className, "[" + methodName + "] DTO 파일 생성 완료");
        return files;
    }
    
    /**
     * RequestDto 파일을 생성합니다
     */
    private void createRequestDto(String baseDir, String basePackage, String entityName, String requestPackage) throws IOException {
        String className = entityName + "RequestDto";
        String packageName = basePackage + "." + requestPackage;
        
        String content = String.format(
            "package %s;\n\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import lombok.AllArgsConstructor;\n\n" +
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
            "    // TODO: 요청 필드들을 추가하세요\n" +
            "    \n" +
            "}",
            packageName, entityName, className
        );
        
        writeFile(baseDir, requestPackage, className, content);
    }
    
    /**
     * ResponseDto 파일을 생성합니다
     */
    private void createResponseDto(String baseDir, String basePackage, String entityName, String responsePackage) throws IOException {
        String className = entityName + "ResponseDto";
        String packageName = basePackage + "." + responsePackage;
        
        String content = String.format(
            "package %s;\n\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import lombok.AllArgsConstructor;\n\n" +
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
            "    // TODO: 응답 필드들을 추가하세요\n" +
            "    \n" +
            "}",
            packageName, entityName, className
        );
        
        writeFile(baseDir, responsePackage, className, content);
    }
    
    /**
     * DomainDto 파일을 생성합니다
     */
    private void createDomainDto(String baseDir, String basePackage, String entityName, String domainPackage) throws IOException {
        String className = entityName + "DomainDto";
        String packageName = basePackage + "." + domainPackage;
        
        String content = String.format(
            "package %s;\n\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import lombok.AllArgsConstructor;\n\n" +
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
            "    // TODO: 도메인 필드들을 추가하세요\n" +
            "    \n" +
            "}",
            packageName, entityName, className
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
} 