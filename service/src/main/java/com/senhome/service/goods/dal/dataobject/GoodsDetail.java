package com.senhome.service.goods.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class GoodsDetail extends BaseDO
{
    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 详情图片
     */
    private String url;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 排序值
     */
    private Integer sequence;

    /**
     * 跳转URL
     */
    private String link;
}
