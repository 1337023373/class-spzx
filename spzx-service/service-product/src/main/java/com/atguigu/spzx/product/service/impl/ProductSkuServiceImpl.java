package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductSkuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductSkuServiceImpl implements ProductSkuService {
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Override
    public PageInfo<ProductSku> findProductSku(Integer current, Integer size, ProductSkuDto productSkuDto) {
        PageHelper.startPage(current, size);
        List<ProductSku> productSkuList = productSkuMapper.findProductSku(productSkuDto);
            return new PageInfo<>(productSkuList);
    }

    /**
     * 通过skuid得到数据
     * @param skuId
     * @return
     */
    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.findProductSkuById(skuId);
    }

    @Override
    public void updateSkuSaleNum(Long skuId, Integer skuNum) {
        productSkuMapper.updateSkuSaleNum(skuId,skuNum);
    }

}
