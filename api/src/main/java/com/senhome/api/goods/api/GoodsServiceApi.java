package com.senhome.api.goods.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("goodsServiceApi")
public interface GoodsServiceApi
{
    ViewResult goodsDetail(Integer type, Integer goodsId);
}
