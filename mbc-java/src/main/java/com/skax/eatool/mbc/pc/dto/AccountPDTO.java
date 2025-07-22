/*
 * (@)# AccountPDTO.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.pc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;
import java.util.Date;

/**
 * AccountPDTO class - Entity와 일치하는 필드명으로 수정
 */
public class AccountPDTO extends NewAbstractDTO {

	private String accountNumber; // 계좌번호 (Entity와 일치)
	private String name; // 계정명 (Entity와 일치)
	private String accountType; // 계정 타입
	private String status; // 계정 상태 (Entity와 일치)
	private String currency; // 통화 (Entity와 일치)
	private String netAmount; // 잔액 (Entity와 일치, String으로 유지)
	private String interestRate; // 이자율 (Entity와 일치, String으로 유지)
	private String identificationNumber; // 주민번호 (Entity와 일치)
	private String password; // 비밀번호 (Entity와 일치)
	private String lastTransaction; // 마지막거래일 (Entity와 일치, String으로 유지)
	private String createdDate; // 생성일 (Entity와 일치, String으로 유지)
	private String updatedDate; // 수정일 (Entity와 일치, String으로 유지)

	public AccountPDTO() {
		// Default constructor
	}

	// Getters and setters
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(String lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	// 레거시 호환성을 위한 메서드들
	@Deprecated
	public String getAccountId() {
		return accountNumber;
	}

	@Deprecated
	public void setAccountId(String accountId) {
		this.accountNumber = accountId;
	}

	@Deprecated
	public String getAccountName() {
		return name;
	}

	@Deprecated
	public void setAccountName(String accountName) {
		this.name = accountName;
	}

	@Deprecated
	public String getAccountStatus() {
		return status;
	}

	@Deprecated
	public void setAccountStatus(String accountStatus) {
		this.status = accountStatus;
	}

	@Deprecated
	public String getAccountCurrency() {
		return currency;
	}

	@Deprecated
	public void setAccountCurrency(String accountCurrency) {
		this.currency = accountCurrency;
	}

	@Deprecated
	public String getAccountBalance() {
		return netAmount;
	}

	@Deprecated
	public void setAccountBalance(String accountBalance) {
		this.netAmount = accountBalance;
	}

	@Deprecated
	public String getAccountInterestRate() {
		return interestRate;
	}

	@Deprecated
	public void setAccountInterestRate(String accountInterestRate) {
		this.interestRate = accountInterestRate;
	}
}