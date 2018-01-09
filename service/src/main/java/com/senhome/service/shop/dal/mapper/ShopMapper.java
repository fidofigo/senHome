package com.senhome.service.shop.dal.mapper;

import com.senhome.service.shop.dal.dataobject.Shop;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopMapper
{
    /**
     * 根据店铺id查询店铺信息
     * @param id
     * @return
     */
    Shop findById(Integer id);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
