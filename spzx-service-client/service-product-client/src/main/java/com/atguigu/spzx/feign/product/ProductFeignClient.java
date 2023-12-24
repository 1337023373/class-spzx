package com.atguigu.spzx.feign.product;

import com.atguigu.spzx.feign.product.impl.ProductFeignClientFallback;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// com.atguigu.spzx.feign.product;
@FeignClient(value = "service-product",fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    @GetMapping("/api/product/getBySkuId/{skuId}")
    public abstract Result<ProductSku> getBySkuId(@PathVariable Long skuId) ;

//    支付完成后远程调用product接口，更改商品数量给
    @GetMapping("/api/product/updateSkuSaleNum/{skuId}/{skuNum}")
    public void updateSkuSaleNum(@PathVariable Long skuId,@PathVariable  Integer skuNum);
}