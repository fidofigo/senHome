package com.senhome.web.shop.param;

import lombok.Data;

@Data
public class ShopParam
{
    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 0:关店, 1:开店
     */
    private Integer isOpen;
}
