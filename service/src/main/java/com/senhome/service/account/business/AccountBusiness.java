package com.senhome.service.account.business;

import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.service.account.dal.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBusiness
{
    @Autowired
    private AccountMapper accountMapper;

    public Account findById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return accountMapper.findById(id);
    }

    public Account findByEmail(String email)
    {
        if(email == null)
        {
            return null;
        }

        return accountMapper.findByEmail(email);
    }

    public int insertAccount(Account account)
    {
        if(account == null)
        {
            return 0;
        }

        return accountMapper.insert(account);
    }

    public int updateAccount(Account account)
    {
        if(account == null)
        {
            return 0;
        }

        return accountMapper.updateAccount(account);
    }
}
