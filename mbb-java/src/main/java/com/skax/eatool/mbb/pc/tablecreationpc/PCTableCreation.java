package com.skax.eatool.mbb.pc.tablecreationpc;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.mbb.dc.dto.TableDefinitionDDTO;
import com.skax.eatool.mbb.dc.tablecreationdc.DCTableCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 테이블 생성 Process Component
 *
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Component
public class PCTableCreation {
    
    private final NewIKesaLogger logger = NewKesaLogHelper.getLogger(this.getClass());
    
    @Autowired
    private DCTableCreation dcTableCreation;
    
    /**
     * 테이블을 생성합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 생성 결과
     */
    public boolean createTable(TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();

        try {
            logger.info(className, "=== PCTableCreation.createTable START ===");
            logger.info(className, "테이블 생성 프로세스 시작: " + tableDefinition.getTableName());

            // DC 의존성 주입 확인
            if (dcTableCreation == null) {
                logger.error(className, "DC 의존성 주입 실패: dcTableCreation is null");
                return false;
            }
            
            logger.info(className, "DC 의존성 주입 확인 완료: " + dcTableCreation.getClass().getSimpleName());

            // 비즈니스 로직 검증
            if (!validateBusinessRules(tableDefinition)) {
                logger.error(className, "비즈니스 규칙 검증 실패");
                return false;
            }

            // DC 호출하여 테이블 생성
            logger.info(className, "DC 호출 시작: " + dcTableCreation.getClass().getSimpleName() + ".createTable");
            boolean result = dcTableCreation.createTable(tableDefinition);
            logger.info(className, "DC 호출 완료, 결과: " + result);

            if (result) {
                logger.info(className, "테이블 생성 프로세스 완료: " + tableDefinition.getTableName());
            } else {
                logger.error(className, "테이블 생성 프로세스 실패: " + tableDefinition.getTableName());
            }

            logger.info(className, "=== PCTableCreation.createTable END ===");
            return result;

        } catch (Exception e) {
            logger.error(className, "테이블 생성 프로세스 중 오류 발생: " + e.getMessage());
            logger.error(className, "=== PCTableCreation.createTable ERROR ===");
            e.printStackTrace(); // 스택 트레이스 출력
            return false;
        }
    }
    
    /**
     * 비즈니스 규칙을 검증합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 검증 결과
     */
    private boolean validateBusinessRules(TableDefinitionDDTO tableDefinition) {
        String className = this.getClass().getSimpleName();
        
        // 테이블명 규칙 검증
        if (!validateTableName(tableDefinition.getTableName())) {
            logger.error(className, "테이블명 규칙 검증 실패: " + tableDefinition.getTableName());
            return false;
        }
        
        // 컬럼 규칙 검증
        if (!validateColumnRules(tableDefinition)) {
            logger.error(className, "컬럼 규칙 검증 실패");
            return false;
        }
        
        return true;
    }
    
    /**
     * 테이블명 규칙을 검증합니다.
     *
     * @param tableName 테이블명
     * @return 검증 결과
     */
    private boolean validateTableName(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            return false;
        }
        
        // 테이블명은 영문자, 숫자, 언더스코어만 허용
        if (!tableName.matches("^[A-Za-z][A-Za-z0-9_]*$")) {
            return false;
        }
        
        // 예약어 체크
        String[] reservedWords = {"SELECT", "FROM", "WHERE", "INSERT", "UPDATE", "DELETE", "CREATE", "DROP", "TABLE"};
        String upperTableName = tableName.toUpperCase();
        for (String reservedWord : reservedWords) {
            if (upperTableName.equals(reservedWord)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 컬럼 규칙을 검증합니다.
     *
     * @param tableDefinition 테이블 정의서
     * @return 검증 결과
     */
    private boolean validateColumnRules(TableDefinitionDDTO tableDefinition) {
        if (tableDefinition.getColumns() == null || tableDefinition.getColumns().isEmpty()) {
            return false;
        }
        
        // 컬럼명 중복 검증
        long distinctColumnCount = tableDefinition.getColumns().stream()
                .map(TableDefinitionDDTO.ColumnDefinition::getName)
                .distinct()
                .count();
        
        if (distinctColumnCount != tableDefinition.getColumns().size()) {
            return false;
        }
        
        // PK 검증
        long pkCount = tableDefinition.getColumns().stream()
                .filter(TableDefinitionDDTO.ColumnDefinition::isPk)
                .count();
        
        if (pkCount == 0) {
            return false;
        }
        
        if (pkCount > 1) {
            return false;
        }
        
        // 컬럼명 규칙 검증
        for (TableDefinitionDDTO.ColumnDefinition column : tableDefinition.getColumns()) {
            if (!validateColumnName(column.getName())) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 컬럼명 규칙을 검증합니다.
     *
     * @param columnName 컬럼명
     * @return 검증 결과
     */
    private boolean validateColumnName(String columnName) {
        if (columnName == null || columnName.trim().isEmpty()) {
            return false;
        }
        
        // 컬럼명은 영문자, 숫자, 언더스코어만 허용
        if (!columnName.matches("^[A-Za-z][A-Za-z0-9_]*$")) {
            return false;
        }
        
        return true;
    }
} 