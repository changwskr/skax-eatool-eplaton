package com.skax.eatool.mbc.ac.eplatonac;


/**
 * =============================================================================
 * 프로그램 명:
 * =============================================================================
 * 이 클래스는 business layer와 application layer 사이에서 주고 받는
 * 정보를 담는다.<br>
 * Application layer에서 business layer로 전달하는 정보는 request field에 담기고,
 * Business layer에서 application layer로 전달하는 정보는 response field에 담긴다.
 * 그리고 Application layer에서 business layer로 전달하는 request와 함께 오는
 * 공통 정보는 common field에 담긴다.
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 */

public final class EPlatonEvent implements IEvent {

    private String action;
    private EPlatonCommonDTO common;
    private TPSVCINFODTO tpmsvc;
    private IDTO request;
    private IDTO response;
    private String source;
    private String type;
    private Object data;

    /**
     * 기본 생성자
     */
    public EPlatonEvent() {
        this.action = "xxxxxxxx";
        this.common = new EPlatonCommonDTO();
        this.tpmsvc = new TPSVCINFODTO();
    }

    /**
     * 모든 request와 함께 오는 공통 정보를 리턴한다.<br>
     *
     * @return EPlatonCommonDTO 공통 정보
     */
    public EPlatonCommonDTO getCommon() {
        return common;
    }

    /**
     * 모든 request와 함께 오는 공통 정보를 지정한다.<br>
     *
     * @param common 공통 정보
     */
    public void setCommon(EPlatonCommonDTO common) {
        this.common = common;
    }

    public TPSVCINFODTO getTPSVCINFODTO() {
        return tpmsvc;
    }

    /**
     * 모든 request와 함께 오는 공통 정보를 지정한다.<br>
     *
     * @param tpmsvc 공통 정보
     */
    public void setTPSVCINFO(TPSVCINFODTO tpmsvc) {
        this.tpmsvc = tpmsvc;
    }

    public void setTPSVCINFODTO(TPSVCINFODTO tpmsvc) {
        this.tpmsvc = tpmsvc;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#setAction(String)
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#getAction()
     */
    public String getAction() {
        return action;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#setRequest(IDTO)
     */
    public void setRequest(IDTO request) {
        this.request = request;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#getRequest()
     */
    public IDTO getRequest() {
        return request;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#setResponse(IDTO)
     */
    public void setResponse(IDTO response) {
        this.response = response;
    }

    /**
     * @see com.skcc.oversea.framework.transfer.IEvent#getResponse()
     */
    public IDTO getResponse() {
        return response;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EPlatonEvent {");
        sb.append(System.lineSeparator());
        sb.append("  source: ").append(source);
        sb.append(System.lineSeparator());
        sb.append("  type: ").append(type);
        sb.append(System.lineSeparator());
        sb.append("  data: ").append(data);
        sb.append(System.lineSeparator());
        sb.append("}");
        return sb.toString();
    }
}
