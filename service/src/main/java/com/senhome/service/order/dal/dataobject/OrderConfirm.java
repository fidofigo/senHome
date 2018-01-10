package com.senhome.service.order.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class OrderConfirm extends BaseDO
{
    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 订单确认编号
     */
    private String number;

    /**
     * 总价，分为单位
     */
    private Integer totalPrice;

    /**
     * 地址id
     */
    private Integer receiveAddressId;

    /**
     * 店铺id
     */
    private Integer shopId;
}
