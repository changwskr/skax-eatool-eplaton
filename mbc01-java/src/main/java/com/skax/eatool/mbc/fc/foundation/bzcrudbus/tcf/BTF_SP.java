package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tcf;

import java.util.ArrayList;
import java.util.HashMap;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.ActionConfig;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.CommandObj;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.BTFSPException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IfrsLogHelper;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.tpm.TPMUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSCommonDTO;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * BTF_SP (Business Transaction Framework - Server Processing) 클래스
 * 
 * 프로그램명: BTF_SP.java
 * 설명: This is oversea banking package.
 * Server 모드에서 비즈니스 로직을 처리하는 클래스
 * 
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - Server 모드 비즈니스 로직 처리
 * - PC 컴포넌트 호출 및 결과 처리
 * - TPM 통신 처리
 * - 에러 처리 및 예외 관리
 * - DC/PC 컴포넌트 간 데이터 전달
 * 
 * @version 1.0
 */
public class BTF_SP {

	/** 싱글톤 인스턴스 */
	private static BTF_SP instance;

	/** IFRS 로거 */
	private IIfrsLogger logger = IfrsLogHelper.getServer();

	/**
	 * BTF의 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return BTF
	 */
	public static synchronized BTF_SP getInstance() {

		if (instance == null) {
			try {
				instance = new BTF_SP();
			} catch (Exception igex) {
			}
		}
		return instance;

	}

	/**
	 * 기본 생성자
	 */
	public BTF_SP() {
	}

	/**
	 * 실행 함수.
	 * 
	 * @param ifrsEvent IFRSEvent
	 * @return IFRSEvent
	 * @throws BTFSPException BTF SP 예외
	 */
	public IFRSEvent execute(IFRSEvent ifrsEvent) throws BTFSPException {

		try {
			logger.info(ifrsEvent.getCommonDto().getTransactionNo(), "[BTF_SP] begin");

			IFRSCommonDTO commonDTO = ifrsEvent.getCommonDto();
			commonDTO.setToMap((HashMap) new HashMap());
			String cmdId = ifrsEvent.getCommonDto().getCommandId();
			ArrayList callInfoList = ActionConfig.getInstance(cmdId.substring(0, 3).toUpperCase()).getCmdList(cmdId);

			/*
			 * PC 컴포넌트 호출 함수 부분을 담당한다.
			 */

			CommandObj cmdObj = null;

			for (int i = 0; i < callInfoList.size(); i++) {

				cmdObj = (CommandObj) callInfoList.get(i);

				TPMUtil.getInstance().TPSSendRecv(commonDTO, cmdObj);

			}

			/*
			 * DC/PC 컴포넌트에서 처리한 결과를 담은 CommonDTO를 IFRSEvent에 설정한다.
			 */

			logger.info(ifrsEvent.getCommonDto().getTransactionNo(), "[BTF_SP] end]");

			return ifrsEvent;

		} catch (Exception ex) {
			ifrsEvent.getCommonDto().setErrorCode("ESBTF01001");
			ifrsEvent.getCommonDto().setErrorMsg(ex.getMessage());
			throw new BTFSPException("ESBTF01001", ex.toString());
		}

	}
}
