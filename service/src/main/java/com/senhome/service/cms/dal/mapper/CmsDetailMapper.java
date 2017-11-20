package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsDetailMapper {
    /**
     * 通过cmsId获取cms模版详情列表
     * @param cmsId
     * @return
     */
    List<CmsDetail> findByCmsId(Integer cmsId);
}
