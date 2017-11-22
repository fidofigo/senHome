package com.senhome.service.goods.dal.mapper;

import com.senhome.service.goods.dal.dataobject.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper
{
    /**
     * 获取所有在售商品
     * @param start
     * @param pageCount
     * @return
     */
    List<Goods> findAll(@Param("start") int start, @Param("pageCount") int pageCount);

    /**
     * 通过id查找商品信息
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 批量获取商品信息
     * @param ids
     * @return
     */
    List<Goods> findByIds(List<Integer> ids);

    /**
     * 通过类目id查找商品
     * @param categoryId
     * @param start
     * @param pageCount
     * @return
     */
    List<Goods> findByCategoryId(@Param("categoryId") Integer categoryId, @Param("start") int start, @Param("pageCount") int pageCount);
}
