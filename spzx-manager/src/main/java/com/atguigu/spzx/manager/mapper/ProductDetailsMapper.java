package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;

public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails getById(Long id);

    void updateById(ProductDetails productDetails);

    void deleteById(Long id);
}
