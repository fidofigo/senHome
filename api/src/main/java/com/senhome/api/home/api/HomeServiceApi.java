package com.senhome.api.home.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("homeServiceApi")
public interface HomeServiceApi
{
    ViewResult homeDetail(Integer addressId);

    ViewResult homeGoodsDetail(Integer type, Integer categoryId, Integer shopId, Integer page, Integer pageCount);
}
