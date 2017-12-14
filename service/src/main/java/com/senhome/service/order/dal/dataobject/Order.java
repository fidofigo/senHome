package com.senhome.service.order.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

import java.util.Date;

@Data
public class Order extends BaseDO
{
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 总价，分为单位
     */
    private Integer totalPrice;

    /**
     * 订单状态；1：待付款，2：待配送，3：配送中，4：交易成功，5：已取消
     */
    private Byte type;

    /**
     * 支付渠道；1：信用卡
     */
    private Byte payChannel;

    /**
     * 收货地址id
     */
    private Integer receiveAddressId;

    /**
     * 付款时间
     */
    private Date payTime;
}
