package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> GetProductSpecPage(Integer page, Integer limit);

    void SaveProductSpec(ProductSpec productSpec);

    void UpdateProductSpecById(ProductSpec productSpec);

    void DeleteProductSpecById(Long id);

    List<ProductSpec> FindAllProductSpec();
}
