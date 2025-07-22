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
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountId: " + accountPDTO.getAccountId() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountName: " + accountPDTO.getAccountName() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountType: " + accountPDTO.getAccountType() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountStatus: " + accountPDTO.getAccountStatus() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountBalance: " + accountPDTO.getAccountBalance() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountCurrency: " + accountPDTO.getAccountCurrency() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountOpenDate: " + accountPDTO.getAccountOpenDate() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountOwner: " + accountPDTO.getAccountOwner() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountManager: " + accountPDTO.getAccountManager() + " ===");
		logger.info("ASMBC71001", "=== ASMBC71001.execute - accountPDTO.accountBranch: " + accountPDTO.getAccountBranch() + " ===");
		
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountId: {} (SLF4J) ===", accountPDTO.getAccountId());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountName: {} (SLF4J) ===", accountPDTO.getAccountName());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountType: {} (SLF4J) ===", accountPDTO.getAccountType());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountStatus: {} (SLF4J) ===", accountPDTO.getAccountStatus());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountBalance: {} (SLF4J) ===", accountPDTO.getAccountBalance());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountCurrency: {} (SLF4J) ===", accountPDTO.getAccountCurrency());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountOpenDate: {} (SLF4J) ===", accountPDTO.getAccountOpenDate());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountOwner: {} (SLF4J) ===", accountPDTO.getAccountOwner());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountManager: {} (SLF4J) ===", accountPDTO.getAccountManager());
		slf4jLogger.info("=== ASMBC71001.execute - accountPDTO.accountBranch: {} (SLF4J) ===", accountPDTO.getAccountBranch());

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
