package com.senhome.service.shop.service;

import com.senhome.api.shop.api.ShopServiceApi;
import com.senhome.api.shop.model.ShopDetailDTO;
import com.senhome.service.shop.business.ShopBusiness;
import com.senhome.service.shop.dal.dataobject.Shop;
import com.senhome.shell.common.result.ViewResult;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShopServiceImpl implements ShopServiceApi
{
    @Autowired
    private ShopBusiness shopBusiness;

    @Override
    public ViewResult shopDetail(Integer shopId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(shopId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("shop not exist");
            return viewResult;
        }

        Shop shop = shopBusiness.findShopById(shopId);

        if(shop == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("shop not exist");
            return viewResult;
        }

        ShopDetailDTO shopDetailDTO = new ShopDetailDTO();
        shopDetailDTO.setIncome(BigDecimal.valueOf(shop.getIncome()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        shopDetailDTO.setWithdraw(BigDecimal.valueOf(shop.getWithdraw()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        shopDetailDTO.setIsOpen(shop.getIsOpen());
        shopDetailDTO.setName(shop.getName());
        shopDetailDTO.setHead(shop.getHead());

        return viewResult.putDefaultModel(shopDetailDTO);
    }

    @Override
    public ViewResult shopOperation(Integer shopId, Integer isOpen)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(shopId == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("shop not exist");
            return viewResult;
        }

        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setIsOpen(isOpen);

        if(shopBusiness.updateShop(shop) <= 0)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("open shop fail");
            return viewResult;
        }

        return viewResult;
    }
}
