package com.atguigu.spzx.feign.user.impl;

import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public UserAddress getUserAddress(Long id) {
        log.info("UserFeignClientFallback...getUserAddress的方法执行了");
        return null;
    }

}