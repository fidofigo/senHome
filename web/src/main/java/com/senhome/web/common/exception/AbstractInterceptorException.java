package com.senhome.web.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractInterceptorException extends RuntimeException {

    private String errorMessage;

    public AbstractInterceptorException(String errorMessage){
        this.errorMessage = errorMessage;
    }

}
