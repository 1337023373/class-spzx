package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.CategotyService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategotyServiceImpl implements CategotyService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;
    @Override
    public IndexVo Index() {
//    展示所有一级分类
        List<Category> categoryList = categoryMapper.showAllFirstCategory();
//        根据销量展示所有
        List<ProductSku> productSkuList = productSkuMapper.showAllBySale();
//        把得到的数据存到创建好的类中
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return indexVo;
    }

    @Cacheable(value = "category",key = "'tree'")
    @Override
    public List<Category> findCategoryTree() {
////        先从数据库中拿到所有数据
//        List<Category> categoryList = categoryMapper.findAll();
////        把得到的数据通过Steam流进行tree展示
////        把parentId == 0 的一级菜单遍历出来
//        List<Category> firstCategoryList = categoryList.stream().filter(category -> category.getParentId().longValue() ==0).collect(Collectors.toList());
////        通过
//        if (!CollectionUtils.isEmpty(firstCategoryList)) {
//            firstCategoryList.forEach(firstLevelCategory ->{
//                List<Category> secondCategoryList = categoryList.stream().filter(category -> category.getParentId().longValue() == firstLevelCategory.getId().longValue()).collect(Collectors.toList());
//                firstLevelCategory.setChildren(secondCategoryList);
//                if (!CollectionUtils.isEmpty(secondCategoryList)) {
//                    secondCategoryList.forEach(secondLevelCategory ->{
//                        List<Category> thirdCategoryList = categoryList.stream().filter(category -> category.getParentId().longValue() == secondLevelCategory.getId().longValue()).collect(Collectors.toList());
//                        if (!CollectionUtils.isEmpty(thirdCategoryList)) {
//                            secondLevelCategory.setChildren(thirdCategoryList);
//                        }
//                    });
//                }
//            });
//        }
//        return firstCategoryList;
//        第一步,拿到所有菜单的数据
        List<Category> categoryList = categoryMapper.findAll();
//        第二步,遍历所有数据，可以使用原始的递归遍历赋值，也可以用steam流进行遍历，通过parentId = 0 ,得到一级菜单
        List<Category> oneCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == 0).collect(Collectors.toList());
//        第三步，遍历一级菜单，同时遍历所有菜单，通过parentId = id ,判断是否有子级菜单
        oneCategoryList.forEach(oneCategory ->{
            List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
//            把二级菜单封装
            oneCategory.setChildren(twoCategoryList);
//        第四步，重复上一步，因为已知有三级菜单，所有再才遍历二级菜单，且遍历所有菜单，通过parentId = id ,判断是否有子级菜单
            twoCategoryList.forEach(twoCategory -> {
                List<Category> threeCategory = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                twoCategory.setChildren(threeCategory);
            });
        });
        return oneCategoryList;
    }

}
