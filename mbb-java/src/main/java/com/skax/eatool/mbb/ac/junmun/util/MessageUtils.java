package com.skax.eatool.mbb.ac.junmun.util;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.ac.junmun.dto.CompleteMessageDTO;
import com.skax.eatool.mbb.ac.junmun.dto.BusinessDataDTO;

import java.util.Map;
import java.util.Optional;

/**
 * 전문 메시지 유틸리티 클래스
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
public class MessageUtils {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getLogger(MessageUtils.class);
    
    /**
     * 업무 데이터에서 특정 키의 값을 반환
     *
     * @param message 전문 메시지
     * @param key 키
     * @return Optional로 감싼 값
     */
    public static Optional<Object> getBusinessDataValue(CompleteMessageDTO message, String key) {
        if (message == null || message.getBusinessData() == null || key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(message.getBusinessData().get(key));
    }
    
    /**
     * 업무 데이터에 키-값 쌍을 설정
     *
     * @param message 전문 메시지
     * @param key 키
     * @param value 값
     */
    public static void setBusinessDataValue(CompleteMessageDTO message, String key, Object value) {
        if (message == null) {
            return;
        }
        
        if (message.getBusinessData() == null) {
            message.setBusinessData(new java.util.HashMap<>());
        }
        message.getBusinessData().put(key, value);
    }
    
    /**
     * 업무 데이터를 특정 DTO로 변환하여 반환
     *
     * @param message 전문 메시지
     * @param clazz 변환할 DTO 클래스
     * @param <T> DTO 타입
     * @return Optional로 감싼 DTO 객체
     */
    public static <T> Optional<T> getBusinessDataAs(CompleteMessageDTO message, Class<T> clazz) {
        if (message == null || message.getBusinessData() == null) {
            return Optional.empty();
        }
        
        try {
            // ObjectMapper를 사용하여 Map을 DTO로 변환
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            T dto = mapper.convertValue(message.getBusinessData(), clazz);
            return Optional.of(dto);
        } catch (Exception e) {
            logger.error("MessageUtils", "업무 데이터를 DTO로 변환 중 오류: " + e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    /**
     * 업무 데이터를 특정 DTO로 설정
     *
     * @param message 전문 메시지
     * @param dto 설정할 DTO 객체
     * @param <T> DTO 타입
     */
    public static <T> void setBusinessDataFrom(CompleteMessageDTO message, T dto) {
        if (message == null) {
            return;
        }
        
        if (dto == null) {
            message.setBusinessData(null);
            return;
        }
        
        try {
            // ObjectMapper를 사용하여 DTO를 Map으로 변환
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> businessData = mapper.convertValue(dto, Map.class);
            message.setBusinessData(businessData);
        } catch (Exception e) {
            logger.error("MessageUtils", "DTO를 업무 데이터로 변환 중 오류: " + e.getMessage(), e);
            message.setBusinessData(null);
        }
    }
    
    /**
     * 업무 데이터를 BusinessDataDTO로 변환하여 반환
     *
     * @param message 전문 메시지
     * @return Optional로 감싼 BusinessDataDTO 객체
     */
    public static Optional<BusinessDataDTO> getBusinessDataAsBusinessDataDTO(CompleteMessageDTO message) {
        return getBusinessDataAs(message, BusinessDataDTO.class);
    }
    
    /**
     * BusinessDataDTO를 업무 데이터로 설정
     *
     * @param message 전문 메시지
     * @param businessDataDTO 설정할 BusinessDataDTO 객체
     */
    public static void setBusinessDataFromBusinessDataDTO(CompleteMessageDTO message, BusinessDataDTO businessDataDTO) {
        setBusinessDataFrom(message, businessDataDTO);
    }
    
    /**
     * 업무 데이터를 로그로 출력
     *
     * @param businessData 업무 데이터
     * @param title 로그 제목
     */
    public static void logBusinessData(Map<String, Object> businessData, String title) {
        if (businessData == null) {
            logger.info("MessageUtils", title + " - 업무 데이터가 null입니다.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(" - 업무 데이터 내용:\n");
        
        for (Map.Entry<String, Object> entry : businessData.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        logger.info("MessageUtils", sb.toString());
    }
    
    /**
     * 전문 메시지의 업무 데이터를 로그로 출력
     *
     * @param message 전문 메시지
     * @param title 로그 제목
     */
    public static void logMessageBusinessData(CompleteMessageDTO message, String title) {
        if (message == null) {
            logger.info("MessageUtils", title + " - 전문 메시지가 null입니다.");
            return;
        }
        
        logBusinessData(message.getBusinessData(), title);
    }
    
    /**
     * 전문 메시지가 유효한지 검증
     *
     * @param message 검증할 전문 메시지
     * @return 유효성 여부
     */
    public static boolean isValidMessage(CompleteMessageDTO message) {
        if (message == null) {
            return false;
        }
        
        // 필수 필드 검증
        if (message.getStandardHeader() == null || 
            message.getTransmissionInfo() == null || 
            message.getTransactionInfo() == null || 
            message.getApprovalInfo() == null || 
            message.getControlInfo() == null || 
            message.getTerminalInfo() == null || 
            message.getSecurityInfo() == null || 
            message.getMiscInfo() == null || 
            message.getBusinessData() == null || 
            message.getFooter() == null) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 전문 메시지의 요청/응답 구분을 확인
     *
     * @param message 전문 메시지
     * @return true: 요청, false: 응답
     */
    public static boolean isRequestMessage(CompleteMessageDTO message) {
        if (message == null || message.getStandardHeader() == null) {
            return false;
        }
        
        String rqstRspDvcd = message.getStandardHeader().getRqstRspDvcd();
        return "S".equals(rqstRspDvcd);
    }
    
    /**
     * 전문 메시지의 성공/실패 구분을 확인
     *
     * @param message 전문 메시지
     * @return true: 성공, false: 실패
     */
    public static boolean isSuccessMessage(CompleteMessageDTO message) {
        if (message == null || message.getControlInfo() == null) {
            return false;
        }
        
        String rspRsltDvcd = message.getControlInfo().getRspRsltDvcd();
        return "1".equals(rspRsltDvcd);
    }
    
    /**
     * 전문 메시지에 오류가 있는지 확인
     *
     * @param message 전문 메시지
     * @return true: 오류 있음, false: 오류 없음
     */
    public static boolean hasError(CompleteMessageDTO message) {
        return message != null && message.getErrorInfo() != null && 
               message.getErrorInfo().getErrorCode() != null;
    }
    
    /**
     * 전문 메시지의 오류 정보를 로그로 출력
     *
     * @param message 전문 메시지
     */
    public static void logErrorMessage(CompleteMessageDTO message) {
        if (message == null || message.getErrorInfo() == null) {
            return;
        }
        
        var errorInfo = message.getErrorInfo();
        logger.error("MessageUtils", String.format(
            "전문 오류 - 코드: %s, 메시지: %s, 상세: %s",
            errorInfo.getErrorCode(),
            errorInfo.getErrorMessage(),
            errorInfo.getErrorDetail()
        ));
    }
} 