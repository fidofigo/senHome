/**
 *
 */
package com.senhome.web.common;

import java.io.Serializable;

/**
 * 接口调用结果包装类</br> 请先通过{@link #isSuccess()}判断是否成功</br> 如果成功，则通过{@link #getData()}
 * 获取数据</br> 如果失败，则通过{@link #getCode()}和{@link #getMessage()}获取错误码和错误描述
 *
 * @author kolor
 */
public class Result<TData> implements Serializable {
    private static final long serialVersionUID = -166079700594141525L;

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回结果
     */
    private TData data;
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误描述
     */
    private String message;

    public String getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 请通过{@link ErrorConstants}常量类定义的错误码
     * @return
     */
    public Result<TData> setCode(String code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<TData> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public TData getData() {
        return data;
    }

    public Result<TData> setData(TData data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<TData> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Result [success=");
        builder.append(success);
        builder.append(", data=");
        builder.append(data);
        builder.append(", code=");
        builder.append(code);
        builder.append(", message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }

}
