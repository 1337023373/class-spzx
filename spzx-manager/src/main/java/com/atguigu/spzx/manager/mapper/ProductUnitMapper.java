package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.base.ProductUnit;

import java.util.List;

public interface ProductUnitMapper {
    List<ProductUnit> findAll();

    List<ProductUnit> find(Integer page, Integer limit);

    ProductUnit save(ProductUnit productUnit);

    ProductUnit update(ProductUnit productUnit);

    void delete(Long id);
}
