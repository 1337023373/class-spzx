package com.atguigu.spzx.manager.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.atguigu.spzx.manager.mapper.SysUserManageMapper;
import com.atguigu.spzx.manager.service.SysUserManageService;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class SysUserManageServiceImpl implements SysUserManageService {
    @Autowired
    private SysUserManageMapper sysUserManageMapper;
    @Override
    public PageInfo<SysUser> getPageList(Integer current, Integer size, SysUserDto sysUserDto) {
//        引用pagehelper,再使用mapper方法,直到数据是list
        PageHelper.startPage(current, size);
        List<SysUser> sysUserList = sysUserManageMapper.getPageList(sysUserDto);
//        前端要的时pageInfo的数据，所以需要把从mapper那里得到的数据，封装到对象中
        PageInfo pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public void addSysUser(SysUser sysUser) {
//        需要对密码进行加密
        String digest = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(digest);
        sysUserManageMapper.addSysUser(sysUser);
    }

    @Override
    public SysUser showSysUserById(Long id) {
        SysUser sysUser = sysUserManageMapper.showSysUserById(id);
        return sysUser;
    }

    @Override
    public void updateSysUseById(SysUser sysUser) {
        sysUserManageMapper.updateSysUseById(sysUser);
    }

    @Override
    public void deletedSysUserById(Long id) {
        sysUserManageMapper.deletedSysUserById(id);
    }
}
