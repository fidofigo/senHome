package com.senhome.web.order.param;

import lombok.Data;

@Data
public class OrderDetailParam
{
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单状态
     */
    private Integer type;
}
