package com.skax.eatool.mbb.pc.dtoas;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dtoas.DCDtoGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * DTO 생성 Process Controller
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class PCDtoGeneration {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    private final String className = this.getClass().getSimpleName();
    
    @Autowired
    private DCDtoGeneration dcDtoGeneration;
    
    /**
     * DTO 생성 실행
     * 
     * @param requestData 요청 데이터
     * @return DTO 생성 결과
     */
    public NewKBData generateDto(Map<String, Object> requestData) {
        String methodName = "generateDto";
        logger.info(className, "[" + methodName + "] DTO 생성 PC 시작");
        
        try {
            // 입력 데이터 검증
            if (requestData == null || requestData.isEmpty()) {
                logger.error(className, "[" + methodName + "] 요청 데이터가 없습니다");
                NewKBData errorResponse = new NewKBData();
                errorResponse.setValid(false);
                errorResponse.setErrorMessage("요청 데이터가 없습니다");
                return errorResponse;
            }
            
            // DC 호출
            NewKBData result = dcDtoGeneration.generateDto(requestData);
            
            logger.info(className, "[" + methodName + "] DTO 생성 PC 완료");
            return result;
            
        } catch (Exception e) {
            logger.error(className, "[" + methodName + "] DTO 생성 PC 중 오류 발생: " + e.getMessage(), e);
            
            NewKBData errorResponse = new NewKBData();
            errorResponse.setValid(false);
            errorResponse.setErrorMessage("DTO 생성 PC 중 오류 발생: " + e.getMessage());
            return errorResponse;
        }
    }
} 