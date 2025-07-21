package com.skax.eatool.mbc.pc.usermanagementpilotpc;

import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.usermgtpilotdc.DCUserPilot;
import com.skax.eatool.mbc.dc.usermgtpilotdc.IDCUserPilot;
import com.skax.eatool.mbc.dc.usermgtpilotdc.UserPilot;
import com.skax.eatool.mbc.dc.usermgtpilotdc.dto.UserPilotDDTO;
import com.skax.eatool.mbc.pc.dto.UserPilotPDTO;

public class PCUserManagementPilot implements IPCUserManagementPilot {

	public List<UserPilot> getListUser(UserPilotPDTO userpilotPDTO) throws NewBusinessException {

		IDCUserPilot idcuserpilot = new DCUserPilot();

		return idcuserpilot.getListUser(NewObjectUtil.copyForClass(UserPilotDDTO.class, userpilotPDTO));
	}

}
