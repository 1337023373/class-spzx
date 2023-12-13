package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> getSysMenu();

    void addSysMenu(SysMenu sysMenu);

    void updateSysMenu(SysMenu sysMenu);

    void removeSysMenu(Long id);

    List<SysMenuVo> getSysRoleMenu();
}
