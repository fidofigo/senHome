package com.senhome.api.order.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderConfirmDTO
{
    /**
     * 商品列表
     */
    private List<OrderGoodsDetailDTO> orderGoods;

    /**
     * 地址详情
     */
    private String addressDetail;

    /**
     * 收货手机号
     */
    private String mobileNumber;

    /**
     * 订单总价
     */
    private String totalPrice;

    /**
     * 订单新增时需要传输该参数
     */
    private String confirmId;
}
