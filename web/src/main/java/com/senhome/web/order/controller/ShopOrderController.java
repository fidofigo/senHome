package com.senhome.web.order.controller;

import com.senhome.api.order.api.OrderServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.order.param.OrderDetailParam;
import com.senhome.web.order.param.OrderListParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/appNative/shopOrder")
public class ShopOrderController
{
    @Resource
    private OrderServiceApi orderServiceApi;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object orderUpdate(OrderDetailParam orderDetailParam)
    {
        ViewResult result = orderServiceApi.updateOrder(orderDetailParam.getOrderId(), orderDetailParam.getType());

        return result.toJson();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object orderDetail(OrderDetailParam orderDetailParam)
    {
        ViewResult result = orderServiceApi.orderDetail(orderDetailParam.getOrderId());

        return result.toJson();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(OrderListParam orderListParam)
    {
        ViewResult result = orderServiceApi.orderList(orderListParam.getType(), orderListParam.getPage(), orderListParam.getPageCount(), orderListParam.getAccountId(), true);

        return result.toJson();
    }
}
