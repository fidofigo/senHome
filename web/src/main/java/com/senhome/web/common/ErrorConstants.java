package com.senhome.web.common;

/**
 * 错误码常量类
 *
 * @author kolor
 */
public class ErrorConstants {
    /**
     * 成功
     */
    public static final String SUCCESS = "SUCCESS";
    /**
     * 部分成功
     */
    public static final String PARTIAL_SUCCESS = "PARTIAL_SUCCESS";
    /**
     * 系统异常
     */
    public static final String ERR_SYS = "ERR_SYS";
    /**
     * 限流
     */
    public static final String ERR_ERR_FLOW_LIMIT = "ERR_FLOW_LIMIT";
    /**
     * 参数异常
     */
    public static final String ERR_ILLEGAL_ARGUMENT = "ERR_ILLEGAL_ARGUMENT";
    /**
     * 结果不存在
     */
    public static final String ERR_NOT_EXISTED = "ERR_NOT_EXISTED";
    /**
     * 已过期
     */
    public static final String ERR_EXPIRED = "ERR_EXPIRED";
    /**
     * 发短信失败
     */
    public static final String ERR_SEND_SMS_FAILED = "ERR_SEND_SMS_FAILED";

    /**
     * <p>
     * 登陆token 失效
     * </p>
     */
    public static final String ERR_TOKEN_EXPIRED = "ERR_TOKEN_EXPIRED";

    /**
     * HTTP请求出现异常
     */
    public static final String HTTP_ERR = "HTTP_ERR";

    /**
     * 鉴权没有登陆
     */
    public static final String AUTH_NO_LOGIN = "AUTH_NO_LOGIN";

    /**
     * <p>
     * token失效
     * </p>
     */
    public static final String AUTH_TOKEN_EXPIRED = "AUTH_TOKEN_EXPIRED";

    /**
     * 鉴权没有登陆
     */
    public static final String AUTH_LOGIN = "AUTH_LOGIN";

    /**
     * 鉴权免登陆
     */
    public static final String AUTH_FREE = "AUTH_FREE";

}
