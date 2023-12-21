package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址")
    @GetMapping("auth/findUserAddressList")
    public Result getUserAddress() {
        List<UserAddress> userAddressList = userAddressService.getUserAddress();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }
}
