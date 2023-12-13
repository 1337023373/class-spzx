package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysRoleMenuMapper {

    List<Long> getRoleMenu(Long roleId);

    void deleteRoleMenu(Long roleId);

    void insertRoleMenu(AssginMenuDto assginMenuDto);
}
