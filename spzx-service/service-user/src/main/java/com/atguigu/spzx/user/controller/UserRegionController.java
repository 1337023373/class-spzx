package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.h5.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "编辑修货地址接口")
@RestController
@RequestMapping("/api/user/region")
public class UserRegionController {
    @Autowired
    private UserRegionService userRegionService;

    @Operation(summary = "新增/修改用户地址")
    @GetMapping("/findByParentCode")
    public Result findByParentCode() {
       UserAddress userAddress =  userRegionService.findByParentCode();
        return Result.build(userAddress, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户地址")
    @GetMapping("/removeById")
    public Result removeById() {
         userRegionService.removeById();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
