package com.senhome.web.cart.controller;

import com.senhome.api.cart.api.CartApi;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.cart.param.CartParam;
import com.senhome.web.common.ThreadCacheUtils;
import com.senhome.web.interceptor.request.RequestContext;
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
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = cartApi.cartCount(account.getId(), cartParam.getShopId());

        return result.toJson();
    }

    @RequestMapping(value = "/addCartGoods", method = RequestMethod.POST)
    public Object addCartGoods(CartParam cartParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = cartApi.addCartGoods(account.getId(), cartParam.getGoodsId(), cartParam.getGoodsCount(), cartParam.getShopId());

        return result.toJson();
    }

    @RequestMapping(value = "/editCartGoods", method = RequestMethod.POST)
    public Object editCartGoods(CartParam cartParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = cartApi.editCartGoods(cartParam.getCartId(), cartParam.getGoodsCount());

        return result.toJson();
    }

    @RequestMapping(value = "/deleteCartGoods", method = RequestMethod.POST)
    public Object deleteCartGoods(CartParam cartParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = cartApi.deleteCartGoods(cartParam.getCartIds());

        return result.toJson();
    }

    @RequestMapping(value = "/cartGoodsList", method = RequestMethod.POST)
    public Object cartGoodsList(CartParam cartParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = cartApi.cartGoodsList(account.getId(), cartParam.getShopId());

        return result.toJson();
    }
}
