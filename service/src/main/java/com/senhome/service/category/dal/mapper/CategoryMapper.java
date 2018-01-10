package com.senhome.service.category.dal.mapper;

import com.senhome.service.category.dal.dataobject.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper
{
    /**
     * 查找所有类目
     * @return
     */
    List<Category> findAll();

    /**
     * 通过类目id列表查找类目列表
     * @param ids
     * @return
     */
    List<Category> findByIds(List<Integer> ids);
}
