/*
 * (@)# ASEDU74001.java
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
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br>
 * [프로그램명] 계정삭제
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
@Service
public class ASMBC74001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();
	private static final Logger slf4jLogger = LoggerFactory.getLogger(ASMBC74001.class);

	@Autowired
	private PCAccount pcAccount;

	/**
	 * <br>
	 * [메서드명] 계정삭제
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param KBData
	 *               <ul>
	 *               <li>accountNumber //계좌번호
	 *               </ul>
	 * @return KBData
	 */
	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.execute START ===");
		slf4jLogger.info("=== ASMBC74001.execute START (SLF4J) ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.execute - reqData is null ===");
			slf4jLogger.warn("=== ASMBC74001.execute - reqData is null (SLF4J) ===");
			logger.info("ASMBC74001", "=== ASMBC74001.execute END ===");
			slf4jLogger.info("=== ASMBC74001.execute END (SLF4J) ===");
			return null;
		}

		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA)
				.get("AccountPDTO");

		// AccountPDTO null 체크
		if (accountPDTO == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.execute - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC74001.execute - accountPDTO is null (SLF4J) ===");
			logger.info("ASMBC74001", "=== ASMBC74001.execute END ===");
			slf4jLogger.info("=== ASMBC74001.execute END (SLF4J) ===");
			return reqData;
		}

		// 2.PC호출
		pcAccount.deleteAccount(accountPDTO);
		
		logger.info("ASMBC74001", "=== ASMBC74001.execute END ===");
		slf4jLogger.info("=== ASMBC74001.execute END (SLF4J) ===");
		return reqData;
	}

}