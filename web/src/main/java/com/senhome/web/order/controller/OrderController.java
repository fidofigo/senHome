package com.senhome.web.order.controller;

import com.senhome.api.order.api.OrderServiceApi;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.common.ThreadCacheUtils;
import com.senhome.web.interceptor.request.RequestContext;
import com.senhome.web.order.param.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/order")
public class OrderController
{
    @Resource
    private OrderServiceApi orderServiceApi;

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public Object orderConfirm(OrderConfirmParam orderConfirmParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = orderServiceApi.orderConfirm(orderConfirmParam.getCartIds(), orderConfirmParam.getPayPrice(), orderConfirmParam.getAddressId(), orderConfirmParam.getAccountId(), orderConfirmParam.getShopId());

        return result.toJson();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object orderAdd(OrderAddParam orderAddParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = orderServiceApi.orderAdd(orderAddParam.getConfirmId(), orderAddParam.getPayPrice(), account.getId());

        return result.toJson();
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Object orderPay(OrderPayParam orderPayParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = orderServiceApi.orderPay(orderPayParam.getOrderId(), orderPayParam.getChannel(), orderPayParam.getPayPrice(), account.getId());

        return result.toJson();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object orderDetail(OrderDetailParam orderDetailParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        ViewResult result = orderServiceApi.orderDetail(orderDetailParam.getOrderId(), false);

        return result.toJson();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object orderList(OrderListParam orderListParam)
    {
        if(!RequestContext.get().isLogin())
        {
            ViewResult viewResult = ViewResult.ofFail();

            viewResult.setMessage("account not login");
            return viewResult;
        }

        Account account = ThreadCacheUtils.getAccount();

        ViewResult result = orderServiceApi.orderList(orderListParam.getType(), orderListParam.getPage(), orderListParam.getPageCount(), account.getId(), false);

        return result.toJson();
    }
}
