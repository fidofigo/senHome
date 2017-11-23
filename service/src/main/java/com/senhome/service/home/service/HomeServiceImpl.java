package com.senhome.service.home.service;

import com.senhome.api.home.api.HomeServiceApi;
import com.senhome.api.home.model.*;
import com.senhome.service.banner.business.BannerBusiness;
import com.senhome.service.banner.dal.dataobject.Banner;
import com.senhome.service.category.business.CategoryBusiness;
import com.senhome.service.category.dal.dataobject.Category;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeServiceApi
{
    @Autowired
    private BannerBusiness bannerBusiness;

    @Autowired
    private GoodsBusiness goodsBusiness;

    @Autowired
    private CategoryBusiness categoryBusiness;


    @Override
    public ViewResult homeDetail()
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
        List<Category> categoryList = categoryBusiness.findAllCategory();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

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

        HomeDTO homeDTO = new HomeDTO();
        homeDTO.setBanner(bannerDTO);
        homeDTO.setCategory(categoryDTOList);

        return ViewResult.ofSuccess().putDefaultModel(homeDTO);
    }

    @Override
    public ViewResult homeProductDetail(Integer categoryId, Integer page, Integer pageCount)
    {
        List<Goods> goodsList = goodsBusiness.findGoodsListByCategoryId(categoryId, page, pageCount);

        Integer total = goodsBusiness.findGoodsCountByCategoryId(categoryId);

        List<HomeGoodsDetailDTO> homeGoods = new ArrayList<>();

        for(Goods goods : goodsList)
        {
            HomeGoodsDetailDTO homeGoodsDetail = new HomeGoodsDetailDTO();
            homeGoodsDetail.setId(goods.getId());
            homeGoodsDetail.setImage(goods.getImage1());
            homeGoodsDetail.setMarketPrice(BigDecimal.valueOf(goods.getMarketPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            homeGoodsDetail.setSalesPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
            homeGoodsDetail.setName(goods.getName());

            homeGoods.add(homeGoodsDetail);
        }

        HomeGoodsDTO goodsDTO = new HomeGoodsDTO();
        goodsDTO.setTotal(total);
        goodsDTO.setHomeGoods(homeGoods);

        return ViewResult.ofSuccess().putDefaultModel(goodsDTO);
    }
}
