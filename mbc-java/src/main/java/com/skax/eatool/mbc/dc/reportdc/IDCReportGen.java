package com.skax.eatool.mbc.dc.reportdc;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.bzcrudbus.transfer.ICommonDTO;

public interface IDCReportGen {

	public void generateReport(ICommonDTO commonDto) throws NewBusinessException;
}
