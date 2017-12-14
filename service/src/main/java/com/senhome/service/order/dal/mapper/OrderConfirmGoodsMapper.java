package com.senhome.service.order.dal.mapper;

import com.senhome.service.order.dal.dataobject.OrderConfirmGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderConfirmGoodsMapper
{
    /**
     * 查询确认订单商品
     * @param orderConfirmId
     * @return
     */
    List<OrderConfirmGoods> findByOrderConfirmId(Integer orderConfirmId);

    /**
     * 新增确认订单商品
     * @param orderConfirmGoods
     * @return
     */
    int insertOrderConfirmGoods(OrderConfirmGoods orderConfirmGoods);
}
