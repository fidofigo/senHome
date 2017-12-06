package com.senhome.api.cart.model;

import lombok.Data;

import java.util.List;

@Data
public class CartGoodsDTO
{
    /**
     * 购物车商品详情
     */
    private List<CartGoodsDetailDTO> cartGoods;
}
