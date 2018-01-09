package com.senhome.api.shop.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

@Component("shopServiceApi")
public interface ShopServiceApi
{
    ViewResult shopDetail(Integer shopId);

    ViewResult shopOperation(Integer shopId, Integer isOpen);
}
