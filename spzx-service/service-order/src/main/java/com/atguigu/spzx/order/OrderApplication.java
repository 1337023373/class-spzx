package com.atguigu.spzx.order;

import com.atguigu.spzx.common.service.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//  com.atguigu.spzx.order;
@SpringBootApplication
@EnableUserTokenFeignInterceptor
@ComponentScan(basePackages = "com.atguigu.spzx")
@EnableFeignClients(basePackages = "com.atguigu.spzx.feign.cart")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }

}