package com.senhome.web.order.param;

import lombok.Data;

@Data
public class OrderListParam
{
    /**
     * 页数
     */
    private Integer page;

    /**
     * 页面
     */
    private Integer pageCount;

    /**
     * 订单状态 1:待付款 2:待配送 3:配送中 4:交易成功 5:已取消 6:全部
     */
    private Byte type;

    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 店铺id
     */
    private Integer shopId;
}
