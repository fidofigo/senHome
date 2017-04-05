package com.senhome.shell.common.error;

public interface ErrorInfo {

    String getCode();

    String getSubcode();

    String getErrorCode();

    String getErrorMessage();

    ErrorInfo of(Object... errorMessageArgs);

    ErrorInfo subcode(String subcode);

    ErrorInfo subcode(String subcode, String errorMessage);

}
