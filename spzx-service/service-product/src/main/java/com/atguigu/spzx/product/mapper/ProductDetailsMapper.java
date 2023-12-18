package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;

import java.util.List;

public interface ProductDetailsMapper {
    List<ProductDetails> findProductDetailsById(Long productId);
}
