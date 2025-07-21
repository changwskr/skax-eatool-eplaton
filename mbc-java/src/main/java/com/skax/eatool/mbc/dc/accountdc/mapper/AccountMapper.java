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
        account.setInterestRate(rs.getFloat("INTEREST_RATE"));
        account.setLastTransaction(rs.getTimestamp("LAST_TRANSACTION"));
        account.setPassword(rs.getString("PASSWORD"));
        account.setNetAmount(rs.getDouble("NET_AMOUNT"));
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
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, accountNumber);
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
        String sql = "SELECT * FROM ACCOUNT";
        List<Account> result = jdbcTemplate.query(sql, accountRowMapper);
        logger.info("=== AccountMapper.findAll END ===");
        return result;
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
        
        logger.info("=== AccountMapper.save - Processing account: accountNumber={}, name={} ===", 
                   account.getAccountNumber(), account.getName());
        
        if (existsByAccountNumber(account.getAccountNumber())) {
            update(account);
        } else {
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
        
        logger.info("=== AccountMapper.insert - Inserting account: accountNumber={}, name={}, netAmount={} ===", 
                   account.getAccountNumber(), account.getName(), account.getNetAmount());
        
        String sql = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, LAST_TRANSACTION, PASSWORD, NET_AMOUNT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                account.getAccountNumber(),
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount());
        logger.info("=== AccountMapper.insert END ===");
    }

    /**
     * 계정 수정
     * 
     * @param account 계정 정보
     */
    public void update(Account account) {
        logger.info("=== AccountMapper.update START ===");
        String sql = "UPDATE ACCOUNT SET NAME = ?, IDENTIFICATION_NUMBER = ?, INTEREST_RATE = ?, LAST_TRANSACTION = ?, PASSWORD = ?, NET_AMOUNT = ? WHERE ACCOUNT_NUMBER = ?";
        jdbcTemplate.update(sql,
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount(),
                account.getAccountNumber());
        logger.info("=== AccountMapper.update END ===");
    }

    /**
     * 계정 삭제
     * 
     * @param account 계정 정보
     */
    public void delete(Account account) {
        logger.info("=== AccountMapper.delete START ===");
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        jdbcTemplate.update(sql, account.getAccountNumber());
        logger.info("=== AccountMapper.delete END ===");
    }
}