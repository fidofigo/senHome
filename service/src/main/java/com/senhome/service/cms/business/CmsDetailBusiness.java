package com.senhome.service.cms.business;

import com.senhome.service.cms.dal.dataobject.Cms;
import com.senhome.service.cms.dal.dataobject.CmsCarouselDetail;
import com.senhome.service.cms.dal.dataobject.CmsDetail;
import com.senhome.service.cms.dal.dataobject.CmsGroupDetail;
import com.senhome.service.cms.dal.mapper.CmsCarouselDetailMapper;
import com.senhome.service.cms.dal.mapper.CmsDetailMapper;
import com.senhome.service.cms.dal.mapper.CmsGroupDetailMapper;
import com.senhome.service.cms.dal.mapper.CmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Component
@Transactional
public class CmsDetailBusiness {

    @Autowired
    private CmsDetailMapper cmsDetailMapper;

    @Autowired
    private CmsGroupDetailMapper cmsGroupDetailMapper;

    @Autowired
    private CmsMapper cmsMapper;

    @Autowired
    private CmsCarouselDetailMapper cmsCarouselDetailMapper;

    @Transactional(readOnly = true)
    public List<CmsDetail> getCmsDetailList(Integer cmsId)
    {
        if (cmsId == null)
            return null;

        return cmsDetailMapper.findByCmsId(cmsId);
    }

    @Transactional(readOnly = true)
    public List<CmsCarouselDetail> getCmsCarouselDetailList(Integer cmsDetailId)
    {
        if(cmsDetailId == null)
            return null;

        return cmsCarouselDetailMapper.findByCmsDetailId(cmsDetailId);
    }

    @Transactional(readOnly = true)
    public List<CmsGroupDetail> getCmsGroupDetailList(Integer cmsDetailId)
    {
        if(cmsDetailId == null)
            return null;

        return cmsGroupDetailMapper.findByCmsDetailId(cmsDetailId);
    }

    @Transactional(readOnly = true)
    public List<CmsGroupDetail> getCmsGroupDetailListByIds(List<Integer> cmsIds)
    {
        if(CollectionUtils.isEmpty(cmsIds))
            return Collections.emptyList();

        return cmsGroupDetailMapper.findByCmsDetailByIds(cmsIds);
    }

    @Transactional(readOnly = true)
    public Cms getCmsById(Integer id)
    {
        if(id == null)
            return null;

        return cmsMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cms> getCmsListByIds(List<Integer> cmsIds)
    {
        if(CollectionUtils.isEmpty(cmsIds))
            return Collections.emptyList();

        return cmsMapper.findByCmsByIds(cmsIds);
    }
}
