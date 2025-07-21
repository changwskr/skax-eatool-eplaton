package com.skax.eatool.mbc.dc.accountdc.repository;

import java.util.List;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.dc.accountdc.Account;
import com.skax.eatool.mbc.dc.accountdc.dto.AccountDDTO;

/**
 * 계정 관리 공통 인터페이스
 * 
 * 프로그램명: AccountRepository.java
 * 설명: 계정 관리 관련 데이터베이스 작업을 위한 공통 인터페이스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 계정 CRUD 작업
 * - 계정 조회 및 검색
 * 
 * @version 1.0
 */
public interface AccountRepository {

    /**
     * 계정 조회
     * 
     * @param accountDDTO 계정 조회 조건
     * @return 계정 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    Account getAccount(AccountDDTO accountDDTO) throws NewBusinessException;

    /**
     * 계정 목록 조회
     * 
     * @param accountDDTO 계정 조회 조건
     * @return 계정 목록
     * @throws NewBusinessException 비즈니스 예외
     */
    List<Account> getListAccount(AccountDDTO accountDDTO) throws NewBusinessException;

    /**
     * 계정 생성
     * 
     * @param accountDDTO 계정 정보
     * @return 생성된 행 수
     * @throws NewBusinessException 비즈니스 예외
     */
    int createAccount(AccountDDTO accountDDTO) throws NewBusinessException;

    /**
     * 계정 수정
     * 
     * @param accountDDTO 계정 정보
     * @return 수정된 행 수
     * @throws NewBusinessException 비즈니스 예외
     */
    int updateAccount(AccountDDTO accountDDTO) throws NewBusinessException;

    /**
     * 계정 삭제
     * 
     * @param accountDDTO 계정 정보
     * @return 삭제된 행 수
     * @throws NewBusinessException 비즈니스 예외
     */
    int deleteAccount(AccountDDTO accountDDTO) throws NewBusinessException;

    /**
     * 계좌번호로 계정 조회
     * 
     * @param accountNumber 계좌번호
     * @return 계정 정보
     * @throws NewBusinessException 비즈니스 예외
     */
    Account getAccountByNumber(String accountNumber) throws NewBusinessException;

    /**
     * 계정 존재 여부 확인
     * 
     * @param accountNumber 계좌번호
     * @return 존재 여부 (1: 존재, 0: 존재하지 않음)
     * @throws NewBusinessException 비즈니스 예외
     */
    int existsAccount(String accountNumber) throws NewBusinessException;

    /**
     * 잔액 업데이트
     * 
     * @param accountNumber 계좌번호
     * @param netAmount     잔액
     * @return 업데이트된 행 수
     * @throws NewBusinessException 비즈니스 예외
     */
    int updateBalance(String accountNumber, double netAmount) throws NewBusinessException;

    /**
     * 마지막 거래일 업데이트
     * 
     * @param accountNumber   계좌번호
     * @param lastTransaction 마지막 거래일
     * @return 업데이트된 행 수
     * @throws NewBusinessException 비즈니스 예외
     */
    int updateLastTransaction(String accountNumber, String lastTransaction) throws NewBusinessException;
}