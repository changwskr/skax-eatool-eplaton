package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 단말 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TerminalInfoDTO extends NewAbstractDTO {
    
    /**
     * 소속센터코드 - 단말이 소속된 센터 코드
     */
    private String blgCmgrCd;
    
    /**
     * 총지점번호 - 총지점 번호
     */
    private String ttBrno;
    
    /**
     * 모점번호 - 모점 번호
     */
    private String mtbcno;
    
    /**
     * 사번 - 거래 처리자 사번
     */
    private String enob;
    
    /**
     * 지점구분코드 - '1'=지점, '2'=센터
     */
    private String shpDvcd;
    
    /**
     * 거래센터코드 - 거래 처리 센터 코드
     */
    private String trnsCmgrCd;
    
    /**
     * 거래지점번호 - 거래 처리 지점 번호
     */
    private String tuBrno;
    
    /**
     * 거래단말번호 - 거래 처리 단말 번호
     */
    private String trnsTuno;
    
    /**
     * 가상지점번호 - 가상 거래 시 사용하는 지점 번호
     */
    private String veBrno;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getBlgCmgrCd() { return blgCmgrCd; }
    public void setBlgCmgrCd(String blgCmgrCd) { this.blgCmgrCd = blgCmgrCd; }
    
    public String getTtBrno() { return ttBrno; }
    public void setTtBrno(String ttBrno) { this.ttBrno = ttBrno; }
    
    public String getMtbcno() { return mtbcno; }
    public void setMtbcno(String mtbcno) { this.mtbcno = mtbcno; }
    
    public String getEnob() { return enob; }
    public void setEnob(String enob) { this.enob = enob; }
    
    public String getShpDvcd() { return shpDvcd; }
    public void setShpDvcd(String shpDvcd) { this.shpDvcd = shpDvcd; }
    
    public String getTrnsCmgrCd() { return trnsCmgrCd; }
    public void setTrnsCmgrCd(String trnsCmgrCd) { this.trnsCmgrCd = trnsCmgrCd; }
    
    public String getTuBrno() { return tuBrno; }
    public void setTuBrno(String tuBrno) { this.tuBrno = tuBrno; }
    
    public String getTrnsTuno() { return trnsTuno; }
    public void setTrnsTuno(String trnsTuno) { this.trnsTuno = trnsTuno; }
    
    public String getVeBrno() { return veBrno; }
    public void setVeBrno(String veBrno) { this.veBrno = veBrno; }
} 