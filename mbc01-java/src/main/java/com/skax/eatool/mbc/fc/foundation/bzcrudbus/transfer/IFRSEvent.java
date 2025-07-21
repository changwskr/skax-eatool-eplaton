package com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer;

// import javax.servlet.http.HttpServletRequest;
// import com.kbstar.ksa.infra.po.KBData;

/**
 * IFRS 이벤트 클래스
 * 
 * 프로그램명: IFRSEvent.java
 * 설명: 이 클래스는 business layer와 application layer 사이에 데이터를 주고받는
 * 컨테이너 역할을 한다.<br>
 * Application layer에서 business layer로 전달되는 데이터는 request field에 저장되고,
 * Business layer에서 application layer로 전달되는 데이터는 response field에 저장된다.
 * 또한, Application layer에서 business layer로 전달되는 request에는 항상 공통적으로 사용되는
 * 공통 데이터가 common field에 저장된다.
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 공통 DTO 관리
 * - KBData 관리
 * - HTTP 서블릿 요청 관리
 * - 예외 처리
 * 
 * @version 1.0
 */
public class IFRSEvent {

	/** IFRS 공통 DTO */
	private IFRSCommonDTO commonDto = null;

	/** 요청 데이터 */
	// private KBData request = null;

	/** 응답 데이터 */
	// private KBData response = null;

	/** HTTP 서블릿 요청 */
	// private HttpServletRequest httpServletRequest = null;

	/** 예외 객체 */
	private Exception exception = null;

	/**
	 * 기본 생성자
	 */
	public IFRSEvent() {
	}

	/**
	 * IFRS 공통 DTO를 반환한다.
	 * 
	 * @return IFRS 공통 DTO
	 */
	public IFRSCommonDTO getCommonDto() {
		return commonDto;
	}

	/**
	 * IFRS 공통 DTO를 설정한다.
	 * 
	 * @param commonDto IFRS 공통 DTO
	 */
	public void setCommonDto(IFRSCommonDTO commonDto) {
		this.commonDto = commonDto;
	}

	/**
	 * 예외 객체를 반환한다.
	 * 
	 * @return 예외 객체
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * 예외 객체를 설정한다.
	 * 
	 * @param exception 예외 객체
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * HTTP 서블릿 요청을 반환한다.
	 * 
	 * @return HTTP 서블릿 요청
	 */
	public Object getServletRequest() {
		// TODO: Implement when servlet functionality is available
		return null;
	}

	/*
	 * 나머지 메서드들은 주석 처리
	 */
}
