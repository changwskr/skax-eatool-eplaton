package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 전문 제어 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ControlInfoDTO extends NewAbstractDTO {
    
    /**
     * 화면ID - 전문을 생성한 화면의 ID
     */
    private String screnId;
    
    /**
     * 입력전문ID - 입력 전문의 고유 ID
     */
    private String inputGramId;
    
    /**
     * 입력전문하위ID - 입력 전문의 하위 ID
     */
    private String inputGramLwrId;
    
    /**
     * 메시지연속코드 - '1'=연속없음, '2'=연속있음
     */
    private String msgCotiCd;
    
    /**
     * 다음페이지요청코드 - '1'=다음페이지없음, '2'=다음페이지요청
     */
    private String nextPgeReuCd;
    
    /**
     * 비요청메시지출력코드 - '1'=비요청메시지없음, '2'=비요청메시지출력
     */
    private String nonRqstMsgOutptCd;
    
    /**
     * 취소정정구분코드 - '1'=정상, '2'=취소, '3'=정정
     */
    private String canCorrDvcd;
    
    /**
     * 자동거래요청코드 - '1'=자동거래없음, '2'=자동거래요청
     */
    private String autoTrnsReuCd;
    
    /**
     * 입력전문편집구분코드 - '1'=편집없음, '2'=편집있음
     */
    private String inputGramEditDvcd;
    
    /**
     * 동시거래구분코드 - '1'=일반거래, '2'=동시거래
     */
    private String smltTrnsDvcd;
    
    /**
     * 가상거래구분코드 - '1'=일반, '2'=가상
     */
    private String vdTrnsDvcd;
    
    /**
     * 전문연속순번 - 전문 연속 처리 시 순번
     */
    private String gramCotiSeq;
    
    /**
     * 출력전문유형코드 - '1'=일반거래전문, '2'=조회전문
     */
    private String outptGramTpcd;
    
    /**
     * 응답결과구분코드 - '1'=성공, '2'=실패
     */
    private String rspRsltDvcd;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getScrenId() { return screnId; }
    public void setScrenId(String screnId) { this.screnId = screnId; }
    
    public String getInputGramId() { return inputGramId; }
    public void setInputGramId(String inputGramId) { this.inputGramId = inputGramId; }
    
    public String getInputGramLwrId() { return inputGramLwrId; }
    public void setInputGramLwrId(String inputGramLwrId) { this.inputGramLwrId = inputGramLwrId; }
    
    public String getMsgCotiCd() { return msgCotiCd; }
    public void setMsgCotiCd(String msgCotiCd) { this.msgCotiCd = msgCotiCd; }
    
    public String getNextPgeReuCd() { return nextPgeReuCd; }
    public void setNextPgeReuCd(String nextPgeReuCd) { this.nextPgeReuCd = nextPgeReuCd; }
    
    public String getNonRqstMsgOutptCd() { return nonRqstMsgOutptCd; }
    public void setNonRqstMsgOutptCd(String nonRqstMsgOutptCd) { this.nonRqstMsgOutptCd = nonRqstMsgOutptCd; }
    
    public String getCanCorrDvcd() { return canCorrDvcd; }
    public void setCanCorrDvcd(String canCorrDvcd) { this.canCorrDvcd = canCorrDvcd; }
    
    public String getAutoTrnsReuCd() { return autoTrnsReuCd; }
    public void setAutoTrnsReuCd(String autoTrnsReuCd) { this.autoTrnsReuCd = autoTrnsReuCd; }
    
    public String getInputGramEditDvcd() { return inputGramEditDvcd; }
    public void setInputGramEditDvcd(String inputGramEditDvcd) { this.inputGramEditDvcd = inputGramEditDvcd; }
    
    public String getSmltTrnsDvcd() { return smltTrnsDvcd; }
    public void setSmltTrnsDvcd(String smltTrnsDvcd) { this.smltTrnsDvcd = smltTrnsDvcd; }
    
    public String getVdTrnsDvcd() { return vdTrnsDvcd; }
    public void setVdTrnsDvcd(String vdTrnsDvcd) { this.vdTrnsDvcd = vdTrnsDvcd; }
    
    public String getGramCotiSeq() { return gramCotiSeq; }
    public void setGramCotiSeq(String gramCotiSeq) { this.gramCotiSeq = gramCotiSeq; }
    
    public String getOutptGramTpcd() { return outptGramTpcd; }
    public void setOutptGramTpcd(String outptGramTpcd) { this.outptGramTpcd = outptGramTpcd; }
    
    public String getRspRsltDvcd() { return rspRsltDvcd; }
    public void setRspRsltDvcd(String rspRsltDvcd) { this.rspRsltDvcd = rspRsltDvcd; }
} 