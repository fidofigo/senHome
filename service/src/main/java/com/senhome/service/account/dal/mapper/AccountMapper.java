package com.senhome.service.account.dal.mapper;

import com.senhome.service.account.dal.dataobject.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper
{
    /**
     * 通过id查询账号信息
     * @param id
     * @return
     */
    Account findById(Integer id);

    /**
     * 通过邮箱查询账号信息
     * @param email
     * @return
     */
    Account findByEmail(String email);

    /**
     * 插入用户信息
     * @param account
     * @return
     */
    int insert(Account account);

    /**
     * 更新用户信息
     * @param account
     * @return
     */
    int updateAccount(Account account);
}
