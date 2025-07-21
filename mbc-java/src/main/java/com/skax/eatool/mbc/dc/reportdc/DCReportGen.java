package com.skax.eatool.mbc.dc.reportdc;

import java.util.List;

import com.skax.eatool.ksa.das.ibatis.NewSqlMapper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.as.bzcrudbus.report.WordReportUtil;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.ICommonDTO;

public class DCReportGen implements IDCReportGen {

	public void generateReport(ICommonDTO commonDto) throws NewBusinessException {
		try {
			System.out.println("generateReport 1");

			List resultList = (List) NewSqlMapper.getSqlMapClient().queryForList("DCRptMeta.getListReporting",
					commonDto.getParameterMap());
			System.out.println("generateReport 2");
			System.out.println("generateReport 3 : " + resultList);
			String rtfTemplatePath = "C:/ReportTest/";
			String rtfReportPath = "C:/ReportTest/";
			WordReportUtil reportUtil = new WordReportUtil(rtfTemplatePath, rtfReportPath);
			reportUtil.makeReport(resultList, NewSqlMapper.getSqlMapClient());

		} catch (Exception e) {
			e.printStackTrace();
			throw new NewBusinessException("D9005105", e.toString());
		}
	}
}
