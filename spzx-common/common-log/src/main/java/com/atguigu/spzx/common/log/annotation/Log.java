package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.enums.BusinessType;
import com.atguigu.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类
 */
@Target(ElementType.METHOD)//指定该注解只能添加到方法上
@Retention(RetentionPolicy.RUNTIME)//指定注解的生命周期
public @interface Log {
    /**
     * 操作的模块
     */
    String title();
    /**
     * 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;
    /**
     * 操作人的类型
     */
    OperatorType operatorType() default OperatorType.MANAGE;
    /**
     * 是否保存请求数据
     */
    boolean isSaveRequestData() default true;
    /**
     * 是否保存响应数据
     */
    boolean isSaveResponseData() default true;

}
