/**
 * (@)# CodeGenerationTaskDDTO.java
 * Copyright SKAX Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 프로그램 설명 : 코드 생성 작업 DTO 클래스
 * 
 * 주요 기능:
 * - 코드 생성 작업 정보 저장
 * - 작업 상태 관리
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
package com.skax.eatool.mbb.dc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 코드 생성 작업 DTO 클래스
 * 
 * AI 코딩 워크플로우에서 코드 생성 작업을 처리하기 위한 DTO입니다.
 * 작업 정보, 상태, 결과 등을 포함합니다.
 * 
 * @author SKAX Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CodeGenerationTaskDDTO extends NewAbstractDTO {
    
    /** 작업 ID */
    @JsonProperty("taskId")
    private String taskId_;
    
    /** 작업명 */
    @JsonProperty("taskName")
    private String taskName_;
    
    /** 작업 설명 */
    @JsonProperty("description")
    private String description_;
    
    /** 테이블 정의 */
    @JsonProperty("tableDefinition")
    private TableDefinitionDDTO tableDefinition_;
    
    /** 생성할 작업 목록 */
    @JsonProperty("tasks")
    private List<String> tasks_;
    
    /** 작업 상태 */
    @JsonProperty("status")
    private String status_;
    
    /** 생성된 파일 목록 */
    @JsonProperty("generatedFiles")
    private List<String> generatedFiles_;
    
    /** 생성 시작 시간 */
    @JsonProperty("startTime")
    private LocalDateTime startTime_;
    
    /** 생성 완료 시간 */
    @JsonProperty("endTime")
    private LocalDateTime endTime_;
    
    /** 오류 메시지 */
    @JsonProperty("errorMessage")
    private String errorMessage_;
    
    /**
     * 기본 생성자
     */
    public CodeGenerationTaskDDTO() {
        super();
    }
    
    /**
     * 작업 ID를 반환한다.
     * 
     * @return 작업 ID
     */
    public String getTaskId() {
        return taskId_;
    }
    
    /**
     * 작업 ID를 설정한다.
     * 
     * @param taskId 작업 ID
     */
    public void setTaskId(String taskId) {
        this.taskId_ = taskId;
    }
    
    /**
     * 작업명을 반환한다.
     * 
     * @return 작업명
     */
    public String getTaskName() {
        return taskName_;
    }
    
    /**
     * 작업명을 설정한다.
     * 
     * @param taskName 작업명
     */
    public void setTaskName(String taskName) {
        this.taskName_ = taskName;
    }
    
    /**
     * 작업 설명을 반환한다.
     * 
     * @return 작업 설명
     */
    public String getDescription() {
        return description_;
    }
    
    /**
     * 작업 설명을 설정한다.
     * 
     * @param description 작업 설명
     */
    public void setDescription(String description) {
        this.description_ = description;
    }
    
    /**
     * 테이블 정의를 반환한다.
     * 
     * @return 테이블 정의
     */
    public TableDefinitionDDTO getTableDefinition() {
        return tableDefinition_;
    }
    
    /**
     * 테이블 정의를 설정한다.
     * 
     * @param tableDefinition 테이블 정의
     */
    public void setTableDefinition(TableDefinitionDDTO tableDefinition) {
        this.tableDefinition_ = tableDefinition;
    }
    
    /**
     * 생성할 작업 목록을 반환한다.
     * 
     * @return 생성할 작업 목록
     */
    public List<String> getTasks() {
        return tasks_;
    }
    
    /**
     * 생성할 작업 목록을 설정한다.
     * 
     * @param tasks 생성할 작업 목록
     */
    public void setTasks(List<String> tasks) {
        this.tasks_ = tasks;
    }
    
    /**
     * 작업 상태를 반환한다.
     * 
     * @return 작업 상태
     */
    public String getStatus() {
        return status_;
    }
    
    /**
     * 작업 상태를 설정한다.
     * 
     * @param status 작업 상태
     */
    public void setStatus(String status) {
        this.status_ = status;
    }
    
    /**
     * 생성된 파일 목록을 반환한다.
     * 
     * @return 생성된 파일 목록
     */
    public List<String> getGeneratedFiles() {
        return generatedFiles_;
    }
    
    /**
     * 생성된 파일 목록을 설정한다.
     * 
     * @param generatedFiles 생성된 파일 목록
     */
    public void setGeneratedFiles(List<String> generatedFiles) {
        this.generatedFiles_ = generatedFiles;
    }
    
    /**
     * 생성 시작 시간을 반환한다.
     * 
     * @return 생성 시작 시간
     */
    public LocalDateTime getStartTime() {
        return startTime_;
    }
    
    /**
     * 생성 시작 시간을 설정한다.
     * 
     * @param startTime 생성 시작 시간
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime_ = startTime;
    }
    
    /**
     * 생성 완료 시간을 반환한다.
     * 
     * @return 생성 완료 시간
     */
    public LocalDateTime getEndTime() {
        return endTime_;
    }
    
    /**
     * 생성 완료 시간을 설정한다.
     * 
     * @param endTime 생성 완료 시간
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime_ = endTime;
    }
    
    /**
     * 오류 메시지를 반환한다.
     * 
     * @return 오류 메시지
     */
    public String getErrorMessage() {
        return errorMessage_;
    }
    
    /**
     * 오류 메시지를 설정한다.
     * 
     * @param errorMessage 오류 메시지
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage_ = errorMessage;
    }
} 