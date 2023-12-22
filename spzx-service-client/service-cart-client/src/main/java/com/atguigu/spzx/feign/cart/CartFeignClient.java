package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.feign.cart.impl.CartFeignClientFallback;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart" , fallback = CartFeignClientFallback.class)
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public abstract Result<List<CartInfo>> getAllCkecked();

    @GetMapping("/api/order/cart/auth/deleteChecked")
    public Result deleteChecked();
}