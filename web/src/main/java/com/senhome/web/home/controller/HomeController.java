package com.senhome.web.home.controller;

import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.home.param.HomeBaseParam;
import com.senhome.web.home.param.HomeParam;
import com.senhome.web.interceptor.request.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/home")
public class HomeController
{
    @Resource
    private HomeServiceApi homeServiceApi;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(HomeBaseParam homeBaseParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = homeServiceApi.homeDetail(homeBaseParam.getAddressId());

        return result.toJson();
    }

    @RequestMapping(value = "/goodsDetail", method = RequestMethod.POST)
    public Object goodsDetail(HomeParam homeParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = homeServiceApi.homeGoodsDetail(1, homeParam.getCategoryId(), homeParam.getShopId(), homeParam.getPage(), homeParam.getPageCount());

        return result.toJson();
    }
}
