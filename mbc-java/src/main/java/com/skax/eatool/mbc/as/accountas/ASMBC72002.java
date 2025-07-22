/*
 * (@)# ASMBC72002.java
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
 * [프로그램명] 계정상세조회
 * <br>
 * [설명]
 * <br>
 * [상세설명]
 * <br>
 * [변경이력]
 * <ul>
 * <li>2024-01-01::전체::최초작성
 * </ul>
 */
@Service
public class ASMBC72002 implements NewIApplicationService {

	    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ASMBC72002.class);
	private static final Logger slf4jLogger = LoggerFactory.getLogger(ASMBC72002.class);

	@Autowired
	private PCAccount pcAccount;

	/**
	 * <br>
	 * [메서드명] 계정상세조회
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param NewKBData
	 * <ul>
	 * <li>accountId //계정ID
	 * </ul>
	 * 
	 * @return NewKBData
	 *         <ul>
	 *         <li>accountId //계정ID
	 *         <li>accountName //계정명
	 *         <li>accountType //계정타입
	 *         <li>accountStatus //계정상태
	 *         <li>accountBalance //계정잔액
	 *         </ul>
	 */
	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC72002", "=== ASMBC72002.execute START ===");
		slf4jLogger.info("=== ASMBC72002.execute START (SLF4J) ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC72002", "=== ASMBC72002.execute - reqData is null ===");
			slf4jLogger.warn("=== ASMBC72002.execute - reqData is null (SLF4J) ===");
			logger.info("ASMBC72002", "=== ASMBC72002.execute END ===");
			slf4jLogger.info("=== ASMBC72002.execute END (SLF4J) ===");
			return null;
		}

		// 입력 객체 필드값 출력
		logger.info("ASMBC72002", "=== ASMBC72002.execute - Input Object Field Values ===");
		slf4jLogger.info("=== ASMBC72002.execute - Input Object Field Values (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC72002", "=== ASMBC72002.execute - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC72002.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		// inputDto의 주요 키들을 직접 확인
		if (inputDto != null) {
			logger.info("ASMBC72002", "=== ASMBC72002.execute - Checking inputDto contents ===");
			slf4jLogger.info("=== ASMBC72002.execute - Checking inputDto contents (SLF4J) ===");
			
			// 주요 키들을 직접 확인
			Object accountIdValue = inputDto.get("accountId");
			
			logger.info("ASMBC72002", "=== ASMBC72002.execute - accountId value: " + accountIdValue + " ===");
			slf4jLogger.info("=== ASMBC72002.execute - accountId value: {} (SLF4J) ===", accountIdValue);
		}

		// 1.계정ID 가져오기
		String accountId = (String) reqData.getInputGenericDto().get("accountId");
		
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountId: " + accountId + " ===");
		slf4jLogger.info("=== ASMBC72002.execute - accountId: {} (SLF4J) ===", accountId);

		// accountId null 체크
		if (accountId == null) {
			logger.warn("ASMBC72002", "=== ASMBC72002.execute - accountId is null ===");
			slf4jLogger.warn("=== ASMBC72002.execute - accountId is null (SLF4J) ===");
			logger.info("ASMBC72002", "=== ASMBC72002.execute END ===");
			slf4jLogger.info("=== ASMBC72002.execute END (SLF4J) ===");
			return reqData;
		}

		// 2.AccountPDTO 생성
		AccountPDTO accountPDTO = new AccountPDTO();
		accountPDTO.setAccountId(accountId);

		// AccountPDTO 필드값 상세 출력
		logger.info("ASMBC72002", "=== ASMBC72002.execute - AccountPDTO Field Values ===");
		slf4jLogger.info("=== ASMBC72002.execute - AccountPDTO Field Values (SLF4J) ===");
		
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.accountNumber: " + accountPDTO.getAccountNumber() + " ===");
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.name: " + accountPDTO.getName() + " ===");
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.status: " + accountPDTO.getStatus() + " ===");
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.netAmount: " + accountPDTO.getNetAmount() + " ===");
		logger.info("ASMBC72002", "=== ASMBC72002.execute - accountPDTO.currency: " + accountPDTO.getCurrency() + " ===");
		
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.accountNumber: {} (SLF4J) ===", accountPDTO.getAccountNumber());
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.name: {} (SLF4J) ===", accountPDTO.getName());
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.status: {} (SLF4J) ===", accountPDTO.getStatus());
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.netAmount: {} (SLF4J) ===", accountPDTO.getNetAmount());
		slf4jLogger.info("=== ASMBC72002.execute - accountPDTO.currency: {} (SLF4J) ===", accountPDTO.getCurrency());

		// 3.PC호출
		AccountPDTO resAccountPDTO = pcAccount.getAccount(accountPDTO);

		// 4.결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(resAccountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		
		logger.info("ASMBC72002", "=== ASMBC72002.execute END ===");
		slf4jLogger.info("=== ASMBC72002.execute END (SLF4J) ===");
		return reqData;
	}

}
