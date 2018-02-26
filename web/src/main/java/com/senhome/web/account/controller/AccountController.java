package com.senhome.web.account.controller;

import com.senhome.api.account.api.AccountServiceApi;
import com.senhome.shell.common.redis.RedisUtils;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.account.param.AccountParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CacheConfig
@RequestMapping("/appNative/account")
public class AccountController
{
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountServiceApi accountServiceApi;

    @Resource
    private RedisUtils redisUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(AccountParam accountParam)
    {
        if(!redisUtils.exists("hello"))
        {
            redisUtils.set("hello", "123", 600L);
        }
        else
        {
            String value = redisUtils.get("hello").toString();
            logger.info("login" + value);
        }
        ViewResult result = accountServiceApi.login(accountParam.getEmail(), accountParam.getPwd());

        return result.toJson();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(AccountParam accountParam)
    {
        ViewResult result = accountServiceApi.register(accountParam.getEmail(), accountParam.getPwd());

        return result.toJson();
    }
}
