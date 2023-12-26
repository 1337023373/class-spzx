package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.dto.user.UserLoginDto;
import com.atguigu.spzx.model.dto.user.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.entity.user.UserCollect;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.user.UserInfoVo;
import com.atguigu.spzx.user.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "会员用户接口")
@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "会员注册")
    @PostMapping("/userInfo/register")
    public Result userRegister(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.userRegister(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员登录")
    @PostMapping("/userInfo/login")
//    这里的request是为了拿到后面的ip地址,做更新
    public Result login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        String token = userInfoService.login(userLoginDto,request);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户收藏")
    @GetMapping("/userInfo/auth/collect/{skuId}")
    public Result collect(@PathVariable Long skuId) {

        Boolean resultBoolean =  userInfoService.collect(skuId);
        return Result.build(resultBoolean, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户取消收藏")
    @GetMapping("/userInfo/auth/cancelCollect/{skuId}")
    public Result cancelCollect(@PathVariable Long skuId) {

        Boolean cancelCollect =  userInfoService.cancelCollect(skuId);
        return Result.build(cancelCollect, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询用户是否收藏")
    @GetMapping("/userInfo/isCollect/{skuId}")
    public Result isCollect(@PathVariable Long skuId) {
        Boolean resultBoolean =  userInfoService.isCollect(skuId);
        return Result.build(resultBoolean, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取用户收藏分页列表")
    @GetMapping("/userInfo/auth/findUserCollectPage/{page}/{limit}")
    public Result findUserCollectPage(@PathVariable Integer page,@PathVariable Integer limit) {
        PageInfo<UserCollect> pageInfo = userInfoService.findUserCollectPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存用户浏览历史")
    @GetMapping("/userBrowseHistory/save/{userId}/{skuId}")
    public Result userBrowseHistory(@PathVariable Long userId,@PathVariable Long skuId) {
         userInfoService.userBrowseHistory(userId, skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取用户浏览历史分页列表")
    @GetMapping("/userInfo/auth/findUserBrowseHistoryPage/{page}/{limit}")
    public Result findUserBrowseHistoryPage(@PathVariable Integer page,@PathVariable Integer limit) {
        PageInfo<UserBrowseHistory> pageInfo = userInfoService.findUserBrowseHistoryPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
