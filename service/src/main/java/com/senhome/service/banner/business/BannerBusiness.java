package com.senhome.service.banner.business;

import com.senhome.service.banner.dal.dataobject.Banner;
import com.senhome.service.banner.dal.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerBusiness
{
    @Autowired
    private BannerMapper bannerMapper;

    public List<Banner> findAllBanner()
    {
        return bannerMapper.findAll();
    }
}
