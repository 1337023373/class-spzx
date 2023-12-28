package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.UserInfoMapper;
import com.atguigu.spzx.manager.service.UserInfoService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public PageInfo<ProductUnit> getPageList(Integer page, Integer limit) {
        return null;
    }
}
