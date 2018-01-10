package com.senhome.api.order.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO
{
    /**
     * 订单金额
     */
    private String payPrice;

    /**
     * 订单商品
     */
    private List<OrderGoodsDetailDTO> orderGoods;

    /**
     * 订单地址详情
     */
    private String addressDetail;

    /**
     * 订单是否支付
     */
    private Integer isPay;

    /**
     * 订单状态
     */
    private Byte type;

    /**
     * 收益
     */
    private Integer income;
}
