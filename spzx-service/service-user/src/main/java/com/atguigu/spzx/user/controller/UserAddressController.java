package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址")
    @GetMapping("/auth/findUserAddressList")
    public Result getUserAddress() {
        List<UserAddress> userAddressList = userAddressService.getUserAddress();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取地址信息")
    @GetMapping("/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id) {
        return userAddressService.getById(id);
    }

    @Operation(summary = "新增用户地址")
    @PostMapping("/auth/save")
    public Result saveAddress(@RequestBody UserAddress userAddress) {
        List<UserAddress> userAddressList = userAddressService.saveAddress(userAddress);
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }
}

