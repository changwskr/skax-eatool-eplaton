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
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.getAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		AccountDDTO dDto = dcAccount.getAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
		logger.info("=== PCAccount.getAccount END ===");
		return NewObjectUtil.copyForClass(AccountPDTO.class, dDto);
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
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.updateAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		dcAccount.updateAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
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
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.deleteAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		dcAccount.deleteAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
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
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.createAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		// TODO: Implement account creation logic
		logger.info("Creating account with ID: " + (accountPDTO.getAccountId() != null ? accountPDTO.getAccountId() : "null"));
		
		// AccountPDTO를 AccountDDTO로 직접 매핑
		AccountDDTO accountDDTO = new AccountDDTO();
		accountDDTO.setAccountNumber(accountPDTO.getAccountId());
		accountDDTO.setName(accountPDTO.getAccountName());
		accountDDTO.setIdentificationNumber(""); // 기본값 설정
		accountDDTO.setInterestRate(0.0f); // 기본값 설정
		accountDDTO.setPassword(""); // 기본값 설정
		accountDDTO.setNetAmount(0.0); // 기본값 설정
		
		logger.info("=== PCAccount.createAccount END ===");
		dcAccount.createAccount(accountDDTO);
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
		
		// dcAccount null 체크 추가
		if (dcAccount == null) {
			logger.error("=== PCAccount.getListAccount - dcAccount is null ===");
			throw new NewBusinessException("B0000001", "DCAccount is not injected");
		}
		
		List<AccountDDTO> dDtoList = dcAccount.getListAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
		logger.info("=== PCAccount.getListAccount END ===");
		return NewObjectUtil.copyForList(AccountPDTO.class, dDtoList);
	}
}
