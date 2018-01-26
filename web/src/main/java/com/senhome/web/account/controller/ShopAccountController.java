package com.senhome.web.account.controller;

import com.senhome.api.account.api.AccountServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.account.param.AccountParam;
import com.senhome.web.interceptor.request.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/shopAccount")
public class ShopAccountController
{
    @Resource
    private AccountServiceApi accountServiceApi;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(AccountParam accountParam)
    {
        ViewResult result = accountServiceApi.shopLogin(accountParam.getMobileNumber(), accountParam.getPwd());

        return result.toJson();
    }
}
