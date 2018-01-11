package com.senhome.api.order.model;

import lombok.Data;

@Data
public class OrderGoodsDetailDTO
{
    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品数量
     */
    private Integer count;

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

    /**
     * 收益
     */
    private String income;
}
