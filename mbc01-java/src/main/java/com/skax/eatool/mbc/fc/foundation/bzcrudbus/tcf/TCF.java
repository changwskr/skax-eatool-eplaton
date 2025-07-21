package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

/**
 * TCF (Transaction Control Framework) 클래스
 * 
 * 프로그램명: TCF.java
 * 설명: This is oversea banking package.
 * 
 * 프로그램 설명:
 * =============================================================================
 * 트랜잭션을 처리하면서 각 비즈니스 컴포넌트들을 호출한다.
 * 전체 구조는 3개의 부분으로 구성되어 있다.
 * 1. STF
 *    트랜잭션을 시작하기 위한 기본적인 설정을 담당한다.
 * 2. BTF
 *    비즈니스 로직을 처리하기 위해 호출된다.
 * 3. ETF
 *    트랜잭션을 완료를 처리하기 위해 호출되는 부분이다.
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 트랜잭션 제어 프레임워크
 * - STF, BTF, ETF 순차 실행
 * - 에러 처리 및 로깅
 * - WAF/Server 모드 지원
 * 
 * @version 1.0
 */

import javax.naming.NamingException;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.TCFConstantErrcode;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.ErrorCheckUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IfrsLogHelper;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

public class TCF extends AbstractTCF {

	/** 싱글톤 인스턴스 */
	private static TCF instance;

	/** 트랜잭션 모드 (WAF에서 처리할지 서버에서 처리할지를 결정한다) */
	// transactionMode=waf,server
	// public static String transactionMode = ComExecAS.transactionMode;
	public static String transactionMode;

	/** STF (Start Transaction Framework) */
	private STF stf;

	/** ETF (End Transaction Framework) */
	private ETF etf;

	/** BTF (Business Transaction Framework) */
	private BTF btf;

	/** IFRS 이벤트 */
	private IFRSEvent ifrsEvent;

	/** IFRS 로거 */
	private IIfrsLogger logger;

	/**
	 * TCF 인스턴스를 반환하는 함수.
	 * 
	 * @return TCF
	 */
	/*
	 * public static synchronized TCF getInstance() {
	 * if (instance == null) {
	 * try {
	 * instance = new TCF();
	 * } catch (Exception igex) {
	 * igex.printStackTrace();
	 * }
	 * }
	 * return instance;
	 * }
	 * 
	 * public static synchronized TCF getInstance(String transactionMode) {
	 * if (instance == null) {
	 * try {
	 * instance = new TCF(transactionMode);
	 * } catch (Exception igex) {
	 * igex.printStackTrace();
	 * }
	 * }
	 * return instance;
	 * }
	 */

	/**
	 * 생성자 함수.
	 *
	 * @param transactionMode 트랜잭션 모드
	 * @throws NamingException 네이밍 예외
	 */
	public TCF(String transactionMode) {
		TCF.transactionMode = transactionMode;

		if (transactionMode.equals(Constants.TRANSACTION_MODE_WAF)) {
			// logger = IfrsLogHelper.getWaf();
			logger = null; // TODO: Initialize when logger is available
		} else {
			logger = IfrsLogHelper.getServer();
		}
	}

	/**
	 * 기본 생성자 함수.
	 *
	 * @throws NamingException 네이밍 예외
	 */
	public TCF() {

	}

	/**
	 * desc : 전체 트랜잭션을 처리하는 부분이다.
	 * 
	 * @param ppifrsEvent IFRS 이벤트
	 * @return IFRSEvent
	 */
	public IFRSEvent execute(IFRSEvent ppifrsEvent) {
		try {

			this.ifrsEvent = ppifrsEvent;

			// TCF 시작
			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[TCF] begin");

			// STF 시작
			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[STF] begin");

			// STF 실행
			this.ifrsEvent = (this.stf = new STF(transactionMode)).execute(ifrsEvent);

			/*
			 * STF에서 에러가 발생했는지 확인한다.
			 * 에러가 없을 경우에만 BTF를 실행하고 에러가 있을 경우 클라이언트로 반환한다.
			 *
			 */

			if (isError()) {
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[STF] error");
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[STF] end");
			} else {
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[STF] success");
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[STF] end");
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF] begin");

				// 비즈니스 로직을 실행한다.
				this.ifrsEvent = (this.btf = new BTF(transactionMode)).execute(ifrsEvent);

				// 비즈니스 로직이 성공적으로 호출되었는지 확인한다.
				if (isError()) {
					logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF] error");
				} else {
					logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF] success");
				}
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF] end");
			}

			/*
			 * 트랜잭션을 완료한다.
			 */
			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF] begin");

			this.ifrsEvent = (this.etf = new ETF(transactionMode)).execute(ifrsEvent);

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF] end");

		} catch (Exception ex) {

			setTCFError(TCFConstantErrcode.ETCF004, TCFConstantErrcode.ETCF004_MSG);

		}

		logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[TCF] end");

		return ifrsEvent;

	}

	/**
	 * 에러확인 함수.
	 * 
	 * @return boolean
	 */
	private boolean isError() {

		return ErrorCheckUtil.getInstance().isError(ifrsEvent.getCommonDto().getErrorCode().charAt(0));
		/*
		 * switch (ifrsEvent.getCommonDto().getErrorCode().charAt(0)) {
		 * case 'e':
		 * case 'b':
		 * case 's':
		 * case 'd':
		 * case 'E':
		 * case 'B':
		 * case 'S':
		 * case 'D':
		 * return true;
		 * default:
		 * return false;
		 * }
		 */
	}

	/**
	 * TCF 에러 코드 설정 함수.
	 * 
	 * @param errorcode 에러 코드
	 * @param message   에러 메시지
	 */
	private void setTCFError(String errorCode, String errorMsg) {
		// 에러가 발생하지 않았을 경우에만 에러 코드와 메시지를 설정한다.
		if (!isError()) {
			ifrsEvent.getCommonDto().setErrorCode(errorCode);
			ifrsEvent.getCommonDto().setErrorMsg(errorMsg);
		}
	}

	/**
	 * IFRSEvent 반환 함수.
	 * 
	 * @return IFRSEvent
	 */
	public IFRSEvent getIfrsEvent() {
		return ifrsEvent;
	}

}
