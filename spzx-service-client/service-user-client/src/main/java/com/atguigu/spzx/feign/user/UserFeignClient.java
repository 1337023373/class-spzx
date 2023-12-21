package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.feign.user.impl.UserFeignClientFallback;
import com.atguigu.spzx.model.entity.h5.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// com.atguigu.spzx.feign.user;
@FeignClient(value = "service-user" , fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public abstract UserAddress getUserAddress(@PathVariable Long id) ;

}