package com.senhome.service.invite.business;

import com.senhome.service.invite.dal.dataobject.Invite;
import com.senhome.service.invite.dal.mapper.InviteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteBusiness
{
    @Autowired
    private InviteMapper inviteMapper;

    public List<Invite> findInviteByAccountId(Integer accountId)
    {
        if(accountId == null)
        {
            return null;
        }

        return inviteMapper.findByAccountId(accountId);
    }

    public int insertInvite(Invite invite)
    {
        if(invite == null)
        {
            return 0;
        }

        return inviteMapper.insert(invite);
    }

}
