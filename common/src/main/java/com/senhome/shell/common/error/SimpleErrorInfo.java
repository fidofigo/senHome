package com.senhome.shell.common.error;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
public class SimpleErrorInfo implements ErrorInfo {

    private String code;

    private String subcode;

    private String errorMessage;

    @Override
    public String getErrorCode() {

        if (StringUtils.isEmpty(subcode)) {
            return code;
        }

        return code + ":" + subcode;

    }

    private SimpleErrorInfo(String code, String subcode, String errorMessage) {

        this.code = StringUtils.trimToEmpty(code);
        this.subcode = StringUtils.trimToEmpty(subcode);
        this.errorMessage = StringUtils.trimToEmpty(errorMessage);
    }

    public static SimpleErrorInfo of(String code, String errorMessage) {
        return of(code, null, errorMessage);
    }

    public static SimpleErrorInfo of(String code, String subcode, String errorMessage) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(code), "Argument code is empty.");
        Preconditions.checkArgument(StringUtils.isNotEmpty(errorMessage), "Argument errorMessage is empty.");

        return new SimpleErrorInfo(code, subcode, errorMessage);
    }

    public SimpleErrorInfo of(Object... errorMessageArgs) {

        Preconditions.checkArgument(ArrayUtils.isNotEmpty(errorMessageArgs), "Argument errorMessageArgs is empty.");

        return of(this.code, this.subcode, String.format(this.errorMessage, errorMessageArgs));
    }

    public SimpleErrorInfo subcode(String subcode) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(subcode), "Argument subcode is empty.");

        return of(this.code, subcode, this.errorMessage);
    }

    public SimpleErrorInfo subcode(String subcode, String errorMessage) {

        Preconditions.checkArgument(StringUtils.isNotEmpty(subcode), "Argument subcode is empty.");
        Preconditions.checkArgument(StringUtils.isNotEmpty(errorMessage), "Argument errorMessage is empty.");

        return of(this.code, subcode, errorMessage);
    }

}
