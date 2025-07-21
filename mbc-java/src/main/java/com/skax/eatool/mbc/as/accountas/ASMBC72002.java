package com.skax.eatool.mbc.as.accountas;

import java.util.List;

import com.skax.eatool.mbc.pc.accountpc.PCAccount;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;

/**
 * 계정 목록 조회 Application Service
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
public class ASMBC72002 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		// TODO 코드 구현 및 메서드 추가
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA)
				.get("AccountPDTO");

		List<AccountPDTO> resAccountPDTOs = new PCAccount().getListAccount(accountPDTO);

		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resAccountPDTOs);

		return reqData;
	}

}
