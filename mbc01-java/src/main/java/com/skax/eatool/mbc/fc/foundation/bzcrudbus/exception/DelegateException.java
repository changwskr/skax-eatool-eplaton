package com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception;

/**
 * 델리게이트 예외 클래스
 * 
 * 프로그램명: DelegateException.java
 * 설명: 델리게이트 패턴 관련 예외를 처리하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 델리게이트 관련 예외 처리
 * - 에러 메시지 관리
 */
public class DelegateException extends Exception {

	/**
	 * 기본 생성자
	 */
	public DelegateException() {
		super();
	}

	/**
	 * 에러 메시지를 받는 생성자
	 * 
	 * @param errMsg 에러 메시지
	 */
	public DelegateException(String errMsg) {
		super(errMsg);
	}

}
