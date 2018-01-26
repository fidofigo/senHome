package com.senhome.web.goods.controller;

import com.senhome.api.goods.api.GoodsServiceApi;
import com.senhome.shell.common.redis.RedisUtils;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.goods.param.GoodsParam;
import com.senhome.web.interceptor.request.RequestContext;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Resource
    private RedisUtils redisUtils;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object detail(GoodsParam goodsParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = goodsServiceApi.goodsDetail(1, goodsParam.getGoodsId());

        return result.toJson();
    }
}
