package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductUnitMapper;
import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;
    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> productUnitList = productUnitMapper.findAll();
        return productUnitList;
    }

    /**
     * 添加商品单位
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageInfo<ProductUnit> getPageList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductUnit> productUnitList = productUnitMapper.find(page, limit);
        return new PageInfo<>(productUnitList);

    }

    /**
     * 添加商品单位
     * @param productUnit
     * @return
     */
    @Override
    public ProductUnit save(ProductUnit productUnit) {
        ProductUnit saveProductUnit = productUnitMapper.save(productUnit);
        return saveProductUnit;
    }

    /**
     * 更新商品单位
     * @param productUnit
     * @return
     */
    @Override
    public ProductUnit update(ProductUnit productUnit) {
        ProductUnit updateProductUnit = productUnitMapper.update(productUnit);
        return updateProductUnit;
    }

    @Override
    public void deleteById(Long id) {
        productUnitMapper.delete(id);
    }
}
