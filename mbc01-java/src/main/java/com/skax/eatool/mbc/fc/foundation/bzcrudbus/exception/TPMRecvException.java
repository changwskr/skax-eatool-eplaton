package com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception;

/**
 * TPM 수신 예외 클래스
 * 
 * 프로그램명: TPMRecvException.java
 * 설명: TPM(Transaction Processing Monitor) 수신 관련 예외를 처리하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - TPM 수신 관련 예외 처리
 * - 에러 코드 관리
 * - 에러 메시지 관리
 */
public class TPMRecvException extends Exception {

	/**
	 * 직렬화 버전 ID
	 */
	private static final long serialVersionUID = 1L;

	/** 에러 코드 */
	protected String errorCode = "ETPMRECV";

	/**
	 * 기본 생성자
	 */
	public TPMRecvException() {
		super();
	}

	/**
	 * 에러 메시지를 받는 생성자
	 * 
	 * @param errorMsg 에러 메시지
	 */
	public TPMRecvException(String errorMsg) {
		super(errorMsg);
	}

	/**
	 * 에러 코드와 메시지를 받는 생성자
	 * 
	 * @param errorCode 에러 코드
	 * @param errorMsg  에러 메시지
	 */
	public TPMRecvException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	/**
	 * 에러 코드를 반환한다.
	 * 
	 * @return 에러 코드
	 */
	public String getErrorCode() {
		return this.errorCode;
	}
}
