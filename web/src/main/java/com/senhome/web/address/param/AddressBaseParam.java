package com.senhome.web.address.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class AddressBaseParam extends ProtocolPojo
{
    /**
     * 地址id
     */
    private Integer addressId;
}
