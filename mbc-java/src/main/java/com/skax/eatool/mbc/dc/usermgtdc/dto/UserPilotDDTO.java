package com.skax.eatool.mbc.dc.usermgtdc.dto;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * UserPilotDDTO class
 */
public class UserPilotDDTO extends NewAbstractDTO {

    private String userId;
    private String userName;

    public UserPilotDDTO() {
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
}