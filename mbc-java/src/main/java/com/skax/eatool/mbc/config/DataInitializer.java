package com.skax.eatool.mbc.config;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 데이터 초기화 클래스
 * 
 * 프로그램명: DataInitializer.java
 * 설명: 애플리케이션 시작 시 데이터베이스 초기화를 담당
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 애플리케이션 시작 시 데이터베이스 초기화
 * - 테이블 생성 및 테스트 데이터 삽입
 * - 초기화 상태 로깅
 * 
 * @version 1.0
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final NewIKesaLogger logger = NewKesaLogHelper.getBiz();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== 데이터베이스 초기화 시작 ===", "DataInitializer");

        try {
            // 1. 테이블 존재 여부 확인
            checkTablesExist();

            // 2. 초기 데이터 확인
            checkInitialData();

            // 3. 초기화 완료 로그
            logInitializationSummary();

            logger.info("=== 데이터베이스 초기화 완료 ===", "DataInitializer");

        } catch (Exception e) {
            logger.error("데이터베이스 초기화 중 오류 발생: " + e.getMessage(), "DataInitializer");
            throw e;
        }
    }

    /**
     * 테이블 존재 여부 확인
     */
    private void checkTablesExist() {
        logger.info("테이블 존재 여부 확인 중...", "DataInitializer");

        String[] tables = {
                "ACCOUNT", "USER_INFO", "USER_ACCOUNT",
                "SYSTEM_CODE", "SYSTEM_LOG"
        };

        for (String table : tables) {
            try {
                String sql = "SELECT COUNT(*) FROM " + table;
                int count = jdbcTemplate.queryForObject(sql, Integer.class);
                logger.info("테이블 " + table + " 확인 완료 - 레코드 수: " + count, "DataInitializer");
            } catch (Exception e) {
                logger.warn("테이블 " + table + " 확인 실패: " + e.getMessage(), "DataInitializer");
            }
        }
    }

    /**
     * 초기 데이터 확인
     */
    private void checkInitialData() {
        logger.info("초기 데이터 확인 중...", "DataInitializer");

        try {
            // ACCOUNT 테이블 데이터 확인
            String accountSql = "SELECT COUNT(*) FROM ACCOUNT";
            int accountCount = jdbcTemplate.queryForObject(accountSql, Integer.class);
            logger.info("ACCOUNT 테이블 레코드 수: " + accountCount, "DataInitializer");

            if (accountCount > 0) {
                // ACCOUNT 테이블의 실제 데이터 확인
                String accountDataSql = "SELECT ACCOUNT_NUMBER, NAME, NET_AMOUNT FROM ACCOUNT LIMIT 5";
                List<Map<String, Object>> accounts = jdbcTemplate.queryForList(accountDataSql);
                
                logger.info("ACCOUNT 테이블 샘플 데이터:", "DataInitializer");
                for (Map<String, Object> account : accounts) {
                    logger.info("  - 계좌번호: " + account.get("ACCOUNT_NUMBER") + 
                               ", 이름: " + account.get("NAME") + 
                               ", 잔액: " + account.get("NET_AMOUNT"), "DataInitializer");
                }
            } else {
                logger.warn("ACCOUNT 테이블에 데이터가 없습니다!", "DataInitializer");
            }

            // USER_INFO 테이블 데이터 확인
            String userSql = "SELECT COUNT(*) FROM USER_INFO";
            int userCount = jdbcTemplate.queryForObject(userSql, Integer.class);
            logger.info("USER_INFO 테이블 레코드 수: " + userCount, "DataInitializer");

            // SYSTEM_CODE 테이블 데이터 확인
            String codeSql = "SELECT COUNT(*) FROM SYSTEM_CODE";
            int codeCount = jdbcTemplate.queryForObject(codeSql, Integer.class);
            logger.info("SYSTEM_CODE 테이블 레코드 수: " + codeCount, "DataInitializer");

        } catch (Exception e) {
            logger.error("초기 데이터 확인 중 오류 발생: " + e.getMessage(), "DataInitializer");
        }
    }

    /**
     * 초기화 요약 로그
     */
    private void logInitializationSummary() {
        logger.info("=== 초기화 요약 ===", "DataInitializer");

        // 계정별 통계 (ACCOUNT_TYPE 대신 실제 컬럼 사용)
        String accountSql = "SELECT COUNT(*) FROM ACCOUNT";
        int accountCount = jdbcTemplate.queryForObject(accountSql, Integer.class);
        logger.info("총 계정 수: " + accountCount + "건", "DataInitializer");

        // 사용자 역할별 통계
        String userRoleSql = "SELECT ROLE, COUNT(*) FROM USER_INFO GROUP BY ROLE";
        try {
            jdbcTemplate.query(userRoleSql, (rs, rowNum) -> {
                String role = rs.getString("ROLE");
                int count = rs.getInt("COUNT(*)");
                logger.info("사용자 역할 " + role + ": " + count + "건", "DataInitializer");
                return null;
            });
        } catch (Exception e) {
            logger.warn("사용자 역할별 통계 조회 실패: " + e.getMessage(), "DataInitializer");
        }

        // 사용자 상태별 통계
        String userStatusSql = "SELECT STATUS, COUNT(*) FROM USER_INFO GROUP BY STATUS";
        try {
            jdbcTemplate.query(userStatusSql, (rs, rowNum) -> {
                String status = rs.getString("STATUS");
                int count = rs.getInt("COUNT(*)");
                logger.info("사용자 상태 " + status + ": " + count + "건", "DataInitializer");
                return null;
            });
        } catch (Exception e) {
            logger.warn("사용자 상태별 통계 조회 실패: " + e.getMessage(), "DataInitializer");
        }

        // 거래 내역 통계
        String transactionSql = "SELECT TRANSACTION_TYPE, COUNT(*) FROM TRANSACTION GROUP BY TRANSACTION_TYPE";
        try {
            jdbcTemplate.query(transactionSql, (rs, rowNum) -> {
                String type = rs.getString("TRANSACTION_TYPE");
                int count = rs.getInt("COUNT(*)");
                logger.info("거래 타입 " + type + ": " + count + "건", "DataInitializer");
                return null;
            });
        } catch (Exception e) {
            logger.warn("거래 내역 통계 조회 실패: " + e.getMessage(), "DataInitializer");
        }

        // 시스템 코드 통계
        String codeTypeSql = "SELECT CODE_TYPE, COUNT(*) FROM SYSTEM_CODE GROUP BY CODE_TYPE";
        try {
            jdbcTemplate.query(codeTypeSql, (rs, rowNum) -> {
                String type = rs.getString("CODE_TYPE");
                int count = rs.getInt("COUNT(*)");
                logger.info("시스템 코드 타입 " + type + ": " + count + "건", "DataInitializer");
                return null;
            });
        } catch (Exception e) {
            logger.warn("시스템 코드 통계 조회 실패: " + e.getMessage(), "DataInitializer");
        }

        logger.info("=== 초기화 요약 완료 ===", "DataInitializer");
    }

    /**
     * SQL 파일 읽기 (필요시 사용)
     */
    private String readSqlFile(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("SQL 파일 읽기 실패: " + fileName, String.valueOf(e));
            return null;
        }
    }
}