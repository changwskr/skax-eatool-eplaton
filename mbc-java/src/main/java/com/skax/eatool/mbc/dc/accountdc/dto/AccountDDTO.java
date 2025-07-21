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

	private Float interestRate; // Interest Rate

	private Date lastTransaction; // Last Transaction Date

	private String password; // Password

	private String netAmount; // Amount

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

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
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

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}