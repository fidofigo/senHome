package com.senhome.service.goods.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class GoodsBase extends BaseDO
{
    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 商品主图
     */
    private String image;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 产地
     */
    private String country;
}
