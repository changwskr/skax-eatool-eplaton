/*
 * (@)# DCAccount.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.dc.accountdc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIDomainComponent;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.accountdc.dto.AccountDDTO;
import com.skax.eatool.mbc.dc.accountdc.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 * [프로그램명] DC계정
 * <br>
 * [설명] 계정에 대한 데이터를 처리하는 도메인 컴포넌트
 * <br>
 * [상세설명]
 * <br>
 * [변경이력]
 * <ul>
 * <li>2008-08-26::전체::최초작성
 * <li>2024-01-01::전체::JPA 마이그레이션
 * </ul>
 */
@Repository
public class DCAccount implements NewIDomainComponent {
	private static final Logger logger = LoggerFactory.getLogger(DCAccount.class);

	@Autowired
	private AccountMapper accountMapper;

	/**
	 * <br>
	 * [메서드명] 계정조회
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param AccountDDTO
	 *                    <ul>
	 *                    <li>accountNumber //계좌번호
	 *                    </ul>
	 * @return AccountDDTO
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
	public AccountDDTO getAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		logger.info("=== DCAccount.getAccount START ===");
		
		// Null 체크 추가
		if (accountDDTO == null) {
			logger.warn("=== DCAccount.getAccount - accountDDTO is null ===");
			logger.info("=== DCAccount.getAccount END ===");
			return null;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== DCAccount.getAccount - Input AccountDDTO Field Values ===");
		logger.info("=== DCAccount.getAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
		logger.info("=== DCAccount.getAccount - accountDDTO.name: {} ===", accountDDTO.getName());
		logger.info("=== DCAccount.getAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
		logger.info("=== DCAccount.getAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
		logger.info("=== DCAccount.getAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
		logger.info("=== DCAccount.getAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
		logger.info("=== DCAccount.getAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
		logger.info("=== DCAccount.getAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
		logger.info("=== DCAccount.getAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
		logger.info("=== DCAccount.getAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
		logger.info("=== DCAccount.getAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
		logger.info("=== DCAccount.getAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		
		try {
			logger.info("=== DCAccount.getAccount - Calling AccountMapper.findByAccountNumber: {} ===", accountDDTO.getAccountNumber());
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				
				// Mapper 결과 Account 엔티티 필드값 출력
				logger.info("=== DCAccount.getAccount - Mapper Result Account Entity Field Values ===");
				logger.info("=== DCAccount.getAccount - account.accountNumber: {} ===", account.getAccountNumber());
				logger.info("=== DCAccount.getAccount - account.name: {} ===", account.getName());
				logger.info("=== DCAccount.getAccount - account.accountType: {} ===", account.getAccountType());
				logger.info("=== DCAccount.getAccount - account.status: {} ===", account.getStatus());
				logger.info("=== DCAccount.getAccount - account.netAmount: {} ===", account.getNetAmount());
				logger.info("=== DCAccount.getAccount - account.currency: {} ===", account.getCurrency());
				logger.info("=== DCAccount.getAccount - account.interestRate: {} ===", account.getInterestRate());
				logger.info("=== DCAccount.getAccount - account.identificationNumber: {} ===", account.getIdentificationNumber());
				logger.info("=== DCAccount.getAccount - account.password: {} ===", account.getPassword());
				logger.info("=== DCAccount.getAccount - account.lastTransaction: {} ===", account.getLastTransaction());
				logger.info("=== DCAccount.getAccount - account.createdDate: {} ===", account.getCreatedDate());
				logger.info("=== DCAccount.getAccount - account.updatedDate: {} ===", account.getUpdatedDate());
				
				AccountDDTO result = NewObjectUtil.copyForClass(AccountDDTO.class, account);
				
				// 변환된 DDTO 필드값 출력
				logger.info("=== DCAccount.getAccount - Converted AccountDDTO Field Values ===");
				logger.info("=== DCAccount.getAccount - result.accountNumber: {} ===", result.getAccountNumber());
				logger.info("=== DCAccount.getAccount - result.name: {} ===", result.getName());
				logger.info("=== DCAccount.getAccount - result.accountType: {} ===", result.getAccountType());
				logger.info("=== DCAccount.getAccount - result.status: {} ===", result.getStatus());
				logger.info("=== DCAccount.getAccount - result.netAmount: {} ===", result.getNetAmount());
				logger.info("=== DCAccount.getAccount - result.currency: {} ===", result.getCurrency());
				logger.info("=== DCAccount.getAccount - result.interestRate: {} ===", result.getInterestRate());
				logger.info("=== DCAccount.getAccount - result.identificationNumber: {} ===", result.getIdentificationNumber());
				logger.info("=== DCAccount.getAccount - result.password: {} ===", result.getPassword());
				logger.info("=== DCAccount.getAccount - result.lastTransaction: {} ===", result.getLastTransaction());
				logger.info("=== DCAccount.getAccount - result.createdDate: {} ===", result.getCreatedDate());
				logger.info("=== DCAccount.getAccount - result.updatedDate: {} ===", result.getUpdatedDate());
				
				logger.info("=== DCAccount.getAccount - Account found and converted successfully ===");
				return result;
			} else {
				logger.warn("=== DCAccount.getAccount - Account not found for accountNumber: {} ===", accountDDTO.getAccountNumber());
				return null;
			}
		} catch (Exception e) {
			logger.error("=== DCAccount.getAccount - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0100001", "processCode", e);
		} finally {
			logger.info("=== DCAccount.getAccount END ===");
		}
	}

	/**
	 * <br>
	 * [메서드명] 계정수정
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param AccountDDTO
	 *                    <ul>
	 *                    <li>accountNumber //계좌번호
	 *                    <li>name //이름
	 *                    <li>identificationNumber //주민번호
	 *                    <li>interestRate //이자율
	 *                    <li>lastTransaction //마지막거래일
	 *                    <li>password //비밀번호
	 *                    <li>netAmount //잔액
	 *                    </ul>
	 * @return void
	 */
	public void updateAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		logger.info("=== DCAccount.updateAccount START ===");
		
		// Null 체크 추가
		if (accountDDTO == null) {
			logger.warn("=== DCAccount.updateAccount - accountDDTO is null ===");
			logger.info("=== DCAccount.updateAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== DCAccount.updateAccount - Input AccountDDTO Field Values ===");
		logger.info("=== DCAccount.updateAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
		logger.info("=== DCAccount.updateAccount - accountDDTO.name: {} ===", accountDDTO.getName());
		logger.info("=== DCAccount.updateAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
		logger.info("=== DCAccount.updateAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
		logger.info("=== DCAccount.updateAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
		logger.info("=== DCAccount.updateAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
		logger.info("=== DCAccount.updateAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
		logger.info("=== DCAccount.updateAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
		logger.info("=== DCAccount.updateAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
		logger.info("=== DCAccount.updateAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
		logger.info("=== DCAccount.updateAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
		logger.info("=== DCAccount.updateAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		
		try {
			logger.info("=== DCAccount.updateAccount - Calling AccountMapper.findByAccountNumber: {} ===", accountDDTO.getAccountNumber());
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				
				// 기존 Account 엔티티 필드값 출력 (업데이트 전)
				logger.info("=== DCAccount.updateAccount - Before Update Account Entity Field Values ===");
				logger.info("=== DCAccount.updateAccount - account.accountNumber: {} ===", account.getAccountNumber());
				logger.info("=== DCAccount.updateAccount - account.name: {} ===", account.getName());
				logger.info("=== DCAccount.updateAccount - account.accountType: {} ===", account.getAccountType());
				logger.info("=== DCAccount.updateAccount - account.status: {} ===", account.getStatus());
				logger.info("=== DCAccount.updateAccount - account.netAmount: {} ===", account.getNetAmount());
				logger.info("=== DCAccount.updateAccount - account.currency: {} ===", account.getCurrency());
				logger.info("=== DCAccount.updateAccount - account.interestRate: {} ===", account.getInterestRate());
				logger.info("=== DCAccount.updateAccount - account.identificationNumber: {} ===", account.getIdentificationNumber());
				logger.info("=== DCAccount.updateAccount - account.password: {} ===", account.getPassword());
				logger.info("=== DCAccount.updateAccount - account.lastTransaction: {} ===", account.getLastTransaction());
				logger.info("=== DCAccount.updateAccount - account.createdDate: {} ===", account.getCreatedDate());
				logger.info("=== DCAccount.updateAccount - account.updatedDate: {} ===", account.getUpdatedDate());
				
				// Update account fields
				account.setName(accountDDTO.getName());
				account.setIdentificationNumber(accountDDTO.getIdentificationNumber());
				account.setInterestRate(accountDDTO.getInterestRate());
				account.setLastTransaction(accountDDTO.getLastTransaction());
				account.setPassword(accountDDTO.getPassword());
				account.setNetAmount(accountDDTO.getNetAmount());
				
				// 누락된 컬럼들 추가
				account.setAccountType(accountDDTO.getAccountType());
				account.setStatus(accountDDTO.getStatus());
				account.setCurrency(accountDDTO.getCurrency());
				account.setUpdatedDate(new java.util.Date()); // 수정일 업데이트

				// 업데이트된 Account 엔티티 필드값 출력 (업데이트 후)
				logger.info("=== DCAccount.updateAccount - After Update Account Entity Field Values ===");
				logger.info("=== DCAccount.updateAccount - account.accountNumber: {} ===", account.getAccountNumber());
				logger.info("=== DCAccount.updateAccount - account.name: {} ===", account.getName());
				logger.info("=== DCAccount.updateAccount - account.accountType: {} ===", account.getAccountType());
				logger.info("=== DCAccount.updateAccount - account.status: {} ===", account.getStatus());
				logger.info("=== DCAccount.updateAccount - account.netAmount: {} ===", account.getNetAmount());
				logger.info("=== DCAccount.updateAccount - account.currency: {} ===", account.getCurrency());
				logger.info("=== DCAccount.updateAccount - account.interestRate: {} ===", account.getInterestRate());
				logger.info("=== DCAccount.updateAccount - account.identificationNumber: {} ===", account.getIdentificationNumber());
				logger.info("=== DCAccount.updateAccount - account.password: {} ===", account.getPassword());
				logger.info("=== DCAccount.updateAccount - account.lastTransaction: {} ===", account.getLastTransaction());
				logger.info("=== DCAccount.updateAccount - account.createdDate: {} ===", account.getCreatedDate());
				logger.info("=== DCAccount.updateAccount - account.updatedDate: {} ===", account.getUpdatedDate());

				logger.info("=== DCAccount.updateAccount - Calling AccountMapper.save ===");
				accountMapper.save(account);
				logger.info("=== DCAccount.updateAccount - Account updated successfully ===");
				
				if (logger.isDebugEnabled())
					logger.debug(this.getClass().getName() + ", update completed");
			} else {
				logger.warn("=== DCAccount.updateAccount - Account not found for accountNumber: {} ===", accountDDTO.getAccountNumber());
			}
		} catch (Exception e) {
			logger.error("=== DCAccount.updateAccount - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.updateAccount END ===");
		}
	}

	/**
	 * <br>
	 * [메서드명] 계정삭제
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param AccountDDTO
	 *                    <ul>
	 *                    <li>accountNumber //계좌번호
	 *                    </ul>
	 * @return void
	 */
	public void deleteAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		logger.info("=== DCAccount.deleteAccount START ===");
		
		// Null 체크 추가
		if (accountDDTO == null) {
			logger.warn("=== DCAccount.deleteAccount - accountDDTO is null ===");
			logger.info("=== DCAccount.deleteAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== DCAccount.deleteAccount - Input AccountDDTO Field Values ===");
		logger.info("=== DCAccount.deleteAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.name: {} ===", accountDDTO.getName());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
		logger.info("=== DCAccount.deleteAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		
		try {
			logger.info("=== DCAccount.deleteAccount - Calling AccountMapper.findByAccountNumber: {} ===", accountDDTO.getAccountNumber());
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				
				// 삭제할 Account 엔티티 필드값 출력
				logger.info("=== DCAccount.deleteAccount - Account to Delete Field Values ===");
				logger.info("=== DCAccount.deleteAccount - account.accountNumber: {} ===", account.getAccountNumber());
				logger.info("=== DCAccount.deleteAccount - account.name: {} ===", account.getName());
				logger.info("=== DCAccount.deleteAccount - account.accountType: {} ===", account.getAccountType());
				logger.info("=== DCAccount.deleteAccount - account.status: {} ===", account.getStatus());
				logger.info("=== DCAccount.deleteAccount - account.netAmount: {} ===", account.getNetAmount());
				logger.info("=== DCAccount.deleteAccount - account.currency: {} ===", account.getCurrency());
				logger.info("=== DCAccount.deleteAccount - account.interestRate: {} ===", account.getInterestRate());
				logger.info("=== DCAccount.deleteAccount - account.identificationNumber: {} ===", account.getIdentificationNumber());
				logger.info("=== DCAccount.deleteAccount - account.password: {} ===", account.getPassword());
				logger.info("=== DCAccount.deleteAccount - account.lastTransaction: {} ===", account.getLastTransaction());
				logger.info("=== DCAccount.deleteAccount - account.createdDate: {} ===", account.getCreatedDate());
				logger.info("=== DCAccount.deleteAccount - account.updatedDate: {} ===", account.getUpdatedDate());
				
				logger.info("=== DCAccount.deleteAccount - Calling AccountMapper.delete ===");
				accountMapper.delete(account);
				logger.info("=== DCAccount.deleteAccount - Account deleted successfully ===");
				
				if (logger.isDebugEnabled())
					logger.debug(this.getClass().getName() + ", delete completed");
			} else {
				logger.warn("=== DCAccount.deleteAccount - Account not found for accountNumber: {} ===", accountDDTO.getAccountNumber());
			}
		} catch (Exception e) {
			logger.error("=== DCAccount.deleteAccount - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.deleteAccount END ===");
		}
	}

	/**
	 * <br>
	 * [메서드명] 계정생성
	 * <br>
	 * [설명]
	 * <br>
	 * [상세설명]
	 * 
	 * @param AccountDDTO
	 *                    <ul>
	 *                    <li>accountNumber //계좌번호
	 *                    <li>name //이름
	 *                    <li>identificationNumber //주민번호
	 *                    <li>interestRate //이자율
	 *                    <li>lastTransaction //마지막거래일
	 *                    <li>password //비밀번호
	 *                    <li>netAmount //잔액
	 *                    </ul>
	 * @return void
	 */
	public void createAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		logger.info("=== DCAccount.createAccount START ===");
		
		// Null 체크 추가
		if (accountDDTO == null) {
			logger.warn("=== DCAccount.createAccount - accountDDTO is null ===");
			logger.info("=== DCAccount.createAccount END ===");
			return;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== DCAccount.createAccount - Input AccountDDTO Field Values ===");
		logger.info("=== DCAccount.createAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
		logger.info("=== DCAccount.createAccount - accountDDTO.name: {} ===", accountDDTO.getName());
		logger.info("=== DCAccount.createAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
		logger.info("=== DCAccount.createAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
		logger.info("=== DCAccount.createAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
		logger.info("=== DCAccount.createAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
		logger.info("=== DCAccount.createAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
		logger.info("=== DCAccount.createAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
		logger.info("=== DCAccount.createAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
		logger.info("=== DCAccount.createAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
		logger.info("=== DCAccount.createAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
		logger.info("=== DCAccount.createAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		
		try {
			// AccountDDTO를 Account로 직접 매핑
			Account account = new Account();
			account.setAccountNumber(accountDDTO.getAccountNumber());
			account.setName(accountDDTO.getName());
			account.setIdentificationNumber(accountDDTO.getIdentificationNumber());
			account.setInterestRate(accountDDTO.getInterestRate());
			account.setLastTransaction(accountDDTO.getLastTransaction());
			account.setPassword(accountDDTO.getPassword());
			account.setNetAmount(accountDDTO.getNetAmount());
			
			// 누락된 컬럼들 추가
			account.setAccountType(accountDDTO.getAccountType());
			account.setStatus(accountDDTO.getStatus());
			account.setCurrency(accountDDTO.getCurrency());
			account.setCreatedDate(accountDDTO.getCreatedDate());
			account.setUpdatedDate(accountDDTO.getUpdatedDate());
			
			// 생성된 Account 엔티티 필드값 출력
			logger.info("=== DCAccount.createAccount - Created Account Entity Field Values ===");
			logger.info("=== DCAccount.createAccount - account.accountNumber: {} ===", account.getAccountNumber());
			logger.info("=== DCAccount.createAccount - account.name: {} ===", account.getName());
			logger.info("=== DCAccount.createAccount - account.accountType: {} ===", account.getAccountType());
			logger.info("=== DCAccount.createAccount - account.status: {} ===", account.getStatus());
			logger.info("=== DCAccount.createAccount - account.netAmount: {} ===", account.getNetAmount());
			logger.info("=== DCAccount.createAccount - account.currency: {} ===", account.getCurrency());
			logger.info("=== DCAccount.createAccount - account.interestRate: {} ===", account.getInterestRate());
			logger.info("=== DCAccount.createAccount - account.identificationNumber: {} ===", account.getIdentificationNumber());
			logger.info("=== DCAccount.createAccount - account.password: {} ===", account.getPassword());
			logger.info("=== DCAccount.createAccount - account.lastTransaction: {} ===", account.getLastTransaction());
			logger.info("=== DCAccount.createAccount - account.createdDate: {} ===", account.getCreatedDate());
			logger.info("=== DCAccount.createAccount - account.updatedDate: {} ===", account.getUpdatedDate());
			
			logger.info("=== DCAccount.createAccount - Calling AccountMapper.save ===");
			accountMapper.save(account);
			logger.info("=== DCAccount.createAccount - Account created successfully ===");
		} catch (Exception e) {
			logger.error("=== DCAccount.createAccount - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.createAccount END ===");
		}
	}

	/**
	 * 계정 목록 조회 (검색 및 페이징 지원)
	 * 
	 * @param accountDDTO 검색 조건
	 * @return 계정 목록
	 */
	public List<AccountDDTO> getListAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		logger.info("=== DCAccount.getListAccount START ===");
		
		// 입력 객체 필드값 출력 (null일 수 있음)
		if (accountDDTO != null) {
			logger.info("=== DCAccount.getListAccount - Input AccountDDTO Field Values ===");
			logger.info("=== DCAccount.getListAccount - accountDDTO.accountNumber: {} ===", accountDDTO.getAccountNumber());
			logger.info("=== DCAccount.getListAccount - accountDDTO.name: {} ===", accountDDTO.getName());
			logger.info("=== DCAccount.getListAccount - accountDDTO.accountType: {} ===", accountDDTO.getAccountType());
			logger.info("=== DCAccount.getListAccount - accountDDTO.status: {} ===", accountDDTO.getStatus());
			logger.info("=== DCAccount.getListAccount - accountDDTO.netAmount: {} ===", accountDDTO.getNetAmount());
			logger.info("=== DCAccount.getListAccount - accountDDTO.currency: {} ===", accountDDTO.getCurrency());
			logger.info("=== DCAccount.getListAccount - accountDDTO.interestRate: {} ===", accountDDTO.getInterestRate());
			logger.info("=== DCAccount.getListAccount - accountDDTO.identificationNumber: {} ===", accountDDTO.getIdentificationNumber());
			logger.info("=== DCAccount.getListAccount - accountDDTO.password: {} ===", accountDDTO.getPassword());
			logger.info("=== DCAccount.getListAccount - accountDDTO.lastTransaction: {} ===", accountDDTO.getLastTransaction());
			logger.info("=== DCAccount.getListAccount - accountDDTO.createdDate: {} ===", accountDDTO.getCreatedDate());
			logger.info("=== DCAccount.getListAccount - accountDDTO.updatedDate: {} ===", accountDDTO.getUpdatedDate());
		} else {
			logger.info("=== DCAccount.getListAccount - Input AccountDDTO is null (no search criteria) ===");
		}
		
		try {
			logger.info("=== DCAccount.getListAccount - Calling AccountMapper.findAll ===");
			List<Account> accountList = accountMapper.findAll();
			logger.info("=== DCAccount.getListAccount - Retrieved {} accounts from database ===", accountList.size());
			
			// 데이터가 없으면 테스트 데이터 삽입
			if (accountList.isEmpty()) {
				logger.info("=== DCAccount.getListAccount - No data found, inserting test data ===");
				accountMapper.insertTestData();
				accountList = accountMapper.findAll();
				logger.info("=== DCAccount.getListAccount - Retrieved {} accounts after test data insertion ===", accountList.size());
			}
			
			// 검색 조건이 있으면 필터링
			if (accountDDTO != null) {
				logger.info("=== DCAccount.getListAccount - Applying search criteria filter ===");
				accountList = filterAccounts(accountList, accountDDTO);
				logger.info("=== DCAccount.getListAccount - After filtering: {} accounts ===", accountList.size());
			}
			
			// 결과 Account 엔티티들의 필드값 출력 (최대 5개까지만)
			logger.info("=== DCAccount.getListAccount - Result Account Entity Field Values (showing up to 5) ===");
			int count = 0;
			for (Account account : accountList) {
				if (count >= 5) {
					logger.info("=== DCAccount.getListAccount - ... and {} more accounts ===", accountList.size() - 5);
					break;
				}
				logger.info("=== DCAccount.getListAccount - Account[{}]: accountNumber={}, name={}, accountType={}, status={}, netAmount={} ===", 
				           count, account.getAccountNumber(), account.getName(), account.getAccountType(), account.getStatus(), account.getNetAmount());
				count++;
			}
			
			logger.info("=== DCAccount.getListAccount - Converting to AccountDDTO list ===");
			List<AccountDDTO> result = NewObjectUtil.copyForList(AccountDDTO.class, accountList);
			
			// 결과 DDTO들의 필드값 출력 (최대 5개까지만)
			logger.info("=== DCAccount.getListAccount - Result AccountDDTO Field Values (showing up to 5) ===");
			count = 0;
			for (AccountDDTO dto : result) {
				if (count >= 5) {
					logger.info("=== DCAccount.getListAccount - ... and {} more DTOs ===", result.size() - 5);
					break;
				}
				logger.info("=== DCAccount.getListAccount - AccountDDTO[{}]: accountNumber={}, name={}, accountType={}, status={}, netAmount={} ===", 
				           count, dto.getAccountNumber(), dto.getName(), dto.getAccountType(), dto.getStatus(), dto.getNetAmount());
				count++;
			}
			
			logger.info("=== DCAccount.getListAccount - Final result: {} accounts ===", result.size());
			return result;
		} catch (Exception e) {
			logger.error("=== DCAccount.getListAccount - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.getListAccount END ===");
		}
	}

	/**
	 * 계정 검색 (타입별)
	 * 
	 * @param accountType 계정 타입
	 * @return 계정 목록
	 */
	public List<AccountDDTO> getAccountsByType(String accountType) throws NewBusinessException {
		logger.info("=== DCAccount.getAccountsByType START ===");
		try {
			List<Account> accountList = accountMapper.findByAccountType(accountType);
			return NewObjectUtil.copyForList(AccountDDTO.class, accountList);
		} catch (Exception e) {
			logger.error("=== DCAccount.getAccountsByType - Error: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.getAccountsByType END ===");
		}
	}

	/**
	 * 계정 검색 (상태별)
	 * 
	 * @param status 계정 상태
	 * @return 계정 목록
	 */
	public List<AccountDDTO> getAccountsByStatus(String status) throws NewBusinessException {
		logger.info("=== DCAccount.getAccountsByStatus START ===");
		try {
			List<Account> accountList = accountMapper.findByStatus(status);
			return NewObjectUtil.copyForList(AccountDDTO.class, accountList);
		} catch (Exception e) {
			logger.error("=== DCAccount.getAccountsByStatus - Error: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.getAccountsByStatus END ===");
		}
	}

	/**
	 * 계정 검색 (계좌번호 부분 검색)
	 * 
	 * @param accountNumber 계좌번호 (부분)
	 * @return 계정 목록
	 */
	public List<AccountDDTO> searchAccountsByNumber(String accountNumber) throws NewBusinessException {
		logger.info("=== DCAccount.searchAccountsByNumber START ===");
		try {
			List<Account> accountList = accountMapper.findByAccountNumberContaining(accountNumber);
			return NewObjectUtil.copyForList(AccountDDTO.class, accountList);
		} catch (Exception e) {
			logger.error("=== DCAccount.searchAccountsByNumber - Error: {} ===", e.getMessage(), e);
			throw new NewBusinessException("B0000002", "processCode", e);
		} finally {
			logger.info("=== DCAccount.searchAccountsByNumber END ===");
		}
	}

	/**
	 * 계정 필터링 (메모리 내 필터링)
	 * 
	 * @param accounts 원본 계정 목록
	 * @param searchCriteria 검색 조건
	 * @return 필터링된 계정 목록
	 */
	private List<Account> filterAccounts(List<Account> accounts, AccountDDTO searchCriteria) {
		return accounts.stream()
			.filter(account -> {
				// 계정 타입 필터
				if (searchCriteria.getAccountType() != null && !searchCriteria.getAccountType().isEmpty()) {
					if (!searchCriteria.getAccountType().equals(account.getAccountType())) {
						return false;
					}
				}
				
				// 계정 상태 필터
				if (searchCriteria.getStatus() != null && !searchCriteria.getStatus().isEmpty()) {
					if (!searchCriteria.getStatus().equals(account.getStatus())) {
						return false;
					}
				}
				
				// 통화 필터
				if (searchCriteria.getCurrency() != null && !searchCriteria.getCurrency().isEmpty()) {
					if (!searchCriteria.getCurrency().equals(account.getCurrency())) {
						return false;
					}
				}
				
				return true;
			})
			.collect(java.util.stream.Collectors.toList());
	}
}
