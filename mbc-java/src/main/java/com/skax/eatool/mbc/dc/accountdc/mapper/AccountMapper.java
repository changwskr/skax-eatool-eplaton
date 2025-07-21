package com.skax.eatool.mbc.dc.accountdc.mapper;

import java.util.List;
import java.util.Optional;

import com.skax.eatool.mbc.dc.accountdc.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        account.setNetAmount(rs.getString("NET_AMOUNT"));
        return account;
    };

    /**
     * 계좌번호로 계정 조회
     * 
     * @param accountNumber 계좌번호
     * @return 계정 정보
     */
    public Optional<Account> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        List<Account> accounts = jdbcTemplate.query(sql, accountRowMapper, accountNumber);
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
    }

    /**
     * 계정 존재 여부 확인
     * 
     * @param accountNumber 계좌번호
     * @return 존재 여부
     */
    public boolean existsByAccountNumber(String accountNumber) {
        String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, accountNumber);
        return count > 0;
    }

    /**
     * 계정 타입별 조회
     * 
     * @param accountType 계정 타입
     * @return 계정 목록
     */
    public List<Account> findByAccountType(String accountType) {
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_TYPE = ?";
        return jdbcTemplate.query(sql, accountRowMapper, accountType);
    }

    /**
     * 상태별 계정 조회
     * 
     * @param status 상태
     * @return 계정 목록
     */
    public List<Account> findByStatus(String status) {
        String sql = "SELECT * FROM ACCOUNT WHERE STATUS = ?";
        return jdbcTemplate.query(sql, accountRowMapper, status);
    }

    /**
     * 통화별 계정 조회
     * 
     * @param currency 통화
     * @return 계정 목록
     */
    public List<Account> findByCurrency(String currency) {
        String sql = "SELECT * FROM ACCOUNT WHERE CURRENCY = ?";
        return jdbcTemplate.query(sql, accountRowMapper, currency);
    }

    /**
     * 잔액 범위로 계정 조회
     * 
     * @param minBalance 최소 잔액
     * @param maxBalance 최대 잔액
     * @return 계정 목록
     */
    public List<Account> findByBalanceBetween(Double minBalance, Double maxBalance) {
        String sql = "SELECT * FROM ACCOUNT WHERE CAST(NET_AMOUNT AS DECIMAL) BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, accountRowMapper, minBalance, maxBalance);
    }

    /**
     * 계좌번호로 부분 검색
     * 
     * @param accountNumber 계좌번호 (부분)
     * @return 계정 목록
     */
    public List<Account> findByAccountNumberContaining(String accountNumber) {
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER LIKE ?";
        return jdbcTemplate.query(sql, accountRowMapper, "%" + accountNumber + "%");
    }

    /**
     * 모든 계정 조회
     * 
     * @return 계정 목록
     */
    public List<Account> findAll() {
        String sql = "SELECT * FROM ACCOUNT";
        return jdbcTemplate.query(sql, accountRowMapper);
    }

    /**
     * 계정 저장 (생성 또는 수정)
     * 
     * @param account 계정 정보
     */
    public void save(Account account) {
        if (existsByAccountNumber(account.getAccountNumber())) {
            update(account);
        } else {
            insert(account);
        }
    }

    /**
     * 계정 생성
     * 
     * @param account 계정 정보
     */
    public void insert(Account account) {
        String sql = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, NAME, IDENTIFICATION_NUMBER, INTEREST_RATE, LAST_TRANSACTION, PASSWORD, NET_AMOUNT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                account.getAccountNumber(),
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount());
    }

    /**
     * 계정 수정
     * 
     * @param account 계정 정보
     */
    public void update(Account account) {
        String sql = "UPDATE ACCOUNT SET NAME = ?, IDENTIFICATION_NUMBER = ?, INTEREST_RATE = ?, LAST_TRANSACTION = ?, PASSWORD = ?, NET_AMOUNT = ? WHERE ACCOUNT_NUMBER = ?";
        jdbcTemplate.update(sql,
                account.getName(),
                account.getIdentificationNumber(),
                account.getInterestRate(),
                account.getLastTransaction(),
                account.getPassword(),
                account.getNetAmount(),
                account.getAccountNumber());
    }

    /**
     * 계정 삭제
     * 
     * @param account 계정 정보
     */
    public void delete(Account account) {
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";
        jdbcTemplate.update(sql, account.getAccountNumber());
    }
}