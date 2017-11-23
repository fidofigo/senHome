package com.senhome.service.banner.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Banner extends BaseDO
{
    /**
     * 图片地址
     */
    private String image;

    /**
     * 访问类型；1:商品
     */
    private Byte type;

    /**
     * 类型id
     */
    private Integer displayId;

    /**
     * 排序
     */
    private Integer sequence;
}
