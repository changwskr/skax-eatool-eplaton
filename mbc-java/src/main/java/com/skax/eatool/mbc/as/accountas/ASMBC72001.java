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
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
@Service
public class ASMBC72001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();
	private static final Logger slf4jLogger = LoggerFactory.getLogger(ASMBC72001.class);

	@Autowired
	private PCAccount pcAccount;

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
		logger.info("ASMBC72001", "=== ASMBC72001.execute START ===");
		slf4jLogger.info("=== ASMBC72001.execute START (SLF4J) ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC72001", "=== ASMBC72001.execute - reqData is null ===");
			slf4jLogger.warn("=== ASMBC72001.execute - reqData is null (SLF4J) ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute END ===");
			slf4jLogger.info("=== ASMBC72001.execute END (SLF4J) ===");
			return null;
		}

		// 입력 객체 필드값 출력
		logger.info("ASMBC72001", "=== ASMBC72001.execute - Input Object Field Values ===");
		slf4jLogger.info("=== ASMBC72001.execute - Input Object Field Values (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC72001", "=== ASMBC72001.execute - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC72001.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		// inputDto의 주요 키들을 직접 확인
		if (inputDto != null) {
			logger.info("ASMBC72001", "=== ASMBC72001.execute - Checking inputDto contents ===");
			slf4jLogger.info("=== ASMBC72001.execute - Checking inputDto contents (SLF4J) ===");
			
			// 주요 키들을 직접 확인
			Object accountPDTOValue = inputDto.get("AccountPDTO");
			Object searchKeywordValue = inputDto.get("searchKeyword");
			Object accountTypeValue = inputDto.get("accountType");
			Object pageNumberValue = inputDto.get("pageNumber");
			Object pageSizeValue = inputDto.get("pageSize");
			
			logger.info("ASMBC72001", "=== ASMBC72001.execute - AccountPDTO value: " + accountPDTOValue + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - searchKeyword value: " + searchKeywordValue + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountType value: " + accountTypeValue + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - pageNumber value: " + pageNumberValue + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - pageSize value: " + pageSizeValue + " ===");
			
			slf4jLogger.info("=== ASMBC72001.execute - AccountPDTO value: {} (SLF4J) ===", accountPDTOValue);
			slf4jLogger.info("=== ASMBC72001.execute - searchKeyword value: {} (SLF4J) ===", searchKeywordValue);
			slf4jLogger.info("=== ASMBC72001.execute - accountType value: {} (SLF4J) ===", accountTypeValue);
			slf4jLogger.info("=== ASMBC72001.execute - pageNumber value: {} (SLF4J) ===", pageNumberValue);
			slf4jLogger.info("=== ASMBC72001.execute - pageSize value: {} (SLF4J) ===", pageSizeValue);
		}

		if (logger.isDebugEnabled())
			logger.debug(this.getClass().getName() + ", log test입니다.");
		
		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto()
				.using(NewGenericDto.INDATA).get("AccountPDTO");

		// AccountPDTO null 체크 - 목록 조회인 경우 처리
		if (accountPDTO == null) {
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO is null, checking for list search ===");
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO is null, checking for list search (SLF4J) ===");
			
			// 검색 조건 확인 - inputDto에서 직접 가져오기
			logger.info("ASMBC72001", "=== ASMBC72001.execute - inputDto: " + inputDto + " ===");
			slf4jLogger.info("=== ASMBC72001.execute - inputDto: {} (SLF4J) ===", inputDto);
			
			String searchKeyword = (String) inputDto.get("searchKeyword");
			String accountType = (String) inputDto.get("accountType");
			Integer pageNumber = (Integer) inputDto.get("pageNumber");
			Integer pageSize = (Integer) inputDto.get("pageSize");
			
			logger.info("ASMBC72001", "=== ASMBC72001.execute - searchKeyword: " + searchKeyword + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountType: " + accountType + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - pageNumber: " + pageNumber + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - pageSize: " + pageSize + " ===");
			
			slf4jLogger.info("=== ASMBC72001.execute - searchKeyword: {} (SLF4J) ===", searchKeyword);
			slf4jLogger.info("=== ASMBC72001.execute - accountType: {} (SLF4J) ===", accountType);
			slf4jLogger.info("=== ASMBC72001.execute - pageNumber: {} (SLF4J) ===", pageNumber);
			slf4jLogger.info("=== ASMBC72001.execute - pageSize: {} (SLF4J) ===", pageSize);
			
			// 목록 조회용 AccountPDTO 생성
			accountPDTO = new AccountPDTO();
			if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
				accountPDTO.setAccountId(searchKeyword); // 검색 키워드를 계정 ID로 사용
				logger.info("ASMBC72001", "=== ASMBC72001.execute - Set accountId: " + searchKeyword + " ===");
			}
			if (accountType != null && !accountType.trim().isEmpty()) {
				accountPDTO.setAccountType(accountType);
				logger.info("ASMBC72001", "=== ASMBC72001.execute - Set accountType: " + accountType + " ===");
			}
			
			logger.info("ASMBC72001", "=== ASMBC72001.execute - Created AccountPDTO: " + accountPDTO + " ===");
			slf4jLogger.info("=== ASMBC72001.execute - Created AccountPDTO: {} (SLF4J) ===", accountPDTO);
		}

		// AccountPDTO 필드값 상세 출력
		if (accountPDTO != null) {
			logger.info("ASMBC72001", "=== ASMBC72001.execute - AccountPDTO Field Values ===");
			slf4jLogger.info("=== ASMBC72001.execute - AccountPDTO Field Values (SLF4J) ===");
			
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO.accountId: " + accountPDTO.getAccountId() + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO.accountName: " + accountPDTO.getAccountName() + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO.accountStatus: " + accountPDTO.getAccountStatus() + " ===");
			logger.info("ASMBC72001", "=== ASMBC72001.execute - accountPDTO.accountBalance: " + accountPDTO.getAccountBalance() + " ===");
			
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO.accountId: {} (SLF4J) ===", accountPDTO.getAccountId());
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO.accountName: {} (SLF4J) ===", accountPDTO.getAccountName());
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO.accountStatus: {} (SLF4J) ===", accountPDTO.getAccountStatus());
			slf4jLogger.info("=== ASMBC72001.execute - accountPDTO.accountBalance: {} (SLF4J) ===", accountPDTO.getAccountBalance());
		}

		// 2.PC호출 - 목록 조회
		java.util.List<AccountPDTO> resultList = pcAccount.getListAccount(accountPDTO);

		// 3.결과를 OUTDATA에 set
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		
		logger.info("ASMBC72001", "=== ASMBC72001.execute END ===");
		slf4jLogger.info("=== ASMBC72001.execute END (SLF4J) ===");
		return reqData;
	}

}
