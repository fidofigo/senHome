package com.senhome.service.account.service;

import com.senhome.api.account.api.AccountServiceApi;
import com.senhome.api.account.model.AccountDTO;
import com.senhome.service.account.business.AccountBusiness;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountServiceApi")
public class AccountServiceImpl implements AccountServiceApi
{

    @Autowired
    private AccountBusiness accountBusiness;

    @Override
    public ViewResult login(String email, String pwd)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findByEmail(email);

        if(account != null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("email has been register");
            return viewResult;
        }
        else
        {
            account = new Account();
            account.setEmail(email);
            account.setPwd(pwd);

            if(accountBusiness.insertAccount(account) > 0)
            {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(account.getId());

                return ViewResult.ofSuccess().putDefaultModel(accountDTO);
            }
            else
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("login fail");
                return viewResult;
            }
        }
    }

    @Override
    public ViewResult register(String email, String pwd)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findByEmail(email);

        if(account == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("account not exist");
            return viewResult;
        }
        else
        {
            if(pwd.equals(account.getPwd()))
            {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(account.getId());

                return ViewResult.ofSuccess().putDefaultModel(accountDTO);
            }
            else
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("password error");
                return viewResult;
            }
        }
    }
}
