package com.senhome.web.home.controller;

import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.home.param.HomeParam;
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
    public Object detail()
    {
        ViewResult result = homeServiceApi.homeDetail();

        return result.toJson();
    }

    @RequestMapping(value = "/goodsDetail", method = RequestMethod.POST)
    public Object goodsDetail(HomeParam homeParam)
    {
        ViewResult result = homeServiceApi.homeGoodsDetail(homeParam.getCategoryId(), homeParam.getPage(), homeParam.getPageCount());

        return result.toJson();
    }
}
