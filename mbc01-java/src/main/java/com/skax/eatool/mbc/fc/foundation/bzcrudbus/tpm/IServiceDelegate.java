package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tpm;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.DelegateException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * 서비스 델리게이트 인터페이스
 * 
 * 프로그램명: IServiceDelegate.java
 * 설명: 서비스 델리게이트 구현을 위한 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IFRS 이벤트 데이터 전송
 * - 델리게이트 예외 처리
 * 
 * @version 1.0
 */
public interface IServiceDelegate {

	/**
	 * IFRS 이벤트 데이터를 전송한다.
	 * 
	 * @param ifrsEvent IFRS 이벤트 객체
	 * @return 전송 결과
	 * @throws DelegateException 델리게이트 예외
	 */
	public String sendData(IFRSEvent ifrsEvent) throws DelegateException;
}
