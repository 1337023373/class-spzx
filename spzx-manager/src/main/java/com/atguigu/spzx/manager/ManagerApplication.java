package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.properties.MyMinioProperties;
import com.atguigu.spzx.manager.properties.UserAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.atguigu.spzx.manager.mapper")
//用它来代替@Mapper
@ComponentScan(basePackages = "com.atguigu.spzx")
@EnableConfigurationProperties(value ={ UserAuthProperties.class, MyMinioProperties.class})
//这里的调整是因为引入了异常处理,之前的扫描包的范围已经无法满足,所以扩大了范围
@SpringBootApplication
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class , args) ;
    }

}