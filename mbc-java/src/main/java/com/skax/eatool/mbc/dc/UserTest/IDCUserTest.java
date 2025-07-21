package com.skax.eatool.mbc.dc.UserTest;

import java.util.HashMap;
import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.IFRSCommonDTO;

public interface IDCUserTest {

	public List<HashMap> getUserList(IFRSCommonDTO commonDto) throws NewBusinessException;
}
