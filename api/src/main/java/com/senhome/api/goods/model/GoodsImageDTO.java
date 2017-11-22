package com.senhome.api.goods.model;

import lombok.Data;

@Data
public class GoodsImageDTO
{
    /**
     * 图像url
     */
    private String image;

    /**
     * 图像宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 跳转url
     */
    private String url;
}
