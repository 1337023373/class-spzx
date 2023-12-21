package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.h5.UserAddress;

import java.util.List;

public interface UserAddressMapper {
    List<UserAddress> getUserAddress(Long userId);
}
