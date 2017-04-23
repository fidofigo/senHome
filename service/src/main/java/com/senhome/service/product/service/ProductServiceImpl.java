package com.senhome.service.product.service;

import com.senhome.product.api.ProductServiceApi;
import com.senhome.product.model.ProductDTO;
import com.senhome.service.product.business.ProductBusiness;
import com.senhome.service.product.dal.dataobject.Product;
import com.senhome.shell.common.result.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fidofigo on 17/4/12.
 */
@Component
@Service("productServiceApi")
public class ProductServiceImpl implements ProductServiceApi{

    @Autowired
    private ProductBusiness productBusiness;

    @Override
    public ViewResult getProduct(Integer id) {
        ViewResult viewResult = ViewResult.ofSuccess();

        if(id == null){
            viewResult.setSuccess(false);
            viewResult.setMessage("商品ID无效！");
            return viewResult;
        }

        Product product = productBusiness.getProduct(id);

        if(product == null) {
            viewResult.setSuccess(false);
            viewResult.setMessage("商品无效！");
            return viewResult;
        }

        List<String> productImageList = new ArrayList<>();

        if(product.getImage1() != null) {
            productImageList.add(product.getImage1());
        }

        if(product.getImage2() != null) {
            productImageList.add(product.getImage2());
        }

        if(product.getImage3() != null) {
            productImageList.add(product.getImage3());
        }

        if(product.getImage4() != null) {
            productImageList.add(product.getImage4());
        }

        if(product.getImage5() != null) {
            productImageList.add(product.getImage5());
        }

        if(product.getImage6() != null) {
            productImageList.add(product.getImage6());
        }

        if(product.getImage7() != null) {
            productImageList.add(product.getImage7());
        }

        if(product.getImage8() != null) {
            productImageList.add(product.getImage8());
        }

        if(product.getImage9() != null) {
            productImageList.add(product.getImage9());
        }

        if(product.getImage10() != null) {
            productImageList.add(product.getImage10());
        }

        if(product.getImage11() != null) {
            productImageList.add(product.getImage11());
        }

        if(product.getImage12() != null) {
            productImageList.add(product.getImage12());
        }

        if(product.getImage13() != null) {
            productImageList.add(product.getImage13());
        }

        if(product.getImage14() != null) {
            productImageList.add(product.getImage14());
        }

        if(product.getImage15() != null) {
            productImageList.add(product.getImage15());
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductImageList(productImageList);

        return ViewResult.ofSuccess().putDefaultModel(productDTO);
    }
}
