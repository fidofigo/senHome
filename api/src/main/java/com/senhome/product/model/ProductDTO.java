package com.senhome.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by fidofigo on 17/4/12.
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDTO {

    /**
     * 商品图片详情列表
     */
    private List<String> productImageList;
}
