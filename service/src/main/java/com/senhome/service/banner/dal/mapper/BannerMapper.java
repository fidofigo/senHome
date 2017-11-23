package com.senhome.service.banner.dal.mapper;

import com.senhome.service.banner.dal.dataobject.Banner;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper
{
    List<Banner> findAll();
}
