package com.senhome.shell.common.dal.constant;

import com.senhome.shell.common.lang.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonType implements BaseEnum{

    INTERIOR_DESIGN(1, "软装风格"),
    SPATIAL_DISTRIBUTION(2, "空间布局"),
    BUYER(3, "买手街");

    private int code;
    private String name;

    public static CommonType valueOf(int code) {

        for (CommonType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }

        return null;
    }
}
