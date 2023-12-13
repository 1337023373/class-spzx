package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "品牌管理接口")
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Operation(summary = "展示所有品牌")
    @GetMapping("/{page}/{limit}")
    public Result getBrandPageList(@PathVariable Integer page, @PathVariable Integer limit) {
//        记清楚分页要返回的数据类型
        PageInfo<Brand> pageInfo = brandService.getBrandPageList(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加品牌")
    @PostMapping("/save")
    public Result<Brand> SaveBrand(@RequestBody Brand brand) {
        brandService.SaveBrand(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id查询")
//    用于点击修改时，展示数据
    @GetMapping("/getById/{id}")
    public Result<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        return Result.build(brand, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改品牌")
    @PutMapping("/updateById")
    public Result<Brand> UpdateBrandById(@RequestBody Brand brand) {
        brandService.UpdateBrandById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/deleteById/{id}")
    public Result<Brand> DeleteBrandById(@PathVariable Long id) {
        brandService.DeleteBrandById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有品牌")
//    用于分类品牌管理的展示
    @GetMapping("/findAll")
    public Result<Brand> FindAllBrand() {
       List<Brand> brandList =  brandService.FindAllBrand();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
