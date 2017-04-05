package com.senhome.service.product.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product extends BaseDO{
    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品描述
     */
    private String description;

    /**
     * cms模版id
     */
    private Integer cmsId;

    /**
     * 商品图1
     */
    private String image1;

    /**
     * 商品图2
     */
    private String image2;

    /**
     * 商品图3
     */
    private String image3;

    /**
     * 商品图4
     */
    private String image4;

    /**
     * 商品图5
     */
    private String image5;

    /**
     * 商品图6
     */
    private String image6;

    /**
     * 商品图7
     */
    private String image7;

    /**
     * 商品图8
     */
    private String image8;

    /**
     * 商品图9
     */
    private String image9;

}
