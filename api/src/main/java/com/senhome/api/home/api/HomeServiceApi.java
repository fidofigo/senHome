package com.senhome.api.home.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("homeServiceApi")
public interface HomeServiceApi
{
    ViewResult homeDetail();

    ViewResult homeGoodsDetail(Integer categoryId, Integer page, Integer pageCount);
}
