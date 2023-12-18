package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.h5.IndexVo;

import java.util.List;

public interface CategotyService {
    IndexVo Index();

    List<Category> findCategoryTree();
}
