package com.senhome.product.api;

import com.senhome.shell.common.result.ViewResult;
import org.springframework.stereotype.Component;

/**
 * Created by fidofigo on 17/4/12.
 */
@Component("productServiceApi")
public interface ProductServiceApi {
    /**
     * 获取商品信息
     */
    ViewResult getProduct(Integer id);
}
