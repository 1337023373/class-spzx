package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {

    PageInfo<SysRole> getPageList(Integer current, Integer size, SysRoleDto sysRoleDto);

    void addSysRole(SysRole sysRole);

    void updateById(SysRole sysRole);

    void deletedSysRoleById(Long id);

    SysRole getSysRoleById(Long id);

    Map<String, Object> fingAllRole(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
