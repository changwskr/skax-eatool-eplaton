package com.skax.eatool.mbb.as.repositoryas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.pc.repositorypc.PCRepositoryGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Repository 생성 Application Service
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Component
public class ASRepositoryGeneration {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    private final String className = this.getClass().getSimpleName();
    
    @Autowired
    private PCRepositoryGeneration pcRepositoryGeneration;
    
    /**
     * Repository 생성 실행
     * 
     * @param requestData 요청 데이터
     * @return Repository 생성 결과
     */
    public NewKBData generateRepository(Map<String, Object> requestData) {
        String methodName = "generateRepository";
        logger.info(className, "[" + methodName + "] Repository 생성 AS 시작");
        
        try {
            // PC 호출
            NewKBData result = pcRepositoryGeneration.generateRepository(requestData);
            
            logger.info(className, "[" + methodName + "] Repository 생성 AS 완료");
            return result;
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] Repository 생성 AS 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("Repository 생성 AS 중 오류 발생: " + e.getMessage());
            return errorResponse;
        }
    }
} 