package com.senhome.service.product.dal.mapper;

import com.senhome.service.product.dal.dataobject.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fidofigo on 17/4/4.
 */
@Repository
public interface ProductMapper {
    /**
     * 通过id获取商品信息
     * @param id
     * @return
     */
    Product findById(Integer id);

    /**
     * 批量获取商品详情列表
     * @param ids
     * @return
     */
    List<Product> findProductByIds(List<Integer> ids);
}
