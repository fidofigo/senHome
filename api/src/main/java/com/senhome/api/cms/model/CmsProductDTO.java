package com.senhome.api.cms.model;

import lombok.*;

@Data
public class CmsProductDTO{
    /**
     * 商品跳转url
     */
    private String url;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品主图
     */
    private String image;
}
