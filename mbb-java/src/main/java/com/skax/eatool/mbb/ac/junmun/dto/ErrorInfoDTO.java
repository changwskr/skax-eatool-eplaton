package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 오류 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorInfoDTO extends NewAbstractDTO {
    
    /**
     * 오류 코드 - 시스템에서 정의한 오류 코드
     */
    private String errorCode;
    
    /**
     * 오류 메시지 - 사용자에게 표시할 오류 메시지
     */
    private String errorMessage;
    
    /**
     * 오류 상세 정보 - 개발자를 위한 상세 오류 정보
     */
    private String errorDetail;
    
    /**
     * 오류 발생 시간 - 오류가 발생한 시간 (YYYYMMDDHHmmssSSS 형식)
     */
    private String errorTime;
    
    /**
     * 오류 발생 시스템 - 오류가 발생한 시스템 코드
     */
    private String errorSystem;
    
    /**
     * 오류 발생 모듈 - 오류가 발생한 모듈명
     */
    private String errorModule;
    
    /**
     * 오류 발생 함수 - 오류가 발생한 함수명
     */
    private String errorFunction;
    
    /**
     * 오류 레벨 - '1'=경고, '2'=오류, '3'=심각
     */
    private String errorLevel;
    
    /**
     * 오류 타입 - 'S'=시스템, 'B'=업무, 'V'=검증
     */
    private String errorType;
    
    /**
     * 오류 해결 방법 - 오류 해결을 위한 가이드
     */
    private String errorSolution;
    
    /**
     * 오류 추적 정보 - 오류 추적을 위한 추가 정보
     */
    private String errorTrace;
} 