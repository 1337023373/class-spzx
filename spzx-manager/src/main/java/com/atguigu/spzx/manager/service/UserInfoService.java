package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.github.pagehelper.PageInfo;

public interface UserInfoService {
    PageInfo<ProductUnit> getPageList(Integer page, Integer limit);
}
