package com.senhome.service.home.service;

import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.api.home.model.*;
import com.senhome.service.address.business.AddressBusiness;
import com.senhome.service.address.dal.dataobject.Address;
import com.senhome.service.banner.business.BannerBusiness;
import com.senhome.service.banner.dal.dataobject.Banner;
import com.senhome.service.category.business.CategoryBusiness;
import com.senhome.service.category.dal.dataobject.Category;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.business.ShopGoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.ShopGoods;
import com.senhome.service.shop.business.ShopBusiness;
import com.senhome.service.shop.dal.dataobject.Shop;
import com.senhome.shell.common.dal.domain.DistanceDO;
import com.senhome.shell.common.lang.GoogleMapsUtil;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    private ShopBusiness shopBusiness;

    @Autowired
    private AddressBusiness addressBusiness;

    private final Integer MAX_DISTANCE = 10000;

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

        HomeDTO homeDTO = new HomeDTO();
        homeDTO.setBanner(bannerDTO);

        //获取类目相关信息
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        Shop shop = getShop(addressId);

        if(shop == null)
        {
            return ViewResult.ofSuccess().putDefaultModel(homeDTO);
        }

        List<Integer> categoryIds = shopGoodsBusiness.findCategoryIdListByShop(shop.getId());
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

        homeDTO.setCategory(categoryDTOList);
        homeDTO.setShopId(shop.getId());
        homeDTO.setShopName(shop.getName() + shop.getCode());

        return ViewResult.ofSuccess().putDefaultModel(homeDTO);
    }

    /**
     * 获取用户附近商家
     * @param addressId
     * @return
     */
    private Shop getShop(Integer addressId)
    {
        //获取附近商家
        List<Shop> shopList = shopBusiness.findAllOpenShop();

        if(CollectionUtils.isEmpty(shopList))
        {
            return null;
        }

        List<String> codeIds = shopList.stream().map(Shop::getCode).collect(Collectors.toList());
        Map<String, Shop> idShopMap = shopList.stream().collect(Collectors.toMap(Shop::getCode, x -> x, (t, u) -> u));

        //获取用户code码
        Address address = addressBusiness.findAddressById(addressId);

        List<DistanceDO> distanceList = GoogleMapsUtil.getDistance(codeIds, address.getCode().toString());

        if(CollectionUtils.isEmpty(distanceList))
        {
            return null;
        }

        distanceList.sort(Comparator.comparing(DistanceDO::getDistance));

        DistanceDO distanceDO = distanceList.get(0);
        if(distanceDO.getDistance() >= MAX_DISTANCE)
        {
            return null;
        }

        return idShopMap.get(distanceDO.getCode());
    }

    @Override
    public ViewResult homeGoodsDetail(Integer type, Integer categoryId, Integer shopId, Integer page, Integer pageCount)
    {
        List<ShopGoods> shopGoodsList = shopGoodsBusiness.findShopGoodsListByCategoryId(categoryId, shopId, page, pageCount);
        Map<Integer, ShopGoods> idShopGoodsMap = shopGoodsList.stream().collect(Collectors.toMap(ShopGoods::getGoodsId, x -> x));
        List<Integer> goodsIds = shopGoodsList.stream().map(ShopGoods::getGoodsId).collect(Collectors.toList());

        List<Goods> goodsList = goodsBusiness.findGoodsListByIds(goodsIds);

        Integer total = shopGoodsBusiness.findShopGoodsCountByCategoryId(categoryId, shopId);

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
                homeGoodsDetail.setIncome(BigDecimal.valueOf(goods.getIncome()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
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
