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

	    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ASMBC71001.class);
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

		// 입력 객체 필드값 출력
		logger.info("ASMBC71001", "=== ASMBC71001.execute - Input Object Field Values ===");
		slf4jLogger.info("=== ASMBC71001.execute - Input Object Field Values (SLF4J) ===");
		
		NewGenericDto inputDto = reqData.getInputGenericDto();
		logger.info("ASMBC71001", "=== ASMBC71001.execute - inputDto: " + inputDto + " ===");
		slf4jLogger.info("=== ASMBC71001.execute - inputDto: {} (SLF4J) ===", inputDto);
		
		// inputDto의 주요 키들을 직접 확인
		if (inputDto != null) {
			logger.info("ASMBC71001", "=== ASMBC71001.execute - Checking inputDto contents ===");
			slf4jLogger.info("=== ASMBC71001.execute - Checking inputDto contents (SLF4J) ===");
			
			// 주요 키들을 직접 확인
			Object accountPDTOValue = inputDto.get("AccountPDTO");
			logger.info("ASMBC71001", "=== ASMBC71001.execute - AccountPDTO value: " + accountPDTOValue + " ===");
			slf4jLogger.info("=== ASMBC71001.execute - AccountPDTO value: {} (SLF4J) ===", accountPDTOValue);
		}

		// 1.AccountPDTO 생성 - 직접 가져오는 방식으로 변경
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

		// AccountPDTO 필드값 상세 출력
		logger.info("ASMBC71001", "=== ASMBC71001.execute - AccountPDTO Field Values ===");
		slf4jLogger.info("=== ASMBC71001.execute - AccountPDTO Field Values (SLF4J) ===");
		
		// AccountPDTO 값 로그 출력
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountNumber: " + accountPDTO.getAccountNumber() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.name: " + accountPDTO.getName() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.status: " + accountPDTO.getStatus() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.netAmount: " + accountPDTO.getNetAmount() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.currency: " + accountPDTO.getCurrency() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.interestRate: " + accountPDTO.getInterestRate() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.identificationNumber: " + accountPDTO.getIdentificationNumber() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.createdDate: " + accountPDTO.getCreatedDate() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.updatedDate: " + accountPDTO.getUpdatedDate() + " ===");
		
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountNumber: {} (SLF4J) ===", accountPDTO.getAccountNumber());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.name: {} (SLF4J) ===", accountPDTO.getName());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.status: {} (SLF4J) ===", accountPDTO.getStatus());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.netAmount: {} (SLF4J) ===", accountPDTO.getNetAmount());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.currency: {} (SLF4J) ===", accountPDTO.getCurrency());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.interestRate: {} (SLF4J) ===", accountPDTO.getInterestRate());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.identificationNumber: {} (SLF4J) ===", accountPDTO.getIdentificationNumber());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.createdDate: {} (SLF4J) ===", accountPDTO.getCreatedDate());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.updatedDate: {} (SLF4J) ===", accountPDTO.getUpdatedDate());

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
