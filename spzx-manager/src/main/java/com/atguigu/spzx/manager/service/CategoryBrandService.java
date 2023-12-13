package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> GetCategoryBrandPage(Integer current, Integer size, CategoryBrandDto categoryBrandDto);

    void SaveCategoryBrand(CategoryBrand categoryBrand);

    void UpdateCategoryBrandById(CategoryBrand categoryBrand);

    void DeleteCategoryBrandById(Long id);

    List<Brand> FindBrandByCategoryId(Long categoryId);
}
