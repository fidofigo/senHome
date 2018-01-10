package com.senhome.web.cart.param;

import lombok.Data;

import java.util.List;

@Data
public class CartParam
{
    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 购物车id
     */
    private Integer cartId;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 购物车id列表
     */
    private List<Integer> cartIds;

    /**
     * 店铺id
     */
    private Integer shopId;
}
