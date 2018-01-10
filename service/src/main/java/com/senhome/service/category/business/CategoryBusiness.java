package com.senhome.service.category.business;

import com.senhome.service.category.dal.dataobject.Category;
import com.senhome.service.category.dal.mapper.CategoryMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBusiness
{
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> findAllCategory()
    {
        return categoryMapper.findAll();
    }

    public List<Category> findByCategoryIds(List<Integer> ids)
    {
        if(CollectionUtils.isEmpty(ids))
        {
            return null;
        }

        return categoryMapper.findByIds(ids);
    }
}
