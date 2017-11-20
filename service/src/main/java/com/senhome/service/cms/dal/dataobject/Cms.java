package com.senhome.service.cms.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Cms extends BaseDO{
    /**
     * cms名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
