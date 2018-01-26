package com.senhome.web.order.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

@Data
public class OrderListParam extends ProtocolPojo
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
     * 店铺id
     */
    private Integer shopId;
}
