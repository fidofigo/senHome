package com.senhome.web.home.param;

import lombok.Data;

@Data
public class HomeParam
{
    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 页数
     */
    private Integer page;

    /**
     * 页面
     */
    private Integer pageCount;
}
