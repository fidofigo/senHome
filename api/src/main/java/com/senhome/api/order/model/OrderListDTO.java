package com.senhome.api.order.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderListDTO
{
    private List<OrderDetailDTO> orderList;
}
