package com.senhome.web.shop.controller;

import com.senhome.api.shop.api.ShopServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.interceptor.request.RequestContext;
import com.senhome.web.shop.param.ShopParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/shop")
public class ShopController
{
    @Resource
    private ShopServiceApi shopServiceApi;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(ShopParam shopParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = shopServiceApi.shopDetail(shopParam.getShopId());

        return result.toJson();
    }

    @RequestMapping(value = "/operation", method = RequestMethod.POST)
    public Object operation(ShopParam shopParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = shopServiceApi.shopOperation(shopParam.getShopId(), shopParam.getIsOpen());

        return result.toJson();
    }
}
