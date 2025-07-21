package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.ActionConfig;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.CommandObj;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.TCFConstantErrcode;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.ErrorCheckUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Utils;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.WafErrorUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.tpm.TPMUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSCommonDTO;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * BTF_WP (Business Transaction Framework - Web Processing) 클래스
 * 
 * 프로그램명: BTF_WP.java
 * 설명: This is oversea banking package.
 * WAF 모드에서 비즈니스 로직을 처리하는 클래스
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - WAF 모드 비즈니스 로직 처리
 * - PC 컴포넌트 호출 및 결과 처리
 * - XML 에러 체크 및 처리
 * - 에러 메시지 처리
 * - TPM 통신 처리
 * 
 * @version 1.0
 */
public class BTF_WP {

	/** 싱글톤 인스턴스 */
	private static BTF_WP instance;

	/** IFRS 이벤트 */
	private IFRSEvent ifrsEvent;

	/** 공통 DTO */
	private IFRSCommonDTO commonDTO;

	/** 트랜잭션 모드 */
	private String transactionMode = null;

	/** 작업 타입 */
	private String workType;

	/** IFRS 로거 */
	// private IIfrsLogger logger = IfrsLogHelper.getWaf();
	private IIfrsLogger logger = null; // TODO: Initialize when logger is available

	/**
	 * BTF의 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return BTF
	 */
	public static synchronized BTF_WP getInstance() {

		if (instance == null) {
			try {
				instance = new BTF_WP();
			} catch (Exception igex) {
			}
		}
		return instance;

	}

	/**
	 * 기본 생성자
	 */
	public BTF_WP() {
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
	 * @param ifrsEvent IFRSEvent
	 * @return IFRSEvent
	 */
	public IFRSEvent execute(IFRSEvent ifrsEvent) {

		try {

			logger.info(ifrsEvent.getCommonDto().getWafSeq(), "[BTF_WP] begin");

			/*
			 * PC 컴포넌트 호출 함수 부분을 담당한다.
			 */

			// command id에 따른 처리 로직을 실행한다.
			ArrayList arrList = ActionConfig.getInstance("WAF").getCmdList(ifrsEvent.getCommonDto().getCommandId());

			CommandObj cmdObj = null;
			String resultXml = null;

			// 순차 호출 및 결과 처리
			for (Iterator itr = Utils.snapshotIterator(arrList); itr.hasNext();) {

				cmdObj = (CommandObj) itr.next();

				ifrsEvent.getCommonDto().setKey(cmdObj.key);
				ifrsEvent.getCommonDto().setHostServer(cmdObj.server);
				ifrsEvent.getCommonDto().setHostAs(cmdObj.name);

				resultXml = TPMUtil.getInstance().TPCSendRecv(ifrsEvent);

				// XML 에러 체크 및 결과 확인
				boolean bError = checkXmlError(resultXml);

				// 에러가 있는 경우
				if (bError) {

					setBTFError(TCFConstantErrcode.EBTF003, TCFConstantErrcode.EBTF003_MSG);

					ifrsEvent.getCommonDto().setErrorMap(WafErrorUtil.setErrorInfo(null, TCFConstantErrcode.EBTF003,
							new Exception(TCFConstantErrcode.EBTF003_MSG)));

					break;

				} else {

					// 결과 XML을 설정
					ifrsEvent.getCommonDto().setResultXml(resultXml);
				}

			}

			String forwardName = ifrsEvent.getCommonDto().getForwardName();

			// 에러 발생 여부 확인
			String errOccur = (String) ifrsEvent.getCommonDto().getToMap().get(Constants.ERROR_OCCUR_VAR);

			// 에러가 발생한 경우 에러 처리
			if (errOccur != null && errOccur.equals("Y")) {

				processError(ifrsEvent);

			}

		} catch (Exception ex) {

			// ex.printStackTrace();

			setBTFError(TCFConstantErrcode.EBTF003, TCFConstantErrcode.EBTF003_MSG);

			// Added by 김민주
			ifrsEvent.getCommonDto().setErrorMap(WafErrorUtil.setErrorInfo(null, TCFConstantErrcode.EBTF003, ex));

			logger.info(ifrsEvent.getCommonDto().getWafSeq(), "[BTF_WP] end");

		}

		return ifrsEvent;

	}

	/**
	 * 에러 처리 (DB에 없는 에러 메시지 조회 및 처리 XML 생성)
	 * 
	 * @param ifrsEvent IFRS 이벤트
	 * @return 에러 XML
	 */
	protected String processError(IFRSEvent ifrsEvent) {

		// BaseForm baseForm = (BaseForm) form;

		String errCd = (String) ifrsEvent.getCommonDto().getErrorMap().get(Constants.ERROR_MSG_CD_VAR);
		String errMsg = (String) ifrsEvent.getCommonDto().getErrorMap().get(Constants.ERROR_MSG_TEXT_VAR);

		// 데이터베이스에서 코드에 해당하는 메시지 데이터를 조회한다.
		// HashMap msgMap = (new BaseDAO()).getMsgData(baseForm);
		HashMap msgMap = new HashMap();

		if (msgMap != null) {
			// SsnBean.setMsgData(ifrsEvent.getServletRequest(), msgMap);
		} else {
			msgMap = new HashMap();

			errMsg = "메시지가 등록되지 않았습니다.";
		}

		// 에러 XML 생성
		String errorXml = "";

		return errorXml;

	}

	/**
	 * 
	 * 에러 체크 함수.
	 * 
	 * @return boolean
	 * 
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
	 * BTF 에러 설정 함수.
	 * 
	 * @param errorcode String
	 * 
	 * @param message   String
	 * 
	 */

	private void setBTFError(String errorCode, String errorMsg) {

		// 에러 코드가 설정되지 않은 경우에만 설정한다.
		if (!isError()) {
			ifrsEvent.getCommonDto().setErrorCode(errorCode);
			ifrsEvent.getCommonDto().setErrorMsg(errorMsg);
		}

	}

	/**
	 * 
	 * 에러 발생 시 예외 처리 함수.
	 * 
	 * @param _e Throwable
	 * 
	 */

	private void BTF_Exception(Throwable _e) {

		logger.info(ifrsEvent.getCommonDto().getWafSeq(), "[BTF_Exception] begin");

		Throwable cause = _e;

		int count = 0;

		while (cause != null) {

			if (count > 100)
				break;

			if (cause instanceof InvocationTargetException) {

				cause = ((InvocationTargetException) cause).getTargetException();

			} else if (cause instanceof Exception) {

				cause = ((Exception) cause);

				break;

			} else {

				cause = null;

			}

		}

		String transactionNo = "No Transaction Number";

		if (cause instanceof Exception) {

			cause.printStackTrace();

			Exception _eee = (Exception) cause;

		} else {

			cause.printStackTrace();

			Exception ex = (Exception) cause;

		}

		logger.info(ifrsEvent.getCommonDto().getWafSeq(), "[BTF_Exception] end");

	}

	// XML 에러 체크 함수
	protected boolean checkXmlError(String xml) {

		return false;
	}

}
