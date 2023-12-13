package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;

import java.util.List;

public interface SysRoleMapper {


    void updateById(SysRole sysRole);

    List<SysRole> getPageList(SysRoleDto sysRoleDto);

    void addSysRole(SysRole sysRole);

    void deletedSysRoleById(Long id);

    SysRole getSysRoleById(Long id);

    List<SysRole> fingAllRole();
}
