package com.senhome.web.common.exception;

public class TokenInvalidException extends AbstractInterceptorException {

    public TokenInvalidException(String errorMessage) {
        super(errorMessage);
    }

}
