package com.senhome.shell.common.dal.domain;

import lombok.Data;

@Data
public class DistanceDO
{
    /**
     * 邮编
     */
    private String code;

    /**
     * 距离
     */
    private Integer distance;
}
