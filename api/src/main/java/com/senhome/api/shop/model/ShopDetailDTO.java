package com.senhome.api.shop.model;

import lombok.Data;

@Data
public class ShopDetailDTO
{
    /**
     * 总收益，单位为分
     */
    private String income;

    /**
     * 总收入(已提现)，单位为分
     */
    private String withdraw;

    /**
     * 是否开店；0：否，1：是
     */
    private Integer isOpen;

    /**
     * 店名
     */
    private String name;

    /**
     * 头像地址
     */
    private String head;
}
