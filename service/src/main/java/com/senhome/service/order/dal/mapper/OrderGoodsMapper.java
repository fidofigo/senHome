package com.senhome.service.order.dal.mapper;

import com.senhome.service.order.dal.dataobject.OrderGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderGoodsMapper
{
    /**
     * 查询订单商品
     * @param orderId
     * @return
     */
    List<OrderGoods> findByOrderId(Integer orderId);

    /**
     * 批量查询订单商品
     * @param orderIds
     * @return
     */
    List<OrderGoods> findByOrderIdList(List<Integer> orderIds);

    /**
     * 新增订单商品
     * @param orderGoods
     * @return
     */
    int insertOrderGoods(OrderGoods orderGoods);
}
