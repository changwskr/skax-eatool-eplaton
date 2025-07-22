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
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
@Service
public class ASMBC73001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();
	private static final Logger slf4jLogger = LoggerFactory.getLogger(ASMBC73001.class);

	@Autowired
	private PCAccount pcAccount;

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
		slf4jLogger.info("=== ASMBC73001.execute START (SLF4J) ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC73001", "=== ASMBC73001.execute - reqData is null ===");
			slf4jLogger.warn("=== ASMBC73001.execute - reqData is null (SLF4J) ===");
			logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
			slf4jLogger.info("=== ASMBC73001.execute END (SLF4J) ===");
			return null;
		}

		// 입력 객체 필드값 출력
		logger.info("ASMBC73001", "=== ASMBC73001.execute - Input Object Field Values ===");
		slf4jLogger.info("=== ASMBC73001.execute - Input Object Field Values (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC73001", "=== ASMBC73001.execute - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC73001.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		// inputDto의 주요 키들을 직접 확인
		if (inputDto != null) {
			logger.info("ASMBC73001", "=== ASMBC73001.execute - Checking inputDto contents ===");
			slf4jLogger.info("=== ASMBC73001.execute - Checking inputDto contents (SLF4J) ===");
			
			// 주요 키들을 직접 확인
			Object accountPDTOValue = inputDto.using(NewGenericDto.INDATA).get("AccountPDTO");
			
			logger.info("ASMBC73001", "=== ASMBC73001.execute - AccountPDTO value: " + accountPDTOValue + " ===");
			slf4jLogger.info("=== ASMBC73001.execute - AccountPDTO value: {} (SLF4J) ===", accountPDTOValue);
		}

		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA)
				.get("AccountPDTO");

		// AccountPDTO null 체크
		if (accountPDTO == null) {
			logger.warn("ASMBC73001", "=== ASMBC73001.execute - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC73001.execute - accountPDTO is null (SLF4J) ===");
			logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
			slf4jLogger.info("=== ASMBC73001.execute END (SLF4J) ===");
			return reqData;
		}

		// AccountPDTO 필드값 상세 출력
		logger.info("ASMBC73001", "=== ASMBC73001.execute - AccountPDTO Field Values ===");
		slf4jLogger.info("=== ASMBC73001.execute - AccountPDTO Field Values (SLF4J) ===");
		
		logger.info("ASMBC73001", "=== ASMBC73001.execute - accountPDTO.accountId: " + accountPDTO.getAccountId() + " ===");
		logger.info("ASMBC73001", "=== ASMBC73001.execute - accountPDTO.accountName: " + accountPDTO.getAccountName() + " ===");
		logger.info("ASMBC73001", "=== ASMBC73001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC73001", "=== ASMBC73001.execute - accountPDTO.accountStatus: " + accountPDTO.getAccountStatus() + " ===");
		logger.info("ASMBC73001", "=== ASMBC73001.execute - accountPDTO.accountBalance: " + accountPDTO.getAccountBalance() + " ===");
		
		slf4jLogger.info("=== ASMBC73001.execute - accountPDTO.accountId: {} (SLF4J) ===", accountPDTO.getAccountId());
		slf4jLogger.info("=== ASMBC73001.execute - accountPDTO.accountName: {} (SLF4J) ===", accountPDTO.getAccountName());
		slf4jLogger.info("=== ASMBC73001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC73001.execute - accountPDTO.accountStatus: {} (SLF4J) ===", accountPDTO.getAccountStatus());
		slf4jLogger.info("=== ASMBC73001.execute - accountPDTO.accountBalance: {} (SLF4J) ===", accountPDTO.getAccountBalance());

		// 2.PC호출
		pcAccount.updateAccount(accountPDTO);
		
		logger.info("ASMBC73001", "=== ASMBC73001.execute END ===");
		slf4jLogger.info("=== ASMBC73001.execute END (SLF4J) ===");
		return reqData;
	}

}
