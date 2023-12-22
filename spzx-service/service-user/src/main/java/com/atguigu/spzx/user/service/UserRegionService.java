package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.h5.UserAddress;

public interface UserRegionService {
    UserAddress findByParentCode();

    void removeById();
}
