package com.skax.eatool.mbc.fc.foundation.bzcrudbus.kesa;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

// import org.dom4j.Attribute;
// import org.dom4j.Document;
// import org.dom4j.DocumentHelper;
// import org.dom4j.Element;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.Env;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Utils;

/**
 * KESA XML 관리 클래스
 * 
 * 프로그램명: KesaXmlManager.java
 * 설명: KESA 시스템과의 XML 통신을 관리하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - KESA XML 생성 및 변환
 * - MCI XML을 KESA XML로 변환
 * - 헤더 속성 관리
 * - XML 문서 처리
 * 
 * @version 1.0
 */
public class KesaXmlManager {

	/** 싱글톤 인스턴스 */
	private static KesaXmlManager instance;

	/** 헤더 속성 이름 맵 */
	private HashMap headerAttrNameMap = null;

	/** 헤더 서비스 속성 이름 맵 */
	private HashMap headerSvcAttrNameMap = null;

	/**
	 * 기본 생성자
	 * 설정에서 헤더 속성 이름들을 읽어와서 맵을 초기화한다.
	 */
	public KesaXmlManager() {
		headerAttrNameMap = new HashMap();
		headerSvcAttrNameMap = new HashMap();

		String headerIdnt = Env.getProperty("KesaXmlManager.HEADER_NAMES");
		String headerSvcIdnt = Env.getProperty("KesaXmlManager.HEADER_SVC_NAMES");
		String headerSep = Env.getProperty("KesaXmlManager.HEADER_SEPERATOR").trim();

		String[] attrArray = headerIdnt.split(headerSep);

		// 헤더 속성 맵에 저장
		for (int i = 0; i < attrArray.length; i++) {
			headerAttrNameMap.put(attrArray[i], null);
		}

		String[] attrArray2 = headerSvcIdnt.split(headerSep);

		// 헤더 서비스 속성 맵에 저장
		for (int j = 0; j < attrArray2.length; j++) {
			headerSvcAttrNameMap.put(attrArray2[j], null);
		}
	}

	/**
	 * 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return KesaXmlManager 인스턴스
	 */
	public static synchronized KesaXmlManager getInstance() {
		if (instance == null) {
			instance = new KesaXmlManager();
		}
		return instance;
	}

	/** 기본 XML 템플릿 1 */
	private final String sendXml1 = "<KB-Message><Header StndCicsTrncd=\"\" StndIntnlStndTelgmLen=\"\" StndTranBaseYmd=\"\" StndGuIdNo=\"ed91950c-8d48-4b16-9ba3-af94eae47708\" StndTelgmDmndDstcd=\"S\" StndTelgmFmatDstcd=\"\" StndTelgmValdYmd=\"";

	/** 기본 XML 템플릿 2 */
	private final String sendXml2 = "\"/><Common><TranInfo StndGroupCoCd=\"\" StndTelgmRecvTranCd=\"";

	/** 기본 XML 템플릿 3 */
	private final String sendXml3 = "\" StndPrcssRtdTranCd=\"\" StndScrenNo=\"";

	/** 기본 XML 템플릿 4 */
	private final String sendXml4 = "\" StndOsidInstiCd=\"\" StndTranSerno=\"000000000001\" StndInoPartlDstcd=\"1\" StndCmpxTranDmndDstcd=\"\" StndSysOperEvirnDstcd=\"T\" StndTranPtrnDstcd=\"\" StndRbndTranYn=\"\" StndTermlWaitRqstYn=\"\" StndOgtranRstrYn=\"\"/><ChnlInfo StndBnkCd=\"\" StndTranBrncd=\"0099\" StndRelayChnlDstcd=\"\" StndChnlDstcd=\"4004\" StndMdiaDstcd=\"TH\" StndLangDstcd=\"\" StndTrmno=\"71\" StndUserEmpid=\"3802737\" StndTermlOprtrno=\"\" StndTermlSpvsrNo=\"\" StndN2NdSpvsrBrncd=\"\" StndN2NdSpvsrTrmno=\"\"/><InMsgInfo StndInptMsgPtrnDstcd=\"\" StndInptMsgCtnnYn=\"\" StndInptMsgSerno=\"\" StndInptMsgWritYms=\"\"/><AthorInfo StndAthorFnshDstcd=\"\" StndSpvsrAthorDstcd=\"\" StndN1StSpvsrEmpid=\"\" StndN2NdSpvsrEmpid=\"\" StndSpvsrAResnNoitm=\"\" StndSpvsrAthorResnCd=\"\"/><EntrBnkBzwkCmn StndClsngAfYn=\"\" StndBnkbkTranYn=\"\" StndLsdtTranYn=\"\" StndIdtrek=\"\" StndSodBbrnPtrnDstcd=\"\" StndSodUserPtrnDstcd=\"\" StndInotAbilYn=\"\" StndNoRtaUserYn=\"\" StndTranDscnDmndYn=\"\" StndCallgPgmName=\"\" StndRecvLuName=\"\" StndCnclDstcd=\"\" StndCnclPtrnDstcd=\"\" StndTranCcResnDstcd=\"\" StndTranCcResnCtnt=\"\" StndOgtranYms=\"\" StndDscnTranDstcd=\"\" StndIdiviDataEdtYn=\"\" StndOpbrnCd=\"\"/><OutMsgInfo StndOutptDPtrnDstcd=\"\" StndOutptMsgPtrnDstcd=\"\" StndOutptMsgCtnnYn=\"\" StndOutptMsgSerno=\"\" StndOutptMsgWritYms=\"\" StndUserPaNotacrdYn=\"\"/><ErrInfo StndErrcd=\"\" StndTreatCd=\"\" StndCncutDscnRqstYn=\"\"/></Common></KB-Message>";

	/**
	 * 기본 템플릿으로 구성된 xml을 생성한다.
	 * 
	 * @param xmlVo KESA XML VO 객체
	 * @return 기본 XML 문자열
	 */
	private String getBaseXml(KesaXmlVO xmlVo) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(sendXml1);
		// 현재 날짜 설정
		strBuf.append(Utils.getSysDate(""));
		strBuf.append(sendXml2);
		// AS 이름 설정
		strBuf.append(xmlVo.getAsName());
		strBuf.append(sendXml3);

		strBuf.append(xmlVo.getAsName());
		strBuf.append(sendXml4);

		return strBuf.toString();
	}

	/**
	 * KESA 전송용 xml을 생성하여 반환한다.
	 * 
	 * @param xmlVo KESA XML VO 객체
	 * @return 생성된 XML 문자열
	 */
	public String createXml(KesaXmlVO xmlVo) {
		String retStr = "";
		try {
			// Document doc = DocumentHelper.parseText(getBaseXml(xmlVo));
			// Element root = doc.getRootElement();

			// Element individual = root.addElement(Constants.KESA_XML_INNODE_NAME1);

			// Element dataHeader = individual.addElement(Constants.KESA_XML_INNODE_NAME2);

			// Element inData = individual.addElement(Constants.KESA_XML_INNODE_NAME3);

			// Element paramElmt = inData.addElement(xmlVo.getElementName());
			// Element paramElmt = inData.addElement(Constants.KESA_XML_INNODE_NAME4);

			// HashMap<String, String> svcMap = xmlVo.getWafSvcMap();

			// HashMap<String, String> inDataMap = xmlVo.getParamDataMap();
			// Set set = inDataMap.keySet();

			// 파라미터별로 분류
			// String keyName = null;
			// for (Iterator itr = Utils.snapshotIterator(set); itr.hasNext();) {
			// keyName = (String) itr.next();

			// 파라미터와 헤더 속성이 일치하는 경우 헤더에 분류
			// if (headerAttrNameMap.containsKey(keyName)) {
			// dataHeader.addAttribute(keyName, (String) inDataMap.get(keyName));
			// } else if (headerSvcAttrNameMap.containsKey(keyName)) {
			// dataHeader.addAttribute(keyName, (String) svcMap.get(keyName));
			// }
			// InData에 분류
			// else {
			// paramElmt.addAttribute(keyName, (String) inDataMap.get(keyName));
			// }
			// }

			// retStr = doc.asXML();

		} catch (Exception e) {
			retStr = "";
		}
		return retStr;
	}

	/**
	 * MCI XML을 KESA XML로 변환한다.
	 * 
	 * @param mciXml MCI XML 문자열
	 * @param xmlVo  KESA XML VO 객체
	 * @return 변환된 KESA XML 문자열
	 */
	public String mci2Kesa(String mciXml, KesaXmlVO xmlVo) {
		String retStr = "";
		try {
			// Document kesaDoc = DocumentHelper.parseText(getBaseXml(xmlVo));
			// Element kesaRoot = kesaDoc.getRootElement();

			// // Element 인덱스 맵
			// HashMap<String, Element> kesaElmtMap = new HashMap<String, Element>();

			// Element individual = kesaRoot.addElement(Constants.KESA_XML_INNODE_NAME1);

			// Element dataHeader = individual.addElement(Constants.KESA_XML_INNODE_NAME2);

			// Element inData = individual.addElement(Constants.KESA_XML_INNODE_NAME3);
			// kesaElmtMap.put("InData", inData);

			// Document mciDoc = Dom4jUtil.getDocument(mciXml);

			// String mciRootPath = "/MciMessage/MsgBody";

			// List mciElmtList = ((Element)
			// mciDoc.selectSingleNode(mciRootPath)).elements();

			// Element mciElmt = null;
			// Map elmtMap = null;
			// String elmtName = null;
			// String elmtId = null;
			// String fldValue = null;
			// String kesaPrefixNode = "/KB-Message/Individual/";

			// for (Iterator itr = Utils.snapshotIterator(mciElmtList); itr.hasNext();) {
			// mciElmt = (Element) itr.next();
			// elmtName = mciElmt.getName();

			// elmtId = ((Attribute) mciElmt.attribute("id")).getStringValue();

			// // 필드가 아닌 경우 처리
			// if (elmtName.equals("Fld")) {
			// elmtId = ((Attribute) mciElmt.attribute("id")).getStringValue();
			// fldValue = ((Attribute) mciElmt.attribute("value")).getStringValue();

			// // System.out.println("FldId : " + fldId + ", FldValue : " + fldValue);
			// addFldValue(elmtId, fldValue, kesaDoc, kesaPrefixNode, kesaElmtMap);
			// }
			// // 그리드인 경우
			// else if (elmtName.equals("Grid")) {
			// addGridData(elmtId, mciElmt, kesaDoc, kesaPrefixNode, kesaElmtMap);
			// }

			// }

			// retStr = kesaDoc.asXML();
		} catch (Exception e) {
			retStr = "";
		}
		return retStr;

	}

	/**
	 * MCI의 Fld 데이터를 KESA 엘리먼트의 속성으로 변환하여 설정한다.
	 * 
	 * @param fldId          필드 ID
	 * @param fldValue       필드 값
	 * @param kesaDoc        KESA 문서
	 * @param kesaPrefixNode KESA 접두 노드
	 * @param kesaElmtMap    KESA 엘리먼트 맵
	 */
	/*
	 * public void addFldValue(String fldId, String fldValue, Document kesaDoc,
	 * String kesaPrefixNode,
	 * HashMap kesaElmtMap) {
	 * 
	 * String[] nodeArray = fldId.split("/");
	 * String attrName = nodeArray[nodeArray.length - 1];
	 * 
	 * StringBuffer strBuf = new StringBuffer();
	 * 
	 * for (int i = 0; i < nodeArray.length - 1; i++) {
	 * strBuf.append(nodeArray[i]);
	 * 
	 * if (i < nodeArray.length - 2) {
	 * strBuf.append("/");
	 * }
	 * }
	 * 
	 * String kesaNode = strBuf.toString();
	 * Element elmt = null;
	 * if (kesaElmtMap.containsKey(kesaNode)) {
	 * elmt = (Element) kesaElmtMap.get(kesaNode);
	 * } else {
	 * elmt = Dom4jUtil.getElement(kesaDoc, kesaPrefixNode + kesaNode, true);
	 * kesaElmtMap.put(kesaNode, elmt);
	 * }
	 * 
	 * elmt.addAttribute(attrName, fldValue);
	 * }
	 */
	public void addFldValue(String fldId, String fldValue, Object kesaDoc, String kesaPrefixNode,
			HashMap kesaElmtMap) {
		// TODO: Implement when dom4j library is available
	}

	/*
	 * public void addGridData(String gridId, Element mciGridElmt, Document kesaDoc,
	 * String kesaPrefixNode,
	 * HashMap kesaElmtMap) {
	 * String[] nodeArray = gridId.split("/");
	 * String rowElmtName = nodeArray[nodeArray.length - 1];
	 * 
	 * StringBuffer strBuf = new StringBuffer();
	 * 
	 * for (int i = 0; i < nodeArray.length - 1; i++) {
	 * strBuf.append(nodeArray[i]);
	 * 
	 * if (i < nodeArray.length - 2) {
	 * strBuf.append("/");
	 * }
	 * }
	 * 
	 * String kesaNode = strBuf.toString();
	 * Element kesaGridElmt = null;
	 * if (kesaElmtMap.containsKey(kesaNode)) {
	 * kesaGridElmt = (Element) kesaElmtMap.get(kesaNode);
	 * } else {
	 * kesaGridElmt = Dom4jUtil.getElement(kesaDoc, kesaPrefixNode + kesaNode,
	 * true);
	 * kesaElmtMap.put(kesaNode, kesaGridElmt);
	 * }
	 * 
	 * List mciRowElmtList = mciGridElmt.elements();
	 * Element mciRowElmt = null;
	 * Element mciColElmt = null;
	 * Element kesaRowElmt = null;
	 * List mciColElmtList = null;
	 * String colId = null;
	 * String colText = null;
	 * 
	 * for (Iterator itr = Utils.snapshotIterator(mciRowElmtList); itr.hasNext();) {
	 * mciRowElmt = (Element) itr.next();
	 * 
	 * mciColElmtList = mciRowElmt.elements();
	 * kesaRowElmt = kesaGridElmt.addElement(rowElmtName);
	 * for (Iterator itr2 = Utils.snapshotIterator(mciColElmtList); itr2.hasNext();)
	 * {
	 * mciColElmt = (Element) itr2.next();
	 * 
	 * colId = ((Attribute) mciColElmt.attribute("id")).getStringValue();
	 * colText = mciColElmt.getText();
	 * 
	 * kesaRowElmt.addAttribute(colId, colText);
	 * }
	 * }
	 * }
	 */
	public void addGridData(String gridId, Object mciGridElmt, Object kesaDoc, String kesaPrefixNode,
			HashMap kesaElmtMap) {
		// TODO: Implement when dom4j library is available
	}

	private byte[] getFileXml(String fileStr) {
		File f;
		byte readByte[];
		BufferedInputStream bin;
		f = new File(fileStr);
		readByte = new byte[(int) f.length()];
		bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(f), readByte.length);
			bin.read(readByte, 0, readByte.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return readByte;
	}

	public static void main(String[] args) {

		byte[] byteXml = KesaXmlManager.getInstance().getFileXml("c:/mci.xml");

		KesaXmlVO kesaXmlVo = new KesaXmlVO();
		kesaXmlVo.setAsName("MBC75001");

		System.out.println("KESA XML : " + KesaXmlManager.getInstance().mci2Kesa(new String(byteXml), kesaXmlVo));
	}
}
