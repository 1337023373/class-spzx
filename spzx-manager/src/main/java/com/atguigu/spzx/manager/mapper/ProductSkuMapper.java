package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductSkuMapper {
    List<ProductSku> getproductSkuList();

    void save(ProductSku productSku);

    List<ProductSku> getById(Long id);

    void updateById(ProductSku sku);

    void deleteById(Long id);
}
