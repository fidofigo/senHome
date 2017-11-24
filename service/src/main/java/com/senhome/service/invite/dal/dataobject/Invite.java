package com.senhome.service.invite.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Invite extends BaseDO
{
    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 邮箱地址
     */
    private String email;
}
