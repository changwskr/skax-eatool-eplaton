/*
 * (@)# PCAccount.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.pc.accountpc;

import java.util.ArrayList;
import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.oltp.biz.NewIProcessComponent;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.accountdc.DCAccount;
import com.skax.eatool.mbc.dc.accountdc.dto.AccountDDTO;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Account Process Component
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
@Component
public class PCAccount implements NewIProcessComponent {

	private static final Logger logger = LoggerFactory.getLogger(PCAccount.class);
	
	@Autowired
	private DCAccount dcAccount;

	/**
	 * Method Name: getAccount
	 * Description: Get account information
	 * 
	 * @param AccountPDTO
	 *                    <ul>
	 *                    <li>accountNumber // Account number
	 *                    </ul>
	 * @return AccountPDTO
	 *         <ul>
	 *         <li>accountNumber // Account number
	 *         <li>name // Name
	 *         <li>identificationNumber // ID number
	 *         <li>interestRate // Interest rate
	 *         <li>lastTransaction // Last transaction date
	 *         <li>password // Password
	 *         <li>netAmount // Balance
	 *         </ul>
	 */
	public AccountPDTO getAccount(AccountPDTO accountPDTO) throws NewBusinessException {
		logger.info("=== PCAccount.getAccount START ===");
		
		// Null 체크 추가
		if (accountPDTO == null) {
			logger.warn("=== PCAccount.getAccount - accountPDTO is null ===");
			logger.info("=== PCAccount.getAccount END ===");
			return null;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== PCAccount.getAccount - Input AccountPDTO Field Values ===");
		logger.info("=== PCAccount.getAccount - accountPDTO.accountNumber: {} ===", accountPDTO.getAccountNumber());
		logger.info("=== PCAccount.getAccount - accountPDTO.name: {} ===", accountPDTO.getName());
		logger.info("=== PCAccount.getAccount - accountPDTO.accountType: {} ===", accountPDTO.getAccountType());
		logger.info("=== PCAccount.getAccount - accountPDTO.status: {} ===", accountPDTO.getStatus());
		logger.info("=== PCAccount.getAccount - accountPDTO.netAmount: {} ===", accountPDTO.getNetAmount());
		logger.info("=== PCAccount.getAccount - accountPDTO.currency: {} ===", accountPDTO.getCurrency());
		logger.info("=== PCAccount.getAccount - accountPDTO.interestRate: {} ===", accountPDTO.getInterestRate());
		logger.info("=== PCAccount.getAccount - accountPDTO.identificationNumber: {} ===", accountPDTO.getIdentificationNumber());
		logger.info("=== PCAccount.getAccount - accountPDTO.createdDate: {} ===", accountPDTO.getCreatedDate());
		logger.info("=== PCAccount.getAccount - accountPDTO.updatedDate: {} ===", accountPDTO.getUpdatedDate());
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.getAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		AccountDDTO searchDto = NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO);
		
		// searchDto null 체크 추가
		if (searchDto == null) {
			logger.warn("=== PCAccount.getAccount - searchDto is null after copy, creating new instance ===");
			searchDto = new AccountDDTO();
		}
		
		// 수동으로 검색 조건 복사 (NewObjectUtil이 제대로 작동하지 않을 경우)
		if (accountPDTO != null) {
			if (accountPDTO.getAccountNumber() != null && !accountPDTO.getAccountNumber().trim().isEmpty()) {
				searchDto.setAccountNumber(accountPDTO.getAccountNumber());
				logger.info("PCAccount", "=== PCAccount.getAccount - Manually copied accountNumber: " + accountPDTO.getAccountNumber() + " ===");
			}
			if (accountPDTO.getName() != null && !accountPDTO.getName().trim().isEmpty()) {
				searchDto.setName(accountPDTO.getName());
				logger.info("PCAccount", "=== PCAccount.getAccount - Manually copied name: " + accountPDTO.getName() + " ===");
			}
			if (accountPDTO.getAccountType() != null && !accountPDTO.getAccountType().trim().isEmpty()) {
				searchDto.setAccountType(accountPDTO.getAccountType());
				logger.info("PCAccount", "=== PCAccount.getAccount - Manually copied accountType: " + accountPDTO.getAccountType() + " ===");
			}
			if (accountPDTO.getStatus() != null && !accountPDTO.getStatus().trim().isEmpty()) {
				searchDto.setStatus(accountPDTO.getStatus());
				logger.info("PCAccount", "=== PCAccount.getAccount - Manually copied status: " + accountPDTO.getStatus() + " ===");
			}
		}
		
		AccountDDTO dDto = dcAccount.getAccount(searchDto);
		
		// 출력 객체 필드값 출력
		logger.info("=== PCAccount.getAccount - Output AccountDDTO Field Values ===");
		if (dDto != null) {
			logger.info("=== PCAccount.getAccount - dDto.accountNumber: {} ===", dDto.getAccountNumber());
			logger.info("=== PCAccount.getAccount - dDto.name: {} ===", dDto.getName());
			logger.info("=== PCAccount.getAccount - dDto.accountType: {} ===", dDto.getAccountType());
			logger.info("=== PCAccount.getAccount - dDto.status: {} ===", dDto.getStatus());
			logger.info("=== PCAccount.getAccount - dDto.netAmount: {} ===", dDto.getNetAmount());
			logger.info("=== PCAccount.getAccount - dDto.currency: {} ===", dDto.getCurrency());
			logger.info("=== PCAccount.getAccount - dDto.interestRate: {} ===", dDto.getInterestRate());
			logger.info("=== PCAccount.getAccount - dDto.identificationNumber: {} ===", dDto.getIdentificationNumber());
			logger.info("=== PCAccount.getAccount - dDto.createdDate: {} ===", dDto.getCreatedDate());
			logger.info("=== PCAccount.getAccount - dDto.updatedDate: {} ===", dDto.getUpdatedDate());
		} else {
			logger.warn("=== PCAccount.getAccount - dDto is null ===");
		}
		
		AccountPDTO result = NewObjectUtil.copyForClass(AccountPDTO.class, dDto);
		
		// result null 체크 및 수동 변환
		if (result == null) {
			logger.warn("=== PCAccount.getAccount - result is null after copy, creating manual conversion ===");
			result = new AccountPDTO();
			if (dDto != null) {
				result.setAccountNumber(dDto.getAccountNumber());
				result.setName(dDto.getName());
				result.setAccountType(dDto.getAccountType());
				result.setStatus(dDto.getStatus());
				result.setNetAmount(dDto.getNetAmount() != null ? dDto.getNetAmount().toString() : "0");
				result.setCurrency(dDto.getCurrency());
				result.setInterestRate(dDto.getInterestRate() != null ? dDto.getInterestRate().toString() : "0");
				result.setIdentificationNumber(dDto.getIdentificationNumber());
				result.setPassword(dDto.getPassword());
				result.setCreatedDate(dDto.getCreatedDate() != null ? dDto.getCreatedDate().toString() : "");
				result.setUpdatedDate(dDto.getUpdatedDate() != null ? dDto.getUpdatedDate().toString() : "");
			}
			logger.info("=== PCAccount.getAccount - Manual conversion completed ===");
		}
		
		// 최종 출력 객체 필드값 출력
		logger.info("=== PCAccount.getAccount - Final Output AccountPDTO Field Values ===");
		if (result != null) {
			logger.info("=== PCAccount.getAccount - result.accountNumber: {} ===", result.getAccountNumber());
			logger.info("=== PCAccount.getAccount - result.name: {} ===", result.getName());
			logger.info("=== PCAccount.getAccount - result.accountType: {} ===", result.getAccountType());
			logger.info("=== PCAccount.getAccount - result.status: {} ===", result.getStatus());
			logger.info("=== PCAccount.getAccount - result.netAmount: {} ===", result.getNetAmount());
			logger.info("=== PCAccount.getAccount - result.currency: {} ===", result.getCurrency());
			logger.info("=== PCAccount.getAccount - result.interestRate: {} ===", result.getInterestRate());
			logger.info("=== PCAccount.getAccount - result.identificationNumber: {} ===", result.getIdentificationNumber());
			logger.info("=== PCAccount.getAccount - result.createdDate: {} ===", result.getCreatedDate());
			logger.info("=== PCAccount.getAccount - result.updatedDate: {} ===", result.getUpdatedDate());
		} else {
			logger.warn("=== PCAccount.getAccount - result is null ===");
		}
		
		logger.info("=== PCAccount.getAccount END ===");
		return result;
	}

	/**
	 * Method Name: updateAccount
	 * Description: Update account information
	 * 
	 * @param AccountPDTO
	 *                    <ul>
	 *                    <li>accountNumber // Account number
	 *                    <li>name // Name
	 *                    <li>identificationNumber // ID number
	 *                    <li>interestRate // Interest rate
	 *                    <li>lastTransaction // Last transaction date
	 *                    <li>password // Password
	 *                    <li>netAmount // Balance
	 *                    </ul>
	 * @return void
	 */
	public void updateAccount(AccountPDTO accountPDTO) throws NewBusinessException {
		logger.info("=== PCAccount.updateAccount START ===");
		
		// Null 체크 추가
		if (accountPDTO == null) {
			logger.warn("=== PCAccount.updateAccount - accountPDTO is null ===");
			logger.info("=== PCAccount.updateAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== PCAccount.updateAccount - Input AccountPDTO Field Values ===");
		logger.info("=== PCAccount.updateAccount - accountPDTO.accountNumber: {} ===", accountPDTO.getAccountNumber());
		logger.info("=== PCAccount.updateAccount - accountPDTO.name: {} ===", accountPDTO.getName());
		logger.info("=== PCAccount.updateAccount - accountPDTO.accountType: {} ===", accountPDTO.getAccountType());
		logger.info("=== PCAccount.updateAccount - accountPDTO.status: {} ===", accountPDTO.getStatus());
		logger.info("=== PCAccount.updateAccount - accountPDTO.netAmount: {} ===", accountPDTO.getNetAmount());
		logger.info("=== PCAccount.updateAccount - accountPDTO.currency: {} ===", accountPDTO.getCurrency());
		logger.info("=== PCAccount.updateAccount - accountPDTO.interestRate: {} ===", accountPDTO.getInterestRate());
		logger.info("=== PCAccount.updateAccount - accountPDTO.identificationNumber: {} ===", accountPDTO.getIdentificationNumber());
		logger.info("=== PCAccount.updateAccount - accountPDTO.createdDate: {} ===", accountPDTO.getCreatedDate());
		logger.info("=== PCAccount.updateAccount - accountPDTO.updatedDate: {} ===", accountPDTO.getUpdatedDate());
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.updateAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		AccountDDTO dDto = NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO);
		
		// dDto null 체크 추가
		if (dDto == null) {
			logger.warn("=== PCAccount.updateAccount - dDto is null after copy, creating new instance ===");
			dDto = new AccountDDTO();
			// 수동으로 필드 복사 (타입 변환 포함)
			dDto.setAccountNumber(accountPDTO.getAccountNumber());
			dDto.setName(accountPDTO.getName());
			dDto.setAccountType(accountPDTO.getAccountType());
			dDto.setStatus(accountPDTO.getStatus());
			// String -> Double 변환
			if (accountPDTO.getNetAmount() != null && !accountPDTO.getNetAmount().trim().isEmpty()) {
				try {
					dDto.setNetAmount(Double.parseDouble(accountPDTO.getNetAmount()));
				} catch (NumberFormatException e) {
					logger.warn("=== PCAccount.updateAccount - Failed to parse netAmount: {} ===", accountPDTO.getNetAmount());
				}
			}
			dDto.setCurrency(accountPDTO.getCurrency());
			// String -> Double 변환
			if (accountPDTO.getInterestRate() != null && !accountPDTO.getInterestRate().trim().isEmpty()) {
				try {
					dDto.setInterestRate(Double.parseDouble(accountPDTO.getInterestRate()));
				} catch (NumberFormatException e) {
					logger.warn("=== PCAccount.updateAccount - Failed to parse interestRate: {} ===", accountPDTO.getInterestRate());
				}
			}
			dDto.setIdentificationNumber(accountPDTO.getIdentificationNumber());
			dDto.setPassword(accountPDTO.getPassword());
			// String -> Date 변환
			if (accountPDTO.getCreatedDate() != null && !accountPDTO.getCreatedDate().trim().isEmpty()) {
				try {
					// ISO 8601 형식 (yyyy-MM-dd HH:mm:ss) 또는 다른 형식 처리
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dDto.setCreatedDate(sdf.parse(accountPDTO.getCreatedDate()));
				} catch (Exception e) {
					logger.warn("=== PCAccount.updateAccount - Failed to parse createdDate: {} ===", accountPDTO.getCreatedDate());
				}
			}
			if (accountPDTO.getUpdatedDate() != null && !accountPDTO.getUpdatedDate().trim().isEmpty()) {
				try {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dDto.setUpdatedDate(sdf.parse(accountPDTO.getUpdatedDate()));
				} catch (Exception e) {
					logger.warn("=== PCAccount.updateAccount - Failed to parse updatedDate: {} ===", accountPDTO.getUpdatedDate());
				}
			}
			logger.info("=== PCAccount.updateAccount - Manual field copy completed ===");
		}
		
		// 변환된 DDTO 필드값 출력
		logger.info("=== PCAccount.updateAccount - Converted AccountDDTO Field Values ===");
		logger.info("=== PCAccount.updateAccount - dDto.accountNumber: {} ===", dDto.getAccountNumber());
		logger.info("=== PCAccount.updateAccount - dDto.name: {} ===", dDto.getName());
		logger.info("=== PCAccount.updateAccount - dDto.accountType: {} ===", dDto.getAccountType());
		logger.info("=== PCAccount.updateAccount - dDto.status: {} ===", dDto.getStatus());
		logger.info("=== PCAccount.updateAccount - dDto.netAmount: {} ===", dDto.getNetAmount());
		logger.info("=== PCAccount.updateAccount - dDto.currency: {} ===", dDto.getCurrency());
		logger.info("=== PCAccount.updateAccount - dDto.interestRate: {} ===", dDto.getInterestRate());
		logger.info("=== PCAccount.updateAccount - dDto.identificationNumber: {} ===", dDto.getIdentificationNumber());
		logger.info("=== PCAccount.updateAccount - dDto.createdDate: {} ===", dDto.getCreatedDate());
		logger.info("=== PCAccount.updateAccount - dDto.updatedDate: {} ===", dDto.getUpdatedDate());
		
		dcAccount.updateAccount(dDto);
		
		logger.info("=== PCAccount.updateAccount - Account updated successfully ===");
		logger.info("=== PCAccount.updateAccount END ===");
	}

	/**
	 * Method Name: deleteAccount
	 * Description: Delete account
	 * 
	 * @param AccountPDTO
	 *                    <ul>
	 *                    <li>accountNumber // Account number
	 *                    </ul>
	 * @return void
	 */
	public void deleteAccount(AccountPDTO accountPDTO) throws NewBusinessException {
		logger.info("=== PCAccount.deleteAccount START ===");
		
		// Null 체크 추가
		if (accountPDTO == null) {
			logger.warn("=== PCAccount.deleteAccount - accountPDTO is null ===");
			logger.info("=== PCAccount.deleteAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== PCAccount.deleteAccount - Input AccountPDTO Field Values ===");
		logger.info("=== PCAccount.deleteAccount - accountPDTO.accountNumber: {} ===", accountPDTO.getAccountNumber());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.name: {} ===", accountPDTO.getName());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.accountType: {} ===", accountPDTO.getAccountType());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.status: {} ===", accountPDTO.getStatus());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.netAmount: {} ===", accountPDTO.getNetAmount());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.currency: {} ===", accountPDTO.getCurrency());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.interestRate: {} ===", accountPDTO.getInterestRate());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.identificationNumber: {} ===", accountPDTO.getIdentificationNumber());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.createdDate: {} ===", accountPDTO.getCreatedDate());
		logger.info("=== PCAccount.deleteAccount - accountPDTO.updatedDate: {} ===", accountPDTO.getUpdatedDate());
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.deleteAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		AccountDDTO dDto = NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO);
		
		// dDto null 체크 추가
		if (dDto == null) {
			logger.warn("=== PCAccount.deleteAccount - dDto is null after copy, creating new instance ===");
			dDto = new AccountDDTO();
			// 수동으로 필드 복사 (타입 변환 포함)
			dDto.setAccountNumber(accountPDTO.getAccountNumber());
			dDto.setName(accountPDTO.getName());
			dDto.setAccountType(accountPDTO.getAccountType());
			dDto.setStatus(accountPDTO.getStatus());
			// String -> Double 변환
			if (accountPDTO.getNetAmount() != null && !accountPDTO.getNetAmount().trim().isEmpty()) {
				try {
					dDto.setNetAmount(Double.parseDouble(accountPDTO.getNetAmount()));
				} catch (NumberFormatException e) {
					logger.warn("=== PCAccount.deleteAccount - Failed to parse netAmount: {} ===", accountPDTO.getNetAmount());
				}
			}
			dDto.setCurrency(accountPDTO.getCurrency());
			// String -> Double 변환
			if (accountPDTO.getInterestRate() != null && !accountPDTO.getInterestRate().trim().isEmpty()) {
				try {
					dDto.setInterestRate(Double.parseDouble(accountPDTO.getInterestRate()));
				} catch (NumberFormatException e) {
					logger.warn("=== PCAccount.deleteAccount - Failed to parse interestRate: {} ===", accountPDTO.getInterestRate());
				}
			}
			dDto.setIdentificationNumber(accountPDTO.getIdentificationNumber());
			dDto.setPassword(accountPDTO.getPassword());
			logger.info("=== PCAccount.deleteAccount - Manual field copy completed ===");
		}
		
		// 변환된 DDTO 필드값 출력
		logger.info("=== PCAccount.deleteAccount - Converted AccountDDTO Field Values ===");
		logger.info("=== PCAccount.deleteAccount - dDto.accountNumber: {} ===", dDto.getAccountNumber());
		logger.info("=== PCAccount.deleteAccount - dDto.name: {} ===", dDto.getName());
		logger.info("=== PCAccount.deleteAccount - dDto.accountType: {} ===", dDto.getAccountType());
		logger.info("=== PCAccount.deleteAccount - dDto.status: {} ===", dDto.getStatus());
		logger.info("=== PCAccount.deleteAccount - dDto.netAmount: {} ===", dDto.getNetAmount());
		logger.info("=== PCAccount.deleteAccount - dDto.currency: {} ===", dDto.getCurrency());
		logger.info("=== PCAccount.deleteAccount - dDto.interestRate: {} ===", dDto.getInterestRate());
		logger.info("=== PCAccount.deleteAccount - dDto.identificationNumber: {} ===", dDto.getIdentificationNumber());
		logger.info("=== PCAccount.deleteAccount - dDto.createdDate: {} ===", dDto.getCreatedDate());
		logger.info("=== PCAccount.deleteAccount - dDto.updatedDate: {} ===", dDto.getUpdatedDate());
		
		dcAccount.deleteAccount(dDto);
		
		logger.info("=== PCAccount.deleteAccount - Account deleted successfully ===");
		logger.info("=== PCAccount.deleteAccount END ===");
	}

	/**
	 * Method Name: createAccount
	 * Description: Create new account
	 * 
	 * @param AccountPDTO
	 *                    <ul>
	 *                    <li>accountNumber // Account number
	 *                    <li>name // Name
	 *                    <li>identificationNumber // ID number
	 *                    <li>interestRate // Interest rate
	 *                    <li>lastTransaction // Last transaction date
	 *                    <li>password // Password
	 *                    <li>netAmount // Balance
	 *                    </ul>
	 * @return void
	 */
	public void createAccount(AccountPDTO accountPDTO) throws NewBusinessException {
		logger.info("=== PCAccount.createAccount START ===");
		
		// Null 체크 추가
		if (accountPDTO == null) {
			logger.warn("=== PCAccount.createAccount - accountPDTO is null ===");
			logger.info("=== PCAccount.createAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== PCAccount.createAccount - Input AccountPDTO Field Values ===");
		logger.info("=== PCAccount.createAccount - accountPDTO.accountNumber: {} ===", accountPDTO.getAccountNumber());
		logger.info("=== PCAccount.createAccount - accountPDTO.name: {} ===", accountPDTO.getName());
		logger.info("=== PCAccount.createAccount - accountPDTO.accountType: {} ===", accountPDTO.getAccountType());
		logger.info("=== PCAccount.createAccount - accountPDTO.status: {} ===", accountPDTO.getStatus());
		logger.info("=== PCAccount.createAccount - accountPDTO.netAmount: {} ===", accountPDTO.getNetAmount());
		logger.info("=== PCAccount.createAccount - accountPDTO.currency: {} ===", accountPDTO.getCurrency());
		logger.info("=== PCAccount.createAccount - accountPDTO.interestRate: {} ===", accountPDTO.getInterestRate());
		logger.info("=== PCAccount.createAccount - accountPDTO.identificationNumber: {} ===", accountPDTO.getIdentificationNumber());
		logger.info("=== PCAccount.createAccount - accountPDTO.password: {} ===", accountPDTO.getPassword());
		logger.info("=== PCAccount.createAccount - accountPDTO.createdDate: {} ===", accountPDTO.getCreatedDate());
		logger.info("=== PCAccount.createAccount - accountPDTO.updatedDate: {} ===", accountPDTO.getUpdatedDate());
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.createAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		// AccountPDTO를 AccountDDTO로 직접 매핑
		AccountDDTO accountDDTO = new AccountDDTO();
		accountDDTO.setAccountNumber(accountPDTO.getAccountNumber());
		accountDDTO.setName(accountPDTO.getName());
		accountDDTO.setAccountType(accountPDTO.getAccountType());
		accountDDTO.setStatus(accountPDTO.getStatus());
		accountDDTO.setCurrency(accountPDTO.getCurrency());
		
		// String을 Double로 변환
		try {
			if (accountPDTO.getNetAmount() != null && !accountPDTO.getNetAmount().isEmpty()) {
				accountDDTO.setNetAmount(Double.parseDouble(accountPDTO.getNetAmount()));
			} else {
				accountDDTO.setNetAmount(0.0);
			}
		} catch (NumberFormatException e) {
			accountDDTO.setNetAmount(0.0);
		}
		
		try {
			if (accountPDTO.getInterestRate() != null && !accountPDTO.getInterestRate().isEmpty()) {
				accountDDTO.setInterestRate(Double.parseDouble(accountPDTO.getInterestRate()));
			} else {
				accountDDTO.setInterestRate(0.0);
			}
		} catch (NumberFormatException e) {
			accountDDTO.setInterestRate(0.0);
		}
		
		// 기본값 설정
		accountDDTO.setIdentificationNumber(accountPDTO.getIdentificationNumber() != null ? accountPDTO.getIdentificationNumber() : "");
		accountDDTO.setPassword(accountPDTO.getPassword() != null ? accountPDTO.getPassword() : "");
		accountDDTO.setLastTransaction(new java.util.Date());
		accountDDTO.setCreatedDate(new java.util.Date());
		accountDDTO.setUpdatedDate(new java.util.Date());
		
		// 변환된 DDTO 필드값 출력
		logger.info("=== PCAccount.createAccount - Converted AccountDDTO Field Values ===");
		logger.info("=== PCAccount.createAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
		logger.info("=== PCAccount.createAccount - accountDDTO.name: {} ===", accountDDTO.getName());
		logger.info("=== PCAccount.createAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
		logger.info("=== PCAccount.createAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
		logger.info("=== PCAccount.createAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
		logger.info("=== PCAccount.createAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
		logger.info("=== PCAccount.createAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
		logger.info("=== PCAccount.createAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
		logger.info("=== PCAccount.createAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
		logger.info("=== PCAccount.createAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
		logger.info("=== PCAccount.createAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
		logger.info("=== PCAccount.createAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		
		dcAccount.createAccount(accountDDTO);
		
		logger.info("=== PCAccount.createAccount - Account created successfully ===");
		logger.info("=== PCAccount.createAccount END ===");
	}

	/**
	 * Method Name: getListAccount
	 * Description: Get account list
	 * 
	 * @param AccountPDTO
	 *                    <ul>
	 *                    <li>accountNumber // Account number
	 *                    </ul>
	 * @return List <AccountPDTO>
	 *         <ul>
	 *         <li>accountNumber // Account number
	 *         <li>name // Name
	 *         <li>identificationNumber // ID number
	 *         <li>interestRate // Interest rate
	 *         <li>lastTransaction // Last transaction date
	 *         <li>password // Password
	 *         <li>netAmount // Balance
	 *         </ul>
	 */

	public List<AccountPDTO> getListAccount(AccountPDTO accountPDTO) throws NewBusinessException {
		logger.info("=== PCAccount.getListAccount START ===");
		
		// Null 체크 추가
		if (accountPDTO == null) {
			logger.warn("=== PCAccount.getListAccount - accountPDTO is null ===");
			logger.info("=== PCAccount.getListAccount END ===");
			return new ArrayList<>();
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== PCAccount.getListAccount - Input AccountPDTO Field Values ===");
		logger.info("=== PCAccount.getListAccount - accountPDTO.accountNumber: {} ===", accountPDTO.getAccountNumber());
		logger.info("=== PCAccount.getListAccount - accountPDTO.name: {} ===", accountPDTO.getName());
		logger.info("=== PCAccount.getListAccount - accountPDTO.accountType: {} ===", accountPDTO.getAccountType());
		logger.info("=== PCAccount.getListAccount - accountPDTO.status: {} ===", accountPDTO.getStatus());
		logger.info("=== PCAccount.getListAccount - accountPDTO.netAmount: {} ===", accountPDTO.getNetAmount());
		logger.info("=== PCAccount.getListAccount - accountPDTO.currency: {} ===", accountPDTO.getCurrency());
		logger.info("=== PCAccount.getListAccount - accountPDTO.interestRate: {} ===", accountPDTO.getInterestRate());
		logger.info("=== PCAccount.getListAccount - accountPDTO.identificationNumber: {} ===", accountPDTO.getIdentificationNumber());
		logger.info("=== PCAccount.getListAccount - accountPDTO.createdDate: {} ===", accountPDTO.getCreatedDate());
		logger.info("=== PCAccount.getListAccount - accountPDTO.updatedDate: {} ===", accountPDTO.getUpdatedDate());
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.getListAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		AccountDDTO dDto = NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO);
		
		// dDto null 체크 추가
		if (dDto == null) {
			logger.warn("=== PCAccount.getListAccount - dDto is null after copy, creating new instance ===");
			dDto = new AccountDDTO();
		}
		
		// 수동으로 검색 조건 복사 (NewObjectUtil이 제대로 작동하지 않을 경우)
		if (accountPDTO != null) {
			if (accountPDTO.getAccountNumber() != null && !accountPDTO.getAccountNumber().trim().isEmpty()) {
				dDto.setAccountNumber(accountPDTO.getAccountNumber());
				logger.info("PCAccount", "=== PCAccount.getListAccount - Manually copied accountNumber: " + accountPDTO.getAccountNumber() + " ===");
			}
			if (accountPDTO.getName() != null && !accountPDTO.getName().trim().isEmpty()) {
				dDto.setName(accountPDTO.getName());
				logger.info("PCAccount", "=== PCAccount.getListAccount - Manually copied name: " + accountPDTO.getName() + " ===");
			}
			if (accountPDTO.getAccountType() != null && !accountPDTO.getAccountType().trim().isEmpty()) {
				dDto.setAccountType(accountPDTO.getAccountType());
				logger.info("PCAccount", "=== PCAccount.getListAccount - Manually copied accountType: " + accountPDTO.getAccountType() + " ===");
			}
			if (accountPDTO.getStatus() != null && !accountPDTO.getStatus().trim().isEmpty()) {
				dDto.setStatus(accountPDTO.getStatus());
				logger.info("PCAccount", "=== PCAccount.getListAccount - Manually copied status: " + accountPDTO.getStatus() + " ===");
			}
		}
		
		// 변환된 DDTO 필드값 출력
		logger.info("=== PCAccount.getListAccount - Converted AccountDDTO Field Values ===");
		logger.info("=== PCAccount.getListAccount - dDto.accountNumber: {} ===", dDto.getAccountNumber());
		logger.info("=== PCAccount.getListAccount - dDto.name: {} ===", dDto.getName());
		logger.info("=== PCAccount.getListAccount - dDto.accountType: {} ===", dDto.getAccountType());
		logger.info("=== PCAccount.getListAccount - dDto.status: {} ===", dDto.getStatus());
		logger.info("=== PCAccount.getListAccount - dDto.netAmount: {} ===", dDto.getNetAmount());
		logger.info("=== PCAccount.getListAccount - dDto.currency: {} ===", dDto.getCurrency());
		logger.info("=== PCAccount.getListAccount - dDto.interestRate: {} ===", dDto.getInterestRate());
		logger.info("=== PCAccount.getListAccount - dDto.identificationNumber: {} ===", dDto.getIdentificationNumber());
		logger.info("=== PCAccount.getListAccount - dDto.createdDate: {} ===", dDto.getCreatedDate());
		logger.info("=== PCAccount.getListAccount - dDto.updatedDate: {} ===", dDto.getUpdatedDate());
		
		List<AccountDDTO> dDtoList = dcAccount.getListAccount(dDto);
		
		// 출력 결과 로깅
		logger.info("=== PCAccount.getListAccount - Output AccountDDTO List Size: {} ===", dDtoList != null ? dDtoList.size() : 0);
		if (dDtoList != null && !dDtoList.isEmpty()) {
			for (int i = 0; i < dDtoList.size(); i++) {
				AccountDDTO item = dDtoList.get(i);
				logger.info("=== PCAccount.getListAccount - Item[{}]: accountNumber={}, name={}, accountType={}, status={} ===", 
					i, item.getAccountNumber(), item.getName(), item.getAccountType(), item.getStatus());
			}
		}
		
				// NewObjectUtil.copyForList 대신 직접 변환 사용
		logger.info("=== PCAccount.getListAccount - Converting AccountDDTO list to AccountPDTO list ===");
		List<AccountPDTO> result = new java.util.ArrayList<>();
		for (AccountDDTO dto : dDtoList) {
			AccountPDTO pDto = new AccountPDTO();
			pDto.setAccountNumber(dto.getAccountNumber());
			pDto.setName(dto.getName());
			pDto.setAccountType(dto.getAccountType());
			pDto.setStatus(dto.getStatus());
			pDto.setNetAmount(dto.getNetAmount() != null ? dto.getNetAmount().toString() : "0");
			pDto.setCurrency(dto.getCurrency());
			pDto.setInterestRate(dto.getInterestRate() != null ? dto.getInterestRate().toString() : "0");
			pDto.setIdentificationNumber(dto.getIdentificationNumber());
			pDto.setPassword(dto.getPassword());
			pDto.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate().toString() : "");
			pDto.setUpdatedDate(dto.getUpdatedDate() != null ? dto.getUpdatedDate().toString() : "");
			result.add(pDto);
		}
		logger.info("=== PCAccount.getListAccount - Direct conversion completed, result size: {} ===", result.size());
		
		// 최종 출력 결과 로깅
		logger.info("=== PCAccount.getListAccount - Final Output AccountPDTO List Size: {} ===", result != null ? result.size() : 0);
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				AccountPDTO item = result.get(i);
				logger.info("=== PCAccount.getListAccount - Final Item[{}]: accountNumber={}, name={}, accountType={}, status={} ===", 
					i, item.getAccountNumber(), item.getName(), item.getAccountType(), item.getStatus());
			}
		}
		
		logger.info("=== PCAccount.getListAccount END ===");
		return result;
	}
}
