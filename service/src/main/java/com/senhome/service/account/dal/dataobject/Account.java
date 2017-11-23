package com.senhome.service.account.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.*;

@Data
public class Account extends BaseDO
{
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 邀请码
     */
    private String code;
}
