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
    private String income;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 地址编码
     */
    private Integer code;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺图片
     */
    private String shopImage;

    /**
     * 创建时间
     */
    private String createTime;
}
