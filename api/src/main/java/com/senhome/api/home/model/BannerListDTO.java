package com.senhome.api.home.model;

import lombok.Data;

@Data
public class BannerListDTO
{
    /**
     * 图像
     */
    private String image;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 类型id
     */
    private Integer displayId;
}
