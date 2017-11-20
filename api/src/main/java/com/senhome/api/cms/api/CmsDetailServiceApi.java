package com.senhome.api.cms.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

/**
 * Created by fidofigo on 17/2/24.
 */
@Component("cmsDetailServiceApi")
public interface CmsDetailServiceApi {
    /**
     * 获取促销模版详情
     * @param cmsId 促销模版id
     * @return
     */
    ViewResult getCmsDetail(Integer cmsId);
}
