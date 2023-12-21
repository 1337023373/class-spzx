package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.service.anno.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

// com.atguigu.spzx.cart;
@ComponentScan(basePackages = "com.atguigu.spzx")
//远程调用
@EnableFeignClients(basePackages = "com.atguigu.spzx")
//解决远程调用空指针情况
@EnableUserWebMvcConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 排除数据库的自动化配置，Cart微服务不需要访问数据库
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class , args) ;
    }

}