package com.senhome.web.invite.controller;

import com.senhome.api.invite.api.InviteServiceApi;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.common.ThreadCacheUtils;
import com.senhome.web.interceptor.request.RequestContext;
import com.senhome.web.invite.param.InviteParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/invite")
public class InviteController
{
    @Resource
    private InviteServiceApi inviteServiceApi;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(InviteParams inviteParams)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = inviteServiceApi.invite(account.getId(), inviteParams.getEmail());

        return result.toJson();
    }
}
