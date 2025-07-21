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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 계정 생성 Application Service
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
@Service
public class ASMBC71001 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();
	private static final Logger slf4jLogger = LoggerFactory.getLogger(ASMBC71001.class);

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {
		logger.info("ASMBC71001", "=== ASMBC71001.execute START ===");
		slf4jLogger.info("=== ASMBC71001.execute START (SLF4J) ===");

		// Null 체크 추가
		if (reqData == null) {
			logger.warn("ASMBC71001", "=== ASMBC71001.execute - reqData is null ===");
			slf4jLogger.warn("=== ASMBC71001.execute - reqData is null (SLF4J) ===");
			logger.info("ASMBC71001", "=== ASMBC71001.execute END ===");
			slf4jLogger.info("=== ASMBC71001.execute END (SLF4J) ===");
			return null;
		}

		// 1.AccountPDTO 생성
		AccountPDTO accountPDTO = (AccountPDTO) reqData.getInputGenericDto()
				.using(NewGenericDto.INDATA).get("AccountPDTO");

		// AccountPDTO null 체크
		if (accountPDTO == null) {
			logger.warn("ASMBC71001", "=== ASMBC71001.execute - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC71001.execute - accountPDTO is null (SLF4J) ===");
			logger.info("ASMBC71001", "=== ASMBC71001.execute END ===");
			slf4jLogger.info("=== ASMBC71001.execute END (SLF4J) ===");
			return reqData;
		}

		// 2.PC호출
		new PCAccount().createAccount(accountPDTO);

		// 3.결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		
		logger.info("ASMBC71001", "=== ASMBC71001.execute END ===");
		slf4jLogger.info("=== ASMBC71001.execute END (SLF4J) ===");
		return reqData;
	}

}
