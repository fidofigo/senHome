package com.senhome.api.cart.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cartApi")
public interface CartApi
{
    ViewResult cartCount(Integer accountId);

    ViewResult addCartGoods(Integer accountId, Integer goodsId, Integer goodsCount);

    ViewResult editCartGoods( Integer cartId, Integer modifyCount);

    ViewResult deleteCartGoods(List<Integer> cartIds);

    ViewResult cartGoodsList(Integer accountId);
}
