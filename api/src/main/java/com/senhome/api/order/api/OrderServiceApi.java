package com.senhome.api.order.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("orderServiceApi")
public interface OrderServiceApi
{
    ViewResult orderConfirm(List<Integer> cartIds, Integer payPrice, Integer addressId, Integer accountId, Integer shopId);

    ViewResult orderAdd(String confirmId, Integer payPrice, Integer accountId);

    ViewResult orderPay(Integer orderId, Byte channel, Integer payPrice, Integer accountId);

    ViewResult orderDetail(Integer orderId);

    ViewResult updateOrder(Integer orderId, Integer type);

    ViewResult orderList(Byte type, Integer page, Integer pageCount, Integer accountId, Boolean isShop);
}
