package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 승인자/책임자 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalInfoDTO extends NewAbstractDTO {
    
    /**
     * 승인유형구분코드 - '1'=일반승인, '2'=책임자승인
     */
    private String apvlTypeDvcd;
    
    /**
     * 승인자응답결과코드 - '1'=승인, '2'=확인, '3'=반려
     */
    private String apperRspRscd;
    
    /**
     * 1차승인자사번 - 1차 승인자의 사번
     */
    private String otm1ApvlEnob;
    
    /**
     * 1차승인자팀번호 - 1차 승인자의 팀번호
     */
    private String otm1ApvlTuno;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getApvlTypeDvcd() { return apvlTypeDvcd; }
    public void setApvlTypeDvcd(String apvlTypeDvcd) { this.apvlTypeDvcd = apvlTypeDvcd; }
    
    public String getApperRspRscd() { return apperRspRscd; }
    public void setApperRspRscd(String apperRspRscd) { this.apperRspRscd = apperRspRscd; }
    
    public String getOtm1ApvlEnob() { return otm1ApvlEnob; }
    public void setOtm1ApvlEnob(String otm1ApvlEnob) { this.otm1ApvlEnob = otm1ApvlEnob; }
    
    public String getOtm1ApvlTuno() { return otm1ApvlTuno; }
    public void setOtm1ApvlTuno(String otm1ApvlTuno) { this.otm1ApvlTuno = otm1ApvlTuno; }
} 