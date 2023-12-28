package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductUnitService {
    List<ProductUnit> findAll();

    PageInfo<ProductUnit> getPageList(Integer page, Integer limit);

    ProductUnit save(ProductUnit productUnit);

    ProductUnit update(ProductUnit productUnit);

    void deleteById(Long id);
}
