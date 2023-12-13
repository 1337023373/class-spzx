package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    PageInfo<Brand> getBrandPageList(Integer page, Integer limit);

    void SaveBrand(Brand brand);

    Brand getBrandById(Long id);

    void UpdateBrandById(Brand brand);

    void DeleteBrandById(Long id);

    List<Brand> FindAllBrand();
}
