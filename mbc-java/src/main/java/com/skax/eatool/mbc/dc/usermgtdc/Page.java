package com.skax.eatool.mbc.dc.usermgtdc;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * Page class
 */
public class Page extends NewAbstractDTO {

	private String userId;
	private String userName;
	private String email;
	private int totalLineCnt;
	private int outptLineCnt;

	public Page() {
		// Default constructor
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTotalLineCnt() {
		return totalLineCnt;
	}

	public void setTotalLineCnt(int totalLineCnt) {
		this.totalLineCnt = totalLineCnt;
	}

	public int getOutptLineCnt() {
		return outptLineCnt;
	}

	public void setOutptLineCnt(int outptLineCnt) {
		this.outptLineCnt = outptLineCnt;
	}
}
