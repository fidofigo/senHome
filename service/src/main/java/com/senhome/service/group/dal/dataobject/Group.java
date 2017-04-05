package com.senhome.service.group.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Group extends BaseDO{
    /**
     * 备注
     */
    private String remark;
}
