package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    void SaveProduct(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void UpdateProductAuditStatus(Long id, Integer auditStatus);

    void UpdateProductStatus(Long id, Integer status);
}
