package com.atguigu.spzx.gateway.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//       获取request对象
        ServerHttpRequest request = exchange.getRequest();
//        获取请求路径
        String path = request.getURI().getPath();
//        判断path中是否包含auth
        if (path.contains("auth")) {
            
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
