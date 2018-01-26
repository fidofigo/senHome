package com.senhome.web.address.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class AddressParam extends ProtocolPojo
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
     * 地址编码
     */
    private Integer code;

    /**
     * 是否默认；0：否，1：是
     */
    private Integer isDefault;

    /**
     * 地址id
     */
    private Integer addressId;
}
