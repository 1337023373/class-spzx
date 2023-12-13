package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<Brand> getBrandPageList(Integer page, Integer limit) {

        PageHelper.startPage(page, limit);

        List<Brand> brandList = brandMapper.showAll();
        return new PageInfo<>(brandList);
    }

//    添加品牌
    @Override
    public void SaveBrand(Brand brand) {
        brandMapper.addBrand(brand);
    }

//    根据id查询
    @Override
    public Brand getBrandById(Long id) {
       Brand brand =  brandMapper.showBrandById(id);
        return brand;
    }

//    更新品牌
    @Override
    public void UpdateBrandById(Brand brand) {
        brandMapper.update(brand);
    }

//    删除
    @Override
    public void DeleteBrandById(Long id) {
        brandMapper.delete(id);
    }

    @Override
    public List<Brand> FindAllBrand() {
       List<Brand> list =  brandMapper.showAll();
        return list;
    }
}
