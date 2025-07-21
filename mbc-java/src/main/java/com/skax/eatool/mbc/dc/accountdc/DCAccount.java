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
	NewIKesaLogger logger = NewKesaLogHelper.getBiz();

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
		try {
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				return NewObjectUtil.copyForClass(AccountDDTO.class, account);
			}
			return null;
		} catch (Exception e) {
			throw new NewBusinessException("B0100001", "processCode", e);
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
		try {
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			if (accountOpt.isPresent()) {
				Account account = accountOpt.get();
				// Update account fields
				account.setName(accountDDTO.getName());
				account.setIdentificationNumber(accountDDTO.getIdentificationNumber());
				account.setInterestRate(accountDDTO.getInterestRate());
				account.setLastTransaction(accountDDTO.getLastTransaction());
				account.setPassword(accountDDTO.getPassword());
				account.setNetAmount(accountDDTO.getNetAmount());

				accountMapper.save(account);
				if (logger.isDebugEnabled())
					logger.debug(this.getClass().getName() + ", update completed");
			}
		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
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
		try {
			Optional<Account> accountOpt = accountMapper.findByAccountNumber(accountDDTO.getAccountNumber());
			if (accountOpt.isPresent()) {
				accountMapper.delete(accountOpt.get());
				if (logger.isDebugEnabled())
					logger.debug(this.getClass().getName() + ", delete completed");
			}
		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
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
		try {
			Account account = NewObjectUtil.copyForClass(Account.class, accountDDTO);
			accountMapper.save(account);
		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
		}
	}

	public List<AccountDDTO> getListAccount(AccountDDTO accountDDTO) throws NewBusinessException {
		try {
			List<Account> accountList = accountMapper.findAll();
			return NewObjectUtil.copyForList(AccountDDTO.class, accountList);
		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
		}
	}

}
