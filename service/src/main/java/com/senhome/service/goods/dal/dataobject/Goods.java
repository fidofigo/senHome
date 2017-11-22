package com.senhome.service.goods.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Goods extends BaseDO
{
    /**
     * 基础商品id
     */
    private Integer goodsBaseId;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 商品主图
     */
    private String image1;

    /**
     * 品牌图片2
     */
    private String image2;

    /**
     * 品牌图片3
     */
    private String image3;

    /**
     * 品牌图片4
     */
    private String image4;

    /**
     * 品牌图片5
     */
    private String image5;

    /**
     * 商品展示名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 供货价，分为单位
     */
    private Integer marketPrice;

    /**
     * 销售价，分为单位
     */
    private Integer salesPrice;

    /**
     * 收益
     */
    private Integer income;

    /**
     * 加入购物车限制
     */
    private Integer limit;
}
