package com.senhome.web.order.param;

import com.senhome.shell.common.dal.domain.ProtocolPojo;
import lombok.Data;

import java.util.List;

@Data
public class OrderConfirmParam extends ProtocolPojo
{
    /**
     * 购物车id列表
     */
    private List<Integer> cartIds;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 地址id
     */
    private Integer addressId;

    /**
     * 店铺id
     */
    private Integer shopId;
}
