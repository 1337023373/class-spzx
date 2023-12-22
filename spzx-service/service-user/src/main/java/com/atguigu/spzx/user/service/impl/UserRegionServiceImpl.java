package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserRegionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegionServiceImpl implements UserRegionService {
    @Resource
    private UserAddressMapper userAddressMapper;
    @Override
    public UserAddress findByParentCode() {
//        获取当前线程的id
        Long userInfoId = AuthContextUtil.getUserInfo().getId();
        UserAddress userAddress = new UserAddress();
//        通过id是否为o,来判断是增加地址还是修改
        if (userInfoId == 0) {
            userAddressMapper.addUserAddress();
        }else {
//            先根据id展示对应的数据
            UserAddress address = userAddressMapper.getById(userInfoId);
            return address;
        }
        userAddress.setUserId(userInfoId);
       UserAddress userAddress1 =  userAddressMapper.updateAddress(userAddress);
        return userAddress1;
    }

    /**
     * 删除
     */
    @Override
    public void removeById() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        userAddressMapper.deleteById(userId);
    }


}
