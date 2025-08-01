package com.skax.eatool.mbb.ac.junmun.service;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.ac.junmun.dto.CompleteMessageDTO;
import com.skax.eatool.mbb.ac.junmun.dto.StandardHeaderDTO;
import com.skax.eatool.mbb.ac.junmun.dto.TransmissionInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.TransactionInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.ApprovalInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.ControlInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.TerminalInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.ExternalInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.SecurityInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.MiscInfoDTO;
import com.skax.eatool.mbb.ac.junmun.dto.FooterDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 전문 메시지 서비스
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class MessageService {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    /**
     * 요청 전문을 생성합니다.
     *
     * @param businessData 업무 데이터 (Map<String,Object>)
     * @return 생성된 전문 메시지
     */
    public CompleteMessageDTO createRequestMessage(Map<String, Object> businessData) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "요청 전문 생성 시작");
        
        try {
            CompleteMessageDTO message = new CompleteMessageDTO();
            
            // 표준 헤더 설정
            message.setStandardHeader(createStandardHeader("S"));
            
            // 전송 정보 설정
            message.setTransmissionInfo(createTransmissionInfo());
            
            // 거래 정보 설정
            message.setTransactionInfo(createTransactionInfo());
            
            // 승인 정보 설정
            message.setApprovalInfo(createApprovalInfo());
            
            // 제어 정보 설정
            message.setControlInfo(createControlInfo());
            
            // 단말 정보 설정
            message.setTerminalInfo(createTerminalInfo());
            
            // 대외거래 정보 설정
            message.setExternalInfo(createExternalInfo());
            
            // 보안 정보 설정
            message.setSecurityInfo(createSecurityInfo());
            
            // 기타 정보 설정
            message.setMiscInfo(createMiscInfo());
            
            // 업무 데이터 설정
            message.setBusinessData(businessData);
            
            // 푸터 설정
            message.setFooter(createFooter());
            
            logger.info(className, "요청 전문 생성 완료");
            return message;
            
        } catch (Exception e) {
            logger.error(className, "요청 전문 생성 중 오류 발생: " + e.getMessage(), e);
            throw new RuntimeException("요청 전문 생성 실패", e);
        }
    }
    
    /**
     * 응답 전문을 생성합니다.
     *
     * @param requestMessage 원본 요청 전문
     * @param businessData 응답 업무 데이터 (Map<String,Object>)
     * @param isSuccess 성공 여부
     * @return 생성된 응답 전문 메시지
     */
    public CompleteMessageDTO createResponseMessage(CompleteMessageDTO requestMessage, Map<String, Object> businessData, boolean isSuccess) {
        String className = this.getClass().getSimpleName();
        logger.info(className, "응답 전문 생성 시작");
        
        try {
            CompleteMessageDTO responseMessage = new CompleteMessageDTO();
            
            // 표준 헤더 설정 (요청 전문 기반)
            StandardHeaderDTO responseHeader = createStandardHeader("R");
            responseHeader.setGblId(requestMessage.getStandardHeader().getGblId());
            responseHeader.setGramRqstDtti(requestMessage.getStandardHeader().getGramRqstDtti());
            responseHeader.setGramRspDtti(getCurrentDateTime());
            responseMessage.setStandardHeader(responseHeader);
            
            // 전송 정보 설정 (요청 전문 기반)
            responseMessage.setTransmissionInfo(requestMessage.getTransmissionInfo());
            
            // 거래 정보 설정 (요청 전문 기반)
            responseMessage.setTransactionInfo(requestMessage.getTransactionInfo());
            
            // 승인 정보 설정 (요청 전문 기반)
            responseMessage.setApprovalInfo(requestMessage.getApprovalInfo());
            
            // 제어 정보 설정 (요청 전문 기반)
            ControlInfoDTO responseControl = requestMessage.getControlInfo();
            responseControl.setRspRsltDvcd(isSuccess ? "1" : "2");
            responseMessage.setControlInfo(responseControl);
            
            // 단말 정보 설정 (요청 전문 기반)
            responseMessage.setTerminalInfo(requestMessage.getTerminalInfo());
            
            // 대외거래 정보 설정 (요청 전문 기반)
            responseMessage.setExternalInfo(requestMessage.getExternalInfo());
            
            // 보안 정보 설정 (요청 전문 기반)
            responseMessage.setSecurityInfo(requestMessage.getSecurityInfo());
            
            // 기타 정보 설정 (요청 전문 기반)
            responseMessage.setMiscInfo(requestMessage.getMiscInfo());
            
            // 업무 데이터 설정
            responseMessage.setBusinessData(businessData);
            
            // 푸터 설정
            responseMessage.setFooter(createFooter());
            
            logger.info(className, "응답 전문 생성 완료");
            return responseMessage;
            
        } catch (Exception e) {
            logger.error(className, "응답 전문 생성 중 오류 발생: " + e.getMessage(), e);
            throw new RuntimeException("응답 전문 생성 실패", e);
        }
    }
    
    /**
     * 기본 업무 데이터를 생성합니다.
     *
     * @return 기본 업무 데이터 Map
     */
    public Map<String, Object> createDefaultBusinessData() {
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("accountNumber", "");
        businessData.put("accountHolder", "");
        businessData.put("transactionAmount", 0L);
        businessData.put("currencyCode", "KRW");
        businessData.put("transactionType", "");
        businessData.put("description", "");
        businessData.put("referenceNo", "");
        return businessData;
    }
    
    /**
     * 급여이체 업무 데이터를 생성합니다.
     *
     * @param accountNumber 계좌번호
     * @param accountHolder 예금주명
     * @param amount 거래금액
     * @param description 거래메모
     * @return 급여이체 업무 데이터 Map
     */
    public Map<String, Object> createSalaryTransferBusinessData(String accountNumber, String accountHolder, Long amount, String description) {
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("accountNumber", accountNumber);
        businessData.put("accountHolder", accountHolder);
        businessData.put("transactionAmount", amount);
        businessData.put("currencyCode", "KRW");
        businessData.put("transactionType", "DEP");
        businessData.put("description", description);
        businessData.put("referenceNo", "PAY" + getCurrentDate() + "-" + System.currentTimeMillis());
        
        // 요청자 정보
        Map<String, Object> requestedBy = new HashMap<>();
        requestedBy.put("userId", "user001");
        requestedBy.put("department", "인사팀");
        businessData.put("requestedBy", requestedBy);
        
        return businessData;
    }
    
    /**
     * 응답 업무 데이터를 생성합니다.
     *
     * @param isSuccess 성공 여부
     * @param message 결과 메시지
     * @return 응답 업무 데이터 Map
     */
    public Map<String, Object> createResponseBusinessData(boolean isSuccess, String message) {
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("resultMessage", message);
        
        if (isSuccess) {
            // 처리자 정보
            Map<String, Object> processedBy = new HashMap<>();
            processedBy.put("system", "CBS");
            processedBy.put("processDate", getCurrentDate());
            processedBy.put("processTime", getCurrentTime());
            businessData.put("processedBy", processedBy);
        } else {
            // 오류 정보
            businessData.put("errorCode", "E001");
            businessData.put("errorDetail", "처리 중 오류가 발생했습니다.");
        }
        
        return businessData;
    }
    
    /**
     * 표준 헤더를 생성합니다.
     */
    private StandardHeaderDTO createStandardHeader(String rqstRspDvcd) {
        StandardHeaderDTO header = new StandardHeaderDTO();
        header.setStdGramLen("00000000"); // 실제 길이로 계산 필요
        header.setGramEncDvcd("2"); // SHA-256
        header.setGblId(generateGlobalId());
        header.setGramRqstDtti(getCurrentDateTime());
        header.setRqstRspDvcd(rqstRspDvcd);
        header.setTtlUseDvcd("2"); // TTL 사용
        header.setFstSti(getCurrentTime());
        header.setKeepHrSecCnt("060"); // 60초
        return header;
    }
    
    /**
     * 전송 정보를 생성합니다.
     */
    private TransmissionInfoDTO createTransmissionInfo() {
        TransmissionInfoDTO transmission = new TransmissionInfoDTO();
        transmission.setChnlTpcd("01"); // 인터넷뱅킹
        transmission.setCliIp("192.168.0.101");
        transmission.setCliMac("00A0C9FFEE11");
        transmission.setConfInfoDvcd("P"); // 운영
        transmission.setFstTrsSysCd("MCI");
        transmission.setTrsSysCd("CBS");
        transmission.setTrsNodeNo("01");
        transmission.setMciNodeNo("01");
        transmission.setFepNodeNo("02");
        transmission.setEaiNodeNo("03");
        return transmission;
    }
    
    /**
     * 거래 정보를 생성합니다.
     */
    private TransactionInfoDTO createTransactionInfo() {
        TransactionInfoDTO transaction = new TransactionInfoDTO();
        transaction.setXaTrnsDvcd("2"); // XA 거래
        transaction.setTrnsSyncDvcd("S"); // 동기
        transaction.setGramLangDvcd("KR"); // 한글
        transaction.setChnlGramCompartLen("0056");
        return transaction;
    }
    
    /**
     * 승인 정보를 생성합니다.
     */
    private ApprovalInfoDTO createApprovalInfo() {
        ApprovalInfoDTO approval = new ApprovalInfoDTO();
        approval.setApvlTypeDvcd("2"); // 책임자 승인
        approval.setApperRspRscd("2"); // 확인
        approval.setOtm1ApvlEnob("1234567");
        approval.setOtm1ApvlTuno("002");
        return approval;
    }
    
    /**
     * 제어 정보를 생성합니다.
     */
    private ControlInfoDTO createControlInfo() {
        ControlInfoDTO control = new ControlInfoDTO();
        control.setScrenId("A001000001");
        control.setInputGramId("DEP01001");
        control.setInputGramLwrId("01");
        control.setMsgCotiCd("1"); // 연속 없음
        control.setNextPgeReuCd("1"); // 다음 페이지 없음
        control.setNonRqstMsgOutptCd("1"); // 비요청 메시지 없음
        control.setCanCorrDvcd("1"); // 정상
        control.setAutoTrnsReuCd("1"); // 자동 거래 없음
        control.setInputGramEditDvcd("1"); // 편집 없음
        control.setSmltTrnsDvcd("1"); // 일반 거래
        control.setVdTrnsDvcd("1"); // 일반
        control.setGramCotiSeq("00");
        control.setOutptGramTpcd("1"); // 일반 거래 전문
        return control;
    }
    
    /**
     * 단말 정보를 생성합니다.
     */
    private TerminalInfoDTO createTerminalInfo() {
        TerminalInfoDTO terminal = new TerminalInfoDTO();
        terminal.setBlgCmgrCd("01");
        terminal.setTtBrno("123");
        terminal.setMtbcno("100");
        terminal.setEnob("654321");
        terminal.setShpDvcd("1"); // 지점
        terminal.setTrnsCmgrCd("01");
        terminal.setTuBrno("123");
        terminal.setTrnsTuno("002");
        terminal.setVeBrno("000");
        return terminal;
    }
    
    /**
     * 대외거래 정보를 생성합니다.
     */
    private ExternalInfoDTO createExternalInfo() {
        ExternalInfoDTO external = new ExternalInfoDTO();
        external.setFrgOrgtcd("BANK");
        external.setFrgBizCd("TR01");
        external.setTtOpenDvcd("1"); // 취급
        return external;
    }
    
    /**
     * 보안 정보를 생성합니다.
     */
    private SecurityInfoDTO createSecurityInfo() {
        SecurityInfoDTO security = new SecurityInfoDTO();
        security.setRrnoInfoPrtcDvcd("2"); // 마스킹
        security.setCustNmInfoPrtcDvcd("2"); // 마스킹
        security.setAcnoInfoPrtcDvcd("2"); // 마스킹
        security.setCdnoInfoPrtcDvcd("2"); // 마스킹
        security.setTlnoInfoPrtcDvcd("2"); // 마스킹
        security.setAddrInfoPrtcDvcd("2"); // 마스킹
        return security;
    }
    
    /**
     * 기타 정보를 생성합니다.
     */
    private MiscInfoDTO createMiscInfo() {
        MiscInfoDTO misc = new MiscInfoDTO();
        misc.setAitmRnwlModeDvcd("1"); // 당일마감전
        misc.setTadyTrnsNdvCanReuCd("1"); // 없음
        misc.setBnkbPrtrCnntDvcd("2"); // 접속
        misc.setIccrdMdmDvcd("2"); // CONTACT
        misc.setMsUseDvcd("1"); // MS 미사용
        misc.setTrlgKeyVal("KEY" + getCurrentDateTime());
        misc.setDlngStcd("L"); // Lock
        misc.setTtRspInputRsrnDvcd("1"); // 전체복원
        misc.setTtRspInputRsrnEditId("EDT0001");
        misc.setOutptWatStcd("1"); // 출력대기
        return misc;
    }
    
    /**
     * 푸터를 생성합니다.
     */
    private FooterDTO createFooter() {
        FooterDTO footer = new FooterDTO();
        footer.setStdGramEndVal("@@");
        return footer;
    }
    
    /**
     * 글로벌 ID를 생성합니다.
     */
    private String generateGlobalId() {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String systemCode = "MCI";
        String sequence = String.format("%014d", System.currentTimeMillis() % 100000000000000L);
        String progress = "00";
        return currentDate + systemCode + sequence + progress;
    }
    
    /**
     * 현재 날짜시간을 반환합니다.
     */
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
    
    /**
     * 현재 날짜를 반환합니다.
     */
    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    
    /**
     * 현재 시간을 반환합니다.
     */
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }
} 