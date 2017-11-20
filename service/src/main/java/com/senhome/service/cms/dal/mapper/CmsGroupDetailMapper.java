package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsGroupDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsGroupDetailMapper {
    /**
     * 通过cmsId获取cms组合搭配详情列表
     * @param cmsDetailId
     * @return
     */
    List<CmsGroupDetail> findByCmsDetailId(Integer cmsDetailId);

    /**
     * 批量获取cms组合搭配详情列表
     */
    List<CmsGroupDetail> findByCmsDetailByIds(List<Integer> ids);
}
