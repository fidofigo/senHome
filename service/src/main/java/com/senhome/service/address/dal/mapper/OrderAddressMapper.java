package com.senhome.service.address.dal.mapper;

import com.senhome.service.address.dal.dataobject.OrderAddress;
import org.springframework.stereotype.Repository;

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
}
