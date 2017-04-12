package com.senhome.service.product.business;

import com.senhome.service.product.dal.dataobject.Product;
import com.senhome.service.product.dal.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by fidofigo on 17/4/4.
 */
@Service
public class ProductBusiness {

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Product getProduct(Integer id)
    {
        if (id == null)
            return null;

        return productMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductListByIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
            return Collections.emptyList();

        return productMapper.findProductByIds(ids);
    }
}
