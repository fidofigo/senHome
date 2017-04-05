package com.senhome.api.cms.api;

import com.senhome.api.cms.model.CmsDTO;
import com.senhome.shell.common.result.Result;
import com.senhome.shell.common.result.ViewResult;

/**
 * Created by fidofigo on 17/2/24.
 */
public interface CmsDetailServiceApi {
    /**
     * 获取促销模版详情
     * @param cmsId 促销模版id
     * @return
     */
    ViewResult getCmsDetail(Integer cmsId);
}
