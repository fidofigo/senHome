package com.senhome.web.home.controller;

import com.senhome.api.goods.api.GoodsServiceApi;
import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.goods.param.GoodsParam;
import com.senhome.web.home.param.HomeParam;
import com.senhome.web.interceptor.request.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/shopGoods")
public class ShopGoodsController
{
    @Resource
    private HomeServiceApi homeServiceApi;

    @Resource
    private GoodsServiceApi goodsServiceApi;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(HomeParam homeParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = homeServiceApi.homeGoodsDetail(2, homeParam.getCategoryId(), homeParam.getShopId(), homeParam.getPage(), homeParam.getPageCount());

        return result.toJson();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(GoodsParam goodsParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = goodsServiceApi.goodsDetail(2, goodsParam.getGoodsId());

        return result.toJson();
    }
}
