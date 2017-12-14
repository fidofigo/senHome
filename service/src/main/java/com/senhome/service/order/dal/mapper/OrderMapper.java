package com.senhome.service.order.dal.mapper;

import com.senhome.service.order.dal.dataobject.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper
{
    /**
     * 查询订单
     * @param id
     * @return
     */
    Order findById(Integer id);

    /**
     * 查询用户所有订单
     * @param start
     * @param pageCount
     * @param accountId
     * @return
     */
    List<Order> findByAccountId(@Param("start") int start, @Param("pageCount") int pageCount, @Param("accountId") int accountId);

    /**
     * 查询用户不同状态的订单
     * @param accountId
     * @param type
     * @param start
     * @param pageCount
     * @return
     */
    List<Order> findByTypeAndAccountId(@Param("accountId") int accountId, @Param("type") Byte type, @Param("start") int start, @Param("pageCount") int pageCount);

    /**
     * 新增订单
     * @param order
     * @return
     */
    int insertOrder(Order order);

    /**
     * 修改订单
     * @param order
     * @return
     */
    int updateOrder(Order order);
}
