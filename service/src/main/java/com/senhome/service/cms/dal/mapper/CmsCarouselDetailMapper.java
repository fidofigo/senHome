package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsCarouselDetail;

import java.util.List;

public interface CmsCarouselDetailMapper {
    /**
     * 通过cmsId获取cms轮播列表
     * @param cmsId
     * @return
     */
    List<CmsCarouselDetail> findByCmsId(Integer cmsId);
}
