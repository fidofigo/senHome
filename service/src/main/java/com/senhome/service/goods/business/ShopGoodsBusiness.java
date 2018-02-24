package com.senhome.service.goods.business;

import com.senhome.service.goods.dal.dataobject.ShopGoods;
import com.senhome.service.goods.dal.mapper.ShopGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class ShopGoodsBusiness
{
    @Autowired
    private ShopGoodsMapper shopGoodsMapper;

    public ShopGoods findShopGoodsById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return shopGoodsMapper.findById(id);
    }

    public List<ShopGoods> findShopGoodsListByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return Collections.emptyList();
        }

        return shopGoodsMapper.findByIds(ids);
    }

    public List<ShopGoods> findShopGoodsListByIdsForUpdate(List<Integer> ids, Integer shopId)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return Collections.emptyList();
        }

        return shopGoodsMapper.findByIdsForUpdate(ids, shopId);
    }

    public Integer updateShopGoods(ShopGoods shopGoods)
    {
        if(shopGoods == null)
        {
            return 0;
        }

        return shopGoodsMapper.updateShopGoodsStock(shopGoods);
    }

    public List<Integer> findCategoryIdListByShop(Integer shopId)
    {
        if(shopId == null)
        {
            return null;
        }

        return shopGoodsMapper.findAllShopCategoryId(shopId);
    }

    public List<ShopGoods> findShopGoodsListByCategoryId(Integer categoryId, Integer shopId, int page, int pageCount)
    {
        if(categoryId == null)
        {
            return null;
        }

        if(categoryId == 0)
        {
            return shopGoodsMapper.findAll(shopId, (page - 1) * pageCount, pageCount);
        }
        else
        {
            return shopGoodsMapper.findByCategoryId(shopId, categoryId, (page - 1) * pageCount, pageCount);
        }
    }

    public Integer findShopGoodsCountByCategoryId(Integer categoryId, Integer shopId)
    {
        if(categoryId == null)
        {
            return 0;
        }

        if(categoryId == 0)
        {
            return shopGoodsMapper.findAllCount(shopId);
        }
        else
        {
            return shopGoodsMapper.findCountByCategoryId(categoryId, shopId);
        }
    }
}
