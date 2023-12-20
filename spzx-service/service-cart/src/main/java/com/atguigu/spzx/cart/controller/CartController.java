package com.atguigu.spzx.cart.controller;

import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车管理接口")
@RestController
@RequestMapping("/api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "添加购物车")
    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId, @PathVariable Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "展示购物车")
    @GetMapping("/auth/cartList")
    public Result showCartList() {
        List<CartInfo> cartInfoList = cartService.showCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除购物车商品")
    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新购物车选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,@PathVariable Integer isChecked) {
         cartService.checkCart(skuId,isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "完成购物车全选状态")
    @GetMapping("/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked) {
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "清空购物车商品")
    @GetMapping ("/auth/clearCart")
    public Result clearCart() {
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
