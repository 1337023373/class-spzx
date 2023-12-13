package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;

import java.util.List;

public interface SysUserManageMapper {
    List<SysUser> getPageList(SysUserDto sysUserDto);

    void addSysUser(SysUser sysUser);

    SysUser showSysUserById(Long id);

    void updateSysUseById(SysUser sysUser);

    void deletedSysUserById(Long id);
}

