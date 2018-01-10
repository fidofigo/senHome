package com.senhome.service.home.service;

import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.api.home.model.*;
import com.senhome.service.banner.business.BannerBusiness;
import com.senhome.service.banner.dal.dataobject.Banner;
import com.senhome.service.category.business.CategoryBusiness;
import com.senhome.service.category.dal.dataobject.Category;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.business.ShopGoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.ShopGoods;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeServiceApi
{
    @Autowired
    private BannerBusiness bannerBusiness;

    @Autowired
    private GoodsBusiness goodsBusiness;

    @Autowired
    private CategoryBusiness categoryBusiness;

    @Autowired
    private ShopGoodsBusiness shopGoodsBusiness;

    @Override
    public ViewResult homeDetail(Integer addressId)
    {
        //获取banner相关信息
        List<Banner> bannerList = bannerBusiness.findAllBanner();
        List<BannerListDTO> bannerListDTOList = new ArrayList<>();
        for(Banner banner : bannerList)
        {
            BannerListDTO bannerListDTO = new BannerListDTO();
            bannerListDTO.setDisplayId(banner.getDisplayId());
            bannerListDTO.setImage(banner.getImage());
            bannerListDTO.setType(banner.getType());

            bannerListDTOList.add(bannerListDTO);
        }

        BannerDTO bannerDTO = new BannerDTO();
        bannerDTO.setTotal(bannerList.size());
        bannerDTO.setList(bannerListDTOList);

        //获取类目相关信息
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        Integer shopId = 1;
        List<Integer> categoryIds = shopGoodsBusiness.findCategoryIdListByShop(shopId);
        if(categoryIds != null && categoryIds.size() > 0)
        {
            //获取类目相关信息
            List<Category> categoryList = categoryBusiness.findByCategoryIds(categoryIds);

            CategoryDTO categoryAll = new CategoryDTO();
            categoryAll.setName("all");
            categoryAll.setType(0);
            categoryDTOList.add(categoryAll);

            for(Category category : categoryList)
            {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setName(category.getName());
                categoryDTO.setType(category.getId());
                categoryDTOList.add(categoryDTO);
            }
        }

        HomeDTO homeDTO = new HomeDTO();
        homeDTO.setBanner(bannerDTO);
        homeDTO.setCategory(categoryDTOList);
        homeDTO.setShopId(shopId);

        return ViewResult.ofSuccess().putDefaultModel(homeDTO);
    }

    @Override
    public ViewResult homeGoodsDetail(Integer type, Integer categoryId, Integer shopId, Integer page, Integer pageCount)
    {
        List<ShopGoods> shopGoodsList = shopGoodsBusiness.findShopGoodsListByCategoryId(categoryId, shopId, page, pageCount);
        Map<Integer, ShopGoods> idShopGoodsMap = shopGoodsList.stream().collect(Collectors.toMap(ShopGoods::getGoodsId, x -> x));
        List<Integer> goodsIds = shopGoodsList.stream().map(ShopGoods::getGoodsId).collect(Collectors.toList());

        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        Integer total = shopGoodsBusiness.findShopGoodsCountByCategoryId(categoryId);

        List<HomeGoodsDetailDTO> homeGoods = new ArrayList<>();

        for(Goods goods : goodsList)
        {
            HomeGoodsDetailDTO homeGoodsDetail = new HomeGoodsDetailDTO();
            homeGoodsDetail.setId(goods.getId());
            homeGoodsDetail.setImage(goods.getImage1());
            homeGoodsDetail.setMarketPrice(BigDecimal.valueOf(goods.getMarketPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            homeGoodsDetail.setSalesPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            homeGoodsDetail.setName(goods.getName());

            if(type == 2)
            {
                homeGoodsDetail.setIncome(goods.getIncome());
                homeGoodsDetail.setStock(idShopGoodsMap.get(goods.getId()).getStock());
            }
            homeGoods.add(homeGoodsDetail);
        }

        HomeGoodsDTO goodsDTO = new HomeGoodsDTO();
        goodsDTO.setTotal(total);
        goodsDTO.setHomeGoods(homeGoods);

        return ViewResult.ofSuccess().putDefaultModel(goodsDTO);
    }
}
