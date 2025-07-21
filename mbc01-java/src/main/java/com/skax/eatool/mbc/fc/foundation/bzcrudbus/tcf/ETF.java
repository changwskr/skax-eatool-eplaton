package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.TCFConstantErrcode;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.TCFConstants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.ErrorCheckUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IfrsLogHelper;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.tpm.TPMUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSCommonDTO;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * ETF (End Transaction Framework) 클래스
 * 
 * 프로그램명: ETF.java
 * 설명: This is oversea banking package.
 * Transaction Control Framework에서 트랜잭션을 완료한다.
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 트랜잭션 완료 처리
 * - 로그 기록 및 정리
 * - 에러 처리 및 정리
 * - 패킷 이동 및 정리
 * - TPM 서비스 정보 설정
 * 
 * @version 1.0
 */
public class ETF {

	/** 싱글톤 인스턴스 */
	private static ETF instance;

	/** 트랜잭션 타입 */
	private String transaction_type = TCFConstants.CONTAINER_TRANSACTION_MSG;

	/** 트랜잭션 모드 */
	private String transactionMode = null;

	/** IFRS 이벤트 */
	private IFRSEvent ifrsEvent;

	/** 공통 DTO */
	private IFRSCommonDTO commonDTO;

	/** IFRS 로거 */
	private IIfrsLogger logger;

	/**
	 * ETF 인스턴스를 반환하는 함수.
	 * 
	 * @return ETF.
	 */
	public static synchronized ETF getInstance() {

		if (instance == null) {

			try {

				instance = new ETF();

			} catch (Exception igex) {
			}

		}

		return instance;

	}

	/**
	 * 기본 생성자 함수.
	 */
	public ETF() {

	}

	/**
	 * 생성자 함수.
	 *
	 * @param transactionMode 트랜잭션 모드
	 */
	public ETF(String transactionMode) {
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

		return this.ifrsEvent;

	}

	/**
	 * 트랜잭션 완료 처리 함수.
	 * 
	 * @param pevent IFRSEvent.
	 * @return IFRSEvent.
	 */
	public IFRSEvent execute(IFRSEvent pevent) {

		try {

			ifrsEvent = pevent;

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_Init] begin");

			ETF_Init();

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_Init] end");

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_Middle] begin");

			ETF_Middle();

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_Middle] end");

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] begin");

			ETF_End();

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] end");

		} catch (Exception ex) {

			setETFError(TCFConstantErrcode.EETF001, TCFConstantErrcode.EETF001_MSG);

			// logger.info(ifrsEvent.getCommonDto().getSeqNo(), ex.toString());
		}

		return ifrsEvent;

	}

	/**
	 * 트랜잭션을 완료하기 위한 초기화.
	 * 
	 * @return boolean
	 */
	public boolean ETF_Init() {

		try {

			/*************************************************************************
			 * 기본적인 설정을 담당한다.
			 ************************************************************************/

			commonDTO = (IFRSCommonDTO) ifrsEvent.getCommonDto();

			return true;

		} catch (Exception ex) {

			// ex.printStackTrace();

			setETFError(TCFConstantErrcode.EETF002, TCFConstantErrcode.EETF002_MSG);

			return false;

		}

	}

	/**
	 * 트랜잭션을 완료하기 위한 중간 처리.
	 * 
	 * @return boolean
	 */
	public boolean ETF_Middle() {

		try {

			/*************************************************************************
			 * 트랜잭션 타임아웃 간격 체크
			 *************************************************************************
			 * 트랜잭션 타임아웃 간격은 TPSVCINFO, TXTIMER 등을 통해 설정되며,
			 * 이 간격 내에 트랜잭션이 완료되지 않으면 에러로 간주된다.
			 * 
			 * 트랜잭션 완료 시간은 트랜잭션 시작 시간과 비교하여 계산되며,
			 * 이 간격 내에 완료되지 않으면 에러로 간주된다.
			 * 
			 * 트랜잭션 완료 시간은 트랜잭션 시작 시간과 비교하여 계산되며,
			 * 이 간격 내에 완료되지 않으면 에러로 간주된다.
			 ************************************************************************/

			logger.info(ifrsEvent.getCommonDto().getSeqNo(),
					"[ETF_Middle] Check Transaction Timeout between WAF and EJB Tier ::");

			return true;

		} catch (Exception ex) {

			// ex.printStackTrace();

			setETFError(TCFConstantErrcode.EETF003, TCFConstantErrcode.EETF003_MSG);

			return false;

		}

	}

	/**
	 * 트랜잭션 완료 처리 함수.
	 * 
	 * @return boolean
	 */
	public boolean ETF_End() {

		try {

			/*************************************************************************
			 * 트랜잭션 완료 로그 기록
			 ************************************************************************/

			ETF_MovePacket();

			/***************************************************************************
			 * 트랜잭션 완료 로그 기록
			 * 트랜잭션 완료 로그는 INPUT/OUTPUT 등의 패킷 정보를 포함하여 기록된다.
			 * 
			 * 트랜잭션 완료 로그는 트랜잭션 시작 시간과 비교하여 계산되며,
			 * 이 간격 내에 완료되지 않으면 에러로 간주된다.
			 * 
			 * 트랜잭션 완료 로그는 트랜잭션 시작 시간과 비교하여 계산되며,
			 * 이 간격 내에 완료되지 않으면 에러로 간주된다.
			 ************************************************************************/

			if (this.ifrsEvent.getCommonDto().getErrorCode().charAt(0) != 'E') {

				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] DbOutLog start");

				if (!ETF_DbOutLog(ifrsEvent)) {

					logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] DbOutLog error");

				} else {

					logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] DbOutLog success");

				}

				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] DbOutLog end");

			}

			/***************************************************************************
			 * 트랜잭션 완료 로그 기록
			 ************************************************************************/

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] CommonLog start");

			if (!ETF_CommonLog()) {

				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] CommonLog error");

			} else {

				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] CommonLog success");

			}

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] CommonLog end");

			/***************************************************************************
			 * 트랜잭션 완료 처리
			 * 트랜잭션 완료 시에는 Commit/Rollback 처리가 필요하다.
			 * 
			 * 트랜잭션 완료 시에는 UserTransaction을 통해 Tx Data Source를 구성하고,
			 * STF, ETF 등의 트랜잭션 처리 단위에 따라 처리된다.
			 * 
			 * 트랜잭션 완료 시에는 UserTransaction을 통해 Tx Data Source를 구성하고,
			 * STF, ETF 등의 트랜잭션 처리 단위에 따라 처리된다.
			 ************************************************************************/

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] TProllback/TPcommit start");

			/*********************************************************************
			 * 트랜잭션 완료 처리 단위 처리
			 *********************************************************************/

			if (isError()) {
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] Call - TProllback");

				TPMUtil.getInstance().TPRollback();
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] TProllback-success");

			} else {
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] Call - TPcommit");

				TPMUtil.getInstance().TPCommit();
				logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] TPcommit-success");

			}

			logger.info(ifrsEvent.getCommonDto().getSeqNo(), "[ETF_End] TProllback/TPcommit end");

			return true;

		} catch (Exception ex) {

			ex.printStackTrace();

			setETFError(TCFConstantErrcode.EETF004,

					TCFConstantErrcode.EETF004_MSG);

			return false;

		}

	}

	/**
	 * 트랜잭션 완료 로그 기록 함수.
	 * 
	 * @param event IFRSEvent
	 * @return boolean
	 */
	private boolean ETF_DbOutLog(IFRSEvent event) {

		try {

		} catch (Exception ex) {

			// ex.printStackTrace();

			setETFError(TCFConstantErrcode.EETF008, TCFConstantErrcode.EETF008_MSG);

			return false;

		}

		return true;

	}

	/**
	 * 트랜잭션 완료 로그 기록 함수.
	 * 
	 * @return boolean
	 */
	private boolean ETF_CommonLog() {

		// String LOGFILENAME = null;

		// FileOutputStream fos = null;

		// PrintStream ps = null;

		//

		// try {

		// LOGFILENAME = CommonUtil.GetHostName() + "." +
		// commonDTO.getHostAs().substring(0,3).toUpperCase() + "." + "out" + "." +
		// CommonUtil.GetSysDate();

		// fos = new FileOutputStream(LOGFILENAME, true);

		// ps = new PrintStream(fos);

		// String message =
		// com.kdb.eplaton.foundation.utility.Reflector.objectToString(ifrsEvent);

		// if (message.length() > 4000) {

		// message = message.substring(0, 4000);

		// }

		// ps.println(ifrsEvent.getTPSVCINFODTO().getOperation_name() + "|" +

		// ifrsEvent.getTPSVCINFODTO().getOrgseq() + "-" + message);

		//

		// ps.flush();

		// ps.close();

		// fos.close();

		//

		// } catch (Exception e) {

		// try {

		// if (fos != null)

		// fos.close();

		// if (ps != null)

		// ps.close();

		// } catch (Exception ex) {}

		// setETFError(TCFConstantErrcode.EETF009,

		// TCFConstantErrcode.EETF009_MSG);

		// LOGEJ.getInstance().edebug(TCFConstants.ETF_LOG_LEVEL,

		// (IFRSEvent) ifrsEvent, e);

		// e.printStackTrace();

		// return false;

		// }

		return true;

	}

	/**
	 * 에러 상태를 확인하는 함수.
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
	 * 에러 코드와 메시지를 설정하는 함수.
	 * 
	 * @param errorCode 에러 코드
	 * @param errorMsg  에러 메시지
	 */
	private void setETFError(String errorCode, String errorMsg) {

		// 에러 코드가 설정되지 않은 경우에만 에러 코드와 메시지를 설정한다.
		if (!isError()) {
			ifrsEvent.getCommonDto().setErrorCode(errorCode);
			ifrsEvent.getCommonDto().setErrorMsg(errorMsg);
		}

	}

	/**
	 * 웹 트랜잭션 타이머 함수.
	 */
	private void ETF_WebTxTimer() {

		return;

	}

	/**
	 * TPM 서비스 정보를 설정하는 함수.
	 */
	private void ETF_SetTpmSvcInfo() {

		return;

	}

	/**
	 * 패킷을 이동하는 함수.
	 */
	private void ETF_MovePacket() {

		try {

			this.ifrsEvent.setCommonDto(this.commonDTO);

			//////////////////////////////////////////////////////////////////////////

			// 패킷 이동 시 공통 DTO를 설정한다.

			//////////////////////////////////////////////////////////////////////////

		} catch (Exception ex) {

			setETFError(TCFConstantErrcode.EETF001, TCFConstantErrcode.EETF001_MSG);

			return;

		}

		return;

	}

	/**
	 * 트랜잭션 정보를 설정하는 함수.
	 * 
	 * @param offset 오프셋
	 */
	public void setETF_TxInfo(int offset) {

	}

	/**
	 * 트랜잭션 정보를 반환하는 함수.
	 * 
	 * @return int
	 */
	public int getETF_TxInfo() {

		return 0;
	}

}
