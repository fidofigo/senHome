package com.senhome.service.goods.dal.mapper;

import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.ShopGoods;
import com.senhome.service.shop.dal.dataobject.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopGoodsMapper
{
    /**
     * 获取所有在售店铺商品
     * @param start
     * @param pageCount
     * @return
     */
    List<ShopGoods> findAll(@Param("shopId") int shopId, @Param("start") int start, @Param("pageCount") int pageCount);

    /**
     * 查找店铺商品总数
     * @return
     */
    Integer findAllCount();

    /**
     * 查找店铺类目
     * @param shopId
     * @return
     */
    List<Integer> findAllShopCategoryId(Integer shopId);

    /**
     * 按照类目查找店铺商品总数
     * @param categoryId
     * @return
     */
    Integer findCountByCategoryId(Integer categoryId);

    /**
     * 通过id查找店铺商品信息
     * @param id
     * @return
     */
    ShopGoods findById(Integer id);

    /**
     * 批量获取店铺商品信息
     * @param ids
     * @return
     */
    List<ShopGoods> findByIds(List<Integer> ids);

    /**
     * 批量获取店铺商品信息
     * @param ids
     * @return
     */
    List<ShopGoods> findByIdsForUpdate(List<Integer> ids);

    /**
     * 通过类目id查找店铺商品
     * @param shopId
     * @param categoryId
     * @param start
     * @param pageCount
     * @return
     */
    List<ShopGoods> findByCategoryId(@Param("shopId") int shopId, @Param("categoryId") Integer categoryId, @Param("start") int start, @Param("pageCount") int pageCount);

    /**
     * 更新店铺商品库存
     * @param shopGoods
     * @return
     */
    int updateShopGoodsStock(ShopGoods shopGoods);
}
