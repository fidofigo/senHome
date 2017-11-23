package com.senhome.api.home.model;

import lombok.Data;

@Data
public class HomeGoodsDetailDTO
{
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商品图像
     */
    private String image;

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
}
