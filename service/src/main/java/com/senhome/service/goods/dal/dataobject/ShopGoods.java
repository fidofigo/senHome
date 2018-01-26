package com.senhome.service.goods.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class ShopGoods extends BaseDO
{
    /**
     * 基础商品id
     */
    private Integer goodsBaseId;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 锁定库存
     */
    private Integer lock;

    /**
     * 名称
     */
    private String name;
}
