package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.util.HashMap;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.IFRSConfig;

/**
 * 에러 체크 유틸리티 클래스
 * 
 * 프로그램명: ErrorCheckUtil.java
 * 설명: 에러 코드를 체크하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 에러 코드 검증
 * - 에러 문자 맵 관리
 * - 대소문자 구분 없는 에러 체크
 * 
 * @version 1.0
 */
public class ErrorCheckUtil {

	/** 싱글톤 인스턴스 */
	private static ErrorCheckUtil instance;

	/** 에러 문자 맵 */
	private HashMap errorCharMap = null;

	/**
	 * 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return ErrorCheckUtil 인스턴스
	 */
	public static synchronized ErrorCheckUtil getInstance() {
		if (instance == null) {
			instance = new ErrorCheckUtil();
		}
		return instance;
	}

	/**
	 * 기본 생성자
	 * 설정에서 에러 문자 리스트를 읽어와서 맵을 초기화한다.
	 */
	public ErrorCheckUtil() {
		String charList = IFRSConfig.getInstance().getInfo("ErrorCode", "CharList");
		errorCharMap = new HashMap();
		String[] charArray = charList.split("/");

		String charStr = null;
		for (int i = 0; i < charArray.length; i++) {
			charStr = charArray[i];
			errorCharMap.put(charStr, charStr);
			errorCharMap.put(charStr.toLowerCase(), charStr);
			errorCharMap.put(charStr.toUpperCase(), charStr);
		}
	}

	/**
	 * 문자열 에러 코드가 에러인지 확인한다.
	 * 
	 * @param errCode 에러 코드 문자열
	 * @return 에러이면 true, 아니면 false
	 */
	public boolean isError(String errCode) {
		if (errCode == null || errCode.length() < 1) {
			return false;
		}

		return isError(errCode.charAt(0));
	}

	/**
	 * 문자 에러 코드가 에러인지 확인한다.
	 * 
	 * @param errCode 에러 코드 문자
	 * @return 에러이면 true, 아니면 false
	 */
	public boolean isError(char errCode) {
		String errorCode = Character.toString(errCode);
		return errorCharMap.containsKey(errorCode);
	}
}
