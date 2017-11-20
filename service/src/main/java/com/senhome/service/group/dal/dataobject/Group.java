package com.senhome.service.group.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.*;

@Data
public class Group extends BaseDO{
    /**
     * 备注
     */
    private String remark;
}
