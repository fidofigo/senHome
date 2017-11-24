package com.senhome.web.invite.controller;

import com.senhome.api.invite.api.InviteServiceApi;
import com.senhome.shell.common.result.ViewResult;
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
        ViewResult result = inviteServiceApi.invite(inviteParams.getAccountId(), inviteParams.getEmail());

        return result.toJson();
    }
}
