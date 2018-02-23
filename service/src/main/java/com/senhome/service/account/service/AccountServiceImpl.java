package com.senhome.service.account.service;

import com.alibaba.fastjson.JSONObject;
import com.senhome.api.account.api.AccountServiceApi;
import com.senhome.api.account.model.AccountDTO;
import com.senhome.service.account.business.AccountBusiness;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.lang.CommonUtil;
import com.senhome.shell.common.lang.GoogleMapsUtil;
import com.senhome.shell.common.lang.InviteCodeUtil;
import com.senhome.shell.common.result.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountServiceApi")
public class AccountServiceImpl implements AccountServiceApi
{
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountBusiness accountBusiness;

    @Override
    public ViewResult login(String email, String pwd)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findByEmail(email);

        return getLoginViewResult(pwd, viewResult, account);
    }

    @Override
    public ViewResult shopLogin(String mobileNumber, String pwd)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findByMobileNumber(mobileNumber);

        return getLoginViewResult(pwd, viewResult, account);
    }

    @Override
    public ViewResult findAccount(Integer accountId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findById(accountId);

        return viewResult.putDefaultModel(account);
    }

    private ViewResult getLoginViewResult(String pwd, ViewResult viewResult, Account account)
    {
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
                accountDTO.setShopId(account.getShopId());
                accountDTO.setSecretKey(account.getSecretKey());

                return viewResult.putDefaultModel(accountDTO);
            }
            else
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("password error");
                return viewResult;
            }
        }
    }

    @Override
    public ViewResult register(String email, String pwd)
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
                //生成并插入邀请码
                Account accountCode = new Account();
                accountCode.setCode(InviteCodeUtil.getInviteCode(account.getId()));
                accountCode.setId(account.getId());
                try
                {
                    accountCode.setSecretKey(CommonUtil.generateAccountSign(account.getId()));
                }
                catch (Exception e)
                {
                    viewResult.setSuccess(false);
                    viewResult.setMessage("login fail");
                    return viewResult;
                }
                accountBusiness.updateAccount(accountCode);

                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(accountCode.getId());
                accountDTO.setSecretKey(accountCode.getSecretKey());

                return viewResult.putDefaultModel(accountDTO);
            }
            else
            {
                viewResult.setSuccess(false);
                viewResult.setMessage("login fail");
                return viewResult;
            }
        }
    }
}
