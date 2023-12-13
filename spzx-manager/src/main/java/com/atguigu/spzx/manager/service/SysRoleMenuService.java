package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuService {
    Map<String,Object> getRoleMenuById(Long roleId);

    void doAssignMenuId(AssginMenuDto assginMenuDto);
}
