package com.senhome.service.category.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Category extends BaseDO
{
    /**
     * 类目名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sequence;
}

