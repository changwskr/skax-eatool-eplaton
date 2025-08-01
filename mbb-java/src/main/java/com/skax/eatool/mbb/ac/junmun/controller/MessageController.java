package com.skax.eatool.mbb.ac.junmun.controller;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.ac.junmun.dto.CompleteMessageDTO;
import com.skax.eatool.mbb.ac.junmun.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 전문 메시지 컨트롤러
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private MessageService messageService;
    
    /**
     * 요청 전문을 생성합니다.
     *
     * @param businessData 업무 데이터 (Map<String,Object>)
     * @return 생성된 전문 메시지
     */
    @PostMapping("/create-request")
    public ResponseEntity<NewKBData> createRequestMessage(@RequestBody Map<String, Object> businessData) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "요청 전문 생성 API 호출");
        
        try {
            CompleteMessageDTO message = messageService.createRequestMessage(businessData);
            
            NewKBData response = new NewKBData();
            response.setValid(true);
            response.setMessage("요청 전문 생성 성공");
            response.getOutputGenericDto().put("message", message);
            
            logger.info(className, "요청 전문 생성 완료");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "요청 전문 생성 실패: " + e.getMessage(), e);
            
            NewKBData response = new NewKBData();
            response.setValid(false);
            response.setErrorMessage("요청 전문 생성 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 응답 전문을 생성합니다.
     *
     * @param request 요청 데이터 (원본 요청 전문 + 응답 업무 데이터)
     * @return 생성된 응답 전문 메시지
     */
    @PostMapping("/create-response")
    public ResponseEntity<NewKBData> createResponseMessage(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "응답 전문 생성 API 호출");
        
        try {
            // 요청 데이터에서 원본 메시지와 응답 업무 데이터 추출
            CompleteMessageDTO originalMessage = convertToCompleteMessageDTO((Map<String, Object>) request.get("originalMessage"));
            Map<String, Object> responseBusinessData = (Map<String, Object>) request.get("businessData");
            Boolean isSuccess = (Boolean) request.get("isSuccess");
            
            CompleteMessageDTO responseMessage = messageService.createResponseMessage(originalMessage, responseBusinessData, isSuccess);
            
            NewKBData response = new NewKBData();
            response.setValid(true);
            response.setMessage("응답 전문 생성 성공");
            response.getOutputGenericDto().put("message", responseMessage);
            
            logger.info(className, "응답 전문 생성 완료");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "응답 전문 생성 실패: " + e.getMessage(), e);
            
            NewKBData response = new NewKBData();
            response.setValid(false);
            response.setErrorMessage("응답 전문 생성 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 급여이체 요청 전문을 생성합니다.
     *
     * @param request 급여이체 요청 데이터
     * @return 생성된 전문 메시지
     */
    @PostMapping("/create-salary-transfer")
    public ResponseEntity<NewKBData> createSalaryTransferMessage(@RequestBody Map<String, Object> request) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "급여이체 요청 전문 생성 API 호출");
        
        try {
            String accountNumber = (String) request.get("accountNumber");
            String accountHolder = (String) request.get("accountHolder");
            Long amount = Long.valueOf(request.get("amount").toString());
            String description = (String) request.get("description");
            
            Map<String, Object> businessData = messageService.createSalaryTransferBusinessData(
                accountNumber, accountHolder, amount, description);
            
            CompleteMessageDTO message = messageService.createRequestMessage(businessData);
            
            NewKBData response = new NewKBData();
            response.setValid(true);
            response.setMessage("급여이체 요청 전문 생성 성공");
            response.getOutputGenericDto().put("message", message);
            
            logger.info(className, "급여이체 요청 전문 생성 완료");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "급여이체 요청 전문 생성 실패: " + e.getMessage(), e);
            
            NewKBData response = new NewKBData();
            response.setValid(false);
            response.setErrorMessage("급여이체 요청 전문 생성 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 기본 업무 데이터를 생성합니다.
     *
     * @return 기본 업무 데이터
     */
    @GetMapping("/default-business-data")
    public ResponseEntity<NewKBData> getDefaultBusinessData() {
        String className = this.getClass().getSimpleName();
        logger.info(className, "기본 업무 데이터 조회 API 호출");
        
        try {
            Map<String, Object> businessData = messageService.createDefaultBusinessData();
            
            NewKBData response = new NewKBData();
            response.setValid(true);
            response.setMessage("기본 업무 데이터 조회 성공");
            response.getOutputGenericDto().put("businessData", businessData);
            
            logger.info(className, "기본 업무 데이터 조회 완료");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "기본 업무 데이터 조회 실패: " + e.getMessage(), e);
            
            NewKBData response = new NewKBData();
            response.setValid(false);
            response.setErrorMessage("기본 업무 데이터 조회 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 전문 메시지를 검증합니다.
     *
     * @param message 검증할 전문 메시지
     * @return 검증 결과
     */
    @PostMapping("/validate")
    public ResponseEntity<NewKBData> validateMessage(@RequestBody CompleteMessageDTO message) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "전문 메시지 검증 API 호출");
        
        try {
            // 기본 검증 로직
            boolean isValid = validateMessageStructure(message);
            
            NewKBData response = new NewKBData();
            response.setValid(isValid);
            response.setMessage(isValid ? "전문 메시지 검증 성공" : "전문 메시지 검증 실패");
            response.getOutputGenericDto().put("validationResult", isValid);
            
            logger.info(className, "전문 메시지 검증 완료: " + (isValid ? "성공" : "실패"));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error(className, "전문 메시지 검증 실패: " + e.getMessage(), e);
            
            NewKBData response = new NewKBData();
            response.setValid(false);
            response.setErrorMessage("전문 메시지 검증 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 전문 메시지 구조를 검증합니다.
     */
    private boolean validateMessageStructure(CompleteMessageDTO message) {
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
        
        // 표준 헤더 검증
        var header = message.getStandardHeader();
        if (header.getGblId() == null || header.getGramRqstDtti() == null || 
            header.getRqstRspDvcd() == null) {
            return false;
        }
        
        // 푸터 검증
        var footer = message.getFooter();
        if (!"@@".equals(footer.getStdGramEndVal())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Map을 CompleteMessageDTO로 변환합니다.
     */
    private CompleteMessageDTO convertToCompleteMessageDTO(Map<String, Object> map) {
        // 실제 구현에서는 ObjectMapper를 사용하여 변환
        // 여기서는 간단한 예시로 구현
        CompleteMessageDTO message = new CompleteMessageDTO();
        // 필요한 변환 로직 구현
        return message;
    }
} 