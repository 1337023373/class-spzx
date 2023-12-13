package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> GetCategoryBrandPage(Integer current, Integer size, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(current, size);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.showAllByPage(categoryBrandDto);
        return new PageInfo<>(categoryBrandList);
    }

    @Override
    public void SaveCategoryBrand(CategoryBrand categoryBrand) {
        categoryBrandMapper.addCategoryBrand(categoryBrand);
    }

    @Override
    public void UpdateCategoryBrandById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateCategoryBrand(categoryBrand);
    }

    @Override
    public void DeleteCategoryBrandById(Long id) {
        categoryBrandMapper.deleteCategoryBrand(id);
    }

    @Override
    public List<Brand> FindBrandByCategoryId(Long categoryId) {
       List<Brand> categoryBrandList =  categoryBrandMapper.showBrandByCategoryId(categoryId);
        return categoryBrandList;
    }
}
