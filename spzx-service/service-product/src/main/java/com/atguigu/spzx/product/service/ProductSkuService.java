package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageInfo;

public interface ProductSkuService {
    PageInfo<ProductSku> findProductSku(Integer current, Integer size, ProductSkuDto productSkuDto);

    ProductSku getBySkuId(Long skuId);
}
