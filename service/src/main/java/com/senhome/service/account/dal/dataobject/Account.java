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

    /**
     * 1：用户，2：商家
     */
    private Byte type;

    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 头像地址
     */
    private String head;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 店铺id, type=2时有效
     */
    private Integer shopId;
}
