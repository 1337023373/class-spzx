package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "商品管理接口")
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "根据parentId查询菜单")
    @GetMapping("/getCategory/{parentId}")
    public Result<Category> getCategory(@PathVariable Long parentId) {
        List<Category> categoryList = categoryService.getCategory(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "导出功能")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    @Operation(summary = "导入功能")
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
