package com.atguigu.spzx.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.model.entity.h5.UserInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request对象
        ServerHttpRequest request = exchange.getRequest();
        //获取response对象
        ServerHttpResponse response = exchange.getResponse();
        //获取请求路径
        String path = request.getURI().getPath();
        //判断path中是否包含auth
        if(path.contains("auth")){
            //证明需要登录，获取用户信息
            UserInfo userInfo = getUserInfo(request);
            if(userInfo == null){
                //给前端响应208的状态码，让前端自动跳转到登录页面
                return out(response,ResultCodeEnum.LOGIN_AUTH);
            }
        }
        //放行请求
        return chain.filter(exchange);
    }

    //获取用户信息的方法
    private UserInfo getUserInfo(ServerHttpRequest request) {
        //获取请求头中的token
        List<String> strings = request.getHeaders().get("token");
        if(!CollectionUtils.isEmpty(strings)){
            //获取token
            String token = strings.get(0);
            if(StringUtils.hasLength(token)){
                //从Redis中获取用户信息
                String userInfoStr = redisTemplate.opsForValue().get("user:login:" + token);
                if(StringUtils.hasLength(userInfoStr)){
                    //将userInfoStr转换为UserInfo对象
                    UserInfo userInfo = JSONObject.parseObject(userInfoStr, UserInfo.class);
                    return userInfo;
                }
            }
        }
        return null;
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
    @Override
    public int getOrder() {
        return 0;
    }
}
