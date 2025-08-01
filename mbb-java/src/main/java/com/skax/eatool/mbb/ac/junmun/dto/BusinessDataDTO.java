package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 업무 데이터 DTO (예시용)
 * Map<String,Object>와의 호환성을 위한 유틸리티 메서드 포함
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessDataDTO extends NewAbstractDTO {
    
    /**
     * 계좌번호 - 입출금 계좌 번호
     */
    private String accountNumber;
    
    /**
     * 예금주명 - 계좌 소유자명
     */
    private String accountHolder;
    
    /**
     * 거래금액 - 거래 요청 금액
     */
    private Long transactionAmount;
    
    /**
     * 통화코드 - 'KRW', 'USD' 등
     */
    private String currencyCode;
    
    /**
     * 거래유형 - 'DEP'=입금, 'WDR'=출금 등
     */
    private String transactionType;
    
    /**
     * 거래메모 - 거래 사유 또는 메모
     */
    private String description;
    
    /**
     * 참조번호 - 내부 참조용 키값
     */
    private String referenceNo;
    
    /**
     * 요청자정보 - 사용자ID, 부서명 등 포함된 JSON 객체
     */
    private RequestedByDTO requestedBy;
    
    /**
     * 결과메시지 - 응답 시 결과 메시지
     */
    private String resultMessage;
    
    /**
     * 처리자정보 - 응답 시 처리 시스템 정보
     */
    private ProcessedByDTO processedBy;
    
    /**
     * DTO를 Map<String,Object>로 변환합니다.
     *
     * @return Map<String,Object> 형태의 업무 데이터
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("accountNumber", accountNumber);
        map.put("accountHolder", accountHolder);
        map.put("transactionAmount", transactionAmount);
        map.put("currencyCode", currencyCode);
        map.put("transactionType", transactionType);
        map.put("description", description);
        map.put("referenceNo", referenceNo);
        map.put("resultMessage", resultMessage);
        
        if (requestedBy != null) {
            Map<String, Object> requestedByMap = new HashMap<>();
            requestedByMap.put("userId", requestedBy.getUserId());
            requestedByMap.put("department", requestedBy.getDepartment());
            map.put("requestedBy", requestedByMap);
        }
        
        if (processedBy != null) {
            Map<String, Object> processedByMap = new HashMap<>();
            processedByMap.put("system", processedBy.getSystem());
            processedByMap.put("processDate", processedBy.getProcessDate());
            processedByMap.put("processTime", processedBy.getProcessTime());
            map.put("processedBy", processedByMap);
        }
        
        return map;
    }
    
    /**
     * Map<String,Object>에서 DTO를 생성합니다.
     *
     * @param map 업무 데이터 Map
     * @return BusinessDataDTO 객체
     */
    public static BusinessDataDTO fromMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        
        BusinessDataDTO dto = new BusinessDataDTO();
        dto.setAccountNumber((String) map.get("accountNumber"));
        dto.setAccountHolder((String) map.get("accountHolder"));
        dto.setTransactionAmount((Long) map.get("transactionAmount"));
        dto.setCurrencyCode((String) map.get("currencyCode"));
        dto.setTransactionType((String) map.get("transactionType"));
        dto.setDescription((String) map.get("description"));
        dto.setReferenceNo((String) map.get("referenceNo"));
        dto.setResultMessage((String) map.get("resultMessage"));
        
        // requestedBy 처리
        Object requestedByObj = map.get("requestedBy");
        if (requestedByObj instanceof Map) {
            Map<String, Object> requestedByMap = (Map<String, Object>) requestedByObj;
            RequestedByDTO requestedBy = new RequestedByDTO();
            requestedBy.setUserId((String) requestedByMap.get("userId"));
            requestedBy.setDepartment((String) requestedByMap.get("department"));
            dto.setRequestedBy(requestedBy);
        }
        
        // processedBy 처리
        Object processedByObj = map.get("processedBy");
        if (processedByObj instanceof Map) {
            Map<String, Object> processedByMap = (Map<String, Object>) processedByObj;
            ProcessedByDTO processedBy = new ProcessedByDTO();
            processedBy.setSystem((String) processedByMap.get("system"));
            processedBy.setProcessDate((String) processedByMap.get("processDate"));
            processedBy.setProcessTime((String) processedByMap.get("processTime"));
            dto.setProcessedBy(processedBy);
        }
        
        return dto;
    }
    
    /**
     * 요청자 정보 DTO
     */
    @Data
    public static class RequestedByDTO {
        private String userId;
        private String department;
    }
    
    /**
     * 처리자 정보 DTO
     */
    @Data
    public static class ProcessedByDTO {
        private String system;
        private String processDate;
        private String processTime;
    }
} 