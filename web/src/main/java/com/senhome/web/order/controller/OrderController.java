package com.senhome.web.order.controller;

import com.senhome.api.order.api.OrderServiceApi;
import com.senhome.shell.common.result.ViewResult;
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
        ViewResult result = orderServiceApi.orderConfirm(orderConfirmParam.getCartIds(), orderConfirmParam.getPayPrice(), orderConfirmParam.getAddressId(), orderConfirmParam.getAccountId());

        return result.toJson();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object orderAdd(OrderAddParam orderAddParam)
    {
        ViewResult result = orderServiceApi.orderAdd(orderAddParam.getConfirmId(), orderAddParam.getPayPrice(), orderAddParam.getAccountId());

        return result.toJson();
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Object orderPay(OrderPayParam orderPayParam)
    {
        ViewResult result = orderServiceApi.orderPay(orderPayParam.getOrderId(), orderPayParam.getChannel(), orderPayParam.getPayPrice(), orderPayParam.getAccountId());

        return result.toJson();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object orderDetail(OrderDetailParam orderDetailParam)
    {
        ViewResult result = orderServiceApi.orderDetail(orderDetailParam.getOrderId());

        return result.toJson();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object orderList(OrderListParam orderListParam)
    {
        ViewResult result = orderServiceApi.orderList(orderListParam.getType(), orderListParam.getPage(), orderListParam.getPageCount(), orderListParam.getAccountId());

        return result.toJson();
    }
}
