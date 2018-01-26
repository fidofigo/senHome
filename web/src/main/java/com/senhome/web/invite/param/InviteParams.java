package com.senhome.web.invite.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class InviteParams extends ProtocolPojo
{
    /**
     * 邮箱地址
     */
    private String email;
}
