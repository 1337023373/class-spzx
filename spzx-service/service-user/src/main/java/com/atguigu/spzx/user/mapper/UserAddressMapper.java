package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.h5.UserAddress;

import java.util.List;

public interface UserAddressMapper {
    List<UserAddress> getUserAddress(Long userId);

    UserAddress getById(Long id);

//    新增地址
    void addUserAddress();

    UserAddress updateAddress(UserAddress userAddress);

    void deleteById(Long userId);

    List<UserAddress> save(UserAddress userAddress);
}
