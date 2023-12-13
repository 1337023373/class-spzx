package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysOperLogDto;
import com.atguigu.spzx.model.entity.system.SysOperLog;

import java.util.List;

public interface SysOperLogMapper {

    List<SysOperLog> getPageList(SysOperLogDto sysOperLogDto);
}
