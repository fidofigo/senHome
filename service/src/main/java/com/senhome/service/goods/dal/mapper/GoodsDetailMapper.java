package com.senhome.service.goods.dal.mapper;

import com.senhome.service.goods.dal.dataobject.GoodsDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDetailMapper
{
    /**
     * 通过商品id获取商品详情
     * @param goodsId
     * @return
     */
    List<GoodsDetail> findByGoodsId(Integer goodsId);
}
