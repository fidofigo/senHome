package com.senhome.web.cms.controller;

import com.senhome.api.cms.api.CmsDetailServiceApi;
import com.senhome.api.cms.model.CmsDTO;
import com.senhome.shell.common.result.Result;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.cms.param.CmsParam;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fidofigo on 17/2/27.
 */
@RestController
@RequestMapping("/webNative/cms")
public class CmsController {

    @Resource
    private CmsDetailServiceApi cmsDetailServiceApi;

    @RequestMapping(value = "/detail")
    public Object detail(CmsParam cmsParam){
        ViewResult result = cmsDetailServiceApi.getCmsDetail(cmsParam.getCmsId());
        if (!result.isSuccess()) {
            String msg = "Invoke cmsDetailServiceApi.getCmsDetail " + result.getMessage();
            throw new RuntimeException(msg);
        }

        return result.getModel();
    }

}