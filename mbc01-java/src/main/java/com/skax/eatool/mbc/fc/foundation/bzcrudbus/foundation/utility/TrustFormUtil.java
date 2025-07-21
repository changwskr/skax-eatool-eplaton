/**
 * 신뢰할 수 있는 폼 처리 유틸리티 클래스
 * 
 * 프로그램명: TrustFormUtil.java
 * 설명: 웹 폼 데이터 처리를 위한 유틸리티 클래스
 * 작성일: 2007.09.03
 * 작성자: SKAX Project Team
 * 수정일: 2007.09.04 (MergeHashMap, MergeSession 메서드 추가)
 * 수정일: 2007.09.10 (Split 메서드 추가)
 * 
 * 주요 기능:
 * - HTTP 요청 파라미터 처리
 * - 멀티파트 데이터 처리
 * - 세션 데이터 병합
 * - XML 생성
 * - 그리드 데이터 처리
 * 
 * @version 1.0
 */

package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;
// import kr.co.comsquare.db.DBManager;
// import kr.co.comsquare.db.SaveResultSet;
// import kr.co.comsquare.rwXmlLib.RwXml;
// import kr.co.comsquare.rwXmlLib.RwXmlLib;
// import kr.co.comsquare.util.StringUtil;
// import kr.co.comsquare.util.Tokenizer;

// import com.oreilly.servlet.multipart.FilePart;
// import com.oreilly.servlet.multipart.MultipartParser;
// import com.oreilly.servlet.multipart.ParamPart;
// import com.oreilly.servlet.multipart.Part;

/**
 * Trust Form 유틸리티 클래스
 * 
 * 프로그램명: TrustFormUtil.java
 * 설명: Trust Form 처리를 위한 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - Trust Form 처리
 * - 파일 업로드/다운로드
 * - 데이터 변환
 * 
 * @version 1.0
 */
public class TrustFormUtil {

	/**
	 * 기본 생성자
	 */
	private TrustFormUtil() {
	}

	/*
	 * 모든 메서드들은 주석 처리
	 */

	/**
	 * request의 파라미터들을 HashMap으로 변환
	 * 
	 * @param request HTTP 요청 객체
	 * @return 파라미터 맵
	 */
	/*
	 * public HashMap RequestTOHashMap(HttpServletRequest request) {
	 * HashMap RequestHashMap = new HashMap(); // Return 할 데이터 HashMap
	 * 
	 * // 리퀘스트의 모든 파라미터들을 Enumeration객체로 생성
	 * Enumeration requestEnumeration = request.getParameterNames();
	 * 
	 * while (requestEnumeration.hasMoreElements()) {
	 * String strEnumeration = (String) requestEnumeration.nextElement();
	 * 
	 * // Enumeration객체에서 하나씩 꺼내서 HashMap에 저장
	 * RequestHashMap.put(strEnumeration, (String)
	 * request.getParameter(strEnumeration));
	 * }
	 * // 리퀘스트에서 받은 HashMap과 세션에 저장된 데이터들을 Merge
	 * RequestHashMap = MergeSession(request, RequestHashMap);
	 * 
	 * // 세션 유지 시간 설정
	 * HttpSession session = request.getSession(false);
	 * session.setMaxInactiveInterval(MAX_SESSION_TIME);
	 * 
	 * request.setAttribute("success", "");
	 * request.setAttribute("fail", "");
	 * 
	 * try {
	 * 
	 * System.out.println(
	 * "======================================================================");
	 * 
	 * for (Iterator all = RequestHashMap.keySet().iterator(); all
	 * .hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * System.out.println(each + " : "
	 * + RequestHashMap.get(each).toString().trim());
	 * } catch (NullPointerException e) {
	 * System.out.println(each + " : NULL");
	 * }
	 * }
	 * 
	 * System.out.println(
	 * "======================================================================");
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }
	 * return RequestHashMap;
	 * 
	 * }
	 */
	public HashMap RequestTOHashMap(Object request) {
		// TODO: Implement when servlet API is available
		return new HashMap();
	}

	/**
	 * MultiPart로 전송된 파라미터들을 HashMap으로 변환
	 * 
	 * @param request HTTP 요청 객체
	 * @return 파라미터 맵
	 * @throws IOException IO 예외
	 */
	/*
	 * public HashMap MultiPartTOHashMap(HttpServletRequest request)
	 * throws IOException {
	 * 
	 * HashMap RequestHashMap = new HashMap(); // Return 할 데이터 HashMap
	 * 
	 * ClassLoader load = getClass().getClassLoader();
	 * InputStream in = load
	 * .getResourceAsStream("kdic/dbas/common/upload/upload.properties");
	 * Properties property = new Properties();
	 * 
	 * try {
	 * property.load(in);
	 * } catch (IOException e) {
	 * e.printStackTrace();
	 * }
	 * 
	 * int maxSize = Integer.parseInt((String) property.get("maxSize"));
	 * 
	 * MultipartParser mp = new MultipartParser(request, maxSize, true, true,
	 * "UTF-8");
	 * Part part;
	 * 
	 * while ((part = mp.readNextPart()) != null) {
	 * String name = part.getName();
	 * 
	 * // 파라미터 처리
	 * if (part.isParam()) {
	 * ParamPart paramPart = (ParamPart) part;
	 * RequestHashMap.put(name, paramPart.getStringValue());
	 * }
	 * 
	 * }
	 * 
	 * // 리퀘스트에서 받은 HashMap과 세션에 저장된 데이터들을 Merge
	 * RequestHashMap = MergeSession(request, RequestHashMap);
	 * 
	 * request.setAttribute("success", "");
	 * request.setAttribute("fail", "");
	 * 
	 * return RequestHashMap;
	 * 
	 * }
	 */
	public HashMap MultiPartTOHashMap(Object request) throws IOException {
		// TODO: Implement when servlet API and multipart libraries are available
		return new HashMap();
	}

	/**
	 * 그리드에서 전송된 데이터를 HashMap으로 변환하여 리턴
	 * param : 그리드에서 전송된 데이터 파라미터명
	 * mode : i(그리드에 추가할 데이터), u(그리드에서 수정할 데이터), d(그리드에서 삭제할 데이터)를 구분하는 파라미터명
	 * 
	 * @param request HTTP 요청 객체
	 * @param param   파라미터명
	 * @return 결과 맵
	 */
	/*
	 * public HashMap getRs(HttpServletRequest request, String param) {
	 * 
	 * String data = request.getParameter(param);
	 * 
	 * SaveResultSet insertRs = new SaveResultSet();
	 * SaveResultSet updateRs = new SaveResultSet();
	 * SaveResultSet deleteRs = new SaveResultSet();
	 * 
	 * SaveResultSet result = new SaveResultSet();
	 * 
	 * ArrayList list = new ArrayList();
	 * 
	 * HashMap resultMap = new HashMap();
	 * 
	 * // 화면에서 전송된 데이터를 각flag별로 구분하여 Tokenizer를 사용해서 분리
	 * Tokenizer.getSaveResultSet(data, insertRs, updateRs, deleteRs);
	 * 
	 * result = insertRs;
	 * 
	 * // 그리드에서 전송된 데이터 중 insert에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("i", list);
	 * }
	 * 
	 * result = updateRs;
	 * list = new ArrayList();
	 * 
	 * // 그리드에서 전송된 데이터 중 update에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * 
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("u", list);
	 * }
	 * 
	 * result = deleteRs;
	 * list = new ArrayList();
	 * 
	 * // 그리드에서 전송된 데이터 중 delete에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * 
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("d", list);
	 * }
	 * 
	 * return resultMap;
	 * }
	 */
	public HashMap getRs(Object request, String param) {
		// TODO: Implement when servlet API and grid libraries are available
		return new HashMap();
	}

	/**
	 * 그리드에서 전송된 데이터를 HashMap으로 변환하여 리턴
	 * param : 그리드에서 전송된 데이터 파라미터명
	 * mode : i(그리드에 추가할 데이터), u(그리드에서 수정할 데이터), d(그리드에서 삭제할 데이터)를 구분하는 파라미터명
	 * 
	 * @param request  HTTP 요청 객체
	 * @param inputmap 입력 맵
	 * @param param    파라미터명
	 * @return 결과 맵
	 */
	/*
	 * public HashMap getRs(HttpServletRequest request, HashMap inputmap,
	 * String param) {
	 * 
	 * Part part;
	 * 
	 * String data = inputmap.get(param).toString().trim();
	 * 
	 * SaveResultSet insertRs = new SaveResultSet();
	 * SaveResultSet updateRs = new SaveResultSet();
	 * SaveResultSet deleteRs = new SaveResultSet();
	 * 
	 * SaveResultSet result = new SaveResultSet();
	 * 
	 * ArrayList list = new ArrayList();
	 * 
	 * HashMap resultMap = new HashMap();
	 * 
	 * // 화면에서 전송된 데이터를 각flag별로 구분하여 Tokenizer를 사용해서 분리
	 * Tokenizer.getSaveResultSet(data, insertRs, updateRs, deleteRs);
	 * 
	 * result = insertRs;
	 * 
	 * // 그리드에서 전송된 데이터 중 insert에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("i", list);
	 * }
	 * 
	 * result = updateRs;
	 * list = new ArrayList();
	 * 
	 * // 그리드에서 전송된 데이터 중 update에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * 
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("u", list);
	 * }
	 * 
	 * result = deleteRs;
	 * list = new ArrayList();
	 * 
	 * // 그리드에서 전송된 데이터 중 delete에 해당하는 데이터를 List로 변환
	 * if (result.getTotalRow() > 0) {
	 * 
	 * String[] arr = result.getColNameArray();
	 * 
	 * while (result.next()) {
	 * HashMap map = new HashMap();
	 * 
	 * for (int i = 0; i < arr.length; i++) {
	 * map.put(arr[i], result.get(arr[i]));
	 * }
	 * 
	 * // 그리드의 col데이터와 세션 HashMap을 병합하여 추가
	 * map = MergeSession(request, map);
	 * 
	 * list.add(map);
	 * }
	 * resultMap.put("d", list);
	 * }
	 * 
	 * return resultMap;
	 * }
	 */
	public HashMap getRs(Object request, HashMap inputmap, String param) {
		// TODO: Implement when servlet API and grid libraries are available
		return new HashMap();
	}

	/**
	 * 2개의 HashMap을 병합하여 새로운 HashMap을 반환
	 */
	public HashMap MergeHashMap(HashMap merge_A, HashMap merge_B) {

		for (Iterator all = merge_A.keySet().iterator(); all.hasNext();) {
			String each = (String) all.next();
			try {
				merge_B.put(each, merge_A.get(each).toString().trim());
			} catch (Exception e) {
				merge_B.put(each, "");
			}
		}

		return merge_B;

	}

	/**
	 * HashMap에 세션 데이터를 추가하여 반환
	 */
	/*
	 * public HashMap MergeSession(HttpServletRequest request, HashMap result) {
	 * 
	 * HttpSession session = request.getSession(false);
	 * 
	 * // session 객체에서 모든 속성 이름을 Enumeration객체로 생성
	 * Enumeration sessionEnumeration = session.getAttributeNames();
	 * 
	 * while (sessionEnumeration.hasMoreElements()) {
	 * String strEnumeration = (String) sessionEnumeration.nextElement();
	 * // Enumeration객체에서 하나씩 꺼내서 HashMap에 저장
	 * try {
	 * result.put(strEnumeration, (String) session
	 * .getAttribute(strEnumeration));
	 * } catch (Exception e) {
	 * continue;
	 * }
	 * }
	 * // 세션 데이터 추가 후 기본값 설정
	 * if (result.get("SESSION_USR_ID") == null) {
	 * result.put("SESSION_USR_ID", "2054012");
	 * }
	 * if (result.get("SESSION_DEPT_CD") == null) {
	 * result.put("SESSION_DEPT_CD", "EE100");
	 * }
	 * if (result.get("SESSION_USR_NM") == null) {
	 * result.put("SESSION_USR_NM", "̽");
	 * }
	 * return result;
	 * }
	 */
	public HashMap MergeSession(Object request, HashMap result) {
		// TODO: Implement when servlet API is available
		// 세션 데이터 추가 후 기본값 설정
		if (result.get("SESSION_USR_ID") == null) {
			result.put("SESSION_USR_ID", "2054012");
		}
		if (result.get("SESSION_DEPT_CD") == null) {
			result.put("SESSION_DEPT_CD", "EE100");
		}
		if (result.get("SESSION_USR_NM") == null) {
			result.put("SESSION_USR_NM", "테스트");
		}
		return result;
	}

	/**
	 * 프로시저 입력 파라미터를 생성하여 반환
	 */
	/*
	 * public static HashMap ProcedureInpam(HttpServletRequest request,
	 * HashMap inpam, String param) {
	 * 
	 * inpam.put("strInParams", param);
	 * inpam.put("strUserId", inpam.get("SESSION_USR_ID").toString().trim());
	 * inpam.put("strUserIp", request.getLocalAddr());
	 * inpam.put("strHptr", "");
	 * inpam.put("lngRtn", "");
	 * inpam.put("strCount", "");
	 * inpam.put("strDesc", "");
	 * 
	 * return inpam;
	 * }
	 */
	public static HashMap ProcedureInpam(Object request, HashMap inpam, String param) {
		// TODO: Implement when servlet API is available
		inpam.put("strInParams", param);
		inpam.put("strUserId", inpam.get("SESSION_USR_ID").toString().trim());
		inpam.put("strUserIp", "127.0.0.1");
		inpam.put("strHptr", "");
		inpam.put("lngRtn", "");
		inpam.put("strCount", "");
		inpam.put("strDesc", "");

		return inpam;
	}

	/**
	 * 문자열을 지정된 키로 분리하여 ArrayList로 반환
	 */
	public ArrayList Split(String before, String key) {

		ArrayList result = new ArrayList();

		String temp[] = before.split(key);

		for (int i = 0; i < temp.length; i++) {
			result.add(temp[i]);
		}

		return result;
	}

	// 그리드 데이터를 XML로 변환하는 메서드
	/*
	 * public static String makeGridXml(String node, List list) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * int res2 = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * 
	 * for (int i = 0; i < list.size(); i++) {
	 * 
	 * HashMap map = (HashMap) list.get(i);
	 * 
	 * res2 = rx.add(res, "list", "");
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res2, each, map.get(each).toString(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res2, each, "");
	 * }
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeGridXml(String node, List list) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "></" + node + ">";
	}

	// 콤보박스 데이터를 모두 포함하는 XML을 생성하는 메서드 (옵션 추가)
	/*
	 * public static String makeComboAllXml(String node, List list) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * int res2 = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * 
	 * res2 = rx.add(res, "combo", "");
	 * rx.add(res2, "LABEL", "전체");
	 * rx.add(res2, "VALUE", "");
	 * 
	 * for (int i = 0; i < list.size(); i++) {
	 * 
	 * HashMap map = (HashMap) list.get(i);
	 * 
	 * res2 = rx.add(res, "combo", "");
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res2, each, map.get(each).toString().trim(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res2, each, "");
	 * }
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeComboAllXml(String node, List list) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "><combo><LABEL>전체</LABEL><VALUE></VALUE></combo></" + node + ">";
	}

	// 콤보박스 데이터를 XML로 변환하는 메서드
	/*
	 * public static String makeComboXml(String node, List list) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * int res2 = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * 
	 * for (int i = 0; i < list.size(); i++) {
	 * 
	 * HashMap map = (HashMap) list.get(i);
	 * 
	 * res2 = rx.add(res, "combo", "");
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res2, each, map.get(each).toString().trim(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res2, each, "");
	 * }
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeComboXml(String node, List list) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "></" + node + ">";
	}

	// 콤보박스 데이터를 XML로 변환하는 메서드
	/*
	 * public static String makeComboXml(String node, List list, boolean total) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * int res2 = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * 
	 * if (total) {
	 * res2 = rx.add(res, "combo", "");
	 * rx.add(res2, "LABEL", "전체");
	 * rx.add(res2, "VALUE", "");
	 * }
	 * 
	 * for (int i = 0; i < list.size(); i++) {
	 * 
	 * HashMap map = (HashMap) list.get(i);
	 * 
	 * res2 = rx.add(res, "combo", "");
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res2, each, map.get(each).toString().trim(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res2, each, "");
	 * }
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeComboXml(String node, List list, boolean total) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "></" + node + ">";
	}

	/*
	 * public static String makeXml(String node, HashMap map) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res, each, map.get(each).toString().trim(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res, each, "");
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeXml(String node, HashMap map) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "></" + node + ">";
	}

	/*
	 * public static String makeSingleXml(String node, List list) {
	 * 
	 * String flushXML = "";
	 * 
	 * RwXml rx = new RwXml();
	 * rx.setEncoding("UTF-8");
	 * int root = RwXml.rootNodeID;
	 * int res = 0;
	 * 
	 * res = rx.add(root, node, "");
	 * HashMap map = (HashMap) list.get(0);
	 * 
	 * for (Iterator all = map.keySet().iterator(); all.hasNext();) {
	 * String each = (String) all.next();
	 * try {
	 * rx.add(res, each, map.get(each).toString().trim(), true);
	 * } catch (NullPointerException e) {
	 * rx.add(res, each, "");
	 * }
	 * }
	 * 
	 * flushXML = rx.xmlFlush();
	 * flushXML += rx.xmlEndFlush();
	 * 
	 * return flushXML;
	 * }
	 */
	public static String makeSingleXml(String node, List list) {
		// TODO: Implement when RwXml library is available
		return "<" + node + "></" + node + ">";
	}
}
