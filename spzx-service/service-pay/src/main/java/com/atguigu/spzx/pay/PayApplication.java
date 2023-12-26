package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.service.anno.EnableUserTokenFeignInterceptor;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu.spzx")
@MapperScan(basePackages = "com.atguigu.spzx.pay.mapper")
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign"
})
@EnableUserTokenFeignInterceptor
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

}