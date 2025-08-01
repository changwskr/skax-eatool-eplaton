package com.skax.eatool.mbb.as.tablecreationas;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import com.skax.eatool.mbb.pc.tablecreationpc.PCTableCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테이블 생성 Application Service
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class ASTableCreation {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private PCTableCreation pcTableCreation;
    
    /**
     * 테이블을 생성합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 생성 결과
     */
    @Transactional
    public boolean createTable(TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        
        try {
            logger.info(className, "=== ASTableCreation.createTable START ===");
            logger.info(className, "테이블 생성 서비스 시작: " + tableDefinition.getTableName());
            
            // PC 의존성 주입 확인
            if (pcTableCreation == null) {
                logger.error(className, "PC 의존성 주입 실패: pcTableCreation is null");
                return false;
            }
            
            logger.info(className, "PC 의존성 주입 확인 완료: " + pcTableCreation.getClass().getSimpleName());
            
            // 입력값 검증
            if (!validateTableDefinition(tableDefinition)) {
                logger.error(className, "테이블 정의서 검증 실패");
                return false;
            }
            
            // PC 호출하여 테이블 생성
            logger.info(className, "PC 호출 시작: " + pcTableCreation.getClass().getSimpleName() + ".createTable");
            boolean result = pcTableCreation.createTable(tableDefinition);
            logger.info(className, "PC 호출 완료, 결과: " + result);
            
            if (result) {
                logger.info(className, "테이블 생성 서비스 완료: " + tableDefinition.getTableName());
            } else {
                logger.error(className, "테이블 생성 서비스 실패: " + tableDefinition.getTableName());
            }
            
            logger.info(className, "=== ASTableCreation.createTable END ===");
            return result;
            
        } catch (Exception e) {
            logger.error(className, "테이블 생성 서비스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== ASTableCreation.createTable ERROR ===");
            e.printStackTrace(); // 스택 트레이스 출력
            return false;
        }
    }
    
    /**
     * 테이블 정의서를 검증합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 검증 결과
     */
    private boolean validateTableDefinition(TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        
        // 테이블명 검증
        if (tableDefinition.getTableName() == null || tableDefinition.getTableName().trim().isEmpty()) {
            logger.error(className, "테이블명이 비어있습니다.");
            return false;
        }
        
        // 컬럼 검증
        if (tableDefinition.getColumns() == null || tableDefinition.getColumns().isEmpty()) {
            logger.error(className, "컬럼이 정의되지 않았습니다.");
            return false;
        }
        
        // 컬럼명 중복 검증
        long distinctColumnCount = tableDefinition.getColumns().stream()
                .map(TableDefinitionDDTO.ColumnDefinition::getName)
                .distinct()
                .count();
        
        if (distinctColumnCount != tableDefinition.getColumns().size()) {
            logger.error(className, "중복된 컬럼명이 있습니다.");
            return false;
        }
        
        // PK 검증
        long pkCount = tableDefinition.getColumns().stream()
                .filter(TableDefinitionDDTO.ColumnDefinition::isPk)
                .count();
        
        if (pkCount == 0) {
            logger.error(className, "Primary Key가 정의되지 않았습니다.");
            return false;
        }
        
        if (pkCount > 1) {
            logger.error(className, "Primary Key는 하나만 정의할 수 있습니다.");
            return false;
        }
        
        return true;
    }
} 