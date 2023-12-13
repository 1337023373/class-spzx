package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysUserRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public PageInfo<SysRole> getPageList(Integer current, Integer size, SysRoleDto sysRoleDto) {
//        开启分页
        PageHelper.startPage(current,size);
//       调用mapper中的方法,拿到数据库中的数据
       List<SysRole> SysRoleList =  sysRoleMapper.getPageList(sysRoleDto);
//      创建分页对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(SysRoleList);
        return pageInfo;
    }

    @Override
    public void addSysRole(SysRole sysRole) {
        sysRoleMapper.addSysRole(sysRole);
    }

    @Override
    public void updateById(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }


    @Override
    public void deletedSysRoleById(Long id) {
        sysRoleMapper.deletedSysRoleById(id);
    }

    @Override
    public SysRole getSysRoleById(Long id) {
        SysRole sysRole = sysRoleMapper.getSysRoleById(id);
        return sysRole;
    }

    @Override
    public Map<String, Object> fingAllRole(Long userId) {
//        向mapper发送请求,得到一条条的数据,展示所有角色列表
        List<SysRole> sysRole = sysRoleMapper.fingAllRole();

//        同时通过id拿到对应的角色,返回整条数据
        List<Long> sysUserRoleList = sysUserRoleMapper.findUserRole(userId);
        Map<String,Object> map = new HashMap<>();
//        这里的key就需要同前端对应了
        map.put("allRoles",sysRole);
        map.put("userRoleIds", sysUserRoleList);
        return map;
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
//        传入的数据是对象，现在先根据id删除数据，就需要从对象中拿到id属性
        Long userId = assginRoleDto.getUserId();
        sysUserRoleMapper.deleDoAssign(userId);

//       获取对象中的角色id，在AssginRoleDto中的属性
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
//        判断数组是否为空
        if (!CollectionUtils.isEmpty(roleIdList)) {
            sysUserRoleMapper.insertDoAssign(assginRoleDto);
        }
    }
}
