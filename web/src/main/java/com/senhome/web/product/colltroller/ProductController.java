package com.senhome.web.product.colltroller;

import com.senhome.api.product.api.ProductServiceApi;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.product.param.ProductParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by fidofigo on 17/4/12.
 */
@RestController
@RequestMapping("/webNative/product")
public class ProductController {

    @Resource
    private ProductServiceApi productServiceApi;

    @RequestMapping(value = "/detail")
    public Object detail(ProductParam productParam){
        ViewResult result = productServiceApi.getProduct(productParam.getId());
        if (!result.isSuccess()) {
            String msg = "Invoke productServiceApi.getProduct " + result.getMessage();
            throw new RuntimeException(msg);
        }

        return result.toJson();
    }
}
