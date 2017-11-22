package com.senhome.web.goods.controller;

import com.senhome.api.goods.api.GoodsServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.goods.param.GoodsParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/goods")
public class GoodsController
{
    @Resource
    private GoodsServiceApi goodsServiceApi;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object login(GoodsParam goodsParam)
    {
        ViewResult result = goodsServiceApi.goodsDetail(goodsParam.getGoodsId());

        return result.toJson();
    }
}
