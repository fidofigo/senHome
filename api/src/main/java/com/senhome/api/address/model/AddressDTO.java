package com.senhome.api.address.model;

import lombok.Data;

@Data
public class AddressDTO
{
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 姓名
     */
    private String name;

    /**
     * 地址编码
     */
    private Integer code;

    /**
     * 是否默认；0：否，1：是
     */
    private Byte isDefault;

    /**
     * 地址id
     */
    private Integer id;
}
