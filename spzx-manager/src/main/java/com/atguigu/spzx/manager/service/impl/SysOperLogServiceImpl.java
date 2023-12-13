package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.manager.service.SysOperLogService;
import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysOperLogServiceImpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public PageInfo<SysOperLog> getPageList(Integer current, Integer size, SysOperLogDto sysOperLogDto) {
        PageHelper.startPage(current, size);
       List<SysOperLog> list = sysOperLogMapper.getPageList(sysOperLogDto);
        return new PageInfo<>(list);
    }
}
