package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductSkuMapper {
    List<ProductSku> showAllBySale();

    List<ProductSku> findProductSku(ProductSkuDto productSkuDto);

    ProductSku findProductSkuById(Long skuId);

    List<ProductSku> findProductSkuByParendId(Long productId);
}
