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

	    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ASMBC74001.class);
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

		// 입력 객체 필드값 출력
		logger.info("ASMBC74001", "=== ASMBC74001.execute - Input Object Field Values ===");
		slf4jLogger.info("=== ASMBC74001.execute - Input Object Field Values (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.execute - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		// inputDto의 주요 키들을 직접 확인
		if (inputDto != null) {
			logger.info("ASMBC74001", "=== ASMBC74001.execute - Checking inputDto contents ===");
			slf4jLogger.info("=== ASMBC74001.execute - Checking inputDto contents (SLF4J) ===");
			
			// 주요 키들을 직접 확인
			Object accountPDTOValue = inputDto.using(NewGenericDto.INDATA).get("AccountPDTO");
			
			logger.info("ASMBC74001", "=== ASMBC74001.execute - AccountPDTO value: " + accountPDTOValue + " ===");
			slf4jLogger.info("=== ASMBC74001.execute - AccountPDTO value: {} (SLF4J) ===", accountPDTOValue);
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

		// AccountPDTO 필드값 상세 출력
		logger.info("ASMBC74001", "=== ASMBC74001.execute - AccountPDTO Field Values ===");
		slf4jLogger.info("=== ASMBC74001.execute - AccountPDTO Field Values (SLF4J) ===");
		
		// AccountPDTO 값 로그 출력
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.accountNumber: " + accountPDTO.getAccountNumber() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.name: " + accountPDTO.getName() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.status: " + accountPDTO.getStatus() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.netAmount: " + accountPDTO.getNetAmount() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.currency: " + accountPDTO.getCurrency() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.interestRate: " + accountPDTO.getInterestRate() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.identificationNumber: " + accountPDTO.getIdentificationNumber() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.createdDate: " + accountPDTO.getCreatedDate() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001.execute - accountPDTO.updatedDate: " + accountPDTO.getUpdatedDate() + " ===");
		
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.accountNumber: {} (SLF4J) ===", accountPDTO.getAccountNumber());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.name: {} (SLF4J) ===", accountPDTO.getName());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.status: {} (SLF4J) ===", accountPDTO.getStatus());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.netAmount: {} (SLF4J) ===", accountPDTO.getNetAmount());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.currency: {} (SLF4J) ===", accountPDTO.getCurrency());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.interestRate: {} (SLF4J) ===", accountPDTO.getInterestRate());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.identificationNumber: {} (SLF4J) ===", accountPDTO.getIdentificationNumber());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.createdDate: {} (SLF4J) ===", accountPDTO.getCreatedDate());
		slf4jLogger.info("=== ASMBC74001.execute - accountPDTO.updatedDate: {} (SLF4J) ===", accountPDTO.getUpdatedDate());

		// 2.PC호출
		pcAccount.deleteAccount(accountPDTO);
		
		// 3.결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		
		// 출력 결과 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.execute - Output Result: Account deleted successfully ===");
		slf4jLogger.info("=== ASMBC74001.execute - Output Result: Account deleted successfully (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.execute END ===");
		slf4jLogger.info("=== ASMBC74001.execute END (SLF4J) ===");
		return reqData;
	}

}