package com.senhome.service.cms.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class CmsGroupDetail extends BaseDO{

    /**
     * cmsDetailId
     */
    private Integer cmsDetailId;

    /**
     * 排序值
     */
    private Short sequence;

    /**
     * 是否可用；0：否，1：是
     */
    private Byte isAvailable;

    /**
     * 布局方式，1：一行1张，2：一行2张，3：一行3张，4：一行4张
     */
    private int layoutType;

    /**
     * 第一张备注
     */
    private String oneRemark;

    /**
     * 第一张图片url
     */
    private String oneImageUrl;

    /**
     * 第一张图片宽度
     */
    private Integer oneImageWidth;

    /**
     * 第一张图片高度
     */
    private Integer oneImageHeight;

    /**
     * 第一张关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int oneType;

    /**
     * 第一张关联对象Id
     */
    private Integer oneDisplayId;

    /**
     * 第二张备注
     */
    private String twoRemark;

    /**
     * 第二张图片url
     */
    private String twoImageUrl;

    /**
     * 第二张图片宽度
     */
    private Integer twoImageWidth;

    /**
     * 第二张图片高度
     */
    private Integer twoImageHeight;

    /**
     * 第二张关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int twoType;

    /**
     * 第二张关联对象Id
     */
    private Integer twoDisplayId;

    /**
     * 第三张备注
     */
    private String threeRemark;

    /**
     * 第三张图片url
     */
    private String threeImageUrl;

    /**
     * 第三张图片宽度
     */
    private Integer threeImageWidth;

    /**
     * 第三张图片高度
     */
    private Integer threeImageHeight;

    /**
     * 第三张关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int threeType;

    /**
     * 第三张关联对象Id
     */
    private Integer threeDisplayId;

    /**
     * 第四张备注
     */
    private String fourRemark;

    /**
     * 第四张图片url
     */
    private String fourImageUrl;

    /**
     * 第四张图片宽度
     */
    private Integer fourImageWidth;

    /**
     * 第四张图片高度
     */
    private Integer fourImageHeight;

    /**
     * 第四张关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int fourType;

    /**
     * 第四张关联对象Id
     */
    private Integer fourDisplayId;
}
