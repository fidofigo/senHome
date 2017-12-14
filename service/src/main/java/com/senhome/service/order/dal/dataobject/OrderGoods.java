package com.senhome.service.order.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class OrderGoods extends BaseDO
{
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 单价，分为单位
     */
    private Integer salesPrice;

    /**
     * 商品数量
     */
    private Integer count;
}
