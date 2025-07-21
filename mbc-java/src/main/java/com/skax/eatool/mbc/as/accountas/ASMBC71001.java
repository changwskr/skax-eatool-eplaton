package com.skax.eatool.mbc.as.accountas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.pc.accountpc.PCAccount;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.stereotype.Service;

/**
 * 계정 생성 Application Service
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
@Service
public class ASMBC71001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC71001", "=== ASMBC71001 START ===");
		
		// TODO 코드 구현 및 메서드 추가

		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto().using(NewGenericDto.INDATA)
				.get("AccountPDTO");

		new PCAccount().createAccount(accountPDTO);
		
		logger.info("ASMBC71001", "=== ASMBC71001 END ===");
		return null;
	}

}
