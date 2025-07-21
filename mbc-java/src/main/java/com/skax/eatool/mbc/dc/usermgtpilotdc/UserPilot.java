package com.skax.eatool.mbc.dc.usermgtpilotdc;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * UserPilot class
 */
public class UserPilot extends NewAbstractDTO {

	private String userId;
	private String userName;
	private String userEmail;

	public UserPilot() {
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
