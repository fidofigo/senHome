package com.senhome.web.address.controller;

import com.senhome.api.address.api.AddressServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.address.param.AddressBaseParam;
import com.senhome.web.address.param.AddressParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/address")
public class AddressController
{
    @Resource
    private AddressServiceApi addressServiceApi;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(AddressBaseParam addressBaseParam)
    {
        ViewResult result = addressServiceApi.addressDetail(addressBaseParam.getAddressId());

        return result.toJson();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(AddressBaseParam addressBaseParam)
    {
        ViewResult result = addressServiceApi.deleteAddress(addressBaseParam.getAddressId());

        return result.toJson();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(AddressBaseParam addressBaseParam)
    {
        ViewResult result = addressServiceApi.addressList(addressBaseParam.getAccountId());

        return result.toJson();
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public Object change(AddressParam addressParam)
    {
        ViewResult result = addressServiceApi.changeAddress(addressParam.getAccountId(), addressParam.getDetail(), addressParam.getPhone(), addressParam.getName(), addressParam.getIsDefault(), addressParam.getAddressId());

        return result.toJson();
    }
}
