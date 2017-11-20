package com.senhome.service.cms.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Data;

@Data
public class CmsDetail extends BaseDO{

    /**
     * cmsId
     */
    private Integer cmsId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序值
     */
    private Short sequence;

    /**
     * 模版类型，1：组合搭配模块；2：商品展示区域(1行3张)；3：导航；4：滑动banner；5：滑动商品 6：商品展示区域(1行2张)
     */
    private int type;

    /**
     * 导航栏名称
     */
    private String navigationName;

    /**
     * 是否出现在导航栏中；0：否，1：是
     */
    private Byte inNavigation;

    /**
     * type=2，6标题1图片
     */
    private String oneImage;

    /**
     * type=2，6标题2图片
     */
    private String twoImage;

    /**
     * type=2，6标题1图片高度
     */
    private Integer oneImageHeight;

    /**
     * type=2，6标题2图片高度
     */
    private Integer twoImageHeight;

    /**
     * type=2，6背景颜色 type=3模块底色
     */
    private String oneColor;

    /**
     * type=3文字颜色
     */
    private String twoColor;

    /**
     * type=3被选中色块
     */
    private String threeColor;

    /**
     * type=2，6标题1关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int oneType;

    /**
     * type=2，6标题1关联对象Id
     */
    private Integer oneDisplayId;

    /**
     * type=2，6标题2关联类型，1：商品，2：组合，3：cms，4：点击不跳转
     */
    private int twoType;

    /**
     * type=2，6标题2关联对象Id
     */
    private Integer twoDisplayId;

    /**
     * 关联id
     */
    private Integer relationId;
}
