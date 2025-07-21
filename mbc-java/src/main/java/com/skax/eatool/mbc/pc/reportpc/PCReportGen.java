/**
 * 
 */
package com.skax.eatool.mbc.pc.reportpc;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.dc.reportdc.DCReportGen;
import com.skax.eatool.mbc.dc.reportdc.IDCReportGen;
import com.skax.eatool.mbc.bzcrudbus.transfer.ICommonDTO;

/**
 * @author S001849
 *
 */
public class PCReportGen {

	public void generateReport(ICommonDTO commonDto) throws NewBusinessException {
		// KBData reqData = commonDto.getKbData();

		IDCReportGen dcReportGen = new DCReportGen();

		dcReportGen.generateReport(commonDto);

	}

}
