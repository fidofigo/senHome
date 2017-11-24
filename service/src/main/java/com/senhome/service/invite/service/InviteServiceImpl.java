package com.senhome.service.invite.service;

import com.senhome.api.invite.api.InviteServiceApi;
import com.senhome.service.account.business.AccountBusiness;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.service.invite.business.InviteBusiness;
import com.senhome.service.invite.dal.dataobject.Invite;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteServiceImpl implements InviteServiceApi
{
    @Autowired
    private InviteBusiness inviteBusiness;

    @Autowired
    private AccountBusiness accountBusiness;

    @Override
    public ViewResult invite(Integer accountId, String email)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Account account = accountBusiness.findById(accountId);

        if(account == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("please login");
            return viewResult;
        }

        List<Invite> inviteList = inviteBusiness.findInviteByAccountId(accountId);

        if(inviteList != null && inviteList.size() > 0)
        {
            for(Invite invite : inviteList)
            {
                if(invite.getEmail().equals(email))
                {
                    viewResult.setSuccess(false);
                    viewResult.setMessage("has been invited");
                    return viewResult;
                }
            }
        }

        Invite invited = new Invite();
        invited.setAccountId(accountId);
        invited.setEmail(email);
        inviteBusiness.insertInvite(invited);

        return viewResult;
    }
}
