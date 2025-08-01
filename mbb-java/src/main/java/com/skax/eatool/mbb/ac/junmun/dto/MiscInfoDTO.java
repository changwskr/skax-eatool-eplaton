package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 기타 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MiscInfoDTO extends NewAbstractDTO {
    
    /**
     * 자동이체갱신모드구분코드 - '1'=당일마감전, '2'=당일마감후
     */
    private String aitmRnwlModeDvcd;
    
    /**
     * 당일거래미처리취소요청코드 - '1'=없음, '2'=있음
     */
    private String tadyTrnsNdvCanReuCd;
    
    /**
     * 은행간프로토콜연결구분코드 - '1'=연결안함, '2'=연결함
     */
    private String bnkbPrtrCnntDvcd;
    
    /**
     * IC카드모드구분코드 - '1'=CONTACTLESS, '2'=CONTACT
     */
    private String iccrdMdmDvcd;
    
    /**
     * MS사용구분코드 - '1'=MS미사용, '2'=MS사용
     */
    private String msUseDvcd;
    
    /**
     * 트래킹키값 - 거래 추적을 위한 키 값
     */
    private String trlgKeyVal;
    
    /**
     * 대기상태코드 - 'L'=Lock, 'U'=Unlock
     */
    private String dlngStcd;
    
    /**
     * 총응답입력복원구분코드 - '1'=전체복원, '2'=부분복원
     */
    private String ttRspInputRsrnDvcd;
    
    /**
     * 총응답입력복원편집ID - 복원 편집 ID
     */
    private String ttRspInputRsrnEditId;
    
    /**
     * 출력대기상태코드 - '1'=출력대기, '2'=출력완료
     */
    private String outptWatStcd;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getAitmRnwlModeDvcd() { return aitmRnwlModeDvcd; }
    public void setAitmRnwlModeDvcd(String aitmRnwlModeDvcd) { this.aitmRnwlModeDvcd = aitmRnwlModeDvcd; }
    
    public String getTadyTrnsNdvCanReuCd() { return tadyTrnsNdvCanReuCd; }
    public void setTadyTrnsNdvCanReuCd(String tadyTrnsNdvCanReuCd) { this.tadyTrnsNdvCanReuCd = tadyTrnsNdvCanReuCd; }
    
    public String getBnkbPrtrCnntDvcd() { return bnkbPrtrCnntDvcd; }
    public void setBnkbPrtrCnntDvcd(String bnkbPrtrCnntDvcd) { this.bnkbPrtrCnntDvcd = bnkbPrtrCnntDvcd; }
    
    public String getIccrdMdmDvcd() { return iccrdMdmDvcd; }
    public void setIccrdMdmDvcd(String iccrdMdmDvcd) { this.iccrdMdmDvcd = iccrdMdmDvcd; }
    
    public String getMsUseDvcd() { return msUseDvcd; }
    public void setMsUseDvcd(String msUseDvcd) { this.msUseDvcd = msUseDvcd; }
    
    public String getTrlgKeyVal() { return trlgKeyVal; }
    public void setTrlgKeyVal(String trlgKeyVal) { this.trlgKeyVal = trlgKeyVal; }
    
    public String getDlngStcd() { return dlngStcd; }
    public void setDlngStcd(String dlngStcd) { this.dlngStcd = dlngStcd; }
    
    public String getTtRspInputRsrnDvcd() { return ttRspInputRsrnDvcd; }
    public void setTtRspInputRsrnDvcd(String ttRspInputRsrnDvcd) { this.ttRspInputRsrnDvcd = ttRspInputRsrnDvcd; }
    
    public String getTtRspInputRsrnEditId() { return ttRspInputRsrnEditId; }
    public void setTtRspInputRsrnEditId(String ttRspInputRsrnEditId) { this.ttRspInputRsrnEditId = ttRspInputRsrnEditId; }
    
    public String getOutptWatStcd() { return outptWatStcd; }
    public void setOutptWatStcd(String outptWatStcd) { this.outptWatStcd = outptWatStcd; }
} 