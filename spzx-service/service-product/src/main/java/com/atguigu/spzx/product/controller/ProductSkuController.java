package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.ProductSkuService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品列表管理")
@RestController
@RequestMapping("/api/product")
public class ProductSkuController {
    @Autowired
    private ProductSkuService productSkuService;

    @Operation(summary = "")
    @GetMapping("/{current}/{size}")
    public Result findProductSku(@PathVariable Integer current, @PathVariable Integer size, ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productSkuService.findProductSku(current, size, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

//    远程调用：根据skuid返回信息
    @Operation(summary = "获取商品sku信息")
    @GetMapping("/getBySkuId/{skuId}")
    public Result<ProductSku> getBySkuId(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductSku productSku = productSkuService.getBySkuId(skuId);
        return Result.build(productSku , ResultCodeEnum.SUCCESS) ;
    }

}
