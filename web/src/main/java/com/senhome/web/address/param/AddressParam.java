package com.senhome.web.address.param;

import lombok.Data;

@Data
public class AddressParam
{
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 姓名
     */
    private String name;

    /**
     * 是否默认；0：否，1：是
     */
    private Integer isDefault;

    /**
     * 地址id
     */
    private Integer addressId;
}
