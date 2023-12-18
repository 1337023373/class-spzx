package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.CategotyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/category")
@Tag(name = "分类接口管理")
//配置了网关,就不用再配跨域注解了 ,直接通过网关转发
//@CrossOrigin(allowedHeaders = "*", allowCredentials = "true",originPatterns = "*")
public class CategoryController {
    @Autowired
    private CategotyService categotyService;

    @Operation(summary = "获取分类树形数据")
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree() {
        List<Category> categoryList =categotyService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}

