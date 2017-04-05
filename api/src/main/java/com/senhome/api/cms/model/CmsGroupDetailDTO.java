package com.senhome.api.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
