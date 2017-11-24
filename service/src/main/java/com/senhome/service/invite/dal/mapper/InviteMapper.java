package com.senhome.service.invite.dal.mapper;

import com.senhome.service.invite.dal.dataobject.Invite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteMapper
{
    /**
     * 查找用户邀请列表
     * @param accountId
     * @return
     */
    List<Invite> findByAccountId(Integer accountId);

    /**
     * 插入邀请信息
     * @param invite
     * @return
     */
    int insert(Invite invite);
}
