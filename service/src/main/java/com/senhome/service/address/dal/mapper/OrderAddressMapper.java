package com.senhome.service.address.dal.mapper;

import com.senhome.service.address.dal.dataobject.OrderAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAddressMapper
{
    /**
     * 查询收货地址详情
     * @param id
     * @return
     */
    OrderAddress findById(Integer id);

    /**
     * 新增订单收货地址
     * @param orderAddress
     * @return
     */
    int insertOrderAddress(OrderAddress orderAddress);

    /**
     * 根据收货地址id列表获取订单地址列表
     * @param ids
     * @return
     */
    List<OrderAddress> findOrderAddressByIds(List<Integer> ids);
}
