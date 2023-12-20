package com.atguigu.spzx.common.service.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.util.AuthContextUtil;
import com.atguigu.spzx.model.entity.h5.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 在调用微服务接口之前将用户信息放到ThreadLocal中的拦截器
 */
@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token
        String token = request.getHeader("token");
        if(!StrUtil.isEmpty(token)){
            //从Redis中获取用户信息
            String userInfoStr = redisTemplate.opsForValue().get("user:login:" + token);
            if(!StrUtil.isEmpty(userInfoStr)){
                //转换为UserInfo对象
                UserInfo userInfo = JSONObject.parseObject(userInfoStr, UserInfo.class);
                //将UserInfo对象跟当前线程绑定
                AuthContextUtil.setUserInfo(userInfo);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //将ThreadLocal中的UserInfo对象移除
        AuthContextUtil.removeUserInfo();
    }
}
