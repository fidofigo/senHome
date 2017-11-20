/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.senhome.web.interceptor.mvc;

import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * 自定义 RequestMappingHandlerAdapter
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2016/11/26
 * @since 1.0
 */
public class CustomRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter
{

    /**
     * @see RequestMappingHandlerAdapter#createDataBinderFactory(List)
     */
    @Override
    protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> binderMethods) throws Exception {
        return new CustomServletRequestDataBinderFactory(binderMethods, getWebBindingInitializer());
    }

}
