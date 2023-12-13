package com.atguigu.spzx.manager.controller;


import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //    sys_menu表中的数据
    @Operation(summary = "展示所有数据")
    @GetMapping("/showAll")
    public Result<SysMenu> getSysMenu() {
        List<SysMenu> sysMenuList = sysMenuService.getSysMenu();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加数据")
    @PostMapping ("/addSysMenu")
    public Result<SysMenu> addSysMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.addSysMenu(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "更新数据")
    @PutMapping ("/updateSysMenu")
    public Result<SysMenu> updateSysMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateSysMenu(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除数据")
    @DeleteMapping ("/removeSysMenu/{id}")
    public Result<SysMenu> removeSysMenu(@PathVariable Long id) {
        sysMenuService.removeSysMenu(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
