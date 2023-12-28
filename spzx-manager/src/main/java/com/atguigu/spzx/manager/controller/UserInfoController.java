package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.UserInfoService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "")
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "")
    @GetMapping("/{page}/{limit}")
    public Result getPageList(@PathVariable Integer page, @PathVariable Integer limit) {
        PageInfo<ProductUnit> pageInfo = userInfoService.getPageList(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
