package com.senhome.api.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmsDTO{
    /**
     * 模版详情列表
     */
    private List<CmsDetailDTO> cmsDetailList;
}
