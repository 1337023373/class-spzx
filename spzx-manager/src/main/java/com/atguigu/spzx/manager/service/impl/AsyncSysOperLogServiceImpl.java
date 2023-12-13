package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.log.service.AsyncSysOperLogService;
import com.atguigu.spzx.manager.mapper.AsyncSysOperLogMapper;
import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志模块中保存操作日志接口的实现类
 */
@Service
@Transactional
public class AsyncSysOperLogServiceImpl implements AsyncSysOperLogService {

    @Resource
    private AsyncSysOperLogMapper asyncSysOperLogMapper;

    @Async
    @Override
    public void saveAsyncSysOperLog(SysOperLog sysOperLog) {
        //调用SysOperLogMapper中保存的方法
        asyncSysOperLogMapper.save(sysOperLog);
    }
}
