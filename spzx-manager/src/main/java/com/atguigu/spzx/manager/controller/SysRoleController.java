package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
@Tag(name = "用户管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "分页带条件查询")
    @GetMapping("/{current}/{size}")
    public Result getPageList(@PathVariable Integer current, @PathVariable Integer size, SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = sysRoleService.getPageList(current, size, sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public Result<SysRole> addSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.addSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/getById/{id}")
    public Result<SysRole> getSysRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getSysRoleById(id);
        return Result.build(sysRole, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/update")
    public Result<SysRole> updateById(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户")
    //注意删除是通过is_deleted的更改来删除的， 所以实际上还是执行更改的sql
    @DeleteMapping("/{id}")
    public Result<SysRole> deletedSysRoleById(@PathVariable Long id) {
        sysRoleService.deletedSysRoleById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "根据id查询角色列表")
    @GetMapping("/fingAllRole/{userId}")
    public Result fingAllRole(@PathVariable Long userId) {
// 因为这个业务需要展示的数据在sys_role表,里面有两个数据id和role_name
// 所以返回的结构是两个对象,可以有其他方法,这里使用map对两个数据进行封装
        Map<String, Object> map = sysRoleService.fingAllRole(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysRoleService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
