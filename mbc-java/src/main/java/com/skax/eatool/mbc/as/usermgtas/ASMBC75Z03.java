package com.skax.eatool.mbc.as.usermgtas;

import java.util.List;
import java.util.Map;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewApplicationContext;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.dc.usermgtdc.IDCUser;
import com.skax.eatool.mbc.dc.usermgtdc.Page;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.DCUser;

public class ASMBC75Z03 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {

		IDCUser idcuser = null;
		PageDDTO pageDdto = new PageDDTO();
		List<Page> resPage = null;

		NewGenericDto outDto = reqData.getOutputGenericDto().using(
				NewGenericDto.OUTDATA);
		NewGenericDto inDto = reqData.getInputGenericDto()
				.using(NewGenericDto.INDATA);

		String TransactionId = (String) NewApplicationContext
				.get(NewApplicationContext.StndTelgmRecvTranCd);
		String rsux = TransactionId.substring(8, 10);

		// Transaction code R/S/U/X inquiry
		if (rsux != null && rsux.equals("R0")) {

			Map<String, Object> attrMap = inDto.getAttributeMap();

			// Request page number, page line count to DDTO
			pageDdto.setDmndPageNo((String) attrMap.get("dmndPageNo"));
			pageDdto.setPageLineCnt((String) attrMap.get("pageLineCnt"));

			logger.debug("TotalLineCount = " + attrMap.get("totalLineCnt"));

			idcuser = new DCUser();

			// DC call
			try {
				resPage = idcuser.getListPage(pageDdto);
			} catch (Exception e) {
				throw new NewBusinessException("getListUser", "B0000001", e);
			}

			// Output DDTO total line count and output line count setting
			if (resPage.size() > 0) {
				outDto.addAttribute("totalLineCnt", String.valueOf(resPage.get(0)
						.getTotalLineCnt()));
				outDto.addAttribute("outptLineCnt", String.valueOf(resPage.get(0)
						.getOutptLineCnt()));
			}

			outDto.addAttribute("dmndPageNo", (String) attrMap.get("dmndPageNo"));

			// Page information list data output
			reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA)
					.add(resPage);

		}

		return reqData;
	}

}
