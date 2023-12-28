package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品单元/单位接口")
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    @Operation(summary = "展示商品单位")
    @GetMapping("/findAllProductUnit")
    public Result FindAllProductUnit() {
        List<ProductUnit> productUnitList = productUnitService.findAll();
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页展示商品单位")
    @GetMapping("/{page}/{limit}")
    public Result getPageList(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductUnit> pageInfo = productUnitService.getPageList(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加商品单位")
    @PostMapping("/save")
    public Result SaveProductUnit(@RequestBody ProductUnit productUnit) {
        ProductUnit productUnitList = productUnitService.save(productUnit);
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改商品单位")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductUnit productUnit) {
        ProductUnit productUnitList = productUnitService.update(productUnit);
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除商品单位")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productUnitService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
