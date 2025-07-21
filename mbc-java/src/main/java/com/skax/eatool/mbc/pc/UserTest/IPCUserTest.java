package com.skax.eatool.mbc.pc.UserTest;

import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.bzcrudbus.transfer.IFRSCommonDTO;

public interface IPCUserTest {

	public List getUserList(IFRSCommonDTO commonDto) throws NewBusinessException;
}
