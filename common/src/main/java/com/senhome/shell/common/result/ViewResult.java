package com.senhome.shell.common.result;

import com.google.common.collect.Maps;
import com.senhome.shell.common.json.JsonUtil;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@NoArgsConstructor
@ToString
public class ViewResult {

    private static String DEFAULT_MODEL_KEY = "model";

    private static String CODE_SUCCESS = "success";
    private static String CODE_FAIL = "error";

    private static String MESSAGE_SUCCESS = "success!";
    private static String MESSAGE_FAIL = "fail!";
    private static String MESSAGE_ERROR = "error!";

    private static String KEY_CODE = "code";
    private static String KEY_MESSAGE = "message";

    @Getter
    @Setter
    private boolean success = true;

    @Getter
    @Setter
    private String message = MESSAGE_SUCCESS;

    private Map<String, Object> data = Maps.newLinkedHashMap();

    @Getter
    @Setter
    private Object model;

    public ViewResult putDefaultModel(Object model) {
        this.model = model;
        return this;
    }

    public ViewResult putData(@NonNull String key, Object model) {
        data.put(key, model);
        return this;
    }

    public Object getData(@NonNull String key) {
        return data.get(key);
    }

    public static <T> ViewResult ofResult(@NonNull Result<T> result) {
        return ofResult(result, null);
    }

    public static <T> ViewResult ofResult(@NonNull Result<T> result, String message) {

        ViewResult viewResult = ofSuccess();

        if (!result.isSuccess()) {
            viewResult.setSuccess(false);
            viewResult.setMessage(StringUtils.isNotEmpty(message) ? message : result.getErrorMessage());
        } else {
            viewResult.putDefaultModel(result.getModel());
            viewResult.setMessage(StringUtils.isNotEmpty(message) ? message : MESSAGE_SUCCESS);
        }

        return viewResult;

    }

    public <T> ViewResult mergeFailResult(@NonNull Result<T> result) {

        if (result.isSuccess()) {
            return this;
        }

        this.setSuccess(false);
        this.setMessage(result.getErrorMessage());
        this.setModel(null);

        return this;
    }

    public static ViewResult ofSuccess() {
        return ofSuccess(MESSAGE_SUCCESS);
    }

    public static ViewResult ofSuccess(String message) {

        ViewResult viewResult = new ViewResult();

        viewResult.setSuccess(true);
        viewResult.setMessage(StringUtils.isEmpty(message) ? MESSAGE_SUCCESS : message);

        return viewResult;
    }

    public static ViewResult ofFail() {
        return ofFail(MESSAGE_FAIL);
    }

    public static ViewResult ofFail(String message) {

        ViewResult viewResult = new ViewResult();

        viewResult.setSuccess(false);
        viewResult.setMessage(StringUtils.isEmpty(message) ? MESSAGE_FAIL : message);

        return viewResult;
    }

    public static ViewResult ofException() {
        return ofFail(MESSAGE_ERROR);
    }

    public String toJson() {

        Map<String, Object> json = Maps.newLinkedHashMap();

        json.put(KEY_CODE, isSuccess() ? CODE_SUCCESS : CODE_FAIL);
        json.put(KEY_MESSAGE, getMessage());
        json.put(DEFAULT_MODEL_KEY, getModel());

        return JsonUtil.objectToJson(json);

    }

}
