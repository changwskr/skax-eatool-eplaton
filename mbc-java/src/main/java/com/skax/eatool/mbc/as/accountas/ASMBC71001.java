package com.skax.eatool.mbc.as.accountas;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.pc.accountpc.PCAccount;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private PCAccount pcAccount;

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

		// 1.AccountPDTO 생성 - 직접 가져오는 방식으로 변경
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC71001", "=== ASMBC71001.execute - inputDto retrieved ===");
		slf4jLogger.info("=== ASMBC71001.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		AccountPDTO accountPDTO = (AccountPDTO) inputDto.get("AccountPDTO");
		
		// 디버깅을 위한 로그 추가
		logger.info("ASMBC71001", "=== ASMBC71001.execute - Retrieved AccountPDTO ===");
		slf4jLogger.info("=== ASMBC71001.execute - Retrieved AccountPDTO: {} (SLF4J) ===", accountPDTO);

		// AccountPDTO null 체크
		if (accountPDTO == null) {
			logger.warn("ASMBC71001", "=== ASMBC71001.execute - accountPDTO is null ===");
			slf4jLogger.warn("=== ASMBC71001.execute - accountPDTO is null (SLF4J) ===");
			logger.info("ASMBC71001", "=== ASMBC71001.execute END ===");
			slf4jLogger.info("=== ASMBC71001.execute END (SLF4J) ===");
			return reqData;
		}

		// AccountPDTO 값 로그 출력
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountId: " + accountPDTO.getAccountId() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountName: " + accountPDTO.getAccountName() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountId: {} (SLF4J) ===", accountPDTO.getAccountId());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountName: {} (SLF4J) ===", accountPDTO.getAccountName());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());

		// 2.PC호출
		pcAccount.createAccount(accountPDTO);

		// 3.결과를 OUTDATA에 set
		java.util.List<AccountPDTO> resultList = new java.util.ArrayList<>();
		resultList.add(accountPDTO);
		reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resultList);
		
		logger.info("ASMBC71001", "=== ASMBC71001.execute END ===");
		slf4jLogger.info("=== ASMBC71001.execute END (SLF4J) ===");
		return reqData;
	}

}
