package com.senhome.api.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by fidofigo on 17/2/24.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
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
