package com.skax.eatool.mbb.pc.ddlgeneratorpc;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.ddlgeneratordc.DCDdlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * DDL 생성 Process Controller
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class PCDdlGenerator {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private DCDdlGenerator dcDdlGenerator;
    
    /**
     * 테이블 정의를 기반으로 DDL을 생성합니다.
     *
     * @param tableName 테이블명
     * @param tableDefinition 테이블 정의
     * @param databaseType 데이터베이스 타입
     * @param schemaName 스키마명
     * @return 생성된 DDL
     */
    public String generateDDL(String tableName, Map<String, Object> tableDefinition, 
                             String databaseType, String schemaName) {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== PCDdlGenerator.generateDDL START ===");
            logger.info(className, "DDL 생성 PC 시작: " + tableName);
            logger.info(className, "데이터베이스 타입: " + databaseType);
            logger.info(className, "스키마명: " + schemaName);
            
            // DC 의존성 주입 확인
            if (dcDdlGenerator == null) {
                logger.error(className, "DC 의존성 주입 실패: dcDdlGenerator is null");
                throw new RuntimeException("DC 의존성 주입 오류");
            }
            
            logger.info(className, "DC 의존성 주입 확인 완료: " + dcDdlGenerator.getClass().getSimpleName());
            
            // DC 호출하여 DDL 생성
            logger.info(className, "DC 호출 시작: " + dcDdlGenerator.getClass().getSimpleName() + ".generateDDL");
            String ddl = dcDdlGenerator.generateDDL(tableName, tableDefinition, databaseType, schemaName);
            logger.info(className, "DC 호출 완료, DDL 생성 성공");
            
            logger.info(className, "DDL 생성 PC 완료: " + tableName);
            logger.info(className, "=== PCDdlGenerator.generateDDL END ===");
            
            return ddl;
            
        } catch (Exception e) {
            logger.error(className, "DDL 생성 PC 실패: " + tableName + " - " + e.getMessage());
            logger.error(className, "=== PCDdlGenerator.generateDDL ERROR ===");
            e.printStackTrace();
            throw new RuntimeException("DDL 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 테이블 정의 목록을 조회합니다.
     *
     * @return 테이블 정의 목록
     */
    public Map<String, Object> getTableDefinitionList() {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== PCDdlGenerator.getTableDefinitionList START ===");
            
            // DC 의존성 주입 확인
            if (dcDdlGenerator == null) {
                logger.error(className, "DC 의존성 주입 실패: dcDdlGenerator is null");
                throw new RuntimeException("DC 의존성 주입 오류");
            }
            
            logger.info(className, "DC 의존성 주입 확인 완료: " + dcDdlGenerator.getClass().getSimpleName());
            
            // DC 호출하여 테이블 정의 목록 조회
            logger.info(className, "DC 호출 시작: " + dcDdlGenerator.getClass().getSimpleName() + ".getTableDefinitionList");
            Map<String, Object> result = dcDdlGenerator.getTableDefinitionList();
            logger.info(className, "DC 호출 완료, 테이블 정의 목록 조회 성공");
            
            logger.info(className, "테이블 정의 목록 조회 PC 완료");
            logger.info(className, "=== PCDdlGenerator.getTableDefinitionList END ===");
            
            return result;
            
        } catch (Exception e) {
            logger.error(className, "테이블 정의 목록 조회 PC 실패: " + e.getMessage());
            logger.error(className, "=== PCDdlGenerator.getTableDefinitionList ERROR ===");
            e.printStackTrace();
            throw new RuntimeException("테이블 정의 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 