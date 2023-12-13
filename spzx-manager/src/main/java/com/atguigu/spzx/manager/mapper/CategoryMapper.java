package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

public interface CategoryMapper {
    List<Category> getCategory(Long parentId);

    Integer getCountByParentId(Long id);

    List<Category> selectAll();

    void batchInsert(List<CategoryExcelVo> cachedDataList);
}
