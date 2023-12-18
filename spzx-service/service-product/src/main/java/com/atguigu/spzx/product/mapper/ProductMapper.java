package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Product;

public interface ProductMapper {
    Product findProductById(Long productId);
}
