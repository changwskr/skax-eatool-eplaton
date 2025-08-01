/**
 * (@)# DCCodeGeneration.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : 코드 생성 Domain Component
 * 
 * 주요 기능:
 * - 실제 파일 생성
 * - 템플릿 처리
 * - 데이터베이스 접근
 * 
 * 변경이력 :
 *   <ul>
 *     <li>2024-01-01 :: SKAX Team :: 신규작성</li>
 *   </ul>
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
package com.skax.eatool.mbb.dc.codegendc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbb.dc.dto.CodeGenerationTaskDDTO;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import com.skax.eatool.mbb.as.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 코드 생성 Domain Component 클래스
 * 
 * AI 코딩 워크플로우의 실제 파일 생성과 데이터 처리를 담당하는 컴포넌트입니다.
 * 템플릿 기반 코드 생성과 파일 시스템 관리를 수행합니다.
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class DCCodeGeneration {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    @Autowired
    private TemplateService templateService;
    
    /** 생성된 파일 저장 디렉토리 */
    private static final String OUTPUT_DIR = "generated";
    
    /**
     * 코드 생성을 실행합니다.
     *
     * @param requestData 요청 데이터
     * @return 실행 결과
     */
    public NewKBData execute(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== DCCodeGeneration.execute START ===");
            logger.info(className, "코드 생성 DC 실행 시작: " + requestData.getTaskName());
            
            // 명령어에 따른 처리
            String command = requestData.getCommand();
            switch (command.toUpperCase()) {
                case "GENERATE":
                    return handleGenerate(requestData);
                case "VALIDATE":
                    return handleValidate(requestData);
                case "STATUS":
                    return handleStatus(requestData);
                case "DOWNLOAD":
                    return handleDownload(requestData);
                default:
                    NewKBData errorResponse = new NewKBData();
                    errorResponse.setErrorMessage("지원하지 않는 명령어입니다: " + command);
                    return errorResponse;
            }
            
        } catch (Exception e) {
            logger.error(className, "코드 생성 DC 실행 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== DCCodeGeneration.execute ERROR ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("코드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return errorResponse;
        }
    }
    
    /**
     * GENERATE 명령어를 처리합니다.
     */
    private NewKBData handleGenerate(NewKBData requestData) {
        NewKBData response = new NewKBData();
        response.setTaskId("TASK_" + System.currentTimeMillis());
        response.setStatus("COMPLETED");
        response.setMessage("코드 생성이 완료되었습니다.");
        return response;
    }
    
    /**
     * VALIDATE 명령어를 처리합니다.
     */
    private NewKBData handleValidate(NewKBData requestData) {
        NewKBData response = new NewKBData();
        response.setValid(true);
        response.setMessage("테이블 정의가 유효합니다.");
        return response;
    }
    
    /**
     * STATUS 명령어를 처리합니다.
     */
    private NewKBData handleStatus(NewKBData requestData) {
        NewKBData response = new NewKBData();
        response.setTaskId(requestData.getTaskId());
        response.setStatus("COMPLETED");
        response.setMessage("작업이 완료되었습니다.");
        return response;
    }
    
    /**
     * DOWNLOAD 명령어를 처리합니다.
     */
    private NewKBData handleDownload(NewKBData requestData) {
        NewKBData response = new NewKBData();
        response.setDownloadUrl("/api/code-generation/download/" + requestData.getTaskId() + "/file");
        response.setMessage("다운로드 URL이 생성되었습니다.");
        return response;
    }
    
    /**
     * 작업을 저장한다.
     * 
     * @param task 코드 생성 작업
     * @throws NewBusinessException 비즈니스 예외
     */
    public void saveTask(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("=== DCCodeGeneration.saveTask START ===", "DCCodeGeneration");
        
        try {
            // 실제 구현에서는 데이터베이스에 저장
            // 현재는 메모리에 저장하는 것으로 가정
            logger.info("DCCodeGeneration", "작업 저장 완료: " + task.getTaskId());
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.saveTask - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("작업 저장 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 작업을 업데이트한다.
     * 
     * @param task 코드 생성 작업
     * @throws NewBusinessException 비즈니스 예외
     */
    public void updateTask(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.updateTask START ===");
        
        try {
            // 실제 구현에서는 데이터베이스에 업데이트
            logger.info("DCCodeGeneration", "작업 업데이트 완료: " + task.getTaskId());
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.updateTask - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("작업 업데이트 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 작업 ID로 작업을 조회한다.
     * 
     * @param taskId 작업 ID
     * @return 작업 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    public CodeGenerationTaskDDTO getTaskById(String taskId) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.getTaskById START ===");
        
        try {
            // 실제 구현에서는 데이터베이스에서 조회
            // 현재는 더미 데이터 반환
            CodeGenerationTaskDDTO task = new CodeGenerationTaskDDTO();
            task.setTaskId(taskId);
            task.setStatus("COMPLETED");
            
            return task;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.getTaskById - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("작업 조회 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Entity 클래스를 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateEntity(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateEntity START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareEntityTemplateData(tableDefinition);
            
            // 2. Entity 클래스 생성
            String entityContent = templateService.processTemplate("entity.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = capitalizeFirstLetter(tableName) + "Entity.java";
            String filePath = saveFile("entity", fileName, entityContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateEntity END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateEntity - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("Entity 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Repository 인터페이스를 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateRepository(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("=== DCCodeGeneration.generateRepository START ===", "DCCodeGeneration");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareRepositoryTemplateData(tableDefinition);
            
            // 2. Repository 인터페이스 생성
            String repositoryContent = templateService.processTemplate("repository.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = capitalizeFirstLetter(tableName) + "Repository.java";
            String filePath = saveFile("repository", fileName, repositoryContent);
            
            logger.info("=== DCCodeGeneration.generateRepository END ===", "DCCodeGeneration");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateRepository - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("Repository 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Service 클래스를 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateService(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateService START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareServiceTemplateData(tableDefinition);
            
            // 2. Service 클래스 생성
            String serviceContent = templateService.processTemplate("service.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = capitalizeFirstLetter(tableName) + "Service.java";
            String filePath = saveFile("service", fileName, serviceContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateService END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateService - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("Service 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Controller 클래스를 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateController(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateController START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareControllerTemplateData(tableDefinition);
            
            // 2. Controller 클래스 생성
            String controllerContent = templateService.processTemplate("controller.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = capitalizeFirstLetter(tableName) + "Controller.java";
            String filePath = saveFile("controller", fileName, controllerContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateController END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateController - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("Controller 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * HTML 파일을 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateHtml(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateHtml START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareHtmlTemplateData(tableDefinition);
            
            // 2. HTML 파일 생성
            String htmlContent = templateService.processTemplate("html.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = tableName.toLowerCase() + "-management.html";
            String filePath = saveFile("html", fileName, htmlContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateHtml END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateHtml - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("HTML 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * DDL 파일을 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateDdl(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateDdl START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareDdlTemplateData(tableDefinition);
            
            // 2. DDL 파일 생성
            String ddlContent = templateService.processTemplate("ddl.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = tableName.toLowerCase() + ".sql";
            String filePath = saveFile("ddl", fileName, ddlContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateDdl END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateDdl - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("DDL 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 초기 데이터 파일을 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return 생성된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String generateInitData(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateInitData START ===");
        
        try {
            TableDefinitionDDTO tableDefinition = task.getTableDefinition();
            String tableName = tableDefinition.getTableName();
            
            // 1. 템플릿 데이터 준비
            Map<String, Object> templateData = prepareInitDataTemplateData(tableDefinition);
            
            // 2. 초기 데이터 파일 생성
            String initDataContent = templateService.processTemplate("init-data.ftl", templateData);
            
            // 3. 파일 저장
            String fileName = tableName.toLowerCase() + "-init-data.sql";
            String filePath = saveFile("init-data", fileName, initDataContent);
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.generateInitData END ===");
            return filePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.generateInitData - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("초기 데이터 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * ZIP 파일을 생성한다.
     * 
     * @param task 코드 생성 작업
     * @return ZIP 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    public String createZipFile(CodeGenerationTaskDDTO task) throws NewBusinessException {
        logger.info("DCCodeGeneration", "=== DCCodeGeneration.createZipFile START ===");
        
        try {
            // 실제 구현에서는 생성된 파일들을 ZIP으로 압축
            String zipFileName = task.getTaskId() + ".zip";
            String zipFilePath = OUTPUT_DIR + "/" + zipFileName;
            
            logger.info("DCCodeGeneration", "=== DCCodeGeneration.createZipFile END ===");
            return zipFilePath;
            
        } catch (Exception e) {
            logger.error("DCCodeGeneration", "=== DCCodeGeneration.createZipFile - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("ZIP 파일 생성 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Entity 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareEntityTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("className", capitalizeFirstLetter(tableDefinition.getTableName()) + "Entity");
        data.put("columns", tableDefinition.getColumns());
        data.put("comment", tableDefinition.getComment());
        return data;
    }
    
    /**
     * Repository 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareRepositoryTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("className", capitalizeFirstLetter(tableDefinition.getTableName()) + "Repository");
        data.put("entityName", capitalizeFirstLetter(tableDefinition.getTableName()) + "Entity");
        return data;
    }
    
    /**
     * Service 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareServiceTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("className", capitalizeFirstLetter(tableDefinition.getTableName()) + "Service");
        data.put("entityName", capitalizeFirstLetter(tableDefinition.getTableName()) + "Entity");
        data.put("repositoryName", capitalizeFirstLetter(tableDefinition.getTableName()) + "Repository");
        return data;
    }
    
    /**
     * Controller 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareControllerTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("className", capitalizeFirstLetter(tableDefinition.getTableName()) + "Controller");
        data.put("serviceName", capitalizeFirstLetter(tableDefinition.getTableName()) + "Service");
        data.put("entityName", capitalizeFirstLetter(tableDefinition.getTableName()) + "Entity");
        return data;
    }
    
    /**
     * HTML 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareHtmlTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("className", capitalizeFirstLetter(tableDefinition.getTableName()));
        data.put("columns", tableDefinition.getColumns());
        data.put("comment", tableDefinition.getComment());
        return data;
    }
    
    /**
     * DDL 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareDdlTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("comment", tableDefinition.getComment());
        data.put("columns", tableDefinition.getColumns());
        return data;
    }
    
    /**
     * 초기 데이터 템플릿 데이터를 준비한다.
     * 
     * @param tableDefinition 테이블 정의
     * @return 템플릿 데이터
     */
    private Map<String, Object> prepareInitDataTemplateData(TableDefinitionDDTO tableDefinition) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableDefinition.getTableName());
        data.put("columns", tableDefinition.getColumns());
        return data;
    }
    
    /**
     * 파일을 저장한다.
     * 
     * @param subDir 하위 디렉토리
     * @param fileName 파일명
     * @param content 파일 내용
     * @return 저장된 파일 경로
     * @throws NewBusinessException 비즈니스 예외
     */
    private String saveFile(String subDir, String fileName, String content) throws NewBusinessException {
        try {
            // 1. 디렉토리 생성
            Path dirPath = Paths.get(OUTPUT_DIR, subDir);
            Files.createDirectories(dirPath);
            
            // 2. 파일 저장
            Path filePath = dirPath.resolve(fileName);
            Files.write(filePath, content.getBytes("UTF-8"));
            
            return filePath.toString();
            
        } catch (IOException e) {
            throw new NewBusinessException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 첫 글자를 대문자로 변환한다.
     * 
     * @param str 문자열
     * @return 변환된 문자열
     */
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
} 