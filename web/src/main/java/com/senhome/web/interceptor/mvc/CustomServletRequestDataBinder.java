/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.senhome.web.interceptor.mvc;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 自定义 ServletRequestDataBinder
 *
 * 添加ip参数
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2016/11/26
 * @since 1.0
 */
public class CustomServletRequestDataBinder extends ExtendedServletRequestDataBinder
{
    
    private final Map<String, String> propertyNameCache = new ConcurrentReferenceHashMap<String, String>(64);
    
    /**
     * @param target
     * @param objectName
     */
    public CustomServletRequestDataBinder(Object target, String objectName)
    {
        super(target, objectName);
    }
    
    /**
     * @param target
     */
    public CustomServletRequestDataBinder(Object target)
    {
        super(target);
    }
    
    /**
     * @see ExtendedServletRequestDataBinder#addBindValues(MutablePropertyValues, ServletRequest)
     */
    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request)
    {
        super.addBindValues(mpvs, request);
        //mpvs.add("ip", HttpServletUtils.getRemoteIpAddr((HttpServletRequest)request));
        
        // 获取请求来源
//        String source = ObjectUtils.defaultIfNull(mpvs.get("source"), RequestSource.APP.getCode()).toString();
        
//        RequestSource requestSource = EnumUtils.getEnumByCode(RequestSource.class, Integer.valueOf(source));
//        // app请求,需要对参数进行处理
//        if (RequestSource.APP == requestSource)
//        {
            String param = ObjectUtils.defaultIfNull(mpvs.get("params"), "").toString();
            JSONObject paramJson = JSONObject.parseObject(param);
            if (paramJson != null)
            {
                paramJson.entrySet().stream().filter(entry -> mpvs.get(entry.getKey()) == null).forEach(
                    entry -> mpvs.add(entry.getKey(), entry.getValue()));
            }
//        }
//        else
//        {
//            Cookie cookie = HttpServletUtils.getCookieByName((HttpServletRequest)request, WEB_USER_TRACK_TOKEN);
//            String imei = request.getAttribute(WEB_USER_TRACK_TOKEN) == null ? "" : request.getAttribute(WEB_USER_TRACK_TOKEN).toString();
//            mpvs.add("webTrackImei", cookie == null ? imei : cookie.getValue());
//        }
    }
    
}
