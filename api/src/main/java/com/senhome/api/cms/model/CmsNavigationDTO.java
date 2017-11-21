package com.senhome.api.cms.model;

import lombok.*;

/**
 * Created by fidofigo on 17/2/24.
 */
@Data
public class CmsNavigationDTO{
    /**
     * 导航名称
     */
    private String navigationName;

    /**
     * 促销模版详情id
     */
    private Integer cmsId;
}
