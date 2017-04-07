package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.CmsGroupDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cmsGroupDetailMapper")
public interface CmsGroupDetailMapper {
    /**
     * 通过cmsId获取cms组合搭配详情列表
     * @param cmsId
     * @return
     */
    List<CmsGroupDetail> findByCmsId(Integer cmsId);

    /**
     * 批量获取cms组合搭配详情列表
     */
    List<CmsGroupDetail> findByCmsDetailByIds(List<Integer> ids);
}
