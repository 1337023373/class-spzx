package com.atguigu.spzx.feign.cart.impl;

import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

// com.atguigu.spzx.feign.cart.fallback;
@Slf4j
@Component
public class CartFeignClientFallback implements CartFeignClient {

    @Override
    public Result<List<CartInfo>> getAllCkecked() {
        log.info("CartFeignClientFallback...getAllCkecked的方法执行了");
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
