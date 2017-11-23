package com.senhome.api.home.model;

import lombok.Data;

import java.util.List;

@Data
public class BannerDTO
{
    /**
     * banner总数
     */
    private Integer total;

    /**
     * banner详情列表
     */
    private List<BannerListDTO> list;
}
