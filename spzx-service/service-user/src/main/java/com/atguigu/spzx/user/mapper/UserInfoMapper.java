package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.entity.h5.UserInfo;

public interface UserInfoMapper {



    UserInfo userRegister(String username);


    void save(UserInfo userInfo);

    void update(UserInfo userInfo);
}
