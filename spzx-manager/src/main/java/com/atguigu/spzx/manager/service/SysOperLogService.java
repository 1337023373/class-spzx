package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import com.github.pagehelper.PageInfo;

public interface SysOperLogService {
    PageInfo<SysOperLog> getPageList(Integer current, Integer size, SysOperLogDto sysOperLogDto);
}
