package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品规格管理")
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    @Operation(summary = "分页展示所有商品规格")
    public Result GetProductSpecPageList(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductSpec> productSpecPageInfo = productSpecService.GetProductSpecPage(page, limit);
        return Result.build(productSpecPageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加")
    @PostMapping("/save")
    public Result SaveProductSpec(@RequestBody ProductSpec productSpec) {
        productSpecService.SaveProductSpec(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改")
    @PutMapping("/updateById")
    public Result UpdateProductSpecById(@RequestBody ProductSpec productSpec) {
        productSpecService.UpdateProductSpecById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "删除")
    public Result DeleteProductSpecById(@PathVariable Long id) {
        productSpecService.DeleteProductSpecById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("findAllProductSpec")
    @Operation(summary = "展示所有商品规格")
    public Result FindAllProductSpec() {
        List<ProductSpec> productSpecList = productSpecService.FindAllProductSpec();
        return Result.build(productSpecList, ResultCodeEnum.SUCCESS);
    }
}
