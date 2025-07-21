/*
 * (@)# ASEDU72001.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.as.accountas;

import com.skax.eatool.mbc.pc.accountpc.PCAccount;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;

/**
 * <br>
 * [프로그램명] 계정조회
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
public class ASMBC72001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	/**
	 * <br>
	 * [메서드명] 계정조회
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * //@param KBData
	 * <ul>
	 * <li>accountNumber //계좌번호
	 * </ul>
	 * 
	 * @return KBData
	 *         <ul>
	 *         <li>accountNumber //계좌번호
	 *         <li>name //이름
	 *         <li>identificationNumber //주민번호
	 *         <li>interestRate //이자율
	 *         <li>lastTransaction //마지막거래일
	 *         <li>password //비밀번호
	 *         <li>netAmount //잔액
	 *         </ul>
	 */
	public NewKBData execute(NewKBData reqData) throws NewBusinessException {

		if (logger.isDebugEnabled())
			logger.debug(this.getClass().getName() + ", log test입니다.");
		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto()
				.using(NewGenericDto.INDATA).get("AccountPDTO");

		// 2.PC호출
		AccountPDTO resAccountPDTO = new PCAccount().getAccount(accountPDTO);

		// 3.결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(resAccountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		return reqData;
	}

}
