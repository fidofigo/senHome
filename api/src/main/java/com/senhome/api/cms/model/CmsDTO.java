package com.senhome.api.cms.model;

import lombok.*;

import java.util.List;

@Data
public class CmsDTO{
    /**
     * 模版详情列表
     */
    private List<CmsDetailDTO> cmsDetailList;
}
