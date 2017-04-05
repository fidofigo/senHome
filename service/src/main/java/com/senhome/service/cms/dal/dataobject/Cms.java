package com.senhome.service.cms.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
