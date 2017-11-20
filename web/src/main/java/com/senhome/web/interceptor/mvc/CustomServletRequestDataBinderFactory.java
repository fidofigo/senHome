/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.senhome.web.interceptor.mvc;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.List;

/**
 * 自定义 ServletRequestDataBinderFactory
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2016/11/26
 * @since 1.0
 */
public class CustomServletRequestDataBinderFactory extends ServletRequestDataBinderFactory
{

    /**
     * @param binderMethods
     * @param initializer
     */
    public CustomServletRequestDataBinderFactory(List<InvocableHandlerMethod> binderMethods, WebBindingInitializer initializer) {
        super(binderMethods, initializer);
    }

    /**
     * @see ServletRequestDataBinderFactory#createBinderInstance(Object,
     *      String, NativeWebRequest)
     */
    @Override
    protected ServletRequestDataBinder createBinderInstance(Object target, String objectName, NativeWebRequest request) {
        return new CustomServletRequestDataBinder(target, objectName);
    }

}
