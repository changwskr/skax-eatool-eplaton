package com.skax.eatool.mba.ac.eplatonac;



/**
 * =============================================================================
 * 프로그램 명:
 * =============================================================================
 *
 * //TPSsendrecv 작업종료간관리 * call_tpm_in_time;
 * call_tpm_out_time;
 * call_tpm_interval;
 * 
 * //메인버튼STF 작업종료간관리관련 * call_tpm_stf_in_time;
 * call_tpm_stf_out_time;
 * 
 * //메인버튼ETF 작업종료간관리관련 * call_tpm_etf_in_time;
 * call_tpm_etf_out_time;
 * 
 * //메인버튼STF에서ETF까지 간격
 * call_tpm_stf_etf_interval;
 * 
 * //메인버튼 처리번호-관련 * call_hostseq;
 * 
 * //메인버튼 오류코드-관련 * error_code;
 *
 * =============================================================================
 * 변경내용:
 * =============================================================================
 * 2004년03월16일1차버전release
 *
 *
 * =============================================================================
 * 
 * @author : 우우WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */

public class TPMSVCINFO extends DTO {

  private char offset = 'X';
  private String call_service_name;

  private String call_tpm_in_time;
  private String call_tpm_out_time;
  private String call_tpm_interval;
  private String call_tpm_stf_in_time;
  private String call_tpm_stf_out_time;
  private String call_tpm_etf_in_time;
  private String call_tpm_etf_out_time;
  private String call_tpm_stf_etf_interval;
  private String call_hostseq;
  private String call_orgseq;
  private String call_location;
  private String call_tpm_service_interval;
  private String error_code;

  public TPMSVCINFO() {
    // Default constructor
  }

  public TPMSVCINFO(String tarsystem, EPlatonEvent event) {
    TPSVCINFODTO tpmdto = event.getTPSVCINFODTO();

    this.setCall_hostseq(tpmdto.getHostseq()); // 메인버튼 처리번호??
                                               // TPCsendrecv??썑 媛吏????뒗 eplatonevent媛앹??媛??????
    this.setCall_orgseq(tpmdto.getOrgseq()); // 메인버튼 오류코드??
                                             // TPCsendrecv??썑 媛吏????뒗 eplatonevent媛앹??媛????????떎.
    this.setCall_service_name(tarsystem); // 메인버튼 비용명??????몄?????뒪??챸??紐낆???떎.??????컪?? request_name???? // ??媛??
                                          // TPCsendrecv???꾨떖??뒗 requestname????
    this.setCall_tpm_in_time(CommonUtil.GetSysTime()); // TPSsendrecv??옉??컙
    this.setCall_tpm_out_time(this.getCall_tpm_in_time()); // TPSsendrecv?�낅�??�컙

    this.setError_code("IZZ000"); // TPCsendrecv???�?諛⑹�????? this.setOffset('0'); // TPCsendrecv ??�옉 flag

  }

  public String getCall_hostseq() {
    return call_hostseq;
  }

  public String getCall_location() {
    return call_location;
  }

  public String getCall_orgseq() {
    return call_orgseq;
  }

  public String getCall_service_name() {
    return call_service_name;
  }

  public String getCall_tpm_etf_in_time() {
    return call_tpm_etf_in_time;
  }

  public String getCall_tpm_etf_out_time() {
    return call_tpm_etf_out_time;
  }

  public String getCall_tpm_in_time() {
    return call_tpm_in_time;
  }

  public String getCall_tpm_out_time() {
    return call_tpm_out_time;
  }

  public String getCall_tpm_stf_in_time() {
    return call_tpm_stf_in_time;
  }

  public String getCall_tpm_stf_out_time() {
    return call_tpm_stf_out_time;
  }

  public String getCall_tpme_interval() {
    return call_tpm_interval;
  }

  public String getCall_tpme_service_interval() {
    return call_tpm_service_interval;
  }

  public String getError_code() {
    return error_code;
  }

  public void setError_code(String error_code) {
    this.error_code = error_code;
  }

  public void setCall_tpme_service_interval(String call_tpme_service_interval) {
    this.call_tpm_service_interval = call_tpme_service_interval;
  }

  public void setCall_tpme_interval(String call_tpme_interval) {
    this.call_tpm_interval = call_tpme_interval;
  }

  public void setCall_tpm_stf_out_time(String call_tpm_stf_out_time) {
    this.call_tpm_stf_out_time = call_tpm_stf_out_time;
  }

  public void setCall_tpm_stf_in_time(String call_tpm_stf_in_time) {
    this.call_tpm_stf_in_time = call_tpm_stf_in_time;
  }

  public void setCall_tpm_out_time(String call_tpm_out_time) {
    this.call_tpm_out_time = call_tpm_out_time;
    //////////////////////////////////////////////////////////////////////////
    // INTERVAL 계산 및 설정 처리
    ////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////////////
    this.setCall_tpme_interval("010");
  }

  public void setCall_tpm_in_time(String call_tpm_in_time) {
    this.call_tpm_in_time = call_tpm_in_time;
  }

  public void setCall_tpm_etf_out_time(String call_tpm_etf_out_time) {
    this.call_tpm_etf_out_time = call_tpm_etf_out_time;
  }

  public void setCall_tpm_etf_in_time(String call_tpm_etf_in_time) {
    this.call_tpm_etf_in_time = call_tpm_etf_in_time;
  }

  public void setCall_service_name(String call_service_name) {
    this.call_service_name = call_service_name;
  }

  public void setCall_orgseq(String call_orgseq) {
    this.call_orgseq = call_orgseq;
  }

  public void setCall_location(String call_location) {
    this.call_location = call_location;
  }

  public void setCall_hostseq(String call_hostseq) {
    this.call_hostseq = call_hostseq;
  }

  public char getOffset() {
    return offset;
  }

  public void setOffset(char offset) {
    this.offset = offset;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TPMSVCINFO other = (TPMSVCINFO) obj;
    if (call_hostseq == null) {
      if (other.call_hostseq != null)
        return false;
    } else if (!call_hostseq.equals(other.call_hostseq))
      return false;
    if (call_location == null) {
      if (other.call_location != null)
        return false;
    } else if (!call_location.equals(other.call_location))
      return false;
    if (call_orgseq == null) {
      if (other.call_orgseq != null)
        return false;
    } else if (!call_orgseq.equals(other.call_orgseq))
      return false;
    if (call_service_name == null) {
      if (other.call_service_name != null)
        return false;
    } else if (!call_service_name.equals(other.call_service_name))
      return false;
    if (call_tpm_etf_in_time == null) {
      if (other.call_tpm_etf_in_time != null)
        return false;
    } else if (!call_tpm_etf_in_time.equals(other.call_tpm_etf_in_time))
      return false;
    if (call_tpm_etf_out_time == null) {
      if (other.call_tpm_etf_out_time != null)
        return false;
    } else if (!call_tpm_etf_out_time.equals(other.call_tpm_etf_out_time))
      return false;
    if (call_tpm_in_time == null) {
      if (other.call_tpm_in_time != null)
        return false;
    } else if (!call_tpm_in_time.equals(other.call_tpm_in_time))
      return false;
    if (call_tpm_interval == null) {
      if (other.call_tpm_interval != null)
        return false;
    } else if (!call_tpm_interval.equals(other.call_tpm_interval))
      return false;
    if (call_tpm_out_time == null) {
      if (other.call_tpm_out_time != null)
        return false;
    } else if (!call_tpm_out_time.equals(other.call_tpm_out_time))
      return false;
    if (call_tpm_service_interval == null) {
      if (other.call_tpm_service_interval != null)
        return false;
    } else if (!call_tpm_service_interval.equals(other.call_tpm_service_interval))
      return false;
    if (call_tpm_stf_in_time == null) {
      if (other.call_tpm_stf_in_time != null)
        return false;
    } else if (!call_tpm_stf_in_time.equals(other.call_tpm_stf_in_time))
      return false;
    if (call_tpm_stf_out_time == null) {
      if (other.call_tpm_stf_out_time != null)
        return false;
    } else if (!call_tpm_stf_out_time.equals(other.call_tpm_stf_out_time))
      return false;
    if (error_code == null) {
      if (other.error_code != null)
        return false;
    } else if (!error_code.equals(other.error_code))
      return false;
    if (offset != other.offset)
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((call_hostseq == null) ? 0 : call_hostseq.hashCode());
    result = prime * result + ((call_location == null) ? 0 : call_location.hashCode());
    result = prime * result + ((call_orgseq == null) ? 0 : call_orgseq.hashCode());
    result = prime * result + ((call_service_name == null) ? 0 : call_service_name.hashCode());
    result = prime * result + ((call_tpm_etf_in_time == null) ? 0 : call_tpm_etf_in_time.hashCode());
    result = prime * result + ((call_tpm_etf_out_time == null) ? 0 : call_tpm_etf_out_time.hashCode());
    result = prime * result + ((call_tpm_in_time == null) ? 0 : call_tpm_in_time.hashCode());
    result = prime * result + ((call_tpm_interval == null) ? 0 : call_tpm_interval.hashCode());
    result = prime * result + ((call_tpm_out_time == null) ? 0 : call_tpm_out_time.hashCode());
    result = prime * result + ((call_tpm_service_interval == null) ? 0 : call_tpm_service_interval.hashCode());
    result = prime * result + ((call_tpm_stf_in_time == null) ? 0 : call_tpm_stf_in_time.hashCode());
    result = prime * result + ((call_tpm_stf_out_time == null) ? 0 : call_tpm_stf_out_time.hashCode());
    result = prime * result + ((error_code == null) ? 0 : error_code.hashCode());
    result = prime * result + offset;
    return result;
  }

  @Override
  public String toString() {
    return "TPMSVCINFO{" +
        "offset=" + offset +
        ", call_service_name='" + call_service_name + '\'' +
        ", call_tpm_in_time='" + call_tpm_in_time + '\'' +
        ", call_tpm_out_time='" + call_tpm_out_time + '\'' +
        ", call_tpm_interval='" + call_tpm_interval + '\'' +
        ", call_tpm_stf_in_time='" + call_tpm_stf_in_time + '\'' +
        ", call_tpm_stf_out_time='" + call_tpm_stf_out_time + '\'' +
        ", call_tpm_etf_in_time='" + call_tpm_etf_in_time + '\'' +
        ", call_tpm_etf_out_time='" + call_tpm_etf_out_time + '\'' +
        ", call_tpm_service_interval='" + call_tpm_service_interval + '\'' +
        ", call_hostseq='" + call_hostseq + '\'' +
        ", call_orgseq='" + call_orgseq + '\'' +
        ", error_code='" + error_code + '\'' +
        ", call_location='" + call_location + '\'' +
        '}';
  }

  @Override
  public DTO clone() {
    TPMSVCINFO cloned = new TPMSVCINFO();

    // Copy all fields
    cloned.offset = this.offset;
    cloned.call_service_name = this.call_service_name;
    cloned.call_tpm_in_time = this.call_tpm_in_time;
    cloned.call_tpm_out_time = this.call_tpm_out_time;
    cloned.call_tpm_interval = this.call_tpm_interval;
    cloned.call_tpm_stf_in_time = this.call_tpm_stf_in_time;
    cloned.call_tpm_stf_out_time = this.call_tpm_stf_out_time;
    cloned.call_tpm_etf_in_time = this.call_tpm_etf_in_time;
    cloned.call_tpm_etf_out_time = this.call_tpm_etf_out_time;
    cloned.call_tpm_stf_etf_interval = this.call_tpm_stf_etf_interval;
    cloned.call_hostseq = this.call_hostseq;
    cloned.call_orgseq = this.call_orgseq;
    cloned.call_location = this.call_location;
    cloned.call_tpm_service_interval = this.call_tpm_service_interval;
    cloned.error_code = this.error_code;

    return cloned;
  }
}
