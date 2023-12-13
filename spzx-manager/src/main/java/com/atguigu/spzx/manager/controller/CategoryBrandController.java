package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "分类品牌管理")
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    @GetMapping("/{current}/{size}")
    @Operation(summary = "带条件的分页查询")
    public Result<CategoryBrand> GetCategoryBrandPage(@PathVariable Integer current, @PathVariable Integer size, CategoryBrandDto categoryBrandDto) {
        PageInfo<CategoryBrand> categoryBrandList = categoryBrandService.GetCategoryBrandPage(current, size, categoryBrandDto);
        return Result.build(categoryBrandList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加功能")
    @PostMapping("/save")
    public Result SaveCategoryBrand(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.SaveCategoryBrand(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新")
    @PutMapping("/updateById")
    public Result UpdateCategoryBrandById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.UpdateCategoryBrandById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "删除")
    public Result DeleteCategoryBrandById(@PathVariable Long id) {
      categoryBrandService.DeleteCategoryBrandById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据分类id查询品牌")
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result FindBrandByCategoryId(@PathVariable Long categoryId) {
//       根据前端的数据结构返回list集合,同时这里虽然要查category_brand表,但是前端需要的是brand的名称
//        所以这里的泛型类型给Brand这个实现类,这样网页出来的就是名字而不是数字
       List<Brand> categoryBrandList =  categoryBrandService.FindBrandByCategoryId(categoryId);
        return Result.build(categoryBrandList, ResultCodeEnum.SUCCESS);
    }
}
