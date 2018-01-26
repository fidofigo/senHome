package com.senhome.web.order.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class OrderAddParam extends ProtocolPojo
{
    /**
     * 订单确认id
     */
    private String confirmId;

    /**
     * 支付金额
     */
    private Integer payPrice;
}
