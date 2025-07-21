package com.skax.eatool.mbc.dc.UserTest;

import java.util.HashMap;
import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.das.ibatis.NewSqlMapper;
import com.skax.eatool.mbc.bzcrudbus.transfer.IFRSCommonDTO;

public class DCUserTest implements IDCUserTest {

	public List<HashMap> getUserList(IFRSCommonDTO commonDto) throws NewBusinessException {
		List result = null;
		try {
			result = (List) NewSqlMapper.getSqlMapClient().queryForList("userpilot2.getListUser",
					commonDto.getParameterMap());
		} catch (Exception e) {
			throw new NewBusinessException("D9005105", e);
		}
		return result;
	}
}
