package com.skax.eatool.mbc.as.testas;

import java.util.Iterator;
import java.util.List;

import com.skax.eatool.kji.bc.commonutilbc.BCRate;
import com.skax.eatool.kji.bc.commonutilbc.dto.MaktExrtBDTO;
import com.skax.eatool.kji.bc.commonutilbc.dto.MaktExrtListBDTO;
import com.skax.eatool.kji.oltp.transaction.impl.KjiCommonAreaDTO;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewApplicationContext;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;

public class ASMBC72001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("log.ifrs.mbc");

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {

		KjiCommonAreaDTO kjiCommonArea = new KjiCommonAreaDTO();
		NewApplicationContext.set("kjiCommonArea", kjiCommonArea);

		MaktExrtBDTO reqMaktExrtBDTO = new MaktExrtBDTO();

		// Set parameters
		reqMaktExrtBDTO.setInGroupCoCd("001");
		reqMaktExrtBDTO.setInDstcd("1");
		reqMaktExrtBDTO.setInBaseYmd("20081214");
		reqMaktExrtBDTO.setInCncycd("USD");
		reqMaktExrtBDTO.setInExrtAplyNth(1);

		MaktExrtBDTO resMaktExrtBDTO = (new BCRate()).getMarketExchangeRate(reqMaktExrtBDTO);

		String acno = "#";

		// 4. Check the result of the util program
		if ("07".equals(resMaktExrtBDTO.getRtnStat())) {

			// First time exchange rate processing is successful

		} else {

			// Market exchange rate List
			List<MaktExrtListBDTO> maktExrtListBDTOs = resMaktExrtBDTO.getOutMaktExrtList();

			for (Iterator iter = maktExrtListBDTOs.iterator(); iter.hasNext();) {
				MaktExrtListBDTO maktExrtListBDTO = (MaktExrtListBDTO) iter.next();
				acno = maktExrtListBDTO.getOutCncycd();

			}

		}

		/*
		 * MgtNoNbringingBDTO reqMgtNoNbringingBDTO = new MgtNoNbringingBDTO();
		 * 
		 * logger.debug("ASMBC72001 #1");
		 * // Set parameters for management number generation
		 * reqMgtNoNbringingBDTO.setInGroupCoCd("001");
		 * reqMgtNoNbringingBDTO.setInUapplDstcd("KJC");
		 * reqMgtNoNbringingBDTO.setInNbringingIdnfiCd("003");
		 * reqMgtNoNbringingBDTO.setInBrncd("4004");
		 * 
		 * logger.debug("ASMBC72001 #2");
		 * 
		 * MgtNoNbringingBDTO resMgtNoNbringingBDTO = (new
		 * BCNumbering()).getMgtNoNumbering (reqMgtNoNbringingBDTO);
		 * 
		 * logger.debug("ASMBC72001 #3");
		 * 
		 * String acno = resMgtNoNbringingBDTO.getOutEdtMgtNo();
		 * 
		 * logger.debug("ASMBC72001 #4");
		 */
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).addNode("TEST");

		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA + "/TEST").addAttribute("result",
				"SUCCESS!! (" + acno + ")");

		logger.debug("TEST SUCCESS!!! (" + acno + ")");

		return reqData;
	}

}
