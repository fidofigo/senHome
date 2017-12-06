package com.senhome.service.goods.service;

import com.senhome.api.goods.api.GoodsServiceApi;
import com.senhome.api.goods.model.GoodsBaseDTO;
import com.senhome.api.goods.model.GoodsDTO;
import com.senhome.api.goods.model.GoodsImageDTO;
import com.senhome.service.goods.business.GoodsBusiness;
import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.GoodsBase;
import com.senhome.service.goods.dal.dataobject.GoodsDetail;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsServiceApi
{
    @Autowired
    private GoodsBusiness goodsBusiness;

    @Override
    public ViewResult goodsDetail(Integer goodsId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        Goods goods = goodsBusiness.findGoodsById(goodsId);

        if(goods == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("goods not exist");
            return viewResult;
        }

        GoodsBase goodsBase = goodsBusiness.findGoodsBaseById(goods.getGoodsBaseId());

        if(goodsBase == null)
        {
            viewResult.setSuccess(false);
            viewResult.setMessage("goods not exist");
            return viewResult;
        }

        List<GoodsDetail> goodsDetailList = goodsBusiness.findGoodsDetailByGoodsId(goodsId);

        //创建商品基础信息
        GoodsBaseDTO goodsBaseDTO = new GoodsBaseDTO();
        goodsBaseDTO.setBrand(goodsBase.getBrand());
        goodsBaseDTO.setCountry(goodsBase.getCountry());
        goodsBaseDTO.setDesc(goods.getDesc());
        goodsBaseDTO.setId(goodsId);
        goodsBaseDTO.setLimit(goods.getLimit());
        goodsBaseDTO.setMarketPrice(BigDecimal.valueOf(goods.getMarketPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        goodsBaseDTO.setSalesPrice(BigDecimal.valueOf(goods.getSalesPrice()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_FLOOR).toString());
        goodsBaseDTO.setName(goods.getName());

        //创建商品图片列表
        List<String> imageList = new ArrayList<>();
        imageList.add(goods.getImage1());
        if(!goods.getImage2().isEmpty())
        {
            imageList.add(goods.getImage2());
        }
        if(!goods.getImage3().isEmpty())
        {
            imageList.add(goods.getImage3());
        }
        if(!goods.getImage4().isEmpty())
        {
            imageList.add(goods.getImage4());
        }
        if(!goods.getImage5().isEmpty())
        {
            imageList.add(goods.getImage5());
        }

        //创建商品详情图片列表
        List<GoodsImageDTO> goodsImageList = new ArrayList<>();
        if(goodsDetailList != null && goodsDetailList.size() > 0)
        {
            for(GoodsDetail goodsDetail : goodsDetailList)
            {
                GoodsImageDTO goodsImage = new GoodsImageDTO();
                goodsImage.setHeight(goodsDetail.getHeight());
                goodsImage.setWidth(goodsDetail.getWidth());
                goodsImage.setImage(goodsDetail.getUrl());
                goodsImage.setUrl(goodsDetail.getLink());

                goodsImageList.add(goodsImage);
            }
        }

        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setDetailList(goodsImageList);
        goodsDTO.setImageList(imageList);
        goodsDTO.setGoodsBase(goodsBaseDTO);

        return viewResult.putDefaultModel(goodsDTO);
    }
}
