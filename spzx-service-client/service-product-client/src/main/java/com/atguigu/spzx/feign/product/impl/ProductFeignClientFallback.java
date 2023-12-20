package com.atguigu.spzx.feign.product.impl;

import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// com.atguigu.spzx.feign.product.fallback
@Slf4j
@Component
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public Result<ProductSku> getBySkuId(Long skuId) {
        log.info("ProductFeignClientFallback...getBySkuId的方法执行了");
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}