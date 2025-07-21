/*
 * (@)# PCAccount.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.pc.accountpc;

import java.util.List;
import java.util.ArrayList;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIProcessComponent;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.accountdc.DCAccount;
import com.skax.eatool.mbc.dc.accountdc.dto.AccountDDTO;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PC Component for Account Management
 * 
 * Program Name: PCAccount.java
 * Description: Process component for handling account information
 * 
 * Change History:
 * <ul>
 * <li>2008-08-26::Initial::First Creation
 * </ul>
 */
public class PCAccount implements NewIProcessComponent {

	private static final Logger logger = LoggerFactory.getLogger(PCAccount.class);

	/**
	 * Method Name: getAccount
	 * Description: Retrieve account information
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
		
		AccountDDTO accountDDTO = new DCAccount()
				.getAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
		logger.info("=== PCAccount.getAccount END ===");
		return NewObjectUtil.copyForClass(AccountPDTO.class, accountDDTO);
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
		
		new DCAccount().updateAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
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
		
		new DCAccount().deleteAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
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
		
		// TODO: Implement account creation logic
		logger.info("Creating account with ID: " + (accountPDTO.getAccountId() != null ? accountPDTO.getAccountId() : "null"));
		
		logger.info("=== PCAccount.createAccount END ===");
		new DCAccount().createAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
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
		
		List<AccountDDTO> dDtoList = new DCAccount()
				.getListAccount(NewObjectUtil.copyForClass(AccountDDTO.class, accountPDTO));
		logger.info("=== PCAccount.getListAccount END ===");
		return NewObjectUtil.copyForList(AccountPDTO.class, dDtoList);
	}
}
