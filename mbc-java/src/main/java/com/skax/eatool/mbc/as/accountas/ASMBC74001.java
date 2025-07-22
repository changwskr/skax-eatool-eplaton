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
	 * [메서드명] 계정관리 (CRUD 작업)
	 * <br>
	 * [설명] API 요청에 따라 계정 생성, 조회, 수정, 삭제 작업을 수행
	 * <br>
	 * [상세설명] 요청에서 command 파라미터를 확인하여 적절한 PC 메서드를 호출
	 * 
	 * @param KBData
	 *               <ul>
	 *               <li>command //명령어 (CREATE, READ, UPDATE, DELETE, LIST)
	 *               <li>AccountPDTO //계정 정보
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
			Object commandValue = inputDto.using(NewGenericDto.INDATA).get("command");
			
			logger.info("ASMBC74001", "=== ASMBC74001.execute - AccountPDTO value: " + accountPDTOValue + " ===");
			logger.info("ASMBC74001", "=== ASMBC74001.execute - command value: " + commandValue + " ===");
			slf4jLogger.info("=== ASMBC74001.execute - AccountPDTO value: {} (SLF4J) ===", accountPDTOValue);
			slf4jLogger.info("=== ASMBC74001.execute - command value: {} (SLF4J) ===", commandValue);
		}

		// 1. 명령어 추출
		String command = (String) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("command");
		if (command == null) {
			command = "LIST"; // 기본값은 LIST
			logger.info("ASMBC74001", "=== ASMBC74001.execute - command is null, using default: LIST ===");
			slf4jLogger.info("=== ASMBC74001.execute - command is null, using default: LIST (SLF4J) ===");
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.execute - Processing command: " + command + " ===");
		slf4jLogger.info("=== ASMBC74001.execute - Processing command: {} (SLF4J) ===", command);

		// 2. 명령어에 따른 분기 처리
		NewKBData result = null;
		switch (command.toUpperCase()) {
			case "CREATE":
				result = processCreate(reqData);
				break;
			case "READ":
				result = processRead(reqData);
				break;
			case "UPDATE":
				result = processUpdate(reqData);
				break;
			case "DELETE":
				result = processDelete(reqData);
				break;
			case "LIST":
			default:
				result = processList(reqData);
				break;
		}
		
		// 3. 출력 결과 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.execute - Output Result Summary ===");
		slf4jLogger.info("=== ASMBC74001.execute - Output Result Summary (SLF4J) ===");
		
		if (result != null) {
			logger.info("ASMBC74001", "=== ASMBC74001.execute - result is not null ===");
			slf4jLogger.info("=== ASMBC74001.execute - result is not null (SLF4J) ===");
			
			NewGenericDto outputDto = result.getOutputGenericDto();
			if (outputDto != null) {
				logger.info("ASMBC74001", "=== ASMBC74001.execute - outputDto is not null ===");
				slf4jLogger.info("=== ASMBC74001.execute - outputDto is not null (SLF4J) ===");
				
				Object outData = outputDto.using(NewGenericDto.OUTDATA);
				logger.info("ASMBC74001", "=== ASMBC74001.execute - outData: " + outData + " ===");
				slf4jLogger.info("=== ASMBC74001.execute - outData: {} (SLF4J) ===", outData);
				
				if (outData instanceof java.util.List) {
					java.util.List<?> outList = (java.util.List<?>) outData;
					logger.info("ASMBC74001", "=== ASMBC74001.execute - Output list size: " + outList.size() + " ===");
					slf4jLogger.info("=== ASMBC74001.execute - Output list size: {} (SLF4J) ===", outList.size());
					
					for (int i = 0; i < Math.min(outList.size(), 3); i++) {
						Object item = outList.get(i);
						logger.info("ASMBC74001", "=== ASMBC74001.execute - Output item[" + i + "]: " + item + " ===");
						slf4jLogger.info("=== ASMBC74001.execute - Output item[{}]: {} (SLF4J) ===", i, item);
					}
					if (outList.size() > 3) {
						logger.info("ASMBC74001", "=== ASMBC74001.execute - ... and " + (outList.size() - 3) + " more items ===");
						slf4jLogger.info("=== ASMBC74001.execute - ... and {} more items (SLF4J) ===", outList.size() - 3);
					}
				}
			} else {
				logger.warn("ASMBC74001", "=== ASMBC74001.execute - outputDto is null ===");
				slf4jLogger.warn("=== ASMBC74001.execute - outputDto is null (SLF4J) ===");
			}
		} else {
			logger.warn("ASMBC74001", "=== ASMBC74001.execute - result is null ===");
			slf4jLogger.warn("=== ASMBC74001.execute - result is null (SLF4J) ===");
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.execute END ===");
		slf4jLogger.info("=== ASMBC74001.execute END (SLF4J) ===");
		return result;
	}
	
	/**
	 * 계정 생성 처리
	 */
	private NewKBData processCreate(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate START ===");
		slf4jLogger.info("=== ASMBC74001.processCreate START (SLF4J) ===");
		
		// 입력 데이터 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Input Data Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - Input Data Analysis (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - inputDto: {} (SLF4J) ===", inputDto);
		
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("AccountPDTO");
		
		if (accountPDTO == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.processCreate - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC74001.processCreate - accountPDTO is null (SLF4J) ===");
			return reqData;
		}
		
		// AccountPDTO 필드값 상세 출력
		logAccountPDTOFields("processCreate", accountPDTO);
		
		// PC 호출 전 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Calling PC: pcAccount.createAccount() ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - Calling PC: pcAccount.createAccount() (SLF4J) ===");
		
		// PC 호출 (void 메서드)
		pcAccount.createAccount(accountPDTO);
		
		// PC 호출 후 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - PC call completed ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - PC call completed (SLF4J) ===");
		
		// 결과를 OUTDATA에 set (입력된 accountPDTO를 그대로 사용)
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).put("AccountPDTOList", resultList);
		
		// 출력 결과 상세 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Output Result Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - Output Result Analysis (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Result list size: " + resultList.size() + " ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - Result list size: {} (SLF4J) ===", resultList.size());
		
		for (int i = 0; i < resultList.size(); i++) {
			AccountPDTO resultItem = resultList.get(i);
			logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Result item[" + i + "]: accountNumber=" + resultItem.getAccountNumber() + 
			           ", name=" + resultItem.getName() + ", accountType=" + resultItem.getAccountType() + " ===");
			slf4jLogger.info("=== ASMBC74001.processCreate - Result item[{}]: accountNumber={}, name={}, accountType={} (SLF4J) ===", 
			               i, resultItem.getAccountNumber(), resultItem.getName(), resultItem.getAccountType());
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate - Output Result: Account created successfully ===");
		slf4jLogger.info("=== ASMBC74001.processCreate - Output Result: Account created successfully (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processCreate END ===");
		slf4jLogger.info("=== ASMBC74001.processCreate END (SLF4J) ===");
		return reqData;
	}
	
	/**
	 * 계정 조회 처리
	 */
	private NewKBData processRead(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.processRead START ===");
		slf4jLogger.info("=== ASMBC74001.processRead START (SLF4J) ===");
		
		// 입력 데이터 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - Input Data Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processRead - Input Data Analysis (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.processRead - inputDto: {} (SLF4J) ===", inputDto);
		
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("AccountPDTO");
		
		if (accountPDTO == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.processRead - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC74001.processRead - accountPDTO is null (SLF4J) ===");
			return reqData;
		}
		
		// AccountPDTO 필드값 상세 출력
		logAccountPDTOFields("processRead", accountPDTO);
		
		// PC 호출 전 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - Calling PC: pcAccount.getAccount() ===");
		slf4jLogger.info("=== ASMBC74001.processRead - Calling PC: pcAccount.getAccount() (SLF4J) ===");
		
		// PC 호출
		AccountPDTO result = pcAccount.getAccount(accountPDTO);
		
		// PC 호출 후 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - PC call completed ===");
		slf4jLogger.info("=== ASMBC74001.processRead - PC call completed (SLF4J) ===");
		
		// PC 결과 로깅
		if (result != null) {
			logger.info("ASMBC74001", "=== ASMBC74001.processRead - PC result is not null ===");
			slf4jLogger.info("=== ASMBC74001.processRead - PC result is not null (SLF4J) ===");
			logAccountPDTOFields("processRead_PC_Result", result);
		} else {
			logger.warn("ASMBC74001", "=== ASMBC74001.processRead - PC result is null ===");
			slf4jLogger.warn("=== ASMBC74001.processRead - PC result is null (SLF4J) ===");
		}
		
		// 결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		if (result != null) {
			resultList.add(result);
		}
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).put("AccountPDTOList", resultList);
		
		// 출력 결과 상세 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - Output Result Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processRead - Output Result Analysis (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - Result list size: " + resultList.size() + " ===");
		slf4jLogger.info("=== ASMBC74001.processRead - Result list size: {} (SLF4J) ===", resultList.size());
		
		for (int i = 0; i < resultList.size(); i++) {
			AccountPDTO resultItem = resultList.get(i);
			logger.info("ASMBC74001", "=== ASMBC74001.processRead - Result item[" + i + "]: accountNumber=" + resultItem.getAccountNumber() + 
			           ", name=" + resultItem.getName() + ", accountType=" + resultItem.getAccountType() + " ===");
			slf4jLogger.info("=== ASMBC74001.processRead - Result item[{}]: accountNumber={}, name={}, accountType={} (SLF4J) ===", 
			               i, resultItem.getAccountNumber(), resultItem.getName(), resultItem.getAccountType());
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processRead - Output Result: Account retrieved successfully ===");
		slf4jLogger.info("=== ASMBC74001.processRead - Output Result: Account retrieved successfully (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processRead END ===");
		slf4jLogger.info("=== ASMBC74001.processRead END (SLF4J) ===");
		return reqData;
	}
	
	/**
	 * 계정 수정 처리
	 */
	private NewKBData processUpdate(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate START ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate START (SLF4J) ===");
		
		// 입력 데이터 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Input Data Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - Input Data Analysis (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - inputDto: {} (SLF4J) ===", inputDto);
		
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("AccountPDTO");
		
		if (accountPDTO == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.processUpdate - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC74001.processUpdate - accountPDTO is null (SLF4J) ===");
			return reqData;
		}
		
		// AccountPDTO 필드값 상세 출력
		logAccountPDTOFields("processUpdate", accountPDTO);
		
		// PC 호출 전 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Calling PC: pcAccount.updateAccount() ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - Calling PC: pcAccount.updateAccount() (SLF4J) ===");
		
		// PC 호출 (void 메서드)
		pcAccount.updateAccount(accountPDTO);
		
		// PC 호출 후 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - PC call completed ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - PC call completed (SLF4J) ===");
		
		// 결과를 OUTDATA에 set (입력된 accountPDTO를 그대로 사용)
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).put("AccountPDTOList", resultList);
		
		// 출력 결과 상세 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Output Result Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - Output Result Analysis (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Result list size: " + resultList.size() + " ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - Result list size: {} (SLF4J) ===", resultList.size());
		
		for (int i = 0; i < resultList.size(); i++) {
			AccountPDTO resultItem = resultList.get(i);
			logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Result item[" + i + "]: accountNumber=" + resultItem.getAccountNumber() + 
			           ", name=" + resultItem.getName() + ", accountType=" + resultItem.getAccountType() + " ===");
			slf4jLogger.info("=== ASMBC74001.processUpdate - Result item[{}]: accountNumber={}, name={}, accountType={} (SLF4J) ===", 
			               i, resultItem.getAccountNumber(), resultItem.getName(), resultItem.getAccountType());
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate - Output Result: Account updated successfully ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate - Output Result: Account updated successfully (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processUpdate END ===");
		slf4jLogger.info("=== ASMBC74001.processUpdate END (SLF4J) ===");
		return reqData;
	}
	
	/**
	 * 계정 삭제 처리
	 */
	private NewKBData processDelete(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete START ===");
		slf4jLogger.info("=== ASMBC74001.processDelete START (SLF4J) ===");
		
		// 입력 데이터 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Input Data Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - Input Data Analysis (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - inputDto: {} (SLF4J) ===", inputDto);
		
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("AccountPDTO");
		
		if (accountPDTO == null) {
			logger.warn("ASMBC74001", "=== ASMBC74001.processDelete - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC74001.processDelete - accountPDTO is null (SLF4J) ===");
			return reqData;
		}
		
		// AccountPDTO 필드값 상세 출력
		logAccountPDTOFields("processDelete", accountPDTO);
		
		// PC 호출 전 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Calling PC: pcAccount.deleteAccount() ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - Calling PC: pcAccount.deleteAccount() (SLF4J) ===");
		
		// PC 호출
		pcAccount.deleteAccount(accountPDTO);
		
		// PC 호출 후 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - PC call completed ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - PC call completed (SLF4J) ===");
		
		// 결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).put("AccountPDTOList", resultList);
		
		// 출력 결과 상세 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Output Result Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - Output Result Analysis (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Result list size: " + resultList.size() + " ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - Result list size: {} (SLF4J) ===", resultList.size());
		
		for (int i = 0; i < resultList.size(); i++) {
			AccountPDTO resultItem = resultList.get(i);
			logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Result item[" + i + "]: accountNumber=" + resultItem.getAccountNumber() + 
			           ", name=" + resultItem.getName() + ", accountType=" + resultItem.getAccountType() + " ===");
			slf4jLogger.info("=== ASMBC74001.processDelete - Result item[{}]: accountNumber={}, name={}, accountType={} (SLF4J) ===", 
			               i, resultItem.getAccountNumber(), resultItem.getName(), resultItem.getAccountType());
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete - Output Result: Account deleted successfully ===");
		slf4jLogger.info("=== ASMBC74001.processDelete - Output Result: Account deleted successfully (SLF4J) ===");
		
		logger.info("ASMBC74001", "=== ASMBC74001.processDelete END ===");
		slf4jLogger.info("=== ASMBC74001.processDelete END (SLF4J) ===");
		return reqData;
	}
	
	/**
	 * 계정 목록 조회 처리
	 */
	private NewKBData processList(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC74001", "=== ASMBC74001.processList START ===");
		slf4jLogger.info("=== ASMBC74001.processList START (SLF4J) ===");
		
		// 입력 데이터 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processList - Input Data Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processList - Input Data Analysis (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC74001", "=== ASMBC74001.processList - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC74001.processList - inputDto: {} (SLF4J) ===", inputDto);
		
		// 페이징 정보 로깅
		Object pageNumber = inputDto.using(NewGenericDto.INDATA).get("pageNumber");
		Object pageSize = inputDto.using(NewGenericDto.INDATA).get("pageSize");
		logger.info("ASMBC74001", "=== ASMBC74001.processList - pageNumber: " + pageNumber + ", pageSize: " + pageSize + " ===");
		slf4jLogger.info("=== ASMBC74001.processList - pageNumber: {}, pageSize: {} (SLF4J) ===", pageNumber, pageSize);
		
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA).get("AccountPDTO");
		
		// AccountPDTO 상세 출력 프로그램
		logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO Detailed Output Program START ===");
		slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO Detailed Output Program START (SLF4J) ===");
		
		if (accountPDTO != null) {
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO is NOT NULL ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO is NOT NULL (SLF4J) ===");
			
			// 1. AccountPDTO 객체 자체 출력
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO object: " + accountPDTO + " ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO object: {} (SLF4J) ===", accountPDTO);
			
			// 2. AccountPDTO 클래스 정보 출력
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO class: " + accountPDTO.getClass().getName() + " ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO class: {} (SLF4J) ===", accountPDTO.getClass().getName());
			
			// 3. 각 필드별 상세 출력
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO Field-by-Field Analysis ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO Field-by-Field Analysis (SLF4J) ===");
			
			// accountNumber
			String accountNumber = accountPDTO.getAccountNumber();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - accountNumber: '" + accountNumber + "' (type: " + (accountNumber != null ? accountNumber.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - accountNumber: '{}' (type: {}) (SLF4J) ===", accountNumber, accountNumber != null ? accountNumber.getClass().getSimpleName() : "null");
			
			// name
			String name = accountPDTO.getName();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - name: '" + name + "' (type: " + (name != null ? name.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - name: '{}' (type: {}) (SLF4J) ===", name, name != null ? name.getClass().getSimpleName() : "null");
			
			// accountType
			String accountType = accountPDTO.getAccountType();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - accountType: '" + accountType + "' (type: " + (accountType != null ? accountType.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - accountType: '{}' (type: {}) (SLF4J) ===", accountType, accountType != null ? accountType.getClass().getSimpleName() : "null");
			
			// status
			String status = accountPDTO.getStatus();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - status: '" + status + "' (type: " + (status != null ? status.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - status: '{}' (type: {}) (SLF4J) ===", status, status != null ? status.getClass().getSimpleName() : "null");
			
			// netAmount
			String netAmount = accountPDTO.getNetAmount();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - netAmount: '" + netAmount + "' (type: " + (netAmount != null ? netAmount.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - netAmount: '{}' (type: {}) (SLF4J) ===", netAmount, netAmount != null ? netAmount.getClass().getSimpleName() : "null");
			
			// currency
			String currency = accountPDTO.getCurrency();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - currency: '" + currency + "' (type: " + (currency != null ? currency.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - currency: '{}' (type: {}) (SLF4J) ===", currency, currency != null ? currency.getClass().getSimpleName() : "null");
			
			// interestRate
			String interestRate = accountPDTO.getInterestRate();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - interestRate: '" + interestRate + "' (type: " + (interestRate != null ? interestRate.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - interestRate: '{}' (type: {}) (SLF4J) ===", interestRate, interestRate != null ? interestRate.getClass().getSimpleName() : "null");
			
			// identificationNumber
			String identificationNumber = accountPDTO.getIdentificationNumber();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - identificationNumber: '" + identificationNumber + "' (type: " + (identificationNumber != null ? identificationNumber.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - identificationNumber: '{}' (type: {}) (SLF4J) ===", identificationNumber, identificationNumber != null ? identificationNumber.getClass().getSimpleName() : "null");
			
			// password
			String password = accountPDTO.getPassword();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - password: '" + (password != null ? "***MASKED***" : "null") + "' (type: " + (password != null ? password.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - password: '{}' (type: {}) (SLF4J) ===", password != null ? "***MASKED***" : "null", password != null ? password.getClass().getSimpleName() : "null");
			
			// createdDate
			String createdDate = accountPDTO.getCreatedDate();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - createdDate: '" + createdDate + "' (type: " + (createdDate != null ? createdDate.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - createdDate: '{}' (type: {}) (SLF4J) ===", createdDate, createdDate != null ? createdDate.getClass().getSimpleName() : "null");
			
			// updatedDate
			String updatedDate = accountPDTO.getUpdatedDate();
			logger.info("ASMBC74001", "=== ASMBC74001.processList - updatedDate: '" + updatedDate + "' (type: " + (updatedDate != null ? updatedDate.getClass().getSimpleName() : "null") + ") ===");
			slf4jLogger.info("=== ASMBC74001.processList - updatedDate: '{}' (type: {}) (SLF4J) ===", updatedDate, updatedDate != null ? updatedDate.getClass().getSimpleName() : "null");
			
			// 4. 필드 요약 정보
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO Field Summary ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO Field Summary (SLF4J) ===");
			
			int nonNullFields = 0;
			if (accountNumber != null) nonNullFields++;
			if (name != null) nonNullFields++;
			if (accountType != null) nonNullFields++;
			if (status != null) nonNullFields++;
			if (netAmount != null) nonNullFields++;
			if (currency != null) nonNullFields++;
			if (interestRate != null) nonNullFields++;
			if (identificationNumber != null) nonNullFields++;
			if (password != null) nonNullFields++;
			if (createdDate != null) nonNullFields++;
			if (updatedDate != null) nonNullFields++;
			
			logger.info("ASMBC74001", "=== ASMBC74001.processList - Total fields: 11, Non-null fields: " + nonNullFields + ", Null fields: " + (11 - nonNullFields) + " ===");
			slf4jLogger.info("=== ASMBC74001.processList - Total fields: 11, Non-null fields: {}, Null fields: {} (SLF4J) ===", nonNullFields, 11 - nonNullFields);
			
			// 5. 검색 조건 분석
			logger.info("ASMBC74001", "=== ASMBC74001.processList - Search Criteria Analysis ===");
			slf4jLogger.info("=== ASMBC74001.processList - Search Criteria Analysis (SLF4J) ===");
			
			boolean hasSearchCriteria = false;
			if (accountNumber != null && !accountNumber.trim().isEmpty()) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - Search by accountNumber: '" + accountNumber + "' ===");
				slf4jLogger.info("=== ASMBC74001.processList - Search by accountNumber: '{}' (SLF4J) ===", accountNumber);
				hasSearchCriteria = true;
			}
			if (name != null && !name.trim().isEmpty()) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - Search by name: '" + name + "' ===");
				slf4jLogger.info("=== ASMBC74001.processList - Search by name: '{}' (SLF4J) ===", name);
				hasSearchCriteria = true;
			}
			if (accountType != null && !accountType.trim().isEmpty()) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - Search by accountType: '" + accountType + "' ===");
				slf4jLogger.info("=== ASMBC74001.processList - Search by accountType: '{}' (SLF4J) ===", accountType);
				hasSearchCriteria = true;
			}
			if (status != null && !status.trim().isEmpty()) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - Search by status: '" + status + "' ===");
				slf4jLogger.info("=== ASMBC74001.processList - Search by status: '{}' (SLF4J) ===", status);
				hasSearchCriteria = true;
			}
			
			if (!hasSearchCriteria) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - No specific search criteria provided (will return all accounts) ===");
				slf4jLogger.info("=== ASMBC74001.processList - No specific search criteria provided (will return all accounts) (SLF4J) ===");
			}
			
			// 6. 기존 logAccountPDTOFields 호출
			logAccountPDTOFields("processList", accountPDTO);
			
		} else {
			logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO is NULL ===");
			slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO is NULL (SLF4J) ===");
			logger.info("ASMBC74001", "=== ASMBC74001.processList - No search criteria provided (will return all accounts) ===");
			slf4jLogger.info("=== ASMBC74001.processList - No search criteria provided (will return all accounts) (SLF4J) ===");
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processList - AccountPDTO Detailed Output Program END ===");
		slf4jLogger.info("=== ASMBC74001.processList - AccountPDTO Detailed Output Program END (SLF4J) ===");
		
		// PC 호출 전 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processList - Calling PC: pcAccount.getListAccount() ===");
		slf4jLogger.info("=== ASMBC74001.processList - Calling PC: pcAccount.getListAccount() (SLF4J) ===");
		
		// PC 호출
		java.util.List<AccountPDTO> resultList = pcAccount.getListAccount(accountPDTO);


		
		// PC 호출 후 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processList - PC call completed ===");
		slf4jLogger.info("=== ASMBC74001.processList - PC call completed (SLF4J) ===");
		
		// PC 결과 로깅
		if (resultList != null) {
			logger.info("ASMBC74001", "=== ASMBC74001.processList - PC result is not null, size: " + resultList.size() + " ===");
			slf4jLogger.info("=== ASMBC74001.processList - PC result is not null, size: {} (SLF4J) ===", resultList.size());
			
			// 처음 3개 항목만 상세 로깅
			for (int i = 0; i < Math.min(resultList.size(), 3); i++) {
				AccountPDTO item = resultList.get(i);
				logger.info("ASMBC74001", "=== ASMBC74001.processList - PC result item[" + i + "]: accountNumber=" + item.getAccountNumber() + 
				           ", name=" + item.getName() + ", accountType=" + item.getAccountType() + " ===");
				slf4jLogger.info("=== ASMBC74001.processList - PC result item[{}]: accountNumber={}, name={}, accountType={} (SLF4J) ===", 
				               i, item.getAccountNumber(), item.getName(), item.getAccountType());
			}
			if (resultList.size() > 3) {
				logger.info("ASMBC74001", "=== ASMBC74001.processList - ... and " + (resultList.size() - 3) + " more items from PC ===");
				slf4jLogger.info("=== ASMBC74001.processList - ... and {} more items from PC (SLF4J) ===", resultList.size() - 3);
			}
		} else {
			logger.warn("ASMBC74001", "=== ASMBC74001.processList - PC result is null ===");
			slf4jLogger.warn("=== ASMBC74001.processList - PC result is null (SLF4J) ===");
		}
		
		// 결과를 OUTDATA에 set
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).put("AccountPDTOList", resultList);
		
		// 출력 결과 상세 로깅
		logger.info("ASMBC74001", "=== ASMBC74001.processList - Output Result Analysis ===");
		slf4jLogger.info("=== ASMBC74001.processList - Output Result Analysis (SLF4J) ===");
		
		NewGenericDto outputDto = reqData.getOutputGenericDto();
		if (outputDto != null) {
			Object outData = outputDto.using(NewGenericDto.OUTDATA);
			logger.info("ASMBC74001", "=== ASMBC74001.processList - outData: " + outData + " ===");
			slf4jLogger.info("=== ASMBC74001.processList - outData: {} (SLF4J) ===", outData);
			
			if (outData instanceof java.util.List) {
				java.util.List<?> outList = (java.util.List<?>) outData;
				logger.info("ASMBC74001", "=== ASMBC74001.processList - Final output list size: " + outList.size() + " ===");
				slf4jLogger.info("=== ASMBC74001.processList - Final output list size: {} (SLF4J) ===", outList.size());
				
				// 처음 3개 항목만 상세 로깅
				for (int i = 0; i < Math.min(outList.size(), 3); i++) {
					Object item = outList.get(i);
					logger.info("ASMBC74001", "=== ASMBC74001.processList - Final output item[" + i + "]: " + item + " ===");
					slf4jLogger.info("=== ASMBC74001.processList - Final output item[{}]: {} (SLF4J) ===", i, item);
				}
				if (outList.size() > 3) {
					logger.info("ASMBC74001", "=== ASMBC74001.processList - ... and " + (outList.size() - 3) + " more items in final output ===");
					slf4jLogger.info("=== ASMBC74001.processList - ... and {} more items in final output (SLF4J) ===", outList.size() - 3);
				}
			}
		} else {
			logger.warn("ASMBC74001", "=== ASMBC74001.processList - outputDto is null ===");
			slf4jLogger.warn("=== ASMBC74001.processList - outputDto is null (SLF4J) ===");
		}
		
		logger.info("ASMBC74001", "=== ASMBC74001.processList - Output Result: Account list retrieved successfully, count: " + (resultList != null ? resultList.size() : 0) + " ===");
		slf4jLogger.info("=== ASMBC74001.processList - Output Result: Account list retrieved successfully, count: {} (SLF4J) ===", resultList != null ? resultList.size() : 0);
		
		logger.info("ASMBC74001", "=== ASMBC74001.processList END ===");
		slf4jLogger.info("=== ASMBC74001.processList END (SLF4J) ===");
		return reqData;
	}
	
	/**
	 * AccountPDTO 필드값 로깅 헬퍼 메서드
	 */
	private void logAccountPDTOFields(String methodName, AccountPDTO accountPDTO) {
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - AccountPDTO Field Values ===");
		slf4jLogger.info("=== ASMBC74001.{} - AccountPDTO Field Values (SLF4J) ===", methodName);
		
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.accountNumber: " + accountPDTO.getAccountNumber() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.name: " + accountPDTO.getName() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.status: " + accountPDTO.getStatus() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.netAmount: " + accountPDTO.getNetAmount() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.currency: " + accountPDTO.getCurrency() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.interestRate: " + accountPDTO.getInterestRate() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.identificationNumber: " + accountPDTO.getIdentificationNumber() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.createdDate: " + accountPDTO.getCreatedDate() + " ===");
		logger.info("ASMBC74001", "=== ASMBC74001." + methodName + " - accountPDTO.updatedDate: " + accountPDTO.getUpdatedDate() + " ===");
		
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.accountNumber: {} (SLF4J) ===", methodName, accountPDTO.getAccountNumber());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.name: {} (SLF4J) ===", methodName, accountPDTO.getName());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.accountType: {} (SLF4J) ===", methodName, accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.status: {} (SLF4J) ===", methodName, accountPDTO.getStatus());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.netAmount: {} (SLF4J) ===", methodName, accountPDTO.getNetAmount());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.currency: {} (SLF4J) ===", methodName, accountPDTO.getCurrency());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.interestRate: {} (SLF4J) ===", methodName, accountPDTO.getInterestRate());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.identificationNumber: {} (SLF4J) ===", methodName, accountPDTO.getIdentificationNumber());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.createdDate: {} (SLF4J) ===", methodName, accountPDTO.getCreatedDate());
		slf4jLogger.info("=== ASMBC74001.{} - accountPDTO.updatedDate: {} (SLF4J) ===", methodName, accountPDTO.getUpdatedDate());
	}

}