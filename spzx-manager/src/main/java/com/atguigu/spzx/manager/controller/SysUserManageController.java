package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserManageService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserManageController {
    @Autowired
    private SysUserManageService sysUserManageService;

    @Operation(summary = "带条件的分页查询")
    @GetMapping("/{current}/{size}")
//    带当前页,每页数据以及实体类
    public Result<SysUser> getPageList(@PathVariable Integer current, @PathVariable Integer size, SysUserDto sysUserDto) {
//        返回pageIngo
        PageInfo<SysUser> pageInfo = sysUserManageService.getPageList(current, size, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public Result<SysUser> addSysUser(@RequestBody SysUser sysUser) {
        sysUserManageService.addSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id查询用户")
    @GetMapping("/{id}")
    public Result<SysUser> showSysUserById(@PathVariable Long id) {
        SysUser sysUser = sysUserManageService.showSysUserById(id);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/update")
    public Result<SysUser> updateSysUseById(@RequestBody SysUser sysUser) {
        sysUserManageService.updateSysUseById(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<SysUser> deletedSysUserById(@PathVariable Long id) {
        sysUserManageService.deletedSysUserById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
