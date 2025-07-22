package com.skax.eatool.mbc.dc.accountdc.mapper;

import java.util.List;
import java.util.Optional;

import com.skax.eatool.mbc.dc.accountdc.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Account JDBC DAO 클래스
 * 
 * 프로그램명: AccountMapper.java
 * 설명: 계정 관리 관련 데이터베이스 작업을 위한 JDBC DAO 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 CRUD 작업
 * - 계정 조회 및 검색
 * 
 * @version 1.0
 */
@Repository
public class AccountMapper {

    private static final Logger logger = LoggerFactory.getLogger(AccountMapper.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
        account.setName(rs.getString("NAME"));
        account.setIdentificationNumber(rs.getString("IDENTIFICATION_NUMBER"));
        account.setInterestRate(rs.getDouble("INTEREST_RATE")); // Float -> Double로 변경
        account.setLastTransaction(rs.getTimestamp("LAST_TRANSACTION"));
        account.setPassword(rs.getString("PASSWORD"));
        account.setNetAmount(rs.getDouble("NET_AMOUNT"));
        
        // 누락된 컬럼들 추가
        account.setAccountType(rs.getString("ACCOUNT_TYPE"));
        account.setStatus(rs.getString("STATUS"));
        account.setCurrency(rs.getString("CURRENCY"));
        account.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        account.setUpdatedDate(rs.getTimestamp("UPDATED_DATE"));
        
        return account;
    };

    /**
     * 계좌번호로 계정 조회
     * 
     * @param accountNumber 계좌번호
     * @return 계정 정보
     */
    public Optional<Account> findByAccountNumber(String accountNumber) {
        logger.info("=== AccountMapper.findByAccountNumber START ===");
        
        // 입력 파라미터 출력
        logger.info("=== AccountMapper.findByAccountNumber - Input accountNumber: {} ===", accountNumber);
        
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        logger.info("=== AccountMapper.findByAccountNumber - SQL: {} ===", sql);
        logger.info("=== AccountMapper.findByAccountNumber - Parameters: accountNumber={} ===", accountNumber);
        
        List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, accountNumber);
        logger.info("=== AccountMapper.findByAccountNumber - Query result count: {} ===", accounts.size());
        
        if (!accounts.isEmpty()) {
            Account account = accounts.get(0);
            // 결과 Account 엔티티 필드값 출력
            logger.info("=== AccountMapper.findByAccountNumber - Result Account Field Values ===");
            logger.info("=== AccountMapper.findByAccountNumber - account.accountNumber: {} ===", account.getAccountNumber());
            logger.info("=== AccountMapper.findByAccountNumber - account.name: {} ===", account.getName());
            logger.info("=== AccountMapper.findByAccountNumber - account.accountType: {} ===", account.getAccountType());
            logger.info("=== AccountMapper.findByAccountNumber - account.status: {} ===", account.getStatus());
            logger.info("=== AccountMapper.findByAccountNumber - account.netAmount: {} ===", account.getNetAmount());
            logger.info("=== AccountMapper.findByAccountNumber - account.currency: {} ===", account.getCurrency());
            logger.info("=== AccountMapper.findByAccountNumber - account.interestRate: {} ===", account.getInterestRate());
            logger.info("=== AccountMapper.findByAccountNumber - account.identificationNumber: {} ===", account.getIdentificationNumber());
            logger.info("=== AccountMapper.findByAccountNumber - account.password: {} ===", account.getPassword());
            logger.info("=== AccountMapper.findByAccountNumber - account.lastTransaction: {} ===", account.getLastTransaction());
            logger.info("=== AccountMapper.findByAccountNumber - account.createdDate: {} ===", account.getCreatedDate());
            logger.info("=== AccountMapper.findByAccountNumber - account.updatedDate: {} ===", account.getUpdatedDate());
            logger.info("=== AccountMapper.findByAccountNumber - Account found successfully ===");
        } else {
            logger.warn("=== AccountMapper.findByAccountNumber - No account found for accountNumber: {} ===", accountNumber);
        }
        
        logger.info("=== AccountMapper.findByAccountNumber END ===");
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
    }

    /**
     * 계정 존재 여부 확인
     * 
     * @param accountNumber 계좌번호
     * @return 존재 여부
     */
    public boolean existsByAccountNumber(String accountNumber) {
        logger.info("=== AccountMapper.existsByAccountNumber START ===");
        String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, accountNumber);
        logger.info("=== AccountMapper.existsByAccountNumber END ===");
        return count > 0;
    }

    /**
     * 계정 타입별 조회
     * 
     * @param accountType 계정 타입
     * @return 계정 목록
     */
    public List<Account> findByAccountType(String accountType) {
        logger.info("=== AccountMapper.findByAccountType START ===");
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_TYPE = ?";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper, accountType);
        logger.info("=== AccountMapper.findByAccountType END ===");
        return result;
    }

    /**
     * 상태별 계정 조회
     * 
     * @param status 상태
     * @return 계정 목록
     */
    public List<Account> findByStatus(String status) {
        logger.info("=== AccountMapper.findByStatus START ===");
        String sql = "SELECT * FROM ACCOUNT WHERE STATUS = ?";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper, status);
        logger.info("=== AccountMapper.findByStatus END ===");
        return result;
    }

    /**
     * 통화별 계정 조회
     * 
     * @param currency 통화
     * @return 계정 목록
     */
    public List<Account> findByCurrency(String currency) {
        logger.info("=== AccountMapper.findByCurrency START ===");
        String sql = "SELECT * FROM ACCOUNT WHERE CURRENCY = ?";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper, currency);
        logger.info("=== AccountMapper.findByCurrency END ===");
        return result;
    }

    /**
     * 잔액 범위로 계정 조회
     * 
     * @param minBalance 최소 잔액
     * @param maxBalance 최대 잔액
     * @return 계정 목록
     */
    public List<Account> findByBalanceBetween(Double minBalance, Double maxBalance) {
        logger.info("=== AccountMapper.findByBalanceBetween START ===");
        String sql = "SELECT * FROM ACCOUNT WHERE CAST(NET_AMOUNT AS DECIMAL) BETWEEN ? AND ?";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper, minBalance, maxBalance);
        logger.info("=== AccountMapper.findByBalanceBetween END ===");
        return result;
    }

    /**
     * 계좌번호로 부분 검색
     * 
     * @param accountNumber 계좌번호 (부분)
     * @return 계정 목록
     */
    public List<Account> findByAccountNumberContaining(String accountNumber) {
        logger.info("=== AccountMapper.findByAccountNumberContaining START ===");
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER LIKE ?";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper, "%" + accountNumber + "%");
        logger.info("=== AccountMapper.findByAccountNumberContaining END ===");
        return result;
    }

    /**
     * 모든 계정 조회
     * 
     * @return 계정 목록
     */
    public List<Account> findAll() {
        logger.info("=== AccountMapper.findAll START ===");
        
        try {
            // 테이블 존재 여부 확인
            String checkTableSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ACCOUNT'";
            int tableCount = jdbcTemplate.queryForObject(checkTableSql, Integer.class);
            logger.info("=== AccountMapper.findAll - ACCOUNT table exists: {} ===", tableCount > 0);
            
            if (tableCount == 0) {
                logger.error("=== AccountMapper.findAll - ACCOUNT table does not exist ===");
                return new java.util.ArrayList<>();
            }
            
            // 테이블의 총 레코드 수 확인
            String countSql = "SELECT COUNT(*) FROM ACCOUNT";
            int totalCount = jdbcTemplate.queryForObject(countSql, Integer.class);
            logger.info("=== AccountMapper.findAll - Total records in ACCOUNT table: {} ===", totalCount);
            
            if (totalCount == 0) {
                logger.warn("=== AccountMapper.findAll - No data found in ACCOUNT table ===");
                return new java.util.ArrayList<>();
            }
            
            // 컬럼 존재 여부 확인
            String checkColumnsSql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ACCOUNT'";
            List<String> columns = jdbcTemplate.queryForList(checkColumnsSql, String.class);
            logger.info("=== AccountMapper.findAll - Available columns: {} ===", columns);
            
            String sql = "SELECT * FROM ACCOUNT";
            List<Account> result = jdbcTemplate.query(sql, accountRowMapper);
            
            logger.info("=== AccountMapper.findAll - Retrieved {} accounts ===", result.size());
            
            // 결과 상세 로깅
            for (int i = 0; i < result.size(); i++) {
                Account account = result.get(i);
                logger.info("=== AccountMapper.findAll - Account[{}]: accountNumber={}, name={}, netAmount={} ===", 
                           i, account.getAccountNumber(), account.getName(), account.getNetAmount());
            }
            
            logger.info("=== AccountMapper.findAll END ===");
            return result;
            
        } catch (Exception e) {
            logger.error("=== AccountMapper.findAll - Error occurred: {} ===", e.getMessage(), e);
            return new java.util.ArrayList<>();
        }
    }

    /**
     * 계정 저장 (생성 또는 수정)
     * 
     * @param account 계정 정보
     */
    public void save(Account account) {
        logger.info("=== AccountMapper.save START ===");
        
        // Null 체크 추가
        if (account == null) {
            logger.error("=== AccountMapper.save - account is null ===");
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        // 입력 Account 엔티티 필드값 출력
        logger.info("=== AccountMapper.save - Input Account Field Values ===");
        logger.info("=== AccountMapper.save - account.accountNumber: {} ===", account.getAccountNumber());
        logger.info("=== AccountMapper.save - account.name: {} ===", account.getName());
        logger.info("=== AccountMapper.save - account.accountType: {} ===", account.getAccountType());
        logger.info("=== AccountMapper.save - account.status: {} ===", account.getStatus());
        logger.info("=== AccountMapper.save - account.netAmount: {} ===", account.getNetAmount());
        logger.info("=== AccountMapper.save - account.currency: {} ===", account.getCurrency());
        logger.info("=== AccountMapper.save - account.interestRate: {} ===", account.getInterestRate());
        logger.info("=== AccountMapper.save - account.identificationNumber: {} ===", account.getIdentificationNumber());
        logger.info("=== AccountMapper.save - account.password: {} ===", account.getPassword());
        logger.info("=== AccountMapper.save - account.lastTransaction: {} ===", account.getLastTransaction());
        logger.info("=== AccountMapper.save - account.createdDate: {} ===", account.getCreatedDate());
        logger.info("=== AccountMapper.save - account.updatedDate: {} ===", account.getUpdatedDate());
        
        logger.info("=== AccountMapper.save - Processing account: accountNumber={}, name={} ===", 
                   account.getAccountNumber(), account.getName());
        
        if (existsByAccountNumber(account.getAccountNumber())) {
            logger.info("=== AccountMapper.save - Account exists, calling update ===");
            update(account);
        } else {
            logger.info("=== AccountMapper.save - Account does not exist, calling insert ===");
            insert(account);
        }
        logger.info("=== AccountMapper.save END ===");
    }

    /**
     * 계정 생성
     * 
     * @param account 계정 정보
     */
    public void insert(Account account) {
        logger.info("=== AccountMapper.insert START ===");
        
        // Null 체크 추가
        if (account == null) {
            logger.error("=== AccountMapper.insert - account is null ===");
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        // 입력 Account 엔티티 필드값 출력
        logger.info("=== AccountMapper.insert - Input Account Field Values ===");
        logger.info("=== AccountMapper.insert - account.accountNumber: {} ===", account.getAccountNumber());
        logger.info("=== AccountMapper.insert - account.name: {} ===", account.getName());
        logger.info("=== AccountMapper.insert - account.accountType: {} ===", account.getAccountType());
        logger.info("=== AccountMapper.insert - account.status: {} ===", account.getStatus());
        logger.info("=== AccountMapper.insert - account.netAmount: {} ===", account.getNetAmount());
        logger.info("=== AccountMapper.insert - account.currency: {} ===", account.getCurrency());
        logger.info("=== AccountMapper.insert - account.interestRate: {} ===", account.getInterestRate());
        logger.info("=== AccountMapper.insert - account.identificationNumber: {} ===", account.getIdentificationNumber());
        logger.info("=== AccountMapper.insert - account.password: {} ===", account.getPassword());
        logger.info("=== AccountMapper.insert - account.lastTransaction: {} ===", account.getLastTransaction());
        logger.info("=== AccountMapper.insert - account.createdDate: {} ===", account.getCreatedDate());
        logger.info("=== AccountMapper.insert - account.updatedDate: {} ===", account.getUpdatedDate());
        
        logger.info("=== AccountMapper.insert - Inserting account: accountNumber={}, name={}, netAmount={} ===", 
                   account.getAccountNumber(), account.getName(), account.getNetAmount());
        
        String sql = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, LAST_TRANSACTION, PASSWORD, NET_AMOUNT, ACCOUNT_TYPE, STATUS, CURRENCY, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        logger.info("=== AccountMapper.insert - SQL: {} ===", sql);
        logger.info("=== AccountMapper.insert - Parameters: accountNumber={}, name={}, identificationNumber={}, interestRate={}, lastTransaction={}, password={}, netAmount={}, accountType={}, status={}, currency={}, createdDate={}, updatedDate={} ===", 
                   account.getAccountNumber(), account.getName(), account.getIdentificationNumber(), account.getInterestRate(), 
                   account.getLastTransaction(), account.getPassword(), account.getNetAmount(), account.getAccountType(), 
                   account.getStatus(), account.getCurrency(), account.getCreatedDate(), account.getUpdatedDate());
        
        int rowsAffected = jdbcTemplate.update(sql,
                account.getAccountNumber(),
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount(),
                account.getAccountType(),
                account.getStatus(),
                account.getCurrency(),
                account.getCreatedDate(),
                account.getUpdatedDate());
        
        logger.info("=== AccountMapper.insert - Rows affected: {} ===", rowsAffected);
        logger.info("=== AccountMapper.insert - Account inserted successfully ===");
        logger.info("=== AccountMapper.insert END ===");
    }

    /**
     * 계정 수정
     * 
     * @param account 계정 정보
     */
    public void update(Account account) {
        logger.info("=== AccountMapper.update START ===");
        
        // Null 체크 추가
        if (account == null) {
            logger.error("=== AccountMapper.update - account is null ===");
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        // 입력 Account 엔티티 필드값 출력
        logger.info("=== AccountMapper.update - Input Account Field Values ===");
        logger.info("=== AccountMapper.update - account.accountNumber: {} ===", account.getAccountNumber());
        logger.info("=== AccountMapper.update - account.name: {} ===", account.getName());
        logger.info("=== AccountMapper.update - account.accountType: {} ===", account.getAccountType());
        logger.info("=== AccountMapper.update - account.status: {} ===", account.getStatus());
        logger.info("=== AccountMapper.update - account.netAmount: {} ===", account.getNetAmount());
        logger.info("=== AccountMapper.update - account.currency: {} ===", account.getCurrency());
        logger.info("=== AccountMapper.update - account.interestRate: {} ===", account.getInterestRate());
        logger.info("=== AccountMapper.update - account.identificationNumber: {} ===", account.getIdentificationNumber());
        logger.info("=== AccountMapper.update - account.password: {} ===", account.getPassword());
        logger.info("=== AccountMapper.update - account.lastTransaction: {} ===", account.getLastTransaction());
        logger.info("=== AccountMapper.update - account.createdDate: {} ===", account.getCreatedDate());
        logger.info("=== AccountMapper.update - account.updatedDate: {} ===", account.getUpdatedDate());
        
        String sql = "UPDATE ACCOUNT SET NAME = ?, IDENTIFICATION_NUMBER = ?, INTEREST_RATE = ?, LAST_TRANSACTION = ?, PASSWORD = ?, NET_AMOUNT = ?, ACCOUNT_TYPE = ?, STATUS = ?, CURRENCY = ?, UPDATED_DATE = ? WHERE ACCOUNT_NUMBER = ?";
        logger.info("=== AccountMapper.update - SQL: {} ===", sql);
        logger.info("=== AccountMapper.update - Parameters: name={}, identificationNumber={}, interestRate={}, lastTransaction={}, password={}, netAmount={}, accountType={}, status={}, currency={}, updatedDate={}, accountNumber={} ===", 
                   account.getName(), account.getIdentificationNumber(), account.getInterestRate(), 
                   account.getLastTransaction(), account.getPassword(), account.getNetAmount(), account.getAccountType(), 
                   account.getStatus(), account.getCurrency(), account.getUpdatedDate(), account.getAccountNumber());
        
        int rowsAffected = jdbcTemplate.update(sql,
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount(),
                account.getAccountType(),
                account.getStatus(),
                account.getCurrency(),
                account.getUpdatedDate(),
                account.getAccountNumber());
        
        logger.info("=== AccountMapper.update - Rows affected: {} ===", rowsAffected);
        logger.info("=== AccountMapper.update - Account updated successfully ===");
        logger.info("=== AccountMapper.update END ===");
    }

    /**
     * 계정 삭제
     * 
     * @param account 계정 정보
     */
    public void delete(Account account) {
        logger.info("=== AccountMapper.delete START ===");
        
        // Null 체크 추가
        if (account == null) {
            logger.error("=== AccountMapper.delete - account is null ===");
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        // 입력 Account 엔티티 필드값 출력
        logger.info("=== AccountMapper.delete - Input Account Field Values ===");
        logger.info("=== AccountMapper.delete - account.accountNumber: {} ===", account.getAccountNumber());
        logger.info("=== AccountMapper.delete - account.name: {} ===", account.getName());
        logger.info("=== AccountMapper.delete - account.accountType: {} ===", account.getAccountType());
        logger.info("=== AccountMapper.delete - account.status: {} ===", account.getStatus());
        logger.info("=== AccountMapper.delete - account.netAmount: {} ===", account.getNetAmount());
        logger.info("=== AccountMapper.delete - account.currency: {} ===", account.getCurrency());
        logger.info("=== AccountMapper.delete - account.interestRate: {} ===", account.getInterestRate());
        logger.info("=== AccountMapper.delete - account.identificationNumber: {} ===", account.getIdentificationNumber());
        logger.info("=== AccountMapper.delete - account.password: {} ===", account.getPassword());
        logger.info("=== AccountMapper.delete - account.lastTransaction: {} ===", account.getLastTransaction());
        logger.info("=== AccountMapper.delete - account.createdDate: {} ===", account.getCreatedDate());
        logger.info("=== AccountMapper.delete - account.updatedDate: {} ===", account.getUpdatedDate());
        
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        logger.info("=== AccountMapper.delete - SQL: {} ===", sql);
        logger.info("=== AccountMapper.delete - Parameters: accountNumber={} ===", account.getAccountNumber());
        
        int rowsAffected = jdbcTemplate.update(sql, account.getAccountNumber());
        
        logger.info("=== AccountMapper.delete - Rows affected: {} ===", rowsAffected);
        logger.info("=== AccountMapper.delete - Account deleted successfully ===");
        logger.info("=== AccountMapper.delete END ===");
    }

    /**
     * 테스트 데이터 삽입 (개발용)
     */
    public void insertTestData() {
        logger.info("=== AccountMapper.insertTestData START ===");
        
        try {
            // 기존 데이터 확인
            int existingCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ACCOUNT", Integer.class);
            logger.info("=== AccountMapper.insertTestData - Existing records: {} ===", existingCount);
            
            if (existingCount == 0) {
                // 테스트 데이터 삽입
                String sql = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, PASSWORD, NET_AMOUNT, ACCOUNT_TYPE, STATUS, CURRENCY, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
                
                Object[][] testData = {
                    {"ACC001", "홍길동", "123456-1234567", 2.50, "password123", 1000000.00, "SAVINGS", "ACTIVE", "KRW"},
                    {"ACC002", "김철수", "234567-2345678", 1.80, "password456", 500000.00, "CHECKING", "ACTIVE", "KRW"},
                    {"ACC003", "이영희", "345678-3456789", 3.20, "password789", 2000000.00, "INVESTMENT", "ACTIVE", "KRW"},
                    {"ACC004", "박민수", "456789-4567890", 2.80, "password012", 5000000.00, "SAVINGS", "ACTIVE", "KRW"},
                    {"ACC005", "정수진", "567890-5678901", 1.50, "password345", 750000.00, "CHECKING", "ACTIVE", "KRW"}
                };
                
                for (Object[] data : testData) {
                    jdbcTemplate.update(sql, data);
                    logger.info("=== AccountMapper.insertTestData - Inserted: {} ===", data[0]);
                }
                
                logger.info("=== AccountMapper.insertTestData - Test data inserted successfully ===");
            } else {
                logger.info("=== AccountMapper.insertTestData - Data already exists, skipping insertion ===");
            }
            
        } catch (Exception e) {
            logger.error("=== AccountMapper.insertTestData - Error: {} ===", e.getMessage(), e);
        }
        
        logger.info("=== AccountMapper.insertTestData END ===");
    }
}