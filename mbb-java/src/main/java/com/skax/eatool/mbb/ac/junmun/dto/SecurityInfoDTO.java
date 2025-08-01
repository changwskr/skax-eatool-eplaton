package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 고객정보 보호 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityInfoDTO extends NewAbstractDTO {
    
    /**
     * 주민번호정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String rrnoInfoPrtcDvcd;
    
    /**
     * 고객명정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String custNmInfoPrtcDvcd;
    
    /**
     * 계좌번호정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String acnoInfoPrtcDvcd;
    
    /**
     * 카드번호정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String cdnoInfoPrtcDvcd;
    
    /**
     * 전화번호정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String tlnoInfoPrtcDvcd;
    
    /**
     * 주소정보보호구분코드 - '1'=보호없음, '2'=마스킹
     */
    private String addrInfoPrtcDvcd;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getRrnoInfoPrtcDvcd() { return rrnoInfoPrtcDvcd; }
    public void setRrnoInfoPrtcDvcd(String rrnoInfoPrtcDvcd) { this.rrnoInfoPrtcDvcd = rrnoInfoPrtcDvcd; }
    
    public String getCustNmInfoPrtcDvcd() { return custNmInfoPrtcDvcd; }
    public void setCustNmInfoPrtcDvcd(String custNmInfoPrtcDvcd) { this.custNmInfoPrtcDvcd = custNmInfoPrtcDvcd; }
    
    public String getAcnoInfoPrtcDvcd() { return acnoInfoPrtcDvcd; }
    public void setAcnoInfoPrtcDvcd(String acnoInfoPrtcDvcd) { this.acnoInfoPrtcDvcd = acnoInfoPrtcDvcd; }
    
    public String getCdnoInfoPrtcDvcd() { return cdnoInfoPrtcDvcd; }
    public void setCdnoInfoPrtcDvcd(String cdnoInfoPrtcDvcd) { this.cdnoInfoPrtcDvcd = cdnoInfoPrtcDvcd; }
    
    public String getTlnoInfoPrtcDvcd() { return tlnoInfoPrtcDvcd; }
    public void setTlnoInfoPrtcDvcd(String tlnoInfoPrtcDvcd) { this.tlnoInfoPrtcDvcd = tlnoInfoPrtcDvcd; }
    
    public String getAddrInfoPrtcDvcd() { return addrInfoPrtcDvcd; }
    public void setAddrInfoPrtcDvcd(String addrInfoPrtcDvcd) { this.addrInfoPrtcDvcd = addrInfoPrtcDvcd; }
} 