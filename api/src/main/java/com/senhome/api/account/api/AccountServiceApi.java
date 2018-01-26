package com.senhome.api.account.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("accountServiceApi")
public interface AccountServiceApi
{
    ViewResult login(String email, String pwd);

    ViewResult register(String email, String pwd);

    ViewResult shopLogin(String mobileNumber, String pwd);

    ViewResult findAccount(Integer accountId);
}
