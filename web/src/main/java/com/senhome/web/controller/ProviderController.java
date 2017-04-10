package com.senhome.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.senhome.web.controller.AbstractExtController;
import com.senhome.web.common.Result;
import com.senhome.web.view.CompressSupportStringView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class ProviderController extends AbstractExtController {

    private static final Logger LOGGER_ILLEGAL_REQ = LoggerFactory.getLogger("ILLEGAL-REQ");

    public static final long DAYS7 = 1000 * 60 * 60 * 24 * 7;

    /**
     * <p>
     * 排除做数据完整性的接口
     * </p>
     */
    private List<String> excludeUrls = Lists.newArrayList("/h5.html");
    /**
     * jsonp的callback名称
     */
    public static final String KEY_JSONP_CALLBACK = "callback";

    protected View ajaxFail() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "error");
        jsonObject.put("message", "fail");
        return new CompressSupportStringView(jsonObject.toString());
    }

    protected View ajaxFail(String message) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "error");
            jsonObject.put("message", message);
            return new CompressSupportStringView(jsonObject.toString());
        } catch (Exception e) {
            return ajaxFail();
        }
    }

    protected View ajaxSuccess(String message) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "success");
            jsonObject.put("message", message);
            return new CompressSupportStringView(jsonObject.toString());
        } catch (Exception e) {
            return ajaxFail();
        }
    }

    protected ModelAndView returnResult(Result result) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", result.getCode());
        jsonObject.put("message", result.getMessage());
        jsonObject.put("model", result.getData());
        return new ModelAndView(new CompressSupportStringView(jsonObject.toString()));

    }

    protected ModelAndView returnSuccess() {
        return returnSuccess("success");
    }

    protected ModelAndView returnFail() {
        return returnFail("system error");
    }

    protected ModelAndView returnFail(String message) {
        return new ModelAndView(ajaxFail(message));
    }

    protected ModelAndView returnSuccess(String message) {
        return new ModelAndView(ajaxSuccess(message));
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 允许二级域名跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 想要跨域共享cookie时，前端请求头中带有withCredentials=true参数，这里也需要设置

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Request-Headers",
                "token, Content-Type, Authorization, Accept, X-Requested-With");
        response.setHeader("Access-Control-Max-Age", "30");
        //
        return processBiz(request, response);

    }

    protected abstract ModelAndView processBiz(HttpServletRequest request, HttpServletResponse response)
            throws Exception;

}
