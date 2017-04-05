package com.senhome.api.cms.enums;

import com.senhome.shell.common.lang.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CmsGroupType implements BaseEnum {

    PRODUCT(1, "商品"),
    GROUP(2, "组合"),
    CMS(3, "cms"),
    NON_HREF(4, "点击不跳转");

    private int code;
    private String name;

    public static CmsGroupType valueOf(int code) {

        for (CmsGroupType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }

        return null;
    }
}
