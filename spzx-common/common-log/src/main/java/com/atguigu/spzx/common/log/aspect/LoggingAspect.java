package com.atguigu.spzx.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncSysOperLogService;
import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.common.util.IpUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;

/**
 * 切面类
 */
@Aspect //声明当前类是一个切面
@Component //将切面类交给Spring管理
public class LoggingAspect {

    @Autowired
    private AsyncSysOperLogService asyncSysOperLogService;

    @Around(value = "@annotation(log)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log log) {
        //创建SysOperLog对象
        SysOperLog sysOperLog = new SysOperLog();
        try {
            //调用执行目标方法之前处理日志的方法
            beforeHandleLog(sysOperLog,joinPoint,log);
            //执行目标方法
            Object result = joinPoint.proceed();
            //调用执行目标方法之后处理日志的方法
            afterHandleLog(sysOperLog,log,result,null);
            return result;
        } catch (Throwable e) {
            //出现异常时也要调用afterHandleLog方法
            afterHandleLog(sysOperLog,log,null,e);
            throw new RuntimeException(e);
        }
    }

    //执行目标方法之后处理日志的方法
    private void afterHandleLog(SysOperLog sysOperLog, Log log, Object result, Throwable e) {
        //判断是否保存响应数据
        if(log.isSaveResponseData()){
            //将结果转换为JSON字符串
            String jsonResult = JSONObject.toJSONString(result);
            //给sysOperLog的jsonResult属性赋值
            sysOperLog.setJsonResult(jsonResult);
        }
        //给sysOperLog的status属性赋值为0
        sysOperLog.setStatus(0);
        //判断是否有异常
        if(e != null){
            //证明执行目标方法时有异常
            //给sysOperLog的status属性赋值为1
            sysOperLog.setStatus(1);
            //设置异常信息
            sysOperLog.setErrorMsg(e.getMessage());
        }
        //保存操作日志
        asyncSysOperLogService.saveAsyncSysOperLog(sysOperLog);
    }

    //执行目标方法之前处理日志的方法
    private void beforeHandleLog(SysOperLog sysOperLog, ProceedingJoinPoint joinPoint, Log log) {
        /**
         * sysOperLog对象的属性值来自一下四个对象:
         *  1.log
         *  2.joninPoint
         *  3.request
         *  4.sysUser
         */
        //获取request对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //获取请求方式
        String requestMethod = request.getMethod();
        //给sysOperLog对象设置请求方式
        sysOperLog.setRequestMethod(requestMethod);
        //获取请求地址
        String requestURI = request.getRequestURI();
        //给sysOperLog对象设置操作地址
        sysOperLog.setOperUrl(requestURI);
        //获取操作的ip地址
        String ipAddress = IpUtil.getIpAddress(request);
        //给sysOperLog对象设置操作的ip地址
        sysOperLog.setOperIp(ipAddress);
        //获取当前用户的名字
        String username = AuthContextUtil.get().getUsername();
        //给sysOperLog对象设置操作人的名字
        sysOperLog.setOperName(username);

        //获取操作的模块
        String title = log.title();
        //给sysOperLog设置操作模块
        sysOperLog.setTitle(title);
        //给sysOperLog设置操作人的类型
        sysOperLog.setOperatorType(log.operatorType().name());
        //给sysOperLog设置业务类型
        sysOperLog.setBusinessType(log.businessType().name());

        //获取被代理对象的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取目标方法的方法名
        String methodName = joinPoint.getSignature().getName();
        /*
            拼接完整的方法名
            com.atguigu.spzx.manager.controller.SysRoleController.saveSysRole
         */
        String newMethodName = className+"."+methodName+"()";
        //给sysOperLog设置方法名
        sysOperLog.setMethod(newMethodName);
        //判断是否需要保存请求数据
        if(log.isSaveRequestData()){
            //判断是否是POST请求或PUT请求
            if(HttpMethod.POST.name().equals(requestMethod)||HttpMethod.PUT.name().equals(requestMethod)){
                //获取请求参数
                Object[] args = joinPoint.getArgs();
                //将请求参数转换为JSON字符串
                String paramsJson = argsArrayToString(args);
                //给sysOperLog设置操作参数
                sysOperLog.setOperParam(paramsJson);
            }
        }
    }


    // 参数拼装
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {       // 判断是否为数组类型
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);  // 如果是数组，判断其元素类型是否为MultipartFile或其子类
        } else if (Collection.class.isAssignableFrom(clazz)) { // 判断是否为Collection集合类型
            Collection collection = (Collection) o;
            for (Object value : collection) {  // 只要有一个元素的类型为MultipartFile或其子类，则认为该集合对象为过滤对象
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {  // 判断是否为Map集合类型
            Map map = (Map) o;
            for (Object value : map.entrySet()) {  // 只要有一个Value的类型是MultipartFile或其子类，则认为该映射对象为过滤对象
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }

        // 如果以上条件都不满足，最后判断对象本身是否为MultipartFile、HttpServletRequest、HttpServletResponse类的实例
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
