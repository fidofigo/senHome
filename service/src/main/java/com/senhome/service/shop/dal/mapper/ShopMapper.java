package com.senhome.service.shop.dal.mapper;

import com.senhome.service.shop.dal.dataobject.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 获取所有开的店铺信息
     * @return
     */
    List<Shop> findAllOpenShop();

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
