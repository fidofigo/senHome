package com.senhome.service.category.dal.mapper;

import com.senhome.service.category.dal.dataobject.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper
{
    List<Category> findAll();
}
