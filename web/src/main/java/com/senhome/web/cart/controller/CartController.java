package com.senhome.web.cart.controller;

import com.senhome.api.cart.api.CartApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.cart.param.CartParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/cart")
public class CartController
{
    @Resource
    private CartApi cartApi;

    @RequestMapping(value = "/cartCount", method = RequestMethod.POST)
    public Object cartCount(CartParam cartParam)
    {
        ViewResult result = cartApi.cartCount(cartParam.getAccountId());

        return result.toJson();
    }

    @RequestMapping(value = "/addCartGoods", method = RequestMethod.POST)
    public Object addCartGoods(CartParam cartParam)
    {
        ViewResult result = cartApi.addCartGoods(cartParam.getAccountId(), cartParam.getGoodsId(), cartParam.getGoodsCount());

        return result.toJson();
    }

    @RequestMapping(value = "/editCartGoods", method = RequestMethod.POST)
    public Object editCartGoods(CartParam cartParam)
    {
        ViewResult result = cartApi.editCartGoods(cartParam.getCartId(), cartParam.getGoodsCount());

        return result.toJson();
    }

    @RequestMapping(value = "/deleteCartGoods", method = RequestMethod.POST)
    public Object deleteCartGoods(CartParam cartParam)
    {
        ViewResult result = cartApi.deleteCartGoods(cartParam.getCartIds());

        return result.toJson();
    }

    @RequestMapping(value = "/cartGoodsList", method = RequestMethod.POST)
    public Object cartGoodsList(CartParam cartParam)
    {
        ViewResult result = cartApi.cartGoodsList(cartParam.getAccountId());

        return result.toJson();
    }
}
