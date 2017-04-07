package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsCarouselDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cmsCarouselDetailMapper")
public interface CmsCarouselDetailMapper {
    /**
     * 通过cmsId获取cms轮播列表
     * @param cmsId
     * @return
     */
    List<CmsCarouselDetail> findByCmsId(Integer cmsId);
}
