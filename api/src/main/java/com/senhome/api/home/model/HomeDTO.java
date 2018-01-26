package com.senhome.api.home.model;

import lombok.Data;

import java.util.List;

@Data
public class HomeDTO
{
    /**
     * banner列表
     */
    private BannerDTO banner;

    /**
     * 类目列表
     */
    private List<CategoryDTO> category;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 店铺名称
     */
    private String shopName;
}
