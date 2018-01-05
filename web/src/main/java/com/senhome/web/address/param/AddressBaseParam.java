package com.senhome.web.address.param;

import lombok.Data;

@Data
public class AddressBaseParam
{
    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 地址id
     */
    private Integer addressId;
}
