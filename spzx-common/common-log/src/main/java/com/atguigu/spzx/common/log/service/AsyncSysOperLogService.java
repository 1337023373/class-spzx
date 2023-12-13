package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

/**
 * 保存操作日志的接口
 */
public interface AsyncSysOperLogService {
    /**
     * 保存操作日志的方法
     * @param sysOperLog
     */
    void saveAsyncSysOperLog(SysOperLog sysOperLog);
}
