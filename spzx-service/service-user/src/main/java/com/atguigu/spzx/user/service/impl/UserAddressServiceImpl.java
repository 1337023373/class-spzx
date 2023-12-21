package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAddressServiceImpl implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取用户地址
     * @return
     */
    @Override
    public List<UserAddress> getUserAddress() {
//        通过redis中保存的数据拿到id
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<UserAddress> userAddressList =userAddressMapper.getUserAddress(userId);
        return userAddressList;
    }

    @Override
    public UserAddress getById(Long id) {
       return userAddressMapper.getById(id);
    }
}
