package com.atguigu.spzx.model.vo.common;

import lombok.Getter;

// com.atguigu.spzx.model.vo.common
@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200 , "操作成功") ,
    LOGIN_ERROR(201 , "用户名或者密码错误"),
    NOT_EMPTY(202,"账号或者密码为空" ),
    ACCOUNT_NOT_EXIST(203, "账号不存在"),
    PASSWORD_ERROR(204, "密码不正确"),
    ACCOUNT_STOP(205, "账号已禁用"),
    VALIDATECODE_EMPTY(206, "验证码为空"),
    VALIDATECODE_ERROR(207,"验证码不正确" ),
    LOGIN_AUTH(208, "没有登录"),
    ACCOUNT_EXIST(209, "账号已存在"),;

    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}