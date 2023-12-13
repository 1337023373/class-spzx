package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductSpecServiceImpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Override
    public PageInfo<ProductSpec> GetProductSpecPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> productSpecList = productSpecMapper.getProductSpecAll();
        return new PageInfo<>(productSpecList);
    }

    @Override
    public void SaveProductSpec(ProductSpec productSpec) {
        productSpecMapper.addProductSpec(productSpec);
    }

    @Override
    public void UpdateProductSpecById(ProductSpec productSpec) {
        productSpecMapper.updateProductSpec(productSpec);
    }

    @Override
    public void DeleteProductSpecById(Long id) {
        productSpecMapper.deleteProductSpec(id);
    }

    @Override
    public List<ProductSpec> FindAllProductSpec() {
        return productSpecMapper.getProductSpecAll();
    }
}
