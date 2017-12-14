package com.senhome.web.order.param;

import lombok.Data;

@Data
public class OrderAddParam
{
    /**
     * 订单确认id
     */
    private String confirmId;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 用户id
     */
    private Integer accountId;
}
