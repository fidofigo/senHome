package com.senhome.api.cart.model;

import lombok.Data;

@Data
public class CartGoodsDetailDTO
{
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
    private Integer count;

    /**
     * 商品加购限制
     */
    private Integer limit;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String image;
}
