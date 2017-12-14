package com.senhome.service.order.business;

import com.senhome.service.order.dal.dataobject.Order;
import com.senhome.service.order.dal.dataobject.OrderConfirm;
import com.senhome.service.order.dal.dataobject.OrderConfirmGoods;
import com.senhome.service.order.dal.dataobject.OrderGoods;
import com.senhome.service.order.dal.mapper.OrderConfirmGoodsMapper;
import com.senhome.service.order.dal.mapper.OrderConfirmMapper;
import com.senhome.service.order.dal.mapper.OrderGoodsMapper;
import com.senhome.service.order.dal.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class OrderBusiness
{
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderConfirmMapper orderConfirmMapper;

    @Autowired
    private OrderConfirmGoodsMapper orderConfirmGoodsMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    public Order findOrderById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return orderMapper.findById(id);
    }

    public List<Order> findOrderByAccountIdAndType(Integer accountId, int page, int pageCount, Byte type)
    {
        if(accountId == null)
        {
            return Collections.emptyList();
        }

        if(type == 0)
        {
            return orderMapper.findByAccountId((page - 1) * pageCount, pageCount, accountId);
        }
        else
        {
            return orderMapper.findByTypeAndAccountId(accountId, type, (page - 1) * pageCount, pageCount);
        }
    }

    public Integer insertOrder(Order order)
    {
        if(order == null)
        {
            return 0;
        }

        return orderMapper.insertOrder(order);
    }

    public Integer updateOrder(Order order)
    {
        if(order == null)
        {
            return 0;
        }

        return orderMapper.updateOrder(order);
    }

    public List<OrderGoods> findOrderGoodsByOrderId(Integer orderId)
    {
        if(orderId == null)
        {
            return Collections.emptyList();
        }

        return orderGoodsMapper.findByOrderId(orderId);
    }

    public List<OrderGoods> findOrderGoodsByOrderId(List<Integer> orderIds)
    {
        if(CollectionUtils.isEmpty(orderIds))
        {
            return null;
        }

        return orderGoodsMapper.findByOrderIdList(orderIds);
    }

    public Integer insertOrderGoods(OrderGoods orderGoods)
    {
        if(orderGoods == null)
        {
            return 0;
        }

        return orderGoodsMapper.insertOrderGoods(orderGoods);
    }

    public OrderConfirm findOrderConfirmByNumber(String number)
    {
        if(number == null)
        {
            return null;
        }

        return orderConfirmMapper.findByNumber(number);
    }

    public Integer insertOrderConfirm(OrderConfirm orderConfirm)
    {
        if(orderConfirm == null)
        {
            return 0;
        }

        return orderConfirmMapper.insertOrderConfirm(orderConfirm);
    }


    public List<OrderConfirmGoods> getOrderConfirmGoodsList(Integer orderConfirmId)
    {
        if(orderConfirmId == null)
        {
            return Collections.emptyList();
        }

        return orderConfirmGoodsMapper.findByOrderConfirmId(orderConfirmId);
    }

    public Integer insertOrderConfirmGoods(OrderConfirmGoods orderConfirmGoods)
    {
        if(orderConfirmGoods == null)
        {
            return 0;
        }

        return orderConfirmGoodsMapper.insertOrderConfirmGoods(orderConfirmGoods);
    }
}
