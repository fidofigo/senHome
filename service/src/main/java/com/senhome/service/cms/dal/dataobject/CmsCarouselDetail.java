package com.senhome.service.cms.dal.dataobject;

import com.senhome.shell.common.dal.domain.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmsCarouselDetail extends BaseDO{

    /**
     * cmsId
     */
    private Integer cmsId;

    /**
     * 排序值
     */
    private Short sequence;

    /**
     * 关联类型，1：商品，2：组合；3：cms；4：点击不跳转
     */
    private int type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片
     */
    private String image;

    /**
     * 图片跨度
     */
    private Integer imageWidth;

    /**
     * 图片高度
     */
    private Integer imageHeight;

    /**
     * 关联对象Id
     */
    private Integer displayId;
}
