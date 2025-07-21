package com.skax.eatool.mbc.as.commonas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.mbc.as.bzcrudbus.business.as.ASComExec;

public class MBCComAS extends ASComExec {

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		// @TransactionAttribute(timeout=100, xa="false",
		// type=TransactionAttribute.Type.JTA)
		return super.execute(reqData);
	}

}
