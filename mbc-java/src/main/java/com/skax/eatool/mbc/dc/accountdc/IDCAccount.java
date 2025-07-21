package com.skax.eatool.mbc.dc.accountdc;

import com.skax.eatool.mbc.dc.accountdc.dto.AccountDDTO;

/**
 * IDCAccount interface
 */
public interface IDCAccount {

    AccountDDTO selectAccount(String accountId) throws Exception;

    void insertAccount(Account account) throws Exception;

    void updateAccount(Account account) throws Exception;

    void deleteAccount(String accountId) throws Exception;
}