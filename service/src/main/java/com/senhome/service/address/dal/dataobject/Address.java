package com.senhome.service.address.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Address extends BaseDO
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
     * 是否默认；0：否，1：是
     */
    private Byte isDefault;
}
