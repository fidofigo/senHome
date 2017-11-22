package com.senhome.service.goods.dal.mapper;

import com.senhome.service.goods.dal.dataobject.GoodsBase;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsBaseMapper
{
    /**
     * 通过id查找商品信息
     * @param id
     * @return
     */
    GoodsBase findById(Integer id);
}
