package com.skax.eatool.mbc.dc.UserTest;

import java.util.HashMap;
import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.das.ibatis.NewSqlMapper;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.IFRSCommonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DCUserTest implements IDCUserTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DCUserTest.class);

	public List<HashMap> getUserList(IFRSCommonDTO commonDto) throws NewBusinessException {
		logger.info("=== DCUserTest.getUserList START ===");
		
		// Null 체크 추가
		if (commonDto == null) {
			logger.warn("=== DCUserTest.getUserList - commonDto is null ===");
			logger.info("=== DCUserTest.getUserList END ===");
			return null;
		}
		
		// 입력 객체 필드값 출력
		logger.info("=== DCUserTest.getUserList - Input IFRSCommonDTO Field Values ===");
		logger.info("=== DCUserTest.getUserList - commonDto.parameterMap: {} ===", commonDto.getParameterMap());
		
		List result = null;
		try {
			logger.info("=== DCUserTest.getUserList - Calling NewSqlMapper.getSqlMapClient().queryForList ===");
			logger.info("=== DCUserTest.getUserList - SQL ID: userpilot2.getListUser ===");
			logger.info("=== DCUserTest.getUserList - Parameters: parameterMap={} ===", commonDto.getParameterMap());
			
			result = (List) NewSqlMapper.getSqlMapClient().queryForList("userpilot2.getListUser",
					commonDto.getParameterMap());
			
			logger.info("=== DCUserTest.getUserList - Query result count: {} ===", result != null ? result.size() : 0);
			
			// 결과 상세 로깅 (최대 5개까지만)
			if (result != null && !result.isEmpty()) {
				logger.info("=== DCUserTest.getUserList - Result HashMap Values (showing up to 5) ===");
				int count = 0;
				for (Object item : result) {
					if (count >= 5) {
						logger.info("=== DCUserTest.getUserList - ... and {} more items ===", result.size() - 5);
						break;
					}
					if (item instanceof HashMap) {
						HashMap map = (HashMap) item;
						logger.info("=== DCUserTest.getUserList - HashMap[{}]: {} ===", count, map);
					} else {
						logger.info("=== DCUserTest.getUserList - Item[{}]: {} ===", count, item);
					}
					count++;
				}
			} else {
				logger.warn("=== DCUserTest.getUserList - No results found ===");
			}
			
			logger.info("=== DCUserTest.getUserList - User list retrieved successfully ===");
		} catch (Exception e) {
			logger.error("=== DCUserTest.getUserList - Error occurred: {} ===", e.getMessage(), e);
			throw new NewBusinessException("D9005105", e);
		} finally {
			logger.info("=== DCUserTest.getUserList END ===");
		}
		return result;
	}
}
