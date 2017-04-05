package com.senhome.api.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmsDetailDTO{
    /**
     * 模版类型，1：组合搭配模块；2：商品展示区域(1行3张)；3：导航；4：滑动banner；5：滑动商品 6：商品展示区域(1行2张)
     */
    private int type;

    /**
     * cms详情id
     */
    private Integer cmsId;

    /**
     * 排序值
     */
    private Short sequence;

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
     * type=2,6标题1关联url
     */
    private String oneUrl;

    /**
     * type=2,6标题2关联url
     */
    private String twoUrl;

    /**
     * 商品列表,type=2,6时有效
     */
    private List<CmsProductDTO> productList;

    /**
     * 组合商品列表,type=1时有效
     */
    private List<CmsGroupDetailDTO> groupProductList;

    /**
     * 导航列表,type=4时有效
     */
    private List<CmsNavigationDTO> navigationList;

    /**
     * 轮播图片信息, type=8时有效
     */
    private List<CmsGroupProductDTO> carouselDetailList;
}
