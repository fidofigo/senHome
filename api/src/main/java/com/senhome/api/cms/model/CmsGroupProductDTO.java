package com.senhome.api.cms.model;

import lombok.*;

@Data
public class CmsGroupProductDTO{
    /**
     * 图片url
     */
    private String image;

    /**
     * 图片跳转url
     */
    private String url;

    /**
     * 图片宽
     */
    private Integer width;

    /**
     * 图片高
     */
    private Integer height;
}
