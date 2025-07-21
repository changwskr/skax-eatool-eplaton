package com.skax.eatool.mbc.fc.foundation.bzcrudbus.kesa;

import java.util.HashMap;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.Env;

/**
 * KESA XML VO 클래스
 * 
 * 프로그램명: KesaXmlVO.java
 * 설명: KESA XML 통신에 사용되는 Value Object 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - KESA XML 데이터 관리
 * - AS 이름 및 서버 정보 관리
 * - 파라미터 데이터 맵 관리
 * - WAF 서비스 맵 관리
 * 
 * @version 1.0
 */
public class KesaXmlVO {

	/** AS 이름 */
	private String asName = null;

	/** Server 이름 */
	private String hostServer = null;

	/** InData하위 DTO(Element)의 이름 */
	private String elementName = null;

	/** WAF 서비스 맵 */
	private HashMap<String, String> wafSvcMap = null;

	/** InData 하위 Attribute들의 맵 */
	private HashMap<String, String> paramDataMap = null;

	/**
	 * 기본 생성자
	 */
	public KesaXmlVO() {
	}

	/**
	 * 파라미터를 받는 생성자
	 * 
	 * @param hostServer   호스트 서버명
	 * @param asName       AS 이름
	 * @param elementName  엘리먼트 이름
	 * @param paramDataMap 파라미터 데이터 맵
	 */
	public KesaXmlVO(String hostServer, String asName, String elementName, HashMap<String, String> paramDataMap) {
		wafSvcMap = new HashMap();
		wafSvcMap.put(Env.getProperty("KesaXmlVO.HOST_SERVER_PNAME"), hostServer);
		wafSvcMap.put(Env.getProperty("KesaXmlVO.HOST_AS_PNAME"), asName);

		this.asName = asName;
		this.hostServer = hostServer;
		this.elementName = elementName;
		this.paramDataMap = paramDataMap;
	}

	/**
	 * WAF 서비스 맵을 반환한다.
	 * 
	 * @return WAF 서비스 맵
	 */
	public HashMap<String, String> getWafSvcMap() {
		return this.wafSvcMap;
	}

	/**
	 * WAF 서비스 맵을 설정한다.
	 * 
	 * @param wafSvcMap WAF 서비스 맵
	 */
	public void setWafSvcMap(HashMap<String, String> wafSvcMap) {
		this.wafSvcMap = wafSvcMap;
	}

	/**
	 * AS 이름을 반환한다.
	 * 
	 * @return AS 이름
	 */
	public String getAsName() {
		return this.asName;
	}

	/**
	 * AS 이름을 설정한다.
	 * 
	 * @param asName AS 이름
	 */
	public void setAsName(String asName) {
		this.asName = asName;
	}

	/**
	 * 호스트 서버명을 반환한다.
	 * 
	 * @return 호스트 서버명
	 */
	public String getHostServer() {
		return this.hostServer;
	}

	/**
	 * 호스트 서버명을 설정한다.
	 * 
	 * @param hostServer 호스트 서버명
	 */
	public void setHostServer(String hostServer) {
		this.hostServer = hostServer;
	}

	/**
	 * 파라미터 데이터 맵을 반환한다.
	 * 
	 * @return 파라미터 데이터 맵
	 */
	public HashMap<String, String> getParamDataMap() {
		return this.paramDataMap;
	}

	/**
	 * 파라미터 데이터 맵을 설정한다.
	 * 
	 * @param paramDataMap 파라미터 데이터 맵
	 */
	public void setParamDataMap(HashMap<String, String> paramDataMap) {
		this.paramDataMap = paramDataMap;
	}

	/**
	 * 엘리먼트 이름을 반환한다.
	 * 
	 * @return 엘리먼트 이름
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * 엘리먼트 이름을 설정한다.
	 * 
	 * @param elementName 엘리먼트 이름
	 */
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
}
