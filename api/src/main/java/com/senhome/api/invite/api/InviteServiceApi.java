package com.senhome.api.invite.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("inviteServiceApi")
public interface InviteServiceApi
{
    ViewResult invite(Integer accountId, String email);
}
