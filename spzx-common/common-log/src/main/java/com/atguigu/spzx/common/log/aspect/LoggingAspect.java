package com.atguigu.spzx.common.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncSysOperLogService;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private AsyncSysOperLogService asyncSysOperLogService;

    @Around(value = "@annotation(log)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log log) {
        //            创建SysOperLog
        SysOperLog sysOperLog = new SysOperLog();
        try {
//            执行目标方法,并try,catch
            Object proceed = joinPoint.proceed();
//            调用执行方法前处理日志的方法
            beforeHandleLog(sysOperLog, joinPoint, log);
//            调用执行方法后处理日志的方法
            afterHandleLog(sysOperLog, log, proceed, null);
            return proceed;
        } catch (Throwable e) {
//            出现异常也要调用处理日志的方法
            afterHandleLog(sysOperLog, log, null, e);
            throw new RuntimeException(e);
        }
    }
    //   执行目标方法之后处理日志的方法
    private void afterHandleLog(SysOperLog sysOperLog, Log log, Object proceed, Throwable e) {
//        判断是否保存响应数据
        if (log.isSaveRequestData()) {
            String jsonProceed = JSONObject.toJSONString(proceed);
//            给sysOperLog的jsonProceed属性赋值
            sysOperLog.setJsonResult(jsonProceed);
        }
//
    }

    //   执行目标方法之前处理日志的方法
    private void beforeHandleLog(SysOperLog sysOperLog, ProceedingJoinPoint joinPoint, Log log) {

    }
}
