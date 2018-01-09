package com.senhome.api.address.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("addressServiceApi")
public interface AddressServiceApi
{
    ViewResult addressDetail(Integer addressId);

    ViewResult deleteAddress(Integer addressId);

    ViewResult changeAddress(Integer accountId, String detailAddress, String phone, String name, Integer isDefault, Integer addressId);

    ViewResult addressList(Integer accountId);
}
