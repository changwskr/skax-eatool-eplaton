package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Optional;

/**
 * 완전한 전문 메시지 DTO
 * 모든 개별 DTO들을 포함하는 통합 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteMessageDTO extends NewAbstractDTO {
    
    /**
     * 표준 전문 헤더 영역
     */
    private StandardHeaderDTO standardHeader;
    
    /**
     * 시스템/채널 전송 정보
     */
    private TransmissionInfoDTO transmissionInfo;
    
    /**
     * 거래/트랜잭션 정보
     */
    private TransactionInfoDTO transactionInfo;
    
    /**
     * 승인자/책임자 정보
     */
    private ApprovalInfoDTO approvalInfo;
    
    /**
     * 전문 제어 정보
     */
    private ControlInfoDTO controlInfo;
    
    /**
     * 단말 정보
     */
    private TerminalInfoDTO terminalInfo;
    
    /**
     * 대외거래 정보
     */
    private ExternalInfoDTO externalInfo;
    
    /**
     * 고객정보 보호
     */
    private SecurityInfoDTO securityInfo;
    
    /**
     * 기타 정보
     */
    private MiscInfoDTO miscInfo;
    
    /**
     * 업무 데이터 영역 - Map<String,Object>로 유연한 구조 지원
     */
    private Map<String, Object> businessData;
    
    /**
     * 전문 종료부
     */
    private FooterDTO footer;
    
    /**
     * 오류 정보
     */
    private ErrorInfoDTO errorInfo;
    
    // 수동으로 추가한 getter/setter 메서드들
    public StandardHeaderDTO getStandardHeader() { return standardHeader; }
    public void setStandardHeader(StandardHeaderDTO standardHeader) { this.standardHeader = standardHeader; }
    
    public TransmissionInfoDTO getTransmissionInfo() { return transmissionInfo; }
    public void setTransmissionInfo(TransmissionInfoDTO transmissionInfo) { this.transmissionInfo = transmissionInfo; }
    
    public TransactionInfoDTO getTransactionInfo() { return transactionInfo; }
    public void setTransactionInfo(TransactionInfoDTO transactionInfo) { this.transactionInfo = transactionInfo; }
    
    public ApprovalInfoDTO getApprovalInfo() { return approvalInfo; }
    public void setApprovalInfo(ApprovalInfoDTO approvalInfo) { this.approvalInfo = approvalInfo; }
    
    public ControlInfoDTO getControlInfo() { return controlInfo; }
    public void setControlInfo(ControlInfoDTO controlInfo) { this.controlInfo = controlInfo; }
    
    public TerminalInfoDTO getTerminalInfo() { return terminalInfo; }
    public void setTerminalInfo(TerminalInfoDTO terminalInfo) { this.terminalInfo = terminalInfo; }
    
    public ExternalInfoDTO getExternalInfo() { return externalInfo; }
    public void setExternalInfo(ExternalInfoDTO externalInfo) { this.externalInfo = externalInfo; }
    
    public SecurityInfoDTO getSecurityInfo() { return securityInfo; }
    public void setSecurityInfo(SecurityInfoDTO securityInfo) { this.securityInfo = securityInfo; }
    
    public MiscInfoDTO getMiscInfo() { return miscInfo; }
    public void setMiscInfo(MiscInfoDTO miscInfo) { this.miscInfo = miscInfo; }
    
    public Map<String, Object> getBusinessData() { return businessData; }
    public void setBusinessData(Map<String, Object> businessData) { this.businessData = businessData; }
    
    public FooterDTO getFooter() { return footer; }
    public void setFooter(FooterDTO footer) { this.footer = footer; }
    
    public ErrorInfoDTO getErrorInfo() { return errorInfo; }
    public void setErrorInfo(ErrorInfoDTO errorInfo) { this.errorInfo = errorInfo; }
    
    // ========================================
    // 유틸리티 메서드
    // ========================================
    
    /**
     * 업무 데이터를 특정 DTO로 변환하여 반환
     *
     * @param clazz 변환할 DTO 클래스
     * @param <T> DTO 타입
     * @return Optional로 감싼 DTO 객체
     */
    public <T> Optional<T> getBusinessDataAs(Class<T> clazz) {
        if (businessData == null) {
            return Optional.empty();
        }
        
        try {
            // ObjectMapper를 사용하여 Map을 DTO로 변환
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            T dto = mapper.convertValue(businessData, clazz);
            return Optional.of(dto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * 업무 데이터를 특정 DTO로 설정
     *
     * @param dto 설정할 DTO 객체
     * @param <T> DTO 타입
     */
    public <T> void setBusinessDataFrom(T dto) {
        if (dto == null) {
            this.businessData = null;
            return;
        }
        
        try {
            // ObjectMapper를 사용하여 DTO를 Map으로 변환
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            this.businessData = mapper.convertValue(dto, Map.class);
        } catch (Exception e) {
            // 변환 실패 시 null로 설정
            this.businessData = null;
        }
    }
    
    /**
     * 업무 데이터에서 특정 키의 값을 반환
     *
     * @param key 키
     * @return Optional로 감싼 값
     */
    public Optional<Object> getBusinessDataValue(String key) {
        if (businessData == null || key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(businessData.get(key));
    }
    
    /**
     * 업무 데이터에 키-값 쌍을 설정
     *
     * @param key 키
     * @param value 값
     */
    public void setBusinessDataValue(String key, Object value) {
        if (businessData == null) {
            this.businessData = new java.util.HashMap<>();
        }
        this.businessData.put(key, value);
    }
    
    /**
     * 오류 정보를 설정합니다.
     *
     * @param errorCode 오류 코드
     * @param errorMessage 오류 메시지
     * @param errorDetail 오류 상세 정보
     */
    public void setErrorInfo(String errorCode, String errorMessage, String errorDetail) {
        if (errorInfo == null) {
            errorInfo = new ErrorInfoDTO();
        }
        errorInfo.setErrorCode(errorCode);
        errorInfo.setErrorMessage(errorMessage);
        errorInfo.setErrorDetail(errorDetail);
    }
    
    /**
     * 오류가 있는지 확인합니다.
     *
     * @return 오류 존재 여부
     */
    public boolean hasError() {
        return errorInfo != null && errorInfo.getErrorCode() != null;
    }
} 