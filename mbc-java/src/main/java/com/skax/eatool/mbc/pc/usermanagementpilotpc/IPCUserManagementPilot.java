package com.skax.eatool.mbc.pc.usermanagementpilotpc;

import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.dc.usermgtpilotdc.UserPilot;
import com.skax.eatool.mbc.pc.dto.UserPilotPDTO;

public interface IPCUserManagementPilot {
	public List<UserPilot> getListUser(UserPilotPDTO userpilotPDTO) throws NewBusinessException;
}
