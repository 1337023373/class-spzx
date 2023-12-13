package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.entity.system.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    List<Long> findUserRole(Long userId);

    void deleDoAssign(Long userId);


    void insertDoAssign(AssginRoleDto assginRoleDto);
}
