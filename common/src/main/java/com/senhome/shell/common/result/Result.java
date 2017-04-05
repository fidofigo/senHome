package com.senhome.shell.common.result;

import com.google.common.collect.Lists;
import com.senhome.shell.common.error.ErrorInfo;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@ToString
public class Result<T> {

    @Getter
    @Setter
    private boolean success = true;

    @Getter
    @Setter
    private T model;

    @Getter
    private final List<ErrorInfo> errorInfos = Lists.newArrayList();

    @Getter
    @Setter
    private Exception exception;

    private Result(boolean success, T model) {
        this.success = success;
        this.model = model;
    }

    public static <T> Result<T> of(boolean success, T model) {
        return new Result<T>(success, model);
    }

    public static <T> Result<T> of() {
        return of(true);
    }

    public static <T> Result<T> of(boolean success) {
        return of(success, null);
    }

    public static <T> Result<T> of(T module) {
        return of(true, module);
    }

    public static <T> Result<T> of(@NonNull ErrorInfo errorInfo) {

        Result<T> result = of(false);

        result.addErrorInfo(errorInfo);

        return result;
    }

    public static <T> Result<T> of(@NonNull List<ErrorInfo> errorInfos) {

        if (errorInfos.isEmpty()) {
            return of(true);
        }

        Result<T> result = of(false);

        result.addErrorInfos(errorInfos);

        return result;
    }

    public Result<T> addErrorInfo(@NonNull ErrorInfo errorInfo) {
        this.success = false;
        this.errorInfos.add(errorInfo);

        return this;
    }

    public Result<T> addErrorInfos(@NonNull List<ErrorInfo> errorInfos) {

        if (errorInfos.isEmpty()) {
            return this;
        }

        this.success = false;
        this.errorInfos.addAll(errorInfos);

        return this;
    }

    public List<String> getErrorCodes() {

        List<String> errorCodes = Lists.newArrayListWithExpectedSize(errorInfos.size());

        for (ErrorInfo errorInfo : errorInfos) {
            if (StringUtils.isNotEmpty(errorInfo.getErrorCode())) {
                errorCodes.add(errorInfo.getErrorCode());
            }
        }

        return errorCodes;
    }

    public List<String> getErrorMessages() {

        List<String> errorMessages = Lists.newArrayListWithExpectedSize(errorInfos.size());

        for (ErrorInfo errorInfo : errorInfos) {
            if (StringUtils.isNotEmpty(errorInfo.getErrorMessage())) {
                errorMessages.add(errorInfo.getErrorMessage());
            }
        }

        return errorMessages;
    }

    public String getErrorMessage() {
        return StringUtils.join(getErrorMessages(), ";");
    }

    public String getFirstErrorMessage() {

        if (CollectionUtils.isEmpty(errorInfos)) {
            return null;
        }

        return errorInfos.get(0).getErrorMessage();
    }

    public boolean hasModel() {
        return model != null;
    }

    public Result<T> mergeErrorInfos(@NonNull Result<?> result) {

        if (result.isSuccess() || CollectionUtils.isEmpty(result.getErrorInfos())) {
            return this;
        }

        addErrorInfos(result.getErrorInfos());

        return this;
    }

}
