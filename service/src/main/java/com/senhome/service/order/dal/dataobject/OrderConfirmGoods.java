package com.senhome.service.order.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class OrderConfirmGoods extends BaseDO
{
    /**
     * 订单确认id
     */
    private Integer orderConfirmId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 购物车id
     */
    private Integer shoppingCartId;
}
