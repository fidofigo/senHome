package com.senhome.service.shop.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class Shop extends BaseDO
{
    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺用户id
     */
    private Integer accountId;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 总收益，单位为分
     */
    private Integer income;

    /**
     * 总收入(已提现)，单位为分
     */
    private Integer withdraw;

    /**
     * 是否开店；0：否，1：是
     */
    private Integer isOpen;

    /**
     * 手机号
     */
    private Integer mobileNumber;
}
