package com.senhome.shell.common.enums;

/**
 * Created by luliru on 2017/2/28.
 */
public enum OS
{

    IOS("1"),
    ANDROID("2");

    public static OS getByValue(String value){
        if(IOS.value.equals(value)){
            return IOS;
        }
        return ANDROID;
    }

    private String value;

    OS(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
