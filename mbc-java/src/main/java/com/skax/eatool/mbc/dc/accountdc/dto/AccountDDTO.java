/*
 * (@)# AccountDDTO.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.dc.accountdc.dto;

import java.util.Date;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * <br>
 * [Program Name] Account DDTO
 * <br>
 * [Description]
 * <br>
 * [Detailed Description]
 * <br>
 * [Change History]
 * <ul>
 * <li>2008-08-26::KB Bank::Initial Creation
 * </ul>
 */
public class AccountDDTO extends NewAbstractDTO {

	private static final long serialVersionUID = 1L;

	private String accountNumber; // Account Number

	private String name; // Name

	private String identificationNumber; // Identification Number

	private Double interestRate; // Interest Rate (DECIMAL 타입에 맞춰 Double로 변경)

	private Date lastTransaction; // Last Transaction Date

	private String password; // Password

	private Double netAmount; // Amount

	// 누락된 컬럼들 추가
	private String accountType; // 계정 타입

	private String status; // 계정 상태

	private String currency; // 통화

	private Date createdDate; // 생성일

	private Date updatedDate; // 수정일

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 추가된 컬럼들의 getter/setter
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}