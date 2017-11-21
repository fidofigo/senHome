package com.senhome.api.cms.model;

import lombok.*;

import java.util.List;

@Data
public class CmsGroupDetailDTO{
    /**
     * 布局方式，1：一行1张，2：一行2张，3：一行3张，4：一行4张
     */
    private int layoutType;

    /**
     * 组合商品列表
     */
    private List<CmsGroupProductDTO> groupProductDetailList;
}
