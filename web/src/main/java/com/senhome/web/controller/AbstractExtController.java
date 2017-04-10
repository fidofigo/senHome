package com.senhome.web.controller;

import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;

/**
 * 支持更多扩展功能的Controller
 *
 * @author kolor
 */
public abstract class AbstractExtController extends AbstractController {

    /**
     * 有前置nginx时会设置真实客户端IP到此字段
     */
    public static final String HEADER_X_REAL_IP = "X-Real-IP";
    /**
     * nginx针对H5域名请求添加此参数，取值为true
     */
    public static final String HEADER_FROM_H5 = "From-H5";

    /**
     * 判断请求是否来自H5
     *
     * @param request
     * @return
     */
    protected boolean isFromH5(HttpServletRequest request) {
        return "true".equalsIgnoreCase(request.getHeader(HEADER_FROM_H5));
    }

}
