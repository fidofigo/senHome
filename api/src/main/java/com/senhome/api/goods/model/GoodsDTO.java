package com.senhome.api.goods.model;

import lombok.Data;

import java.util.List;

@Data
public class GoodsDTO
{
    /**
     * 展示图列表
     */
    List<String> imageList;

    /**
     * 商品基础详情
     */
    GoodsBaseDTO goodsBase;

    /**
     * 商品详情图片列表
     */
    List<GoodsImageDTO> detailList;
}
