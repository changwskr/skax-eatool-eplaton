package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

import java.lang.reflect.InvocationTargetException;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.TCFConstantErrcode;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.BTFSPException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.ErrorCheckUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IfrsLogHelper;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSCommonDTO;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * BTF (Business Transaction Framework) 클래스
 * 
 * 프로그램명: BTF.java
 * 설명: 해외 은행 업무 처리를 위한 비즈니스 트랜잭션 프레임워크 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 해외 은행 업무 처리
 * - 트랜잭션 모드 관리 (WAF/Server)
 * - 에러 처리 및 로깅
 * - BTF_SP, BTF_WP 실행 관리
 * - 예외 처리 및 에러 코드 관리
 * 
 * @version 1.0
 */
public class BTF {

	/** 싱글톤 인스턴스 */
	private static BTF instance;

	/** IFRS 이벤트 객체 */
	private IFRSEvent ifrsEvent;

	/** 공통 DTO */
	private IFRSCommonDTO commonDTO;

	/** 트랜잭션 모드 */
	private String transactionMode = null;

	/** 로거 */
	private IIfrsLogger logger;

	/**
	 * BTF의 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return BTF 인스턴스
	 */
	public static synchronized BTF getInstance() {

		if (instance == null) {
			try {
				instance = new BTF();
			} catch (Exception igex) {
			}
		}
		return instance;

	}

	/**
	 * 트랜잭션 모드를 지정하여 BTF의 싱글톤 인스턴스를 반환한다.
	 * 
	 * @param transactionMode 트랜잭션 모드
	 * @return BTF 인스턴스
	 */
	public static synchronized BTF getInstance(String transactionMode) {

		if (instance == null) {
			try {
				instance = new BTF(transactionMode);
			} catch (Exception igex) {
			}
		}
		return instance;

	}

	/**
	 * 기본 생성자
	 */
	public BTF() {
	}

	/**
	 * 트랜잭션 모드를 지정하는 생성자
	 * 
	 * @param transactionMode 트랜잭션 모드
	 */
	public BTF(String transactionMode) {
		this.transactionMode = transactionMode;

		if (transactionMode.equals(Constants.TRANSACTION_MODE_WAF)) {
			// logger = IfrsLogHelper.getWaf();
			logger = null; // TODO: Initialize when logger is available
		} else {
			logger = IfrsLogHelper.getServer();
		}
	}

	/**
	 * IFRSEvent를 반환하는 함수.
	 * 
	 * @return IFRSEvent
	 */
	public IFRSEvent getIFRSEvent() {
		return ifrsEvent;

	}

	/**
	 * 실행 함수.
	 * 
	 * @param ppifrsevent IFRSEvent
	 * @return IFRSEvent
	 */
	public IFRSEvent execute(IFRSEvent ppifrsevent) {

		try {

			ifrsEvent = ppifrsevent;
			this.commonDTO = ifrsEvent.getCommonDto();

			if (!isError()) {
				switch (this.transactionMode.charAt(0)) {
					case 'S':
					case 's':
						ifrsEvent = BTF_SP.getInstance().execute(ifrsEvent);
						break;
					case 'W':
					case 'w':
						ifrsEvent = BTF_WP.getInstance().execute(ifrsEvent);
						break;
				}
			}

			BTF_End();

		}
		// BTF_SP 에서 발생하는 Exception Catch.
		catch (BTFSPException bse) {

			ifrsEvent.setException(bse);
			setBTFError(bse.getErrorCode(), bse.getMessage());

		} catch (Exception ex) {

			// ex.printStackTrace();
			setBTFError(TCFConstantErrcode.EBTF001, TCFConstantErrcode.EBTF001_MSG);

		}

		return ifrsEvent;

	}

	/**
	 * BTF 종료.
	 * 
	 * @return boolean.
	 */
	public boolean BTF_End() {

		try {

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF_End] begin");

		} catch (Exception ex) {

			// ex.printStackTrace();

			setBTFError(TCFConstantErrcode.EBTF004, TCFConstantErrcode.EBTF004_MSG);

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF_End] error");

			return false;

		}

		logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF_End] end");

		return true;

	}

	/**
	 * 에러체크 함수.
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
	 * 
	 * BTF ��⿡���� ����üŷ. *
	 * 
	 * @param errorcode
	 *                  String
	 * 
	 * @param message
	 *                  String
	 * 
	 */

	private void setBTFError(String errorCode, String errorMsg) {
		// ������ �ƴѰ�� �����ڵ� ����
		if (!isError()) {
			ifrsEvent.getCommonDto().setErrorCode(errorCode);
			ifrsEvent.getCommonDto().setErrorMsg(errorMsg);
		}

	}

	/**
	 * 
	 * ���������Ͽ��� �߻��� Exception���� ��������ϴ� �޼ҵ�.
	 * 
	 * @param _e
	 *           Throwable
	 * 
	 */

	private void BTF_Exception(Throwable _e) {

		logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF_Exception] begin");

		Throwable cause = _e;

		int count = 0;

		while (cause != null) {

			if (count > 100)
				break;

			if (cause instanceof InvocationTargetException) {

				// logger.info(ifrsEvent.getCommonDto().getSeqNo(), ++count + "1:" +
				// cause.getClass().getName());

				cause = ((InvocationTargetException) cause).getTargetException();

			} else if (cause instanceof Exception) {

				// logger.info(ifrsEvent.getCommonDto().getSeqNo(), ++count + "2:" +
				// cause.getClass().getName());

				cause = ((Exception) cause);

				break;

			} else {

				// logger.info(ifrsEvent.getCommonDto().getSeqNo(), ++count + "3:" +
				// cause.getClass().getName());

				cause = null;

			}

		}

		// LOGEJ.getInstance().debug(TCFConstants.BTF_LOG_LEVEL,ifrsEvent,"Throwable
		// Type : " + _e.getClass() );

		String transactionNo = "No Transaction Number";

		if (cause instanceof Exception) {

			// logger.info(ifrsEvent.getCommonDto().getSeqNo(), "coses app exception
			// ====:::" + cause.getClass().getName() );

			cause.printStackTrace();

			// Throwable��ü�� EplatonAppException����Ÿ������ ĳ�����Ѵ�.

			Exception _eee = (Exception) cause;

		}

		// BizAction class���� �߻��� exception �� EplatonAppException�� �ƴ� ���

		else {

			cause.printStackTrace();

			Exception ex = (Exception) cause;

		}

		logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[BTF_Exception] end");

	}

	/**
	 * 
	 * �������� �����޽��� �����Լ�.
	 * 
	 * @param nationCode
	 *                      String.
	 * 
	 * @param exceptionCode
	 *                      String.
	 * 
	 * @return String.
	 * 
	 */

	private String exceptionMessage(String nationCode, String exceptionCode) {

		String exceptionMsg = "No message.";

		try {

			// �����޽ø� �����Ѵ�.

			// �����ڵ忡 ���� DB�� ��ȸ�Ѵ�.

		}

		// Exception message�� �������� ������ �����

		// Log�� ����� �����Ѵ�.

		catch (Throwable _e) {

			// Log.DelegateLogger.debug(header, _e);

		}

		return exceptionMsg;

	}

	/**
	 * 
	 * �����ܿ��� �߻��� ������ �����ڵ�� �޽����� �����Ѵ�.
	 * 
	 * ���� : EBIZ + �����ڵ� + �޽���
	 * 
	 * 
	 * 
	 * Method exceptionOutput.
	 * 
	 * @param exceptionCode.
	 * 
	 * @param exceptionMessage.
	 * 
	 * @return String.
	 * 
	 */

	private String exceptionOutput(String exceptionCode,

			String exceptionMessage) {

		setBTFError("EBIZ" + exceptionCode, exceptionMessage);

		return "[Excpetion Code] : " + exceptionCode +

				" / [Excpetion Message] : " + exceptionMessage + "^";

	}

	/**
	 * 
	 * Method manageFatalException.
	 * 
	 * @param developerMessage.
	 * 
	 * @param exceptionDetail.
	 * 
	 * @param exceptionCode.
	 * 
	 * @param exceptionMessage.
	 * 
	 * @throws BizDelegateException.
	 * 
	 */

	private void manageFatalException(String header, String developerMessage,

			String exceptionDetail, String exceptionCode,

			String exceptionMessage) {

	}

	/**
	 * 
	 * Method manageFatalExceptionWithStackTrace.
	 * 
	 * @param developerMessage
	 * 
	 * @param t
	 * 
	 * @param exceptionCode
	 * 
	 * @param exceptionMessage
	 * 
	 * @throws BizDelegateException
	 * 
	 */

	private void manageFatalExceptionWithStackTrace(String header,

			String developerMessage,

			Throwable t, String exceptionCode, String exceptionMessage) {

	}

}
