package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 표준 전문 헤더 영역 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StandardHeaderDTO extends NewAbstractDTO {
    
    /**
     * 표준전문길이 - 전체 전문의 Byte 단위 길이
     */
    private String stdGramLen;
    
    /**
     * 전문암호화구분코드 - 암호화 방식
     * '1'=암호화없음, '2'=SHA-256, '3'=SHA-1, '4'=SEED(128), '5'=3DES(112), '6'=DES(56), '7'=RSA(2048), '8'=RSA(1024)
     */
    private String gramEncDvcd;
    
    /**
     * 글로벌ID - 전문 고유 식별자
     * 전문작성일(8) + 시스템코드(3) + 일련번호(13) + 진행상황(2)
     */
    private String gblId;
    
    /**
     * 전문요청일시 - YYYYMMDDhhmmssuuu 형식
     */
    private String gramRqstDtti;
    
    /**
     * 전문응답일시 - YYYYMMDDhhmmssuuu 형식 (응답 시 필수)
     */
    private String gramRspDtti;
    
    /**
     * 요청응답구분코드 - 'S'=요청, 'R'=응답
     */
    private String rqstRspDvcd;
    
    /**
     * TTL사용구분코드 - '1'=사용안함, '2'=사용함
     */
    private String ttlUseDvcd;
    
    /**
     * 최초시작시각 - hhmmss 형식
     */
    private String fstSti;
    
    /**
     * 유지시간초수 - TTL 유지 시간 (초)
     */
    private String keepHrSecCnt;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getStdGramLen() { return stdGramLen; }
    public void setStdGramLen(String stdGramLen) { this.stdGramLen = stdGramLen; }
    
    public String getGramEncDvcd() { return gramEncDvcd; }
    public void setGramEncDvcd(String gramEncDvcd) { this.gramEncDvcd = gramEncDvcd; }
    
    public String getGblId() { return gblId; }
    public void setGblId(String gblId) { this.gblId = gblId; }
    
    public String getGramRqstDtti() { return gramRqstDtti; }
    public void setGramRqstDtti(String gramRqstDtti) { this.gramRqstDtti = gramRqstDtti; }
    
    public String getGramRspDtti() { return gramRspDtti; }
    public void setGramRspDtti(String gramRspDtti) { this.gramRspDtti = gramRspDtti; }
    
    public String getRqstRspDvcd() { return rqstRspDvcd; }
    public void setRqstRspDvcd(String rqstRspDvcd) { this.rqstRspDvcd = rqstRspDvcd; }
    
    public String getTtlUseDvcd() { return ttlUseDvcd; }
    public void setTtlUseDvcd(String ttlUseDvcd) { this.ttlUseDvcd = ttlUseDvcd; }
    
    public String getFstSti() { return fstSti; }
    public void setFstSti(String fstSti) { this.fstSti = fstSti; }
    
    public String getKeepHrSecCnt() { return keepHrSecCnt; }
    public void setKeepHrSecCnt(String keepHrSecCnt) { this.keepHrSecCnt = keepHrSecCnt; }
} 