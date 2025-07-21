package com.skax.eatool.mbc.as.runtimeas;

import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewApplicationContext;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.dc.runtimedc.DCResourceCopy;
import com.skax.eatool.mbc.dc.runtimedc.dto.ResourceCopyDDTO;

public class ASMBC759Z1 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("log.ifrs.mbc");
	Process process = null;

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {

		String tranCd = (String) NewApplicationContext.get(NewApplicationContext.StndTelgmRecvTranCd);
		String tranCdPost = tranCd.substring(8, 10);

		if (tranCdPost != null) {

			DCResourceCopy cdRsrcCopy = new DCResourceCopy();

			// Resource list inquiry
			if (tranCdPost.equals("S0")) {

				// Resource confirmation
				/*
				 * NewGenericDto subDto =
				 * reqData.getInputGenericDto().using("/KB-Message/Individual/InData/Params");
				 * Map<String, String> paramMap = subDto.getAttributeMap();
				 * String uapplDstcd = (String)paramMap.get("uapplDstcd");
				 * String cmpoIdnfr = (String)paramMap.get("cmpoIdnfr");
				 * String fileIdnfr = (String)paramMap.get("fileIdnfr");
				 * String kywrCtnt = (String)paramMap.get("kywrCtnt");
				 */
				ResourceCopyDDTO rsrcCopyDDTO = (ResourceCopyDDTO) reqData.getInputGenericDto()
						.using(NewGenericDto.INDATA + "/Params")
						.get("ResourceCopyDDTO");
				List<ResourceCopyDDTO> rsrcCopyDDTOs = cdRsrcCopy.getListResourceInfo(rsrcCopyDDTO);

				reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(rsrcCopyDDTOs);
			}
			// Resource copy processing
			else if (tranCdPost.equals("R0")) {

				ResourceCopyDDTO[] rsrcCopyDDTOs = (ResourceCopyDDTO[]) reqData.getInputGenericDto()
						.using(NewGenericDto.INDATA + "/PageGridGroup").getArray(ResourceCopyDDTO.class);

				cdRsrcCopy.copyResource(rsrcCopyDDTOs);
			}
		}

		return reqData;
	}

}
