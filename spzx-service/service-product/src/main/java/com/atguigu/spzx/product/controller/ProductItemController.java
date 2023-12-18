package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "详细商品展示接口")
@RestController
@RequestMapping("/api/product")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    @Operation(summary = "商品详情")
    @GetMapping("/item/{skuId}")
    public Result item(@PathVariable Long skuId) {
        ProductItemVo productItemVo = productItemService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

}
