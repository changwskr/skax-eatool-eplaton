/**
 * (@)# PCCodeGeneration.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : 코드 생성 Process Component
 * 
 * 주요 기능:
 * - 코드 생성 작업 처리
 * - 템플릿 기반 코드 생성
 * - 파일 시스템 관리
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
package com.skax.eatool.mbb.pc.codegenpc;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.codegendc.DCCodeGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 코드 생성 Process Component
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class PCCodeGeneration {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private DCCodeGeneration dcCodeGeneration;
    
    /**
     * 코드 생성을 실행합니다.
     *
     * @param requestData 요청 데이터
     * @return 실행 결과
     */
    public NewKBData execute(NewKBData requestData) {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCCodeGeneration.execute START ===");
            logger.info(className, "코드 생성 프로세스 시작: " + requestData.getTaskName());

            // 비즈니스 규칙 검증
            if (!validateCodeGenerationRequest(requestData)) {
                logger.error(className, "코드 생성 요청 비즈니스 규칙 검증 실패");
                NewKBData errorResponse = new NewKBData();
                errorResponse.setErrorMessage("코드 생성 요청 검증 실패");
                return errorResponse;
            }

            // DC 호출하여 코드 생성
            NewKBData result = dcCodeGeneration.execute(requestData);

            logger.info(className, "코드 생성 프로세스 완료: " + requestData.getTaskName());
            logger.info(className, "=== PCCodeGeneration.execute END ===");
            return result;

        } catch (Exception e) {
            logger.error(className, "코드 생성 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCCodeGeneration.execute ERROR ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("코드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return errorResponse;
        }
    }
    
    /**
     * 코드 생성 요청을 검증합니다.
     *
     * @param requestData 요청 데이터
     * @return 검증 결과
     */
    private boolean validateCodeGenerationRequest(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        // 기본 검증
        if (requestData == null) {
            logger.error(className, "요청 데이터가 null입니다.");
            return false;
        }
        
        // 명령어 검증
        String command = requestData.getCommand();
        if (command == null || command.trim().isEmpty()) {
            logger.error(className, "명령어가 비어있습니다.");
            return false;
        }
        
        // 명령어별 검증
        switch (command.toUpperCase()) {
            case "GENERATE":
                return validateGenerateCommand(requestData);
            case "VALIDATE":
                return validateValidateCommand(requestData);
            case "STATUS":
                return validateStatusCommand(requestData);
            case "DOWNLOAD":
                return validateDownloadCommand(requestData);
            default:
                logger.error(className, "지원하지 않는 명령어입니다: " + command);
                return false;
        }
    }
    
    /**
     * GENERATE 명령어를 검증합니다.
     *
     * @param requestData 요청 데이터
     * @return 검증 결과
     */
    private boolean validateGenerateCommand(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        // 작업명 검증
        if (requestData.getTaskName() == null || requestData.getTaskName().trim().isEmpty()) {
            logger.error(className, "작업명이 비어있습니다.");
            return false;
        }
        
        // 테이블 정의 검증
        if (requestData.getTableDefinition() == null) {
            logger.error(className, "테이블 정의가 비어있습니다.");
            return false;
        }
        
        // 작업 목록 검증
        if (requestData.getTasks() == null || requestData.getTasks().isEmpty()) {
            logger.error(className, "작업 목록이 비어있습니다.");
            return false;
        }
        
        return true;
    }
    
    /**
     * VALIDATE 명령어를 검증합니다.
     *
     * @param requestData 요청 데이터
     * @return 검증 결과
     */
    private boolean validateValidateCommand(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        // 테이블 정의 검증
        if (requestData.getTableDefinition() == null) {
            logger.error(className, "테이블 정의가 비어있습니다.");
            return false;
        }
        
        return true;
    }
    
    /**
     * STATUS 명령어를 검증합니다.
     *
     * @param requestData 요청 데이터
     * @return 검증 결과
     */
    private boolean validateStatusCommand(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        // 작업 ID 검증
        if (requestData.getTaskId() == null || requestData.getTaskId().trim().isEmpty()) {
            logger.error(className, "작업 ID가 비어있습니다.");
            return false;
        }
        
        return true;
    }
    
    /**
     * DOWNLOAD 명령어를 검증합니다.
     *
     * @param requestData 요청 데이터
     * @return 검증 결과
     */
    private boolean validateDownloadCommand(NewKBData requestData) {
        String className = this.getClass().getSimpleName();
        
        // 작업 ID 검증
        if (requestData.getTaskId() == null || requestData.getTaskId().trim().isEmpty()) {
            logger.error(className, "작업 ID가 비어있습니다.");
            return false;
        }
        
        return true;
    }
} 