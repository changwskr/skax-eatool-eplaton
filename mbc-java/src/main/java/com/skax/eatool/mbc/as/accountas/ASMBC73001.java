/*
 * (@)# ASEDU73001.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.as.accountas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.pc.accountpc.PCAccount;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;

/**
 * <br>
 * [프로그램명] 계정수정
 * <br>
 * [설명]
 * <br>
 * [상세설명]
 * <br>
 * [변경이력]
 * <ul>
 * <li>2008-08-26::전체::최초작성
 * </ul>
 */
public class ASMBC73001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	/**
	 * <br>
	 * [메서드명] 계정수정
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param NewKBData
	 *                  <ul>
	 *                  <li>accountNumber //계좌번호
	 *                  <li>name //이름
	 *                  <li>identificationNumber //주민번호
	 *                  <li>interestRate //이자율
	 *                  <li>lastTransaction //마지막거래일
	 *                  <li>password //비밀번호
	 *                  <li>netAmount //잔액
	 *                  </ul>
	 * @return NewKBData
	 */
	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC73001", "=== ASMBC73001.execute START ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC73001", "=== ASMBC73001.execute - reqData is null ===");
			logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
			return null;
		}

		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA)
				.get("AccountPDTO");

		// AccountPDTO null 체크
		if (accountPDTO == null) {
			logger.warn("ASMBC73001", "=== ASMBC73001.execute - accountPDTO is null ===");
			logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
			return reqData;
		}

		// 2.PC호출
		new PCAccount().updateAccount(accountPDTO);
		
		logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
		return reqData;
	}

}
