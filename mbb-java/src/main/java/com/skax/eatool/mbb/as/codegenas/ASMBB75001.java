/**
 * (@)# ASMBB75001.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : AI 코딩 워크플로우 Application Service
 * 
 * 주요 기능:
 * - 코드 생성 작업 관리
 * - 테이블 정의 처리
 * - 파일 생성 및 다운로드
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
package com.skax.eatool.mbb.as.codegenas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.pc.codegenpc.PCCodeGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 코드 생성 Application Service
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class ASMBB75001 {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private PCCodeGeneration pcCodeGeneration;
    
    /**
     * 코드 생성을 실행합니다.
     *
     * @param requestData 요청 데이터
     * @return 실행 결과
     */
    @Transactional
    public NewKBData execute(NewKBData requestData) {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== ASMBB75001.execute START ===");
            logger.info(className, "코드 생성 서비스 시작: " + requestData.getTaskName());

            // 입력값 검증
            if (!validateCodeGenerationRequest(requestData)) {
                logger.error(className, "코드 생성 요청 입력값 검증 실패");
                NewKBData errorResponse = new NewKBData();
                errorResponse.setErrorMessage("코드 생성 요청 입력값 검증 실패");
                return errorResponse;
            }

            // PC 호출하여 코드 생성
            NewKBData result = pcCodeGeneration.execute(requestData);

            logger.info(className, "코드 생성 서비스 완료: " + requestData.getTaskName());
            logger.info(className, "=== ASMBB75001.execute END ===");
            return result;

        } catch (Exception e) {
            logger.error(className, "코드 생성 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASMBB75001.execute ERROR ===");
            NewKBData errorResponse = new NewKBData();
            errorResponse.setErrorMessage("코드 생성 서비스 중 오류가 발생했습니다: " + e.getMessage());
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