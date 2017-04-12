package com.senhome.api.cms.enums;

import com.senhome.shell.common.lang.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CmsDetailType implements BaseEnum {

    GROUP(1, "组合搭配模块"),
    PRODUCT_THREE(2, "商品展示区域(1行3张)"),
    NAVIGATION(3, "导航"),
    CAROUSEL(4, "轮播图模块"),
    SCROLL(5, "商品滑动模块"),
    PRODUCT_TWO(6, "商品展示区域(1行2张)");

    private int code;
    private String name;

    public static CmsDetailType valueOf(int code) {

        for (CmsDetailType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }

        return null;
    }
}
