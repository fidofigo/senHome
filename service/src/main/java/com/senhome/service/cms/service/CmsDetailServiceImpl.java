package com.senhome.service.cms.service;

import com.senhome.api.cms.api.CmsDetailServiceApi;
import com.senhome.api.cms.enums.CmsDetailType;
import com.senhome.api.cms.enums.CmsGroupType;
import com.senhome.api.cms.model.*;
import com.senhome.service.cms.business.CmsDetailBusiness;
import com.senhome.service.cms.dal.dataobject.Cms;
import com.senhome.service.cms.dal.dataobject.CmsCarouselDetail;
import com.senhome.service.cms.dal.dataobject.CmsDetail;
import com.senhome.service.cms.dal.dataobject.CmsGroupDetail;
import com.senhome.service.group.business.GroupBusiness;
import com.senhome.service.group.dal.dataobject.Group;
import com.senhome.service.product.business.ProductBusiness;
import com.senhome.service.product.dal.dataobject.Product;
import com.senhome.shell.common.result.Result;
import com.senhome.shell.common.result.ViewResult;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by fidofigo on 17/2/24.
 */
@Component
@Service("cmsDetailServiceApi")
public class CmsDetailServiceImpl implements CmsDetailServiceApi {

    @Autowired
    private CmsDetailBusiness cmsDetailBusiness;

    @Autowired
    private ProductBusiness productBusiness;

    @Autowired
    private GroupBusiness groupBusiness;

    @Override
    public ViewResult getCmsDetail(Integer cmsId)
    {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(cmsId == null){
            viewResult.setSuccess(false);
            viewResult.setMessage("cmsID无效！");
            return viewResult;
        }

        //获取cms系统
        Cms cms = cmsDetailBusiness.getCmsById(cmsId);
        if(cms == null){
            viewResult.setSuccess(false);
            viewResult.setMessage("cmsID无效！");
            return viewResult;
        }

        CmsDTO cmsDTO = new CmsDTO();

        List<CmsDetailDTO> cmsDetailDTOs = new ArrayList<>();
        List<CmsDetail> cmsDetails = cmsDetailBusiness.getCmsDetailList(cms.getId());

        //获取所有组合搭配模版
        List<CmsDetailDTO> detailDTOGroupList = getCmsGroups(cmsDetails.stream().filter(e -> (e.getType() == CmsDetailType.GROUP.getCode())).collect(Collectors.toList()));
        if(detailDTOGroupList != null)
            cmsDetailDTOs.addAll(detailDTOGroupList);

        //获取商品展示区域
        List<CmsDetailDTO> detailDTOProductList = getCmsProducts(cmsDetails.stream().filter(e -> (e.getType() == CmsDetailType.PRODUCT_THREE.getCode() || e.getType() == CmsDetailType.PRODUCT_TWO.getCode())).collect(Collectors.toList()));
        if(detailDTOProductList != null)
            cmsDetailDTOs.addAll(detailDTOProductList);

        //轮播区域
        CmsDetailDTO detailDTOCarousel = getCmsCarousel(cmsDetails.stream().filter(e -> e.getType() == CmsDetailType.CAROUSEL.getCode()).findFirst().orElse(null));
        if(detailDTOCarousel != null)
            cmsDetailDTOs.add(detailDTOCarousel);

        //商品滑动区域
        List<CmsDetailDTO> detailDTOScrollList = getCmsScroll(cmsDetails.stream().filter(e -> e.getType() == CmsDetailType.SCROLL.getCode()).collect(Collectors.toList()));
        if(detailDTOScrollList != null)
            cmsDetailDTOs.addAll(detailDTOScrollList);

        //导航区域
        CmsDetailDTO detailDTONavigation = getCmsNavigation(cmsDetails);
        if(detailDTONavigation != null)
            cmsDetailDTOs.add(detailDTONavigation);

        cmsDetailDTOs.sort((p1, p2) -> p1.getSequence().compareTo(p2.getSequence()));

        cmsDTO.setCmsDetailList(cmsDetailDTOs);

        return ViewResult.ofSuccess().putDefaultModel(cmsDTO);
    }

    /**
     * 获取cms商品组合模块
     * @param detailList
     * @return
     */
    private List<CmsDetailDTO> getCmsProducts(List<CmsDetail> detailList)
    {
        if(detailList.size() == 0)
            return null;

        List<CmsDetailDTO> detailDTOs = new ArrayList<>();

        //获取所有组合
        List<Integer> groupIds = detailList.stream().map(CmsDetail::getRelationId).collect(Collectors.toList());
        Map<Integer, List<Integer>> activityProductIdsMap = groupBusiness.findProductIdsGroupIdsByGroupIds(groupIds);

        //获取所有组合商品
        List<Integer> productIds = groupBusiness.getProductIdsByGroupId(groupIds);
        List<Product> productList = productBusiness.getGroupListByIds(productIds);
        Map<Integer, Product> productMap = productList.parallelStream().collect(Collectors.toMap(Product::getId, e -> e));

        for(CmsDetail detail : detailList) {
            CmsDetailDTO detailDTO = new CmsDetailDTO();
            detailDTO.setCmsId(detail.getId());
            detailDTO.setSequence(detail.getSequence());
            detailDTO.setType(detail.getType());
            detailDTO.setOneImage(detail.getOneImage());
            detailDTO.setTwoImage(detail.getTwoImage());
            detailDTO.setOneImageHeight(detail.getOneImageHeight());
            detailDTO.setTwoImageHeight(detail.getTwoImageHeight());
            detailDTO.setOneColor(detail.getOneColor());
            detailDTO.setOneUrl(getJumpLinkUrl(detail.getOneType(), detail.getOneDisplayId()));
            detailDTO.setTwoUrl(getJumpLinkUrl(detail.getTwoType(), detail.getTwoDisplayId()));

            List<Integer> ids = activityProductIdsMap.get(detail.getRelationId());

            if(ids == null)
                continue;

            List<CmsProductDTO> dtoList = new ArrayList<>();

            for(Integer id : ids) {
                Product product = productMap.get(id);

                if(product == null)
                    continue;

                CmsProductDTO cmsProductDTO = new CmsProductDTO();
                cmsProductDTO.setName(product.getName());
                cmsProductDTO.setImage(product.getImage1());
                cmsProductDTO.setUrl(getJumpLinkUrl(CmsGroupType.PRODUCT.getCode(), product.getId()));

                dtoList.add(cmsProductDTO);
            }

            detailDTO.setProductList(dtoList);

            detailDTOs.add(detailDTO);
        }

        return detailDTOs;
    }

    /**
     * 获取促销模版滑动商品模块
     * @param detailList
     * @return
     */
    private List<CmsDetailDTO> getCmsScroll(List<CmsDetail> detailList)
    {
        if(detailList.size() == 0)
            return null;

        List<CmsDetailDTO> detailDTOs = new ArrayList<>();

        //获取所有组合
        List<Integer> groupIds = detailList.stream().map(CmsDetail::getRelationId).collect(Collectors.toList());
        Map<Integer, List<Integer>> activityProductIdsMap = groupBusiness.findProductIdsGroupIdsByGroupIds(groupIds);

        //获取所有组合商品
        List<Integer> productIds = groupBusiness.getProductIdsByGroupId(groupIds);
        List<Product> productList = productBusiness.getGroupListByIds(productIds);
        Map<Integer, Product> productMap = productList.parallelStream().collect(Collectors.toMap(Product::getId, e -> e));

        for(CmsDetail detail : detailList) {
            CmsDetailDTO detailDTO = new CmsDetailDTO();
            detailDTO.setCmsId(detail.getId());
            detailDTO.setSequence(detail.getSequence());
            detailDTO.setType(detail.getType());
            detailDTO.setOneImage(detail.getOneImage());
            detailDTO.setOneImageHeight(detail.getOneImageHeight());
            detailDTO.setOneUrl(getJumpLinkUrl(detail.getOneType(), detail.getOneDisplayId()));

            List<Integer> ids = activityProductIdsMap.get(detail.getRelationId());

            if(ids == null)
                continue;

            if(ids.size() > 6) {
                ids = ids.subList(0, 6);
            }

            List<CmsProductDTO> dtoList = new ArrayList<>();

            for(Integer id : ids) {
                Product product = productMap.get(id);

                if(product == null)
                    continue;

                CmsProductDTO cmsProductDTO = new CmsProductDTO();
                cmsProductDTO.setName(product.getName());
                cmsProductDTO.setUrl(getJumpLinkUrl(CmsGroupType.PRODUCT.getCode(), product.getId()));
                cmsProductDTO.setImage(product.getImage1());

                dtoList.add(cmsProductDTO);
            }

            detailDTO.setProductList(dtoList);

            detailDTOs.add(detailDTO);
        }

        return detailDTOs;
    }

    /**
     * 获取cms轮播
     * @param detail
     * @return
     */
    private CmsDetailDTO getCmsCarousel(CmsDetail detail)
    {
        if(detail == null)
            return null;

        CmsDetailDTO detailDTO = new CmsDetailDTO();
        detailDTO.setCmsId(detail.getId());
        detailDTO.setSequence(detail.getSequence());
        detailDTO.setType(detail.getType());

        List<CmsGroupProductDTO> cmsGroupProductDTOs = new ArrayList<>();
        List<CmsCarouselDetail> carouselDetails = cmsDetailBusiness.getCmsCarouselDetailList(detail.getId());

        for (CmsCarouselDetail cmsCarouselDetail : carouselDetails) {
            CmsGroupProductDTO cmsGroupProductDTO =  new CmsGroupProductDTO();
            cmsGroupProductDTO.setImage(cmsCarouselDetail.getImage());
            cmsGroupProductDTO.setHeight(cmsCarouselDetail.getImageHeight());
            cmsGroupProductDTO.setWidth(cmsCarouselDetail.getImageWidth());
            cmsGroupProductDTO.setUrl(getJumpLinkUrl(cmsCarouselDetail.getType(), cmsCarouselDetail.getDisplayId()));

            cmsGroupProductDTOs.add(cmsGroupProductDTO);
        }

        detailDTO.setCarouselDetailList(cmsGroupProductDTOs);

        return detailDTO;
    }

    /**
     * 获取所有促销模版组合搭配
     * @param detailList
     * @return
     */
    private List<CmsDetailDTO> getCmsGroups(List<CmsDetail> detailList)
    {
        if(detailList.size() == 0)
            return null;

        List<CmsDetailDTO> detailDTOs = new ArrayList<>();

        //获取所有cms组合搭配详情
        List<Integer> CmsDetailIds = detailList.stream().map(CmsDetail::getId).collect(Collectors.toList());
        List<CmsGroupDetail> groupDetailList = cmsDetailBusiness.getCmsGroupDetailListByIds(CmsDetailIds);

        for (CmsDetail detail : detailList) {
            CmsDetailDTO detailDTO = new CmsDetailDTO();
            detailDTO.setCmsId(detail.getId());
            detailDTO.setSequence(detail.getSequence());
            detailDTO.setType(detail.getType());

            List<CmsGroupDetail> groupDetails = groupDetailList.stream().filter(e -> e.getCmsId().equals(detail.getId())).collect(Collectors.toList());

            groupDetails.sort((p1, p2) -> p1.getSequence().compareTo(p2.getSequence()));

            if(CollectionUtils.isEmpty(groupDetails))
                continue;

            List<CmsGroupDetailDTO> groupDetailDTOs = new ArrayList<>();

            for (CmsGroupDetail groupDetail : groupDetails) {
                CmsGroupDetailDTO groupDetailDTO = new CmsGroupDetailDTO();
                groupDetailDTO.setLayoutType(groupDetail.getLayoutType());

                List<CmsGroupProductDTO> groupProductDTOs = new ArrayList<>();
                CmsGroupProductDTO groupProductDTO = new CmsGroupProductDTO();
                int layoutType = groupDetail.getLayoutType();

                if(layoutType > 0) {
                    groupProductDTOs.add(createGroupProductDTO(groupDetail.getOneImageUrl(), groupDetail.getOneType(), groupDetail.getOneDisplayId(), groupDetail.getOneImageHeight(), groupDetail.getOneImageWidth()));
                }if(layoutType > 1) {
                    groupProductDTOs.add(createGroupProductDTO(groupDetail.getTwoImageUrl(), groupDetail.getTwoType(), groupDetail.getTwoDisplayId(), groupDetail.getTwoImageHeight(), groupDetail.getTwoImageWidth()));
                }if(layoutType > 2) {
                    groupProductDTOs.add(createGroupProductDTO(groupDetail.getThreeImageUrl(), groupDetail.getThreeType(), groupDetail.getThreeDisplayId(), groupDetail.getThreeImageHeight(), groupDetail.getThreeImageWidth()));
                }if(layoutType > 3) {
                    groupProductDTOs.add(createGroupProductDTO(groupDetail.getFourImageUrl(), groupDetail.getFourType(), groupDetail.getFourDisplayId(), groupDetail.getFourImageHeight(), groupDetail.getFourImageWidth()));
                }

                groupDetailDTO.setGroupProductDetailList(groupProductDTOs);
                groupDetailDTOs.add(groupDetailDTO);
            }

            detailDTO.setGroupProductList(groupDetailDTOs);

            detailDTOs.add(detailDTO);
        }

        return detailDTOs;
    }

    /**
     * 获取促销模版导航栏
     * @param cmsDetails
     * @return
     */
    private CmsDetailDTO getCmsNavigation(List<CmsDetail> cmsDetails)
    {
        CmsDetailDTO detailDTO = new CmsDetailDTO();

        CmsDetail detail = cmsDetails.stream().filter(e -> e.getType() == CmsDetailType.NAVIGATION.getCode()).findFirst().orElse(null);

        if(detail == null)
            return null;

        detailDTO.setCmsId(detail.getId());
        detailDTO.setType(detail.getType());
        detailDTO.setSequence(detail.getSequence());
        detailDTO.setOneColor(detail.getOneColor());
        detailDTO.setTwoColor(detail.getTwoColor());
        detailDTO.setThreeColor(detail.getThreeColor());

        List<CmsDetail> detailList = cmsDetails.stream().filter(e -> e.getInNavigation() == 1).collect(Collectors.toList());

        List<CmsNavigationDTO> navigationDTOs = new ArrayList<>();
        for(CmsDetail cmsDetail : detailList) {
            CmsNavigationDTO navigationDTO = new CmsNavigationDTO();
            navigationDTO.setCmsId(cmsDetail.getId());
            navigationDTO.setNavigationName(cmsDetail.getNavigationName());
            navigationDTOs.add(navigationDTO);
        }

        detailDTO.setNavigationList(navigationDTOs);

        return detailDTO;
    }

    /**
     * 创建商品详情
     * @param imageUrl
     * @param type
     * @param displayId
     * @param height
     * @param width
     * @return
     */
    private CmsGroupProductDTO createGroupProductDTO(String imageUrl, int type, Integer displayId, Integer height, Integer width)
    {
        CmsGroupProductDTO groupProductDTO = new CmsGroupProductDTO();
        groupProductDTO.setImage(imageUrl);
        groupProductDTO.setUrl(getJumpLinkUrl(type, displayId));
        groupProductDTO.setHeight(height);
        groupProductDTO.setWidth(width);

        return groupProductDTO;
    }

    /**
     * 获取跳转url
     * @param type
     * @param displayId
     * @return
     */
    private String getJumpLinkUrl(int type, Integer displayId){
        if(type == CmsGroupType.PRODUCT.getCode()) {
            return "https://www.senhomelife.com/webNative/product/detail/" + displayId;
        } else if(type == CmsGroupType.GROUP.getCode()) {
            return "https://www.senhomelife.com/webNative/group/detail/" + displayId;
        } else if(type == CmsGroupType.CMS.getCode()) {
            return "https://www.senhomelife.com/webNative/cms/detail/" + displayId;
        }

        return "";
    }
}
