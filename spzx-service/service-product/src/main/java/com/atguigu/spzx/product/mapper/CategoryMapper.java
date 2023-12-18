package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> showAllFirstCategory();

    List<Category> findAll();
}
