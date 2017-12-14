package com.senhome.web.order.param;

import lombok.Data;

@Data
public class OrderPayParam
{
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 支付渠道
     */
    private Byte channel;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 用户id
     */
    private Integer accountId;
}
