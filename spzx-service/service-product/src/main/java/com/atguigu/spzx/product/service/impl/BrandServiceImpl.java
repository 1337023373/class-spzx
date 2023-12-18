package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.product.mapper.BrandMapper;
import com.atguigu.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> BrandFindAll() {
        List<Brand> brandList = brandMapper.BrandFindAll();
        return brandList;
    }
}
