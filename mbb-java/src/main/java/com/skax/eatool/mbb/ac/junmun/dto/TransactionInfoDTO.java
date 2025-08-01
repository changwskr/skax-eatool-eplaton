package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 거래/트랜잭션 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionInfoDTO extends NewAbstractDTO {
    
    /**
     * XA거래구분코드 - '1'=일반거래, '2'=XA거래
     */
    private String xaTrnsDvcd;
    
    /**
     * 거래동기구분코드 - 'S'=동기, 'A'=비동기
     */
    private String trnsSyncDvcd;
    
    /**
     * 전문언어구분코드 - 'KR'=한글, 'EN'=영문
     */
    private String gramLangDvcd;
    
    /**
     * 채널전문공통길이 - 채널별 전문 공통부 길이
     */
    private String chnlGramCompartLen;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getXaTrnsDvcd() { return xaTrnsDvcd; }
    public void setXaTrnsDvcd(String xaTrnsDvcd) { this.xaTrnsDvcd = xaTrnsDvcd; }
    
    public String getTrnsSyncDvcd() { return trnsSyncDvcd; }
    public void setTrnsSyncDvcd(String trnsSyncDvcd) { this.trnsSyncDvcd = trnsSyncDvcd; }
    
    public String getGramLangDvcd() { return gramLangDvcd; }
    public void setGramLangDvcd(String gramLangDvcd) { this.gramLangDvcd = gramLangDvcd; }
    
    public String getChnlGramCompartLen() { return chnlGramCompartLen; }
    public void setChnlGramCompartLen(String chnlGramCompartLen) { this.chnlGramCompartLen = chnlGramCompartLen; }
} 