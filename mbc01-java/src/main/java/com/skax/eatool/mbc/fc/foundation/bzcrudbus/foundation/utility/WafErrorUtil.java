package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.util.HashMap;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;

/**
 * WAF 에러 처리 유틸리티 클래스
 * 
 * 프로그램명: WafErrorUtil.java
 * 설명: WAF(Web Application Firewall) 관련 에러 정보를 설정하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 에러 정보 설정
 * - 예외 객체 처리
 * - 에러 코드 및 메시지 관리
 * 
 * @version 1.0
 */
public class WafErrorUtil {

	/**
	 * 에러 정보를 설정하여 반환한다.
	 * 
	 * @param errorMap 에러 맵
	 * @param errorCd  에러 코드
	 * @param ex       예외 객체
	 * @return 설정된 에러 맵
	 */
	public static HashMap setErrorInfo(HashMap errorMap, String errorCd, Exception ex) {

		if (errorMap == null) {
			errorMap = new HashMap();
		}

		if (ex == null) {
			return errorMap;
		}

		errorMap.put(Constants.ERROR_OCCUR_VAR, "Y");
		errorMap.put(Constants.ERROR_MSG_CD_VAR, errorCd);
		errorMap.put(Constants.ERROR_MSG_TEXT_VAR, ex.getMessage());
		errorMap.put(Constants.ERROR_OBJECT, ex);

		return errorMap;
	}
}
