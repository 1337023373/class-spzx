package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandMapper {


    List<Brand> showAll();

    void addBrand(Brand brand);

    Brand showBrandById(Long id);

    void update(Brand brand);

    void delete(Long id);

    List<Brand> findAll();
}
