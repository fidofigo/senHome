package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsDetail;

import java.util.List;

public interface CmsDetailMapper {
    /**
     * 通过cmsId获取cms模版详情列表
     * @param cmsId
     * @return
     */
    List<CmsDetail> findByCmsId(Integer cmsId);
}
