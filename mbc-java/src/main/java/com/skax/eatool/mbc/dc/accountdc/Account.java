/*
 * (@)# Account.java
 *
 * Copyright KB Kookmin Bank Inc. All rights reserved.
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.skax.eatool.mbc.dc.accountdc;

import java.util.Date;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * <br>
 * [프로그램명] 계정
 * <br>
 * [설명]
 * <br>
 * [상세설명]
 * <br>
 * [변경이력]
 * <ul>
 * <li>2008-08-26::전체::최초작성
 * <li>2024-01-01::전체::JDBC DTO로 변경
 * </ul>
 */
public class Account extends NewAbstractDTO {

	private static final long serialVersionUID = 1L;

	private String accountNumber; // 계좌번호

	private String name; // 이름

	private String identificationNumber; // 주민번호

	private Float interestRate; // 이자율

	private Date lastTransaction; // 마지막거래일

	private String password; // 비밀번호

	private Double netAmount; // 잔액

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

}