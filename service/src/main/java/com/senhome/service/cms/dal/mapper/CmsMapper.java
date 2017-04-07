package com.senhome.service.cms.dal.mapper;

import com.senhome.service.cms.dal.dataobject.Cms;
import com.senhome.service.cms.dal.dataobject.CmsGroupDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cmsMapper")
public interface CmsMapper {
    /**
     * 获取所有cms列表
     * @return
     */
    List<Cms> findAll();

    /**
     * 通过id获取cms信息
     * @param id
     * @return
     */
    Cms findById(Integer id);

    /**
     * 批量获取cms组合搭配详情列表
     */
    List<Cms> findByCmsByIds(List<Integer> ids);
}
