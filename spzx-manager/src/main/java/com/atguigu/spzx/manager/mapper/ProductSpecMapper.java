package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;

import java.util.List;

public interface ProductSpecMapper {
    List<ProductSpec> getProductSpecAll();

    void addProductSpec(ProductSpec productSpec);

    void updateProductSpec(ProductSpec productSpec);

    void deleteProductSpec(Long id);

}
