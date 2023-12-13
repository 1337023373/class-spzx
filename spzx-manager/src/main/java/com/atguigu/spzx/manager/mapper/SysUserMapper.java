package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface SysUserMapper {
    SysUser getSysUserByName(String userName);

    List<SysMenu> getSysRoleMenu(Long sysUserId);
}
