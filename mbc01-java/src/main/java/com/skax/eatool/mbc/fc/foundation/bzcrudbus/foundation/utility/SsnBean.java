package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 세션 관리 유틸리티 클래스
 * 
 * 프로그램명: SsnBean.java
 * 설명: HTTP 세션에서 사용자 정보와 메시지 데이터를 관리하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 ID 관리
 * - 사용자 이름 관리
 * - 에러 메시지 데이터 관리
 * - 세션 데이터 초기화
 * 
 * @version 1.0
 */
public class SsnBean {

	/**
	 * 기본 생성자
	 */
	public SsnBean() {
	}

	/**
	 * 세션에서 사용자 ID를 반환하는 메서드
	 * 
	 * @param req HTTP 요청 객체
	 * @return 사용자 ID 문자열
	 */
	public static String getUsrId(HttpServletRequest req) {
		return (String) req.getSession(true).getAttribute("USR_ID");
	}

	/**
	 * 세션에 사용자 ID를 설정하는 메서드
	 * 
	 * @param req   HTTP 요청 객체
	 * @param usrId 사용자 ID
	 */
	public static void setUsrId(HttpServletRequest req, String usrId) {
		req.getSession(true).setAttribute("USR_ID", usrId);
	}

	/**
	 * 세션에서 사용자 이름을 반환하는 메서드
	 * 
	 * @param req HTTP 요청 객체
	 * @return 사용자 이름 문자열
	 */
	public static String getUsrName(HttpServletRequest req) {
		return (String) req.getSession(true).getAttribute("USR_NM");
	}

	/**
	 * 세션에 사용자 이름을 설정하는 메서드
	 * 
	 * @param req     HTTP 요청 객체
	 * @param usrName 사용자 이름
	 */
	public static void setUsrName(HttpServletRequest req, String usrName) {
		req.getSession(true).setAttribute("USR_NM", usrName);
	}

	/**
	 * 세션에서 에러 메시지 데이터를 반환하는 메서드
	 * 
	 * @param req HTTP 요청 객체
	 * @return 에러 메시지 맵
	 */
	public static HashMap getMsgData(HttpServletRequest req) {
		return (HashMap) req.getSession(true).getAttribute("ERROR_MSG");
	}

	/**
	 * 세션에 에러 메시지 데이터를 설정하는 메서드
	 * 
	 * @param req    HTTP 요청 객체
	 * @param msgMap 메시지 맵
	 */
	public static void setMsgData(HttpServletRequest req, HashMap msgMap) {
		// req.getSession(true).removeAttribute("ERROR_MSG");
		if (getMsgData(req) == null) {
			req.getSession(true).setAttribute("ERROR_MSG", msgMap);
		}
	}

	/**
	 * 세션에서 에러 메시지 데이터를 초기화하는 메서드
	 * 
	 * @param req HTTP 요청 객체
	 */
	public static void clearMsgData(HttpServletRequest req) {
		req.getSession(true).removeAttribute("ERROR_MSG");
	}
}
