package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 대외거래 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalInfoDTO extends NewAbstractDTO {
    
    /**
     * 대외기관코드 - 대외 기관 식별 코드
     */
    private String frgOrgtcd;
    
    /**
     * 대외업무코드 - 대외 기관 업무 코드
     */
    private String frgBizCd;
    
    /**
     * 총취급구분코드 - '1'=취급, '2'=취급안함
     */
    private String ttOpenDvcd;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getFrgOrgtcd() { return frgOrgtcd; }
    public void setFrgOrgtcd(String frgOrgtcd) { this.frgOrgtcd = frgOrgtcd; }
    
    public String getFrgBizCd() { return frgBizCd; }
    public void setFrgBizCd(String frgBizCd) { this.frgBizCd = frgBizCd; }
    
    public String getTtOpenDvcd() { return ttOpenDvcd; }
    public void setTtOpenDvcd(String ttOpenDvcd) { this.ttOpenDvcd = ttOpenDvcd; }
} 