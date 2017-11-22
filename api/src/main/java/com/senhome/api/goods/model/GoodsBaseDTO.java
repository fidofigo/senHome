package com.senhome.api.goods.model;

import lombok.Data;

@Data
public class GoodsBaseDTO
{
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品原价
     */
    private String marketPrice;

    /**
     * 商品售价
     */
    private String salesPrice;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 产地
     */
    private String country;

    /**
     * 描述
     */
    private String desc;

    /**
     * 加入购物车限制
     */
    private Integer limit;
}
