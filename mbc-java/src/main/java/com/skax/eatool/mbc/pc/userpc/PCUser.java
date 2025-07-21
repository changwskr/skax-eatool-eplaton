package com.skax.eatool.mbc.pc.userpc;

import com.skax.eatool.mbc.dc.usermgtdc.IDCUser;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.mbc.dc.usermgtdc.DCUser;

/**
 * PCUser class
 */
public class PCUser {

    private IDCUser dcUser;

    public PCUser() {
        dcUser = new DCUser();
    }

    public User selectUser(String userId) throws Exception {
        return dcUser.selectUser(userId);
    }
}