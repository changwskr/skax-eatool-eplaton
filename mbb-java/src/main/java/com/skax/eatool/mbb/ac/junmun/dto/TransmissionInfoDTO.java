package com.skax.eatool.mbb.ac.junmun.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 시스템/채널 전송 정보 DTO
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransmissionInfoDTO extends NewAbstractDTO {
    
    /**
     * 채널유형코드 - 요청한 채널 구분
     */
    private String chnlTpcd;
    
    /**
     * Client IP - 전문 생성 시스템의 IP 주소 (IPv4/IPv6)
     */
    private String cliIp;
    
    /**
     * Client MAC - 전문 생성 시스템의 MAC 주소
     */
    private String cliMac;
    
    /**
     * 환경정보구분코드 - 운영(P), 개발(D), 테스트(T) 환경 구분
     */
    private String confInfoDvcd;
    
    /**
     * 최초전송시스템코드 - 최초 전문 생성 시스템 코드
     */
    private String fstTrsSysCd;
    
    /**
     * 전송시스템코드 - 현재 전문 전송 시스템 코드
     */
    private String trsSysCd;
    
    /**
     * 전송노드번호 - 다중 노드 구성 시 노드번호
     */
    private String trsNodeNo;
    
    /**
     * MCI노드번호 - MCI 시스템에서의 노드번호
     */
    private String mciNodeNo;
    
    /**
     * FEP노드번호 - FEP 시스템에서의 노드번호
     */
    private String fepNodeNo;
    
    /**
     * EAI노드번호 - EAI 시스템에서의 노드번호
     */
    private String eaiNodeNo;
    
    /**
     * 수신서비스코드 - 수신 시스템에서 불러질 실제적인 최종 서비스코드
     */
    private String rcvSvCd;
    
    /**
     * 결과수신서비스코드 - Async 응답시 수신할 송신측의 서비스코드
     */
    private String rsltRcvSvCd;
    
    /**
     * FEP인터페이스ID - FEP 호출을 위한 FEP 인터페이스ID
     */
    private String frgMciIfId;
    
    /**
     * EAI인터페이스ID - EAI 호출을 위한 EAI 인터페이스ID
     */
    private String eaiIfId;
    
    // 수동으로 추가한 getter/setter 메서드들
    public String getChnlTpcd() { return chnlTpcd; }
    public void setChnlTpcd(String chnlTpcd) { this.chnlTpcd = chnlTpcd; }
    
    public String getCliIp() { return cliIp; }
    public void setCliIp(String cliIp) { this.cliIp = cliIp; }
    
    public String getCliMac() { return cliMac; }
    public void setCliMac(String cliMac) { this.cliMac = cliMac; }
    
    public String getConfInfoDvcd() { return confInfoDvcd; }
    public void setConfInfoDvcd(String confInfoDvcd) { this.confInfoDvcd = confInfoDvcd; }
    
    public String getFstTrsSysCd() { return fstTrsSysCd; }
    public void setFstTrsSysCd(String fstTrsSysCd) { this.fstTrsSysCd = fstTrsSysCd; }
    
    public String getTrsSysCd() { return trsSysCd; }
    public void setTrsSysCd(String trsSysCd) { this.trsSysCd = trsSysCd; }
    
    public String getTrsNodeNo() { return trsNodeNo; }
    public void setTrsNodeNo(String trsNodeNo) { this.trsNodeNo = trsNodeNo; }
    
    public String getMciNodeNo() { return mciNodeNo; }
    public void setMciNodeNo(String mciNodeNo) { this.mciNodeNo = mciNodeNo; }
    
    public String getFepNodeNo() { return fepNodeNo; }
    public void setFepNodeNo(String fepNodeNo) { this.fepNodeNo = fepNodeNo; }
    
    public String getEaiNodeNo() { return eaiNodeNo; }
    public void setEaiNodeNo(String eaiNodeNo) { this.eaiNodeNo = eaiNodeNo; }
    
    public String getRcvSvCd() { return rcvSvCd; }
    public void setRcvSvCd(String rcvSvCd) { this.rcvSvCd = rcvSvCd; }
    
    public String getRsltRcvSvCd() { return rsltRcvSvCd; }
    public void setRsltRcvSvCd(String rsltRcvSvCd) { this.rsltRcvSvCd = rsltRcvSvCd; }
    
    public String getFrgMciIfId() { return frgMciIfId; }
    public void setFrgMciIfId(String frgMciIfId) { this.frgMciIfId = frgMciIfId; }
    
    public String getEaiIfId() { return eaiIfId; }
    public void setEaiIfId(String eaiIfId) { this.eaiIfId = eaiIfId; }
} 