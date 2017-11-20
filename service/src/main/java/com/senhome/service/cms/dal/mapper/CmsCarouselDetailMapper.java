package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsCarouselDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsCarouselDetailMapper {
    /**
     * 通过cmsId获取cms轮播列表
     * @param cmsDetailId
     * @return
     */
    List<CmsCarouselDetail> findByCmsDetailId(Integer cmsDetailId);
}
