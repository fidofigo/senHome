package com.senhome.service.order.dal.mapper;

import com.senhome.service.order.dal.dataobject.OrderConfirm;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderConfirmMapper
{
    /**
     * 通过订单确认编号查找订单
     * @param number
     * @return
     */
    OrderConfirm findByNumber(String number);

    /**
     * 新增订单确认表
     * @param orderConfirm
     * @return
     */
    int insertOrderConfirm(OrderConfirm orderConfirm);
}
