package com.senhome.service.shop.business;

import com.senhome.service.shop.dal.dataobject.Shop;
import com.senhome.service.shop.dal.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopBusiness
{
    @Autowired
    private ShopMapper shopMapper;

    public Shop findShopById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return shopMapper.findById(id);
    }

    public List<Shop> findAllOpenShop()
    {
        return shopMapper.findAllOpenShop();
    }

    public Integer updateShop(Shop shop)
    {
        if(shop == null)
        {
            return 0;
        }

        return shopMapper.updateShop(shop);
    }

}
