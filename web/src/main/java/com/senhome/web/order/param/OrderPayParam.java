package com.senhome.web.order.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class OrderPayParam extends ProtocolPojo
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
}
