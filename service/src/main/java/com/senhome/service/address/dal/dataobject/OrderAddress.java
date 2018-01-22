package com.senhome.service.address.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class OrderAddress extends BaseDO
{
    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 地址编码
     */
    private Integer code;

    /**
     * 姓名
     */
    private String name;
}
