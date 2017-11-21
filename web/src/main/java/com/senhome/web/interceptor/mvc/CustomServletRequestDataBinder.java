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

        String param = ObjectUtils.defaultIfNull(mpvs.get("params"), "").toString();
        JSONObject paramJson = JSONObject.parseObject(param);
        if (paramJson != null)
        {
            paramJson.entrySet().stream().filter(entry -> mpvs.get(entry.getKey()) == null).forEach(
                entry -> mpvs.add(entry.getKey(), entry.getValue()));
        }
    }
    
}
