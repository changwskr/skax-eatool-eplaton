package com.skax.eatool.mbc.pc.accountpc;

import com.skax.eatool.mbc.pc.dto.AccountPDTO;

/**
 * IPCAccount interface
 */
public interface IPCAccount {

    AccountPDTO selectAccount(String accountId) throws Exception;

    void insertAccount(AccountPDTO account) throws Exception;

    void updateAccount(AccountPDTO account) throws Exception;

    void deleteAccount(String accountId) throws Exception;
}