package com.senhome.service.goods.business;

import com.senhome.service.goods.dal.dataobject.Goods;
import com.senhome.service.goods.dal.dataobject.GoodsBase;
import com.senhome.service.goods.dal.dataobject.GoodsDetail;
import com.senhome.service.goods.dal.mapper.GoodsBaseMapper;
import com.senhome.service.goods.dal.mapper.GoodsDetailMapper;
import com.senhome.service.goods.dal.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class GoodsBusiness
{
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDetailMapper goodsDetailMapper;

    @Autowired
    private GoodsBaseMapper goodsBaseMapper;

    public Goods findGoodsById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return goodsMapper.findById(id);
    }

    public List<Goods> findGoodsListByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return Collections.emptyList();
        }

        return goodsMapper.findByIds(ids);
    }

    public List<Goods> findGoodsListByCategoryId(Integer categoryId, int page, int pageCount)
    {
        if(categoryId == null)
        {
            return null;
        }

        if(categoryId == 0)
        {
            return goodsMapper.findAll((page - 1) * pageCount, pageCount);
        }
        else
        {
            return goodsMapper.findByCategoryId(categoryId, (page - 1) * pageCount, pageCount);
        }
    }

    public List<GoodsDetail> findGoodsDetailByGoodsId(Integer goodsId)
    {
        if(goodsId == null)
        {
            return null;
        }

        return goodsDetailMapper.findByGoodsId(goodsId);
    }

    public GoodsBase findGoodsBaseById(Integer id)
    {
        if(id == null)
        {
            return null;
        }

        return goodsBaseMapper.findById(id);
    }



}
