package com.senhome.service.cart.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Cart extends BaseDO
{
    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 店铺id
     */
    private Integer shopId;
}
