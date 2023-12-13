package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "角色菜单接口")
@RestController
@RequestMapping("/admin/system/sysRoleMenu/")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    //    sys_menu表中的数据
    @Operation(summary = "展示角色中的所有的菜单")
    @GetMapping("/getRoleMenuById/{roleId}")
    public Result<SysMenu> getRoleMenuById(@PathVariable Long roleId) {
        Map<String,Object> roleMenuMap = sysRoleMenuService.getRoleMenuById(roleId);
        return Result.build(roleMenuMap, ResultCodeEnum.SUCCESS);
    }

    //    sys_menu表中的数据
    @Operation(summary = "提交角色中的所有的菜单")
    @PostMapping("/doAssignMenuId")
    public Result<SysMenu> doAssignMenuId(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssignMenuId(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
