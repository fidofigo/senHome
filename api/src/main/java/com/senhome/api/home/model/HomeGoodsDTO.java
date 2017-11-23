package com.senhome.api.home.model;

import lombok.Data;

import java.util.List;

@Data
public class HomeGoodsDTO
{
    /**
     * 商品总数
     */
    private Integer total;

    /**
     * 商品列表
     */
    private List<HomeGoodsDetailDTO> homeGoods;
}
