package com.senhome.web.address.controller;

import com.senhome.api.address.api.AddressServiceApi;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.dal.domain.ProtocolPojo;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.address.param.AddressBaseParam;
import com.senhome.web.address.param.AddressParam;
import com.senhome.web.common.ThreadCacheUtils;
import com.senhome.web.interceptor.request.RequestContext;
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
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = addressServiceApi.addressDetail(addressBaseParam.getAddressId());

        return result.toJson();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(AddressBaseParam addressBaseParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = addressServiceApi.deleteAddress(addressBaseParam.getAddressId());

        return result.toJson();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(ProtocolPojo pojo)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = addressServiceApi.addressList(account.getId());

        return result.toJson();
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public Object change(AddressParam addressParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = addressServiceApi.changeAddress(account.getId(), addressParam.getDetail(), addressParam.getPhone(), addressParam.getName(), addressParam.getIsDefault(), addressParam.getCode(), addressParam.getAddressId());

        return result.toJson();
    }
}
