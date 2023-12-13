package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

public interface SysUserManageService {
    PageInfo<SysUser> getPageList(Integer current, Integer size, SysUserDto sysUserDto);

    void addSysUser(SysUser sysUser);

    SysUser showSysUserById(Long id);

    void updateSysUseById(SysUser sysUser);

    void deletedSysUserById(Long id);
}
