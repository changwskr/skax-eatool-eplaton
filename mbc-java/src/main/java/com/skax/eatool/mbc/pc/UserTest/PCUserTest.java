package com.skax.eatool.mbc.pc.UserTest;

import java.util.List;

import com.skax.eatool.mbc.dc.UserTest.IDCUserTest;
import com.skax.eatool.mbc.dc.UserTest.DCUserTest;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.IFRSCommonDTO;

public class PCUserTest implements IPCUserTest {

	public List getUserList(IFRSCommonDTO commonDto) throws NewBusinessException {

		IDCUserTest dcUser = new DCUserTest();
		List usrList = dcUser.getUserList(commonDto);

		return usrList;
	}
}
