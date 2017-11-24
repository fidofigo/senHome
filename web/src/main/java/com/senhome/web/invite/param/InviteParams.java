package com.senhome.web.invite.param;

import lombok.Data;

@Data
public class InviteParams
{
    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 邮箱地址
     */
    private String email;
}
