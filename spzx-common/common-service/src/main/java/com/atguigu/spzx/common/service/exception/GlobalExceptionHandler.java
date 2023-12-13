package com.atguigu.spzx.common.service.exception;

import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result doResolveException(Exception e) {
        e.printStackTrace();
        return Result.build(null, 444, e.getMessage());
    }

    @ExceptionHandler(value = GuiguException.class)
    public Result doResolveGuiguException(GuiguException e) {
        e.printStackTrace();
        return Result.build(null, e.getCode(), e.getMessage());
    }
}

