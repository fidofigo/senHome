package com.senhome.web.account.param;

import lombok.Data;

@Data
public class AccountParam
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
     * 手机号
     */
    private String mobileNumber;
}
