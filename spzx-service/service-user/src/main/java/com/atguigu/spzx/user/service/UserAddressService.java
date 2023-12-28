package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.h5.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> getUserAddress();

    UserAddress getById(Long id);

    List<UserAddress> saveAddress(UserAddress userAddress);
}
