package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuMapper {

    List<SysMenu> getSysMenu();

    void addSysMenu(SysMenu sysMenu);

    void updateSysMenu(SysMenu sysMenu);

    void removeSysMenu(Long id);

    List<SysMenu> getSysRoleMenu(Long sysUserId);
}
