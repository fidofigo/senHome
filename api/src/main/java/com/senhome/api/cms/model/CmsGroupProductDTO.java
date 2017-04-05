package com.senhome.api.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
